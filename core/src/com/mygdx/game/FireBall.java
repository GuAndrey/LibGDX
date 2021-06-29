package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Admin on 15.04.2018.
 */

public class FireBall {
    private float degrees;
    public float X;
    public float Y;
    Sprite sprite;
    private boolean isExict;

    private float vX;
    private float vY;
    private float deltaX, deltaY;
    private Vector3 v = new Vector3();

    //Для Андимации
    private static final int FRAME_COLS = 5;
    private static final int FRAME_ROWS = 1;

    Animation animation;
    Texture sheet;
    TextureRegion[] frames;
    SpriteBatch spriteBatch;
    TextureRegion currentFrame;
    Sound sound1;

    float stateTime;

    public FireBall(float x, float y) {
        //Звук
        sound1 = Gdx.audio.newSound(Gdx.files.internal("Fireball1.wav"));

        //Анимация
        sheet = new Texture(Gdx.files.internal("fireballz.png"));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/FRAME_COLS, sheet.getHeight()/FRAME_ROWS);
        frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(0.25f, frames);
        spriteBatch = new SpriteBatch();
        stateTime = 0f;




        isExict = true;
        X = x;
        Y = y;
        sprite = new Sprite(new Texture("fireball.png"));
        sprite.setSize(sprite.getWidth()*Game.dWidth, sprite.getHeight()*Game.dHeight);
        sprite.scale(1);

        sprite.setPosition(X - sprite.getWidth()/2, Y - sprite.getHeight()/2);
        spriteBatch = new SpriteBatch();
        if(Game.sound)
            sound1.play(0.2f);
    }


    public void setDelta(Integer x, Integer y, Hero hero) {
        y = Gdx.graphics.getHeight() - y;
        vX = x - X;
        vY = y - Y;
        deltaY = vY / 100;
        deltaX = vX / 100;
        degrees = (float) Math.toDegrees(Math.atan( vY/vX ));
        if(vX<0){
            sprite.rotate(180 + degrees);
            hero.instance.transform.setToTranslation(hero.getX(), hero.getY(), 0);
            hero.instance.transform.rotate(Vector3.X, 90);
            hero.instance.transform.rotate(Vector3.Y,270+ degrees);
            hero.instance.transform.scale(0.01f, 0.01f, 0.01f);
        } else {
            sprite.rotate(degrees);
            hero.instance.transform.setToTranslation(hero.getX(), hero.getY(), 0);
            hero.instance.transform.rotate(Vector3.X, 90);
            hero.instance.transform.rotate(Vector3.Y,90+ degrees);
            hero.instance.transform.scale(0.01f, 0.01f, 0.01f);
        }
    }

    public void move() {
            Y += deltaY;
            X += deltaX;
            sprite.setPosition(X - sprite.getWidth()/2, Y - sprite.getHeight()/2);
        }

    public void setNotExist(){
        isExict = false;
    }

    public boolean isExict() {
        return isExict;
    }
}