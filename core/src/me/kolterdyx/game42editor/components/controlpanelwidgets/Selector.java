package me.kolterdyx.game42editor.components.controlpanelwidgets;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import me.kolterdyx.game42editor.components.enums.Orientation;

public class Selector {

    private int selectedOption;
    private SelectionBox[] boxes;
    private int size;
    private Texture background;
    private Orientation orientation;
    private int margin = 2;
    private Vector2 pos;

    public Selector(Vector2 pos, int size, int options, Orientation orientation){
        this.pos = pos;
        boxes = new SelectionBox[options];
        this.orientation = orientation;
        for (int i=0;i<options;i++){
            if (orientation == Orientation.HORIZONTAL) {
                boxes[i] = new SelectionBox(this, i, size, new Vector2(i*size+margin*i, 0f));
            } else if (orientation == Orientation.VERTICAL){
                boxes[i] = new SelectionBox(this, i, size, new Vector2(0f, i*size+margin*i));
            }
        }
        selectedOption = -1;
    }

    public void setPos(Vector2 pos){
        this.pos = pos;
        for (SelectionBox box : boxes) {
            box.updatePos();
        }
    }

    public Vector2 getPos(){
        return pos;
    }

    public void setIconTexture(Texture iconTexture, int index){
        boxes[index].setIconTexture(iconTexture);
    }

    public void update(int x, int y, boolean release) {
        selectedOption = -1;
        for (SelectionBox box : boxes){
            box.update(x, y, release);
            if (box.selected()){
                selectedOption = box.getIndex();
            }
        }
    }

    protected SelectionBox[] getBoxes(){
        return boxes;
    }

    public void render(Batch batch) {
        for (SelectionBox box : boxes){
            box.render(batch);
        }
    }

    public int getSelected() {
        return selectedOption;
    }
}
