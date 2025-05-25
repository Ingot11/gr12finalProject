public class Pokemon{
    public String name;
    // Normal Regional Dex Number
    public int national, kanto, johto, hoenn, sinnoh, unova, kalos, alola, galar, paldea,
    // DLC / Remakes Dex Number
    yellow, hgss, oras, platinum, legendsArceus, b2w2, legendsZA, ultra, isleOfArmor, crownTundra, kitakami, indigoDisk,
    // Stats and Characteristics
    hp, atk, def, spAtk, spDef, spd, weight, height;
    public String[] forms, moveList, dexEntries;
    // Constructor
    public Pokemon(String name, int national){
        this.name = name;
        this.national = national;
    }
    public int statCalcualtor(int level, String stats, int iv, int ev, String nature){
        int stat = 0;
        Nature boosted;
        try {boosted = Nature.valueOf(nature.toUpperCase());}
        catch(Exception e) {boosted = Nature.ADAMANT;}
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