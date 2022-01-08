package me.kolterdyx.game42editor.components.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import me.kolterdyx.game42editor.components.enums.Orientation;
import me.kolterdyx.game42editor.components.enums.SelectionType;
import me.kolterdyx.game42editor.components.enums.Textures;

import java.util.ArrayList;

public class Selector {

    private int selectedOption;
    private boolean[] selectedOptions;
    private SelectionBox[] boxes;
    private int size;
    private Texture background;
    private Orientation orientation;
    private int margin = 4;
    private Vector2 pos;
    private ArrayList<int[]> indexGroups;
    private SelectionType selectionType;

    public Selector(Vector2 pos, int size, int options, Orientation orientation, SelectionType selectionType){
        this.pos = pos;
        boxes = new SelectionBox[options];
        this.indexGroups = new ArrayList<>();
        this.orientation = orientation;
        this.size = size;
        this.selectionType = selectionType;
        selectedOptions = new boolean[options];
        for (int i=0;i<options;i++){
            if (orientation == Orientation.HORIZONTAL) {
                boxes[i] = new SelectionBox(this, i, size, new Vector2(i*size+margin*i, 0f));
            } else if (orientation == Orientation.VERTICAL){
                boxes[i] = new SelectionBox(this, i, size, new Vector2(0f, -i*size+margin*i));
            }
        }
        selectedOption = -1;

        splitInGroups(options, orientation == Orientation.HORIZONTAL ? Orientation.VERTICAL : Orientation.HORIZONTAL);
    }

    public void setPos(Vector2 pos){
        this.pos = pos;
        for (SelectionBox box : boxes) {
            box.updatePos();
        }
    }

    public Vector2 getPos(){
        return pos.cpy();
    }

    public void setIconTexture(Textures iconTexture, int index){
        boxes[index].setIconTexture(iconTexture.getWithSize(this.size/2));
    }

    public void update(int x, int y, boolean release) {
        if (selectionType == SelectionType.EXCLUSIVE){
            selectedOption = -1;
            for (SelectionBox box : boxes){
                box.update(x, y, release);
                if (box.selected()){
                    selectedOption = box.getIndex();
                }
            }
        } else if (selectionType == SelectionType.INCLUSIVE) {
            for (int i = 0; i < boxes.length; i++) {
                boxes[i].update(x, y, release);
                selectedOptions[i] = boxes[i].selected();
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
        if (selectionType == SelectionType.INCLUSIVE){
            System.out.println("This selector is inclusive and therefore the return value of this call is incorrect, please use .getMultipleSelected() instead.");
        }
        return selectedOption;
    }

    public boolean[] getMultipleSelected() {
        if (selectionType == SelectionType.EXCLUSIVE) {
            System.out.println("This selector is exclusive and therefore the return value of this call is incorrect, please use .getSelected() instead.");
        }
        return selectedOptions;
    }


    public void splitInGroups(int groupSize, Orientation stackDirection) {

        ArrayList<Integer> indices = new ArrayList<>();
        this.indexGroups = new ArrayList<>();

        for (int i = 0; i < boxes.length; i++) {
            indices.add(i);
        }

        while (indices.size() > 0){
            int[] group;
            System.out.println(indices.size());
            if (indices.size() >= groupSize){
                group = new int[groupSize];
                for (int i = 0; i < groupSize; i++) {
                    group[i] = indices.get(0);
                    indices.remove(0);
                }
            } else {
                group = new int[indices.size()];
                for (int i = 0; i < indices.size()+1; i++) {
                    group[i] = indices.get(0);
                    indices.remove(0);
                }
            }
            this.indexGroups.add(group);
        }

        for (int i = 0; i < indexGroups.size(); i++) {
            int[] group = indexGroups.get(i);
            for (int j=0;j<group.length;j++) {
                int index = group[j];
                SelectionBox box = boxes[index];
                if (j==0 && j!=group.length-1) {
                    box.setTexture(Textures.SELECTOR_STARTPOINT, Textures.DARK_SELECTOR_STARTPOINT);
                } else if (j==group.length-1 && j!=0) {
                    box.setTexture(Textures.SELECTOR_ENDPOINT, Textures.DARK_SELECTOR_ENDPOINT);
                } else if (j==0 && j==group.length-1) {
                    box.setTexture(Textures.SELECTOR_STANDALONE, Textures.DARK_SELECTOR_STANDALONE);
                } else {
                    box.setTexture(Textures.SELECTOR_MIDPOINT, Textures.DARK_SELECTOR_MIDPOINT);
                }
                if (stackDirection == Orientation.VERTICAL){
                    box.setOffset(new Vector2(j*size, -(i*size+margin*i)));
                } else if (stackDirection == Orientation.HORIZONTAL){
                    box.setOffset(new Vector2(i*size+margin*i, -(j*size)));
                }

            }
        }
    }

    public SelectionType getSelectionType() {
        return selectionType;
    }

    public void setSelected(boolean[] instructions) {
        for (int i = 0; i < boxes.length; i++) {
            boxes[i].setSelected(instructions[i]);
        }
    }

    public void setSelected(int selected) {
        selectedOption = selected;
        for (int i = 0; i < boxes.length; i++) {
            SelectionBox box = boxes[i];
            box.setSelected(i==selectedOption);
        }
    }
}
