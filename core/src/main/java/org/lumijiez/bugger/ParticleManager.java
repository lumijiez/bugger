package org.lumijiez.bugger;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class ParticleManager {
    private static ParticleManager instance;
    private final ParticleEffectPool particleEffectPool;
    private final Array<ParticleEffectPool.PooledEffect> activeEffects;

    private ParticleManager() {
        ParticleEffect effect = new ParticleEffect();
        effect.load(Gdx.files.internal("particles/boom.p"), Gdx.files.internal("particles"));
        effect.scaleEffect(0.2f);
        particleEffectPool = new ParticleEffectPool(effect, 1, 20);
        activeEffects = new Array<>();
    }

    public static ParticleManager getInstance() {
        if (instance == null) {
            instance = new ParticleManager();
        }
        return instance;
    }

    public void playEffect(float x, float y) {
        ParticleEffectPool.PooledEffect newEffect = particleEffectPool.obtain();
        newEffect.setPosition(x, y);
        newEffect.start();
        activeEffects.add(newEffect);
    }

    public void update(float delta) {
        for (int i = activeEffects.size - 1; i >= 0; i--) {
            ParticleEffectPool.PooledEffect effect = activeEffects.get(i);
            effect.update(delta);
            if (effect.isComplete()) {
                effect.free();
                activeEffects.removeIndex(i);
            }
        }
    }

    public void render(SpriteBatch spriteBatch) {
        for (ParticleEffectPool.PooledEffect effect : activeEffects) {
            GameScreen.spriteBatch.begin();
            effect.draw(spriteBatch);
            GameScreen.spriteBatch.end();
        }
    }

    public void dispose() {
        for (ParticleEffectPool.PooledEffect effect : activeEffects) {
            effect.free();
        }
        activeEffects.clear();
    }
}
