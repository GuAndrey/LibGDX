package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Admin on 18.04.2018.
 */

public abstract class Enemy {
    private boolean dies;
    private boolean isAlive;
    private float X, Y;
    private Sprite sprite;
    private Texture texture;

    public boolean isDies() {
        return dies;
    }

    public boolean isAlive() {
        return isAlive;
    }
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public float getX() {
        return X;
    }
    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }
    public void setY(float y) {Y = y;}

    public Sprite getSprite() {
        return this.sprite;
    }

    public abstract void move(Hero hero);

    //Для Андимации
    private Animation animation;
    private TextureRegion currentFrame;
    private Animation animation2;
    private TextureRegion currentFrame2;
    private float stateTime2;

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }
    public void setCurrentFrame(TextureRegion currentFrame) {
        this.currentFrame = currentFrame;
    }
    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
    private float stateTime;
    public Animation getAnimation() {
        return animation;
    }
    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }
    public float getStateTime() {
        return stateTime;
    }

    public Animation getAnimation2() {
        return animation2;
    }
    public void setAnimation2(Animation animation2) {
        this.animation2 = animation2;
    }
    public TextureRegion getCurrentFrame2() {
        return currentFrame2;
    }
    public void setCurrentFrame2(TextureRegion currentFrame2) {
        this.currentFrame2 = currentFrame2;
    }
    public float getStateTime2() {
        return stateTime2;
    }
    public void setStateTime2(float stateTime2) {
        this.stateTime2 = stateTime2;
    }

    public void attack(Hero hero){
        if(sprite.getBoundingRectangle().contains(hero.SX, hero.SY) &&  !hero.isImm()){
            hero.setHP(hero.getHP()-1);
            hero.setImm(true);
        }
    }

    public void death(FireBall fireBall){
        if(sprite.getBoundingRectangle().overlaps(fireBall.sprite.getBoundingRectangle())){
            fireBall.setNotExist();
            isAlive = false;}
    }

}
