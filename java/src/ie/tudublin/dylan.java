package ie.tudublin;

import processing.core.PApplet;
import ddf.minim.AudioBuffer;

public class dylan extends Visual {
    AudioBuffer ab;
    PApplet p;

    dylan (AudioBuffer ab, PApplet p) {
        this.ab = ab;
        this.p = p;
    }

    public void render() 
    {
        p.background(0);
    }
}
