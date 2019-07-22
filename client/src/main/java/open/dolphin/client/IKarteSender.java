package open.dolphin.client;

import open.dolphin.infomodel.DocumentModel;

/**
 *
 * @author Kazushi Minagawa. Digital Globe, Inc.
 */
public interface IKarteSender {

    Chart getContext();

    void setContext(Chart context);

    void prepare(DocumentModel data);

    void send(DocumentModel data);

}