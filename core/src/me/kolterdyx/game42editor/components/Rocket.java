package me.kolterdyx.game42editor.components;

import com.badlogic.gdx.math.Vector2;
import me.kolterdyx.game42editor.components.enums.Direction;

public class Rocket {
    private Vector2 pos;
    private Vector2 tilePos;
    private Direction facing;

    public Rocket(Vector2 tilePos, Board board, Direction facing){
        this.tilePos = tilePos;
        this.pos = board.getPosFromTile(this.tilePos);
        this.facing = facing;
    }
}
