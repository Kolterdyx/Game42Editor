package me.kolterdyx.game42editor.components.controlpanelwidgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import me.kolterdyx.game42editor.components.enums.Textures;

public class Button {

    private Runnable func;
    private Vector2 pos;
    private Vector2 size;
    private Texture texture;
    private Texture defaultTexture;
    private Texture pressedTexture;
    private String label;
    private GlyphLayout layout;

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
        this.labelPos = new Vector2(pos.x + (size.x - layout.width)/2, pos.y + (size.y + layout.height)/2);
    }

    public Vector2 getSize() {
        return size;
    }

    public Texture getTexture() {
        return texture;
    }

    private BitmapFont font;
    private Vector2 labelPos;

    public Button(Vector2 pos, Vector2 size, Runnable func, String label){
        this.func = func;
        this.pos = pos;
        this.label = label;
        this.font = new BitmapFont();
        this.layout = new GlyphLayout(font, label);
        this.size = size;
        this.size.x = Math.max(size.x, layout.width+30);
        this.labelPos = new Vector2(pos.x + (size.x - layout.width)/2, pos.y + (size.y + layout.height)/2);
        this.defaultTexture = Textures.BOX.getWithSize(size.x, size.y);
        this.pressedTexture = Textures.DARK_BOX.getWithSize(size.x, size.y);
        this.texture = this.defaultTexture;

    }

    private void execute(){
        try {
            func.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean clicked(int x, int y){
        return x>pos.x && x<pos.x+size.x && y>pos.y && y<pos.y+size.y;
    }

    public void update(int x, int y, boolean release) {
        if (clicked(x, y)){
            this.texture = this.pressedTexture;
            if (release) {
                execute();
                this.texture = this.defaultTexture;
            }
        }
    }

    public void setLabel(String label){
        this.label = label;
    }

    public void render(Batch batch){
        batch.draw(texture, pos.x, pos.y);
        font.draw(batch, layout, labelPos.x, labelPos.y);
    }
}
