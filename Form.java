
public class Form{
    public String name, abilityOne, abilityTwo, abilityHidden, formSymbol;
    public int national, hp, atk, def, spAtk, spDef, spd,
    // Characteristics
    eggSteps, baseFriendship, caughtSeenStatus;
    public double weight, height, maleRatio;
    public Form(String text){
        String[] info = text.split(", ");
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