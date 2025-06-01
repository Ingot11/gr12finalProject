import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class swingDex extends JFrame{
    // Window Variables
    private final JComboBox<String> generationSelector, dlcSelector;
    private final JPanel selectorPanel, idPanel, infoPanel;
    private final JScrollPane pokeScroll;
    private final JLabel image, idLabel;
    private final JButton caught, seen;
    private final JTextField idInput;
    private final JList pokeList;

    // Call Window
    public static void main(String[] args){
        swingDex dex = new swingDex();
    }

    // Window Constructor
    public swingDex(){
        // Set Main Frame
        setTitle("Pokédex");
        setSize(600,600);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(450,450));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for(Component i:getComponents()) i.setBackground(Color.WHITE);
        // Initialize Pokedex
        Pokemon.Dex();

        // Id Inputter
        add(idPanel = new JPanel(new FlowLayout()), constraint(0,0,false));
        idPanel.add(idLabel = new JLabel("Dex Number:"));
        idPanel.add(idInput = new JTextField(3));

        // Selectors
        add(selectorPanel = new JPanel(new FlowLayout()), constraint(1,0,false));
        selectorPanel.add(generationSelector = new JComboBox<>(new String[]{"National", "Kanto", "Johto", "Hoenn", "Sinnoh/Hisui", "Unova", "Kalos", "Alola", "Galar", "Paldea"}));
        selectorPanel.add(dlcSelector = new JComboBox<>(new String[]{"National"}));

        // Pokemon Image / Caught and Seen
        add(image = new JLabel(Pokemon.nationalDex[737-1].name, Pokemon.nationalDex[737-1].makeImage("Default", false), 0), constraint(0,1,false));
        add(infoPanel = new JPanel(new FlowLayout()), constraint(0,2,false));
        infoPanel.add(caught = new JButton("Caught"));
        infoPanel.add(seen = new JButton("Seen"));

        // Scroll Dex
        add(pokeScroll = new JScrollPane(pokeList = new JList(new String[]{"Choose a region to begin!"})), constraint(1,1,true));
        setVisible(true);

        // Action Listeners
        generationSelector.addActionListener((ActionEvent e) -> {
            // Choose Generation
            String[][] generation = {{"National"}, /*National*/
            {"Regional", "Red/Blue/Yellow/FireRed/LeafGreen", "Let's Go"}, /*Kanto*/
            {"Regional", "Gold/Silver/Crystal", "HeartGold/SoulSilver"}, /*Johto*/
            {"Regional", "Ruby/Sapphire/Emerald", "OmegaRuby/AlphaSapphire"}, /*Hoenn*/
            {"Regional", "Diamond/Pearl", "Platinum", "Legends: Arceus"}, /*Sinnoh + Hisui*/
            {"Regional", "Black/White", "Black 2/White 2"}, /*Unova*/
            {"Regional", "KalosCentral", "KalosCoastal", "KalosMountain"}, /*Kalos*/
            {"Regional", "Sun/Moon", "Melemele", "Akala", "Ula'Ula", "Poni", "Ultra Sun/Ultra Moon", "Ultra Melemele", "Ultra Akala", "Ultra Ula'Ula", "Ultra Poni"}, /*Alola*/
            {"Regional", "Sword/Shield", "Isle of Armor", "Crown Tundra"}, /*Galar*/
            {"Regional", "Scarlet/Violet", "Teal Mask", "Indigo Disk"}}; /*Paldea*/
            if(generationSelector.getSelectedIndex() > generation.length) generationSelector.setSelectedIndex(0);
            dlcSelector.setModel(new DefaultComboBoxModel<>(generation[generationSelector.getSelectedIndex()]));
            dlcSelector.setSelectedIndex(0);
            revalidate(); // Resets frame
        });
        dlcSelector.addActionListener((ActionEvent e) -> {pokeList.setModel(Pokemon.getDex(generationSelector.getSelectedIndex(), dlcSelector.getSelectedIndex()));});
        idInput.addActionListener((ActionEvent e) -> {
            int input;
            try{input = Integer.parseInt(idInput.getText());}
            catch(NumberFormatException a){input = 1;}
            if(input <= 0 || input > 1025){
                image.setIcon(Pokemon.nationalDex[0].makeImage("", true));
                image.setText(Pokemon.nationalDex[0].name);
            }else{
                image.setIcon(Pokemon.nationalDex[input-1].makeImage("", true));
                image.setText(Pokemon.nationalDex[input-1].name);
                // Debug
                Pokemon.nationalDex[input-1].getDexNumbers();
                for(int i=0;i<Pokemon.nationalDex[input-1].form.size();i++) Pokemon.nationalDex[input-1].form.get(i).getStats();
                System.out.println("---");
            }
        });
    }
    
    // Set Constraints
    public static GridBagConstraints constraint(int x, int y, boolean isList){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0.5; constraints.weighty = 0.5;
        constraints.gridx = x; constraints.gridy = y;
        if(isList){
            constraints.gridheight = 2;
            constraints.fill = GridBagConstraints.BOTH;
            constraints.insets = new Insets(20,20,20,20);
        }
        return constraints;
    }
}