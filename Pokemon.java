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

    // Pokemon Constructor
    public Pokemon(String name, int national){
        this.name = name;
        this.national = national;
        this.form = new ArrayList<>();
    }

    // Constructor from file
    public void inputDex(int[] dexNumber){
        if(dexNumber[0] <= 151) kanto.put(dexNumber[0], this);
        for(int i=1; i<dexOfDex.length;i++) dexOfDex[i].put(dexNumber[i-1],this);
    }
    public void getDexNumbers(){
        String[] dexString = {"RBY/FRLG","Lets Go","GSC", "RSE", "DP", "Platinum", "HGSS", "BW", "B2W2", "KalosCentral", "KalosCoastal", "KalosMountain", "ORAS", "Alola", "Melemele", "Akala", "Ula'Ula", "Poni", "Ultra Alola", "Ultra Melemele", "Ultra Akala", "Ultra Ula'Ula", "Ultra Poni", "Galar", "Isle of Armor", "Crown Tundra", "Hisui", "Paldea", "Teal Mask", "Indigo Disk"};
        System.out.print("National" + ": " + national);
        for(int i=0;i<dexOfDex.length;i++) for(int j: dexOfDex[i].keySet()) {
            if(dexOfDex[i].get(j).equals(this)){
                System.out.print(", "+dexString[i] + ": " + j);
                break;
            }
        }
        System.out.println();
    }

    //Get image from Serebii website
    public ImageIcon makeImage(String formSymbol, boolean shiny){
        String dexString = (national <= 0 || national > 1025) ? "001" : (national > 99) ? "" + national : (national > 9) ? "0" + national : (national > 0) ? "00" + national : "001",
        linkText = shiny ? "Shiny/SV/new/" : "scarletviolet/pokemon/new/";
        try {
            BufferedImage image = ImageIO.read(new URI("https://serebii.net/" + linkText + dexString + ".png").toURL());
            return new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        }catch (URISyntaxException e) {}
        catch (IOException ex) {Logger.getLogger(Pokemon.class.getName()).log(Level.SEVERE, null, ex);}
        return null;
    }
    
    // Creates Dex
    public static void Dex(){
        // Initialize Dexs
        nationalDex = new Pokemon[1025];
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
                // Initialize the Pokemon
                String[] lines = line.split(", ");
                Pokemon temp = new Pokemon(lines[0], Integer.parseInt(lines[1]));
                nationalDex[Integer.parseInt(lines[1])-1] = temp;
                // Input the dex number
                int[] dexNumber = new int[31];
                for(int i=2; i<dexNumber.length; i++) dexNumber[i-2] = Integer.parseInt(lines[i]);
                temp.inputDex(dexNumber);
            }
            for(HashMap<Integer, Pokemon> i : dexOfDex) i.remove(-1);
            buffReadStats.readLine();
            while ((line = buffReadStats.readLine()) != null) {
                String[] lines = line.split(", ");
                nationalDex[Integer.parseInt(lines[0])-1].form.add(new Form(lines));
            }
            buffReadDex.close();
            buffReadStats.close();
        }
        catch (FileNotFoundException | NumberFormatException e) {System.out.println("File not found or Number Format Exception");}
        catch (IOException ex) {Logger.getLogger(Pokemon.class.getName()).log(Level.SEVERE, null, ex);}
    }
    // Gets Pokemon from dex
    public static DefaultListModel<String> getDex(int region, int dlc) {
        int start = 1, end = 1;
        DefaultListModel<String> tempModel = new DefaultListModel<>();
        HashMap<Integer, Pokemon> tempDex = new HashMap<>();
        switch(region) {
            case 0 -> {
                start = 1;
                end = 1025;
            }
            case 1 -> { // Kanto
                switch(dlc) {
                    case 0 -> {
                        start = 1;
                        end = 151;
                    }
                    case 1 -> tempDex = new HashMap<>(kanto);
                    case 2 -> tempDex = new HashMap<>(letsGo);
                }
            }
            case 2 -> { // Johto
                switch(dlc) {
                    case 0 -> {
                        start = 152;
                        end = 251;
                    }
                    case 1 -> tempDex = new HashMap<>(johto);
                    case 2 -> tempDex = new HashMap<>(hgss);
                }
            }
            case 3 -> { // Hoenn
                switch(dlc) {
                    case 0 -> {
                        start = 252;
                        end = 386;
                    }
                    case 1 -> tempDex = new HashMap<>(hoenn);
                    case 2 -> tempDex = new HashMap<>(oras);
                }
            }
            case 4 -> { // Sinnoh + Hisui
                switch(dlc) {
                    case 0 -> {
                        start = 387;
                        end = 493;
                    }
                    case 1 -> tempDex = new HashMap<>(sinnoh);
                    case 2 -> tempDex = new HashMap<>(platinum);
                    case 3 -> tempDex = new HashMap<>(legendsArceus);
                }
            }
            case 5 -> { // Unova
                switch(dlc) {
                    case 0 -> {
                        start = 494;
                        end = 649;
                    }
                    case 1 -> tempDex = new HashMap<>(unova);
                    case 2 -> tempDex = new HashMap<>(b2w2);
                }
            }
            case 6 -> { // Kalos
                switch(dlc) {
                    case 0 -> {
                        start = 650;
                        end = 721;
                    }
                    case 1 -> tempDex = new HashMap<>(kalosCentral);
                    case 2 -> tempDex = new HashMap<>(kalosCoastal);
                    case 3 -> tempDex = new HashMap<>(kalosMountain);
                }
            }
            case 7 -> { // Alola
                switch(dlc) {
                    case 0 -> {
                        start = 722;
                        end = 809;
                    }
                    case 1 -> tempDex = new HashMap<>(alola);
                    case 2 -> tempDex = new HashMap<>(melemele);
                    case 3 -> tempDex = new HashMap<>(akala);
                    case 4 -> tempDex = new HashMap<>(ulaula);
                    case 5 -> tempDex = new HashMap<>(poni);
                    case 6 -> tempDex = new HashMap<>(ultra);
                    case 7 -> tempDex = new HashMap<>(ultraMelemele);
                    case 8 -> tempDex = new HashMap<>(ultraAkala);
                    case 9 -> tempDex = new HashMap<>(ultraUlaula);
                    case 10 -> tempDex = new HashMap<>(ultraPoni);
                }
            }
            case 8 -> { // Galar
                switch(dlc) {
                    case 0 -> {
                        start = 810;
                        end = 905;
                    }
                    case 1 -> tempDex = new HashMap<>(galar);
                    case 2 -> tempDex = new HashMap<>(isleOfArmor);
                    case 3 -> tempDex = new HashMap<>(crownTundra);
                }
            }
            case 9 -> { // Paldea
                switch(dlc) {
                    case 0 -> {
                        start = 906;
                        end = 1025;
                    }
                    case 1 -> tempDex = new HashMap<>(paldea);
                    case 2 -> tempDex = new HashMap<>(tealMask);
                    case 3 -> tempDex = new HashMap<>(indigoDisk);
                }
            }
            default ->{
                System.out.println("Error: invalid region.");
                tempModel.addElement("-1. MissingNo.");
                return tempModel;
            }
        }
        if(dlc == 0){
            for(Pokemon p : nationalDex) {
                if(p.national < start || p.national > end) continue;
                tempDex.put(p.national - start + 1, p);
            }
        }
        for (int i : tempDex.keySet()) tempModel.addElement(i + ". " + tempDex.get(i).name);
        return tempModel;
    }
}