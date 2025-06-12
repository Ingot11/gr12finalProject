import java.awt.*;
import javax.swing.*;

@SuppressWarnings("unused")
public class mainDex extends JFrame{
    // Window Variables
    private JPanel selectorPanel, idPanel, infoPanel;
    private JButton caught, seen, select, shiny;
    private JComboBox<String> region, dlc;
    private int visualForm, baseForm;
    private JList<String> pokeList;
    private JLabel name, image;
    private JTextField idInput;
    private boolean isShiny;
    private JLabel[] info;
    
    // Call Window
    public static void main(String[] args){
        Pokemon.Dex(); // Initialize Pokédex
        mainDex main = new mainDex();
    }

    // Window Constructor
    public mainDex(){
        // Set Main Frame
        setTitle("Pokédex");
        setSize(650, 450);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(650, 450));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Id Inputter
        baseForm = 1;
        add(idPanel = new JPanel(new FlowLayout()), constraint(0, 0, 0));
        idPanel.add(new JLabel("National Dex Number:"));
        idPanel.add(idInput = new JTextField(3));

        // Region Selectors
        add(selectorPanel = new JPanel(new FlowLayout()), constraint(1, 0, 0));
        selectorPanel.add(region = new JComboBox<>(new String[]{"National", "Kanto", "Johto", "Hoenn", "Sinnoh/Hisui", "Unova", "Kalos", "Alola", "Galar", "Paldea"}));
        selectorPanel.add(dlc = new JComboBox<>(new String[]{"National"}));

        // Pokemon Image / More Info Button
        add(image = new JLabel(), constraint(0, 1, 0));
        add(name = new JLabel(), constraint(0, 2, 0));
        add(select = new JButton("More Info"), constraint(0, 3, 0));
        Pokemon.nationalDex[0].labels(name, image, false);

        // Scroll Dex
        add(new JScrollPane(pokeList = new JList<>(Pokemon.getDex(0, 0))), constraint(1, 1, 1));
        setVisible(true);

        // Action Listeners
        region.addActionListener(_ -> { // Choose Generation to get DLC
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
            if(region.getSelectedIndex() > generation.length) region.setSelectedIndex(0);

            // Sets Model of DLC
            dlc.setModel(new DefaultComboBoxModel<>(generation[region.getSelectedIndex()]));
            dlc.setSelectedIndex(0);
            revalidate(); // Resets frame
        });
        dlc.addActionListener(_ -> { // Sets model of JList
            pokeList.setModel(Pokemon.getDex(region.getSelectedIndex(), dlc.getSelectedIndex()));
            pokeList.setSelectedIndex(0);
        });
        pokeList.addListSelectionListener(_ -> { // Updates List with selected box
            Pokemon.get(region.getSelectedIndex(), dlc.getSelectedIndex(), pokeList.getSelectedIndex()).labels(name, image, false);
            baseForm = 0;
        });
        idInput.addActionListener(_ -> { // Takes in the national dex number and outputs it
            try{
                Pokemon.nationalDex[Integer.parseInt(idInput.getText()) - 1].labels(name, image, false);
                Pokemon.nationalDex[Integer.parseInt(idInput.getText()) - 1].getDebug(); // Debug
                baseForm = Integer.parseInt(idInput.getText());
            }catch(NumberFormatException | ArrayIndexOutOfBoundsException e){}
        });
        select.addActionListener(_ -> { // Opens the Pokémon Menu
            statsView x; mainDex y;
            if(baseForm == 0) x = new statsView(Pokemon.get(region.getSelectedIndex(), dlc.getSelectedIndex(), pokeList.getSelectedIndex()));
            else y = new mainDex(Pokemon.nationalDex[baseForm - 1]);
        });
    }

    // Window to Display Pokémon
    public mainDex(Pokemon pkmn){
        setTitle(pkmn.name);
        setSize(700, 350);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 300));

        // Base Information
        info = new JLabel[11];
        visualForm = 0; baseForm = 0; isShiny = false;
        add(selectorPanel = new JPanel(new FlowLayout()), constraint(0, 0, 0));
        selectorPanel.add(select = new JButton("Change Form"));
        selectorPanel.add(shiny = new JButton("Make Shiny"));
        add(name = new JLabel(), constraint(1,0,0));
        add(image = new JLabel(), constraint(0, 1, 2));
        pkmn.labels(name, image, 0, 0, false);
        if(pkmn.forms.size() < 2 && pkmn.forms.get(0).formSymbol.length < 2) select.setVisible(false);

        // Adds Each Characteristic of Pokémon
        for(int i=0; i<info.length; i++) add(info[i] = new JLabel(), constraint(1, i + 1, 0));
        pkmn.forms.getFirst().updateLabels(info);

        // Caught and Seen Buttons
        add(infoPanel = new JPanel(new FlowLayout()), constraint(0, 11, 0));
        infoPanel.add(caught = new JButton("Caught"));
        infoPanel.add(seen = new JButton("Seen"));
        setVisible(true);

        // Change Form Listeners
        select.addActionListener(_ -> {
            if(++visualForm >= pkmn.forms.get(baseForm).formSymbol.length){ // Updates Visual Form
                if(++baseForm >= pkmn.forms.size()) baseForm = 0; // Updates Base Form
                pkmn.forms.get(baseForm).updateLabels(info);
                visualForm = 0;
            } pkmn.labels(name, image, baseForm, visualForm, isShiny); revalidate();
        });
        shiny.addActionListener(_ -> {
            pkmn.labels(name, image, baseForm, visualForm, isShiny = !isShiny); revalidate();
        });

        // Caught and Seen Listeners
        caught.addActionListener(_ -> {
            pkmn.forms.get(baseForm).caughtStatus(info, 2); revalidate();
        });
        seen.addActionListener(_ -> {
            pkmn.forms.get(baseForm).caughtStatus(info, 1); revalidate();
        });
    }

    // Set Constraints
    public static GridBagConstraints constraint(int x, int y, int type){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0.5; constraints.weighty = 0.5;
        constraints.gridx = x; constraints.gridy = y;
        switch(type){
            case 1 -> { // Pokémon Scroll List
                constraints.gridheight = 3;
                constraints.fill = 1;
                constraints.insets = new Insets(20, 20, 20, 20);
            } // Pokémon Image
            case 2 -> constraints.gridheight = 10;
        }
        return constraints;
    }
}