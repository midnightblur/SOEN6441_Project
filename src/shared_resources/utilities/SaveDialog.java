/* 
 * Risk Game Team 2
 * SaveDialog.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.utilities;

import javax.swing.*;
import java.io.File;
import java.io.FileFilter;

/**
 * Overrides super class to open the save dialog into game map folder
 * and apply a custom map filter
 * This class is used to save a map to file.
 */
public class SaveDialog extends JFileChooser {
    
    // region Constructors
    /**
     * Instantiates a new save dialog.
     */
    public SaveDialog() {
        super(new File(Config.MAPS_FOLDER));
        this.setDialogTitle("Saving the map file");
        this.setFileFilter(new MapFilter());
    }
    // endregion
}

/**
 * Override the map filter to display only map files
 */
class MapFilter extends javax.swing.filechooser.FileFilter implements FileFilter {
    
    // region Public methods
    /**
     * Determines whether or not the file is of the default extension type (.map)
     * in order to display only the valid map files in the save drop down list.
     *
     * @param file An object of File to be determined valid or not
     * @return Returns true if the file type is of the default extension, false otherwise
     */
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        final String name = file.getName();
        return name.toLowerCase().endsWith(Config.MAPS_EXTENSION);
    }
    
    /**
     * Provides the description for the map file type.
     *
     * @return String that describes the map file type
     */
    @Override
    public String getDescription() {
        return "Map files (" + Config.MAPS_EXTENSION + ")";
    }
    // endregion
}
