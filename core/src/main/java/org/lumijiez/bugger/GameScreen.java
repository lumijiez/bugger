package org.lumijiez.bugger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.lumijiez.bugger.entities.Player;
import org.lumijiez.bugger.entities.enemies.Wasp;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private final World world;
    private final Player player;
    public static final SpriteBatch spriteBatch = new SpriteBatch();;
    private List<Wasp> enemies;
    private float enemySpawnTimer = 0f; // Timer to manage enemy spawning
    private static final float ENEMY_SPAWN_INTERVAL = 2f;
    private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public GameScreen() {
        world = new World(new Vector2(0, 0), true);
        player = Player.getInstance();
        player.setPlayer(world, 100, 100);
        enemies = new ArrayList<>();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1 / 2f, 6, 2);
        player.render();

        enemySpawnTimer += delta;
        if (enemySpawnTimer >= ENEMY_SPAWN_INTERVAL) {
            enemies.add(new Wasp(world, Player.getInstance().getPosition()));
            enemySpawnTimer = 0; // Reset the timer
        }

        // Move enemies towards the player
        for (Wasp enemy : enemies) {
            enemy.moveTowards(player.getPosition());
        }

        // Render player and enemies
        player.render(); // Call the player's render method
        for (Wasp enemy : enemies) {
            enemy.render(); // Render each enemy
        }

        debugRenderer.render(world, spriteBatch.getProjectionMatrix());
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
        spriteBatch.dispose();
        world.dispose();
    }
}
