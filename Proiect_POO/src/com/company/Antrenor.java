package com.company;

import java.util.ArrayList;

public class Antrenor {
    String Name;
    int age;
    ArrayList<Pokemon> pokemons = new ArrayList<>();

    public Antrenor(String name, int age) {
        Name = name;
        this.age = age;
    }
    public static class AntrenorBuilder{
        String Name;
        int age;
        ArrayList<Pokemon> pokemons;

        public AntrenorBuilder setName(String name){
            this.Name = name;
            return this;
        }
        public AntrenorBuilder setAge(int age){
            this.age = age;
            return this;
        }
        public Antrenor build(){
            return new Antrenor(Name,age);
        }
    }

    public void addPokemon(Pokemon pokemon) {
        this.pokemons.add(pokemon);
    }

    @Override
    public String toString() {
        return "Antrenor{" +
                "Name='" + Name + '\'' +
                ", age=" + age +
                ", pokemons=" + pokemons +
                '}';
    }
}
