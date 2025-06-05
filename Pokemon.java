/* dex.txt Generated Using https://www.dragonflycave.com/resources/pokemon-list-generator
 * %[name]%, %[national_dex]%, %[national_dex]%, %[gsc_dex]%, %[rse_dex]%, %[dp_dex]%, %[pt_dex]%, %[hgss_dex]%, %[bw_dex]%, %[b2w2_dex]%, %[xy_central_dex]%, %[xy_coastal_dex]%, %[xy_mountain_dex]%, %[oras_dex]%, %[sm_dex]%, %[sm_melemele_dex]%, %[sm_akala_dex]%, %[sm_ulaula_dex]%, %[sm_poni_dex]%, %[usum_dex]%, %[usum_melemele_dex]%, %[usum_akala_dex]%, %[usum_ulaula_dex]%, %[usum_poni_dex]%, %[swsh_dex]%, %[swsh_armor_dex]%, %[swsh_tundra_dex]%, %[hisui_dex]%, %[sv_dex]%, %[sv_kitakami_dex]%, %[sv_blueberry_dex]%
 * Edited the second call for national dex to be the kanto region
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
    // Full Pokédex
    public static Pokemon[] nationalDex;
    public static HashMap<Integer,Pokemon>[] dexOfDex;
    // Regional Dex Numbers
    public int national;
    public static HashMap<Integer,Pokemon> kanto, letsGo, johto, hoenn, sinnoh, platinum, hgss, unova, b2w2,
    kalosCentral, kalosCoastal, kalosMountain, oras,
    alola, melemele, akala, ulaula, poni, ultra, ultraMelemele, ultraAkala, ultraUlaula, ultraPoni,
    galar, isleOfArmor, crownTundra, legendsArceus,
    paldea, tealMask, indigoDisk;
    // Stats and Characteristics
    public String name;
    public ArrayList<Form> form;
    public Pokemon(){} // Pokémon Constructor

    // Constructor from file
    public void inputDex(String[] lines){
        this.name = lines[0];
        this.form = new ArrayList<>();
        int[] dexNumber = new int[31];
        for(int i=2; i<dexNumber.length; i++) dexNumber[i-2] = Integer.parseInt(lines[i]);
        this.national = Integer.parseInt(lines[1]);
        if(dexNumber[0] <= 151) dexOfDex[0].put(dexNumber[0], this);
        for(int i=1; i<dexOfDex.length; i++) dexOfDex[i].put(dexNumber[i-1], this);
    }

    // Debug Output
    public void getDebug(){
        String[] dexString = {"RBY/FRLG","Lets Go","GSC", "RSE", "DP", "Platinum", "HGSS", "BW", "B2W2", "KalosCentral", "KalosCoastal", "KalosMountain", "ORAS", "Alola", "Melemele", "Akala", "Ula'Ula", "Poni", "Ultra Alola", "Ultra Melemele", "Ultra Akala", "Ultra Ula'Ula", "Ultra Poni", "Galar", "Isle of Armor", "Crown Tundra", "Hisui", "Paldea", "Teal Mask", "Indigo Disk"};
        System.out.print("Dex Numbers\nNational: " + national);
        for(int i=0; i<dexOfDex.length; i++) for(int j : dexOfDex[i].keySet()) if(dexOfDex[i].get(j).equals(this)){
            System.out.print(", " + dexString[i] + ": " + j);
            break;
        }System.out.println("\nForms:");
        for(int i = 0; i < form.size(); i++) form.get(i).getDebug();
        System.out.println("-----");
    }

    //Get image from Serebii website
    public ImageIcon makeImage(String formSymbol, boolean shiny){
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
    public static void updateLabels(JLabel label, int region, int dlc, int selected, boolean shiny){
        Pokemon temp = getPokemon(region, dlc, selected);
        label.setText(temp.name);
        label.setIcon(temp.makeImage("", shiny));
    }

    // Creates Dex
    @SuppressWarnings("unchecked")
    public static void Dex(){
        // Initialize Dexs
        nationalDex = new Pokemon[1025];
        for(int i=0; i<nationalDex.length; i++) nationalDex[i] = new Pokemon();
        dexOfDex = new HashMap[]{kanto = new HashMap<>(), letsGo = new HashMap<>(), johto = new HashMap<>(), hoenn = new HashMap<>(),
            sinnoh = new HashMap<>(), platinum = new HashMap<>(), hgss = new HashMap<>(), unova = new HashMap<>(), b2w2 = new HashMap<>(),
            kalosCentral = new HashMap<>(), kalosCoastal = new HashMap<>(), kalosMountain = new HashMap<>(), oras = new HashMap<>(),
            alola = new HashMap<>(), melemele = new HashMap<>(), akala = new HashMap<>(), ulaula = new HashMap<>(), poni = new HashMap<>(),
            ultra = new HashMap<>(), ultraMelemele = new HashMap<>(), ultraAkala = new HashMap<>(), ultraUlaula = new HashMap<>(), ultraPoni = new HashMap<>(),
            galar = new HashMap<>(), isleOfArmor = new HashMap<>(), crownTundra = new HashMap<>(), legendsArceus = new HashMap<>(),
            paldea = new HashMap<>(), tealMask = new HashMap<>(), indigoDisk = new HashMap<>()
        };
        // Read from file
        try(BufferedReader buffReadDex = new BufferedReader(new FileReader("dex.csv")); BufferedReader buffReadStats = new BufferedReader(new FileReader("stats.csv"))){
            String line;
            buffReadDex.readLine();
            while ((line = buffReadDex.readLine()) != null) {
                // Initialize the Pokémon
                String[] lines = line.split(", ");
                nationalDex[Integer.parseInt(lines[1])-1].inputDex(lines); // Input the info
            }
            for(HashMap<Integer, Pokemon> i : dexOfDex) i.remove(-1);
            buffReadStats.readLine();
            while ((line = buffReadStats.readLine()) != null) {
                // Initialize each form of Pokémon
                String[] lines = line.split(", ");
                nationalDex[Integer.parseInt(lines[0])-1].form.add(new Form(lines));
            }
        }
        catch (FileNotFoundException | NumberFormatException e) {System.out.println("File not found/Number Format Exception");}
        catch (IOException ex) {Logger.getLogger(Pokemon.class.getName()).log(Level.SEVERE, null, ex);}
    }

    // Gets Pokémon from dex
    public static Pokemon getPokemon(int region, int dlc, int selected){
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

    // Gets the entire dex
    public static HashMap<Integer, Pokemon> getHashMap(int region, int dlc){
        HashMap<Integer, Pokemon> tempDex = new HashMap<>();
        int start = 1, end = 10;
        switch(region) {
            case 0 -> {start = 1; end = 1025;}
            case 1 -> { switch(dlc) { // Kanto
                case 0 -> {start = 1; end = 151;}
                case 1 -> tempDex = kanto;
                case 2 -> tempDex = letsGo;
            }} case 2 -> { switch(dlc) { // Johto
                case 0 -> {start = 152; end = 251;}
                case 1 -> tempDex = johto;
                case 2 -> tempDex = hgss;
            }} case 3 -> { switch(dlc) { // Hoenn
                case 0 -> {start = 252; end = 386;}
                case 1 -> tempDex = hoenn;
                case 2 -> tempDex = oras;
            }} case 4 -> { switch(dlc) { // Sinnoh + Hisui
                case 0 -> {start = 387; end = 493;}
                case 1 -> tempDex = sinnoh;
                case 2 -> tempDex = platinum;
                case 3 -> tempDex = legendsArceus;
            }} case 5 -> { switch(dlc) { // Unova
                case 0 -> {start = 494; end = 649;}
                case 1 -> tempDex = unova;
                case 2 -> tempDex = b2w2;
            }} case 6 -> { switch(dlc) { // Kalos
                case 0 -> {start = 650; end = 721;}
                case 1 -> tempDex = kalosCentral;
                case 2 -> tempDex = kalosCoastal;
                case 3 -> tempDex = kalosMountain;
            }} case 7 -> { switch(dlc) { // Alola
                case 0 -> {start = 722; end = 809;}
                case 1 -> tempDex = alola;
                case 2 -> tempDex = melemele;
                case 3 -> tempDex = akala;
                case 4 -> tempDex = ulaula;
                case 5 -> tempDex = poni;
                case 6 -> tempDex = ultra;
                case 7 -> tempDex = ultraMelemele;
                case 8 -> tempDex = ultraAkala;
                case 9 -> tempDex = ultraUlaula;
                case 10 -> tempDex = ultraPoni;
            }} case 8 -> { switch(dlc) { // Galar
                case 0 -> {start = 810; end = 905;}
                case 1 -> tempDex = galar;
                case 2 -> tempDex = isleOfArmor;
                case 3 -> tempDex = crownTundra;
            }} case 9 -> { switch(dlc) { // Paldea
                case 0 -> {start = 906; end = 1025;}
                case 1 -> tempDex = paldea;
                case 2 -> tempDex = tealMask;
                case 3 -> tempDex = indigoDisk;
            }} default -> System.out.println("Error: invalid region.");
        }
        if(dlc == 0){ for(Pokemon p : nationalDex) { // Gets the dex with a starting and end point
            if(p.national < start || p.national > end) continue;
            tempDex.put(p.national - start + 1, p);
        }} return new HashMap<>(tempDex);
    }
}