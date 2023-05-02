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
        // Seems to be 0
        // System.out.println(amplitude);
        p.circle(pos.x, pos.y, amplitude);

        p.popStyle();
    }
}
