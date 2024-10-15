package org.lumijiez.bugger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import org.lumijiez.bugger.entities.Entity;
import org.lumijiez.bugger.entities.Player;
import org.lumijiez.bugger.entities.enemies.EnemyEntity;
import org.lumijiez.bugger.entities.enemies.Wasp;
import org.lumijiez.bugger.entities.weapons.Arrow;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private final World world;
    private final Player player;
    public static final SpriteBatch spriteBatch = new SpriteBatch();
    private final List<EnemyEntity> enemies;
    private Array<Arrow> arrows;
    private Array<Entity> entitiesToDestroy;
    private float enemySpawnTimer = 0f;
    private static final float ENEMY_SPAWN_INTERVAL = 0.5f;
    private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public GameScreen() {
        world = new World(new Vector2(0, 0), true);
        player = Player.getInstance();
        player.setPlayer(world, 100, 100);
        enemies = new ArrayList<>();
        arrows = new Array<>();
        entitiesToDestroy = new Array<>();
        world.setContactListener(new GameContactListener());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1f, 6, 2);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Arrow arrow = player.shootArrow();
            arrows.add(arrow);
        }

        for (Arrow arrow : arrows) {
            if (!arrow.isMarkedToDestroy()) {
                arrow.update(delta);
                arrow.render();
            } else {
                entitiesToDestroy.add(arrow);
            }
        }

        for (EnemyEntity enemy : enemies) {
            if (enemy.isMarkedToDestroy()) {
                entitiesToDestroy.add(enemy);
            }
        }

        for (Entity entity : entitiesToDestroy) {
            world.destroyBody(entity.getBody());
            if (entity instanceof Arrow) arrows.removeValue((Arrow) entity, true);
            if (entity instanceof EnemyEntity) {
                Gdx.app.log("EFFECT", "PLAYED");
                ParticleManager.getInstance().playEffect(entity.getBody().getPosition().x, entity.getBody().getPosition().y);
                enemies.remove(entity);
            }
        }
        entitiesToDestroy.clear();

        ParticleManager.getInstance().update(delta);
        player.render();



        ParticleManager.getInstance().render(spriteBatch);

        enemySpawnTimer += delta;
        if (enemySpawnTimer >= ENEMY_SPAWN_INTERVAL) {
            enemies.add(new Wasp(world, Player.getInstance().getPosition()));
            enemySpawnTimer = 0;
        }

        for (EnemyEntity enemy : enemies) {
            enemy.moveTowards(player.getPosition());
            enemy.render();
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
