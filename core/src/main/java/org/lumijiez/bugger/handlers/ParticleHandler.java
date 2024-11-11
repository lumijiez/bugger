package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.player.Player;

public class ParticleHandler {
    private static ParticleHandler instance;
    private final ParticleEffectPool bigBoomPool;
    private final ParticleEffectPool smallBoomPool;
    private final ParticleEffectPool logoPool;
    private final ParticleEffectPool hitPool;
    private final Array<ParticleEffectPool.PooledEffect> activeEffects;
    private final Array<ParticleEffectPool.PooledEffect> uiEffects;
    private final ObjectMap<ParticleEffectPool.PooledEffect, String> effectTypes;

    private ParticleHandler() {
        ParticleEffect bigBoomEffect = new ParticleEffect();
        bigBoomEffect.load(Gdx.files.internal("particles/boom.p"), Gdx.files.internal("particles"));
        bigBoomEffect.scaleEffect(0.3f);

        ParticleEffect smallBoomEffect = new ParticleEffect();
        smallBoomEffect.load(Gdx.files.internal("particles/boom.p"), Gdx.files.internal("particles"));
        smallBoomEffect.scaleEffect(0.1f);

        ParticleEffect logoEffect = new ParticleEffect();
        logoEffect.load(Gdx.files.internal("particles/logo.p"), Gdx.files.internal("particles"));
        logoEffect.scaleEffect(3f);

        ParticleEffect hitEffect = new ParticleEffect();
        hitEffect.load(Gdx.files.internal("particles/hit.p"), Gdx.files.internal("particles"));
        hitEffect.scaleEffect(1.5f);
        hitEffect.setDuration(1000);

        bigBoomPool = new ParticleEffectPool(bigBoomEffect, 1, 20);
        smallBoomPool = new ParticleEffectPool(smallBoomEffect, 1, 20);
        logoPool = new ParticleEffectPool(logoEffect, 1, 5);
        hitPool = new ParticleEffectPool(hitEffect, 1, 1);
        activeEffects = new Array<>();
        uiEffects = new Array<>();
        effectTypes = new ObjectMap<>();
    }

    public static ParticleHandler getInstance() {
        if (instance == null) {
            instance = new ParticleHandler();
        }
        return instance;
    }

    public void playBigBoom(float x, float y) {
        ParticleEffectPool.PooledEffect newEffect = bigBoomPool.obtain();
        newEffect.setPosition(x, y);
        newEffect.start();
        activeEffects.add(newEffect);
        effectTypes.put(newEffect, "bigBoom");
    }

    public void playSmallBoom(float x, float y) {
        ParticleEffectPool.PooledEffect newEffect = smallBoomPool.obtain();
        newEffect.setPosition(x, y);
        newEffect.start();
        activeEffects.add(newEffect);
        effectTypes.put(newEffect, "smallBoom");
    }

    public void playLogo(float x, float y) {
        ParticleEffectPool.PooledEffect newEffect = logoPool.obtain();
        newEffect.setPosition(x, y);
        newEffect.start();
        uiEffects.add(newEffect);
        effectTypes.put(newEffect, "logoEffect");
    }

    public void playHit(float x, float y) {
        ParticleEffectPool.PooledEffect newEffect = hitPool.obtain();
        newEffect.setPosition(x, y);
        newEffect.start();
        uiEffects.add(newEffect);
        effectTypes.put(newEffect, "hitEffect");
    }

    public void update(float delta) {
        for (int i = activeEffects.size - 1; i >= 0; i--) {
            ParticleEffectPool.PooledEffect effect = activeEffects.get(i);
            effect.update(delta);

            String effectType = effectTypes.get(effect);
            if ("bigBoom".equals(effectType)) {
                // ToDo
            } else if ("smallBoom".equals(effectType)) {
                effect.setPosition(Player.getInstance().getPosition().x,
                    Player.getInstance().getPosition().y);
            }

            if (effect.isComplete()) {
                effect.free();
                activeEffects.removeIndex(i);
            }
        }

        for (int i = uiEffects.size - 1; i >= 0; i--) {
            ParticleEffectPool.PooledEffect effect = uiEffects.get(i);
            effect.update(delta);

            if (effect.isComplete()) {
                effect.free();
                uiEffects.removeIndex(i);
            }
        }
    }

    public void render(SpriteBatch spriteBatch) {
        for (ParticleEffectPool.PooledEffect effect : activeEffects) {
            Bugger.spriteBatch.begin();
            effect.draw(spriteBatch);
            Bugger.spriteBatch.end();
        }

        for (ParticleEffectPool.PooledEffect effect : uiEffects) {
            Bugger.uiBatch.begin();
            effect.draw(Bugger.uiBatch);
            Bugger.uiBatch.end();
        }
    }

    public void dispose() {
        for (ParticleEffectPool.PooledEffect effect : activeEffects) {
            effect.free();
            effectTypes.remove(effect);
        }
        activeEffects.clear();
    }

    public void cycle(float delta) {
        update(delta);
        render(Bugger.spriteBatch);
    }
}
