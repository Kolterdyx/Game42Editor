package me.kolterdyx.game42editor.components;

import com.badlogic.gdx.math.Vector2;
import me.kolterdyx.game42editor.components.enums.SelectionType;
import org.json.*;
import me.kolterdyx.game42editor.components.board.Board;
import me.kolterdyx.game42editor.components.widgets.Button;
import me.kolterdyx.game42editor.components.widgets.Selector;
import me.kolterdyx.game42editor.components.enums.Orientation;
import me.kolterdyx.game42editor.components.enums.Textures;
import me.kolterdyx.game42editor.components.enums.Tiles;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class EditorPanel extends ControlPanel {

    Button exportButton;
    Button importButton;
    Button clearButton;
    Selector tileSelector;
    Selector instructionSelector;


    private Tiles selectedTile = Tiles.BLANK;
    private final Tiles[] tileOptions;

    public EditorPanel(Level parent){
        this.parent = parent;
        tileOptions = new Tiles[]{Tiles.RED, Tiles.GREEN, Tiles.BLUE, Tiles.RED_TARGET, Tiles.GREEN_TARGET, Tiles.BLUE_TARGET, Tiles.BLANK};
        exportButton = new Button(new Vector2(100f, 100f), new Vector2(100, 50), () -> export_level(), "Export level");
        importButton = new Button(new Vector2(250f, 100f), new Vector2(100, 50), () -> import_level(), "Import level");
        clearButton = new Button(new Vector2(400f, 100f), new Vector2(100, 50), () -> clear_board(), "Clear board");
        tileSelector = new Selector(new Vector2(100f, 300f), 40,7, Orientation.HORIZONTAL, SelectionType.EXCLUSIVE);
        instructionSelector = new Selector(new Vector2(100f, 240f), 40,6, Orientation.HORIZONTAL, SelectionType.INCLUSIVE);
//        tileSelector.splitInGroups(3, Orientation.VERTICAL);
//        instructionSelector.splitInGroups(3, Orientation.VERTICAL);
        addWidget(exportButton);
        addWidget(importButton);
        addWidget(clearButton);
        addWidget(tileSelector);
        addWidget(instructionSelector);
        tileSelector.setIconTexture(Textures.RED_TILE, 0);
        tileSelector.setIconTexture(Textures.GREEN_TILE, 1);
        tileSelector.setIconTexture(Textures.BLUE_TILE, 2);
        tileSelector.setIconTexture(Textures.RED_TARGET, 3);
        tileSelector.setIconTexture(Textures.GREEN_TARGET, 4);
        tileSelector.setIconTexture(Textures.BLUE_TARGET, 5);
        tileSelector.setIconTexture(Textures.ROCKET, 6);
        instructionSelector.setIconTexture(Textures.ARROW_LEFT, 0);
        instructionSelector.setIconTexture(Textures.ARROW_UP, 1);
        instructionSelector.setIconTexture(Textures.ARROW_RIGHT, 2);
        instructionSelector.setIconTexture(Textures.RED_PAINT, 3);
        instructionSelector.setIconTexture(Textures.GREEN_PAINT, 4);
        instructionSelector.setIconTexture(Textures.BLUE_PAINT, 5);
    }

    public Tiles getSelectedColor() {
        return selectedTile;
    }

    public void export_level() {
        try {
            Board board = parent.getBoard();
            int[][] data = board.serialize();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("board", data);
            jsonObj.put("instructions", instructionSelector.getMultipleSelected());
            FileWriter file = new FileWriter("levels/test.json");
            file.write(jsonObj.toString(2));
            file.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void import_level(){
        try {
            File file = new File("levels/test.json");
            String jsonText = "";
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                jsonText += scanner.nextLine();
            }
            JSONObject jsonObj = new JSONObject(jsonText);
            JSONArray jsonBoardData = (JSONArray) jsonObj.get("board");
            int[][] data = new int[jsonBoardData.length()][jsonBoardData.getJSONArray(0).length()];
            for (int x=0;x<jsonBoardData.length();x++){
                JSONArray internalJsonData = jsonBoardData.getJSONArray(x);
                for (int y=0;y<internalJsonData.length();y++){
                    data[x][y] = internalJsonData.getInt(y);
                }
            }
            parent.getBoard().deserialize(data);
            JSONArray jsonInstructionData = (JSONArray) jsonObj.get("instructions");
            boolean[] instructions = new boolean[jsonInstructionData.length()];
            for (int i = 0; i < instructions.length; i++) {
                instructions[i] = jsonInstructionData.getBoolean(i);
            }
            instructionSelector.setSelected(instructions);


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void clear_board(){
        parent.getBoard().clear();
    }

    @Override
    public void onLeftButtonEvent(int x, int y, boolean release){
        int selected = tileSelector.getSelected();
        selectedTile = selected > -1 ? tileOptions[selected] : Tiles.BLANK;
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
