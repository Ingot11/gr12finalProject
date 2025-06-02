import java.awt.*;
import javax.swing.*;
public class swingDex extends JFrame{
    // Window Variables
    private JComboBox<String> generationSelector, dlcSelector;
    private JPanel selectorPanel, idPanel, infoPanel;
    private JScrollPane pokeScroll;
    private JLabel image, idLabel;
    private JButton caught, seen;
    private JTextField idInput;
    private JList<String> pokeList;

    // Call Window
    public static void main(String[] args){
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
        Pokemon.Dex(); // Initialize Pokedex

        // Id Inputter
        add(idPanel = new JPanel(new FlowLayout()), constraint(0, 0, false));
        idPanel.add(idLabel = new JLabel("Dex Number:"));
        idPanel.add(idInput = new JTextField(3));

        // Selectors
        add(selectorPanel = new JPanel(new FlowLayout()), constraint(1, 0, false));
        selectorPanel.add(generationSelector = new JComboBox<>(new String[]{"National", "Kanto", "Johto", "Hoenn", "Sinnoh/Hisui", "Unova", "Kalos", "Alola", "Galar", "Paldea"}));
        selectorPanel.add(dlcSelector = new JComboBox<>(new String[]{"National"}));

        // Pokemon Image / Caught and Seen
        add(image = new JLabel(Pokemon.nationalDex[737-1].name, Pokemon.nationalDex[737-1].makeImage("Default", false), 0), constraint(0, 1, false));
        add(infoPanel = new JPanel(new FlowLayout()), constraint(0, 2, false));
        infoPanel.add(caught = new JButton("Caught"));
        infoPanel.add(seen = new JButton("Seen"));

        // Scroll Dex
        add(pokeScroll = new JScrollPane(pokeList = new JList<>(Pokemon.getDex(0, 0))), constraint(1, 1, true));
        for(Component i:getComponents()) i.setBackground(Color.WHITE);
        setVisible(true);

        // Action Listeners
        generationSelector.addActionListener((_) -> {
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
        dlcSelector.addActionListener((_) -> {
            pokeList.setModel(Pokemon.getDex(generationSelector.getSelectedIndex(), dlcSelector.getSelectedIndex()));
            pokeList.setSelectedIndex(0);
        });
        pokeList.addListSelectionListener((_) -> {
            image.setText(Pokemon.makeName(generationSelector.getSelectedIndex(), dlcSelector.getSelectedIndex(), pokeList.getSelectedIndex()));
            image.setIcon(Pokemon.makeImage(generationSelector.getSelectedIndex(), dlcSelector.getSelectedIndex(), pokeList.getSelectedIndex(), "", true));
        });
        idInput.addActionListener((_) -> {
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
    }

    // Window to Display Pokemon
    public swingDex(Pokemon pokemon){
        setTitle(pokemon.name);
        setSize(500, 500);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(350, 350));
    }

    // Set Constraints
    public static GridBagConstraints constraint(int x, int y, boolean isList){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0.5; constraints.weighty = 0.5;
        constraints.gridx = x; constraints.gridy = y;
        if(isList){
            constraints.gridheight = 2;
            constraints.fill = GridBagConstraints.BOTH;
            constraints.insets = new Insets(20, 20, 20, 20);
        }
        return constraints;
    }
}