package com.mygdx.game;

/**
 * Created by Admin on 30.04.2018.
 */

public class Run {
    final Game game;
    private Hero hero;

    public Run(final Game game){
        this.game = game;
        hero = new Hero(game);
        game.setScreen(new Level(this.game, 5, hero));
    }
}
