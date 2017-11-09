/* 
 * Risk Game Team 2
 * IntegerEditor.java
 * Version 2.0
 * Oct 18, 2017
 */
package shared_resources.utilities;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Custom cell editor
 * Only allows for positive integers if the column type is Integer.
 *
 * @author Team 2
 * @version 1.0
 */
public class IntegerEditor extends DefaultCellEditor {
    
    // region Constructors
    /**
     * Instantiates a new integer editor.
     */
    public IntegerEditor() {
        super(new JTextField());
    }
    // endregion
    
    // region Public methods
    /**
     * @see javax.swing.DefaultCellEditor#stopCellEditing()
     */
    @Override
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
    
    /**
     * @see javax.swing.DefaultCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     */
    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        Component component = super.getTableCellEditorComponent(
                table, value, isSelected, row, column);
        ((JComponent) component).setBorder(new LineBorder(Color.black));
        
        return component;
    }
    // endregion
}