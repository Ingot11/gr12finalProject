import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class swingDex{
   // Window Variables
    private final JPanel headerPanel, middlePanel, bottomPanel;
    private final JFrame mainFrame;
    private static swingDex dex;
    // Call Window
    public static void main(String[] args){
        dex = new swingDex();
        dex.pokedex();
    }
    // Window Constructor
    public swingDex(){
        // Set Main Frame
        mainFrame = new JFrame("Pokedex List");
        mainFrame.setSize(600,600);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Add Panels
        mainFrame.add(headerPanel = new JPanel(new FlowLayout()));
        mainFrame.add(middlePanel = new JPanel(new FlowLayout()));
        mainFrame.add(bottomPanel = new JPanel(new FlowLayout()));
    }
    // Pokedex Window
    public void pokedex(){
        // Initialize Pokedex
        Pokemon.dexList();
        // Completionist Title and Caught and Seen Buttons
        headerPanel.add(new JLabel("Pokedex Completionist"));
        JButton caught = new JButton("Caught"), seen = new JButton("Seen");
        // Generation and DLC Selector
        JComboBox<String> generationSelector = new JComboBox<>(new String[]{"Kanto", "Johto", "Hoenn", "Sinnoh/Hisui", "Unova", "Kalos", "Alola", "Galar", "Paldea"}),
        dlcSelector = new JComboBox<>(new String[]{"Regional","Yellow","Let's Go Games"});
        // Generation Chooser Action Listener
        generationSelector.addActionListener((ActionEvent e) -> {
            // Choose Generation
            String[][] generation = {{"Regional", "Yellow", "Let's Go Games"}, /*Kanto*/
            {"Regional", "HeartGold and SoulSilver"}, /*Johto*/
            {"Regional", "Emerald","Omega Ruby and Alpha Sapphire"}, /*Hoenn*/
            {"Regional", "Platinum","Legends Arceus"}, /*Sinnoh + Hisui*/
            {"Regional", "Black 2 and White 2"}, /*Unova*/
            {"Regional", "Central", "Coastal", "Mountain", "Legends ZA"}, /*Kalos*/
            {"Regional", "Melemele", "Akala", "Ula'Ula", "Poni", "Ultra Regional", "Ultra Melemele", "Ultra Akala", "Ultra Ula'Ula","Ultra Poni"}, /*Alola*/
            {"Regional", "Isle of Armor", "Crown Tundra"}, /*Galar*/
            {"Regional", "Kitakami", "Indigo Disk"}}; /*Paldea*/
            if(generationSelector.getSelectedIndex() > generation.length) generationSelector.setSelectedIndex(0);
            dlcSelector.setModel(new DefaultComboBoxModel<>(generation[generationSelector.getSelectedIndex()]));
            dlcSelector.setSelectedIndex(0);
            mainFrame.revalidate(); // Resets mainFrame
        });
        //Image
        JTextField idTest = new JTextField(3);
        JLabel pokemonImage = new JLabel(Pokemon.pokedexList[737-1].name),
        idTestLabel = new JLabel("Input Pokedex Number:");
        middlePanel.add(idTestLabel);
        middlePanel.add(idTest);
        idTest.addActionListener((ActionEvent e) -> {
            int input = Integer.parseInt(idTest.getText());
            if(input <= 0 || input > 1025){
                pokemonImage.setIcon(Pokemon.pokedexList[0].makeImage("Shiny"));
                pokemonImage.setText(Pokemon.pokedexList[0].name);
            }else{
                pokemonImage.setIcon(Pokemon.pokedexList[input-1].makeImage("Shiny"));
                pokemonImage.setText(Pokemon.pokedexList[input-1].name);
            }
        });
        pokemonImage.setIcon(Pokemon.pokedexList[736].makeImage("Default"));
        // Add to Panels
        headerPanel.add(generationSelector);
        headerPanel.add(dlcSelector);
        bottomPanel.add(pokemonImage);
        bottomPanel.add(caught);
        bottomPanel.add(seen);
        // Normal Operations for Mainframe
        for(Component i:mainFrame.getComponents()) i.setBackground(Color.WHITE);
        mainFrame.setVisible(true);
    }
}