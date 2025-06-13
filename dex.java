import java.awt.*;
import javax.swing.*;
@SuppressWarnings("unused")

public class dex extends Info{
    // Window Variables
    public JPanel idPanel, infoPanel;
    public JComboBox<String> region, dlc;
    public JTextField idInput;
    public JButton credits;
    public JLabel[] info;

    public static void main(String[] args){ // Call Window
        Pokemon.Dex(); // Initialize Pokédex
        dex main = new dex();
    }

    public dex(){ // Window Constructor
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
        selectorPanel.add(credits = new JButton("Credits"));

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
        idInput.addActionListener(_ -> { // Takes in the national dex number and outputs result
            try{
                Pokemon.nationalDex[Integer.parseInt(idInput.getText()) - 1].labels(name, image, false);
                Pokemon.nationalDex[(baseForm = Integer.parseInt(idInput.getText())) - 1].getDebug(); // Debug
            }catch(NumberFormatException | ArrayIndexOutOfBoundsException e){}
        });
        credits.addActionListener(_ -> {
            JOptionPane.showMessageDialog(this,
             "Developed By:\nAzeez Bazara\nZachary Nusbaum\n\nSources:\nDragonFlyCave.com\nSerebii.net\nBubapedia",
             "Credits", -1);
        });
        select.addActionListener(_ -> { // Opens the Pokémon Menu
            Info x; dex y;
            if(baseForm != 0) y = new dex(Pokemon.nationalDex[baseForm - 1]);
            else x = new Info(Pokemon.get(region.getSelectedIndex(), dlc.getSelectedIndex(), pokeList.getSelectedIndex()));
        });
    }

    public dex(Pokemon pkmn){ // Window to Display Pokémon
        setTitle(pkmn.name);
        setSize(700, 350);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 300));

        // Selectors
        visualForm = 0; baseForm = 0; isShiny = false;
        add(selectorPanel = new JPanel(new FlowLayout()), constraint(0, 0, 0));
        selectorPanel.add(select = new JButton("Change Form"));
        selectorPanel.add(shiny = new JButton("Make Shiny"));
        // Hides Change Form if selected doesn't have any
        if(pkmn.forms.size() < 2 && pkmn.forms.get(0).formSymbol.length < 2) select.setVisible(false);

        // Base Information
        add(name = new JLabel(), constraint(1,0,0));
        add(image = new JLabel(), constraint(0, 1, 2));
        pkmn.labels(name, image, 0, 0, false);

        // Adds Each Characteristic of Pokémon
        info = new JLabel[12];
        for(int i=0; i<info.length; i++) add(info[i] = new JLabel(), constraint(1, i + 1, 0));
        pkmn.forms.getFirst().updateLabels(info);

        // Caught and Seen Buttons
        add(infoPanel = new JPanel(new FlowLayout()), constraint(0, 12, 0));
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
            pkmn.labels(name, image, baseForm, visualForm, isShiny = !isShiny);
        });

        // Caught and Seen Listeners
        caught.addActionListener(_ -> {
            pkmn.forms.get(baseForm).caughtStatus(info, 2);
        });
        seen.addActionListener(_ -> {
            pkmn.forms.get(baseForm).caughtStatus(info, 1);
        });
    }

    public static GridBagConstraints constraint(int x, int y, int type){ // Set Constraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0.5; constraints.weighty = 0.5;
        constraints.gridx = x; constraints.gridy = y;
        switch(type){
            case 1 -> { // Pokémon Scroll List
                constraints.gridheight = 3;
                constraints.fill = 1;
                constraints.insets = new Insets(20, 20, 20, 20);
            } // Pokémon Info Image
            case 2 -> constraints.gridheight = 11;
        }
        return constraints;
    }
}