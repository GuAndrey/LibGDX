package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Admin on 21.04.2018.
 */

public class Room {
    private Enemy[] enemies;
    private Sprite background;
    private boolean isComplet;
    public Room(){
        isComplet = false;
        int r = MathUtils.random(4) + 1;
        background = new Sprite(new Texture("Background" + r + ".jpg"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        enemies = new Enemy[5];
        for (int i = 0; i < 5; i++) {
            int random = MathUtils.random(4);
            if (random == 0) enemies[i] = new SmartEnemy();
            else enemies[i] = new SimpleEnemy();
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

    public boolean isComplete() {
        return isComplet;
    }

    public void complet(){
        int a = 0;
        for (int i = 0; i < enemies.length; i++) {
            if (!enemies[i].isAlive()){
                a++;
            }
        }
        if (a == enemies.length) isComplet = true;
    }

    public void drawEnemies(SpriteBatch spriteBatch, FireBall fireBall, Hero hero){
        try {
            for (int i = 0; i < enemies.length; i++) {
                if(enemies[i].isAlive()) {

                    if(!enemies[i].isDies()) {
                        enemies[i].setStateTime(enemies[i].getStateTime() + Gdx.graphics.getDeltaTime());
                        enemies[i].setCurrentFrame((TextureRegion) enemies[i].getAnimation().getKeyFrame(enemies[i].getStateTime(), true));
                        enemies[i].getSprite().setRegion(enemies[i].getCurrentFrame());

                        spriteBatch.begin();
                        enemies[i].getSprite().draw(spriteBatch);
                        enemies[i].move(hero);
                        enemies[i].attack(hero);
                        if(fireBall!=null) enemies[i].death(fireBall);
                        spriteBatch.end();

                    } else{
                        enemies[i].setStateTime2(enemies[i].getStateTime2() + Gdx.graphics.getDeltaTime());
                        enemies[i].setCurrentFrame2((TextureRegion) enemies[i].getAnimation2().getKeyFrame(enemies[i].getStateTime2(), false));
                        enemies[i].getSprite().setRegion(enemies[i].getCurrentFrame2());
                        enemies[i].getSprite().setScale(0.75f);
                        spriteBatch.begin();
                        enemies[i].getSprite().draw(spriteBatch);
                        spriteBatch.end();
                    }

                }
            }
       } catch (Exception ex){
            spriteBatch.end();
            System.out.println(ex);
        }
    }
}
