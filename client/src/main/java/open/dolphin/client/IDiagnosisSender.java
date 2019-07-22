package open.dolphin.client;

import java.util.List;
import open.dolphin.infomodel.RegisteredDiagnosisModel;

/**
 *
 * @author Kazushi Minagawa. Digital Globe, Inc.
 */
public interface IDiagnosisSender {

    Chart getContext();

    void setContext(Chart context);

    void prepare(List<RegisteredDiagnosisModel> data);

    void send(List<RegisteredDiagnosisModel> data);

}
