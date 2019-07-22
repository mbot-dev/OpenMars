/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package open.dolphin.order;

/**
 *
 * @author kazushi
 */
public interface IRpView {
    
       
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
     * @return the inRadio
     */
    javax.swing.JRadioButton getInRadio();

    /**
     * @return the infoLabel
     */
    javax.swing.JLabel getInfoLabel();

    /**
     * @return the medicineCheck
     */
    javax.swing.JCheckBox getMedicineCheck();

    /**
     * @return the okBtn
     */
    javax.swing.JButton getOkBtn();

    /**
     * @return the okCntBtn
     */
    javax.swing.JButton getOkCntBtn();

    /**
     * @return the outRadio
     */
    javax.swing.JRadioButton getOutRadio();

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
     * @return the usageCheck
     */
    javax.swing.JCheckBox getUsageCheck();

    /**
     * @return the usageCombo
     */
    javax.swing.JComboBox getUsageCombo();

    javax.swing.JCheckBox getTonyoChk();

    /**
     * @return the partialChk
     */
    javax.swing.JCheckBox getPartialChk();

    javax.swing.JCheckBox getTemporalChk();
    
//s.oh^ 2014/10/22 Icon表示
javax.swing.JLabel getSearchLabel();
//s.oh$
    
}
