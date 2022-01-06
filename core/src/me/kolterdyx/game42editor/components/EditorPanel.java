package me.kolterdyx.game42editor.components;

import com.badlogic.gdx.math.Vector2;
import me.kolterdyx.game42editor.components.controlpanelwidgets.Button;
import me.kolterdyx.game42editor.components.controlpanelwidgets.Selector;
import me.kolterdyx.game42editor.components.enums.Orientation;
import me.kolterdyx.game42editor.components.enums.Textures;


public class EditorPanel extends ControlPanel {

    Button testButton;
    Selector tileSelector;



    private Textures selectedTile = Textures.BLANK_TILE;
    private Textures[] tileOptions;

    public EditorPanel(Level parent){
        this.parent = parent;
        tileOptions = new Textures[]{Textures.RED_TILE, Textures.GREEN_TILE, Textures.BLUE_TILE, Textures.RED_TARGET, Textures.GREEN_TARGET, Textures.BLUE_TARGET};
        testButton = new Button(new Vector2(100f, 100f), new Vector2(100, 50), () -> test(), "Test");
        tileSelector = new Selector(new Vector2(200f, 300f), 40,6, Orientation.HORIZONTAL);
        addWidget(testButton);
        addWidget(tileSelector);
        tileSelector.setPos(new Vector2(600, 400));
        tileSelector.setIconTexture(Textures.RED_TILE.getWithSize(20), 0);
        tileSelector.setIconTexture(Textures.GREEN_TILE.getWithSize(20), 1);
        tileSelector.setIconTexture(Textures.BLUE_TILE.getWithSize(20), 2);
        tileSelector.setIconTexture(Textures.RED_TARGET.getWithSize(20), 3);
        tileSelector.setIconTexture(Textures.GREEN_TARGET.getWithSize(20), 4);
        tileSelector.setIconTexture(Textures.BLUE_TARGET.getWithSize(20), 5);
    }

    public Textures getSelectedColor() {
        return selectedTile;
    }

    public void test(){
        System.out.println("------------");
        for (int[] x : parent.getBoard().serialize())
        {
            for (int y : x)
            {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void onLeftButtonEvent(int x, int y, boolean release){
        int selected = tileSelector.getSelected();
        selectedTile = selected > -1 ? tileOptions[selected] : Textures.BLANK_TILE;
        for (Button button : this.buttons){
            button.update(x, y, release);
        }
        if (release) {
            for (Selector selector : this.selectors){
                selector.update(x, y, release);
            }
        }
    }

    @Override
    public void onRightButtonEvent(int x, int y, boolean release) {

    }


}
