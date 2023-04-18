package ie.tudublin;

import processing.core.PApplet;

public class Tetris extends PApplet {
  // game board dimensions
  final int ROWS = 20;
  final int COLS = 10;
  final int CELL_SIZE = 30;

  // current falling piece
  int[][] currentPiece = new int[4][2];
  int[][] ghostPiece = new int[4][2];

  int currentType;
  int nextType;
  
  int currentRow;
  int currentCol;
  
  // game board
  int[][] board = new int[ROWS][COLS];

  // tetromino pieces
  final int[][][] tetrominoes = {
    {{0, 0}, {1, 0}, {0, 1}, {1, 1}}, // square
    {{0, 0}, {-1, 0}, {1, 0}, {2, 0}}, // line
    {{0, 0}, {1, 0}, {-1, 1}, {0, 1}}, // z
    {{0, 0}, {-1, 0}, {1, 1}, {0, 1}}, // s
    {{0, 0}, {-1, 0}, {1, 0}, {1, 1}}, // L
    {{0, 0}, {1, 0}, {-1, 0}, {-1, 1}}, // J
    {{0, 0}, {-1, 0}, {1, 0}, {0, 1}} // T
  };

  private int moveInterval = 10; // set to 30 frames per move
  private int fallInterval = 30; // set to 60 frames per second

  public void settings()
  {
    size(COLS * CELL_SIZE, ROWS * CELL_SIZE);
  }

  public void setup() {
    frameRate(60); // adjust the speed of the game
    newPiece();
  }

  public void draw() {
    background(0);
    drawBoard();
    drawPiece();
    drawGhostPiece();
    update();
    // movePiece();
  }

  public void update() {
    // move the current piece down every few frames
    fallInterval--;
    if (fallInterval == 0) {
      movePiece(0, 1);
      fallInterval = moveInterval;
    }
  }


  public void keyPressed() {
    if (keyCode == LEFT) {
      movePiece(-1, 0);
    } else if (keyCode == RIGHT) {
      movePiece(1, 0);
    } else if (keyCode == DOWN) {
      movePiece(0, 1);
    } else if (keyCode == UP) {
      rotatePiece();
    } else if (keyCode == ' ') {
      // move the current piece down until it collides with either the bottom or another locked piece
      while (validMove(currentPiece, currentRow + 1, currentCol)) {
        currentRow++;
      }
      lockPiece(); // lock the piece in place
      newPiece(); // create a new piece
    }
  }

  
  // draw the game board
  public void drawBoard() {
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        if (board[i][j] != 0) {
          fill(255, 0, 0); // change the color to your liking
          rect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
      }
    }
  }

  // draw the current falling piece
  public void drawPiece() {
    for (int i = 0; i < 4; i++) {
      int row = currentRow + currentPiece[i][1];
      int col = currentCol + currentPiece[i][0];
      fill(255); // change the color to your liking
      rect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }
  }

    // rotate the current falling piece
  public void rotatePiece() {
    int[][] newPiece = new int[4][2];
    for (int i = 0; i < 4; i++) {
      newPiece[i][0] = currentPiece[i][1];
      newPiece[i][1] = -currentPiece[i][0];
    }
    if (validMove(newPiece, currentRow, currentCol)) {
      currentPiece = newPiece;
    }
  }

  // check if a move is valid
  public boolean validMove(int[][] piece, int row, int col) {
    for (int i = 0; i < 4; i++) {
      int newRow = row + piece[i][1];
      int newCol = col + piece[i][0];
      if (newRow < 0 || newRow >= ROWS || newCol < 0 || newCol >= COLS || board[newRow][newCol] != 0) {
        return false;
      }
    }
    return true;
  }

  public void drawGhostPiece() {
    // copy the current piece to the ghost piece
    for (int i = 0; i < 4; i++) {
      ghostPiece[i][0] = currentPiece[i][0];
      ghostPiece[i][1] = currentPiece[i][1];
    }
    // move the ghost piece down until it reaches the lowest possible point
    int ghostRow = currentRow;
    while (validMove(ghostPiece, ghostRow + 1, currentCol)) {
      ghostRow++;
    }
    // draw the ghost piece using a different color or transparency
    fill(255, 255, 255, 100);
    for (int i = 0; i < 4; i++) {
      int row = ghostRow + ghostPiece[i][1];
      int col = currentCol + ghostPiece[i][0];
      rect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }
  }

  // lock the current falling piece in place and create a new one
  public void lockPiece() {
    for (int i = 0; i < 4; i++) {
      int row = currentRow + currentPiece[i][1];
      int col = currentCol + currentPiece[i][0];
      board[row][col] = currentType;
    }
    // clear full rows and move cells down
    for (int i = ROWS - 1; i >= 0; i--) {
      boolean isFullRow = true;
      for (int j = 0; j < COLS; j++) {
        if (board[i][j] == 0) {
          isFullRow = false;
          break;
        }
      }
      if (isFullRow) {
        // clear the full row
        for (int j = 0; j < COLS; j++) {
          board[i][j] = 0;
        }
        // move cells down
        for (int k = i - 1; k >= 0; k--) {
          for (int j = 0; j < COLS; j++) {
            board[k + 1][j] = board[k][j];
          }
        }
        // don't move to the next row yet, since there might be another full row
        i++;
      }
    }
    newPiece();
  }  

  // create a new falling piece
  public void newPiece() {
    currentType = (int) random(0, tetrominoes.length);
    currentPiece = tetrominoes[currentType];
    currentRow = 0;
    currentCol = COLS / 2 - 1;
    if (!validMove(currentPiece, currentRow, currentCol)) {
      gameOver();
    }
  }

  // move the falling piece down automatically
  public void movePiece() {
    // if (frameCount % moveInterval != 0) return;
    if (validMove(currentPiece, currentRow + 1, currentCol)) {
      currentRow++;
    } else {
      lockPiece();
    }
  }

  public void movePiece(int dcol, int drow) {
    // if (frameCount % moveInterval != 0) return;
    if (validMove(currentPiece, currentRow + drow, currentCol + dcol)) {
      currentRow += drow;
      currentCol += dcol;
    } else if (drow > 0) {
      lockPiece();
    }
    }

  // game over
  public void gameOver() {
    background(255, 0, 0);
    textSize(32);
    textAlign(CENTER);
    text("Game Over", width/2, height/2);
    noLoop();
  }
}
