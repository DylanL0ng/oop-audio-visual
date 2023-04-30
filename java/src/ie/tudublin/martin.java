package ie.tudublin;

import processing.core.PApplet;
import processing.core.PVector;
import ddf.minim.AudioBuffer;
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

        //if shapes null - create them
        if (shapes == null) 
        {
            shapes = new ArrayList<>();
            //creates 5 cube, 5 lines, random position
            for (int i = 0; i < 10; i++) 
            {
                shapes.add(new CubeShape(p, new PVector(p.random(p.width), p.random(p.height), p.random(-500, 500))));
                shapes.add(new LineShape(p, new PVector(p.random(p.width), p.random(p.height))));
            }
        }
    
        //calls update and display
        for (Shape shape : shapes) 
        {
            shape.update(average);
            shape.display(average); 
        }
    }
    
}

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

//Represent the cubes
class CubeShape extends Shape 
{
    PVector pos, speed;
    float size;

    CubeShape(PApplet p, PVector pos) 
    {
        super(p);
        this.pos = pos;
        this.speed = new PVector(p.random(-1, 1), p.random(-1, 1), p.random(-1, 1));
        this.size = p.random(20, 50);
    }

    //checks if cube gone outside screen, reverse direction
    void update(float average) 
    {
        pos.add(speed);
        if (pos.x < 0 || pos.x > p.width || pos.y < 0 || pos.y > p.height || pos.z < -500 || pos.z > 500) 
        {
            speed.mult(-1);
        }
    }

    void display(float average) 
    {
        this.average = average;
        p.pushMatrix();
        p.translate(pos.x, pos.y, pos.z);
        //fix
        p.fill(p.lerpColor(p.color(0, 0, 255), p.color(255, 0, 255), average * 10));
        p.box(size + average * 200);
        p.popMatrix();
    }
}

class LineShape extends Shape 
{
    PVector pos, target;

    LineShape(PApplet p, PVector pos) 
    {
        super(p);
        this.pos = pos;
        //setting target to centre screen 
        this.target = new PVector(p.width / 2, p.height / 2);
    }

    //end point of the line to follow mouse cursor (testing - code is interactive)
    void update(float average) 
    {
        target.set(p.mouseX, p.mouseY);
    }

    //
    void display(float average) 
    {
        this.average = average;
        //strokeweight proportional to average - changed based on average
        p.strokeWeight(average * 50);
        //fix
        p.stroke(p.lerpColor(p.color(0, 255, 0), p.color(0, 255, 255), average * 10));
        p.line(pos.x, pos.y, target.x, target.y);
    }
}
