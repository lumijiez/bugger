package org.lumijiez.bugger;

import com.badlogic.gdx.Screen;
import org.lumijiez.bugger.entities.Player;

public class GameScreen implements Screen {
    public final Bugger bugger = Bugger.getInstance();



    public GameScreen() {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        bugger.cycle(delta);
    }


    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        bugger.dispose();
    }
}
