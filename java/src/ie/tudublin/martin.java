package ie.tudublin;

import processing.core.PApplet;
import processing.core.PVector;
import ddf.minim.AudioBuffer;

import static processing.core.PConstants.CORNER;

import java.util.ArrayList;

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
    ArrayList<Shape> shapes;

    //Different music zones 
    float Low = (float) 0.03;  
    float Mid = (float) 0.125; 
    float High = (float) 0.20;

    //calculate sum of audio buffer for each music zone 
    float[] sums = new float[3];
    static float[] averages = new float[3];

    public void render() 
    {
        p.pushStyle();
        p.background(0);
        sum = 0;
    
        //lerpedbuffer for 'smoothness'
        for (int i = 0; i < ab.size(); i++) 
        {
            sum += PApplet.abs(ab.get(i));
            lerpedBuffer[i] = PApplet.lerp(lerpedBuffer[i], ab.get(i), 0.01f);
        }
        average = sum / (float) ab.size();

        //calculate sums and average for each music zone
        for (int i = 0; i < ab.size(); i++) 
        {
            int zone = getFrequencyZone(i);
            sums[zone] += PApplet.abs(ab.get(i));
            lerpedBuffer[i] = PApplet.lerp(lerpedBuffer[i], ab.get(i), 0.01f);
        }

        for (int i = 0; i < 3; i++)
        {
            averages[i] = sums[i] / (float) ab.size();
        }

        //if shapes null - create them
        if (shapes == null) 
        {
            //shapes in array list
            shapes = new ArrayList<>();
            //creates 5 cube, 5 lines, random position
            for (int i = 0; i < 15; i++) 
            {
                shapes.add(new CubeShape(p, new PVector(p.random(p.width), p.random(p.height), p.random(-500, 500))));
            }//end for

            //add audio waveforms for each side of the screen
            shapes.add(new AudioSpectrum(p, 0, ab)); //top
            shapes.add(new AudioSpectrum(p, 1, ab)); //bottom
            shapes.add(new AudioSpectrum(p, 2, ab)); //left
            shapes.add(new AudioSpectrum(p, 3, ab )); //right
        }
    
        //calls update and display with selected music zone
        for (Shape shape : shapes) 
        {
            shape.update(average);
            shape.display(average); 
        }//end for

        p.pushStyle();
    }//end render

    //determine music zone for given index
    private int getFrequencyZone(int index)
    {
        float frequency = PApplet.map(index, 0, ab.size(), 20, 20000);
        if (frequency < 500) return 0; //Bass
        if (frequency < 5000) return 1; //Mid
        return 2; //Treble
    }//end getFrequencyZone
    
}//end martin

//template for different kinds of shapes
abstract class Shape 
{
    PApplet p;
    //average to modify shape
    float average;

    Shape(PApplet p) 
    {
        this.p = p;
    }

    //to update position
    abstract void update(float average);
    //drawing shape
    abstract void display(float average);
}//end Shape

class CubeShape extends Shape 
{
    //position and speed of cube in 3D space - PVector objects
    PVector pos, speed;
    float size, initialSize;

    //cube rotation
    float rotation = 0;
    float bassAverage;

    //random rotation speed for each cube
    float rotationSpeed = p.random(0.001f, 0.01f);

    //constructor for CubeShape
    CubeShape(PApplet p, PVector pos) 
    {
        super(p);
        this.pos = pos;
        this.speed = new PVector(p.random(-1, 1), p.random(-1, 1), p.random(1, 2)); //positive Z speed
        this.initialSize = p.random(5, 15); //random initial size
        this.size = initialSize;
    }

    // to update cube - position, size and rotation based on music 
    void update(float average) 
    {
        bassAverage = martin.averages[0]; // accessing bass average
        pos.add(speed); // update cube position based on its speed vector
        //checks if cube gone outside screen or passed the camera, then despawn 
        if (pos.x < 0 || pos.x > 800 || pos.y < 0 || pos.y > 1024 || pos.z > 250) 
        {
            pos.set(new PVector(p.random(p.width), p.random(p.height), p.random(-2000, -1500))); //reset position far back in Z-axis
            speed.set(new PVector(p.random(-1, 1), p.random(-1, 1), p.random(1, 2))); //reset speed
        }
        speed.z = PApplet.map(bassAverage / 2, 0, 1, 1, 2); //maps bass average to Z-axis speed
        rotation += rotationSpeed + PApplet.map(average, 0, 1, 0.001f, 0.01f); //controls the rotation with music and random speed
        size = initialSize + PApplet.map(pos.z, -2000, 500, 0, 200) + average * 30; //update size - distance from camera in Z axis and music
    }//end update


    void display(float average) 
    {
        this.average = average;
        p.pushStyle();
        p.pushMatrix(); //save current transformation matrix
        p.translate(pos.x, pos.y, pos.z); //translate to current pos
        //Cube rotation - apply rotation on all axis
        p.rotateX(rotation);
        p.rotateY(rotation);
        p.rotateZ(rotation);
        //calculate opacity based on distance from z axis
        float opacity = PApplet.map(pos.z, -2000, 0, 0, 255);
        //set stroke weight and colors based on music intensity 
        p.strokeWeight(average * 40);
        //lerped colour - between two different colours depending on music
        p.stroke(p.lerpColor(p.color(240, 100, 100, opacity), p.color(300, 100, 100, opacity), average * 10)); 
        p.fill(p.lerpColor(p.color(120, 100, 100, opacity), p.color(180, 100, 100, opacity), average * 10));
        p.box(size + average * 100);
        p.popMatrix();
        p.popStyle();
    }//end display
}//end CubeShape

//visualisation of pixel audio spectrum
class AudioSpectrum extends Shape 
{
    //sides of screen to display
    int side;
    AudioBuffer ab;
    float[] lerpedBuffer;
    int numBars = 64; //number of bars in audio spectrum
    float barWidth, barHeight, barGap, barX, barY; //dimension and position of bars

    //constructor 
    AudioSpectrum(PApplet p, int side, AudioBuffer ab) 
    {
        super(p);
        this.side = side;
        this.ab = ab;
        this.lerpedBuffer = new float[ab.size()];
        this.barWidth = p.width / numBars; //calculate width of each bar based on number of bars
        this.barGap = 1; 
    }

    void update(float average) 
    {
        // Update the lerped buffer
        for (int i = 0; i < ab.size(); i++) 
        {
            lerpedBuffer[i] = PApplet.lerp(lerpedBuffer[i], ab.get(i), 0.1f);
        }//end for
    }//end update

    void display(float average) 
    {
        //set fill and rectangles 
        p.noStroke();
        p.rectMode(CORNER);

        //determine bar dimensions and position
        switch (side) 
        {
            case 0: //top
                barX = 0;
                barY = 0;
                barHeight = p.height / 2;
                break;
            case 1: //bottom
                barX = 0;
                barY = p.height / 2;
                barHeight = p.height / 2;
                break;
            case 2: //left
                barX = 0;
                barY = 0;
                barHeight = p.height;
                break;
            case 3: //right
                barX = p.width;
                barY = 0;
                barHeight = p.height;
                break;
        }

        // loop through and draw bars
        for (int i = 0; i < numBars; i++) 
        {
            //calculate range of samples to include in bar
            float sum = 0;
            int start = PApplet.floor(PApplet.map(i, 0, numBars, 0, ab.size() - 1));
            int end = PApplet.floor(PApplet.map(i + 1, 0, numBars, 0, ab.size() - 1));

            //calculate the sum of the absolute values of the audio data within the range
            for (int j = start; j < end; j++) 
            {
                sum += PApplet.abs(lerpedBuffer[j]);
            }//end for

            //average level of bar
            float level = sum / (end - start);
            float barHeightMapped = PApplet.map(level, 0, 1, 0, barHeight);
            float barColorMapped = PApplet.map(level, 0, 1, 0, 1);
            p.fill(p.lerpColor(p.color(240, 100, 100), p.color(300, 100, 100), barColorMapped + average));           
            
            //determining position of the audio spectrum for each side
            if (side == 0) 
            {
                p.rect(i * barWidth + barGap, barY, barWidth - barGap, barHeightMapped);
            }
            else if (side == 1)
            {
                p.rect(i * barWidth + barGap, barY + barHeight - barHeightMapped, barWidth - barGap, barHeightMapped);
            } 
            else if (side == 2) 
            {
                //draw bar vertically, top of screen down
                p.rect(barX, i * barHeight / numBars + barGap, barHeightMapped, barHeight / numBars - barGap);
            }
            else if (side == 3) 
            {
                //draw bar verically, top of screen down - mirror
                p.rect(barX - barHeightMapped, i * barHeight / numBars + barGap, barHeightMapped, barHeight / numBars - barGap);
            }
        }//end for 
    }//end display
}//end AudioSpectrum

