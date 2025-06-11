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

    public statsView(Pokemon pkmn) {
        setTitle(pkmn.name);
        setContentPane(panel1);
        setSize(504, 320);
        setMinimumSize(new Dimension(300, 200));

        String[] columnNames = {"HP", "Atk.", "Def.", "Sp. Atk.", "Sp. Def.", "Spd."};
        baseStats.setModel(new DefaultTableModel(new String[][] {pkmn.forms.getFirst().getStats()}, columnNames){
            @Override public boolean isCellEditable(int row, int column){return false;}
        });
        for(int i = 0; i < 6; i++) baseStats.getColumnModel().getColumn(i).setPreferredWidth(50);
        baseStats.setRowHeight(25);
        baseStats.setDefaultEditor(Object.class, null);

        visualForm = 0; baseForm = 0;
        pkmn.labels(name, image, 0, 0, false);
        pkmn.forms.getFirst().updateList(pokeList);
        setVisible(true);
        if(pkmn.forms.size() < 2) select.setVisible(false);

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
        seen.addActionListener(_ -> {
            pkmn.forms.get(baseForm).caughtSeen = (pkmn.forms.get(baseForm).caughtSeen == 1)? 0 : 1;
            pkmn.forms.get(baseForm).updateList(pokeList); revalidate();
        });
        caught.addActionListener(_ -> {
            pkmn.forms.get(baseForm).caughtSeen = (pkmn.forms.get(baseForm).caughtSeen == 2)? 0 : 2;
            pkmn.forms.get(baseForm).updateList(pokeList); revalidate();
        });
    }
}
