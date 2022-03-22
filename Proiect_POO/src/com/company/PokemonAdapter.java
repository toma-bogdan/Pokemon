package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class PokemonAdapter implements Adapter {
    HashMap<String,Pokemon> pokemons = new HashMap<>();//stores all pokemons

    public void setInstance() {
        File file = new File("input_pokemons.txt");
        try {
            Scanner scanner = new Scanner(file);

            do {
                //Builds the pokemon:
                Pokemon pokemon = new Pokemon.PokemonBuilder()
                        .setName(scanner.next())
                        .setHP(scanner.nextInt())
                        .setAttack(scanner.nextInt())
                        .setSpecialAttack(scanner.nextInt())
                        .setDefense(scanner.nextInt())
                        .setSpecialDefense(scanner.nextInt())
                        .setAbility1(new Ability.AbilityBuilder()
                                .setDMG(scanner.nextInt())
                                .setStun(scanner.nextBoolean())
                                .setDodge(scanner.nextBoolean())
                                .setCD(scanner.nextInt())
                                .build())
                        .setAbility2(new Ability.AbilityBuilder()
                                .setDMG(scanner.nextInt())
                                .setStun(scanner.nextBoolean())
                                .setDodge(scanner.nextBoolean())
                                .setCD(scanner.nextInt())
                                .build())
                        .build();
                //Puts the pokemon in the hashmap
                pokemons.put(pokemon.getName(),pokemon);
            }while (scanner.hasNext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public HashMap<String, Pokemon> getInstance() {
        return pokemons;
    }

    public Pokemon getPokemon(String Name){
        return pokemons.get(Name);
    }


}
