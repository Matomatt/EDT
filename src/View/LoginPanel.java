/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;


/**
 *
 * @author Célia BOCHER
 */
public class LoginPanel extends javax.swing.JPanel {
    
	private static final long serialVersionUID = -7196042227727118997L;
	
	private javax.swing.JTextField champ_email;
    private javax.swing.JPasswordField champ_mdp;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    
    
    public LoginPanel() {
    	this.setBounds(0, 0, 1200, 720);
    	this.setLayout(null);
    	
        initComponents();
        
        this.setVisible(true);
        validate();
    }

    private void initComponents() 
    {
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        champ_email = new javax.swing.JTextField();
        champ_mdp = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

       

        jLabel2.setText("Adresse e-mail :");
        jLabel2.setBounds(200, 110, 100, 20);
        this.add(jLabel2);
        

        jLabel3.setText("Mot de passe :");
        jLabel3.setBounds(207, 180, 85, 20);
        this.add(jLabel3);

        champ_email.setBackground(new java.awt.Color(143, 202, 214));
        champ_email.setToolTipText("");
        champ_email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        champ_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                champ_emailActionPerformed(evt);
            }
        });
        champ_email.setBounds(165, 140, 170, 30);
        this.add(champ_email);

        champ_mdp.setBackground(new java.awt.Color(143, 202, 214));
        champ_mdp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        champ_mdp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                champ_mdpActionPerformed(evt);
            }
        });
        champ_mdp.setBounds(165, 205, 170, 30);
        this.add(champ_mdp);

        jButton1.setText("Se connecter");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.setBounds(185, 270, 130, 40);
        this.add(jButton1);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/images/login.png"))); // NOI18N
        jLabel1.setBounds(0, 0, 500, 500);
        this.add(jLabel1);
    }

    private void champ_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_champ_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_champ_emailActionPerformed

    private void champ_mdpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_champ_mdpActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_champ_mdpActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
                // TODO add your handling code here:
    }
}
