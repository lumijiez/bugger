package org.lumijiez.bugger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class SpaceBackground {
    private Texture starTexture;
    private Texture galaxyTexture;
    private Texture nebulaTexture;

    private float[] starPositionsX;
    private float[] starPositionsY;
    private int numStars = 1000; // Number of stars to generate

    private float galaxyScrollSpeed = 0.1f; // Galaxy scroll speed
    private float nebulaScrollSpeed = 0.05f; // Nebula scroll speed
    private float starScrollSpeed = 0.2f; // Star scroll speed

    private float galaxyOffset = 0;
    private float nebulaOffset = 0;

    public SpaceBackground() {
        starTexture = new Texture(Gdx.files.internal("images/star.png"));
        galaxyTexture = new Texture(Gdx.files.internal("images/galaxy.png"));
        nebulaTexture = new Texture(Gdx.files.internal("images/nebula.png"));

        starPositionsX = new float[numStars];
        starPositionsY = new float[numStars];

        for (int i = 0; i < numStars; i++) {
            starPositionsX[i] = MathUtils.random(0, Gdx.graphics.getWidth());
            starPositionsY[i] = MathUtils.random(0, Gdx.graphics.getHeight());
        }
    }

    public void render() {
        GameScreen.spriteBatch.begin();

        // Draw galaxies with parallax effect
        drawGalaxies();

        // Draw nebulae with parallax effect
        drawNebulae();

        // Draw star particles
        drawStars();

        GameScreen.spriteBatch.end();

        // Update offsets for parallax scrolling
        updateOffsets();
    }

    private void drawStars() {
        float starScale = 0.1f; // Scale factor for stars
        for (int i = 0; i < numStars; i++) {
            // Draw the star with scaling
            GameScreen.spriteBatch.draw(starTexture, starPositionsX[i], starPositionsY[i],
                starTexture.getWidth() * starScale / 2, // Origin X
                starTexture.getHeight() * starScale / 2, // Origin Y
                starTexture.getWidth() * starScale,      // Width
                starTexture.getHeight() * starScale,     // Height
                1,                                        // Scale X
                1                                        // Scale Y
            );                                       // Rotation
        }
    }

    private void drawGalaxies() {
        float screenHeight = Gdx.graphics.getHeight();
        float galaxyY = (float) (Gdx.graphics.getHeight() - galaxyTexture.getHeight()) / 2 + galaxyOffset;

        // Draw the galaxy texture
        GameScreen.spriteBatch.draw(galaxyTexture, 0, galaxyY);
        GameScreen.spriteBatch.draw(galaxyTexture, 0, galaxyY + galaxyTexture.getHeight());
    }

    private void drawNebulae() {
        float screenHeight = Gdx.graphics.getHeight();
        float nebulaY = (float) (Gdx.graphics.getHeight() - nebulaTexture.getHeight()) / 2 + nebulaOffset;

        // Draw the nebula texture
        GameScreen.spriteBatch.draw(nebulaTexture, 0, nebulaY);
        GameScreen.spriteBatch.draw(nebulaTexture, 0, nebulaY + nebulaTexture.getHeight());
    }

    private void updateOffsets() {
        // Update offsets for scrolling
        galaxyOffset -= galaxyScrollSpeed; // Move galaxy layer up
        nebulaOffset -= nebulaScrollSpeed; // Move nebula layer up

        // Reset offset if it goes out of bounds
        if (galaxyOffset <= -galaxyTexture.getHeight()) {
            galaxyOffset = 0;
        }
        if (nebulaOffset <= -nebulaTexture.getHeight()) {
            nebulaOffset = 0;
        }
    }

    public void dispose() {
        starTexture.dispose();
        galaxyTexture.dispose();
        nebulaTexture.dispose();
    }
}
