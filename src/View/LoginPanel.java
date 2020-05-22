/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
    
	private static final long serialVersionUID = -7196042227727118997L;
	
	private JTextField champ_email;
    private JPasswordField champ_mdp;
    private JButton jButton1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    
    
    public LoginPanel() {
    	this.setBounds(0, 0, 1200, 720);
    	this.setLayout(null);
    	
        initComponents();
        
        this.setVisible(true);
        validate();
    }

    private void initComponents() 
    {
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        champ_email = new JTextField();
        champ_mdp = new JPasswordField();
        jButton1 = new JButton();
        jLabel1 = new JLabel();

       

        jLabel2.setText("Adresse e-mail :");
        // Ca : getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 100, -1));
        //Devient :
        jLabel2.setBounds(200, 110, 100, 20);
        this.add(jLabel2);
        

        jLabel3.setText("Mot de passe :");
        jLabel3.setBounds(207, 180, 85, 20);
        this.add(jLabel3);

        champ_email.setBackground(new Color(143, 202, 214));
        champ_email.setToolTipText("");
        champ_email.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102)));
        champ_email.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                champ_emailActionPerformed(evt);
            }
        });
        champ_email.setBounds(165, 140, 170, 30);
        this.add(champ_email);

        champ_mdp.setBackground(new java.awt.Color(143, 202, 214));
        champ_mdp.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102)));
        champ_mdp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                champ_mdpActionPerformed(evt);
            }
        });
        champ_mdp.setBounds(165, 205, 170, 30);
        this.add(champ_mdp);

        jButton1.setText("Se connecter");
        jButton1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.setBounds(185, 270, 130, 40);
        this.add(jButton1);

        jLabel1.setIcon(new ImageIcon(getClass().getResource("/View/images/login.png")));
        jLabel1.setBounds(0, 0, 500, 500);
        this.add(jLabel1);
    }

    private void champ_emailActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void champ_mdpActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:

    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        
    }

    private void jButton1MouseClicked(MouseEvent evt) {
           
    }
}
