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
    float cx = width / 2;
    float cy = height / 2;


    public void settings() 
    {
        size(800, 800);
    }

    public void setup() 
    {
        colorMode(HSB);
        background(0);

        minim = new Minim(this);
        
        // ai = minim.getLineIn(Minim.MONO, frameSize, 44100, 16); 
        // ab = ai.mix;

        ap = minim.loadFile("aria_math.mp3", 1024);
        ap.play();
        ab = ap.mix;


        smooth();

        
    }

    public void draw() 
    {

    }
}
