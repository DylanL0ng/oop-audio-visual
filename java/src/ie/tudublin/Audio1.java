package ie.tudublin;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

public class Audio1 extends PApplet {

    Minim minim;

    // All of these classes require imports from the minim library.
    AudioInput ai; // Reads from the microphone
    AudioPlayer ap; // Plays audio
    AudioBuffer ab; // A buffer containing all of the audio samples.

    public void settings() {
        size(1024, 500);
    }

    int frameSize = 1024;

    public void setup() {
        colorMode(HSB);
        background(0);

        minim = new Minim(this); // 'this' is a reference to the current applet. Equivalent to 'self' in Python.

        /* Configures input from the microphone. First argument is the channel width. Second is the buffer size.
         * Third argument is the sample rate, 44100 Hz (CD Quality). Last is the bit size, the number of bits in each sample. */
        ai = minim.getLineIn(Minim.MONO, frameSize, 44100, 16); 
        ab = ai.mix; // Audio buffer will contain samples from a mix of the left and right channels of the microphone.

        smooth();

    }

    public void draw() {
        background(0);
        stroke(255);

        float half = height / 2; // Makes sure the line will appear in the middle of the screen
        float cgap = 255 / (float) ab.size();

        float total = 0;
        for (int i = 0; i < ab.size(); i++) {
            total += abs(ab.get(i)); // If the absolute value is not used, then a loud noise will average out to be zero
            stroke(cgap * i, 255, 255);
            /* ab.get(i) is the same as ab[i] for a normal array. multiplying this by half makes sure the spectrum covers the full screen. */ 
            line(i, half, i, half + ab.get(i) * half); 
        }
        float average = total / (float) ab.size();

        float r = average * 200;
        
        /*  Linear interpolation. This function brings lerpedR 10% closer to R in every frame. 
        This means we go 10% closer to the value read by the microphone instead of directly to it. */
        lerpedR = lerp(lerpedR, r, 0.1f);

        circle(100, 200, lerpedR); // Displays the loudness of the sound.
    }

    float lerpedR = 0;
}
