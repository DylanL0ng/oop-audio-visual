package ie.tudublin;

import ddf.minim.AudioBuffer;

public class LifeBoard {
    boolean[][] board;
    boolean[][] next;
    private int size;
    AudioBuffer ab;
    AudioVisual p;
    float cellWidth;
    boolean running = true;

    boolean[][] creeper;

    public LifeBoard(int size, AudioBuffer ab, AudioVisual p) {
        this.size = size;
        board = new boolean[size][size];
        next = new boolean[size][size];
        this.ab = ab;
        this.p = p;
        cellWidth = p.width / (float)size;
    }

    public boolean getCell(int row, int col) {
        if (row >= 0 && row < size && col >= 0 && col < size) {
            return board[row][col];
        } else {
            return false;
        }
    }

    public int countCells(int row, int col) {
        int count = 0; 

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    if (getCell(row + i, col + j)) {
                        count++;
                    }
                }
            } // end for
        } // end for 

        return count;
    }

    public void applyRules() {

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int count = countCells(row, col);
                // < 2 > 3 dies
                // 2-3 survives
                // dead with 3 neighbours: comes to life
                if (board[row][col] == true) {
                    if (count == 2 || count == 3) {
                        next[row][col] = true;
                    } else {
                        next[row][col] = false;
                    }
                } else {
                    if (count == 3) {
                        next[row][col] = true;
                    } else {
                        next[row][col] = false;
                    }
                    
                }
            }
        }
        
        boolean[][] temp = board;
        board = next;
        next = temp;

    }

    public void randomise() {
        /* This is here to recover from the clear() method. 
         * When the clear() method is run, unless this if statement is here, the 
         * randomise key will not work. After the board has been cleared, the user can press the 
         * randomise key to unpause the game and randomise again.
         */
        if (!running) {
            pause();
        }

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                float dice = p.random(0, 1);
                board[row][col] = (dice <= 0.5f);
            } // end for 
        } // end for 
    }

    public void randomlyRandom() {
        if (!running) {
            pause();
        }
        
        /* 
         * Using values of 1 and size - 1 ensures the numbers are kept within the bounds of the array,
         * with respect to the loops which will access the indices before and after the random index.
         */
        int rand1 = (int) p.random(1, size - 1);
        int rand2 = (int) p.random(1, size - 1);

        
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                   board[rand1 + i][rand2 + j] = true;   
                }
            } // end for
        } // end for 
        
    }

    public void render() {
        // If the simulation is paused, the render() method won't run.
        if (!running) {
            return;
        } else if (p.detectBeat()) {
            randomlyRandom();
        }

        // The background is set to zero within this method so that the simulation will just pause and not be cleared.
        p.background(0);
        applyRules();
        
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                float x = col * cellWidth;
                float y = row * cellWidth;
                
                if (board[row][col]) {
                    p.fill(128, 255, 255);
                } else {
                    p.fill(0);
                }
                p.rect(x, y, cellWidth, cellWidth);
            }
        }
        setMouse();
    }
    
    public int getSize() {
        return size;
    }

    public void pause() {
        running = !running;
    }

    public void clear() {
        pause();
        p.background(0);
    }

    public void drawCross() {
        int mid = size / 2;
        
        for (int i = mid - 20; i <= mid + 20; i++) {
            board[mid][i] = true;
            board[i][mid] = true;
        }
    }

    public void setMouse() {
        int col = (int)(p.mouseX / cellWidth);
        int row = (int)(p.mouseY / cellWidth);

        // If the mouse is within the bounds of the board, the corresponding element is set to be alive.
        if (row >= 0 && row < size && col >= 0 && col < size) {
            board[row][col] = true;
        }
    }
}
