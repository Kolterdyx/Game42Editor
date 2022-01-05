package me.kolterdyx.game42editor.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import me.kolterdyx.game42editor.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Game 42 Level Editor");
		config.setWindowedMode(1280, 720);
		new Lwjgl3Application(new Main(), config);
	}
}
