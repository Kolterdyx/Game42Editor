package me.kolterdyx.game42editor.components.board;

import com.badlogic.gdx.math.Vector2;

public class BoardSerializer {
    private int[][] data;
    private Board parent;

    public BoardSerializer(Board parent){
        this.parent = parent;
        Vector2 shape = parent.getShape();
        this.data = new int[(int) shape.x][(int)shape.y];

    }

    public void serialize(Tile[][] tiles){
        for (int x=0;x< data.length;x++){
            for (int y=0;y<data[0].length;y++){
                Tile tile = tiles[x][y];
                data[x][y] = tile.getTile().getId();
            }
        }
    }

    public int[][] getData() {
        return data;
    }
}
