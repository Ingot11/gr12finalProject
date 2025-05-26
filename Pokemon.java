/* dex.txt Generated Using https://www.dragonflycave.com/resources/pokemon-list-generator
 * %[name]%, %[national_dex]%, %[gsc_dex]%, %[rse_dex]%, %[dp_dex]%, %[pt_dex]%, %[hgss_dex]%, %[bw_dex]%, %[b2w2_dex]%, %[xy_central_dex]%, %[xy_coastal_dex]%, %[xy_mountain_dex]%, %[oras_dex]%, %[sm_dex]%, %[sm_melemele_dex]%, %[sm_akala_dex]%, %[sm_ulaula_dex]%, %[sm_poni_dex]%, %[usum_dex]%, %[usum_melemele_dex]%, %[usum_akala_dex]%, %[usum_ulaula_dex]%, %[usum_poni_dex]%, %[swsh_dex]%, %[swsh_armor_dex]%, %[swsh_tundra_dex]%, %[hisui_dex]%, %[sv_dex]%, %[sv_kitakami_dex]%, %[sv_blueberry_dex]%
*/
public class Pokemon{
    public String name;
    // Normal Regional Dex Number
    public int national, kanto, johto, hoenn, sinnoh, unova, kalosCentral, alola, galar, paldea,
    // DLC / Remakes Dex Number
    yellow, hgss, oras, platinum, legendsArceus, b2w2, kalosCoastal, kalosMountain, legendsZA,
    melemele, akala, ulaula, poni, ultra, ultraMelemele, ultraAkala, ultraUlaula, ultraPoni,
    isleOfArmor, crownTundra, kitakami, indigoDisk,
    // Stats and Characteristics
    hp, atk, def, spAtk, spDef, spd,
    abilityOne, abilityTwo, abilityHidden, weight, height;
    public String[] forms, moveList, dexEntries;
    // Constructor
    public Pokemon(String name, int national){
        this.name = name;
        this.national = national;
    }
    // Constructor from file
    public void inputDex(int[] dexNum){
        if(dexNum[0] <= 151) this.kanto = dexNum[0];
        else this.kanto = -1;
        this.johto = dexNum[1];
        this.hoenn = dexNum[2];
        this.sinnoh = dexNum[3];
        this.platinum = dexNum[4];
        this.hgss = dexNum[5];
        this.unova = dexNum[6];
        this.b2w2 = dexNum[7];
        this.kalosCentral = dexNum[8];
        this.kalosCoastal = dexNum[9];
        this.kalosMountain = dexNum[10];
        this.oras = dexNum[11];
        this.alola = dexNum[12];
        this.melemele = dexNum[13];
        this.akala = dexNum[14];
        this.ulaula = dexNum[15];
        this.poni = dexNum[16];
        this.ultra = dexNum[17];
        this.ultraMelemele = dexNum[18];
        this.ultraAkala = dexNum[19];
        this.ultraUlaula = dexNum[20];
        this.ultraPoni = dexNum[21];
        this.galar = dexNum[22];
        this.isleOfArmor = dexNum[23];
        this.crownTundra = dexNum[24];
        this.legendsArceus = dexNum[25];
        this.paldea = dexNum[26];
        this.kitakami = dexNum[27];
        this.indigoDisk = dexNum[28];
        this.legendsZA = -1;
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