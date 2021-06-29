package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Admin on 13.05.2018.
 */

public class BossRoom extends Room {

    private Sprite background;
    private boolean isComplete;

    public BossRoom(){
        isComplete = false;
        int r = MathUtils.random(4) + 1;
        background = new Sprite(new Texture("BossBack.jpg"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    public boolean isComplete() {
        return isComplete;
    }

    public void complete(Boss boss){
        if(!boss.alive){
            isComplete = true;
        }
    }

    public Sprite getBackground() {
        return background;
    }

    public void drawBack(SpriteBatch spriteBatch){

        spriteBatch.begin();
        background.draw(spriteBatch);
        spriteBatch.end();
    }
}
