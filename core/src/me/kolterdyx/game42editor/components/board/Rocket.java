package me.kolterdyx.game42editor.components.board;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import me.kolterdyx.game42editor.components.board.Board;
import me.kolterdyx.game42editor.components.enums.Direction;
import me.kolterdyx.game42editor.components.enums.Textures;

public class Rocket {
    private Vector2 pos;
    private Vector2 tilePos = new Vector2(0, 0);
    private Direction facing;
    private Texture texture;
    private Board board;
    private int size;
    private float scaleFactor = 0.8f;
    TextureRegion region;

    public Rocket(Vector2 tilePos, Board board, Direction facing){
        this.texture = Textures.ROCKET.getWithSize((int)(board.getTileSize()*0.8));
        this.board = board;
        this.facing = facing;
        setSize(board.getTileSize());
        setTilePos(tilePos);
        region = new TextureRegion(texture);
    }

    public void render(Batch batch){
        switch (facing) {
            case UP:
                batch.draw(texture, pos.x, pos.y);
                break;
            case DOWN:
                batch.draw(region, pos.x, pos.y, texture.getWidth()/2, texture.getHeight()/2, texture.getWidth(), texture.getHeight(), 1f, 1f, 180);
                break;
            case LEFT:
                batch.draw(region, pos.x, pos.y, texture.getWidth()/2, texture.getHeight()/2, texture.getWidth(), texture.getHeight(), 1f, 1f, 90);
                break;
            case RIGHT:
                batch.draw(region, pos.x, pos.y, texture.getWidth()/2, texture.getHeight()/2, texture.getWidth(), texture.getHeight(), 1f, 1f, 270);
                break;
            default:
                break;
        }
    }

    public void goForward(){
        try {
            setTilePos(tilePos.cpy().add(facing.getVector()));
        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    public void turnRight(){
        switch (facing){
            case UP:
                facing = Direction.RIGHT;
                break;
            case RIGHT:
                facing = Direction.DOWN;
                break;
            case DOWN:
                facing = Direction.LEFT;
                break;
            case LEFT:
                facing = Direction.UP;
                break;
        }
    }

    public void turnLeft(){
        switch (facing){
            case UP:
                facing = Direction.LEFT;
                break;
            case RIGHT:
                facing = Direction.UP;
                break;
            case DOWN:
                facing = Direction.RIGHT;
                break;
            case LEFT:
                facing = Direction.DOWN;
                break;
        }
    }

    public void setSize(int size){
        this.size = size;
        texture = Textures.ROCKET.getWithSize((int)(size*scaleFactor));
        region = new TextureRegion(texture);
    }

    public void setTilePos(Vector2 pos){
        setPos(board.getPosFromTile(pos));
        this.tilePos = pos.cpy();
    }

    public void setPos(Vector2 pos) {
        this.pos = pos.cpy().add(new Vector2(size/2, size/2).sub(texture.getWidth()/2, texture.getHeight()/2));
    }

    public Vector2 getTilePos() {
        return tilePos;
    }
}
