package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;


public class GameScreen extends InputAdapter implements Screen {
    final Game game;
    public PerspectiveCamera cam;
    final float[] startPos = {0f, 0f, 100f};
    Hero hero;
    FireBall fireBall;
    Room room ;
    static Music theme;
    Level level;

    public SpriteBatch spriteBatch;
    public Sprite RED1, RED2, BLUE1, BLUE2, next;
    public Texture HP, maxHP;

    public Environment environment;

    ModelBatch modelBatch = new ModelBatch();

    public GameScreen(final Game game, Room room, Level level,  Hero hero){
        this.game = game;
        //Камера
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(startPos[0], startPos[1], startPos[2]);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        this.hero = hero;
        this.hero.setImm(true);
        this.room = room;
        this.level = level;

        //Музыка
        theme = Gdx.audio.newMusic(Gdx.files.internal("FFIITheme.mp3"));
        theme.setVolume(0.1f);
        theme.setLooping(true);
        if(Game.music)
        theme.play();


        //Спрайты
        spriteBatch = new SpriteBatch();
        RED1 = new Sprite(new Texture("red.png"));
        BLUE1 = new Sprite(new Texture("blue.png"));
        RED2 = new Sprite(new Texture("red.png"));
        BLUE2 = new Sprite(new Texture("blue.png"));
        maxHP = new Texture("MaxHP.png");
        HP = new Texture("HP.png");
        next = new Sprite(new Texture("next.png"));
        next.setSize(next.getWidth()*Game.dWidth, next.getHeight()*Game.dHeight);
        next.setPosition(Gdx.graphics.getWidth() - next.getWidth(), Gdx.graphics.getHeight()- next.getHeight());



        BLUE2.setSize(BLUE2.getWidth()*Game.dWidth, BLUE2.getHeight()*Game.dHeight);
        BLUE1.setSize(BLUE1.getWidth()*Game.dWidth, BLUE1.getHeight()*Game.dHeight);
        RED1.setSize(RED1.getWidth()*Game.dWidth, RED1.getHeight()*Game.dHeight);
        RED2.setSize(RED2.getWidth()*Game.dWidth, RED2.getHeight()*Game.dHeight);
        RED1.setPosition(10, 10);
        RED2.setPosition(Gdx.graphics.getWidth() - RED2.getWidth() - 10, 10);
        BLUE1.setPosition(10, 10);
        BLUE2.setPosition(Gdx.graphics.getWidth() - BLUE2.getWidth() - 10, 10);



        //Освещение
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.9f, 0.9f, 0.9f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 10f, 10f, 20f));

        Gdx.input.setInputProcessor(new InputMultiplexer(this));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        room.drawBack(spriteBatch);


        //Отрисовка спрайтов
        if (hero.isAttack()) {
            spriteBatch.begin();
            RED1.draw(spriteBatch);
            RED2.draw(spriteBatch);
            spriteBatch.end();
        } else {
            spriteBatch.begin();
            BLUE1.draw(spriteBatch);
            BLUE2.draw(spriteBatch);
            spriteBatch.end();
        }

        //Oтрисовка Hp
            spriteBatch.begin();
        for (int i = 0; i < hero.getMaxHP(); i++) {
            spriteBatch.draw(maxHP, maxHP.getWidth()*i+10, Gdx.graphics.getHeight() - 10 - maxHP.getHeight());
        }
        for (int i = 0; i < hero.getHP(); i++) {
            spriteBatch.draw(HP, HP.getWidth()*i+10, Gdx.graphics.getHeight() - 10 - HP.getHeight());
        }
            spriteBatch.end();

        //Отрисовка Врагов
        try {
            room.drawEnemies(spriteBatch,fireBall,hero);
        } catch (Exception ex){
            spriteBatch.end();
            System.out.println(ex);
        }

        //Отрисовка FireBall
        try {
            if (!fireBall.isExict()){
                fireBall=null;
            }

            fireBall.stateTime += Gdx.graphics.getDeltaTime();
            fireBall.currentFrame = (TextureRegion) fireBall.animation.getKeyFrame(fireBall.stateTime, true);
            fireBall.sprite.setRegion(fireBall.currentFrame);

            fireBall.spriteBatch.begin();
            fireBall.sprite.draw(fireBall.spriteBatch);
            fireBall.move();
            fireBall.spriteBatch.end();
        } catch (Exception ex){}

        room.complet();
        //Завершение комнаты
        if(room.isComplete()){
            spriteBatch.begin();
            next.draw(spriteBatch);
            spriteBatch.end();

        }



        modelBatch.begin(cam);
        modelBatch.render(hero.instance, environment);
        modelBatch.end();
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if(room.isComplete()){
                if(next.getBoundingRectangle().contains(screenX,Gdx.graphics.getHeight() - screenY)) {
                    theme.stop();
                    if(Game.music)
                        MainMenu.mainTheme.play();
                        game.setScreen(level);
                }
        }

        if ((screenX > BLUE1.getX() && (screenX < (BLUE1.getX() + BLUE1.getWidth())) &&
                (Gdx.graphics.getHeight() - screenY) > BLUE1.getY() && ((Gdx.graphics.getHeight() - screenY) < (BLUE1.getY() + BLUE1.getHeight()))) ||
                (screenX > BLUE2.getX() && (screenX < (BLUE2.getX() + BLUE2.getWidth())) &&
                        (Gdx.graphics.getHeight() - screenY) > BLUE2.getY() && ((Gdx.graphics.getHeight() - screenY) < (BLUE2.getY() + BLUE2.getHeight())))) {

            if (hero.isAttack()) hero.setAttack(false);
            else hero.setAttack(true);

        } else if (!hero.isAttack()) {            //Перемещение
            hero.movement(screenX, screenY, cam);
        } else if (hero.isCanAttack()){    //Aтакa
            fireBall = new FireBall(hero.SX, hero.SY);
            fireBall.setDelta(screenX, screenY, hero);
            hero.setCanAttack(false);
        }

        return true;
    }


    @Override
    public void dispose() {
        spriteBatch.dispose();
        hero.model.dispose();
        modelBatch.dispose();
        theme.dispose();

    }
}
