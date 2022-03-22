package com.company;

public class Pelerina extends Item{
    @Override
    void IncreaseStats(Pokemon pokemon) {
        pokemon.setSpecialDefense(pokemon.getSpecialDefense() + 3);
    }

    @Override
    String getName() {
        return "Pelerina";
    }
}
