import javax.swing.*;
import java.awt.event.ActionEvent;

public class Menu extends JFrame {
    private JPanel panel1;
    private JLabel titleLabel;
    private JLabel regionLabel;
    private JComboBox regionSelect;
    private JLabel gameLabel;
    private JComboBox gameSelect;
    private JList dexList;
    private JLabel image;
    private JScrollBar scrollBar;

    public static void main(String[] args) {
        Menu menu = new Menu();
    }

    public Menu() {
        setContentPane(panel1);
        setSize(500,400);
        setTitle("Pokedex");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        regionSelect.addActionListener((ActionEvent a) -> {
            String[][] generation = {{"Regional", "Red/Blue/Yellow/FireRed/LeafGreen", "Let's Go"}, /*Kanto*/
                    {"Regional", "Gold/Silver/Crystal", "HeartGold/SoulSilver"}, /*Johto*/
                    {"Regional", "Ruby/Sapphire/Emerald", "Omega Ruby/Alpha Sapphire"}, /*Hoenn*/
                    {"Regional", "Diamond/Pearl", "Platinum", "Legends: Arceus"}, /*Sinnoh + Hisui*/
                    {"Regional", "Black/White", "Black 2/White 2"}, /*Unova*/
                    {"Regional", "X/Y", "Central", "Coastal", "Mountain"}, /*Kalos*/
                    {"Regional", "Sun/Moon", "Ultra Sun/Ultra Moon", "Melemele", "Akala", "Ula'Ula", "Poni", "Ultra Regional", "Ultra Melemele", "Ultra Akala", "Ultra Ula'Ula", "Ultra Poni"}, /*Alola*/
                    {"Regional", "Sword/Shield", "Isle of Armor", "Crown Tundra"}, /*Galar*/
                    {"Regional", "Scarlet/Violet", "Teal Mask", "Indigo Disk"}}; /*Paldea*/
            if(regionSelect.getSelectedIndex() > generation.length) regionSelect.setSelectedIndex(0);
            gameSelect.setModel(new DefaultComboBoxModel<>(generation[regionSelect.getSelectedIndex()]));
            gameSelect.setSelectedIndex(0);
            revalidate(); // Resets frame
        });

        gameSelect.addActionListener((ActionEvent a) -> {
            switch(regionSelect.getSelectedIndex()) {
                case 0 -> {
                    switch(gameSelect.getSelectedIndex()) {
                        case 0 -> {

                        }
                        case 1 -> {

                        }
                        case 2 -> {

                        }
                        case 3 -> {

                        }
                    }
                }
            }
        });
    }
}
