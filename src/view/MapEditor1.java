package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapEditor1 extends JFrame implements ActionListener{

    private JPanel contentPane;
    private JTable table_1;
    private JTextField txtEnterCountry;
    private JTextField txtEditCountry;

    /**
     * this method Creates the frame.
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

        /**
         *This Adds RowData here.
         */

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
        panel.setLayout(null);

        Choice choiceadd = new Choice();
        choiceadd.setBounds(55, 20, 50, 22);
        panel.add(choiceadd);

        Choice choiceeditdelete = new Choice();
        choiceeditdelete.setBounds(55, 66, 50, 22);
        panel.add(choiceeditdelete);

        JPanel panel_2 = new JPanel();
        panel_2.setBounds(444, 0, 171, 227);
        contentPane.add(panel_2);
        panel_2.setLayout(null);

        txtEnterCountry = new JTextField();
        txtEnterCountry.setBounds(11, 5, 116, 22);
        txtEnterCountry.setText("new country");
        panel_2.add(txtEnterCountry);
        txtEnterCountry.setColumns(10);

        Choice choiceaddtocontinent = new Choice();
        choiceaddtocontinent.setSize(64, 22);
        choiceaddtocontinent.setLocation(new Point(50, 50));
        panel_2.add(choiceaddtocontinent);

        JComboBox comboboxneighbours = new JComboBox();
        comboboxneighbours.setBounds(50, 90, 64, 25);
        panel_2.add(comboboxneighbours);

        JButton btnadd = new JButton("Add");
        btnadd.setBounds(50, 170, 64, 25);
        panel_2.add(btnadd);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(631, 0, 171, 227);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        txtEditCountry = new JTextField();
        txtEditCountry.setBounds(11, 5, 116, 22);
        txtEditCountry.setText("edit country");
        txtEditCountry.setColumns(10);
        panel_1.add(txtEditCountry);

        Choice choicecontinent = new Choice();
        choicecontinent.setSize(62, 28);
        choicecontinent.setLocation(new Point(39, 47));
        panel_1.add(choicecontinent);

        JComboBox comboBoxNeighbors = new JComboBox();
        comboBoxNeighbors.setBounds(39, 87, 62, 22);
        panel_1.add(comboBoxNeighbors);

        JButton btnok = new JButton("OK");
        btnok.setBounds(39, 141, 69, 22);
        panel_1.add(btnok);

        JButton btndelete = new JButton("DELETE");
        btndelete.setBounds(39, 176, 75, 22);
        panel_1.add(btndelete);

        JButton btnback = new JButton("Back");
        btnback.setBounds(10, 255, 97, 25);
        btnback.addActionListener(this);
        btnback.setActionCommand("BackToMain");
        contentPane.add(btnback);



        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String cmd = e.getActionCommand();
        if(cmd.equals("BackToMain")) {
            dispose();
            new MainPage1();
        }

    }
}
