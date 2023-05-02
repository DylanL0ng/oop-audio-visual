package ie.tudublin;

import processing.core.PApplet;
import processing.core.PVector;

public class InnerCircle {
    
    PApplet p;
    PVector pos;

    /*
     * The constructor is passed the PApplet class (p)
     * and a PVector class (pos)
     * 
     * Then they are then assigned to the classes variables.
    */
    InnerCircle(PApplet p, PVector pos)
    {
        this.p = p;
        this.pos = pos;
    } // end InnerCircle()

    /*
     * The function render is passed an int (hue) and a
     * float (amplitude)
     * 
     * hue controls the hue colour of the shapes.
     * amplitude represents the current music amplitude
     * volume on average.
     * 
     * render is called each frame.
    */
    void render(int hue, float amplitude)
    {
        /*
         * Push the style onto the stack
         * so that the BackgroundFiller won't be affected by
         * the style changes (fill).
        */
        p.pushStyle();
        
        /*
        * Using map to set the brightness based on the 
        * amplitude levels, but give the circle a minimum
        * brightness of 100, so that it doesn't fully disapear.
        */ 
        float brightness = PApplet.map(360 * amplitude, 0, 360, 100, 360);
        p.fill(100, 360, brightness);

        /*
         * Using map to set the width of the circle
         * based on the amplitude levels, but give the circle
         * a minimum width of 100, so that we can see the circle
         * when no or low music is playing.
         * 
         * Additionally amplitude needs to be multiplied to increase
         * the intensity of the changes.
        */

        amplitude = amplitude * 1000f;
        float circle_width = PApplet.map(amplitude, 0, 1024, 100, 400);
        p.circle(pos.x, pos.y, circle_width);

        
        /*
         * Popping the style remove the style from the stack,
         * basically it will tell the program that we want to
         * keep the changes made in between pop and push as their own
         * seperate things.
        */
        p.popStyle();
    } // render()
}
