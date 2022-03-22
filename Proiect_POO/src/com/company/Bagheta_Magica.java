package com.company;

public class Bagheta_Magica extends Item{

    @Override
    void IncreaseStats(Pokemon pokemon) {
        if(pokemon.getSpecialAttack() != 0){
            pokemon.setSpecialAttack(pokemon.getSpecialAttack() + 3);
        }
    }

    @Override
    String getName() {
        return "Bagheta_Magica";
    }
}
