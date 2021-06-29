package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;


/**
 * Created by Admin on 21.04.2018.
 */

public class Game extends com.badlogic.gdx.Game {
    static boolean music, sound;
    final static int height = 410;
    final static int width = 640;
    int newHeight;
    int newWidth;
    static int dWidth;
    static int dHeight;
    AssetManager assetManager;
    @Override
    public void create() {
        newWidth = Gdx.graphics.getWidth();
        newHeight = Gdx.graphics.getHeight();
        dHeight = newHeight / height;
        dWidth = newWidth / width;
        assetManager = new AssetManager();

        music = true;
        sound = true;
        this.setScreen(new MainMenu(this));
    }
    public void render() {
        super.render();
    }
    public void dispose() {
        super.dispose();
    }
}
