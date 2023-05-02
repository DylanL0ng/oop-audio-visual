package ie.tudublin;

import ddf.minim.AudioPlayer;
import processing.core.PApplet;
import processing.core.PVector;

public class BackgroundFiller {
    PApplet p;
    AudioPlayer ap;

    PVector pos;

    float offset_1;
    float offset_2;
    
    BackgroundFiller(PApplet p, PVector pos, AudioPlayer ap)
    {
        this.p = p;
        this.pos = pos;
        this.ap = ap;
    }

    public void render(int hue, int abIdx)
    {
        float angle = PApplet.sin(abIdx+offset_1)* 3; 
        float angle2 = PApplet.sin(abIdx+offset_2)* 300; 
    
        float degree = PApplet.radians(abIdx);

        float x = PApplet.sin(degree)*(angle2+30); 
        float y = PApplet.cos(degree)*(angle2+30);
    
        float x2 = PApplet.sin(degree)*(500/angle); 
        float y2 = PApplet.cos(degree)*(500/angle);

        p.pushStyle();
        p.pushMatrix();
        
        p.translate(this.pos.x, this.pos.y);
        
        p.fill (hue, 360, 360);
        
        float leftChannel = ap.left.get(abIdx) * 10;
        float rightChannel = ap.right.get(abIdx) * 10;

        p.rect(x2, y2, leftChannel * 2, leftChannel);

        p.rect(x, y, rightChannel, leftChannel);
        
        p.rect(x2, y2, rightChannel, rightChannel * 2);
        
        p.pushStyle();
        p.popMatrix();

        offset_1 += 0.002;
        offset_2 += 0.004;
    }

}
