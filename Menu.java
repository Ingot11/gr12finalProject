import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class Menu extends JFrame {
    private JPanel panel1;
    private JLabel titleLabel, regionLabel, gameLabel, image;
    private JComboBox regionSelect, gameSelect;
    private JScrollBar scrollBar;
    private JList dexList;
    private JLabel pkmnName;

    public static void main(String[] args) {
        Pokemon.Dex();
        Menu menu = new Menu();
    }

    public Menu() {
        setContentPane(panel1);
        setSize(500,400);
        setMinimumSize(new Dimension(400,300));
        setTitle("Pokédex");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        regionSelect.addActionListener((ActionEvent a) -> {
            String[][] generation = {{"National"},
                {"Regional", "Red/Blue/Yellow/FireRed/LeafGreen", "Let's Go"}, /*Kanto*/
                {"Regional", "Gold/Silver/Crystal", "HeartGold/SoulSilver"}, /*Johto*/
                {"Regional", "Ruby/Sapphire/Emerald", "Omega Ruby/Alpha Sapphire"}, /*Hoenn*/
                {"Regional", "Diamond/Pearl", "Platinum", "Legends: Arceus"}, /*Sinnoh + Hisui*/
                {"Regional", "Black/White", "Black 2/White 2"}, /*Unova*/
                {"Regional", "KalosCentral", "KalosCoastal", "KalosMountain"}, /*Kalos*/
                {"Regional", "Sun/Moon", "Melemele", "Akala", "Ula'Ula", "Poni", "Ultra Sun/Ultra Moon", "Ultra Melemele", "Ultra Akala", "Ultra Ula'Ula", "Ultra Poni"}, /*Alola*/
                {"Regional", "Sword/Shield", "Isle of Armor", "Crown Tundra"}, /*Galar*/
                {"Regional", "Scarlet/Violet", "Teal Mask", "Indigo Disk"}}; /*Paldea*/
            if(regionSelect.getSelectedIndex() > generation.length) regionSelect.setSelectedIndex(0);
            gameSelect.setModel(new DefaultComboBoxModel<>(generation[regionSelect.getSelectedIndex()]));
            gameSelect.setSelectedIndex(0);
            revalidate(); // Resets frame
        });

        gameSelect.addActionListener((ActionEvent a) -> {dexList.setModel(Pokemon.getDex(regionSelect.getSelectedIndex(), gameSelect.getSelectedIndex()));});
        dexList.addListSelectionListener((_) -> {
            pkmnName.setText(Pokemon.makeName(regionSelect.getSelectedIndex(), gameSelect.getSelectedIndex(), dexList.getSelectedIndex()));
            image.setIcon(Pokemon.makeImage(regionSelect.getSelectedIndex(), gameSelect.getSelectedIndex(), dexList.getSelectedIndex(), "", false));
        });
    }
}
