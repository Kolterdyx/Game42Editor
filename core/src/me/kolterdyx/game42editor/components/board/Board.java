package me.kolterdyx.game42editor.components.board;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import me.kolterdyx.game42editor.components.EditorPanel;
import me.kolterdyx.game42editor.components.Level;
import me.kolterdyx.game42editor.components.enums.Direction;
import me.kolterdyx.game42editor.components.enums.Textures;
import me.kolterdyx.game42editor.components.enums.Tiles;

public class Board {
    private Tile[][] board;
    private Vector2 size;
    private int margin;
    private int tileSize;
    private Vector2 pos;
    private Tile[] board1d;
    private final Level parent;
    private BoardSerializer serializer;
    private Rocket rocket;

    public Board(Level parent, Vector2 pos, int width, int height, int margin, int tileSize){
        this.pos = pos;
        this.board = new Tile[width][height];
        this.size = new Vector2(width, height);
        this.margin = margin;
        this.tileSize = tileSize;
        this.parent = parent;
        this.serializer = new BoardSerializer(this);

        for (int x=0;x<width;x++){
            for (int y=0;y<height;y++){
                Tile tile = new Tile(new Vector2(x*tileSize+margin*x+pos.x, y*tileSize+margin*y+pos.y));
                tile.setTile(Tiles.BLANK);
                this.board[x][y] = tile;
                tile.setSize(tileSize);
            }
        }

        this.rocket = new Rocket(new Vector2(0, 0), this, Direction.UP);
        rocket.setTilePos(new Vector2(0, 0));
        this.board1d = board1d(board);
    }

    public Board(Level parent, float width, float height){
        this(parent, Vector2.Zero, (int) width, (int) height, 2, 70);
    }

    public Board(Level parent, Vector2 boardSize){
        this(parent, boardSize.x, boardSize.y);
    }

    public void setPos(int x, int y){
        this.setPos(new Vector2(x, y));
    }

    public void setPos(Vector2 pos){
        this.pos = pos;

        for (int x=0;x<size.x;x++){
            for (int y=0;y<size.y;y++){
                Tile tile = board[x][y];
                tile.setPos((int)(x*tileSize+margin*x+pos.x), (int)(y*tileSize+margin*y+pos.y));
            }
        }

        rocket.setTilePos(rocket.getTilePos());
    }

    public void setTileSize(int size){
        this.tileSize = size;
        for (Tile tile: board1d){
            tile.setSize(this.tileSize);
        }
        rocket.setSize(size);
        this.setPos(this.pos);
    }

    public Tile[] board1d(Tile[][] input){
        Tile[] out = new Tile[input.length * input[0].length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                out[i + (j * input.length)] = input[i][j];
            }
        }
        return out;
    }

    public Vector2 getPosFromTile(Vector2 tilePos){
        return board[(int)tilePos.x][(int)tilePos.y].getPos().cpy();
    }

    public void render(Batch batch){
        for (Tile tile : board1d){
            Texture tex = tile.getTexture();
            batch.draw(tex, tile.getPos().x, tile.getPos().y);
        }
        rocket.render(batch);
    }

    public void leftClick(int x, int y){
        for (Tile tile : board1d){
            if (tile.clicked(x, y)) {
                EditorPanel panel = (EditorPanel) parent.getPanel();
                tile.setTile(panel.getSelectedColor());
            }
        }
    }

    public void rightClick(int x, int y) {
        for (Tile tile : board1d){
            if (tile.clicked(x, y)) {
                tile.setTile(Tiles.BLANK);
            }
        }
    }

    public int[][] serialize(){
        serializer.serialize(board);
        return serializer.getData();
    }

    public void deserialize(int[][] data){
        if (data.length != board.length || data[0].length != board[0].length) {
            System.out.println("Wrong board shape, data not loaded");
            return;
        }
        for (int x=0;x<data.length;x++){
            for (int y=0;y<data[0].length;y++){
                int value = data[x][y];
                Tile btile = board[x][y];
                for (Tiles tile : Tiles.values()){
                    if (value == tile.getId()){
                        btile.setTile(tile);
                        break;
                    }
                }
            }
        }
    }

    public void clear() {
        for (Tile tile : board1d){
            tile.setTile(Tiles.BLANK);
        }
    }

    public Vector2 getShape() {
        return new Vector2(board.length, board[0].length);
    }

    public int getTileSize() {
        return tileSize;
    }

    public Rocket getRocket() {
        return rocket;
    }
}
