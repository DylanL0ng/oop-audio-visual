package ie.tudublin;

import processing.core.PApplet;
import processing.core.PVector;

public class InnerCircle {
    PApplet p;
    PVector pos;

    InnerCircle(PApplet p, PVector pos)
    {
        this.p = p;
        this.pos = pos;
    }

    void render(int hue, float amplitude)
    {
        p.pushStyle();
        
        p.fill(100, 360, PApplet.map(360 * amplitude, 0, 360, 100, 360));

        amplitude = amplitude * 1000f;
        p.circle(pos.x, pos.y, PApplet.map(amplitude, 0, 1024, 100, 400));

        p.popStyle();
    }
}
