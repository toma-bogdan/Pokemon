package com.company;

public class Vesta extends Item{
    @Override
    void IncreaseStats(Pokemon pokemon) {
        pokemon.setHP(pokemon.getHP() + 10);
    }

    @Override
    String getName() {
        return "Vesta";
    }
}
