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

    float cx = frameSize / 2;
    float cy = frameSize / 2;

    float lerpedBuffer[] = new float[1024];
    float sum;
    float average;


    public void render() 
    {

        p.background(0);

        for(int i = 0 ; i < ab.size() ; i ++)
        {
            sum += PApplet.abs(ab.get(i));
            lerpedBuffer[i] = PApplet.lerp(lerpedBuffer[i], ab.get(i), 0.1f);
        }
        average = sum / (float) ab.size();


        p.background(0);
        for(int i = 0 ; i < ab.size() ; i ++)
        {
            float c = map(i, 0, ab.size(), 0, 255);
            float f = lerpedBuffer[i] * cy * 5f;
            drawSqTower(f, i, c);       
        }
    }

    public void drawSqTower(float count, int pos, float c)
    {
        // square size adjustment
        int size = 8;

        // rounding off inputs so they 'snap' to grid
        int sqCount = Math.round(count / 2.0f) * 2;
        int xpos = Math.round(pos / (float)size) * size;

        // loop for creating line of squares
        for(int i = 1 ; i <= sqCount ; i++)
        {
            // square y position offset for towers
            float ypos = (cy - (size * i) + ((sqCount + 1) * (size / 2)));

            p.rectMode(CENTER);
            p.noStroke();

            // gradient effect
            if(i <= sqCount / 2)
            {
                // halfway or below
                p.fill(c, 255, 20 * i);
            }
            else
            {
                // halfway and up 
                p.fill(c, 255, 255 - (10 * i));

            } // end if
        
            // draw square
            p.rect(xpos, ypos, size, size);

        } // end for

    } // end drawSqTower()
}
