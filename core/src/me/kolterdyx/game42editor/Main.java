package me.kolterdyx.game42editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import me.kolterdyx.game42editor.components.utils.InputHandler;
import me.kolterdyx.game42editor.components.Level;

public class Main extends ApplicationAdapter {
	SpriteBatch boardBatch;
	SpriteBatch guiBatch;
	Level level;
	public static Level currentLevel;

	@Override
	public void create () {
		Gdx.input.setInputProcessor(new InputHandler());
		boardBatch = new SpriteBatch();
		guiBatch = new SpriteBatch();
		level = new Level(new Vector2(15, 15));
		currentLevel = level;
	}

	@Override
	public void render () {
		ScreenUtils.clear(0f, 0.05f, 0.05f, 1f);
		boardBatch.begin();
		guiBatch.begin();
		level.render(boardBatch, guiBatch);
		boardBatch.end();
		guiBatch.end();
	}
	
	@Override
	public void dispose () {
		boardBatch.dispose();
	}
}
