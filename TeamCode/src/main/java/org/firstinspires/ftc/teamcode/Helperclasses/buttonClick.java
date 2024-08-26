//check the state of a button

package org.firstinspires.ftc.teamcode.Helperclasses;

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
<<<<<<< HEAD
        if(buttonState && !pressed) /* when the button starts being pressed*/ {
            pressed = true;
            startTime = System.currentTimeMillis(); /*when the button starts to be pressed*/
        } else if (!buttonState && pressed) /*when the button is released*/ {
            pressed = false;
            long elapsedTime = System.currentTimeMillis() - startTime; /*figure out how long the button has been pressed */
            if (elapsedTime > pressTime) /*if the button has been pressed for long enough, increment the count. */ {
=======
        if(buttonState && !pressed) {
            pressed = true;
            startTime = System.currentTimeMillis();
        } else if(!buttonState && pressed) {
            pressed = false;
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime > pressTime) {
>>>>>>> d7bcddad4902f8638b97ab7fd967c5938c1df1b9
                clickCount++;
            }
        }
    }

<<<<<<< HEAD
    public int getClickCount() {
        return clickCount;
    }

    public void resetClickCount() {
        clickCount = 0;
    }
=======
    public int getClickCount() { return clickCount; }

    public void resetClickCount() { clickCount = 0; }
>>>>>>> d7bcddad4902f8638b97ab7fd967c5938c1df1b9
}
