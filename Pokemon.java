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
    public static String[] types;
    // Characteristics
    public String name;
    public int national;
    public ArrayList<Form> forms;
    public Pokemon(){} // Pokémon Constructor

    // Initializes Dex
    @SuppressWarnings("unchecked")
    public static void Dex(){
        // Initialize National and Regional Dexes
        types =  new String[]{"", "bug", "dark", "dragon", "electric", "fairy", "fighting", "fire", "flying", "ghost", "grass", "ground", "ice", "poison", "psychic", "rock", "steel", "water"};
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
                // Different Types for Arceus and Silvally
                if(lines[2].equals("types")) for(int i=0; i<types.length; i++){
                    lines[2] = types[i];
                    if(i!=0) lines[4] = (types[i].charAt(0) + "").toUpperCase() + types[i].substring(1);
                    nationalDex[Integer.parseInt(lines[0]) - 1].forms.add(new Form(lines));
                }    
                else nationalDex[Integer.parseInt(lines[0]) - 1].forms.add(new Form(lines));
            }
        }
        catch (FileNotFoundException | NumberFormatException e) {System.out.println("File not found/Number Format Exception");}
        catch (IOException ex) {Logger.getLogger(Pokemon.class.getName()).log(Level.SEVERE, null, ex);}
    }

    // Constructor from file
    private void inputDex(String[] lines){
        this.name = lines[0];
        this.forms = new ArrayList<>();
        int[] dexNumber = new int[31];
        for(int i = 2; i < dexNumber.length; i++) dexNumber[i - 2] = Integer.parseInt(lines[i]);
        this.national = Integer.parseInt(lines[1]);
        if(dexNumber[0] <= 151) regionalDex[0].put(dexNumber[0], this);
        for(int i = 1; i < regionalDex.length; i++) regionalDex[i].put(dexNumber[i - 1], this);
    }

    // Debug Output
    public void getDebug(){
        String[] dexString = {"RBY/FRLG", "Lets Go", "GSC", "HGSS", "RSE", "ORAS", "DP", "Platinum", "Hisui", "BW", "B2W2", "KalosCentral", "KalosCoastal", "KalosMountain", "Alola", "Melemele", "Akala", "Ula'Ula", "Poni", "Ultra Alola", "Ultra Melemele", "Ultra Akala", "Ultra Ula'Ula", "Ultra Poni", "Galar", "Isle of Armor", "Crown Tundra", "Paldea", "Teal Mask", "Indigo Disk"};
        System.out.print("Dex Numbers\nNational: " + national);
        for(int i=0; i<regionalDex.length; i++) for(int j : regionalDex[i].keySet()) if(regionalDex[i].get(j).equals(this)){
            System.out.print(", " + dexString[i] + ": " + j);
            break;
        }System.out.println("\nForms:");
        for(int i = 0; i < forms.size(); i++) forms.get(i).getDebug();
        System.out.println("-----");
    }

    //Get image from Serebii website
    public ImageIcon image(String formSymbol, boolean shiny){
        String dexString = (national <= 0 || national > 1025) ? "001" : (national > 99) ? "" + national : (national > 9) ? "0" + national : (national > 0) ? "00" + national : "001",
        linkText = "https://serebii.net/" + (shiny ? "Shiny/SV/new/" : "scarletviolet/pokemon/new/") + dexString + (formSymbol.equals("") ? "" : "-") + formSymbol + ".png";
        try {
            BufferedImage image = ImageIO.read(new URI(linkText).toURL());
            return new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        }catch (MalformedURLException | URISyntaxException e) {}
        catch (IOException ex) {Logger.getLogger(Pokemon.class.getName()).log(Level.SEVERE, null, ex);}
        return null;
    }

    // Set Name and Icon for Pokémon List
    public static void labels(JLabel label, int region, int dlc, int selected){
        Pokemon temp = get(region, dlc, selected);
        label.setIcon(temp.image("", false));
        label.setText(temp.name);
    }

    // Gets Pokémon from dex
    public static Pokemon get(int region, int dlc, int selected){
        if(region == 5 && dlc != 0) selected--; // Accounting for Victini in Unova Dex
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
        int start = 1, end = 10;
        switch(region) {
            case 0 -> {start = 1; end = 1025; dlc = 0;}
            case 1 -> { switch(dlc) { // Kanto
                case 0 -> {start = 1; end = 151;}
                case 1 -> tempDex = regionalDex[0];
                case 2 -> tempDex = regionalDex[1];
            }} case 2 -> { switch(dlc) { // Johto
                case 0 -> {start = 152; end = 251;}
                case 1 -> tempDex = regionalDex[2];
                case 2 -> tempDex = regionalDex[3];
            }} case 3 -> { switch(dlc) { // Hoenn
                case 0 -> {start = 252; end = 386;}
                case 1 -> tempDex = regionalDex[4];
                case 2 -> tempDex = regionalDex[5];
            }} case 4 -> { switch(dlc) { // Sinnoh + Hisui
                case 0 -> {start = 387; end = 493;}
                case 1 -> tempDex = regionalDex[6];
                case 2 -> tempDex = regionalDex[7];
                case 3 -> tempDex = regionalDex[8];
            }} case 5 -> { switch(dlc) { // Unova
                case 0 -> {start = 494; end = 649;}
                case 1 -> tempDex = regionalDex[9];
                case 2 -> tempDex = regionalDex[10];
            }} case 6 -> { switch(dlc) { // Kalos
                case 0 -> {start = 650; end = 721;}
                case 1 -> tempDex = regionalDex[11];
                case 2 -> tempDex = regionalDex[12];
                case 3 -> tempDex = regionalDex[13];
            }} case 7 -> { switch(dlc) { // Alola
                case 0 -> {start = 722; end = 809;}
                case 1 -> tempDex = regionalDex[14];
                case 2 -> tempDex = regionalDex[15];
                case 3 -> tempDex = regionalDex[16];
                case 4 -> tempDex = regionalDex[17];
                case 5 -> tempDex = regionalDex[18];
                case 6 -> tempDex = regionalDex[19];
                case 7 -> tempDex = regionalDex[20];
                case 8 -> tempDex = regionalDex[21];
                case 9 -> tempDex = regionalDex[22];
                case 10 -> tempDex = regionalDex[23];
            }} case 8 -> { switch(dlc) { // Galar
                case 0 -> {start = 810; end = 905;}
                case 1 -> tempDex = regionalDex[24];
                case 2 -> tempDex = regionalDex[25];
                case 3 -> tempDex = regionalDex[26];
            }} case 9 -> { switch(dlc) { // Paldea
                case 0 -> {start = 906; end = 1025;}
                case 1 -> tempDex = regionalDex[27];
                case 2 -> tempDex = regionalDex[28];
                case 3 -> tempDex = regionalDex[29];
            }} default -> System.out.println("Error: invalid region.");
        }
        if(dlc == 0) for(Pokemon p : nationalDex) { // Gets the dex with a starting and end point
            if(p.national < start || p.national > end) continue;
            tempDex.put(p.national - start + 1, p);
        } return new HashMap<>(tempDex);
    }
}