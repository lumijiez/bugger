package org.lumijiez.bugger;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
    public final Bugger bugger = Bugger.getInstance();

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        bugger.cycle(delta);
    }


    @Override
    public void resize(int w, int h) {
        bugger.resize(w, h);
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
