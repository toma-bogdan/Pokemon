package com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Start {
    public static void start()  {
        AntrenorAdapter antrenorAdapter = new AntrenorAdapter();
        antrenorAdapter.setInstance();

        ArrayList<Antrenor> antrenori = (ArrayList<Antrenor>) antrenorAdapter.getInstance();

        PokemonAdapter pokemonAdapter = new PokemonAdapter();
        pokemonAdapter.setInstance();
        Pokemon neutrel1 = pokemonAdapter.getPokemon("Neutrel1");
        Pokemon neutrel2 = pokemonAdapter.getPokemon("Neutrel2");

        try {
            PrintWriter out = new PrintWriter("logger.txt");
            Adventure adventure = new Adventure(antrenori.get(0),antrenori.get(1),neutrel1,neutrel2, out);
            adventure.startAdventure();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
