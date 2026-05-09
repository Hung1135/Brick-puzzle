import { COLS, ROWS, TETROMINOES, SCORE_TABLE, SPEEDS } from "../constants.js";

export default class GameModel {
  constructor() {
    this.reset();
  }

  // ─────────────────────────────
  // INIT
  // ─────────────────────────────
  reset() {
    this.board = Array.from({ length: ROWS }, () => Array(COLS).fill(null));
    this.score = 0;
    this.lines = 0;
    this.level = 1;
    this.gameOver = false;

    this.current = null;
    this.next = null;

    this._bag = [];
    this._fillBag();

    this.next = this._nextPiece();
    this._spawnPiece();
  }

  // ─────────────────────────────
  // BAG SYSTEM
  // ─────────────────────────────
  _fillBag() {
    this._bag = [...Array(TETROMINOES.length).keys()];
    for (let i = this._bag.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [this._bag[i], this._bag[j]] = [this._bag[j], this._bag[i]];
    }
  }

  _nextPiece() {
    if (this._bag.length === 0) this._fillBag();

    const idx = this._bag.pop();
    const t = TETROMINOES[idx];

    return {
      shape: t.shape.map(r => [...r]),
      color: t.color,
      x: Math.floor(COLS / 2) - Math.floor(t.shape[0].length / 2),
      y: 0,
    };
  }

  _spawnPiece() {
    this.current = this.next;
    this.next = this._nextPiece();

    if (this._collides(this.current, 0, 0)) {
      this.gameOver = true;
    }
  }

  // ─────────────────────────────
  // COLLISION
  // ─────────────────────────────
  _collides(piece, dx, dy, shape) {
    const s = shape || piece.shape;

    for (let r = 0; r < s.length; r++) {
      for (let c = 0; c < s[r].length; c++) {
        if (!s[r][c]) continue;

        const nx = piece.x + c + dx;
        const ny = piece.y + r + dy;

        if (nx < 0 || nx >= COLS || ny >= ROWS) return true;
        if (ny >= 0 && this.board[ny][nx]) return true;
      }
    }
    return false;
  }

  // ─────────────────────────────
  // ROTATE
  // ─────────────────────────────
  _rotate(shape, dir = 1) {
    const N = shape.length;
    const M = shape[0].length;
    const res = Array.from({ length: M }, () => Array(N).fill(0));

    for (let r = 0; r < N; r++)
      for (let c = 0; c < M; c++)
        if (dir === 1)
          res[c][N - 1 - r] = shape[r][c];
        else
          res[M - 1 - c][r] = shape[r][c];

    return res;
  }

  rotate(dir = 1) {
    const rotated = this._rotate(this.current.shape, dir);
    const kicks = [0, -1, 1, -2, 2];

    for (const k of kicks) {
      if (!this._collides(this.current, k, 0, rotated)) {
        this.current.shape = rotated;
        this.current.x += k;
        return;
      }
    }
  }

  // ─────────────────────────────
  // MOVE
  // ─────────────────────────────
  moveLeft() {
    if (!this._collides(this.current, -1, 0)) {
      this.current.x--;
    }
  }

  moveRight() {
    if (!this._collides(this.current, 1, 0)) {
      this.current.x++;
    }
  }

  softDrop() {
    if (!this._collides(this.current, 0, 1)) {
      this.current.y++;
      this.score += 1;
      return false;
    }

    this._lockPiece();
    return true;
  }

  hardDrop() {
    let dist = 0;
    while (!this._collides(this.current, 0, dist + 1)) {
      dist++;
    }

    this.current.y += dist;
    this.score += dist * 2;

    this._lockPiece();
  }

  // ─────────────────────────────
  // LOCK + CLEAR
  // ─────────────────────────────
  _lockPiece() {
    const { shape, x, y, color } = this.current;

    for (let r = 0; r < shape.length; r++) {
      for (let c = 0; c < shape[r].length; c++) {
        if (!shape[r][c]) continue;

        const ny = y + r;
        if (ny < 0) {
          this.gameOver = true;
          return;
        }

        this.board[ny][x + c] = color;
      }
    }

    const cleared = this._clearLines();
    this._addScore(cleared);
    this._spawnPiece();
  }

  _clearLines() {
    let count = 0;

    for (let r = ROWS - 1; r >= 0; r--) {
      if (this.board[r].every(cell => cell !== null)) {
        this.board.splice(r, 1);
        this.board.unshift(Array(COLS).fill(null));
        count++;
        r++;
      }
    }

    return count;
  }

  _addScore(lines) {
    if (lines === 0) return;

    this.score += (SCORE_TABLE[lines] || 800) * this.level;
    this.lines += lines;

    this.level = Math.floor(this.lines / 10) + 1;
  }

  // ─────────────────────────────
  // UTIL
  // ─────────────────────────────
  getGhostY() {
    let dist = 0;
    while (!this._collides(this.current, 0, dist + 1)) {
      dist++;
    }
    return this.current.y + dist;
  }

  getDropSpeed() {
    return SPEEDS[Math.min(this.level - 1, SPEEDS.length - 1)];
  }
}