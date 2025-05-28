import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class swingDex{
    // Window Variables
    private final JPanel headerPanel, middlePanel, bottomPanel;
    private final JFrame mainFrame;
    // Call Window
    public static void main(String[] args){
        swingDex dex = new swingDex();
        dex.pokedex();
    }
    // Window Constructor
    public swingDex(){
        // Set Main Frame
        mainFrame = new JFrame("Pokedex List");
        mainFrame.setSize(600,600);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.setMinimumSize(new Dimension(450,450));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Add Panels
        mainFrame.add(headerPanel = new JPanel(new FlowLayout()));
        mainFrame.add(middlePanel = new JPanel(new FlowLayout()));
        mainFrame.add(bottomPanel = new JPanel(new FlowLayout()));
    }
    // Pokedex Window
    private JTextField idInput;
    private JButton caught, seen;
    private JLabel headerText, pokemonImage, idLabel;
    private JComboBox<String> generationSelector, dlcSelector;
    public void pokedex(){
        // Initialize Pokedex
        Pokemon.initializeDex();
        // Header Text, Caught and Seen Buttons
        headerText = new JLabel("Pokedex Completionist");
        caught = new JButton("Caught");
        seen = new JButton("Seen");
        // Generation and DLC Selector
        generationSelector = new JComboBox<>(new String[]{"Kanto", "Johto", "Hoenn", "Sinnoh/Hisui", "Unova", "Kalos", "Alola", "Galar", "Paldea"});
        dlcSelector = new JComboBox<>(new String[]{"Regional", "Let's Go"});
        //Image from ID
        pokemonImage = new JLabel(Pokemon.pokedexList[737-1].name, Pokemon.pokedexList[737-1].makeImage("Default"), 0);
        idLabel = new JLabel("Input Pokedex Number:");
        idInput = new JTextField(3);
        // Add All to Panels
        headerPanel.add(headerText);
        headerPanel.add(generationSelector);
        headerPanel.add(dlcSelector);
        middlePanel.add(idLabel);
        middlePanel.add(idInput);
        bottomPanel.add(pokemonImage);
        bottomPanel.add(caught);
        bottomPanel.add(seen);
        // Action Listeners
        generationSelector.addActionListener((ActionEvent e) -> {
            // Choose Generation
            String[][] generation = {{"Regional", "Let's Go Games"}, /*Kanto*/
            {"Regional", "HeartGold and SoulSilver"}, /*Johto*/
            {"Regional", "Omega Ruby and Alpha Sapphire"}, /*Hoenn*/
            {"Regional", "Platinum", "Legends Arceus"}, /*Sinnoh + Hisui*/
            {"Regional", "Black 2 and White 2"}, /*Unova*/
            {"Regional", "Coastal", "Mountain", "Legends ZA"}, /*Kalos*/
            {"Regional", "Melemele", "Akala", "Ula'Ula", "Poni", "Ultra Regional", "Ultra Melemele", "Ultra Akala", "Ultra Ula'Ula","Ultra Poni"}, /*Alola*/
            {"Regional", "Isle of Armor", "Crown Tundra"}, /*Galar*/
            {"Regional", "Kitakami", "Indigo Disk"}}; /*Paldea*/
            if(generationSelector.getSelectedIndex() > generation.length) generationSelector.setSelectedIndex(0);
            dlcSelector.setModel(new DefaultComboBoxModel<>(generation[generationSelector.getSelectedIndex()]));
            dlcSelector.setSelectedIndex(0);
            mainFrame.revalidate(); // Resets frame
        });
        idInput.addActionListener((ActionEvent e) -> {
            int input;
            try{input = Integer.parseInt(idInput.getText());}
            catch(NumberFormatException a){input = 1;}
            if(input <= 0 || input > 1025){
                pokemonImage.setIcon(Pokemon.pokedexList[0].makeImage("Shiny"));
                pokemonImage.setText(Pokemon.pokedexList[0].name);
            }else{
                pokemonImage.setIcon(Pokemon.pokedexList[input-1].makeImage("Shiny"));
                pokemonImage.setText(Pokemon.pokedexList[input-1].name);
            }
        });
        // Normal Operations for Mainframe
        for(Component i:mainFrame.getComponents()) i.setBackground(Color.WHITE); // Sets Background to Pure White
        mainFrame.setVisible(true); // Makes Frame Visible
    }
}