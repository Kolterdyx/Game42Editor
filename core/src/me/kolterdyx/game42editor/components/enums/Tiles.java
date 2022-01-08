package me.kolterdyx.game42editor.components.enums;

import com.badlogic.gdx.graphics.Texture;
import me.kolterdyx.game42editor.components.utils.Counter;

public enum Tiles {
    RED(Textures.RED_TILE),
    GREEN(Textures.GREEN_TILE),
    BLUE(Textures.BLUE_TILE),
    RED_TARGET(Textures.RED_TARGET),
    GREEN_TARGET(Textures.GREEN_TARGET),
    BLUE_TARGET(Textures.BLUE_TARGET),
    BLANK(Textures.BLANK_TILE);

    private Textures texture;
    private int id;

    Tiles(Textures texture){
        this.texture = texture;
        this.id = Counter.getTileId();
    }

    public int getId(){
        return id;
    }

    public int getTextureId(){
        return texture.getId();
    }

    public Texture getWithSize(float size){
        return this.texture.getWithSize(size);
    }

}
