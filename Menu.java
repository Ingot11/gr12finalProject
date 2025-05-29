import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class Menu extends JFrame {
    private JPanel panel1;
    private JLabel titleLabel, regionLabel, gameLabel, image;
    private JComboBox regionSelect, gameSelect;
    private JScrollBar scrollBar;
    private JList dexList;

    public static void main(String[] args) {
        Pokemon.Dex();
        Menu menu = new Menu();
    }

    public Menu() {
        setContentPane(panel1);
        setSize(500,400);
        setMinimumSize(new Dimension(400,300));
        setTitle("Pokedex");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        regionSelect.addActionListener((ActionEvent a) -> {
            String[][] generation = {{"National"},
                {"Regional", "Red/Blue/Yellow/FireRed/LeafGreen", "Let's Go"}, /*Kanto*/
                {"Regional", "Gold/Silver/Crystal", "HeartGold/SoulSilver"}, /*Johto*/
                {"Regional", "Ruby/Sapphire/Emerald", "Omega Ruby/Alpha Sapphire"}, /*Hoenn*/
                {"Regional", "Diamond/Pearl", "Platinum", "Legends: Arceus"}, /*Sinnoh + Hisui*/
                {"Regional", "Black/White", "Black 2/White 2"}, /*Unova*/
                {"Regional", "X/Y", "Central", "Coastal", "Mountain"}, /*Kalos*/
                {"Regional", "Sun/Moon", "Melemele", "Akala", "Ula'Ula", "Poni", "Ultra Sun/Ultra Moon", "Ultra Melemele", "Ultra Akala", "Ultra Ula'Ula", "Ultra Poni"}, /*Alola*/
                {"Regional", "Sword/Shield", "Isle of Armor", "Crown Tundra"}, /*Galar*/
                {"Regional", "Scarlet/Violet", "Teal Mask", "Indigo Disk"}}; /*Paldea*/
            if(regionSelect.getSelectedIndex() > generation.length) regionSelect.setSelectedIndex(0);
            gameSelect.setModel(new DefaultComboBoxModel<>(generation[regionSelect.getSelectedIndex()]));
            gameSelect.setSelectedIndex(0);
            revalidate(); // Resets frame
        });

        gameSelect.addActionListener((ActionEvent a) -> {
            switch(regionSelect.getSelectedIndex()) {
                case 0 -> addPkmnToDex("national");
                case 1 -> { // Kanto
                    switch(gameSelect.getSelectedIndex()) {
                        case 1 -> addPkmnToDex("kanto");
                        case 2 -> addPkmnToDex("letsGo");
                    }
                }
                case 2 -> { // Johto
                    switch(gameSelect.getSelectedIndex()) {
                        case 1 -> addPkmnToDex("johto");
                        case 2 -> addPkmnToDex("hgss");
                    }
                }
                case 3 -> { // Hoenn
                    switch(gameSelect.getSelectedIndex()) {
                        case 1 -> addPkmnToDex("hoenn");
                        case 2 -> addPkmnToDex("oras");
                    }
                }
                case 4 -> { // Sinnoh + Hisui
                    switch(gameSelect.getSelectedIndex()) {
                        case 1 -> addPkmnToDex("sinnoh");
                        case 2 -> addPkmnToDex("platinum");
                        case 3 -> addPkmnToDex("legendsArceus");
                    }
                }
                case 5 -> { // Unova
                    switch(gameSelect.getSelectedIndex()) {
                        case 1 -> addPkmnToDex("unova");
                        case 2 -> addPkmnToDex("b2w2");
                    }
                }
                case 6 -> { // Kalos
                    switch(gameSelect.getSelectedIndex()) {
                        case 1 -> addPkmnToDex("kalosCentral");
                        case 2 -> addPkmnToDex("kalosCoastal");
                        case 3 -> addPkmnToDex("kalosMountain");
                    }
                }
                case 7 -> { // Alola
                    switch(gameSelect.getSelectedIndex()) {
                        case 1 -> addPkmnToDex("alola");
                        case 2 -> addPkmnToDex("melemele");
                        case 3 -> addPkmnToDex("akala");
                        case 4 -> addPkmnToDex("ulaula");
                        case 5 -> addPkmnToDex("poni");
                        case 6 -> addPkmnToDex("ultra");
                        case 7 -> addPkmnToDex("ultraMelemele");
                        case 8 -> addPkmnToDex("ultraAkala");
                        case 9 -> addPkmnToDex("ultraUlaula");
                        case 10 -> addPkmnToDex("ultraPoni");
                    }
                }
                case 8 -> { // Galar
                    switch(gameSelect.getSelectedIndex()) {
                        case 1 -> addPkmnToDex("galar");
                        case 2 -> addPkmnToDex("isleOfArmor");
                        case 3 -> addPkmnToDex("crownTundra");
                    }
                }
                case 9 -> { // Paldea
                    switch(gameSelect.getSelectedIndex()) {
                        case 1 -> addPkmnToDex("paldea");
                        case 2 -> addPkmnToDex("tealMask");
                        case 3 -> addPkmnToDex("indigoDisk");
                    }
                }
            }
        });
    }

    public void addPkmnToDex(String regionDex) {
        DefaultListModel<String> tempModel = new DefaultListModel<>();
        HashMap<Integer, Pokemon> tempDex = new HashMap<>();
        switch(regionDex) {
            case "national" -> {
                for(Pokemon p : Pokemon.nationalDex) {
                    if(p.national < 0) continue;
                    tempDex.put(p.national, p);
                }
            }
            // Kanto
            case "kanto" -> tempDex = new HashMap<>(Pokemon.kanto);
            case "letsGo" -> tempDex = new HashMap<>(Pokemon.letsGo);
            // Johto
            case "johto" -> tempDex = new HashMap<>(Pokemon.johto);
            case "hgss" -> tempDex = new HashMap<>(Pokemon.hgss);
            // Hoenn
            case "hoenn" -> tempDex = new HashMap<>(Pokemon.hoenn);
            case "oras" -> tempDex = new HashMap<>(Pokemon.oras);
            // Sinnoh
            case "sinnoh" -> tempDex = new HashMap<>(Pokemon.sinnoh);
            case "platinum" -> tempDex = new HashMap<>(Pokemon.platinum);
            case "legendsArceus" -> tempDex = new HashMap<>(Pokemon.legendsArceus);
            // Unova
            case "unova" -> tempDex = new HashMap<>(Pokemon.unova);
            case "b2w2" -> tempDex = new HashMap<>(Pokemon.b2w2);
            // Kalos
            case "kalosCentral" -> tempDex = new HashMap<>(Pokemon.kalosCentral);
            case "kalosCoastal" -> tempDex = new HashMap<>(Pokemon.kalosCoastal);
            case "kalosMountain" -> tempDex = new HashMap<>(Pokemon.kalosMountain);
            // Alola
            case "alola" -> tempDex = new HashMap<>(Pokemon.alola);
            case "melemele" -> tempDex = new HashMap<>(Pokemon.melemele);
            case "akala" -> tempDex = new HashMap<>(Pokemon.akala);
            case "ulaula" -> tempDex = new HashMap<>(Pokemon.ulaula);
            case "poni" -> tempDex = new HashMap<>(Pokemon.poni);
            case "ultra" -> tempDex = new HashMap<>(Pokemon.ultra);
            case "ultraMelemele" -> tempDex = new HashMap<>(Pokemon.ultraMelemele);
            case "ultraAkala" -> tempDex = new HashMap<>(Pokemon.ultraAkala);
            case "ultraUlaula" -> tempDex = new HashMap<>(Pokemon.ultraUlaula);
            case "ultraPoni" -> tempDex = new HashMap<>(Pokemon.ultraPoni);
            // Galar
            case "galar" -> tempDex = new HashMap<>(Pokemon.galar);
            case "isleOfArmor" -> tempDex = new HashMap<>(Pokemon.isleOfArmor);
            case "crownTundra" -> tempDex = new HashMap<>(Pokemon.crownTundra);
            // Paldea
            case "paldea" -> tempDex = new HashMap<>(Pokemon.paldea);
            case "tealMask" -> tempDex = new HashMap<>(Pokemon.tealMask);
            case "indigoDisk" -> tempDex = new HashMap<>(Pokemon.indigoDisk);
            default ->{
                System.out.println("Error: invalid region.");
                dexList.setModel(tempModel);
                return;
            }
        }
        for (int i : tempDex.keySet()) tempModel.addElement(i + ". " + tempDex.get(i).name);
        dexList.setModel(tempModel);
    }
}
