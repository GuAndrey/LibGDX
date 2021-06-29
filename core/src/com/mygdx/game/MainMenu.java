package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;

import java.awt.Button;

import javax.swing.Action;


/**
 * Created by Admin on 21.04.2018.
 */

public class MainMenu extends InputAdapter implements Screen {
    final Game game;
    Sprite btnStart, back, btnMusic, btnSound;
    Texture btnTEXMusic, btnTEXSound;
    TextureRegion musON, musOFF, sounON, sounOFF;
    SpriteBatch spriteBatch;
    Run run;
    Loading loading;
    boolean loadingFinish=false, isLoading=false;

    static Music mainTheme;

    public MainMenu(final Game game) {
        loading = new Loading();
        this.game = game;
        //Музыка
        mainTheme = Gdx.audio.newMusic(Gdx.files.internal("MainTheme.mp3"));
        mainTheme.setVolume(0.1f);
        mainTheme.setLooping(true);
        if(Game.music)
        mainTheme.play();

        //Кнопка музыки
        btnTEXMusic = new Texture("Music.png");
        musON = new TextureRegion(btnTEXMusic, btnTEXMusic.getWidth()/2, btnTEXMusic.getHeight());
        musOFF = new TextureRegion(btnTEXMusic,btnTEXMusic.getWidth()/2, 0, btnTEXMusic.getWidth()/2, btnTEXMusic.getHeight());
        if(Game.music)
            btnMusic = new Sprite(musON);
        else
            btnMusic = new Sprite(musOFF);
        btnMusic.setPosition(10, 10);
        btnMusic.setSize(45*Game.dWidth,45*Game.dHeight);

        //Кнопка звуков
        btnTEXSound = new Texture("Sound.png");
        sounON = new TextureRegion(btnTEXSound, btnTEXSound.getWidth()/2, btnTEXSound.getHeight());
        sounOFF = new TextureRegion(btnTEXSound,btnTEXSound.getWidth()/2, 0, btnTEXSound.getWidth()/2, btnTEXSound.getHeight());
        if(Game.sound)
            btnSound = new Sprite(sounON);
        else
            btnSound = new Sprite(sounOFF);

        btnSound.setPosition(10+btnMusic.getWidth()+10, 10);
        btnSound.setSize(45*Game.dWidth,45*Game.dHeight);

        back = new Sprite(new Texture("MainBackground.jpg"));
        back.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch = new SpriteBatch();
        btnStart = new Sprite(new Texture("start.png"));
        btnStart.setSize(btnStart.getWidth()*Game.dWidth, btnStart.getHeight()*Game.dHeight);
        btnStart.setPosition(Gdx.graphics.getWidth()/2-btnStart.getWidth()/2, Gdx.graphics.getHeight()/2-btnStart.getHeight()/2);

        Gdx.input.setInputProcessor(new InputMultiplexer(this));

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        spriteBatch.begin();
        back.draw(spriteBatch);
        btnSound.draw(spriteBatch);
        btnMusic.draw(spriteBatch);
        btnStart.draw(spriteBatch);
       if(isLoading) loading.drawing(spriteBatch);
        spriteBatch.end();

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(!isLoading) {
            if (btnStart.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                isLoading = true;
                Timer timer = new Timer();
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        run = new Run(game);
                    }
                },0.1f);


            }

            if (btnMusic.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                if (Game.music == true) {
                    Game.music = false;
                    mainTheme.stop();
                    btnMusic.setRegion(musOFF);
                } else {
                    Game.music = true;
                    mainTheme.play();
                    btnMusic.setRegion(musON);
                }
            }
            if (btnSound.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                if (Game.sound == true) {
                    Game.sound = false;
                    btnSound.setRegion(sounOFF);
                } else {
                    Game.sound = true;
                    btnSound.setRegion(sounON);
                }
            }
        }

        return true;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        mainTheme.dispose();
    }
}
