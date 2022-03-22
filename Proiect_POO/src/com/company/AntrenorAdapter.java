package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.util.Scanner;


public class AntrenorAdapter implements Adapter{
    ArrayList<Antrenor> antrenori = new ArrayList<>();//stores the 2 trainers

    public void setInstance(){
        //to run the other tests , file needs to be changed manually
        File file = new File("input/test2.txt");

        try {
            Scanner scanner = new Scanner(file);
            do {
                //Builds the trainer:
                Antrenor antrenor = new Antrenor.AntrenorBuilder()
                        .setName(scanner.next())
                        .setAge(scanner.nextInt())
                        .build();

                PokemonAdapter pokemonAdapter = new PokemonAdapter();
                pokemonAdapter.setInstance();

                //Stores the pokemon names from input:
                String input = scanner.nextLine();
                String[] names = input.split(" ");

                for (String name : names) {
                    //Adds the pokemons to the trainer array lists
                    if (pokemonAdapter.getPokemon(name) != null)
                        antrenor.addPokemon(pokemonAdapter.getPokemon(name));
                }
                //Equips the trainers pokemons with the desired items
                setItems(scanner.nextInt(),scanner,antrenor, 0);
                setItems(scanner.nextInt(),scanner,antrenor, 1);
                setItems(scanner.nextInt(),scanner,antrenor, 2);

                antrenori.add(antrenor);

            }while (scanner.hasNext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /*Sets the items to a pokemon*/
    void setItems(int n, Scanner scanner, Antrenor antrenor, int j){
        ItemFactory itemFactory = new ItemFactory();
        ArrayList<Item> items = new ArrayList<>();// Stores the items a pokemon have in case of duplicates

        for(int i = 0; i < n; i++){
            int ok = 1;
            Item item = itemFactory.createItem(scanner.next());

            for(Item item1 : items){//verifies if the item is already equipped
                if(item1.getName().equals(item.getName())){
                    System.out.println("The item is already equipped");
                    ok = 0;
                }
            }
            if(ok == 1){//Adds the item to the pokemon
                items.add(item);
                item.IncreaseStats(antrenor.pokemons.get(j));
            }
        }
    }

    @Override
    public Object getInstance() {
        return antrenori;
    }
}
