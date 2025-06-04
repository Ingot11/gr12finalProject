import javax.swing.*;
public class Form{
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
        this.formSymbol = text[2].split("# ");
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
    public void getStats(){
        for(String i : new String[]{name, formSymbol[0], category, type1, type2, ability1, ability2, abilityH, growthRate, egg1, egg2})
            System.out.print(i + ", ");
        System.out.println();
        for(double i : new double[]{national, catchRate, baseFriendship, baseExp, eggCycles, hp, atk, def, spAtk, spDef, spd, caughtSeen, height, weight, maleRatio})
            System.out.print(i + ", ");
        System.out.println();
    }
    public String getBaseStats(){
        return "HP: " + hp + ", Atk: " + atk + ", Def: " + def + ", Sp.Atk: " + spAtk + ", Sp.Def: " + spDef + ", Spd: " + spd;
    }
    public String getAbilities(){
        return ability1 + (!ability2.equals("") ? (", " + ability2) : "") + (!abilityH.equals("") ? ", Hidden: " + abilityH : "");
    }
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
}