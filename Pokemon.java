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
    // Full Pokedex
    public static Pokemon[] pokedexList;
    // Regional Dex Numbers
    public int national, kanto, johto, hoenn, sinnoh, platinum, hgss, unova, b2w2,
    kalosCentral, kalosCoastal, kalosMountain, oras,
    alola, melemele, akala, ulaula, poni, ultra, ultraMelemele, ultraAkala, ultraUlaula, ultraPoni,
    galar, isleOfArmor, crownTundra, legendsArceus,
    paldea, kitakami, indigoDisk,
    letsGo;
    public String name, category;
    public ArrayList<Form> form;
    // Pokemon Constructor
    public Pokemon(String name, int national){
        this.name = name;
        this.national = national;
    }
    // Constructor from file
    public void inputDex(int[] dexNum){
        if(dexNum[0] <= 151) this.kanto = dexNum[0];
        else this.kanto = -1;
        this.letsGo = dexNum[0];
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
    }
    public void getDexNumbers(){
        int[] dexNums = {kanto, johto, hoenn, sinnoh, platinum, hgss, unova, b2w2, kalosCentral, kalosCoastal, kalosMountain, oras, alola, melemele, akala, ulaula, poni, ultra, ultraMelemele, ultraAkala, ultraUlaula, ultraPoni, galar, isleOfArmor, crownTundra, legendsArceus, paldea,kitakami, indigoDisk,};
        String[] dexString = {"RBY/FRLG/LetsGo","GSC", "RSE", "DP", "Platinum", "HGSS", "BW", "B2W2", "KalosCentral", "KalosCoastal", "KalosMountain", "ORAS", "Alola", "Melemele", "Akala", "Ula'Ula", "Poni", "Ultra Alola", "Ultra Melemele", "Ultra Akala", "Ultra Ula'Ula", "Ultra Poni", "Galar", "Isle of Armor", "Crown Tundra", "Hisui", "Paldea", "Kitakami", "Indigo Disk"};
        for(int i=0;i<dexNums.length;i++) System.out.print(dexString[i]+": "+dexNums[i]+", ");
    }
    //Get image from Serebii website
    public ImageIcon makeImage(String form){
        String dexString = (national<=0 || national > 1025) ? "001" : (national > 99) ? "" + national : (national > 9) ? "0" + national : (national > 0) ? "00" + national : "001",
        linkText = form.equals("Shiny") ? "Shiny/SV/new/" : "scarletviolet/pokemon/new/";
        try {
            BufferedImage image = ImageIO.read(new URI("https://serebii.net/" + linkText + dexString + ".png").toURL());
            return new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        }catch (URISyntaxException e) {}
        catch (IOException ex) {Logger.getLogger(Pokemon.class.getName()).log(Level.SEVERE, null, ex);}
        return null;
    }
    // Creates Dex
    public static void initializeDex(){
        pokedexList = new Pokemon[1025];
        try(BufferedReader buffRead = new BufferedReader(new FileReader("dex.txt"))){
            String line;
            buffRead.readLine();
            while ((line = buffRead.readLine()) != null) {
                // Initialize the Pokemon
                String[] lines = line.split(", ");
                Pokemon temp = new Pokemon(lines[0], Integer.parseInt(lines[1]));
                pokedexList[Integer.parseInt(lines[1])-1] = temp;
                // Input the dex number
                int[] dexNumber = new int[31];
                for(int i=2; i<dexNumber.length; i++) dexNumber[i-2] = Integer.parseInt(lines[i]);
                temp.inputDex(dexNumber);
            }
            buffRead.close();
        }
        catch (FileNotFoundException | NumberFormatException e) {System.out.println("File not found or Number Format Exception");}
        catch (IOException ex) {Logger.getLogger(Pokemon.class.getName()).log(Level.SEVERE, null, ex);}
    }
}