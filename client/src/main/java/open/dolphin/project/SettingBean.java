package open.dolphin.project;

/**
 *
 * @author Kazushi Minagawa
 */
public interface SettingBean {
    
    String[] propertyOrder();
    
    boolean isTagProperty(String property);
    
    String[] getTags(String property);
    
    boolean isDirectoryProperty(String property);
    
    boolean isSpinnerProperty(String property);
    
    int[] getSpinnerSpec(String property);
    
    boolean isDecimalProperty(String property);
    
    // Return true if bean has valid data set
    boolean isValidBean();
    
    // Set beans' value from the setting file
    void populate();
    
    // Store propertis to the setting file
    void store();
    
}
