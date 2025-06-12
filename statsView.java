import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class statsView extends JFrame {
    private JPanel panel1;
    private JButton caught, seen, select;
    private int visualForm, baseForm;
    private JList<String> pokeList;
    private JLabel name, image;
    private JTable baseStats;
    private JButton shinyButton;

    // New Window to Display Pokémon
    public statsView(Pokemon pkmn) {
        setTitle(pkmn.name);
        setContentPane(panel1);
        setSize(504, 350);
        setMinimumSize(new Dimension(300, 200));

        // Characteristics Table
        String[] columnNames = {"HP", "Atk.", "Def.", "Sp. Atk.", "Sp. Def.", "Spd."};
        baseStats.setModel(new DefaultTableModel(new String[][] {pkmn.forms.getFirst().getStats()}, columnNames){
            @Override public boolean isCellEditable(int row, int column){return false;}
        });
        for(int i = 0; i < columnNames.length; i++) baseStats.getColumnModel().getColumn(i).setPreferredWidth(50);
        baseStats.setRowHeight(25);
        baseStats.setDefaultEditor(Object.class, null);
        baseStats.setPreferredScrollableViewportSize(new Dimension(450, 400));

        // Name and Image
        visualForm = 0; baseForm = 0;
        pkmn.labels(name, image, 0, 0, false);
        pkmn.forms.getFirst().updateList(pokeList);
        setVisible(true);
        // Hides Select if no forms are found
        if(pkmn.forms.size() < 2 && pkmn.forms.get(0).formSymbol.length < 2) select.setVisible(false);

        // Change Form Listener
        select.addActionListener(_ -> {
            if(++visualForm >= pkmn.forms.get(baseForm).formSymbol.length){ // Updates Visual Form
                if(++baseForm >= pkmn.forms.size()) baseForm = 0; // Updates Base Form
                pkmn.forms.get(baseForm).updateList(pokeList);
                baseStats.setModel(new DefaultTableModel(new String[][] {pkmn.forms.get(baseForm).getStats()}, columnNames){
                    @Override public boolean isCellEditable(int row, int column){return false;}
                }); visualForm = 0;
            }
            pkmn.labels(name, image, baseForm, visualForm, false); revalidate();
        });
        // Caught and Seen Listeners
        caught.addActionListener(_ -> {
            pkmn.forms.get(baseForm).caughtStatus(pokeList, 2); revalidate();
        });
        seen.addActionListener(_ -> {
            pkmn.forms.get(baseForm).caughtStatus(pokeList, 1); revalidate();
        });
    }
}
