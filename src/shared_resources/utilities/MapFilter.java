/*
 * Risk Game Team 2
 * MapFilter.java
 * Version 1.0
 * Nov 7, 2017
 */
package shared_resources.utilities;

import java.io.File;
import java.io.FileFilter;

/**
 * Override the map filter to display only custom files
 *
 * @author Team 2
 * @version 1.0
 */
public class MapFilter extends javax.swing.filechooser.FileFilter implements FileFilter {
    private String fileExtension;
    
    /**
     * Constructor accepting a file extension
     *
     * @param fileExtension the extension to filter by
     */
    public MapFilter(String fileExtension) {
        this.fileExtension = fileExtension;
    }
    
    // region Public methods
    
    /**
     * Determines whether or not the file is of the desired extension type
     * in order to display only the valid files.
     *
     * @param file An object of File to be determined valid or not
     *
     * @return Returns true if the file type is of the default extension, false otherwise
     */
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        final String name = file.getName();
        return name.toLowerCase().endsWith(fileExtension);
    }
    
    /**
     * Provides the description for the file type.
     *
     * @return String that describes the file type
     */
    @Override
    public String getDescription() {
        return "Game file (*" + fileExtension + ")";
    }
    // endregion
}
