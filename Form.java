import java.util.*;
import javax.swing.*;
public class Form{
    public static String[] types;
    public String[] formSymbol;
    public String name, category, type1, type2, 
    ability1, ability2, abilityH, growthRate, egg1, egg2;
    public int national, catchRate, baseFriendship, baseExp, eggCycles,
    hp, atk, def, spAtk, spDef, spd, caughtSeen;
    public double height, weight, maleRatio;
    // Constructor for each form
    public Form(String[] text){
        if(text.length != 25) return;
        this.name = text[1];
        this.formSymbol = text[2].split("~");
        this.category = text[3];
        this.type1 = text[4];
        this.type2 = text[5];
        this.ability1 = text[6];
        this.ability2 = text[7];
        this.abilityH = text[8];
        this.growthRate = text[16];
        this.egg1 = text[17];
        this.egg2 = text[18];
        this.caughtSeen = 0;
        // Checks if number is empty or not a number
        for(int i : new int[]{0, 9, 10, 11, 12, 13, 14, 15, 19, 20, 21, 22, 23, 24}){
            try{Double.valueOf(text[i]);}
            catch(NumberFormatException e){text[i] = "-1";}
        }
        this.national = Integer.parseInt(text[0]);
        this.height = Double.parseDouble(text[9]);
        this.weight = Double.parseDouble(text[10]);
        this.catchRate = Integer.parseInt(text[11]);
        this.baseFriendship = Integer.parseInt(text[12]);
        this.baseExp = Integer.parseInt(text[13]);
        this.maleRatio = Double.parseDouble(text[14]);
        this.eggCycles = Integer.parseInt(text[15]);
        this.hp = Integer.parseInt(text[19]);
        this.atk = Integer.parseInt(text[20]);
        this.def = Integer.parseInt(text[21]);
        this.spAtk = Integer.parseInt(text[22]);
        this.spDef = Integer.parseInt(text[23]);
        this.spd = Integer.parseInt(text[24]);
    }

    // Debug Output
    public void getDebug(){
        System.out.println();
        for(String i : new String[]{name, formSymbol[0], category, type1, type2, ability1, ability2, abilityH, growthRate, egg1, egg2})
            System.out.print(i + ", ");
        System.out.println();
        for(double i : new double[]{national, catchRate, baseFriendship, baseExp, eggCycles, hp, atk, def, spAtk, spDef, spd, caughtSeen, height, weight, maleRatio})
            System.out.print(i + ", ");
        System.out.println("\nWeaknesses and Resistances:");
        for(String i: typeDefensive().keySet())
            System.out.print(i + ": " + typeDefensive().get(i) + ", ");
        System.out.print("\n---");
    }

    // Update Labels
    public void updateLabels(JLabel[] info){
        if(info.length != 11) return;
        info[0].setText(category + " Pokémon");
        info[1].setText(type1 + (!type2.equals("") ? (", " + type2) : "") + " Type");
        info[2].setText("HP: " + hp + ", Atk: " + atk + ", Def: " + def + ", Sp.Atk: " + spAtk + ", Sp.Def: " + spDef + ", Spd: " + spd);
        info[3].setText(ability1 + (!ability2.equals("") ? (", " + ability2) : "") + (!abilityH.equals("") ? (", Hidden: " + abilityH) : ""));
        info[4].setText("Egg Cycles: " + eggCycles);
        info[5].setText("Growth Rate: " + growthRate);
        info[6].setText("Base Friendship: " + baseFriendship);
        info[7].setText("Base EXP: " + baseExp);
        info[8].setText("Male Percent: " + ((maleRatio==-1) ? "Unknown" : (maleRatio==0) ? "Never" : (maleRatio==100) ? "Always" : (maleRatio + "%")));
        info[9].setText("Height: " + height + ", Weight: " + weight);
        info[10].setText("Status: " + ((caughtSeen==1) ? "Seen" : (caughtSeen==2) ? "Caught" : "Unregistered"));
    }

    // Calculates Stats
    public int statCalcualtor(String stats, int level, int iv, int ev, String nature){
        int stat = 0;
        Nature boosted;
        try {boosted = Nature.valueOf(nature.toUpperCase());}
        catch(Exception e) {boosted = Nature.HARDY;}
        switch(stats){
            case "hp" -> stat = hp;
            case "atk" -> stat = atk;
            case "def" -> stat = def;
            case "spAtk" -> stat = spAtk;
            case "spDef" -> stat = spDef;
            case "spd" -> stat = spd;
        }
        double equals = ((2.0*stat) + iv + (ev/4.0)) * level / 100.0 ;
        if(stats.equals("hp")) return (int) (equals + level + 10);
        else return (int) ((equals + 5) * (boosted.boost(stats)));
    }

    // Stats in String Array
    public String[] getStats() {
        String[] output = new String[6];
        int[] stats = new int[]{hp, atk, def, spAtk, spDef, spd};
        for(int i=0; i<output.length; i++) output[i] = "" + stats[i];
        return output;
    }

    // Each forms weaknesses and resistances
    public HashMap<String, Double> typeDefensive(){
        double normal = 1, bug = 1, dark = 1, dragon = 1, electric = 1, fairy = 1, fighting = 1, fire = 1, flying = 1,
        ghost = 1, grass = 1, ground = 1, ice = 1, poison = 1, psychic = 1, rock = 1, steel = 1, water = 1;
        for(String i : new String[]{type1,type2}) switch(i){
            case "Normal" -> {
                fighting*=2;
                ghost=0;
            }case "Bug" -> {
                fire*=2;
                flying*=2;
                grass*=.5;
                fighting*=.5;
                ground*=.5;
            }case "Dark" -> {
                fighting*=2;
                bug*=2;
                fairy*=2;
                ghost*=.5;
                dark*=.5;
                psychic=0; 
            }case "Dragon" -> {
                ice*=2;
                dragon*=2;
                fairy*=2;
                fire*=.5;
                water*=.5;
                electric*=.5;
                grass*=.5;
            }case "Electric" -> {
                ground*=2;
                electric*=.5;
                flying*=.5;
                steel*=.5;
            }case "Fairy" -> {
                poison*=2;
                steel*=2;
                fighting*=.5;
                bug*=.5;
                dark*=.5;
                dragon=0;
            }case "Fighting" -> {
                flying*=2;
                psychic*=2;
                fairy*=2;
                bug*=.5;
                rock*=.5;
                dark*=.5;
            }case "Fire" -> {
                water*=2;
                ground*=2;
                rock*=2;
                fire*=.5;
                grass*=.5;
                ice*=.5;
                bug*=.5;
                steel*=.5;
                fairy*=.5;
            }case "Flying" -> {
                electric*=2;
                ice*=2;
                rock*=2;
                grass*=.5;
                fighting*=.5;
                bug*=.5;
                ground=0;
            }case "Ghost" -> {
                ghost*=2;
                dark*=2;
                poison*=.5;
                bug*=.5;
                normal=0;
                fighting=0;
            }case "Grass" -> {
                fire*=2;
                ice*=2;
                poison*=2;
                flying*=2;
                bug*=2;
                water*=.5;
                electric*=.5;
                grass*=.5;
                ground*=.5;
            }case "Ground" -> {
                water*=2;
                grass*=2;
                ice*=2;
                poison*=.5;
                rock*=.5;
                electric=0;
            }case "Ice" -> {
                fire*=2;
                fighting*=2;
                rock*=2;
                steel*=2;
                ice*=.5;
            }case "Poison" -> {
                ground*=2;
                psychic*=2;
                grass*=.5;
                fighting*=.5;
                poison*=.5;
                bug*=.5;
                fairy*=.5;
            }case "Psychic" -> {
                bug*=2;
                ghost*=2;
                dark*=2;
                fighting*=.5;
                psychic*=.5;
            }case "Rock" -> {
                water*=2;
                grass*=2;
                fighting*=2;
                ground*=2;
                steel*=2;
                normal*=.5;
                fire*=.5;
                poison*=.5;
                flying*=.5;
            }case "Steel" -> {
                fire*=2;
                fighting*=2;
                ground*=2;
                normal*=.5;
                grass*=.5;
                ice*=.5;
                flying*=.5;
                psychic*=.5;
                bug*=.5;
                rock*=.5;
                dragon*=.5;
                steel*=.5;
                fairy*=.5;
                poison=0;
            }case "Water" -> {
                electric*=2;
                grass*=2;
                fire*=.5;
                water*=.5;
                ice*=.5;
                steel*=.5;
            }
        }    
        double[] typeArray = new double[]{normal, bug, dark, dragon, electric, fairy, fighting, fire, flying, ghost, grass, ground, ice, poison, psychic, rock, steel, water};
        HashMap<String, Double> defensive = new HashMap<>();
        for(int i=0; i<types.length; i++) if(typeArray[i] != 1) defensive.put(types[i], typeArray[i]);
        return defensive;
    }
}