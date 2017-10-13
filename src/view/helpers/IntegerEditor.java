package view.helpers;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Custom cell editor
 * Only allows for positive integers if the column type is Integer
 */
public class IntegerEditor extends DefaultCellEditor {
    public IntegerEditor() {
        super(new JTextField());
    }
    
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
                        "Alert!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (ClassCastException exception) {
            return false;
        }
        
        return super.stopCellEditing();
    }
    
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        Component c = super.getTableCellEditorComponent(
                table, value, isSelected, row, column);
        ((JComponent) c).setBorder(new LineBorder(Color.black));
        
        return c;
    }
    
}