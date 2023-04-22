package ie.tudublin;

import ddf.minim.AudioBuffer;
import ddf.minim.analysis.FFT;

public class LifeBoard {
    boolean[][] board;
    boolean[][] next;
    private int size;
    AudioBuffer ab;
    AudioVisual p;
    float cellWidth;
    boolean running = true;
    FFT fft;

    /* The constructor is passed the size of the board, the audio buffer and the AudioVisual instance p.
     * Passing the audio buffer allows this class to analyse the audio buffer within its methods.
     * The AudioVisual class is a subclass of Visual, which extends PApplet. This allows us to use 
     * AudioVisual p to use methods from the PApplet class, and draw on the canvas. */
    public LifeBoard(int size, AudioBuffer ab, AudioVisual p) {
        this.size = size;
        board = new boolean[size][size];
        next = new boolean[size][size];
        this.ab = ab;
        this.p = p;
        cellWidth = p.width / (float) size;
        this.fft = new FFT(p.getFrameSize(), 44100);
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

    public void randomise(int row, int col) {
        int shapeSelect = (int)p.random(0, 2);

        switch (shapeSelect) {
            case 0: {
                surroundingCells(row, col);
                break;
            }

            case 1: {
                drawCross(row, col);
                break;
            }

            case 2: {
                drawX(row, col);
                break;
            }
        }
    }

    public void surroundingCells(int row, int col) {
        // This method sets all cells surrounding a particular cell to be alive, inclusive.
        if (!running) {
            pause();
        }

        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                board[row + i][col + j] = true;
            } // end for
        } // end for
    }

    public void render() {
        // This calculation ensures the colour spectrum correctly spans the size of the board.
        float colour = 255 / (float) size;
        float freq;

        // If the simulation is paused, the render() method won't run.
        if (!running) {
            return;
        } else if (detectVolume()) {
            /* If a sharp change in volume is detected, this code runs. 
             * detectVolume() is further explained at its definition. */

            // Gets the frequency of the note currently playing.
            freq = getCurrentFrequency();
            // Draws on the board, based on the frequency found.
            drawFrequency(freq); 
        }

        p.background(0);
        p.noStroke(); // I think it looks cooler when the cells blend into one another, with no border.
        applyRules();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                float x = col * cellWidth;
                float y = row * cellWidth;

                if (board[row][col]) {
                    p.fill(colour * col, 255, 255);
                } else {
                    p.fill(0);
                }
                p.rect(x, y, cellWidth, cellWidth);
            }
        }
    }

    public boolean detectVolume() {
        float sum = 0;
        for (int i = 0; i < ab.size(); i++) {
            sum += AudioVisual.abs(ab.get(i));
        }

        // The average amplitude of the audio buffer.
        float average = sum / ab.size();
        /* A threshold value based on the average amplitude. A lower multiplication value makes the detection more sensitive. 
         * I found 3.75 to be a visually appealing sweetspot. */
        float threshold = (float) (3.75 * average);

        for (int i = 0; i < ab.size(); i++) {
            // If the current note is above the threshold, the function returns true.
            if (AudioVisual.abs(ab.get(i)) > threshold) {
                return true;   
            }
        }

        return false;
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

    public void drawCross(int row, int col) {
        int crossSize = (int)p.random(1, 10);
        
        /* The Math.max and Math.min values are both used here. This is to ensure that the method 
         * does not attempt to change values which are out of bounds. 
         * The loop starts at whichever value is higher between row - crossSize and 0, 
         * and ends at whichever is smaller between row + crossSize and size - 1.
         */
        for (int i = Math.max(row - crossSize, 0); i <= Math.min(row + crossSize, size - 1); i++) {
            board[i][col] = true;
        }
        
        for (int j = Math.max(col - crossSize, 0); j <= Math.min(col + crossSize, size - 1); j++) {
            board[row][j] = true;
        }
    }

    public void drawX(int row, int col) {
        int Xsize = (int)p.random(1, 10);

        // Draw top-left to bottom-right diagonal line
        for (int i = 0; i < Xsize; i++) {
            int r = row + i;
            int c = col + i;

            // The shape is only drawn if the points found are within the bounds of the array.
            if (r < p.height && c < p.width) {
                board[r][c] = true;
            }
        }
        
        // Draw top-right to bottom-left diagonal line
        for (int i = 0; i < Xsize; i++) {
            int r = row + i;
            int c = col - i;

            // The shape is only drawn if the points found are within the bounds of the array.
            if (r < p.height && c >= 0) {
                board[r][c] = true;
            }
        }
    }
    

    public void setMouse() {
        int col = (int) (p.mouseX / cellWidth);
        int row = (int) (p.mouseY / cellWidth);

        /* If the mouse is within the bounds of the board, the corresponding element is
         * set to be alive. */
        if (row >= 0 && row < size && col >= 0 && col < size) {
            board[row][col] = true;
        }
    }

    public float getCurrentFrequency() {
        /* This function goes through the frequencies within the audio buffer,
         * and returns the highest frequency currently playing. */
        int highestIndex = 0;
        fft.forward(ab);

        for (int i = 0; i < fft.specSize() / 5; i++) {
            if (fft.getBand(i) > fft.getBand(highestIndex)) {
                highestIndex = i;
            }
        }

        return fft.indexToFreq(highestIndex);
    }

    public void drawFrequency(float freq) {
        float newFrequency = freq / 10; // Divided for simplicity purposes
        int halfSize = size / 2;
        int offset = 30;

        int rand1, rand2;

        // Less than or equal to 300Hz
        if (newFrequency <= 30) {
            /* When the song is playing at a low frequency, shapes are generated around the outside of the board.
             * The loop runs until coordinates are generated that are within the outside x cells of the board,
             * on all axes. The offset of these cells is stored within the variable 'offset'. */
            do {
                rand1 = (int) p.random(5, size - 5);
                rand2 = (int) p.random(5, size - 5);
            } while ((!((rand1 >= 0 && rand1 <= offset) || (rand1 <= size - offset && rand1 >= size))) &&
                     (!((rand2 >= 0 && rand2 <= offset) || (rand2 <= size - offset && rand2 >= size))));

            randomise(rand1, rand2);
        }

        // Between 301 and 600 Hz
        if (newFrequency > 30 && newFrequency <= 60) {
            /* When the song is playing at a medium frequency, shapes are generated between the outside cells 
             * and the middle of the board on all axes. The loop runs until the coordinates generated 
             * satisfy these criteria. */
            do {
                rand1 = (int) p.random(offset, size - offset);
                rand2 = (int) p.random(offset, size - offset);
            } while ((!(rand1 > offset && rand1 < halfSize - offset) || (rand1 < halfSize + offset && rand1 > size - offset)) &&
                     (!(rand2 > offset && rand2 < halfSize - offset) || (rand2 < halfSize + offset && rand2 > size - offset)));

            randomise(rand1, rand2);
        }

        /* Over 600 Hz is counted as high frequency. 
         * Usually in music theory, 'high' frequency is considered to be 2kHz or over,
         * but in this song, most higher instruments lie around 900-1500 Hz. */
        if (newFrequency > 60) {
            /* When the song is playing at a high frequency, shapes are generated within the middle of the board,
             * plus or minus 'offset' cells. The loop runs until the coordinates generated meet these criteria. */
            do {
                rand1 = (int) p.random(halfSize - offset, halfSize + offset);
                rand2 = (int) p.random(halfSize - offset, halfSize + offset);
            } while (!(rand1 >= halfSize - offset && rand1 <= halfSize + offset) && !(rand2 >= halfSize - offset && rand1 <= halfSize + offset));

            randomise(rand1, rand2);
        }
    }
}
