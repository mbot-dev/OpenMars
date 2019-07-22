package open.dolphin.client;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import open.dolphin.impl.labrcv.NLaboImportSummary;

/**
 *
 * @author Kazushi Minagawa.
 */
public interface LabResultParser {

    String MIHOKOKU = java.util.ResourceBundle.getBundle("open/dolphin/client/resources/LabResultParser").getString("text.unReported");
    String NO_RESULT = java.util.ResourceBundle.getBundle("open/dolphin/client/resources/LabResultParser").getString("text.noResultValue");
    String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm";
    String DATE_FORMAT_8 = "yyyyMMdd";
    String DATE_FORMAT_10 = "yyyy-MM-dd";
    String CSV_DELIM = "\\s*,\\s*";
    
    List<NLaboImportSummary> parse(Path path) throws IOException;
}
