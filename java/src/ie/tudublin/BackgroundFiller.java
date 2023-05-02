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
    
    /*
     * The constructor is passed the PApplet class (p),
     * a PVector class (pos), and a AudioPlayer class (ap).
     * 
     * Then they are assigned to the classes variables.
     */
    BackgroundFiller(PApplet p, PVector pos, AudioPlayer ap)
    {
        this.p = p;
        this.pos = pos;
        this.ap = ap;
    } // end BackgroundFiller()

    /*
     * The function render is passed an int hue and
     * an int abIdx. 
     * 
     * hue controls the hue colour of the shapes.
     * abIdx passes the current Audio Buffer index so
     * that we can get information from the audio buffer.
     * 
     * render is called each frame.
     */
    public void render(int hue, int abIdx)
    {
        /*
         * Calculate the angle that the particles are 
         * going to be sent into, offsets will add a
         * gradual rotation to the angles over time
         * which will give it a cool shape almost like
         * an atom.
         */

        float angle = PApplet.sin(abIdx+offset_1)* 3; 
        float angle2 = PApplet.sin(abIdx+offset_2)* 300; 
    
        float degree = PApplet.radians(abIdx);

        float pos_offset_1 = angle2 + 30;
        float pos_offset_2 = 500 / angle;

        float degree_sin = PApplet.sin(degree); 
        float degree_cos = PApplet.cos(degree); 

        float x = degree_sin * pos_offset_1; 
        float y = degree_cos * pos_offset_1;
    
        float x2 = degree_sin * pos_offset_2; 
        float y2 = degree_cos * pos_offset_2;

        /*
         * Push the style and matrix onto the stack
         * so that the Circle won't be affected by
         * the style changes (fill) and matrix
         * changes (translate)
        */

        p.pushStyle();
        p.pushMatrix();
        
        // Positions where the initialised PVector is.
        p.translate(this.pos.x, this.pos.y);
        
        /*
         * Sets the fill to the passed through hue.
         * This will give a random like effect
         */
        p.fill (hue, 360, 360);
        
        /*
         * Gets the left and right channel audio buffer
         * seperately and multiplying them by 10 helps
         * make them more visible.
         */
        float leftChannel = ap.left.get(abIdx) * 10;
        float rightChannel = ap.right.get(abIdx) * 10;

        // Spawn the particles on the screen
        p.rect(x2, y2, leftChannel * 2, leftChannel);
        p.rect(x, y, rightChannel, leftChannel);
        p.rect(x2, y2, rightChannel, rightChannel * 2);
        
        /*
         * Popping the style and matrix will remove
         * the style and matrix from the stack, basically
         * it will tell the program that we want to keep the
         * changes made in between pop and push as their own
         * seperate things.
         */
        p.popStyle();
        p.popMatrix();

        /*
         * After messing around with the params I found that
         * a very low offset like the following helps get a
         * really cool pattern as the scene increases
        */
        offset_1 += 0.002;
        offset_2 += 0.004;
    } // end render()

}
