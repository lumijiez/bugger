package org.lumijiez.bugger;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    @Override
    public void create() {
        setScreen(new GameScreen());
        Gdx.graphics.setResizable(true);
        Gdx.graphics.setTitle("Bugger");
    }
}
