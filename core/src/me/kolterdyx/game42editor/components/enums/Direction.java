package me.kolterdyx.game42editor.components.enums;

import com.badlogic.gdx.math.Vector2;

public enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final Vector2 vector;

    Direction(float x, float y){
        vector = new Vector2(x, y);
    }

    public Vector2 getVector() {
        return vector;
    }
}
