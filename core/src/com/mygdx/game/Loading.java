package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Timer;

/**
 * Created by Admin on 18.05.2018.
 */

public class Loading {

    private Sprite sprite;

    float stateTime;
    public Loading(){
        sprite = new Sprite(new Texture("loading2.png"));
        sprite.setSize(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/15);
        sprite.setPosition(Gdx.graphics.getWidth()/2-sprite.getWidth()/2,10);

    }

    public  void drawing(SpriteBatch spriteBatch){
        sprite.draw(spriteBatch);
    }

}
