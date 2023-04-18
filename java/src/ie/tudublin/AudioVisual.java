package ie.tudublin;

public class AudioVisual extends Visual {

    Audio1 example;
    LifeBoard life;

    public void settings() {
        size(1024, 800);
    }

    public void setup() {

        colorMode(HSB);
        background(0);

        startMinim();
        loadAudio("java/data/aria_math.mp3");
        
        smooth();
        /*
         * Use this to make a new instance of your written class.
         * e.g rokas = new Rokas(x, y, z, this)
         */

        example = new Audio1(ab, this);
        life = new LifeBoard(frameSize / 10, ab, this);
        life.randomise();

        ap.play();

    }

    public void keyPressed() {
        if (key == ' ') {
            if (ap.isPlaying()) {
                ap.pause();
            } else {
                ap.play();
            }
        }
    }

    public void draw() {

        /*
         * Make a method such as render() in your own class, which can be called from
         * this method.
         * We can later use timing within this method to call our own render() methods
         * at different points in the song.
         * 
         * rokas.render();
         */

        // example.render();
        life.render();
        life.setMouse();
    }
}
