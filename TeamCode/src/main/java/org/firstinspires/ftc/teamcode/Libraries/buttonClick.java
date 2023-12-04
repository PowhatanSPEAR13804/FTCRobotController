package org.firstinspires.ftc.teamcode.Libraries;

public class buttonClick {
    public long startTime;
    public boolean pressed;
    public int clickCount;
    public int pressTime;

    public buttonClick() {
        startTime = 0;
        pressed = false;
        pressTime = 1;
    }

    public void checkButton(boolean buttonState) {
        if(buttonState && !pressed) {
            pressed = true;
            startTime = System.currentTimeMillis();
        } else if (!buttonState && pressed) {
            pressed = false;
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime > pressTime) {
                clickCount++;
            }
        }
    }

    public int getClickCount() {
        return clickCount;
    }

    public void resetClickCount() {
        clickCount = 0;
    }
}
