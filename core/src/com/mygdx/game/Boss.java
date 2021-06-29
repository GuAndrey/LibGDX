package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by Admin on 13.05.2018.
 */

public class Boss {
    final Game game;
    private Integer HP = 12;
    private float X, Y;
    boolean alive = true;

    private Vector3 v = new Vector3();
    public float SX, SY, nX, nY;
    private boolean imm = false;
    private boolean canAttack = true;

    public Model model;
    public ModelInstance instance;
    public ModelBatch modelBatch;

    public Sprite aim;

    public AssetManager assets;
    public boolean loading;

    float time1 = 0;
    float time2 = 0;

    //Для Андимации
    private static final int FRAME_COLS = 3;
    private static final int FRAME_ROWS = 1;

    Animation animation;
    Texture sheet;
    TextureRegion[] frames;
    SpriteBatch spriteBatch;
    TextureRegion currentFrame;
    Sound sound1;
    boolean anim;

    float stateTime;

    public Boss(final Game game){
        this.game = game;
        //Анимация
        sheet = new Texture(Gdx.files.internal("aim.png"));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/FRAME_COLS, sheet.getHeight()/FRAME_ROWS);
        frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(0.5f, frames);
        spriteBatch = new SpriteBatch();
        stateTime = 0f;
        anim =false;
        //модель Босса
        ModelBuilder modelBuilder = new ModelBuilder();

        assets = new AssetManager();
        assets.load("Dragon_Baked_Actions_fbx_7.4_binary.g3db", Model.class);
        loading = true;
        assets.finishLoading();
        if (loading)
            if (assets.update()) {
                model = assets.get("Dragon_Baked_Actions_fbx_7.4_binary.g3db", Model.class);
                instance = new ModelInstance(model);
                instance.transform.scale(0.005f, 0.005f, 0.005f);
                loading = false;
            } else {
                return;
            }
        X = -50;
        Y = -50;
        instance = new ModelInstance(model);
        instance.transform.setToLookAt(v.Y, v.X);
        instance.transform.scale(0.005f, 0.005f, 0.005f);
        modelBatch = new ModelBatch();
        nX=Gdx.graphics.getWidth()/2-50;
        nY=Gdx.graphics.getHeight()/2+50;
        instance.transform.setToTranslation(X, Y, 0);
        instance.transform.rotate(Vector3.X, 90);
        instance.transform.scale(0.005f, 0.005f, 0.005f);
        aim = new Sprite(new Texture("aim.png"));
        aim.setSize(aim.getWidth()*Game.dWidth, aim.getHeight()*Game.dHeight);

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

    public void movement(Camera cam) {
        nY = MathUtils.random(50,Gdx.graphics.getHeight()-50);
        nX = MathUtils.random(50,Gdx.graphics.getWidth()-50);
        aim.setPosition(nX,nY);
        System.out.println(nX + " " + nY);
        anim = true;
        nY=Gdx.graphics.getHeight()-nY;
        v.set(nX, nY, 0);
        cam.unproject(v);
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                X = v.x * 100;
                Y = v.y * 100;
                anim = false;
                stateTime = 0;
                instance.transform.setToTranslation(X, Y, 0);
                instance.transform.rotate(Vector3.X, 90);
                instance.transform.scale(0.005f, 0.005f, 0.005f);
            }
        }, 1.5f);

    }

    public void damage(FireBall fireBall) {
        if(fireBall.sprite.getBoundingRectangle().contains(nX, Gdx.graphics.getHeight()-nY)){
            setHP(HP - 1);
            fireBall.setNotExist();
        }
    }


    public Integer getHP() {
        return HP;
    }

    public void setHP(Integer HP) {
        this.HP = HP;
        if(HP <= 0){
           alive =false;
        }
    }
}
