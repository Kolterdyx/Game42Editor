package me.kolterdyx.game42editor.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import me.kolterdyx.game42editor.Main;

public class InputHandler implements InputProcessor {
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
        if (button == Input.Buttons.LEFT){
            Main.currentLevel.onLeftButtonEvent(screenX, Gdx.app.getGraphics().getHeight()-screenY, false);
            return true;
        } else if (button == Input.Buttons.RIGHT){
            Main.currentLevel.onRightButtonEvent(screenX, Gdx.app.getGraphics().getHeight()-screenY, false);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT){
            Main.currentLevel.onLeftButtonEvent(screenX, Gdx.app.getGraphics().getHeight()-screenY, true);
            return true;
        } else if (button == Input.Buttons.RIGHT){
            Main.currentLevel.onRightButtonEvent(screenX, Gdx.app.getGraphics().getHeight()-screenY, true);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
