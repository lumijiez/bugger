package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.weapons.Ray;

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
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Ray ray = Bugger.getInstance().getPlayer().shootArrow();
            Bugger.getInstance().getProjectiles().add(ray);
        }
    }
}
