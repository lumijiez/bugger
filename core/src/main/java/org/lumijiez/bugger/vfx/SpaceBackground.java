package org.lumijiez.bugger.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import org.lumijiez.bugger.Bugger;

public class SpaceBackground {
    private final Texture starTexture;
    private final Texture galaxyTexture;
    private final Texture nebulaTexture;
    private final float[] starPositionsX;
    private final float[] starPositionsY;
    private final int numStars = 10000;

    public SpaceBackground() {
        starTexture = new Texture(Gdx.files.internal("images/star.png"));
        galaxyTexture = new Texture(Gdx.files.internal("images/galaxy.png"));
        nebulaTexture = new Texture(Gdx.files.internal("images/nebula.png"));

        starPositionsX = new float[numStars];
        starPositionsY = new float[numStars];

        for (int i = 0; i < numStars; i++) {
            starPositionsX[i] = MathUtils.random(-Gdx.graphics.getWidth() * 10, Gdx.graphics.getWidth() * 10);
            starPositionsY[i] = MathUtils.random(-Gdx.graphics.getHeight() * 10, Gdx.graphics.getHeight() * 10);
        }
    }

    public void render() {
        Bugger.getInstance().batch().begin();

        float cameraX = Bugger.cam.position.x;
        float cameraY = Bugger.cam.position.y;

        drawGalaxies(cameraX, cameraY);
        drawNebulae(cameraX, cameraY);
        drawStars(cameraX, cameraY);

        Bugger.getInstance().batch().end();
    }

    private void drawStars(float cameraX, float cameraY) {

        for (int i = 0; i < numStars; i++) {
            float starScrollSpeedX = 0.5f;
            float starX = starPositionsX[i] - cameraX * starScrollSpeedX;
            float starScrollSpeedY = 0.5f;
            float starY = starPositionsY[i] - cameraY * starScrollSpeedY;

            float offsetX = MathUtils.random(-2f, 2f);
            float offsetY = MathUtils.random(-2f, 2f);

            float starScale = 0.1f;

            float scaledWidth = starTexture.getWidth() * starScale;
            float scaledHeight = starTexture.getHeight() * starScale;

            Bugger.spriteBatch.draw(starTexture,
                starX + offsetX,
                starY + offsetY,
                scaledWidth,
                scaledHeight,
                0,
                0,
                1,
                1
            );
        }
    }

    private void drawGalaxies(float cameraX, float cameraY) {
        float galaxyScrollSpeedY = 0.2f;
        float galaxyY1 = (cameraY * galaxyScrollSpeedY) % galaxyTexture.getHeight() - galaxyTexture.getHeight();
        // float galaxyY2 = galaxyY1 + galaxyTexture.getHeight();

        float galaxyScrollSpeedX = 0.2f;
        float galaxyX1 = -cameraX * galaxyScrollSpeedX;
        float galaxyX2 = galaxyX1 + galaxyTexture.getWidth();

        Bugger.spriteBatch.draw(galaxyTexture, galaxyX1, galaxyY1);
        Bugger.spriteBatch.draw(galaxyTexture, galaxyX2, galaxyY1);
    }

    private void drawNebulae(float cameraX, float cameraY) {
        float nebulaScrollSpeedY = 0.025f;
        float nebulaY1 = (cameraY * nebulaScrollSpeedY) % nebulaTexture.getHeight() - nebulaTexture.getHeight();
        // float nebulaY2 = nebulaY1 + nebulaTexture.getHeight();

        float nebulaScrollSpeedX = 0.025f;
        float nebulaX1 = -cameraX * nebulaScrollSpeedX;
        float nebulaX2 = nebulaX1 + nebulaTexture.getWidth();

        Bugger.spriteBatch.draw(nebulaTexture, nebulaX1, nebulaY1);
        Bugger.spriteBatch.draw(nebulaTexture, nebulaX2, nebulaY1);
    }

    public void dispose() {
        starTexture.dispose();
        galaxyTexture.dispose();
        nebulaTexture.dispose();
    }
}
