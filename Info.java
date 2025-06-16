import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class Info extends JFrame {
    public JPanel selectorPanel;
    public JButton caught, seen, select, shiny;
    public int visualForm, baseForm;
    public JList<String> pokeList;
    public JLabel name, image;
    public JTable baseStats;
    public boolean isShiny;
    
    public Info(){}
    // New Window to Display Pokémon
    public Info(Pokemon pkmn) {
        setTitle(pkmn.name);
        setContentPane(selectorPanel);
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
        visualForm = 0; baseForm = 0; isShiny = false;
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
            pkmn.labels(name, image, baseForm, visualForm, isShiny); revalidate();
        });

        // Caught and Seen Listeners
        caught.addActionListener(_ -> {pkmn.forms.get(baseForm).caughtStatus(pokeList, 2);});
        seen.addActionListener(_ -> {pkmn.forms.get(baseForm).caughtStatus(pokeList, 1);});
        shiny.addActionListener(_ -> {pkmn.labels(name, image, baseForm, visualForm, isShiny = !isShiny);});
    }
}