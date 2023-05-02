package ie.tudublin;

public class AudioVisual extends Visual {

    LifeBoard callum;
    rokas rokas;
    dylan dylan;
    martin martin;

    // song is 310 seconds long
    int seconds = 0;
    int currentSecond = 0;

    int scene = 0;

    public void settings() {
        size(1024, 800, P3D);
    }

    public void setup() {

        colorMode(HSB);
        background(0);

        startMinim();
        loadAudio("data/aria_math.mp3");

        smooth();

        callum = new LifeBoard(frameSize / 8, ab, this);
        rokas = new rokas(ab, this);
        dylan = new dylan(ab, ap, this);
        martin = new martin(ab, this);

        ap.play();

        // ap.skip(60000);
    }

    public void keyPressed() {
        if (key == ' ') {
            callum.pause();
            if (ap.isPlaying()) {
                ap.pause();
            } else {
                ap.play();
            }
        }

        if (key == '1') {
            scene = 1;
        }
        if (key == '2') {
            scene = 2;
        }
        if (key == '3') {
            scene = 3;
        }
        if (key == '4') {
            scene = 4;
        }
    }

    public void draw() {
        renderScene();
        time();

        if (seconds == 1) {
            // song fades in
            // Rokas scene
            scene = 2;
        }

        if (seconds == 24) {
            // higher instrument introduced
            // Callum scene
            scene = 1;
        }

        if (seconds == 51) {
            // interesting instrument change here
            // Dylan scene
            scene = 3;
        }

        if (seconds == 81) {
            // interesting instrument change here
            // Martin scene
            scene = 4;
        }

        if (seconds == 111) {
            // Tempo change
            // Dylan scene
            scene = 3;
        }

        if (seconds == 151) {
            // instruments slow down and go quiet here
            // Rokas scene
            scene = 2;
        }

        if (seconds == 185) {
            // instruments get very loud here
            // Martin scene
            scene = 4;
        }

        if (seconds == 195) {
            // Song climax
            // Dylan scene
            scene = 3;
        }

        if (seconds == 219) {
            // strong bass here
            // Callum scene
            scene = 1;
        }

        if (seconds == 241) {
            // orchestra
            // Dylan scene
            scene = 3;
        }

        if (seconds == 265) {
            // more orchestra
            // Martin scene
            scene = 4;
        }

        if (seconds == 287) {
            // music dies out
            // Rokas scene
            scene = 2;
        }
    }

    public void time() {
        int newSecond = millis() / 1000 % 60;
        if (newSecond != currentSecond && ap.isPlaying()) {
            // seconds only get increased if the song is playing
            seconds++;
            System.out.println(seconds);
        }
        currentSecond = newSecond;
    }

    public void renderScene() {
        switch (scene) {
            case 1:
                callum.render();
                break;

            case 2:
                rokas.render();
                break;

            case 3:
                dylan.render();
                break;

            case 4:
                martin.render();
                break;
        }

        if (scene != 1) {
            callum.clearBoard();
        }
    }
}