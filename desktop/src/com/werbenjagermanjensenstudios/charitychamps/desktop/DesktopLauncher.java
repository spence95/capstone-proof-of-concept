package com.werbenjagermanjensenstudios.charitychamps.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.werbenjagermanjensenstudios.charitychamps.proofOfConcept;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new proofOfConcept(), config);
	}
}
