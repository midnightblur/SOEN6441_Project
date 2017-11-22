/* 
 * Risk Game Team 2
 * SaveDialog.java
 * Version 3.0
 * Nov 22, 2017
 */
package shared_resources.utilities;

import javax.swing.*;
import java.io.File;

import static javax.swing.JFileChooser.CUSTOM_DIALOG;

/**
 * Overrides super class to open the save dialog into game map folder
 * and apply a custom map filter
 * This class is used to save a map to file and to save/load a game state.
 *
 * @author Team 2
 * @version 3.0
 */
public class SaveOpenDialog {
    private JFileChooser dialog;
    
    // region Constructors
    
    /**
     * Object constructor that instantiates a custom save dialog
     *
     * @param mapFilter     the file filter
     * @param approveButton the text on the approve button
     */
    public SaveOpenDialog(MapFilter mapFilter, String approveButton) {
        dialog = new JFileChooser();
        dialog.setCurrentDirectory(new File(Config.MAPS_FOLDER));
        dialog.setDialogTitle("Select a file...");
        dialog.setFileFilter(mapFilter);
        dialog.setApproveButtonText(approveButton);
        dialog.setApproveButtonToolTipText(approveButton);
        dialog.setDialogType(CUSTOM_DIALOG);
    }
    // endregion
    
    //region Getters & Setters
    
    /**
     * shows the dialog
     *
     * @return the integer value of the approve button
     */
    public int showDialog() {
        return dialog.showDialog(dialog.getParent(), dialog.getApproveButtonText());
    }
    
    /**
     * Gets the selected file
     *
     * @return the selected file
     */
    public File getSelectedFile() {
        return dialog.getSelectedFile();
    }
    // endregion
}

