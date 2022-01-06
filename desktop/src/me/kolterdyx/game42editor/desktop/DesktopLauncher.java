package me.kolterdyx.game42editor.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import me.kolterdyx.game42editor.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Game 42 Level Editor");
		config.setWindowedMode(1280, 720);
		config.setResizable(false);
		boolean debug = false;
		if (debug){
			int marginX = 150;
			int marginY = 150;
			Graphics.Monitor secondary = Lwjgl3ApplicationConfiguration.getMonitors()[1];
			config.setWindowPosition(secondary.virtualX + marginX, secondary.virtualY + marginY);
		}
		new Lwjgl3Application(new Main(), config);
	}
}
