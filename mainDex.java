import java.awt.*;
import javax.swing.*;
public class mainDex extends JFrame{
    // Window Variables
    private JPanel selectorPanel, idPanel, infoPanel;
    private JButton caught, seen, select;
    private JComboBox<String> gen, dlc;
    private int visualForm, baseForm;
    private JList<String> pokeList;
    private JLabel image;
    private JTextField idInput;
    private JLabel[] info;
    
    // Call Window
    public static void main(String[] args){
        Pokemon.Dex(); // Initialize Pokédex
        @SuppressWarnings("unused")
        mainDex main = new mainDex();
    }

    // Window Constructor
    public mainDex(){
        // Set Main Frame
        setTitle("Pokédex");
        setSize(600, 600);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(450, 450));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        baseForm = 1;
        // Id Inputter
        add(idPanel = new JPanel(new FlowLayout()), constraint(0, 0, 0));
        idPanel.add(new JLabel("Dex Number:"));
        idPanel.add(idInput = new JTextField(3));

        // Selectors
        add(selectorPanel = new JPanel(new FlowLayout()), constraint(1, 0, 0));
        selectorPanel.add(gen = new JComboBox<>(new String[]{"National", "Kanto", "Johto", "Hoenn", "Sinnoh/Hisui", "Unova", "Kalos", "Alola", "Galar", "Paldea"}));
        selectorPanel.add(dlc = new JComboBox<>(new String[]{"National"}));

        // Pokemon Image / More Info Button
        add(image = new JLabel(), constraint(0, 1, 0));
        add(select = new JButton("More Info"), constraint(0, 2, 0));
        Pokemon.labels(image, 0, 0, 0);

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
            Pokemon.labels(image, gen.getSelectedIndex(), dlc.getSelectedIndex(), pokeList.getSelectedIndex());
            baseForm = 0;
        });
        idInput.addActionListener(a -> {
            try{
                Pokemon.labels(image, 0, 0, Pokemon.nationalDex[Integer.parseInt(idInput.getText()) - 1].national - 1);
                Pokemon.nationalDex[Integer.parseInt(idInput.getText()) - 1].getDebug(); // Debug
                baseForm = Integer.parseInt(idInput.getText());
            }catch(NumberFormatException | ArrayIndexOutOfBoundsException e){}
        });
        select.addActionListener(a -> {
            @SuppressWarnings("unused")
            mainDex x;
            if(baseForm == 0) x = new mainDex(Pokemon.get(gen.getSelectedIndex(), dlc.getSelectedIndex(), pokeList.getSelectedIndex()));
            else x = new mainDex(Pokemon.get(0,0,baseForm - 1));
        });
    }

    // Window to Display Pokémon
    public mainDex(Pokemon pkmn){
        info = new JLabel[11];
        setTitle(pkmn.name);
        setSize(700, 350);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 300));

        // Base Information
        visualForm = 0; baseForm = 0;
        add(select = new JButton("Change Form"),constraint(0, 0, 0));
        add(image = new JLabel(pkmn.image(pkmn.forms.get(0).formSymbol[0], false)), constraint(0, 1, 2));
        for(int i=0; i<info.length; i++) add(info[i] = new JLabel(), constraint(1, i, 0));
        pkmn.forms.get(0).updateLabels(info);
        // Caught and Seen Buttons
        add(infoPanel = new JPanel(new FlowLayout()), constraint(0, 10, 0));
        infoPanel.add(caught = new JButton("Caught"));
        infoPanel.add(seen = new JButton("Seen"));
        setVisible(true);

        // Action Listeners
        select.addActionListener(a -> {
            if(++visualForm < pkmn.forms.get(baseForm).formSymbol.length){
                image.setIcon(pkmn.image(pkmn.forms.get(baseForm).formSymbol[visualForm], false));
                return;
            }if(++baseForm >= pkmn.forms.size()) baseForm = 0;
            image.setIcon(pkmn.image(pkmn.forms.get(baseForm).formSymbol[0], false));
            pkmn.forms.get(baseForm).updateLabels(info);
            visualForm = 0;
        });
        seen.addActionListener(a -> {
            pkmn.forms.get(baseForm).caughtSeen = 1;
            pkmn.forms.get(baseForm).updateLabels(info);
        });
        caught.addActionListener(a -> {
            pkmn.forms.get(baseForm).caughtSeen = 2;
            pkmn.forms.get(baseForm).updateLabels(info);
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