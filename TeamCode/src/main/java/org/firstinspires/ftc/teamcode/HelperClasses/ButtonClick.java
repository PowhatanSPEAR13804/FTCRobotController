//check the state of a button

package org.firstinspires.ftc.teamcode.HelperClasses;

public class ButtonClick {
    public long startTime;
    public boolean pressed;
    public int clickCount;
    public int pressTime;

    public ButtonClick() {
        startTime = 0;
        pressed = false;
        pressTime = 1;
    }

    public void checkButton(boolean buttonState) {
        if(buttonState && !pressed) {
            pressed = true;
            startTime = System.currentTimeMillis();
        } else if(!buttonState && pressed) {
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
}
