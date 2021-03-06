/*
 * PatientSearchView.java
 *
 * Created on 2007/11/22, 18:43
 */

package open.dolphin.impl.schedule;

import open.dolphin.impl.psearch.*;

/**
 * (予定カルテ対応)
 * @author  kazushi
 */
public class PatientScheduleView extends javax.swing.JPanel {
   
    
    /** Creates new form PatientSearchView */
    public PatientScheduleView() {
        initComponents();
    }

    public javax.swing.JLabel getCountLbl() {
        return countLbl;
    }

    public javax.swing.JTable getTable() {
        return table;
    }

    public javax.swing.JTextField getKeywordFld() {
        return keywordFld;
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        table = new AddressTipsTable();
        keywordFld = new javax.swing.JTextField();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        countLbl = new javax.swing.JLabel();
        rpButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        claimChk = new javax.swing.JCheckBox();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("open/dolphin/impl/schedule/resources/PatientScheduleView"); // NOI18N
        keywordFld.setToolTipText(bundle.getString("keywordFld.toolTipText")); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText(bundle.getString("scheduleDateLabel.text")); // NOI18N

        countLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        countLbl.setText(bundle.getString("countLabel.text")); // NOI18N

        rpButton.setText(bundle.getString("rpButton.text")); // NOI18N
        rpButton.setToolTipText(bundle.getString("rpButton.toolTipText")); // NOI18N

        updateButton.setText(bundle.getString("updateButton.text")); // NOI18N
        updateButton.setToolTipText(bundle.getString("updateButton.toolTipText")); // NOI18N

        claimChk.setText(bundle.getString("claimChk.text")); // NOI18N
        claimChk.setToolTipText(bundle.getString("claimChk.toolTipText")); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(keywordFld, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 188, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(countLbl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(updateButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(rpButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(claimChk)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(keywordFld, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(countLbl)
                    .add(updateButton)
                    .add(rpButton)
                    .add(claimChk))
                .add(7, 7, 7)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox claimChk;
    private javax.swing.JLabel countLbl;
    private javax.swing.JTextField keywordFld;
    private javax.swing.JButton rpButton;
    private javax.swing.JTable table;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JButton getRpButton() {
        return rpButton;
    }

    public javax.swing.JCheckBox getClaimChk() {
        return claimChk;
    }

    public javax.swing.JButton getUpdateButton() {
        return updateButton;
    }
}
