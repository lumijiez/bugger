package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import org.lumijiez.bugger.entities.enemies.behaviors.Behaviors;
import org.lumijiez.bugger.entities.player.Player;

public class InputHandler {
    private static InputHandler instance;

    private InputHandler() {}

    public static InputHandler getInstance() {
        if (instance == null) {
            instance = new InputHandler();
        }
        return instance;
    }

    public void handleInput() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            ProjectileHandler.getInstance().shootRay();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            InterfaceHandler.getInstance().toggleDebug();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            EnemyHandler.getInstance().overrideBehaviorForExisting(Behaviors.DEFENSIVE);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            EnemyHandler.getInstance().overrideBehaviorForExisting(Behaviors.FOLLOW);
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            float numRays = 8;
            float radius = 0.5f;

            shootRaysWithDelay(numRays, radius);
        }
    }

    private void shootRaysWithDelay(float duration, float radius) {
        new Thread(() -> {
            int raysPerCircle = 100;
            int totalRays = (int) ((duration / 2) * raysPerCircle);

            for (int i = 0; i < totalRays; i++) {
                float angle = (float) (i * (2 * Math.PI) / raysPerCircle);

                float x = radius * (float) Math.cos(angle);
                float y = radius * (float) Math.sin(angle);

                Vector2 direction = new Vector2(x, y).add(Player.getInstance().getPosition());
                Vector2 shootDirection = direction.cpy().sub(Player.getInstance().getPosition()).nor();

                Gdx.app.postRunnable(() -> ProjectileHandler.getInstance().shootRay(Player.getInstance().getPosition(), shootDirection, 20f));

                try {
                    float delay = (2f / raysPerCircle) * 500;
                    Thread.sleep((long) delay);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }


}
