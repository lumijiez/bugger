package org.lumijiez.bugger.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.Player;
import org.lumijiez.bugger.handlers.*;

import static org.lumijiez.bugger.Bugger.spriteBatch;

public class GameSystemsFacade {
    private static GameSystemsFacade instance;
    private final ProjectileHandler projectileHandler;
    private final SpaceVFXHandler spaceVFXHandler;
    private final EnemyProjectileHandler enemyProjectileHandler;
    private final InputHandler inputHandler;
    private final CameraHandler cameraHandler;
    private final EnemyHandler enemyHandler;
    private final ParticleHandler particleHandler;
    private final CleanupHandler cleanupHandler;
    private final InterfaceHandler interfaceHandler;
    private final Player player;

    public GameSystemsFacade() {
        player = Player.getInstance();
        interfaceHandler = InterfaceHandler.getInstance();
        enemyProjectileHandler = EnemyProjectileHandler.getInstance();
        inputHandler = InputHandler.getInstance();
        cameraHandler = CameraHandler.getInstance();
        spaceVFXHandler = SpaceVFXHandler.getInstance();
        projectileHandler = ProjectileHandler.getInstance();
        enemyHandler = EnemyHandler.getInstance();
        particleHandler = ParticleHandler.getInstance();
        cleanupHandler = CleanupHandler.getInstance();
    }

    public static GameSystemsFacade getInstance() {
        if (instance == null) {
            instance = new GameSystemsFacade();
        }
        return instance;
    }

    public void update(float delta) {
        step();
        renderClear();
        spaceVFXHandler.render();
        projectileHandler.cycle(delta);
        enemyProjectileHandler.cycle(delta);
        enemyHandler.cycle();
        particleHandler.cycle(delta);
        cleanupHandler.tryClean();
        cameraHandler.updateCamera();
        player.render();
        enemyHandler.render(delta);

        if (interfaceHandler.isDebug()) renderDebug();

        interfaceHandler.renderUI();
        inputHandler.handleInput();
    }

    public void renderClear() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void renderDebug() {
        Bugger.getInstance().debugRenderer.render(
            Bugger.getInstance().getWorld(), spriteBatch.getProjectionMatrix());
    }

    public void step() {
        Bugger.getInstance().getWorld().step((float) 1 / 30f, 6, 2);
    }
}
