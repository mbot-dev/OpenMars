package open.dolphin.order;

/**
 *
 * @author Kazushi Minagawa.
 */
public interface IRadView {
    
       
    /**
     * @return the clearBtn
     */
    javax.swing.JButton getClearBtn();

    /**
     * @return the countField
     */
    javax.swing.JTextField getCountField();

    /**
     * @return the deleteBtn
     */
    javax.swing.JButton getDeleteBtn();

    /**
     * @return the infoLabel
     */
    javax.swing.JLabel getInfoLabel();

    /**
     * @return the okBtn
     */
    javax.swing.JButton getOkBtn();

    /**
     * @return the okCntBtn
     */
    javax.swing.JButton getOkCntBtn();

    /**
     * @return the partBtn
     */
    javax.swing.JButton getPartBtn();

    /**
     * @return the partCheck
     */
    javax.swing.JCheckBox getPartCheck();

    /**
     * @return the rtCheck
     */
    javax.swing.JCheckBox getRtCheck();

    /**
     * @return the searchResultTable
     */
    javax.swing.JTable getSearchResultTable();

    /**
     * @return the searchTextField
     */
    javax.swing.JTextField getSearchTextField();

    /**
     * @return the setTable
     */
    javax.swing.JTable getSetTable();

    /**
     * @return the stampNameField
     */
    javax.swing.JTextField getStampNameField();

    /**
     * @return the techCheck
     */
    javax.swing.JCheckBox getTechCheck();

    /**
     * @return the parialChk
     */
    javax.swing.JCheckBox getPartialChk();

//s.oh^ 2014/03/31 スタンプ回数対応
javax.swing.JComboBox getNumberCombo();
//s.oh$
    
//s.oh^ 2014/10/22 Icon表示
javax.swing.JLabel getSearchLabel();
//s.oh$

}
