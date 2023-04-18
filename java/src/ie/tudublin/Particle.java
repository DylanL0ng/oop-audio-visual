package ie.tudublin;

import processing.core.PApplet;

public class Particle extends PApplet {
    public void settings()
    {
      size(640, 640);      
    }
    public void setup()
    {      
      background(51);
    }
    
    int red = 255;
    int green = 0;
    int blue = 255;
    int addGreen = 1;
    int addBlue = 0;
    int addRed = 0;
    
    boolean isPrime(int n)
    {
        if (n%2==0)
          return false;
        for(int i=3;i*i<=n;i+=2)
        {
            if(n%i==0)
                return false;
        }
        if (addGreen == 1)
        {
          if (green < 254)
          {
            green++;
            red--;
          }
          else
          {
            addGreen = 0;
            addBlue = 1;
          }
        }
        else if (addBlue == 1)
        {
          if (blue < 254)
          {
            blue++;
            green--;
          }
          else
          {
            addBlue = 0;
            addRed = 1;
          }
        }
        else if (addRed == 1)
        {
          if (red < 254)
          {
            red++;
            blue--;
          }
          else
          {
            addRed = 0;
            addGreen = 1;
          }
        }
        stroke(red, green, blue);
        return true;
    }
    
    int  change = 1;
    int  count = 0;
    int dir = 0;
    int  x = 320;
    int  y = 320;
    int nb = 1;
    int mod = 1;
    
    public void draw()
    {
      if (isPrime(nb))
        point(x, y);
      count++;
      if (dir == 0)
        y++;
      if (dir == 1)
        x--;
      if (dir == 2)
        y--;
      if (dir == 3)
        x++;
      if (count == change)
      {
        if (dir < 3)
          dir++;
        else
          dir = 0;
        count = 0;
        mod++;
        if (mod % 2 == 0)
          change++;
      }
      nb++;
    }

}
