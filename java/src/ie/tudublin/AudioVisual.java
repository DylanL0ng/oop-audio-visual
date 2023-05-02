package ie.tudublin;

public class AudioVisual extends Visual {

    // Audio1 example;
    LifeBoard life;
    
    // song is 310 seconds long
    int seconds = 0;
    rokas rokas;

    dylan dylan;

    public void settings() {
        size(1024, 800, P3D);
    }

    public void setup() {

        colorMode(HSB);
        background(0);

        startMinim();
        loadAudio("data/aria_math.mp3");
        
        smooth();
        /*
         * Use this to make a new instance of your written class.
         * e.g rokas = new Rokas(x, y, z, this)
         */

        life = new LifeBoard(frameSize / 8, ab, this);
        rokas = new rokas(ab, this);
        dylan = new dylan(ab, ap, this);

        ap.play();

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
    }

    public void draw() {
        dylan.render();
        // return;
        /*
         * Make a method such as render() in your own class, which can be called from
         * this method.
         * We can later use timing within this method to call our own render() methods
         * at different points in the song.
         * 
         * rokas.render();
         */

        // time();

        // if (seconds <= 22) {
        //     // song fades in 
        //     rokas.render();
        // } 
        
        // if (seconds > 22 && seconds <= 48) {
        //     // higher instrument introduced
        //     life.render();
        // }

        // if (seconds > 48 && seconds <= 79) {
        //     // interesting instrument change here
        // }

        // if (seconds > 79 && seconds <= 109) {
        //     // interesting instrument change here
        // }

        // if (seconds > 109 && seconds <= 150) {
        //     // instruments slow down and go quiet here
        // }

        // if (seconds > 150 && seconds <= 193) {
        //     // instruments get very loud here
        // }

        // if (seconds > 193 && seconds <= 216) {
        //     // strong bass here
        // }

        // if (seconds > 216 && seconds <= 262) {
        //     // orchestra instruments here
        // }

        // if (seconds > 262 && seconds <= 285) {
        //     // instruments dying, song fades
        // }
    }

    public void time() {
        if (frameCount % 60 == 0) {
            if (ap.isPlaying()) {
                // seconds only get increased if the song is playing
                seconds++;
            }
        }
    }
}