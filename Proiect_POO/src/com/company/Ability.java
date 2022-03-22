package com.company;

public class Ability {
    int DMG;
    boolean Stun;
    boolean Dodge;
    int CD;

    public Ability(int DMG, boolean stun, boolean dodge, int CD) {
        this.DMG = DMG;
        Stun = stun;
        Dodge = dodge;
        this.CD = CD;
    }
    public static class AbilityBuilder{
        int DMG;
        boolean Stun;
        boolean Dodge;
        int CD;

        public AbilityBuilder setDMG(int DMG){
            this.DMG = DMG;
            return this;
        }
        public AbilityBuilder setStun(boolean Stun){
            this.Stun = Stun;
            return this;
        }
        public AbilityBuilder setDodge(boolean Dodge){
            this.Dodge = Dodge;
            return this;
        }
        public AbilityBuilder setCD(int CD){
            this.CD = CD;
            return this;
        }
        public Ability build(){
            return new Ability(DMG, Stun, Dodge, CD);
        }
    }

    public int getDMG() {
        return DMG;
    }

    public boolean isStun() {
        return Stun;
    }

    public boolean isDodge() {
        return Dodge;
    }

    public int getCD() {
        return CD;
    }

    @Override
    public String toString() {
        return "Ability{" +
                "DMG=" + DMG +
                ", Stun=" + Stun +
                ", Dodge=" + Dodge +
                ", CD=" + CD +
                '}';
    }
}
