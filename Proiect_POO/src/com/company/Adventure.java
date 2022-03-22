package com.company;

import java.io.*;
import java.util.Random;

public class Adventure {
    Antrenor antrenor1;
    Antrenor antrenor2;
    Pokemon neutrel1;
    Pokemon neutrel2;
    int pokemonattacked = 0;// flag to make sure one pokemon attack only if the other one did
    int isfinished = 0; //flag to check if a pokemon won
    int winner; //stores the winner
    PrintWriter out;



    public Adventure(Antrenor antrenor1, Antrenor antrenor2, Pokemon neutrel1, Pokemon neutrel2, PrintWriter out) {
        this.antrenor1 = antrenor1;
        this.antrenor2 = antrenor2;
        this.neutrel1 = neutrel1;
        this.neutrel2 = neutrel2;
        this.out = out;
    }
    public void startAdventure(){
        try {
            for(int i = 0; i < 3 ; i++) {
                battleNeutrel(antrenor1.pokemons.get(i), neutrel2);
                battleNeutrel(antrenor2.pokemons.get(i), neutrel1);
                battlePokemons(antrenor1.pokemons.get(i), antrenor2.pokemons.get(i));
            }
            Pokemon best_pokemon1 = bestPokemon(antrenor1);
            Pokemon best_pokemon2 = bestPokemon(antrenor2);
            battlePokemons(best_pokemon1,best_pokemon2);
            if(winner == 1)
                System.out.println("Antrenorul castigator este: " + antrenor1.Name);
            if(winner == 2)
                System.out.println("Antrenorul castigator este: " + antrenor2.Name);
            if(winner == 0)
                System.out.println("Draw");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public Pokemon bestPokemon(Antrenor antrenor){
        int best_stats = 0;
        Pokemon best_pokemon = null;
        for(Pokemon pokemon : antrenor.pokemons){
            int stats = pokemon.getHP() + pokemon.getAttack() + pokemon.getSpecialAttack() + pokemon.getDefense() + pokemon.getSpecialDefense();
            if(stats > best_stats)
                best_pokemon = pokemon;
        }
        return best_pokemon;
    }
    public void setPokemonattacked(int pokemonattacked) {
        this.pokemonattacked = pokemonattacked;
    }

    public int getPokemonattacked() {
        return pokemonattacked;
    }

    public void setIsfinished(int isfinished) {
        this.isfinished = isfinished;
    }

    public void battlePokemons(Pokemon pokemon, Pokemon rivalPokemon) throws InterruptedException {
        out.println("Lupta intre: " + pokemon.getName() + " si " + rivalPokemon.getName() + "\n");
        setIsfinished(0);
        setPokemonattacked(0);
        PokemonVsPokemon pokemonVsPokemon = new PokemonVsPokemon(pokemon,rivalPokemon);
        Thread t1 = new Thread(() -> {
            try {
                pokemonVsPokemon.figthPokemon(rivalPokemon,pokemon);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                pokemonVsPokemon.figthRivalPokemon(pokemon,rivalPokemon);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // Start both threads
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        if(isfinished == 1){
            System.out.println(pokemon.getName() + " won vs " + rivalPokemon.getName());
            WonBattle(pokemon);
            winner = 1;
        }
        else{
            if(isfinished == 2){
                System.out.println(rivalPokemon.getName() + " won vs " + pokemon.getName());
                WonBattle(rivalPokemon);
                winner = 2;
            }
            else {
                System.out.println("Draw");
                winner = 0;
            }
        }
        setIsfinished(0);

    }
    public void battleNeutrel(Pokemon pokemon, Pokemon neutrel) throws InterruptedException {
        out.println("Lupta cu Neutrel: " + pokemon.getName() + "\n");
        PokemonVsNeutrel pokemonAttacks = new PokemonVsNeutrel(neutrel, pokemon);
        Thread t1 = new Thread(() -> {
            try {
                pokemonAttacks.figthPokemon(neutrel, pokemon);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                pokemonAttacks.figthNeutrel(pokemon,neutrel);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // Start both threads
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        if(isfinished == 1){
            System.out.println(pokemon.getName() + " won vs " + neutrel.getName());
            WonBattle(pokemon);
        }
        else{
            if(isfinished == 2)
                System.out.println(neutrel.getName() + "won vs " + neutrel.getName());
            else
                System.out.println("Draw");
        }
        setIsfinished(0);
    }
    public class PokemonVsNeutrel{
        int hpNeutrel;
        int hpPokemon;
        int neutrelIsStunned = 0;
        int cooldown1 = 0;
        int cooldown2 = 0;
        int pokemonDodges = 0;
        String log = "";

        public PokemonVsNeutrel(Pokemon neutrel,Pokemon pokemon){
            this.hpNeutrel = neutrel.getHP();
            this.hpPokemon = pokemon.getHP();
        }

        public void SpecialAttackPokemon(Pokemon neutrel, Pokemon pokemon){
            /*Special attack for pokemon*/
            out.print(pokemon.getName() + " special attack/");
            if(neutrel.getSpecialDefense() >= pokemon.getSpecialAttack()){
                return;//Neutrel doesn't take damage from pokemon
            }
            this.hpNeutrel = this.hpNeutrel - (pokemon.getSpecialAttack() - neutrel.getSpecialDefense());

            if(this.hpNeutrel <= 0)// Pokemon won
                setIsfinished(1);
        }
        public void NormalAttackPokemon(Pokemon neutrel, Pokemon pokemon){
            out.print(pokemon.getName() + " normal attack/");
            if(neutrel.getDefense() >= pokemon.getAttack()){
                return;
            }
            this.hpNeutrel = this.hpNeutrel - (pokemon.getAttack() - neutrel.getSpecialDefense());

            if(this.hpNeutrel <= 0)
                setIsfinished(1);
        }
        public void Ability1Pokemon(Pokemon pokemon){
            out.print(pokemon.getName() + " ability1 attack/");
            //updates the hp
            this.hpNeutrel = this.hpNeutrel - pokemon.getAbility1().getDMG();

            //Sets the cooldown and the dodge/stun
            if(pokemon.getAbility1().isDodge())
                this.pokemonDodges = 1;
            if(pokemon.getAbility1().isStun())
                this.neutrelIsStunned = 1;
            this.cooldown1 = pokemon.getAbility1().getCD();

            if(this.hpNeutrel <= 0) //set the finished flag 1
                setIsfinished(1);
        }
        void Ability2Pokemon(Pokemon pokemon){
            out.print(pokemon.getName()+ " ability2 attack/");
            //updates the hp
            this.hpNeutrel = this.hpNeutrel - pokemon.getAbility2().getDMG();

            //Sets the cooldown and the dodge/stun
            if(pokemon.getAbility2().isDodge())
                this.pokemonDodges = 1;
            if(pokemon.getAbility2().isStun())
                this.neutrelIsStunned = 1;
            this.cooldown2 = pokemon.getAbility2().getCD();

            if(this.hpNeutrel <= 0) //set the finished flag 1
                setIsfinished(1);
        }
        public void figthPokemon(Pokemon neutrel, Pokemon pokemon) throws InterruptedException {

            Random rand = new Random();
            while(this.hpNeutrel > 0 && isfinished == 0){
                synchronized (this){
                    if(getPokemonattacked() == 1) { // checks if it is his turn to attack
                        wait();
                    }
                    chooseAttack(neutrel, pokemon, rand);//chooses an attack for the pokemon
                    setPokemonattacked(1);

                    notify();
                }
            }
        }
        public void attackNeutrel(Pokemon pokemon, Pokemon neutrel){
            out.println(neutrel.getName() + " normal attack -> Rezultat:");
            if(pokemon.getDefense() >= neutrel.getAttack() ){
                return; // Pokemon doesn't take damage from neutrel
            }
            this.hpPokemon = this.hpPokemon - (neutrel.getAttack() - pokemon.getDefense());

            if(this.hpPokemon <= 0) { // Neutrel won
                setIsfinished(2);
            }
        }
        public void figthNeutrel(Pokemon pokemon, Pokemon neutrel) throws InterruptedException {
            while(this.hpPokemon > 0 && isfinished == 0){
                synchronized (this){
                    if(getPokemonattacked() == 0) { // checks if it is his turn to attack
                        wait();
                    }
                     //checks if neutrel is stunned or if pokemon dodges his attack
                    if(neutrelIsStunned == 1) {
                        out.println("Neutrel nu ataca fiindca are stun -> Rezultat:");
                        neutrelIsStunned = 0;
                    }else {
                        if(pokemonDodges == 1){
                            out.println(pokemon.getName() + " a dat dodge la atacul " + neutrel.getName() + "-> Rezultat:");
                            pokemonDodges = 0;
                        }
                        else {
                            attackNeutrel(pokemon, neutrel);
                        }
                    }
                    out.println(pokemon.getName() + ": " + this.hpPokemon + " " + this.log);
                    out.println(neutrel.getName() + ": " + this.hpNeutrel);
                    this.log = "";
                    out.println();
                    setPokemonattacked(0);

                    notify();
                }
            }
            if(this.hpNeutrel <= 0 && this.hpPokemon <= 0)
                setIsfinished(3);
        }
        public void chooseAttack(Pokemon neutrel, Pokemon pokemon, Random rand){
            int n;
            if(cooldown1 != 0 && cooldown2 != 0){
                if (pokemon.getAttack() != 0)
                    NormalAttackPokemon(neutrel, pokemon);
                else
                    SpecialAttackPokemon(neutrel, pokemon);

            }
            if(cooldown1 == 0 && cooldown2 == 0){
                n = rand.nextInt(3);
                if(n == 2){
                    Ability2Pokemon(pokemon);
                }
                if(n == 1){
                    Ability1Pokemon(pokemon);
                }
                if(n == 0) {
                    if (pokemon.getAttack() != 0)
                        NormalAttackPokemon(neutrel, pokemon);
                    else
                        SpecialAttackPokemon(neutrel, pokemon);
                }
                if(cooldown1 != 0){
                    this.log += "ability 1 cooldown " + cooldown1 + " ";
                    cooldown1--;
                }
                if(cooldown2 != 0){
                    this.log += "ability2 cooldown " + cooldown2 + " ";
                    cooldown2--;
                }
                return;
            }

            if(cooldown1 != 0 && cooldown2 == 0){ //if ability 1 is on cooldown it chooses between normal attack and ability 2
                n = rand.nextInt(2);
                if(n == 1){
                    Ability2Pokemon(pokemon);
                }else{
                    if (pokemon.getAttack() != 0)
                        NormalAttackPokemon(neutrel, pokemon);
                    else
                        SpecialAttackPokemon(neutrel, pokemon);
                }

            }
            if(cooldown2 != 0 && cooldown1 == 0){ //if ability 2 is on cooldown it chooses between normal/special attack and ability 1
                n = rand.nextInt(2);
                if(n == 1){
                    Ability1Pokemon(pokemon);
                }else {
                    if (pokemon.getAttack() != 0)
                        NormalAttackPokemon(neutrel, pokemon);
                    else
                        SpecialAttackPokemon(neutrel, pokemon);
                }
            }

            if(cooldown1 != 0){
                this.log += "ability 1 cooldown " + cooldown1 + " ";
                cooldown1--;
            }
            if(cooldown2 != 0){
                this.log += "ability2 cooldown " + cooldown2 + " ";
                cooldown2--;
            }
        }
    }
    public class PokemonVsPokemon{
        int hpPokemon;
        int hpRivalPokemon;
        int pokemonIsStunned = 0;
        int rivalPokemonIsStunned = 0;
        int p_cooldown1 = 0;
        int p_cooldown2 = 0;
        int r_cooldown1 = 0;
        int r_cooldown2 = 0;
        int pokemonDodges = 0;
        int rivalPokemonDodges = 0;
        String logPokemon = "";
        String logRival = "";

        public PokemonVsPokemon(Pokemon pokemon, Pokemon rival){
            this.hpPokemon = pokemon.getHP();
            this.hpRivalPokemon = rival.getHP();
        }

        public void SpecialAttackRival(Pokemon rivalPokemon, Pokemon pokemon){
            /*Special attack for pokemon*/
            out.println(pokemon.getName() + " special attack -> Rezultat");
            if(rivalPokemon.getSpecialDefense() >= pokemon.getSpecialAttack()){
                return;//Rival doesn't take damage from pokemon
            }
            this.hpPokemon = this.hpPokemon - (pokemon.getSpecialAttack() - rivalPokemon.getSpecialDefense());

            if(this.hpPokemon <= 0)// Pokemon won
                setIsfinished(2);
        }
        public void NormalAttackRival(Pokemon rivalPokemon, Pokemon pokemon){
            out.println(pokemon.getName() + " normal attack -> Rezultat");
            if(rivalPokemon.getDefense() >= pokemon.getAttack()){
                return;
            }
            this.hpPokemon = this.hpPokemon - (pokemon.getAttack() - rivalPokemon.getDefense());

            if(this.hpPokemon <= 0)
                setIsfinished(2);
        }
        void Ability2Rival(Pokemon pokemon){
            out.println(pokemon.getName()+ " ability2 attack -> Rezultat");
            //updates the hp
            this.hpPokemon = this.hpPokemon - pokemon.getAbility2().getDMG();

            //Sets the cooldown and the dodge/stun
            if(pokemon.getAbility2().isDodge())
                this.rivalPokemonDodges = 1;
            if(pokemon.getAbility2().isStun())
                this.pokemonIsStunned = 1;
            this.r_cooldown2 = pokemon.getAbility2().getCD();

            if(this.hpPokemon <= 0) //set the finished flag 2
                setIsfinished(2);
        }
        public void Ability1Rival(Pokemon pokemon){
            out.println(pokemon.getName() + " ability1 attack -> Rezultat:");
            //updates the hp
            this.hpPokemon = this.hpPokemon - pokemon.getAbility1().getDMG();

            //Sets the cooldown and the dodge/stun
            if(pokemon.getAbility1().isDodge())
                this.rivalPokemonDodges = 1;
            if(pokemon.getAbility1().isStun())
                this.pokemonIsStunned = 1;
            this.r_cooldown1 = pokemon.getAbility1().getCD();

            if(this.hpPokemon <= 0) //set the finished flag2
                setIsfinished(2);
        }
        void Ability2Pokemon(Pokemon pokemon){
            out.print(pokemon.getName()+ " ability2 attack/");
            //updates the hp
            this.hpRivalPokemon = this.hpRivalPokemon - pokemon.getAbility2().getDMG();

            //Sets the cooldown and the dodge/stun
            if(pokemon.getAbility2().isDodge())
                this.pokemonDodges = 1;
            if(pokemon.getAbility2().isStun())
                this.rivalPokemonIsStunned = 1;
            this.p_cooldown2 = pokemon.getAbility2().getCD();

            if(this.hpRivalPokemon <= 0) //set the finished flag 1
                setIsfinished(1);
        }
        public void Ability1Pokemon(Pokemon pokemon){
            out.print(pokemon.getName() + " ability1 attack/");
            //updates the hp
            this.hpRivalPokemon = this.hpRivalPokemon - pokemon.getAbility1().getDMG();

            //Sets the cooldown and the dodge/stun
            if(pokemon.getAbility1().isDodge())
                this.pokemonDodges = 1;
            if(pokemon.getAbility1().isStun())
                this.rivalPokemonIsStunned = 1;
            this.p_cooldown1 = pokemon.getAbility1().getCD();

            if(this.hpRivalPokemon <= 0) //set the finished flag 1
                setIsfinished(1);
        }
        public void SpecialAttackPokemon(Pokemon rivalPokemon, Pokemon pokemon){
            /*Special attack for pokemon*/
            out.print(pokemon.getName() + " special attack/");
            if(rivalPokemon.getSpecialDefense() >= pokemon.getSpecialAttack()){
                return;//Rival doesn't take damage from pokemon
            }
            this.hpRivalPokemon = this.hpRivalPokemon - (pokemon.getSpecialAttack() - rivalPokemon.getSpecialDefense());
            if(this.hpRivalPokemon <= 0)// RivalPokemon won
                setIsfinished(1);
        }
        public void NormalAttackPokemon(Pokemon rivalPokemon, Pokemon pokemon){
            out.print(pokemon.getName() + " normal attack/");
            if(rivalPokemon.getDefense() >= pokemon.getAttack()){
                return;
            }
            this.hpRivalPokemon = this.hpRivalPokemon - (pokemon.getAttack() - rivalPokemon.getDefense());
            if(this.hpRivalPokemon <= 0)
                setIsfinished(1);
        }
        public void figthPokemon(Pokemon rivalPokemon, Pokemon pokemon) throws InterruptedException {
            Random rand = new Random();

            while(this.hpRivalPokemon > 0 && isfinished == 0){
                synchronized (this){
                    if(getPokemonattacked() == 1) { // checks if it is his turn to attack
                        wait();
                    }
                    if(isfinished == 0) {
                        if(pokemonIsStunned == 1) {
                            out.print(pokemon.getName() + " nu ataca fiindca are stun /");
                            pokemonIsStunned = 0;
                            if(p_cooldown1 != 0)
                                p_cooldown1--;
                            if(p_cooldown2 != 0)
                                p_cooldown2--;
                        }else {
                            if(rivalPokemonDodges == 1){
                                out.print(rivalPokemon.getName() + " a dat dodge la atacul " + pokemon.getName() + "/");
                                if(p_cooldown1 != 0)
                                    p_cooldown1--;
                                if(p_cooldown2 != 0)
                                    p_cooldown2--;
                                rivalPokemonDodges = 0;
                            }else {
                                chooseAttack(rivalPokemon, pokemon, rand);
                            }
                        }
                        setPokemonattacked(1);
                    }

                    notify();
                }
            }
        }
        public void figthRivalPokemon(Pokemon pokemon, Pokemon rivalPokemon) throws InterruptedException {
            Random rand = new Random();

            while(this.hpPokemon > 0 && isfinished == 0){
                synchronized (this){
                    if(getPokemonattacked() == 0) { // checks if it is his turn to attack
                        wait();
                    }
                    if(rivalPokemonIsStunned == 1) {
                        out.println(rivalPokemon.getName() + " nu ataca fiindca are stun -> Rezultat:");
                        rivalPokemonIsStunned = 0;
                        if(r_cooldown1 != 0)
                            r_cooldown1--;
                        if(r_cooldown2 != 0)
                            r_cooldown2--;
                    }else {
                        if(pokemonDodges == 1){
                            out.println(pokemon.getName() + " a dat dodge la atacul " + rivalPokemon.getName() + "-> Rezultat:");
                            pokemonDodges = 0;
                            if(r_cooldown1 != 0)
                                r_cooldown1--;
                            if(r_cooldown2 != 0)
                                r_cooldown2--;
                        }
                        else {
                            chooseAttackRival(pokemon,rivalPokemon,rand);
                        }
                    }

                    if(this.logPokemon.equals("")){
                        if(this.p_cooldown1 != 0)
                            this.logPokemon += "abilitate1 cooldown " + p_cooldown1 + " ";
                        if(this.p_cooldown2 != 0)
                            this.logPokemon += "abilitate2 cooldown " + p_cooldown2;
                    }
                    if(this.logRival.equals("")){
                        if(this.r_cooldown1 != 0)
                            this.logRival += "abilitate1 cooldown " + r_cooldown1 + " ";
                        if(this.r_cooldown2 != 0)
                            this.logPokemon += "abilitate2 cooldown " + r_cooldown2;
                    }
                    out.println(pokemon.getName() + ": " + this.hpPokemon + " " + this.logPokemon);
                    out.println(rivalPokemon.getName() + ": " + this.hpRivalPokemon + " " + this.logRival);
                    out.println();
                    this.logPokemon = "";
                    this.logRival = "";

                    setPokemonattacked(0);

                    notify();
                }
            }
        }
        public void chooseAttack(Pokemon rivalPokemon, Pokemon pokemon, Random rand){
            int n;
            if(p_cooldown1 != 0 && p_cooldown2 != 0){
                if (pokemon.getAttack() != 0)
                    NormalAttackPokemon(rivalPokemon, pokemon);
                else
                    SpecialAttackPokemon(rivalPokemon, pokemon);

            }
            if(p_cooldown1 == 0 && p_cooldown2 == 0){
                n = rand.nextInt(3);
                if(n == 2){
                    Ability2Pokemon(pokemon);
                }
                if(n == 1){
                    Ability1Pokemon(pokemon);
                }
                if(n == 0) {
                    if (pokemon.getAttack() != 0)
                        NormalAttackPokemon(rivalPokemon, pokemon);
                    else
                        SpecialAttackPokemon(rivalPokemon, pokemon);
                }
                if(p_cooldown1 != 0){
                    this.logPokemon += "ability 1 cooldown " + p_cooldown1 + " ";
                    p_cooldown1--;
                }
                if(p_cooldown2 != 0){
                    this.logPokemon += "ability2 cooldown " + p_cooldown2 + " ";
                    p_cooldown2--;
                }
                return;
            }

            if(p_cooldown1 != 0 && p_cooldown2 == 0){ //if ability 1 is on cooldown it chooses between normal attack and ability 2
                n = rand.nextInt(2);
                if(n == 1){
                    Ability2Pokemon(pokemon);
                }else{
                    if (pokemon.getAttack() != 0)
                        NormalAttackPokemon(rivalPokemon, pokemon);
                    else
                        SpecialAttackPokemon(rivalPokemon, pokemon);
                }

            }
            if(p_cooldown2 != 0 && p_cooldown1 == 0){ //if ability 2 is on cooldown it chooses between normal/special attack and ability 1
                n = rand.nextInt(2);
                if(n == 1){
                    Ability1Pokemon(pokemon);
                }else {
                    if (pokemon.getAttack() != 0)
                        NormalAttackPokemon(rivalPokemon, pokemon);
                    else
                        SpecialAttackPokemon(rivalPokemon, pokemon);
                }
            }

            if(p_cooldown1 != 0){
                this.logPokemon += "ability 1 cooldown " + p_cooldown1 + " ";
                p_cooldown1--;
            }
            if(p_cooldown2 != 0){
                this.logPokemon += "ability2 cooldown " + p_cooldown2 + " ";
                p_cooldown2--;
            }
        }
        public void chooseAttackRival(Pokemon pokemon, Pokemon rival, Random rand){
            int n;
            if(r_cooldown1 != 0 && r_cooldown2 != 0){
                if (rival.getAttack() != 0)
                    NormalAttackRival(pokemon, rival);
                else
                    SpecialAttackRival(pokemon, rival);

            }
            if(r_cooldown1 == 0 && r_cooldown2 == 0){
                n = rand.nextInt(3);
                if(n == 2){
                    Ability2Rival(rival);
                }
                if(n == 1){
                    Ability1Rival(rival);
                }
                if(n == 0) {
                    if (rival.getAttack() != 0)
                        NormalAttackRival(pokemon, rival);
                    else
                        SpecialAttackRival(pokemon, rival);
                }
                if(r_cooldown1 != 0){
                    this.logRival += "ability 1 cooldown " + r_cooldown1 + " ";
                    r_cooldown1--;
                }
                if(r_cooldown2 != 0){
                    this.logRival += "ability2 cooldown " + r_cooldown2 + " ";
                    r_cooldown2--;
                }
                return;
            }

            if(r_cooldown1 != 0 && r_cooldown2 == 0){ //if ability 1 is on cooldown it chooses between normal attack and ability 2
                n = rand.nextInt(2);
                if(n == 1){
                    Ability2Rival(rival);
                }else{
                    if (rival.getAttack() != 0)
                        NormalAttackRival(pokemon, rival);
                    else
                        SpecialAttackRival(pokemon, rival);
                }

            }
            if(r_cooldown2 != 0 && r_cooldown1 == 0){ //if ability 2 is on cooldown it chooses between normal/special attack and ability 1
                n = rand.nextInt(2);
                if(n == 1){
                    Ability1Rival(rival);
                }else {
                    if (rival.getAttack() != 0)
                        NormalAttackRival(pokemon, rival);
                    else
                        SpecialAttackRival(pokemon, rival);
                }
            }
            if(r_cooldown1 != 0){
                this.logRival += "ability 1 cooldown " + r_cooldown1 + " ";
                r_cooldown1--;
            }
            if(r_cooldown2 != 0){
                this.logRival += "ability2 cooldown " + r_cooldown2 + " ";
                r_cooldown2--;
            }
        }
    }
    public void WonBattle(Pokemon pokemon){
        /*Increments all stats of a pokemon by 1 if he won a battle*/
        pokemon.setHP(pokemon.getHP() + 1);
        if(pokemon.getAttack() != 0){
            pokemon.setAttack(pokemon.getAttack() + 1);
        }else {
            pokemon.setSpecialAttack(pokemon.getSpecialAttack() + 1);
        }
        pokemon.setDefense(pokemon.getDefense() + 1);
        pokemon.setSpecialDefense(pokemon.getSpecialDefense() + 1);
    }
}

