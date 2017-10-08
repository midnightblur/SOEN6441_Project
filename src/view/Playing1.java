package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Playing1 extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JTextField txtEnterCountry;
    private JTextField txtEditCountry;


    /**
     * Create the frame.
     */
    public Playing1() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

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
        String column[] ={"Country","Neighbours","Sum"};
        String rowData[][]={
                {"row 1 counrty","row 1 neighbour","row 1 sum"},
                {"row 2 counrty","row 2 neighbour", "row 2 sum"}

        };


        table = new JTable(rowData, column);
        scrollPane.setViewportView(table);

        setVisible(true);
    }

}
