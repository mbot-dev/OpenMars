package open.dolphin.client;

import open.dolphin.infomodel.LetterModule;

/**
 * 新規文書インターフェイス。
 * 
 * @author Kazushi Minagawa. Digital Globe, Inc.
 */
public interface Letter extends NChartDocument {

    void modelToView(LetterModule model);
    
    void viewToModel(boolean print);

    void setEditables(boolean b);

    void setListeners();

    boolean letterIsDirty();

    void makePDF();
    
}
