package me.kolterdyx.game42editor.components.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import me.kolterdyx.game42editor.Main;

public class InputHandler implements InputProcessor {
    private boolean isButtonPressed = false;
    private int buttonPressed = Input.Buttons.LEFT;
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        isButtonPressed = true;
        buttonPressed = button;
        if (button == Input.Buttons.LEFT){
            Main.currentLevel.onLeftButtonEvent(screenX, Gdx.app.getGraphics().getHeight()-screenY, false, false);
            return true;
        } else if (button == Input.Buttons.RIGHT){
            Main.currentLevel.onRightButtonEvent(screenX, Gdx.app.getGraphics().getHeight()-screenY, false, false);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isButtonPressed = false;
        if (button == Input.Buttons.LEFT){
            Main.currentLevel.onLeftButtonEvent(screenX, Gdx.app.getGraphics().getHeight()-screenY, true, false);
            return true;
        } else if (button == Input.Buttons.RIGHT){
            Main.currentLevel.onRightButtonEvent(screenX, Gdx.app.getGraphics().getHeight()-screenY, true, false);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isButtonPressed) {
            if (buttonPressed == Input.Buttons.LEFT) {
                Main.currentLevel.onLeftButtonEvent(screenX, Gdx.app.getGraphics().getHeight() - screenY, false, true);
                return true;
            } else if (buttonPressed == Input.Buttons.RIGHT) {
                Main.currentLevel.onRightButtonEvent(screenX, Gdx.app.getGraphics().getHeight() - screenY, false, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
