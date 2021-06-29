package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BaseJsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Timer;

import java.io.InputStream;

/**
 * Created by Admin on 11.04.2018.
 */

public class Hero {
    final Game game;
    private Integer HP;
    private Integer maxHP;
    private boolean isAttack = false;
    private float X, Y;
    private AssetManager assets;

    private Vector3 v = new Vector3();
    public float SX, SY;
    private boolean imm = false;
    private boolean canAttack = true;

    public Model model;
    public ModelInstance instance;


    public boolean loading;

    public Hero(final Game game){
        HP = 3;
        maxHP = 3;
        this.game = game;
        //модель будущего мага
        ModelBuilder modelBuilder = new ModelBuilder();

        assets = new AssetManager();
        assets.load("123.g3db", Model.class);
        loading = true;
        assets.finishLoading();
        if (loading)
            if (assets.update()) {
                model = assets.get("123.g3db", Model.class);
                instance = new ModelInstance(model);
                instance.transform.scale(0.01f, 0.01f, 0.01f);
                loading = false;
            } else {
                return;
            }

      //  model = this.game.assetManager.get("123.g3db", Model.class);
        SX = Gdx.graphics.getWidth()/2;
        SY = Gdx.graphics.getHeight()/2;
        instance = new ModelInstance(model);
        instance.transform.setToLookAt(v.Y, v.X);
        instance.transform.scale(0.01f, 0.01f, 0.01f);

    }

    public boolean isImm() {
        return imm;
    }

    public void setImm(boolean imm) {
        if(imm==true){
            this.imm = true;
            Timer timer = new Timer();
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    fImm();
                }
            }, 1);
        }
    }
    private void fImm(){
        imm = false;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        if(canAttack==false){
            this.canAttack = false;
            Timer timer = new Timer();
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    cat();
                }
            }, 1);
        }
    }
    private void cat(){
        canAttack = true;
    }

    public float getX() {
        return X;
    }

    public float getY() {
        return Y;
    }

    public void setX(float x) {
        X = x;
    }

    public void setY(float y) {
        Y = y;
    }


    public void movement(int x, int y, Camera cam) {
        SX = x;
        SY = Gdx.graphics.getHeight() - y;
        v.set(x, y, 0);
        cam.unproject(v);
        X = v.x * 100;
        Y = v.y * 100;
        instance.transform.setToTranslation(X, Y, 0);
        instance.transform.rotate(Vector3.X, 90);
        instance.transform.scale(0.01f, 0.01f, 0.01f);
    }

    public Integer getHP() {
        return HP;
    }

    public void setHP(Integer HP) {
        if(HP<=maxHP)
        this.HP = HP;
        if(HP <= 0){
            try {
                BossFightScreen.theme.stop();
            }catch (Exception ex){}
            try {
            GameScreen.theme.stop();
            }catch (Exception ex){}
            game.setScreen(new  GameOverScreen(game));
        }
    }

    public Integer getMaxHP() {
        return maxHP;
    }
    public void setMaxHP(Integer maxHP) {
        this.maxHP = maxHP;
    }

    public boolean isAttack() {
        return isAttack;
    }

    public void setAttack(boolean attack) {
        isAttack = attack;
    }


}
