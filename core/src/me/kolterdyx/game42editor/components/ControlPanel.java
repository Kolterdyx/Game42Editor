package me.kolterdyx.game42editor.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import me.kolterdyx.game42editor.components.controlpanelwidgets.Button;
import me.kolterdyx.game42editor.components.controlpanelwidgets.Selector;

import java.util.ArrayList;

public abstract class ControlPanel {
    protected ArrayList<Button> buttons = new ArrayList<>();
    protected ArrayList<Selector> selectors = new ArrayList<>();
    protected Vector2 pos = new Vector2(0, 0);
    protected Level parent;

    public void addWidget(Selector selector){
        selector.setPos(selector.getPos().add(pos));
        this.selectors.add(selector);
    }

    public void addWidget(Button button){
        button.setPos(button.getPos().add(pos));
        this.buttons.add(button);
    }

    public abstract void onLeftButtonEvent(int x, int y, boolean release);
    public abstract void onRightButtonEvent(int x, int y, boolean release);

    public void setPos(Vector2 pos) {
        this.pos = pos;
        for (Button button : buttons){
            button.setPos(button.getPos().add(pos));
        }
    };

    public void render(Batch batch){
        for (Button button : buttons) {
            button.render(batch);
        }
        for (Selector selector : selectors){
            selector.render(batch);
        }
    }

}
