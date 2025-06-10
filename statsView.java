import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class statsView extends JFrame {
    private JPanel panel1;
    private JTable baseStats;
    private JLabel image;
    private JLabel name;
    private JButton seenButton;
    private JButton caughtButton;
    private JButton selectButton;
    private JList list1;

    private int visualForm, baseForm;
    private String[] info;
    private String[][] data;

    public statsView(Pokemon pkmn) {
        setTitle(pkmn.name);
        setContentPane(panel1);
        setSize(500, 350);
        setMinimumSize(new Dimension(300, 200));

        String[] columnNames = {"HP", "Atk.", "Def.", "Sp. Atk.", "Sp. Def.", "Spd."};
        data = new String[][] {
                pkmn.forms.getFirst().getStats()
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames){
            @Override public boolean isCellEditable(int row, int column){return false;}
        };
        baseStats.setModel(tableModel);
        for(int i = 0; i < 6; i++) baseStats.getColumnModel().getColumn(i).setPreferredWidth(50);
        baseStats.setRowHeight(25);
        baseStats.setDefaultEditor(Object.class, null);

        visualForm = 0; baseForm = 0;
        pkmn.labels(name, image, 0, 0);

        pkmn.forms.getFirst().updateLabels(info);

        setVisible(true);

        selectButton.addActionListener(_ -> {
            // Updates Visual Form
            if(++visualForm < pkmn.forms.get(baseForm).formSymbol.length){
                pkmn.labels(name, image, baseForm, visualForm);
                return;
            }if(++baseForm >= pkmn.forms.size()) baseForm = 0; // Updates Base Form
            pkmn.labels(name, image, baseForm, 0);
            pkmn.forms.get(baseForm).updateLabels(info);
            visualForm = 0;
        });

        seenButton.addActionListener(_ -> {
            if(pkmn.forms.get(baseForm).caughtSeen == 1) pkmn.forms.get(baseForm).caughtSeen = 0;
            else pkmn.forms.get(baseForm).caughtSeen = 1;
            pkmn.forms.get(baseForm).updateLabels(info);
        });
        caughtButton.addActionListener(_ -> {
            if(pkmn.forms.get(baseForm).caughtSeen == 2) pkmn.forms.get(baseForm).caughtSeen = 0;
            else pkmn.forms.get(baseForm).caughtSeen = 2;
            pkmn.forms.get(baseForm).updateLabels(info);
        });
    }
}
