package me.kolterdyx.game42editor.components.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import me.kolterdyx.game42editor.components.enums.SelectionType;
import me.kolterdyx.game42editor.components.enums.Textures;

public class SelectionBox {
    private final int index;
    private boolean selected;
    private int size;
    private Vector2 pos;
    private Texture bgTexture;
    private Texture selectedBgTexture;
    private Texture defaultBgTexture;
    private Texture iconTexture;
    private Vector2 offset;
    private Selector parent;
    private boolean clicked;

    public SelectionBox(Selector parent, int index, int size, Vector2 offset){
        this.index = index;
        this.size = size;
        this.pos = parent.getPos().cpy().add(offset);
        this.offset = offset;
        this.parent = parent;

        this.defaultBgTexture = Textures.BOX.getWithSize(size);
        this.selectedBgTexture = Textures.DARK_BOX.getWithSize(size);
        this.bgTexture = defaultBgTexture;
    }

    public void setOffset(Vector2 offset){
        this.offset = offset;
        updatePos();
    }

    public void setTexture(Textures default_, Textures selected){
        this.defaultBgTexture = default_.getWithSize(size);
        this.selectedBgTexture = selected.getWithSize(size);
        bgTexture = defaultBgTexture;
    }

    public void updatePos() {
        pos = parent.getPos().cpy().add(offset);
    }

    public void update(int x, int y, boolean release) {

        bgTexture = defaultBgTexture;

        clicked = x > pos.x && x < pos.x+size && y > pos.y && y < pos.y+size;

        if (parent.getSelectionType() == SelectionType.EXCLUSIVE){
            if (clicked && release) {
                selected = !selected;
            }
            if (selected) {
                bgTexture = selectedBgTexture;
                for (SelectionBox box : parent.getBoxes()) {
                    if (box != this) {
                        box.setSelected(false);
                        box.bgTexture = box.defaultBgTexture;
                    }
                }
            }
        } else if (parent.getSelectionType() == SelectionType.INCLUSIVE) {
            if (clicked && release) {
                selected = !selected;
            }

            if (selected) {
                bgTexture = selectedBgTexture;
            }
        }
    }

    void setSelected(boolean selected) {
        this.selected = selected;
    }


    public void render(Batch batch){
        batch.draw(bgTexture, pos.x, pos.y);
        if (iconTexture != null) batch.draw(iconTexture, pos.x+(bgTexture.getWidth()-iconTexture.getWidth())/2, pos.y+(bgTexture.getHeight()-iconTexture.getHeight())/2);
    }

    public void setIconTexture(Texture iconTexture){
        this.iconTexture = iconTexture;
//        defaultBgTexture = Textures.combineTextures(Textures.BOX.getWithSize(size), iconTexture);
////        selectedBgTexture = Textures.combineTextures(Textures.DARK_BOX.getWithSize(size), iconTexture);
//        bgTexture = defaultBgTexture;
    }

    public boolean selected(){
        return selected;
    }

    public int getIndex() {
        return index;
    }
}
