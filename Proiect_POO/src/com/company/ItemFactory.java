package com.company;


public class ItemFactory{

    public Item createItem(String name){
        switch (name){
            case "Scut":
                return new Scut();
            case "Vesta":
                return new Vesta();
            case "Sabiuta":
                return new Sabiuta();
            case "Bagheta_Magica":
                return new Bagheta_Magica();
            case "Vitamine":
                return new Vitamine();
            case "Brad_de_Craciun":
                return new Brad_De_Craciun();
            case "Pelerina":
                return new Pelerina();

        }
        throw new IllegalArgumentException("The type of item " + name +
                " is not recognised.");
    }
}
