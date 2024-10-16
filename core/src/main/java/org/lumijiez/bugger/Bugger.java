package org.lumijiez.bugger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import org.lumijiez.bugger.entities.Entity;
import org.lumijiez.bugger.entities.Player;
import org.lumijiez.bugger.entities.enemies.EnemyEntity;
import org.lumijiez.bugger.entities.enemies.Wasp;
import org.lumijiez.bugger.entities.weapons.Arrow;
import org.lumijiez.bugger.handlers.GameContactListener;
import org.lumijiez.bugger.vfx.ParticleManager;
import org.lumijiez.bugger.vfx.SpaceBackground;

import java.util.ArrayList;
import java.util.List;

public class Bugger {
    private static Bugger instance;
    private final World world;
    private final SpaceBackground spaceBackground;
    private final Array<Arrow> projectiles;
    private final List<EnemyEntity> enemies;
    private final Array<Entity> entitiesToDestroy;
    private final Player player;
    private float enemySpawnTimer = 0f;
    private static final float ENEMY_SPAWN_INTERVAL = 0.5f;
    private final Box2DDebugRenderer debugRenderer;
    public static OrthographicCamera cam;
    public static SpriteBatch spriteBatch;

    private Bugger() {
        world = new World(new Vector2(0, 0), true);
        this.projectiles = new Array<>();
        this.entitiesToDestroy = new Array<>();
        this.enemies = new ArrayList<>();
        this.spaceBackground = new SpaceBackground();
        this.player = Player.getInstance();
        this.player.setPlayer(world, 100, 100);
        this.world.setContactListener(new GameContactListener());
        this.debugRenderer = new Box2DDebugRenderer();
        spriteBatch = new SpriteBatch();
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(Player.getInstance().getPosition().x / 2f, Player.getInstance().getPosition().y / 2f, 0);
        cam.update();
    }

    public static Bugger getInstance() {
        if (instance == null) {
            instance = new Bugger();
        }
        return instance;
    }

    public void cycle(float delta) {
        updateCamera(delta);

        renderClear();
        renderBackground();

        step();

        handleInput();

        cycleProjectiles(delta);
        cycleEnemies();
        cycleParticles(delta);

        clearEntities();

        renderPlayer();
        renderEnemies(delta);
        renderDebug();
    }

    public void renderBackground() {
        spaceBackground.render();
    }

    public void renderEnemies(float delta) {
        enemySpawnTimer += delta;
        if (enemySpawnTimer >= ENEMY_SPAWN_INTERVAL) {
            enemies.add(new Wasp(world, Player.getInstance().getPosition()));
            enemySpawnTimer = 0;
        }

        for (EnemyEntity enemy : enemies) {
            enemy.moveTowards(player.getPosition());
            enemy.render();
        }
    }

    public void renderPlayer() {
        player.render();
    }

    public void renderClear() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void renderDebug() {
        debugRenderer.render(world, spriteBatch.getProjectionMatrix());
    }

    public void updateCamera(float delta) {
        cam.update();
        spriteBatch.setProjectionMatrix(cam.combined);
        cam.position.set(Player.getInstance().getPosition().x, Player.getInstance().getPosition().y, 0);
    }

    public void cycleProjectiles(float delta) {
        for (Arrow arrow : projectiles) {
            if (!arrow.isMarkedToDestroy()) {
                arrow.update(delta);
                arrow.render();
            } else {
                entitiesToDestroy.add(arrow);
            }
        }
    }

    public void cycleEnemies() {
        for (EnemyEntity enemy : enemies) {
            if (enemy.isMarkedToDestroy()) {
                entitiesToDestroy.add(enemy);
            }
        }
    }

    public void cycleParticles(float delta) {
        ParticleManager.getInstance().update(delta);
        ParticleManager.getInstance().render(spriteBatch);
    }

    public void clearEntities() {
        for (Entity entity : entitiesToDestroy) {
            world.destroyBody(entity.getBody());
            if (entity instanceof Arrow) projectiles.removeValue((Arrow) entity, true);
            if (entity instanceof EnemyEntity) {
                playParticle(entity.getBody().getPosition().x, entity.getBody().getPosition().y);
                enemies.remove(entity);
            }
        }
        entitiesToDestroy.clear();
    }

    public void step() {
        world.step(1f, 6, 2);
    }

    public void playParticle(float x, float y) {
        ParticleManager.getInstance().playEffect(x, y);
    }

    public void shoot() {
        Arrow arrow = player.shootArrow();
        projectiles.add(arrow);
    }

    public void handleInput() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            shoot();
        }
    }

    public SpriteBatch batch() {
        return spriteBatch;
    }

    public void dispose() {
        spriteBatch.dispose();
        world.dispose();
        spaceBackground.dispose();
    }

}
