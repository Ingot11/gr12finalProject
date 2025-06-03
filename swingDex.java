import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
public class swingDex extends JFrame{
    // Window Variables
    private JComboBox<String> generationSelector, dlcSelector;
    private JPanel selectorPanel, idPanel, infoPanel;
    private JScrollPane pokeScroll;
    private JLabel image, idLabel;
    private JLabel[] info;
    private JButton caught, seen, select;
    private JTextField idInput;
    private JList<String> pokeList;
    private int selectedForm;
    
    // Call Window
    public static void main(String[] args){
        Pokemon.Dex(); // Initialize Pokédex
        swingDex dex = new swingDex();
    }

    // Window Constructor
    public swingDex(){
        // Set Main Frame
        setTitle("Pokédex");
        setSize(600, 600);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(450, 450));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Id Inputter
        add(idPanel = new JPanel(new FlowLayout()), constraint(0, 0, 0));
        idPanel.add(idLabel = new JLabel("Dex Number:"));
        idPanel.add(idInput = new JTextField(3));

        // Selectors
        add(selectorPanel = new JPanel(new FlowLayout()), constraint(1, 0, 0));
        selectorPanel.add(generationSelector = new JComboBox<>(new String[]{"National", "Kanto", "Johto", "Hoenn", "Sinnoh/Hisui", "Unova", "Kalos", "Alola", "Galar", "Paldea"}));
        selectorPanel.add(dlcSelector = new JComboBox<>(new String[]{"National"}));

        // Pokemon Image / More Info Button
        add(image = new JLabel(Pokemon.nationalDex[737-1].name, Pokemon.nationalDex[737-1].makeImage("Default", false), 0), constraint(0, 1, 0));
        add(select = new JButton("More Info"), constraint(0, 2, 0));

        // Scroll Dex
        add(pokeScroll = new JScrollPane(pokeList = new JList<>(Pokemon.getDex(0, 0))), constraint(1, 1, 1));
        for(Component i:getComponents()) i.setBackground(Color.WHITE);
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
        dlcSelector.addActionListener((ActionEvent e) -> {
            pokeList.setModel(Pokemon.getDex(generationSelector.getSelectedIndex(), dlcSelector.getSelectedIndex()));
            pokeList.setSelectedIndex(0);
        });
        pokeList.addListSelectionListener((ListSelectionEvent e) -> {
            image.setText(Pokemon.makeName(generationSelector.getSelectedIndex(), dlcSelector.getSelectedIndex(), pokeList.getSelectedIndex()));
            image.setIcon(Pokemon.makeImage(generationSelector.getSelectedIndex(), dlcSelector.getSelectedIndex(), pokeList.getSelectedIndex(), "", true));
        });
        idInput.addActionListener((ActionEvent e) -> {
            Pokemon input;
            try{input = Pokemon.nationalDex[Integer.parseInt(idInput.getText()) - 1];}
            catch(NumberFormatException|ArrayIndexOutOfBoundsException a){input = Pokemon.nationalDex[0];}
            image.setIcon(input.makeImage("", true));
            image.setText(input.name);
            // Debug
            input.getDexNumbers();
            for(int i=0; i<input.form.size(); i++) input.form.get(i).getStats();
            System.out.println("---");
        });
        select.addActionListener((ActionEvent e) -> {swingDex x = new swingDex(Pokemon.getPokemon(generationSelector.getSelectedIndex(), dlcSelector.getSelectedIndex(), pokeList.getSelectedIndex()));});
    }

    // Window to Display Pokemon
    public swingDex(Pokemon pokemon){
        info = new JLabel[5];
        setTitle(pokemon.name);
        setSize(700, 350);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 300));

        // Base Information
        selectedForm = 0;
        add(select = new JButton("Change Form"),constraint(0, 0, 0));
        add(image = new JLabel(pokemon.makeImage(pokemon.form.get(0).formSymbol, false)), constraint(0, 1, 2));
        add(idLabel = new JLabel(pokemon.form.get(0).name), constraint(1, 0, 0));
        add(info[0] = new JLabel(pokemon.form.get(0).category + " Pokémon"), constraint(1, 1, 0));
        add(info[1] = new JLabel(pokemon.form.get(0).getBaseStats()), constraint(1, 2, 0));
        add(info[2] = new JLabel(pokemon.form.get(0).getAbilities()), constraint(1, 3, 0));
        add(info[3] = new JLabel("Egg Cycles: " + pokemon.form.get(0).eggCycles), constraint(1, 4, 0));
        add(info[4] = new JLabel("Growth Rate: " + pokemon.form.get(0).growthRate), constraint(1, 5, 0));

        // Caught and Seen Buttons
        add(infoPanel = new JPanel(new FlowLayout()), constraint(0, 6, 0));
        infoPanel.add(caught = new JButton("Caught"));
        infoPanel.add(seen = new JButton("Seen"));
        setVisible(true);

        // Action Listeners
        select.addActionListener((ActionEvent e) -> {
            if(++selectedForm >= pokemon.form.size()) selectedForm = 0;
            image.setIcon(pokemon.makeImage(pokemon.form.get(selectedForm).formSymbol, false));
            idLabel.setText(pokemon.form.get(selectedForm).name);
            info[0].setText(pokemon.form.get(selectedForm).category + " Pokémon");
            info[1].setText(pokemon.form.get(selectedForm).getBaseStats());
            info[2].setText(pokemon.form.get(selectedForm).getAbilities());
            info[3].setText("Egg Cycles: " + pokemon.form.get(selectedForm).eggCycles);
            info[4].setText("Growth Rate: " + pokemon.form.get(selectedForm).growthRate);
        });
        seen.addActionListener((ActionEvent e) -> {pokemon.form.get(selectedForm).caughtSeen = 1;});
        caught.addActionListener((ActionEvent e) -> {pokemon.form.get(selectedForm).caughtSeen = 2;});
    }

    // Set Constraints
    public static GridBagConstraints constraint(int x, int y, int type){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0.5; constraints.weighty = 0.5;
        constraints.gridx = x; constraints.gridy = y;
        switch(type){
            case 1 -> { // Pokemon Scroll List
                constraints.gridheight = 2;
                constraints.fill = GridBagConstraints.BOTH;
                constraints.insets = new Insets(20, 20, 20, 20);
            }
            case 2 -> constraints.gridheight = 5;
        }
        return constraints;
    }
}