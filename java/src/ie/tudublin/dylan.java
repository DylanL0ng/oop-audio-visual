package ie.tudublin;

import processing.core.PApplet;
import processing.core.PVector;
import ddf.minim.*;
import ddf.minim.analysis.*;

public class dylan extends Visual {

    AudioBuffer ab;
    AudioPlayer ap;
    PApplet p;
    FFT fft;

    InnerCircle innerCircle;
    BackgroundFiller backgroundFiller;

    float amplitude = 0.0f;

    /*
     * The constructor is passed the AudioBuffer class (ab),
     * an AudioPlayer class (ap), and a PApplet class (p)
     * 
     * Then they are assigned to the classes variables,
     * fft is fetched from the super class, and InnerCircle and
     * BackgroundFiller are both initialised and assigned.
     */
    dylan (AudioBuffer ab, AudioPlayer ap, PApplet p) {
        this.ab = ab;
        this.ap = ap;
        this.p = p;

        fft = getFFT();
        innerCircle = new InnerCircle(p, new PVector(1024/2, 800/2));
        backgroundFiller = new BackgroundFiller(p, new PVector(1024/2, 800/2), ap);
    } // end dylan()

    /*
     * The function render is called each frame and
     * takes no parameters.
     * 
     * It acts as the main source of rendering for my
     * portion of the assignment
    */
    public void render() 
    {
        // Reset the background each frame
        p.background(0);

        // Loop through the AudioBuffer
        for (int i = 0; i < ab.size(); i++)
        {
            /*
            * Calculate the hue using the current
            * index and ensuring it doesnt go over
            * 360 using modulo
            */ 

            int hue = i % 360;

            // Calculate the average amplitude
            calculateAverageAmplitude();

            // Render both screen elements
            innerCircle.render(hue, amplitude);
            backgroundFiller.render(hue, i);

        } // end for
    } // end render()

    /*
     * The AudioVisual calculateAverageAmplitude() function
     * seems to be bugged, it produces an error when I try
     * to use it's function so I'm overriding it with a
     * working version 
     */
	public void calculateAverageAmplitude()
	{
        float sum = 0.0f;

        for (int i = 0; i < ab.size(); i++)
            sum += AudioVisual.abs(ab.get(i));

        // The average amplitude of the audio buffer.
        float average = sum / ab.size();
        
        amplitude = average;
	} // end calculateAverageAmplitude()

}
