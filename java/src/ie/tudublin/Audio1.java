package ie.tudublin;

import ddf.minim.AudioBuffer;
import processing.core.PApplet;

public class Audio1 {

    public Audio1 (AudioBuffer ab, PApplet p) {
        this.ab = ab;
        this.p = p;
    }

    PApplet p;

    AudioBuffer ab; // A buffer containing all of the audio samples.

    public void render() {
        p.background(0);
        p.stroke(255);

        float half = p.height / 2; // Makes sure the line will appear in the middle of the screen
        float cgap = 255 / (float) ab.size();

        float total = 0;
        for (int i = 0; i < ab.size(); i++) {
            total += PApplet.abs(ab.get(i)); // If the absolute value is not used, then a loud noise will average out to be zero
            p.stroke(cgap * i, 255, 255);
            /* ab.get(i) is the same as ab[i] for a normal array. multiplying this by half makes sure the spectrum covers the full screen. */ 
            p.line(i, half, i, half + ab.get(i) * half); 
        }
        float average = total / (float) ab.size();

        float r = average * 200;
        
        /*  Linear interpolation. This function brings lerpedR 10% closer to R in every frame. 
        This means we go 10% closer to the value read by the microphone instead of directly to it. */
        lerpedR = PApplet.lerp(lerpedR, r, 0.1f);

        p.circle(100, 200, lerpedR); // Displays the loudness of the sound.
    }

    float lerpedR = 0;
}
