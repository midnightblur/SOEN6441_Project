package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MapEditor1 extends JFrame {

    private JPanel contentPane;
    private JTable table_1;
    private JTextField txtEnterCountry;
    private JTextField txtEditCountry;

    /**
     * Create the frame.
     */
    public MapEditor1() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 832, 506);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("Map");
        menuBar.add(mnNewMenu);

        JMenuItem mntmNewMenuItem = new JMenuItem("Load Map");
        mnNewMenu.add(mntmNewMenuItem);

        JMenuItem mntmNewMenuItem_2 = new JMenuItem("New Map");
        mnNewMenu.add(mntmNewMenuItem_2);

        JMenuItem mntmNewMenuItem_3 = new JMenuItem("Edit Map");
        mnNewMenu.add(mntmNewMenuItem_3);

        JMenuItem mntmNewMenuItem_4 = new JMenuItem("Save Map");
        mnNewMenu.add(mntmNewMenuItem_4);

        JMenu mnNewMenu_1 = new JMenu("Player");
        menuBar.add(mnNewMenu_1);

        JMenuItem mntmNewMenuItem_5 = new JMenuItem("New menu item");
        mnNewMenu_1.add(mntmNewMenuItem_5);

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("New menu item");
        mnNewMenu_1.add(mntmNewMenuItem_1);

        JMenu mnNewMenu_2 = new JMenu("Play");
        menuBar.add(mnNewMenu_2);

        JMenuItem mntmNewMenuItem_6 = new JMenuItem("New menu item");
        mnNewMenu_2.add(mntmNewMenuItem_6);

        JMenu mnNewMenu_3 = new JMenu("Statics");
        menuBar.add(mnNewMenu_3);

        JMenuItem mntmNewMenuItem_7 = new JMenuItem("New menu item");
        mnNewMenu_3.add(mntmNewMenuItem_7);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 0, 239, 224);
        contentPane.add(scrollPane);

        //Add RowData here
        String column[] ={"Country","Neighbours"};
        String rowData[][]={
                {"row 1 counrty","row 1 neighbour"},
                {"row 2 counrty","row 2 neighbour"}

        };


        table_1 = new JTable(rowData, column);
        scrollPane.setViewportView(table_1);

        JPanel panel = new JPanel();
        panel.setBounds(261, 0, 171, 227);
        contentPane.add(panel);

        Choice choiceadd = new Choice();
        panel.add(choiceadd);

        Choice choiceeditdelete = new Choice();
        panel.add(choiceeditdelete);

        JPanel panel_2 = new JPanel();
        panel_2.setBounds(444, 0, 171, 227);
        contentPane.add(panel_2);

        txtEnterCountry = new JTextField();
        txtEnterCountry.setText("new country");
        panel_2.add(txtEnterCountry);
        txtEnterCountry.setColumns(10);

        Choice choiceaddtocontinent = new Choice();
        choiceaddtocontinent.setLocation(new Point(0, 56));
        panel_2.add(choiceaddtocontinent);

        JComboBox comboboxneighbours = new JComboBox();
        panel_2.add(comboboxneighbours);

        JButton btnadd = new JButton("Add");
        panel_2.add(btnadd);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(631, 0, 171, 227);
        contentPane.add(panel_1);

        txtEditCountry = new JTextField();
        txtEditCountry.setText("edit country");
        txtEditCountry.setColumns(10);
        panel_1.add(txtEditCountry);

        Choice choicecontinent = new Choice();
        choicecontinent.setLocation(new Point(0, 56));
        panel_1.add(choicecontinent);

        JComboBox comboBoxNeighbors = new JComboBox();
        panel_1.add(comboBoxNeighbors);

        JButton btnok = new JButton("OK");
        panel_1.add(btnok);

        JButton btndelete = new JButton("DELETE");
        panel_1.add(btndelete);


        setVisible(true);

    }
}
