import GameModel from "../model/GameModel.js";
import GameView from "../view/GameView.js";

export default class GameController {

    constructor() {

        this.model = new GameModel();
        this.view = new GameView();

        this.state = 'start'; // start | playing | paused | over

        this.hi = parseInt(localStorage.getItem('tetris_hi') || '0');

        this.newRecord = false;

        this._lastTick = 0;
        this._rafId = null;
        this._dropAcc = 0;

        // FIX GAME OVER MULTIPLE CALL
        this.gameOverHandled = false;

        this._bindInputs();

        this.view.showStart();
        this.view.updateHUD(this.model, this.hi);

        this.contextPath = window.location.pathname
            .split("/tetris-mvc")[0];
    }

    // ─────────────────────────────
    // INPUT
    // ─────────────────────────────

    _bindInputs() {

        document.addEventListener('keydown', e => this._onKey(e));

        const startBtn = document.getElementById('start-btn');
        const resumeBtn = document.getElementById('resume-btn');
        const restartBtn = document.getElementById('restart-btn');
        const homeBtn = document.getElementById('home-btn');

        if (startBtn) {
            startBtn.onclick = () => this.startGame();
        }

        if (resumeBtn) {
            resumeBtn.onclick = () => this.resume();
        }

        if (restartBtn) {
            restartBtn.onclick = () => this.restartGame();
        }

        if (homeBtn) {
            homeBtn.onclick = () => this._returnToMenu();
        }
    }

    // ─────────────────────────────
    // GAME FLOW
    // ─────────────────────────────

    startGame() {

        if (this.state === 'playing') return;

        this.model.reset();

        // RESET FLAG
        this.gameOverHandled = false;

        this.state = 'playing';

        this.newRecord = false;

        this.view.hideAll();

        this._syncView();

        this._dropAcc = 0;

        this._lastTick = performance.now();

        if (this._rafId) {
            cancelAnimationFrame(this._rafId);
        }

        this._loop(this._lastTick);
    }

    restartGame() {

        if (this.state !== 'over') return;

        this.startGame();
    }

    _returnToMenu() {

        this.stopGame();

        this.view.showStart();
    }

    stopGame() {

        if (this.state === 'over') return;

        this.state = 'over';

        if (this._rafId) {
            cancelAnimationFrame(this._rafId);
        }

        // Xóa current piece để render lại board sạch (không còn gạch cuối)
        this.model.current = null;
        this.view.render(this.model);

        this._updateHighScore();

        this.view.showGameOver(
            this.model.score,
            this.hi,
            this.newRecord
        );
    }

    pause() {

        if (this.state !== 'playing') return;

        this.state = 'paused';

        cancelAnimationFrame(this._rafId);

        this.view.showPause();
    }

    resume() {

        if (this.state !== 'paused') return;

        this.state = 'playing';

        this.view.hideAll();

        this._lastTick = performance.now();

        this._loop(this._lastTick);
    }

    // ─────────────────────────────
    // GAME OVER
    // ─────────────────────────────

    _checkGameOver() {

        if (this.gameOverHandled) return;

        if (this.model.gameOver) {

            // SET FLAG NGAY TẠI ĐÂY (sync) trước khi gọi async handleGameOver
            // Tránh race condition: loop + keypress đều gọi _checkGameOver
            // trước khi await fetch() trong handleGameOver kịp chạy
            this.gameOverHandled = true;

            this.handleGameOver();
        }
    }

    async handleGameOver() {

        console.log("GAME OVER FUNCTION RUNNING");

        try {

            const response = await fetch(
                `${window.CONTEXT_PATH}/save-score`,
                {
                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify({

                        score: this.model.score,

                        level: this.model.level,

                        linesCleared: this.model.lines,

                        playTimeSeconds: 120
                    })
                }
            );

            console.log("STATUS:", response.status);

            const text = await response.text();

            console.log("RESPONSE:", text);

        } catch (e) {

            console.error("SAVE SCORE ERROR:", e);
        }

        this.stopGame();
    }

    _updateHighScore() {

        if (this.model.score > this.hi) {

            this.hi = this.model.score;

            localStorage.setItem('tetris_hi', this.hi);

            this.newRecord = true;
        }
    }

    // ─────────────────────────────
    // INPUT HANDLER
    // ─────────────────────────────

    _onKey(e) {

        if (this.state !== 'playing') {

            if (e.code === 'KeyP' && this.state === 'paused') {
                this.resume();
            }

            return;
        }

        const m = this.model;

        switch (e.code) {

            case 'ArrowLeft':
                m.moveLeft();
                break;

            case 'ArrowRight':
                m.moveRight();
                break;

            case 'ArrowDown':
                m.softDrop();
                this._dropAcc = 0;
                break;

            case 'ArrowUp':
            case 'KeyZ':
                m.rotate(1);
                break;

            case 'KeyX':
                m.rotate(-1);
                break;

            case 'Space':
                m.hardDrop();
                break;

            case 'KeyP':
                this.pause();
                break;

            default:
                return;
        }

        e.preventDefault();

        this._checkGameOver();

        this._syncView();
    }

    // ─────────────────────────────
    // GAME LOOP
    // ─────────────────────────────

    _loop(timestamp) {

        // DỪNG LOOP NẾU KHÔNG PLAYING
        if (this.state !== 'playing') return;

        const dt = timestamp - this._lastTick;

        this._lastTick = timestamp;

        this._dropAcc += dt;

        const speed = this.model.getDropSpeed();

        let leveledUp = false;

        if (this._dropAcc >= speed) {

            this._dropAcc -= speed;

            const prevLevel = this.model.level;

            if (!this.model._collides(this.model.current, 0, 1)) {

                this.model.current.y++;

            } else {

                this.model._lockPiece();

                if (this.model.level !== prevLevel) {
                    leveledUp = true;
                }
            }

            this._checkGameOver();
        }

        if (leveledUp) {
            this.view.flashLevelUp();
        }

        this._syncView();

        if (this.state === 'playing') {

            this._rafId = requestAnimationFrame(
                ts => this._loop(ts)
            );
        }
    }

    // ─────────────────────────────
    // VIEW SYNC
    // ─────────────────────────────

    _syncView() {

        this.view.render(this.model);

        this.view.renderNext(this.model.next);

        this.view.updateHUD(this.model, this.hi);
    }
}