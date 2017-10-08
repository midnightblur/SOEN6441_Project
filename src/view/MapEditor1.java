import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Choice;
import javax.swing.JTable;
import java.awt.ScrollPane;
import java.awt.Panel;
import java.awt.Point;
import javax.swing.JComboBox;
import javax.swing.JButton;


/** This class contains frame for the configuration of
 * game where player can edit the map and add continents
 * and corresponding countries to it. The player can also
 * delete or edit. The player can also choose from the
 * existing Maps.
 *
 * @author Yashleen Kaur Virk
 * @version 1.0
 */
public class MapEditor1 extends JFrame {

	private JPanel contentPane;
	private JTable table_1;
	private JTextField txtEnterCountry;
	private JTextField txtEditCountry;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MapEditor1 frame = new MapEditor1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * This method is a constructor where GUI has
	 * been designed.
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

		Choice choice = new Choice();
		panel.add(choice);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(444, 0, 171, 227);
		contentPane.add(panel_2);

		txtEnterCountry = new JTextField();
		txtEnterCountry.setText("enter country");
		panel_2.add(txtEnterCountry);
		txtEnterCountry.setColumns(10);

		Choice choice_1 = new Choice();
		choice_1.setLocation(new Point(0, 56));
		panel_2.add(choice_1);

		JComboBox comboBox = new JComboBox();
		panel_2.add(comboBox);

		JButton btnAdd = new JButton("Add");
		panel_2.add(btnAdd);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(631, 0, 171, 227);
		contentPane.add(panel_1);

		txtEditCountry = new JTextField();
		txtEditCountry.setText("edit country");
		txtEditCountry.setColumns(10);
		panel_1.add(txtEditCountry);

		Choice choice_2 = new Choice();
		choice_2.setLocation(new Point(0, 56));
		panel_1.add(choice_2);

		JComboBox comboBox_1 = new JComboBox();
		panel_1.add(comboBox_1);

		JButton btnOk = new JButton("OK");
		panel_1.add(btnOk);

		JButton btnDelete = new JButton("DELETE");
		panel_1.add(btnDelete);

	}
}
