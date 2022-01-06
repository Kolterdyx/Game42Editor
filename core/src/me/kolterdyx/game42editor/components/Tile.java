package me.kolterdyx.game42editor.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import me.kolterdyx.game42editor.components.enums.Textures;
import org.w3c.dom.Text;

public class Tile {
    private Vector2 pos;
    private int size;
    private Texture texture;
    private Textures tile;

    public Tile(Vector2 pos){
        this(pos, Textures.BLANK_TILE, 50);
    }

    public Tile(Vector2 pos, Textures tile, int size){
        this.pos = pos;
        this.size = size;
        this.texture = tile.getWithSize((float) size);
        this.tile = tile;
    }

    public void setSize(int size){
        this.size = size;
        this.texture = this.tile.getWithSize((float) size);
    }

    public void setPos(int x, int y){
        this.setPos(new Vector2(x, y));
    }

    public boolean clicked(int x, int y){
        return ((x>pos.x) && (x<pos.x+size) && (y>pos.y) && (y<pos.y+size));
    }

    public void setPos(Vector2 pos){
        this.pos = pos;
    }

    public Vector2 getPos(){
        return this.pos;
    }

    public Textures getTile(){
        return this.tile;
    }

    public int getSize() {
        return size;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setTile(Textures tile){
        this.tile = tile;
        setTexture(tile.getWithSize(size));
    }
}
