/* dex.csv Generated Using https://www.dragonflycave.com/resources/pokemon-list-generator
 * %[name]%, %[national_dex]%, %[national_dex]%, %[gsc_dex]%, %[hgss_dex]%, %[rse_dex]%, %[oras_dex]%, %[dp_dex]%, %[pt_dex]%, %[hisui_dex]%, %[bw_dex]%, %[b2w2_dex]%, %[xy_central_dex]%, %[xy_coastal_dex]%, %[xy_mountain_dex]%, %[sm_dex]%, %[sm_melemele_dex]%, %[sm_akala_dex]%, %[sm_ulaula_dex]%, %[sm_poni_dex]%, %[usum_dex]%, %[usum_melemele_dex]%, %[usum_akala_dex]%, %[usum_ulaula_dex]%, %[usum_poni_dex]%, %[swsh_dex]%, %[swsh_armor_dex]%, %[swsh_tundra_dex]%, %[sv_dex]%, %[sv_kitakami_dex]%, %[sv_blueberry_dex]%
 * Edited the second call for national_dex to be the kanto region
 * stats.csv Created using information by Bulbapedia and Serebii
 */
import java.io.*;
import java.awt.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import java.util.logging.*;
public class Pokemon{
    // National and Regional Pokédexes
    public static Pokemon[] nationalDex;
    public static HashMap<Integer,Pokemon>[] regionalDex;
    // Characteristics
    public String name;
    public int national;
    public ArrayList<Form> forms;
    public Pokemon(){} // Pokémon Constructor

    // Initialize Pokédex
    @SuppressWarnings("unchecked")
    public static void Dex(){
        // Initialize National and Regional Dexes
        Form.types = new String[]{"Normal", "Bug", "Dark", "Dragon", "Electric", "Fairy", "Fighting", "Fire", "Flying", "Ghost", "Grass", "Ground", "Ice", "Poison", "Psychic", "Rock", "Steel", "Water"};
        nationalDex = new Pokemon[1025];
        regionalDex = new HashMap[30];
        for(int i=0; i<nationalDex.length; i++) nationalDex[i] = new Pokemon();
        for(int i=0; i<regionalDex.length; i++) regionalDex[i] = new HashMap<>();

        // Read from file
        try(BufferedReader readDex = new BufferedReader(new FileReader("dex.csv")); BufferedReader readStats = new BufferedReader(new FileReader("stats.csv"))){
            String line;
            readDex.readLine();
            while ((line = readDex.readLine()) != null) { // Initialize the Pokémon
                String[] lines = line.split(", ");
                nationalDex[Integer.parseInt(lines[1])-1].inputDex(lines); // Input the info
            }
            for(HashMap<Integer, Pokemon> i : regionalDex) i.remove(-1);
            readStats.readLine();
            while ((line = readStats.readLine()) != null) { // Initialize each form of Pokémon
                String[] lines = line.split(", ");
                int x = Integer.parseInt(lines[0]) - 1;
                switch (lines[2]) {
                    case "types" -> { // Different Types for Arceus and Silvally
                        for(int i=0; i<Form.types.length; i++){
                            if(i != 0){
                                lines[2] = Form.types[i].toLowerCase();
                                lines[4] = Form.types[i];
                            }else lines[2] = "";
                            nationalDex[x].forms.add(new Form(lines));
                        }
                    }
                    case "berries" -> { // Different Alcreamie Forms
                        for(String j : new String[]{"", "rc", "mac", "mic", "lc", "sc", "rs", "cs", "ras"})
                            for(String i : new String[]{"", "berry", "love", "star", "clover", "flower", "ribbon"}){
                                lines[2] = j + i;
                                nationalDex[x].forms.add(new Form(lines));
                            }
                    }
                    default -> nationalDex[x].forms.add(new Form(lines));
                }
            }
        }
        catch (FileNotFoundException | NumberFormatException e) {System.out.println("File not found/Number Format Exception");}
        catch (IOException ex) {Logger.getLogger(Object.class.getName()).log(Level.SEVERE, null, ex);}
    }

    // Constructs Dex Numbers
    private void inputDex(String[] lines){
        this.name = lines[0];
        this.forms = new ArrayList<>();
        int[] dexNumber = new int[31];
        for(int i = 2; i < dexNumber.length; i++) dexNumber[i - 2] = Integer.parseInt(lines[i]);
        this.national = Integer.parseInt(lines[1]);
        if(dexNumber[0] <= 151) regionalDex[0].put(dexNumber[0], this); // Add Dex number of Kanto
        for(int i = 1; i < regionalDex.length; i++) regionalDex[i].put(dexNumber[i - 1], this);
    }

    // Debug Output
    public void getDebug(){
        String[] dexString = {"RBY/FRLG", "Lets Go", "GSC", "HGSS", "RSE", "ORAS", "DP", "Platinum", "Hisui", "BW", "B2W2", "KalosCentral", "KalosCoastal", "KalosMountain", "Alola", "Melemele", "Akala", "Ula'Ula", "Poni", "Ultra Alola", "Ultra Melemele", "Ultra Akala", "Ultra Ula'Ula", "Ultra Poni", "Galar", "Isle of Armor", "Crown Tundra", "Paldea", "Teal Mask", "Indigo Disk"};
        System.out.print("Dex Numbers\nNational: " + national);
        for(int i=0; i<regionalDex.length; i++) for(int j : regionalDex[i].keySet()) if(regionalDex[i].get(j).equals(this)){
            System.out.print(", " + dexString[i] + ": " + j);
            break;
        }System.out.print("\nForms:\n---");
        for(int i = 0; i < forms.size(); i++) forms.get(i).getDebug();
        System.out.println("-----");
    }

    //Get image from Serebii website
    public ImageIcon image(String formSymbol, boolean shiny){
        String nationalString = (national <= 0 || national > 1025) ? "001" : (national > 99) ? "" + national : (national > 9) ? "0" + national : (national > 0) ? "00" + national : "001",
        linkText = (shiny ? "Shiny/SV/new/" : "scarletviolet/pokemon/new/") + nationalString + (formSymbol.equals("") ? "" : "-") + formSymbol;
        try {
            BufferedImage image = ImageIO.read(new URI("https://serebii.net/" + linkText + ".png").toURL());
            return new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        }catch (MalformedURLException | URISyntaxException e) {}
        catch (IOException ex) {Logger.getLogger(Object.class.getName()).log(Level.SEVERE, null, ex);}
        return null;
    }

    // Set Name and Icon for Pokémon List
    public void labels(JLabel nameLabel, JLabel imageLabel, boolean shiny){
        nameLabel.setText(name);
        imageLabel.setIcon(image("", shiny));
    }
    // For Specific Forms
    public void labels(JLabel nameLabel, JLabel imageLabel, int base, int visual, boolean shiny){
        nameLabel.setText(forms.get(base).name);
        imageLabel.setIcon(image(forms.get(base).formSymbol[visual], shiny));
    }

    // Gets Pokémon from dex
    public static Pokemon get(int region, int dlc, int selected){
        if(region == 5 && dlc != 0) selected--; // Accounting for Victini in Unova Dexes
        return (getHashMap(region, dlc).get(selected + 1) == null) ? nationalDex[0] : getHashMap(region, dlc).get(selected + 1);
    }

    // Puts Dex on JList
    public static DefaultListModel<String> getDex(int region, int dlc) {
        DefaultListModel<String> tempModel = new DefaultListModel<>();
        HashMap<Integer, Pokemon> tempDex = getHashMap(region, dlc);
        for (int i : tempDex.keySet()) tempModel.addElement(i + ". " + tempDex.get(i).name);
        return tempModel;
    }

    // Gets HashMap of Dex
    public static HashMap<Integer, Pokemon> getHashMap(int region, int dlc){
        HashMap<Integer, Pokemon> tempDex = new HashMap<>();
        int start = 1, end = 10; // Start and End point for regional dexes
        switch(region) {
            case 0 -> {start = 1; end = 1025; dlc = 0;} // National
            case 1 -> { switch(dlc) {
                case 0 -> {start = 1; end = 151;} // Kanto
                case 1 -> tempDex = regionalDex[0]; // Red, Blue, Yellow, FireRed, and LeafGreen
                case 2 -> tempDex = regionalDex[1]; // Let's Go
            }} case 2 -> { switch(dlc) {
                case 0 -> {start = 152; end = 251;} // Johto
                case 1 -> tempDex = regionalDex[2]; // Gold, Silver, and Crystal
                case 2 -> tempDex = regionalDex[3]; // HeartGold and SoulSilver
            }} case 3 -> { switch(dlc) {
                case 0 -> {start = 252; end = 386;} // Hoenn
                case 1 -> tempDex = regionalDex[4]; // Ruby, Sapphire, and Emerald
                case 2 -> tempDex = regionalDex[5]; // OmegaRuby and AlphaSapphire
            }} case 4 -> { switch(dlc) {
                case 0 -> {start = 387; end = 493;} // Sinnoh + Hisui
                case 1 -> tempDex = regionalDex[6]; // Diamond and Pearl, Brilliant Diamond, and Shining Pearl
                case 2 -> tempDex = regionalDex[7]; // Platinum
                case 3 -> tempDex = regionalDex[8]; // Legends Arceus
            }} case 5 -> { switch(dlc) {
                case 0 -> {start = 494; end = 649;} // Unova
                case 1 -> tempDex = regionalDex[9]; // Black and White
                case 2 -> tempDex = regionalDex[10]; // Black 2 and White 2
            }} case 6 -> { switch(dlc) {
                case 0 -> {start = 650; end = 721;} // Kalos
                case 1 -> tempDex = regionalDex[11]; // Central
                case 2 -> tempDex = regionalDex[12]; // Coastal
                case 3 -> tempDex = regionalDex[13]; // Mountain
            }} case 7 -> { switch(dlc) {
                case 0 -> {start = 722; end = 809;} // Alola
                case 1 -> tempDex = regionalDex[14]; // Sun and Moon
                case 2 -> tempDex = regionalDex[15]; // Melemele
                case 3 -> tempDex = regionalDex[16]; // Akala
                case 4 -> tempDex = regionalDex[17]; // Ula'Ula
                case 5 -> tempDex = regionalDex[18]; // Poni
                case 6 -> tempDex = regionalDex[19]; // Ultra Sun and Ultra Moon
                case 7 -> tempDex = regionalDex[20]; // Ultra Melemele
                case 8 -> tempDex = regionalDex[21]; // Ultra Akala
                case 9 -> tempDex = regionalDex[22]; // Ultra Ula'Ula
                case 10 -> tempDex = regionalDex[23]; // Ultra Poni
            }} case 8 -> { switch(dlc) {
                case 0 -> {start = 810; end = 905;} // Galar
                case 1 -> tempDex = regionalDex[24]; // Sword and Shield
                case 2 -> tempDex = regionalDex[25]; // Isle of Armor
                case 3 -> tempDex = regionalDex[26]; // Crown Tundra
            }} case 9 -> { switch(dlc) {
                case 0 -> {start = 906; end = 1025;} // Paldea
                case 1 -> tempDex = regionalDex[27]; // Scarlet and Violet
                case 2 -> tempDex = regionalDex[28]; // Teal Mask
                case 3 -> tempDex = regionalDex[29]; // Indigo Disk
            }} default -> System.out.println("Error: invalid region.");
        }
        if(dlc == 0) for(Pokemon p : nationalDex) { // Gets the dex with a starting and end point
            if(p.national < start || p.national > end) continue;
            tempDex.put(p.national - start + 1, p);
        } return new HashMap<>(tempDex);
    }
}