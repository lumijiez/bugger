package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import org.lumijiez.bugger.Bugger;

public class SpaceVFXHandler {
    private static SpaceVFXHandler instance;

    public static SpaceVFXHandler getInstance() {
        if (instance == null) {
            instance = new SpaceVFXHandler();
        }
        return instance;
    }

    private final Texture starTexture;
    private final Texture galaxyTexture;
    private final Texture nebulaTexture;
    private final float[] starPositionsX;
    private final float[] starPositionsY;
    private final float[] galaxyPositionsX;
    private final float[] galaxyPositionsY;
    private final float[] nebulaPositionsX;
    private final float[] nebulaPositionsY;
    private final int numStars = 20000;
    private final int numGalaxies = 20;
    private final int numNebulae = 20;
    private final float scaleFactor = 0.1f;

    private SpaceVFXHandler() {
        starTexture = new Texture(Gdx.files.internal("images/star.png"));
        galaxyTexture = new Texture(Gdx.files.internal("images/galaxy.png"));
        nebulaTexture = new Texture(Gdx.files.internal("images/nebula.png"));

        starPositionsX = new float[numStars];
        starPositionsY = new float[numStars];
        galaxyPositionsX = new float[numGalaxies];
        galaxyPositionsY = new float[numGalaxies];
        nebulaPositionsX = new float[numNebulae];
        nebulaPositionsY = new float[numNebulae];

        for (int i = 0; i < numStars; i++) {
            starPositionsX[i] = MathUtils.random(-Gdx.graphics.getWidth() * 2, Gdx.graphics.getWidth() * 2);
            starPositionsY[i] = MathUtils.random(-Gdx.graphics.getHeight() * 2, Gdx.graphics.getHeight() * 2);
        }

        for (int i = 0; i < numGalaxies; i++) {
            galaxyPositionsX[i] = MathUtils.random(-Gdx.graphics.getWidth() * 2, Gdx.graphics.getWidth() * 2);
            galaxyPositionsY[i] = MathUtils.random(-Gdx.graphics.getHeight() * 2, Gdx.graphics.getHeight() * 2);
        }

        for (int i = 0; i < numNebulae; i++) {
            nebulaPositionsX[i] = MathUtils.random(-Gdx.graphics.getWidth() * 2, Gdx.graphics.getWidth() * 2);
            nebulaPositionsY[i] = MathUtils.random(-Gdx.graphics.getHeight() * 2, Gdx.graphics.getHeight() * 2);
        }
    }

    public void render() {
        Bugger.spriteBatch.begin();

        float cameraX = CameraHandler.getInstance().getCamera().position.x;
        float cameraY = CameraHandler.getInstance().getCamera().position.y;

        drawStars(cameraX, cameraY);
        drawGalaxies(cameraX, cameraY);
        drawNebulae(cameraX, cameraY);

        Bugger.spriteBatch.end();
    }

    private void drawStars(float cameraX, float cameraY) {
        for (int i = 0; i < numStars; i++) {
            float starScrollSpeed = 0.5f;
            float starX = starPositionsX[i] - (cameraX * starScrollSpeed);
            float starY = starPositionsY[i] - (cameraY * starScrollSpeed);

            float scaledWidth = starTexture.getWidth() * scaleFactor / 5;
            float scaledHeight = starTexture.getHeight() * scaleFactor / 5;

            Bugger.spriteBatch.draw(starTexture, starX, starY, scaledWidth, scaledHeight);
        }
    }

    private void drawGalaxies(float cameraX, float cameraY) {
        for (int i = 0; i < numGalaxies; i++) {
            float galaxyScrollSpeed = 0.1f;
            float galaxyX = galaxyPositionsX[i] - (cameraX * galaxyScrollSpeed);
            float galaxyY = galaxyPositionsY[i] - (cameraY * galaxyScrollSpeed);

            float scaledGalaxyWidth = galaxyTexture.getWidth() * scaleFactor * 2;
            float scaledGalaxyHeight = galaxyTexture.getHeight() * scaleFactor * 2;

            Bugger.spriteBatch.draw(galaxyTexture, galaxyX, galaxyY, scaledGalaxyWidth, scaledGalaxyHeight);
        }
    }

    private void drawNebulae(float cameraX, float cameraY) {
        for (int i = 0; i < numNebulae; i++) {
            float nebulaScrollSpeed = 0.05f;
            float nebulaX = nebulaPositionsX[i] - (cameraX * nebulaScrollSpeed);
            float nebulaY = nebulaPositionsY[i] - (cameraY * nebulaScrollSpeed);

            float scaledNebulaWidth = nebulaTexture.getWidth() * scaleFactor * 1.5f;
            float scaledNebulaHeight = nebulaTexture.getHeight() * scaleFactor * 1.5f;

            Bugger.spriteBatch.draw(nebulaTexture, nebulaX, nebulaY, scaledNebulaWidth, scaledNebulaHeight);
        }
    }

    public void dispose() {
        starTexture.dispose();
        galaxyTexture.dispose();
        nebulaTexture.dispose();
    }
}
