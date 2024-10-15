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

    private int numStars = 10000; // Number of stars to generate

    // Speeds for different layers
    private float galaxyScrollSpeedX = 0.2f; // Galaxy horizontal scroll speed
    private float galaxyScrollSpeedY = 0.2f; // Galaxy vertical scroll speed
    private float nebulaScrollSpeedX = 0.025f; // Nebula horizontal scroll speed
    private float nebulaScrollSpeedY = 0.025f; // Nebula vertical scroll speed
    private float starScrollSpeedX = 0.5f; // Star horizontal scroll speed
    private float starScrollSpeedY = 0.5f; // Star vertical scroll speed

    public SpaceBackground() {
        starTexture = new Texture(Gdx.files.internal("images/star.png"));
        galaxyTexture = new Texture(Gdx.files.internal("images/galaxy.png"));
        nebulaTexture = new Texture(Gdx.files.internal("images/nebula.png"));

        starPositionsX = new float[numStars];
        starPositionsY = new float[numStars];

        // Randomly place stars within a larger range to create dispersion
        for (int i = 0; i < numStars; i++) {
            // Increase the range to make stars more dispersed
            starPositionsX[i] = MathUtils.random(-Gdx.graphics.getWidth() * 10, Gdx.graphics.getWidth() * 10);
            starPositionsY[i] = MathUtils.random(-Gdx.graphics.getHeight() * 10, Gdx.graphics.getHeight() * 10);
        }
    }

    public void render() {
        GameScreen.spriteBatch.begin();

        // Draw galaxies with parallax effect
        drawGalaxies();

        // Draw nebulae with parallax effect
        drawNebulae();

        // Draw star particles with parallax effect
        drawStars();

        GameScreen.spriteBatch.end();
    }

    private void drawStars() {
        float cameraX = GameScreen.cam.position.x;
        float cameraY = GameScreen.cam.position.y;

        // Adjust the star positions based on the camera's position
        for (int i = 0; i < numStars; i++) {
            // Calculate new positions based on camera movement
            float starX = starPositionsX[i] - cameraX * starScrollSpeedX; // Horizontal parallax
            float starY = starPositionsY[i] - cameraY * starScrollSpeedY; // Vertical parallax

            // Add a slight random offset to the star's position
            float offsetX = MathUtils.random(-2f, 2f); // Change the offset range as needed
            float offsetY = MathUtils.random(-2f, 2f); // Change the offset range as needed

            // Define a scale factor for the stars
            float starScale = 0.1f; // Adjust this value to make stars smaller or larger

            // Calculate scaled dimensions
            float scaledWidth = starTexture.getWidth() * starScale;
            float scaledHeight = starTexture.getHeight() * starScale;

            // Draw the star with the correct parameters
            GameScreen.spriteBatch.draw(starTexture,
                starX + offsetX,        // X position
                starY + offsetY,        // Y position
                scaledWidth,                      // Origin X
                scaledHeight,                      // Origin Y
                0,           // Scaled Width
                0,          // Scaled Height
                1,                      // Scale X (since we're already scaling)
                1                       // Scale Y (since we're already scaling)
            );
        }
    }

    private void drawGalaxies() {
        float cameraX = GameScreen.cam.position.x;
        float cameraY = GameScreen.cam.position.y;

        // Determine Y positions for galaxies based on camera position and scroll speed
        float galaxyY1 = (cameraY * galaxyScrollSpeedY) % galaxyTexture.getHeight() - galaxyTexture.getHeight();
        float galaxyY2 = galaxyY1 + galaxyTexture.getHeight(); // Draw a second galaxy texture for seamless scrolling

        // Draw galaxies with horizontal offset
        float galaxyX1 = -cameraX * galaxyScrollSpeedX; // Horizontal position based on camera
        float galaxyX2 = galaxyX1 + galaxyTexture.getWidth();

        // Draw the galaxy texture
        GameScreen.spriteBatch.draw(galaxyTexture, galaxyX1, galaxyY1);
        GameScreen.spriteBatch.draw(galaxyTexture, galaxyX2, galaxyY1);
    }

    private void drawNebulae() {
        float cameraX = GameScreen.cam.position.x;
        float cameraY = GameScreen.cam.position.y;

        // Determine Y positions for nebulae based on camera position and scroll speed
        float nebulaY1 = (cameraY * nebulaScrollSpeedY) % nebulaTexture.getHeight() - nebulaTexture.getHeight();
        float nebulaY2 = nebulaY1 + nebulaTexture.getHeight(); // Draw a second nebula texture for seamless scrolling

        // Draw nebulae with horizontal offset
        float nebulaX1 = -cameraX * nebulaScrollSpeedX; // Horizontal position based on camera
        float nebulaX2 = nebulaX1 + nebulaTexture.getWidth();

        // Draw the nebula texture
        GameScreen.spriteBatch.draw(nebulaTexture, nebulaX1, nebulaY1);
        GameScreen.spriteBatch.draw(nebulaTexture, nebulaX2, nebulaY1);
    }

    public void dispose() {
        starTexture.dispose();
        galaxyTexture.dispose();
        nebulaTexture.dispose();
    }
}
