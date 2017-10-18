/*
  @file  IntegerEditor.java
 * @brief 
 * 
 * 
 * 
 * @author Team 2
 * @version 1.0
 * @since  Oct 18, 2017
 * @bug No known bugs.
 */
package view.helpers;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Custom cell editor
 * Only allows for positive integers if the column type is Integer.
 */
public class IntegerEditor extends DefaultCellEditor {
    
    /**
     * Instantiates a new integer editor.
     */
    public IntegerEditor() {
        super(new JTextField());
    }
    
    /* (non-Javadoc)
     * @see javax.swing.DefaultCellEditor#stopCellEditing()
     */
    public boolean stopCellEditing() {
        try {
            Integer editingValue = Integer.parseInt((String) getCellEditorValue());
            
            if (editingValue < 0) {
                JTextField textField = (JTextField) getComponent();
                textField.setBorder(new LineBorder(Color.red));
                textField.selectAll();
                textField.requestFocusInWindow();
                
                JOptionPane.showMessageDialog(
                        null,
                        "Please enter a positive integer.",
                        "Entry Error!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (ClassCastException | NumberFormatException exception) {
            JOptionPane.showMessageDialog(
                    null,
                    "Invalid entry. Please re-enter a number.",
                    "Entry Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        return super.stopCellEditing();
    }
    
    /* (non-Javadoc)
     * @see javax.swing.DefaultCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     */
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        Component c = super.getTableCellEditorComponent(
                table, value, isSelected, row, column);
        ((JComponent) c).setBorder(new LineBorder(Color.black));
        
        return c;
    }
    
}