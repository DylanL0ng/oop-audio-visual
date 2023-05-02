package ie.tudublin;

import ddf.minim.AudioPlayer;
import processing.core.PApplet;
import processing.core.PVector;

public class BackgroundFiller {
    PApplet p;
    AudioPlayer ap;

    PVector pos;

    float n4;
    float n6;
    
    BackgroundFiller(PApplet p, PVector pos, AudioPlayer ap)
    {
        this.p = p;
        this.pos = pos;
        this.ap = ap;
    }

    public void updateMovement(PVector pos)
    {
        this.pos = pos;
    }

    public void render(int hue, int abIdx)
    {
        float angle = p.sin(abIdx+n4)* 10; 
        float angle2 = p.sin(abIdx+n6)* 300; 
    
        float x = p.sin(p.radians(abIdx))*(angle2+30); 
        float y = p.cos(p.radians(abIdx))*(angle2+30);
    
        float x2 = p.sin(p.radians(abIdx))*(500/angle); 
        float y2 = p.cos(p.radians(abIdx))*(500/angle);

        p.pushStyle();
        p.pushMatrix();
        
        p.translate(this.pos.x, this.pos.y);
        
        p.fill (hue, 360, 360); //wt
        p.rect(x2, y2, ap.left.get(abIdx)*20, ap.left.get(abIdx)*10);
        
        p.fill (hue, 360, 360); //orange
        p.rect(x, y, ap.right.get(abIdx)*10, ap.left.get(abIdx)*10);
        
        
        p.fill(hue, 360, 360); //wt
        p.rect(x2, y2, ap.right.get(abIdx)*10, ap.right.get(abIdx)*20);
        
        p.pushStyle();
        p.popMatrix();

        n4 += 0.002;
        n6 += 0.004;
    }

}
