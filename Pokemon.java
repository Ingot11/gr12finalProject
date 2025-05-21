public class Pokemon{
    public String name, national;
    // Normal Regional Dex Number
    public int kanto, johto, hoenn, sinnoh, unova, kalos, alola, galar, paldea,
    // DLC / Remakes Dex Number
    yellow, hgss, oras, platinum, legendsArceus, b2w2, legendsZA, ultra, isleOfArmor, crownTundra, kitakami, indigoDisk,
    // Stats and Characteristics
    hp, atk, def, spAtk, spDef, spd, weight, height;
    public String[] forms, moveList, dexEntries;
    // Constructor
    public Pokemon(String name, String national){
        this.name = name;
        this.national = national;
    }
}