package com.company;

class Scut extends Item{

    @Override
    void IncreaseStats(Pokemon pokemon) {
        pokemon.setDefense(pokemon.getDefense() + 2);
        pokemon.setSpecialDefense(pokemon.getSpecialDefense() + 2);
    }

    @Override
    String getName() {
        return "Scut";
    }
}
