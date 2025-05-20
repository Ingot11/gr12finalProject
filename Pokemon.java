public class Pokemon{
    public String name, national;
    // Normal Regional Dex
    public int kanto, johto, hoenn, sinnoh, unova, kalos, alola, galar, paldea;
    // DLC / Remakes
    public int yellow, hgss, oras, platinum, legendsArceus, b2w2, legendsZA, ultra, isleOfArmor, crownTundra, kitakami, indigoDisk;
    // Stats
    public int hp, atk, def, sAtk, sDef, spd;
    // Constructor
    public Pokemon(String name, String national){
        this.name = name;
        this.national = national;
    }
}