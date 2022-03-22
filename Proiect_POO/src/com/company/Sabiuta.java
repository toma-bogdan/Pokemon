package com.company;

public class Sabiuta extends Item{
    @Override
    void IncreaseStats(Pokemon pokemon) {
        if(pokemon.getAttack() != 0){
            pokemon.setAttack(pokemon.getAttack() + 3);
        }
    }

    @Override
    String getName() {
        return "Sabiuta";
    }
}
