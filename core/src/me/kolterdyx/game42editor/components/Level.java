package me.kolterdyx.game42editor.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import me.kolterdyx.game42editor.components.board.Board;
import me.kolterdyx.game42editor.components.controller.Function;
import me.kolterdyx.game42editor.components.enums.LevelMode;

import java.util.ArrayList;

public class Level {

    private Board board;
    private LevelMode mode;
    private ControlPanel panel;
    private ArrayList<Function> functions;

    public Level(Vector2 boardSize){
        this.mode = LevelMode.EDIT;
        this.panel = new EditorPanel(this);
        this.panel.setPos(new Vector2(500, 100));
        this.board = new Board(this, boardSize);
        this.board.setPos(100, 100);
        this.board.setTileSize(30);
        this.functions = new ArrayList<>();

    }

    public void render(Batch boardBatch, Batch guiBatch){
        this.panel.render(guiBatch);
        this.board.render(boardBatch);
    }

    public void onLeftButtonEvent(int x, int y, boolean release, boolean dragging){
        if (release || dragging) board.leftClick(x, y);
        this.panel.onLeftButtonEvent(x, y, release);
    }

    public void onRightButtonEvent(int x, int y, boolean release, boolean dragging) {
        if (release || dragging) board.rightClick(x, y);
    }

    public ControlPanel getPanel() {
        return this.panel;
    }

    public Board getBoard() {
        return board;
    }
}
