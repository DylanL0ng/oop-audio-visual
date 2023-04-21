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
        life = new LifeBoard(frameSize / 8, ab, this);
        life.randomise();

        ap.play();
        ap.skip((ap.length() / 2) + 35000);

    }

    public void keyPressed() {
        if (key == ' ') {
            life.pause();
            if (ap.isPlaying()) {
                ap.pause();
            } else {
                ap.play();
            }
        } 

        if (key == 'a') {
            life.setLifeGrid(30, 30);
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
    }

    public boolean detectBeat() {
        float sum = 0;
        for (int i = 0; i < ab.size(); i++) {
            sum += abs(ab.get(i));
        }

        float average = sum / ab.size();
        float threshold = (float) (4 * average);

        for (int i = 0; i < ab.size(); i++) {
            if (abs(ab.get(i)) > threshold) {
                return true;   
            }
        }

        return false;
    }
}
