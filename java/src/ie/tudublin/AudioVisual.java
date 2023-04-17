package ie.tudublin;

import ddf.minim.*;
import processing.core.PApplet;

public class AudioVisual extends PApplet {

    Audio1 example;
    Minim minim;

    // All of these classes require imports from the minim library.
    AudioPlayer ap; // Plays audio
    AudioBuffer ab; // A buffer containing all of the audio samples.

    public void settings() {
        size(1024, 800);
    }

    public void setup() {

        int frameSize = 1024;
        
        colorMode(HSB);
        background(0);
        
        minim = new Minim(this); // 'this' is a reference to the current applet. Equivalent to 'self' in Python.
        ap = minim.loadFile("java/data/aria_math.mp3", frameSize);
        ab = ap.mix; // Audio buffer will contain samples from a mix of the left and right channels
        // of the file.
        
        smooth();
        /*
         * Use this to make a new instance of your written class.
         * e.g rokas = new Rokas(x, y, z, this)
         */

        example = new Audio1(ab, this);

        ap.play();

    }

    public void keyPressed() {
        if (key == ' ') {
            if (ap.isPlaying()) {
                ap.pause();
            } else {
                ap.play();
            }
        }
    }

    public void draw() {

        /*
         * Make a method such as render() in your own class, which can be called from
         * this method.
         * We can later use timing within this method to call our own render() methods
         * at different points in the song.
         * 
         * rokas.render();
         */

        example.render();
    }
}
