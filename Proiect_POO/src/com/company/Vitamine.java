package com.company;

public class Vitamine extends Item{
    @Override
    void IncreaseStats(Pokemon pokemon) {
        pokemon.setHP(pokemon.getHP() + 2);
        if(pokemon.getAttack() != 0){
            pokemon.setAttack(pokemon.getAttack() + 2);
        }
        if(pokemon.getSpecialAttack() != 0){
            pokemon.setSpecialAttack(pokemon.getSpecialAttack() + 2);
        }
    }

    @Override
    String getName() {
        return "Vitamine";
    }
}
