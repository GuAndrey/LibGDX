package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by Admin on 30.04.2018.
 */

public class SmartEnemy extends Enemy {
    private boolean dies;
    private boolean isAlive;
    private float X, Y,vX, vY, deltaY, deltaX;
    private Sprite sprite;
    private Texture texture;
    private float speed = 2f;
    private float[] v = new float[2];
    Sound sound1;

    //Для Андимации
    private static final int FRAME_COLS = 5;
    private static final int FRAME_ROWS = 1;

    Texture sheet;
    TextureRegion[] frames;

    private Animation animation;
    private TextureRegion currentFrame;
    private float stateTime;

    private static final int FRAME_COLS2 = 4;
    private static final int FRAME_ROWS2 = 1;

    Texture sheet2;
    TextureRegion[] frames2;

    private Animation animation2;
    private TextureRegion currentFrame2;
    private float stateTime2;

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

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }
    public void setCurrentFrame(TextureRegion currentFrame) {
        this.currentFrame = currentFrame;
    }
    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
    public Animation getAnimation() {
        return animation;
    }
    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }
    public float getStateTime() {
        return stateTime;
    }


    public SmartEnemy(){
        //Анимация
        dies = false;
        sheet = new Texture(Gdx.files.internal("SMА.png"));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/FRAME_COLS, sheet.getHeight()/FRAME_ROWS);
        frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(0.25f, frames);
        stateTime = 0f;

        //Анимация Смерти
        sheet2 = new Texture(Gdx.files.internal("SMАnim.png"));
        TextureRegion[][] tmp2 = TextureRegion.split(sheet2, sheet2.getWidth()/FRAME_COLS2, sheet2.getHeight()/FRAME_ROWS2);
        frames2 = new TextureRegion[FRAME_COLS2 * FRAME_ROWS2];
        int index2 = 0;
        for (int i = 0; i < FRAME_ROWS2; i++) {
            for (int j = 0; j < FRAME_COLS2; j++) {
                frames2[index2++] = tmp2[i][j];
            }
        }
        animation2 = new Animation(0.25f, frames2);
        stateTime2 = 0f;

        //Звук
        sound1 = Gdx.audio.newSound(Gdx.files.internal("EDie.wav"));

        isAlive = true;
        sprite = new Sprite(new Texture("SM.png"));
        X= MathUtils.random(Gdx.graphics.getWidth());
        Y=Gdx.graphics.getHeight()-sprite.getHeight();
        sprite.setPosition(X, Y);
        sprite.setSize(sprite.getWidth()*Game.dWidth, sprite.getHeight()*Game.dHeight);

    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    public boolean isAlive() {
        return isAlive;
    }
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isDies() {
        return dies;
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

    @Override
    public void move(Hero hero) {
        vX = hero.SX - X;
        vY = hero.SY - Y;
        vX = (float) (vX / Math.sqrt(vX*vX + vY*vY));
        vY = (float) (vY / Math.sqrt(vX*vX + vY*vY));
        deltaY = vY * 2;
        deltaX = vX * 2;
        Y += deltaY;
        X += deltaX;
        /*
        if (hero.SX > X) X+=1.5;
        else if (hero.SX < X) X-=1.5;
        if (hero.SY > Y) Y+=1.5;
        else if (hero.SY < Y) Y-=1.5;
        */

        sprite.setPosition(X,Y);
    }

    @Override
    public void death(FireBall fireBall) {
        if(sprite.getBoundingRectangle().overlaps(fireBall.sprite.getBoundingRectangle())){
            dies = true;
            stateTime2 += Gdx.graphics.getDeltaTime();
            currentFrame2 = (TextureRegion) animation2.getKeyFrame(stateTime2, false);
            sprite.setRegion(currentFrame2);

            if(Game.sound)
                sound1.play(0.1f);

            Timer timer = new Timer();
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {

                    isAlive = false;
                }
            }, 1);
            fireBall.setNotExist();
        }

    }

    public void attack(Hero hero){
        if(sprite.getBoundingRectangle().contains(hero.SX, hero.SY) &&  !hero.isImm()){
            hero.setHP(hero.getHP()-1);
            hero.setImm(true);
        }
    }
}
