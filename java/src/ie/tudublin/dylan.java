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

    dylan (AudioBuffer ab, AudioPlayer ap, PApplet p) {
        this.ab = ab;
        this.ap = ap;
        this.p = p;

        fft = getFFT();
        innerCircle = new InnerCircle(p, new PVector(1024/2, 800/2));
        backgroundFiller = new BackgroundFiller(p, new PVector(1024/2, 800/2), ap);
    }

    public void render() 
    {
        p.background(0);

        for (int i = 0; i < ab.size(); i++)
        {
            int hue = i % 360;

            calculateAverageAmplitude();
            innerCircle.render(hue, amplitude);
            backgroundFiller.render(hue, i);
        }
    }

	public void calculateAverageAmplitude()
	{
        float sum = 0.0f;

        for (int i = 0; i < ab.size(); i++) {
            sum += AudioVisual.abs(ab.get(i));
        }

        // The average amplitude of the audio buffer.
        float average = sum / ab.size();
        
        amplitude = average;
	}

}
