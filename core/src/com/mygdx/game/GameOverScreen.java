package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



/**
 * Created by Admin on 30.04.2018.
 */

public class GameOverScreen extends InputAdapter implements Screen {
    final Game game;
    private Texture text;
    private SpriteBatch spriteBatch;
    private Sprite btnMainMenu, back;
    Music overTheme;

    public GameOverScreen(final Game game) {
        this.game = game;
        //Музыка
        overTheme = Gdx.audio.newMusic(Gdx.files.internal("GameOver.mp3"));
        overTheme.setVolume(0.1f);
        overTheme.setLooping(true);
        if(Game.music)
        overTheme.play();

        back = new Sprite(new Texture("MainBackground.jpg"));
        back.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        btnMainMenu = new Sprite(new Texture("MM.png"));
        btnMainMenu.setSize(btnMainMenu.getWidth()*Game.dWidth, btnMainMenu.getHeight()*Game.dHeight);
        btnMainMenu.setPosition(Gdx.graphics.getWidth()/2-btnMainMenu.getWidth()/2, Gdx.graphics.getHeight()/3-btnMainMenu.getHeight());
        spriteBatch = new SpriteBatch();
        Gdx.input.setInputProcessor(new InputMultiplexer(this));
        text = new Texture("GO.png");
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
        spriteBatch.draw(text, Gdx.graphics.getWidth()/2-text.getWidth()/2, Gdx.graphics.getHeight()/1.5f);
        btnMainMenu.draw(spriteBatch);
        spriteBatch.end();

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(btnMainMenu.getBoundingRectangle().contains(screenX,Gdx.graphics.getHeight() - screenY)) {
            overTheme.stop();
            game.setScreen(new MainMenu(game));
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
        overTheme.dispose();
    }
}
