package ie.tudublin;

public class AudioVisual extends Visual {

    LifeBoard callum;
    rokas rokas;
    dylan dylan;

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

        ap.play();
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
            scene = 2;
            // rokas.render();
        }

        if (seconds == 24) {
            // higher instrument introduced
            scene = 1;
            // callum.render();
        }

        if (seconds == 51) {
            // interesting instrument change here
            scene = 3;
            // dylan.render();
        }

        if (seconds == 81) {
            // interesting instrument change here
            scene = 1;
            // martin.render()
        }

        if (seconds == 111) {
            scene = 3;
            // rokas.render();
        }

        if (seconds == 151) {
            // instruments slow down and go quiet here
            scene = 2;
            // callum.render();
        }

        if (seconds == 185) {
            // instruments get very loud here
            scene = 3;
        }

        if (seconds == 195) {
            // scene = 4;
            // martin.render();
            scene = 1;
        }
        
        if (seconds == 219) {
            // strong bass here
            scene = 3;
            // dylan.render();
        }

        if (seconds == 241) {
            // orchestra
            scene = 1;
        }

        if (seconds == 265) {
            // more orchestra
            scene = 3;
            // rokas.render();
        }

        if (seconds == 287) {
            // music dies out 
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
                // martin.render();
                break;
        }

        if (scene != 1) {
            callum.clearBoard();
        }
    }
}