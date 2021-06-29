package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by Admin on 21.04.2018.
 */

public class Level extends InputAdapter implements Screen {
    final Game game;
    private Integer countRoom;
    private Room[] rooms;
    private Sprite[] roomSprites;
    private Sprite back, bossRoomSprite;
    private SpriteBatch spriteBatch;
    private Hero hero;
    private BossRoom bossRoom;
    private boolean complete;

    Loading loading;
    boolean loadingFinish=false, isLoading=false;

    public Level(Game game, Integer countRoom, Hero hero) {
        loading = new Loading();
        this.hero = hero;
        complete = false;
        back = new Sprite(new Texture("MainBackground.jpg"));
        back.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch = new SpriteBatch();
        this.game = game;
        this.countRoom = countRoom;
        roomSprites = new Sprite[countRoom];
        rooms = new Room[countRoom];
        //Создание карты
        creatingMap();


    }

    @Override
    public void show() {
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.input.setInputProcessor(new InputMultiplexer(this));
        //Отрисовка карты
        spriteBatch.begin();
        back.draw(spriteBatch);
        for (int i = 0; i < countRoom; i++) {
            if(rooms[i].isComplete()){
                roomSprites[i].setTexture(rooms[i].getBackground().getTexture());
            }
            roomSprites[i].draw(spriteBatch);
        }

        if(isLoading) loading.drawing(spriteBatch);



        //отрисовка комнаты босса
        if(complete)
        bossRoomSprite.draw(spriteBatch);
        spriteBatch.end();


        startNewLevel();

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(!isLoading) {
            if (bossRoomSprite.getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY) && complete) {
                MainMenu.mainTheme.stop();
                isLoading = true;
                Timer timer = new Timer();
                final Level level = this;
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                game.setScreen(new BossFightScreen(game, bossRoom, level, hero));
                    }
                },0.1f);
            }
            for (int i = 0; i < countRoom; i++) {
                if (roomSprites[i].getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                    MainMenu.mainTheme.stop();
                    game.setScreen(new GameScreen(game, rooms[i], this, hero));
                }
            }
        }
        return true;
    }



    private void startNewLevel(){
        int tmp = 0;
        for (int i = 0; i < countRoom; i++) {
            if (rooms[i].isComplete()) tmp++;
        }
        if (tmp == countRoom){
            complete = true;
        }
        if (bossRoom.isComplete()) tmp+=1;
        if (tmp == countRoom+1){
            hero.setMaxHP(hero.getMaxHP()+1);
            hero.setHP(hero.getHP()+1);
            game.setScreen(new Level(game, countRoom+2, hero));
        }
    }

    private void creatingMap() {
        bossRoom = new BossRoom();
        bossRoomSprite = new Sprite();
        bossRoomSprite.setTexture(bossRoom.getBackground().getTexture());
        bossRoomSprite.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
        bossRoomSprite.setPosition(Gdx.graphics.getWidth() / 2 - bossRoomSprite.getWidth() / 2, Gdx.graphics.getHeight() / 4-Gdx.graphics.getHeight() / 10);

        rooms[0] = new Room();
        roomSprites[0] = new Sprite(new Texture("unkown.png"));
        roomSprites[0].setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
        roomSprites[0].setPosition(Gdx.graphics.getWidth() / 2 - roomSprites[0].getWidth() / 2, Gdx.graphics.getHeight() / 4);
        for (int i = 1; i < countRoom; i++) {
            int r = MathUtils.random(2);
            rooms[i] = new Room();
            roomSprites[i] = new Sprite(new Texture("unkown.png"));
            roomSprites[i].setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
            try {
                while (true) {
                    if (r == 0) {
                        roomSprites[i].setPosition(roomSprites[i - 1].getX() + roomSprites[i].getWidth(), roomSprites[i - 1].getY());
                        if (roomSprites[i - 2].getX() == roomSprites[i].getX() && roomSprites[i - 2].getY() == roomSprites[i].getY()
                                || roomSprites[i].getX() < 0 || roomSprites[i].getX()+roomSprites[i].getWidth() > Gdx.graphics.getWidth() ||
                                roomSprites[i].getY()+roomSprites[i].getHeight()>Gdx.graphics.getHeight() || roomSprites[i].getY()< 0) {
                            r = MathUtils.random(2);
                        } else break;
                    }
                    if (r == 1) {
                        roomSprites[i].setPosition(roomSprites[i - 1].getX() - roomSprites[i].getWidth(), roomSprites[i - 1].getY());
                        if (roomSprites[i - 2].getX() == roomSprites[i].getX() && roomSprites[i - 2].getY() == roomSprites[i].getY()
                                || roomSprites[i].getX() < 0 || roomSprites[i].getX()+roomSprites[i].getWidth() > Gdx.graphics.getWidth() ||
                                roomSprites[i].getY()+roomSprites[i].getHeight()>Gdx.graphics.getHeight() || roomSprites[i].getY()< 0) {
                            r = MathUtils.random(2);
                        } else break;
                    }
                    if (r == 2) {
                        roomSprites[i].setPosition(roomSprites[i - 1].getX(), roomSprites[i - 1].getY() + roomSprites[i].getHeight());
                        if (roomSprites[i - 2].getX() == roomSprites[i].getX() && roomSprites[i - 2].getY() == roomSprites[i].getY()
                                || roomSprites[i].getX() < 0 || roomSprites[i].getX()+roomSprites[i].getWidth() > Gdx.graphics.getWidth() ||
                                roomSprites[i].getY()+roomSprites[i].getHeight()>Gdx.graphics.getHeight() || roomSprites[i].getY()< 0) {
                            r = MathUtils.random(2);
                        } else break;
                    }
                }
            }catch (Exception ez){}
        }

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
    }
}
