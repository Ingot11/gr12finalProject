import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class statsView extends JFrame {
    private JPanel panel1;
    private JTable baseStats;
    private JLabel image;
    private JLabel name;

    private String[][] data;

    public statsView(Pokemon pkmn) {
        String[] columnNames = {"HP", "Atk.", "Def.", "Sp. Atk.", "Sp. Def.", "Spd."};
        data = new String[][] {
                pkmn.forms.getFirst().getStats()
        };
        pkmn.labels(name, image, 0, 0);

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        baseStats.setModel(tableModel);
        for(int i = 0; i < 6; i++) {
            baseStats.getColumnModel().getColumn(i).setPreferredWidth(50);
        }
        baseStats.setRowHeight(25);
        baseStats.setDefaultEditor(Object.class, null);

        setTitle(pkmn.name);
        setContentPane(panel1);
        setSize(500, 300);
        setMinimumSize(new Dimension(300, 200));
        setVisible(true);
    }

    public abstract class UneditableTableModel extends AbstractTableModel {
        @Override
        public boolean isCellEditable(int row, int column){return false;}
    }
}
