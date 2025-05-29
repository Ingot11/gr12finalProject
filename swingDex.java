import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class swingDex extends JFrame{
    // Window Variables
    private final JPanel headerPanel, middlePanel, bottomPanel;
    private final JTextField idInput;
    private final JButton caught, seen;
    private final JLabel headerText, image, idLabel;
    private final JComboBox<String> generationSelector, dlcSelector;

    // Call Window
    public static void main(String[] args){
        swingDex dex = new swingDex();
    }

    // Window Constructor
    public swingDex(){
        // Set Main Frame
        setTitle("Pokedex List");
        setSize(600,600);
        setLayout(new GridLayout(3, 1));
        setMinimumSize(new Dimension(450,450));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for(Component i:getComponents()) i.setBackground(Color.WHITE);
        // Add to Frame
        add(headerPanel = new JPanel(new FlowLayout()));
        add(middlePanel = new JPanel(new FlowLayout()));
        add(bottomPanel = new JPanel(new FlowLayout()));

        // Initialize Pokedex
        Pokemon.Dex();
        // Defining Variables and adding it to Panels
        headerPanel.add(headerText = new JLabel("Pokedex Completionist"));
        headerPanel.add(generationSelector = new JComboBox<>(new String[]{"National", "Kanto", "Johto", "Hoenn", "Sinnoh/Hisui", "Unova", "Kalos", "Alola", "Galar", "Paldea/Teal Mask/Blueberry Academy"}));
        headerPanel.add(dlcSelector = new JComboBox<>(new String[]{"National"}));
        middlePanel.add(idLabel = new JLabel("Input Pokedex Number:"));
        middlePanel.add(idInput = new JTextField(3));
        bottomPanel.add(image = new JLabel(Pokemon.nationalDex[737-1].name, Pokemon.nationalDex[737-1].makeImage("Default"), 0));
        bottomPanel.add(caught = new JButton("Caught"));
        bottomPanel.add(seen = new JButton("Seen"));
        setVisible(true);

        // Action Listeners
        generationSelector.addActionListener((ActionEvent e) -> {
            // Choose Generation
            String[][] generation = {{"National"}, /*National*/
            {"Regional", "Red/Blue/Yellow/FireRed/LeafGreen", "Let's Go"}, /*Kanto*/
            {"Regional", "Gold/Silver/Crystal", "HeartGold/SoulSilver"}, /*Johto*/
            {"Regional", "Ruby/Sapphire/Emerald", "Omega Ruby/Alpha Sapphire"}, /*Hoenn*/
            {"Regional", "Diamond/Pearl", "Platinum", "Legends: Arceus"}, /*Sinnoh + Hisui*/
            {"Regional", "Black/White", "Black 2/White 2"}, /*Unova*/
            {"Regional", "X/Y", "Central", "Coastal", "Mountain"}, /*Kalos*/
            {"Regional", "Sun/Moon", "Melemele", "Akala", "Ula'Ula", "Poni", "Ultra Sun/Ultra Moon", "Ultra Melemele", "Ultra Akala", "Ultra Ula'Ula", "Ultra Poni"}, /*Alola*/
            {"Regional", "Sword/Shield", "Isle of Armor", "Crown Tundra"}, /*Galar*/
            {"Regional", "Scarlet/Violet", "Teal Mask", "Indigo Disk"}}; /*Paldea*/
            if(generationSelector.getSelectedIndex() > generation.length) generationSelector.setSelectedIndex(0);
            dlcSelector.setModel(new DefaultComboBoxModel<>(generation[generationSelector.getSelectedIndex()]));
            dlcSelector.setSelectedIndex(0);
            revalidate(); // Resets frame
        });
        
        idInput.addActionListener((ActionEvent e) -> {
            int input;
            try{input = Integer.parseInt(idInput.getText());}
            catch(NumberFormatException a){input = 1;}
            if(input <= 0 || input > 1025){
                image.setIcon(Pokemon.nationalDex[0].makeImage("Shiny"));
                image.setText(Pokemon.nationalDex[0].name);
            }else{
                image.setIcon(Pokemon.nationalDex[input-1].makeImage("Shiny"));
                image.setText(Pokemon.nationalDex[input-1].name);
                // Debug
                Pokemon.nationalDex[input-1].getDexNumbers();
                for(int i=0;i<Pokemon.nationalDex[input-1].form.size();i++) Pokemon.nationalDex[input-1].form.get(i).getStats();
                System.out.print("---");
            }
        });
    }
}