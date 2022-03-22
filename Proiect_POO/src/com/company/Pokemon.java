package com.company;


public class Pokemon {
    private final String name;
    private int HP;
    private int Attack;
    private int SpecialAttack;
    private int Defense;
    private int SpecialDefense;
    private final Ability ability1;
    private final Ability ability2;


    public Pokemon(String name, int HP, int attack, int specialAttack, int defense, int specialDefense, Ability ability1, Ability ability2) {
        this.name = name;
        this.HP = HP;
        Attack = attack;
        SpecialAttack = specialAttack;
        Defense = defense;
        SpecialDefense = specialDefense;
        this.ability1 = ability1;
        this.ability2 = ability2;
    }

    public static class PokemonBuilder{
        private String name;
        private int HP;
        private int Attack;
        private int SpecialAttack;
        private int Defense;
        private int SpecialDefense;
        private Ability ability1;
        private Ability ability2;

        public PokemonBuilder setName(String name){
            this.name = name;
            return this;
        }
        public PokemonBuilder setHP(int HP){
            this.HP = HP;
            return this;
        }
        public PokemonBuilder setAttack(int attack){
            this.Attack = attack;
            return this;
        }
        public PokemonBuilder setSpecialAttack(int specialAttack){
            this.SpecialAttack = specialAttack;
            return this;
        }
        public PokemonBuilder setDefense(int defense){
            this.Defense = defense;
            return this;
        }
        public PokemonBuilder setSpecialDefense(int specialDefense){
            this.SpecialDefense = specialDefense;
            return this;
        }
        public PokemonBuilder setAbility1(Ability ability1){
            this.ability1 = ability1;
            return this;
        }
        public PokemonBuilder setAbility2(Ability ability2){
            this.ability2 = ability2;
            return this;
        }
        public Pokemon build(){
            return new Pokemon(name, HP, Attack, SpecialAttack, Defense, SpecialDefense, ability1, ability2);
        }
    }

    public String getName() {
        return name;
    }

    public int getHP() {
        return HP;
    }

    public int getAttack() {
        return Attack;
    }

    public int getSpecialAttack() {
        return SpecialAttack;
    }

    public int getDefense() {
        return Defense;
    }

    public int getSpecialDefense() {
        return SpecialDefense;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setAttack(int attack) {
        Attack = attack;
    }

    public void setSpecialAttack(int specialAttack) {
        SpecialAttack = specialAttack;
    }

    public void setDefense(int defense) {
        Defense = defense;
    }

    public void setSpecialDefense(int specialDefense) {
        SpecialDefense = specialDefense;
    }

    public Ability getAbility1() {
        return ability1;
    }

    public Ability getAbility2() {
        return ability2;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", HP=" + HP +
                ", Attack=" + Attack +
                ", SpecialAttack=" + SpecialAttack +
                ", Defense=" + Defense +
                ", SpecialDefense=" + SpecialDefense +
                ", ability1=" + ability1 +
                ", ability2=" + ability2 +
                '}';
    }
}
