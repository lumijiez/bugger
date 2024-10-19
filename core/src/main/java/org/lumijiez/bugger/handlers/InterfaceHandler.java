package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.Player;

public class InterfaceHandler {
    private final BitmapFont bitmapFont;
    private static InterfaceHandler instance;

    private InterfaceHandler() {
        bitmapFont = new BitmapFont(Gdx.files.internal("EA.fnt"), Gdx.files.internal("EA.png"), false);
    }

    public static InterfaceHandler getInstance() {
        if (instance == null) {
            instance = new InterfaceHandler();
        }
        return instance;
    }

    public void renderUI() {
        SpriteBatch uiBatch = Bugger.uiBatch;
        OrthographicCamera uiCam = CameraHandler.getInstance().getUICamera();
        int kills = Bugger.kills;
        int enemies = EnemyHandler.getInstance().getEnemies().size();
        int projectiles = ProjectileHandler.getInstance().getDeployedProjectiles().size;
        int bodies = Bugger.getInstance().getWorld().getBodyCount();
        uiBatch.begin();

        uiBatch.setColor(1, 1, 1, 1);
        bitmapFont.draw(uiBatch, String.format("Speed: %.2f", Player.getInstance().getBody().getLinearVelocity().len()),
            10, uiCam.viewportHeight - 10);
        bitmapFont.draw(uiBatch, "Kills: " + kills, 10, uiCam.viewportHeight - 40);

        bitmapFont.setColor(0, 1, 0, 1);

        bitmapFont.draw(uiBatch, "Enemies: " + enemies, 10, uiCam.viewportHeight - 70);
        bitmapFont.draw(uiBatch, "Projectiles: " + projectiles, 10, uiCam.viewportHeight - 100);
        bitmapFont.draw(uiBatch, String.format("Player Pos: (%.2f, %.2f)",
            Player.getInstance().getBody().getPosition().x,
            Player.getInstance().getBody().getPosition().y), 10, uiCam.viewportHeight - 130);
        bitmapFont.draw(uiBatch, "Bodies: " + bodies, 10, uiCam.viewportHeight - 160);

        bitmapFont.setColor(1, 1, 1, 1);

        uiBatch.end();
        uiBatch.setProjectionMatrix(uiCam.combined);
        uiCam.update();
    }
}
