package com.devamrboxaiscratch.game.desktop;

import AiFollow.GamMain;
import AiFollow.Screens.ScrStart;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.width = 1000;
                config.height = 500;
		new LwjglApplication(new ScrStart(), config);
	}
}
