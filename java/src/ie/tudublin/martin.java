package ie.tudublin;


import processing.core.PApplet;
import ddf.minim.AudioBuffer;


public class martin extends Visual
{
    public martin(AudioBuffer ab, PApplet p)
    {
        this.ab = ab; 
        this.p = p;
    }
    
    AudioBuffer ab;
    PApplet p;

    float lerpedBuffer[] = new float[1024];
    float sum; 
    float average;


    float rotationX, rotationY;
    int colorIndex;

    public void render() 
    {
        p.background(0);

        for(int i = 0 ; i < ab.size() ; i ++)
        {
            sum += PApplet.abs(ab.get(i));
            lerpedBuffer[i] = PApplet.lerp(lerpedBuffer[i], ab.get(i), 0.01f);
        }
        average = sum / (float) ab.size();

        test();

    }

    public void test()
    {
        p.strokeWeight(2);
        p.translate(p.width / 2, p.height / 2);
        p.rotateX(rotationX);
        p.rotateY(rotationY);

        // Get the maximum amplitude of the audio buffer
        float maxAmplitude = 0;
        for (int i = 0; i < ab.size(); i++) {
            float amplitude = PApplet.abs(ab.get(i));
            if (amplitude > maxAmplitude) {
                maxAmplitude = amplitude;
            }
        }

        // Set the color based on the amplitude
        int[] colors = {0xffFF4136, 0xff0074D9, 0xff2ECC40, 0xffFFDC00};
        p.stroke(colors[colorIndex]);
        if (maxAmplitude > 0.5) {
            colorIndex = (colorIndex + 1) % colors.length;
        }

        // Draw a different 3D shape based on the amplitude
        float size = maxAmplitude * 300;
        if (maxAmplitude < 0.1) {
            p.sphere(size);
        } else{
            p.box(size);
        }
 

        // Rotate the shapes based on the audio buffer's data
        rotationX += lerpedBuffer[0] * 0.1;
        rotationY += lerpedBuffer[ab.size() - 1] * 0.1;
    }
}