package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.player.Player;

public class CameraHandler {
    private static CameraHandler instance;

    private final OrthographicCamera cam;
    private final OrthographicCamera uiCam;

    private CameraHandler() {
        cam = new OrthographicCamera((float) (Gdx.graphics.getWidth() / 3), (float) (Gdx.graphics.getHeight() / 3));
        cam.position.set(Player.getInstance().getPosition().x / 2f, Player.getInstance().getPosition().y / 2f, 0);
        cam.update();

        uiCam = new OrthographicCamera((float) (Gdx.graphics.getWidth() * 1.5), (float) (Gdx.graphics.getHeight() * 1.5));
        uiCam.position.set(uiCam.viewportWidth / 2, uiCam.viewportHeight / 2, 0);
        uiCam.update();
    }

    public static CameraHandler getInstance() {
        if (instance == null) {
            instance = new CameraHandler();
        }
        return instance;
    }

    public OrthographicCamera getCamera() {
        return cam;
    }

    public OrthographicCamera getUICamera() {
        return uiCam;
    }

    public void updateCamera() {
        Player player = Player.getInstance();
        cam.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
        cam.update();
        Bugger.spriteBatch.setProjectionMatrix(cam.combined);
    }
}
