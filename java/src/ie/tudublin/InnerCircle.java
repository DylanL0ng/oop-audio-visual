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

    void render(int hue)
    {
        p.pushStyle();
        // p.circle(pos.x, pos.y, 50);
        // p.fill(0, 0, 0);
        
        // p.noFill();
        // p.stroke(hue, 360, 360);
        // p.strokeWeight(p.random(1, 2));
        p.circle(pos.x, pos.y, 50);

        p.popStyle();
    }
}
