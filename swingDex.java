import java.awt.*;
import javax.swing.*;
public class swingDex extends JFrame{
    // Window Variables
    private JPanel selectorPanel, idPanel, infoPanel;
    private JButton caught, seen, select;
    private JComboBox<String> gen, dlc;
    private int visualForm, baseForm;
    private JList<String> pokeList;
    private JLabel image, idLabel;
    private JTextField idInput;
    private JLabel[] info;
    
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
        selectorPanel.add(gen = new JComboBox<>(new String[]{"National", "Kanto", "Johto", "Hoenn", "Sinnoh/Hisui", "Unova", "Kalos", "Alola", "Galar", "Paldea"}));
        selectorPanel.add(dlc = new JComboBox<>(new String[]{"National"}));

        // Pokemon Image / More Info Button
        add(image = new JLabel(), constraint(0, 1, 0));
        add(select = new JButton("More Info"), constraint(0, 2, 0));
        Pokemon.updateLabels(image, 0, 0, 0, false);

        // Scroll Dex
        add(new JScrollPane(pokeList = new JList<>(Pokemon.getDex(0, 0))), constraint(1, 1, 1));
        setVisible(true);

        // Action Listeners
        gen.addActionListener(a -> {
            // Choose Generation
            String[][] generation = {{"National"}, /*National*/
            {"Regional", "Red/Blue/Yellow/FireRed/LeafGreen", "Let's Go"}, /*Kanto*/
            {"Regional", "Gold/Silver/Crystal", "HeartGold/SoulSilver"}, /*Johto*/
            {"Regional", "Ruby/Sapphire/Emerald", "OmegaRuby/AlphaSapphire"}, /*Hoenn*/
            {"Regional", "Diamond/Pearl", "Platinum", "Legends: Arceus"}, /*Sinnoh + Hisui*/
            {"Regional", "Black/White", "Black 2/White 2"}, /*Unova*/
            {"Regional", "KalosCentral", "KalosCoastal", "KalosMountain"}, /*Kalos*/
            {"Regional", "Sun/Moon", "Melemele", "Akala", "Ula'Ula", "Poni", /*Alola*/
            "Ultra Sun/Ultra Moon", "Ultra Melemele", "Ultra Akala", "Ultra Ula'Ula", "Ultra Poni"}, /*Ultra Alola*/
            {"Regional", "Sword/Shield", "Isle of Armor", "Crown Tundra"}, /*Galar*/
            {"Regional", "Scarlet/Violet", "Teal Mask", "Indigo Disk"}}; /*Paldea*/
            if(gen.getSelectedIndex() > generation.length) gen.setSelectedIndex(0);
            dlc.setModel(new DefaultComboBoxModel<>(generation[gen.getSelectedIndex()]));
            dlc.setSelectedIndex(0);
            revalidate(); // Resets frame
        });
        dlc.addActionListener(a -> {
            pokeList.setModel(Pokemon.getDex(gen.getSelectedIndex(), dlc.getSelectedIndex()));
            pokeList.setSelectedIndex(0);
        });
        pokeList.addListSelectionListener(a -> {
            Pokemon.updateLabels(image, gen.getSelectedIndex(), dlc.getSelectedIndex(), pokeList.getSelectedIndex(), false);
        });
        idInput.addActionListener(a -> {
            Pokemon input;
            try{input = Pokemon.nationalDex[Integer.parseInt(idInput.getText()) - 1];}
            catch(NumberFormatException | ArrayIndexOutOfBoundsException e){input = Pokemon.nationalDex[0];}
            image.setIcon(input.makeImage("", false));
            image.setText(input.name);
            input.getDebug(); // Debug
        });
        select.addActionListener(a -> {
            swingDex x = new swingDex(Pokemon.getPokemon(gen.getSelectedIndex(), dlc.getSelectedIndex(), pokeList.getSelectedIndex()));
        });
    }

    // Window to Display Pokémon
    public swingDex(Pokemon pkmn){
        info = new JLabel[11];
        setTitle(pkmn.name);
        setSize(700, 350);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 300));

        // Base Information
        visualForm = 0; baseForm = 0;
        add(select = new JButton("Change Form"),constraint(0, 0, 0));
        add(image = new JLabel(pkmn.makeImage(pkmn.form.get(0).formSymbol[0], false)), constraint(0, 1, 2));
        for(int i=0; i<info.length; i++) add(info[i] = new JLabel(), constraint(1, i, 0));
        pkmn.form.get(0).updateLabels(info);
        // Caught and Seen Buttons
        add(infoPanel = new JPanel(new FlowLayout()), constraint(0, 10, 0));
        infoPanel.add(caught = new JButton("Caught"));
        infoPanel.add(seen = new JButton("Seen"));
        setVisible(true);

        // Action Listeners
        select.addActionListener(a -> {
            if(++visualForm < pkmn.form.get(baseForm).formSymbol.length){
                image.setIcon(pkmn.makeImage(pkmn.form.get(baseForm).formSymbol[visualForm], false));
                return;
            }if(++baseForm >= pkmn.form.size()) baseForm = 0;
            image.setIcon(pkmn.makeImage(pkmn.form.get(baseForm).formSymbol[0], false));
            pkmn.form.get(baseForm).updateLabels(info);
            visualForm = 0;
        });
        seen.addActionListener(a -> {
            pkmn.form.get(baseForm).caughtSeen = 1;
            pkmn.form.get(baseForm).updateLabels(info);
        });
        caught.addActionListener(a -> {
            pkmn.form.get(baseForm).caughtSeen = 2;
            pkmn.form.get(baseForm).updateLabels(info);
        });
    }

    // Set Constraints
    public static GridBagConstraints constraint(int x, int y, int type){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0.5; constraints.weighty = 0.5;
        constraints.gridx = x; constraints.gridy = y;
        switch(type){
            case 1 -> { // Pokémon Scroll List
                constraints.gridheight = 2;
                constraints.fill = 1;
                constraints.insets = new Insets(20, 20, 20, 20);
            } // Pokémon Image
            case 2 -> constraints.gridheight = 9;
        }
        return constraints;
    }
}