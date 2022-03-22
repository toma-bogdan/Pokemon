package com.company;

public class Brad_De_Craciun extends Item{
    @Override
    void IncreaseStats(Pokemon pokemon) {
        if(pokemon.getAttack() != 0){
            pokemon.setAttack(pokemon.getAttack() + 3);
        }
        pokemon.setDefense(pokemon.getDefense() + 1);
    }

    @Override
    String getName() {
        return "Brad_De_Craciun";
    }
}
