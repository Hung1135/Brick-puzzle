import { COLS, ROWS, CELL, NEXT_CELL } from "../constants.js";

export default class GameView {
  constructor() {
    this.boardCanvas = document.getElementById('board-canvas');
    this.ctx = this.boardCanvas.getContext('2d');

    this.nextCanvas = document.getElementById('next-canvas');
    this.nctx = this.nextCanvas.getContext('2d');

    this.scoreEl = document.getElementById('score-display');
    this.linesEl = document.getElementById('lines-display');
    this.levelEl = document.getElementById('level-badge');
    this.hiEl = document.getElementById('hi-display');
    this.finalEl = document.getElementById('final-score-text');

    this.startScreen = document.getElementById('start-screen');
    this.pauseScreen = document.getElementById('pause-screen');
    this.gameoverScreen = document.getElementById('gameover-screen');
  }

  // ─────────────────────────────
  // RENDER BOARD
  // ─────────────────────────────
  render(model) {
    const ctx = this.ctx;
    ctx.clearRect(0, 0, COLS * CELL, ROWS * CELL);

    // Grid
    ctx.strokeStyle = '#ffffff08';
    for (let r = 0; r < ROWS; r++) {
      for (let c = 0; c < COLS; c++) {
        ctx.strokeRect(c * CELL, r * CELL, CELL, CELL);
      }
    }

    // Board
    for (let r = 0; r < ROWS; r++) {
      for (let c = 0; c < COLS; c++) {
        if (model.board[r][c]) {
          this._drawCell(ctx, c, r, model.board[r][c], CELL);
        }
      }
    }

    if (model.current) {
      // Ghost
      const gy = model.getGhostY();
      this._drawPiece(ctx, model.current, gy, CELL, 0.2);

      // Current
      this._drawPiece(ctx, model.current, model.current.y, CELL, 1);
    }
  }

  _drawPiece(ctx, piece, yOverride, size, alpha = 1) {
    const { shape, x, color } = piece;
    ctx.globalAlpha = alpha;

    for (let r = 0; r < shape.length; r++) {
      for (let c = 0; c < shape[r].length; c++) {
        if (shape[r][c]) {
          this._drawCell(ctx, x + c, yOverride + r, color, size);
        }
      }
    }

    ctx.globalAlpha = 1;
  }

  _drawCell(ctx, x, y, color, size) {
    const pad = 1.5;
    const s = size - pad * 2;

    ctx.fillStyle = color;
    ctx.shadowColor = color;
    ctx.shadowBlur = 10;
    ctx.fillRect(x * size + pad, y * size + pad, s, s);

    ctx.shadowBlur = 0;
  }

  // ─────────────────────────────
  // NEXT PIECE
  // ─────────────────────────────
  renderNext(piece) {
    const ctx = this.nctx;
    const W = this.nextCanvas.width;
    const H = this.nextCanvas.height;

    ctx.clearRect(0, 0, W, H);

    const cols = piece.shape[0].length;
    const rows = piece.shape.length;

    const offX = Math.floor((W / NEXT_CELL - cols) / 2);
    const offY = Math.floor((H / NEXT_CELL - rows) / 2);

    for (let r = 0; r < rows; r++) {
      for (let c = 0; c < cols; c++) {
        if (piece.shape[r][c]) {
          this._drawCell(ctx, offX + c, offY + r, piece.color, NEXT_CELL);
        }
      }
    }
  }

  // ─────────────────────────────
  // HUD
  // ─────────────────────────────
  updateHUD(model, hi) {
    this.scoreEl.textContent = model.score.toLocaleString();
    this.linesEl.textContent = model.lines;
    this.levelEl.textContent = model.level;
    this.hiEl.textContent = hi.toLocaleString();
  }

  flashLevelUp() {
    this.levelEl.classList.add('level-up');
    setTimeout(() => this.levelEl.classList.remove('level-up'), 300);
  }


  showStart() {
    this._showOnly('start-screen');
  }

  showPause() {
    this._showOnly('pause-screen');
  }

  showGameOver(score, hi, newRecord) {
    if (this.finalEl) {
      this.finalEl.innerHTML = `SCORE: ${score}` + (newRecord ? '<br><span class="new-record">NEW RECORD!</span>' : '');
    }
    if (this.hiEl) {
      this.hiEl.textContent = hi.toLocaleString();
    }
    this._showOnly('gameover-screen');
  }

  hideAll() {
    ['start-screen', 'pause-screen', 'gameover-screen']
      .forEach(id => {
        const el = document.getElementById(id);
        if (el) el.classList.add('hidden');
      });
  }

  _showOnly(id) {
    ['start-screen', 'pause-screen', 'gameover-screen']
      .forEach(sid => {
        const el = document.getElementById(sid);
        if (!el) return;
        el.classList[sid === id ? 'remove' : 'add']('hidden');
      });
  }
}