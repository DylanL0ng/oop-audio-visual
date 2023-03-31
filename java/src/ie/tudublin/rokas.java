package ie.tudublin;

import processing.core.PApplet;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;



public class rokas extends PApplet {

    Minim minim;

    AudioInput ai;
    AudioPlayer ap;
    AudioBuffer ab;

    int frameSize = 800;
    float cx = frameSize / 2;
    float cy = frameSize / 2;


    public void settings() 
    {
        size(800, 800);
    }

    public void setup() 
    {   
        colorMode(HSB);

        minim = new Minim(this);
        
        // ai = minim.getLineIn(Minim.MONO, frameSize, 44100, 16); 
        // ab = ai.mix;

        ap = minim.loadFile("aria_math.mp3", 1024);
        ap.play();
        ab = ap.mix;

        smooth();
    }

    float lerpedBuffer[] = new float[1024];
    float sum;
    float average;

    public void draw() 
    {
        background(0);

        for(int i = 0 ; i < ab.size() ; i ++)
        {
            sum += abs(ab.get(i));
            lerpedBuffer[i] = lerp(lerpedBuffer[i], ab.get(i), 0.1f);
        }
        average = sum / (float) ab.size();


        background(0);
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
        int size = 10;

        // rounding off inputs so they 'snap' to grid
        int sqCount = Math.round(count / 2.0f) * 2;
        int xpos = Math.round(pos / (float)size) * size;

        // loop for creating line of squares
        for(int i = 1 ; i <= sqCount ; i++)
        {
            // square y position offset for towers
            float ypos = (cy - (size * i) + ((sqCount + 1) * (size / 2)));

            rectMode(CENTER);
            noStroke();

            // gradient effect
            if(i <= sqCount / 2)
            {
                // halfway or below
                fill(c, 255, 20 * i);
            }
            else
            {
                // halfway and up 
                fill(c, 255, 255 - (10 * i));
            }
        
            // draw square
            rect(xpos, ypos, size, size);

        } // end for

    } // end drawSqTower()
}
