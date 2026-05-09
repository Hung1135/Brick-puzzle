export const COLS = 10;
export const ROWS = 20;
export const CELL = 28;
export const NEXT_CELL = 22;

export const TETROMINOES = [
  { shape: [[0,0,0,0],[1,1,1,1],[0,0,0,0],[0,0,0,0]], color: '#00f5ff' },
  { shape: [[1,1],[1,1]], color: '#ffe600' },
  { shape: [[0,1,0],[1,1,1],[0,0,0]], color: '#c87dff' },
  { shape: [[0,1,1],[1,1,0],[0,0,0]], color: '#00ff87' },
  { shape: [[1,1,0],[0,1,1],[0,0,0]], color: '#ff2d6e' },
  { shape: [[1,0,0],[1,1,1],[0,0,0]], color: '#ff8c00' },
  { shape: [[0,0,1],[1,1,1],[0,0,0]], color: '#1a8fff' },
];

export const SCORE_TABLE = [0, 100, 300, 500, 800];
export const SPEEDS = [800,700,600,500,400,300,250,200,150,100];