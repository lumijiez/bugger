package org.lumijiez.bugger.pools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;
import java.util.Map;

public class ProjectileFlyweight {
    private static final Map<Boolean, ProjectileFlyweight> flyweights = new HashMap<>();
    private final Sprite sprite;
    private final float size;
    private final float defaultSpeed;
    private final boolean isEnemy;
    public static final String ENEMY_TEXTURE = "images/enemyblaze.png";
    public static final String PLAYER_TEXTURE = "images/blaze.png";

    ProjectileFlyweight(boolean isEnemy) {
        this.sprite = new Sprite(new Texture(isEnemy ? ENEMY_TEXTURE : PLAYER_TEXTURE));
        this.size = 5f;
        this.defaultSpeed = 5000f;
        this.isEnemy = isEnemy;
    }

    public static ProjectileFlyweight get(boolean isEnemy) {
        return flyweights.computeIfAbsent(isEnemy, ProjectileFlyweight::new);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getSize() {
        return size;
    }

    public float getDefaultSpeed() {
        return defaultSpeed;
    }

    public boolean isEnemy() {
        return isEnemy;
    }
}

