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
    
    /**
     * Instantiates a new save dialog.
     */
    public SaveDialog() {
        super(new File(Config.MAPS_FOLDER));
        this.setDialogTitle("Saving the map file");
        this.setFileFilter(new MapFilter());
    }
}

/**
 * Override the map filter to display only map files
 */
class MapFilter extends javax.swing.filechooser.FileFilter implements FileFilter {
    
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        final String name = f.getName();
        return name.toLowerCase().endsWith(Config.MAPS_EXTENSION);
    }
    
    @Override
    public String getDescription() {
        return "Map files (" + Config.MAPS_EXTENSION + ")";
    }
    
}
