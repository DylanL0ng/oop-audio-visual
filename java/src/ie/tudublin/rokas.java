package ie.tudublin;

import processing.core.PApplet;
import ddf.minim.AudioBuffer;


public class rokas extends Visual {

    public rokas (AudioBuffer ab, PApplet p) {
        this.ab = ab;
        this.p = p;
    }

    AudioBuffer ab;
    PApplet p;

    float cy = (frameSize / 2) - 80;
    float lerpedBuffer[] = new float[1024];


    public void render() 
    {
        p.pushStyle();
        p.background(0);
        p.stroke(0,0,0);


        for(int i = 0 ; i < ab.size() ; i ++)
        {
            lerpedBuffer[i] = PApplet.lerp(lerpedBuffer[i], ab.get(i), 0.1f);

        } // end for

        // loop for generating oldschool-esque pixelated waveform
        for(int i = 0 ; i < ab.size() ; i ++)
        {
            float c = map(i, 0, ab.size(), 0, 255);
            float f = lerpedBuffer[i] * cy * 5f;
            drawSqTower(f, i, c);     

        } // end for

        p.popStyle();
    }

    // method for generating a tower of squares based on a frequency
    public void drawSqTower(float count, int pos, float c)
    {
        // square size adjustment
        int size = 4;

        // rounding off inputs so they 'snap' to grid
        int sqCount = Math.round(count / 2.0f) * 2;
        int xpos = Math.round(pos / (float)size) * size;

        // loop for creating line of squares
        for(int i = 1 ; i <= sqCount ; i++)
        {
            // square y position offset for towers
            float ypos = (cy - (size * i)); // top
            float ypos2 = (cy + (size * i)); // bottom
            

            p.rectMode(CENTER);

            // gradient effect
            if(i <= sqCount / 2)
            {
                // inner waveform
                p.fill(c, 255, 255);
            }
            else
            {
                // outer waveform (gradient)
                p.fill(c, 255, 255 - (10 * i));

            } // end if
        
            // draw square
            p.rect(xpos, ypos, size, size);
            p.rect(xpos, ypos2 - 4, size, size);

        } // end for

    } // end drawSqTower()
}
