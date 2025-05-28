public class Form{
    public String name, formSymbol, category, type1, type2, 
    ability1, ability2, abilityH, growthRate, egg1, egg2;
    public int national, catchRate, baseFriendship, baseExp, eggCycles,
    hp, atk, def, spAtk, spDef, spd, caughtSeenStatus;
    public double height, weight, maleRatio;
    // Constructor for each form
    public Form(String text){
        String[] info = text.split(", ");
        if(info.length != 25) return;
        this.name = info[1];
        this.formSymbol = info[2];
        this.category = info[3];
        this.type1 = info[4];
        this.type2 = info[5];
        this.ability1 = info[6];
        this.ability2 = info[7];
        this.abilityH = info[8];
        this.growthRate = info[16];
        this.egg1 = info[17];
        this.egg2 = info[18];
        try{
            this.national = Integer.parseInt(info[0]);
            this.height = Double.parseDouble(info[9]);
            this.weight = Double.parseDouble(info[10]);
            this.catchRate = Integer.parseInt(info[11]);
            this.baseFriendship = Integer.parseInt(info[12]);
            this.baseExp = Integer.parseInt(info[13]);
            this.maleRatio = Double.parseDouble(info[14]);
            this.eggCycles = Integer.parseInt(info[15]);
            this.hp = Integer.parseInt(info[19]);
            this.atk = Integer.parseInt(info[20]);
            this.def = Integer.parseInt(info[21]);
            this.spAtk = Integer.parseInt(info[22]);
            this.spDef = Integer.parseInt(info[23]);
            this.spd = Integer.parseInt(info[24]);
        }catch(NumberFormatException e){}
    }
    public int statCalcualtor(int level, String stats, int iv, int ev, String nature){
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