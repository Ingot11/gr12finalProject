import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class Menu extends JFrame {
    private JPanel panel1;
    private JLabel titleLabel;
    private JLabel regionLabel;
    private JComboBox regionSelect;
    private JLabel gameLabel;
    private JComboBox gameSelect;
    private JList dexList;
    private JLabel image;
    private JScrollBar scrollBar;

    public static void main(String[] args) {
        Pokemon.initializeDex();
        Menu menu = new Menu();
    }

    public Menu() {
        setContentPane(panel1);
        setSize(500,400);
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
                    {"Regional", "Sun/Moon", "Ultra Sun/Ultra Moon", "Melemele", "Akala", "Ula'Ula", "Poni", "Ultra Regional", "Ultra Melemele", "Ultra Akala", "Ultra Ula'Ula", "Ultra Poni"}, /*Alola*/
                    {"Regional", "Sword/Shield", "Isle of Armor", "Crown Tundra"}, /*Galar*/
                    {"Regional", "Scarlet/Violet", "Teal Mask", "Indigo Disk"}}; /*Paldea*/
            if(regionSelect.getSelectedIndex() > generation.length) regionSelect.setSelectedIndex(0);
            gameSelect.setModel(new DefaultComboBoxModel<>(generation[regionSelect.getSelectedIndex()]));
            gameSelect.setSelectedIndex(0);
            revalidate(); // Resets frame
        });

        gameSelect.addActionListener((ActionEvent a) -> {
            switch(regionSelect.getSelectedIndex()) {
                case 0 -> {
                    addPkmnToDex("national");
                }
                case 1 -> {
                    switch(gameSelect.getSelectedIndex()) {
                        case 1 -> {
                            addPkmnToDex("kanto");
                        }
                        case 2 -> {
                            addPkmnToDex("letsGo");
                        }
                    }
                }
                case 2 -> {
                    switch(gameSelect.getSelectedIndex()) {
                        case 1 -> {
                            addPkmnToDex("johto");
                        }
                        case 2 -> {
                            addPkmnToDex("hgss");
                        }
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
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.national < 0) continue;
                    tempDex.put(p.national, p);
                }
                for(int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }

            }
            case "kanto" -> {
                for (Pokemon p : Pokemon.pokedexList) {
                    if (p.kanto < 0) continue;
                    tempDex.put(p.kanto, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "letsGo" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.letsGo < 0) continue;
                    tempDex.put(p.letsGo, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "johto" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.johto < 0) continue;
                    tempDex.put(p.johto, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "hoenn" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.hoenn < 0) continue;
                    tempDex.put(p.hoenn, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "sinnoh" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.sinnoh < 0) continue;
                    tempDex.put(p.sinnoh, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "platinum" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.platinum < 0) continue;
                    tempDex.put(p.platinum, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "hgss" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.hgss < 0) continue;
                    tempDex.put(p.hgss, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "unova" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.unova < 0) continue;
                    tempDex.put(p.unova, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "b2w2" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.b2w2 < 0) continue;
                    tempDex.put(p.b2w2, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "kalosCentral" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.kalosCentral < 0) continue;
                    tempDex.put(p.kalosCentral, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "kalosCoastal" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.kalosCoastal < 0) continue;
                    tempDex.put(p.kalosCoastal, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "kalosMountain" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.kalosMountain < 0) continue;
                    tempDex.put(p.kalosMountain, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "oras" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.oras < 0) continue;
                    tempDex.put(p.oras, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "alola" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.alola < 0) continue;
                    tempDex.put(p.alola, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "melemele" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.melemele < 0) continue;
                    tempDex.put(p.melemele, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "akala" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.akala < 0) continue;
                    tempDex.put(p.akala, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "ulaula" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.ulaula < 0) continue;
                    tempDex.put(p.ulaula, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "poni" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.poni < 0) continue;
                    tempDex.put(p.poni, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "ultra" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.ultra < 0) continue;
                    tempDex.put(p.ultra, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "ultraMelemele" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.ultraMelemele < 0) continue;
                    tempDex.put(p.ultraMelemele, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "ultraAkala" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.ultraAkala < 0) continue;
                    tempDex.put(p.ultraAkala, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "ultraUlaula" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.ultraUlaula < 0) continue;
                    tempDex.put(p.ultraUlaula, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "ultraPoni" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.ultraPoni < 0) continue;
                    tempDex.put(p.ultraPoni, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "galar" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.galar < 0) continue;
                    tempDex.put(p.galar, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "isleOfArmor" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.isleOfArmor < 0) continue;
                    tempDex.put(p.isleOfArmor, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "crownTundra" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.crownTundra < 0) continue;
                    tempDex.put(p.crownTundra, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "legendsArceus" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.legendsArceus < 0) continue;
                    tempDex.put(p.legendsArceus, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "paldea" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.paldea < 0) continue;
                    tempDex.put(p.paldea, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "kitakami" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.kitakami < 0) continue;
                    tempDex.put(p.kitakami, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            case "indigoDisk" -> {
                for(Pokemon p : Pokemon.pokedexList) {
                    if(p.indigoDisk < 0) continue;
                    tempDex.put(p.indigoDisk, p);
                }
                for (int i : tempDex.keySet()) {
                    tempModel.addElement(i + ". " + tempDex.get(i).name);
                }
            }
            default -> System.out.println("Error: invalid region.");
        }
        dexList.setModel(tempModel);
    }
}
