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
        p.background(0);
        sum = 0;
    
        //lerpedbuffer for 'smoothness'
        for (int i = 0; i < ab.size(); i++) 
        {
            sum += PApplet.abs(ab.get(i));
            lerpedBuffer[i] = PApplet.lerp(lerpedBuffer[i], ab.get(i), 0.01f);
        }
        average = sum / (float) ab.size();

        //Calculate sums and average for each music zone
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
            shapes = new ArrayList<>();
            //creates 5 cube, 5 lines, random position
            for (int i = 0; i < 15; i++) 
            {
                shapes.add(new CubeShape(p, new PVector(p.random(p.width), p.random(p.height), p.random(-500, 500))));
            }
            // Add audio waveforms for each side of the screen
            shapes.add(new AudioSpectrum(p, 0, ab)); // Top
            shapes.add(new AudioSpectrum(p, 1, ab)); // Bottom
            shapes.add(new AudioSpectrum(p, 2, ab)); // Left
            shapes.add(new AudioSpectrum(p, 3, ab )); // Right
        }
    
        //calls update and display with selected music zone
        for (Shape shape : shapes) 
        {
            //int zone = 0; 
            shape.update(average);
            shape.display(average); 
        }
    }

    //determine music zone for given index
    private int getFrequencyZone(int index)
    {
        float frequency = PApplet.map(index, 0, ab.size(), 20, 20000);
        if (frequency < 500) return 0; // Bass
        if (frequency < 5000) return 1; // Mid
        return 2; // Treble
    }
    
}

//maybe something minecraft related? - Different Cubes?
//Rotating Cubes based on music
//Different ways to interact with render
//Colour changes, Different implementation of shapes
//Background??

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
}

class CubeShape extends Shape 
{
    PVector pos, speed;
    float size, initialSize;
    // cube rotation
    float rotation = 0;
    float bassAverage;
    // random rotation speed for each cube
    float rotationSpeed = p.random(0.001f, 0.01f);

    CubeShape(PApplet p, PVector pos) 
    {
        super(p);
        this.pos = pos;
        this.speed = new PVector(p.random(-1, 1), p.random(-1, 1), p.random(1, 2)); // positive Z speed
        this.initialSize = p.random(5, 15); // smaller initial size
        this.size = initialSize;
    }

    void update(float average) 
    {
        bassAverage = martin.averages[0]; // accessing bass average
        pos.add(speed);
        // checks if cube gone outside screen or passed the camera, then despawn
        if (pos.x < 0 || pos.x > 800 || pos.y < 0 || pos.y > 1024 || pos.z > 250) 
        {
            pos.set(new PVector(p.random(p.width), p.random(p.height), p.random(-2000, -1500))); //reset position far back in Z-axis
            speed.set(new PVector(p.random(-1, 1), p.random(-1, 1), p.random(1, 2))); //reset speed
        }
        speed.z = PApplet.map(bassAverage / 2, 0, 1, 1, 2); // maps bass average to Z-axis speed
        rotation += rotationSpeed + PApplet.map(average, 0, 1, 0.001f, 0.01f); // controls the rotation with music and random speed
        size = initialSize + PApplet.map(pos.z, -2000, 500, 0, 200) + average * 50;
    }

    void display(float average) 
    {
        this.average = average;
        p.pushMatrix();
        p.translate(pos.x, pos.y, pos.z);
        // Cube rotation
        p.rotateX(rotation);
        p.rotateY(rotation);
        p.rotateZ(rotation);
        // Calculate alpha based on distance
        float alpha = PApplet.map(pos.z, -2000, 0, 0, 255);
        p.strokeWeight(average * 40);
        p.stroke(p.lerpColor(p.color(240, 100, 100, alpha), p.color(300, 100, 100, alpha), average * 10));
        p.fill(p.lerpColor(p.color(120, 100, 100, alpha), p.color(180, 100, 100, alpha), average * 10));
        p.box(size + average * 100);
        p.popMatrix();
    }
}


class AudioSpectrum extends Shape 
{
    int side;
    AudioBuffer ab;
    float[] lerpedBuffer;
    int numBars = 64;
    float barWidth, barHeight, barGap, barX, barY;

    AudioSpectrum(PApplet p, int side, AudioBuffer ab) 
    {
        super(p);
        this.side = side;
        this.ab = ab;
        this.lerpedBuffer = new float[ab.size()];
        this.barWidth = p.width / numBars;
        this.barGap = 1;
    }

    void update(float average) 
    {
        // Update the lerped buffer
        for (int i = 0; i < ab.size(); i++) 
        {
            lerpedBuffer[i] = PApplet.lerp(lerpedBuffer[i], ab.get(i), 0.1f);
        }
    }

    void display(float average) 
    {
        p.noStroke();
        p.rectMode(CORNER);

        switch (side) 
        {
            case 0: // Top
                barX = 0;
                barY = 0;
                barHeight = p.height / 2;
                break;
            case 1: // Bottom
                barX = 0;
                barY = p.height / 2;
                barHeight = p.height / 2;
                break;
            case 2: // Left
                barX = 0;
                barY = 0;
                barHeight = p.height;
                break;
            case 3: // Right
                barX = p.width;
                barY = 0;
                barHeight = p.height;
                break;
        }

        // Draw the bars
        for (int i = 0; i < numBars; i++) 
        {
            float sum = 0;
            int start = PApplet.floor(PApplet.map(i, 0, numBars, 0, ab.size() - 1));
            int end = PApplet.floor(PApplet.map(i + 1, 0, numBars, 0, ab.size() - 1));
            for (int j = start; j < end; j++) 
            {
                sum += PApplet.abs(lerpedBuffer[j]);
            }
            float level = sum / (end - start);
            float barHeightMapped = PApplet.map(level, 0, 1, 0, barHeight);
            float barColorMapped = PApplet.map(level, 0, 1, 0, 1);
            p.fill(p.lerpColor(p.color(240, 100, 100), p.color(300, 100, 100), barColorMapped + average));            
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
                p.rect(barX, i * barHeight / numBars + barGap, barHeightMapped, barHeight / numBars - barGap);
            }
            else if (side == 3) 
            {
                p.rect(barX - barHeightMapped, i * barHeight / numBars + barGap, barHeightMapped, barHeight / numBars - barGap);
            }
        }
    }
}

