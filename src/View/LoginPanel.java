/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Utilitaires.ImageManager;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginPanel extends JPanel {
    
	private static final long serialVersionUID = -7196042227727118997L;
	
	BaseWindow baseWindow;
	
	private JLabel background;
	private JTextField champ_email;
    private JPasswordField champ_mdp;
    private JButton jButton1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    
    public LoginPanel(BaseWindow _baseWindow)
    {
    	baseWindow = _baseWindow;
    	this.setLayout(new GridBagLayout());
    	
    	try {
    		background = new JLabel(new ImageIcon( ImageManager.LoadImage("./Images/login.png", java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/2, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/2) ));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	background.setLayout(new GridBagLayout());
        initComponents();
        
        this.add(background);
        
        background.setVisible(true);
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
        new JLabel();
        
        jLabel2.setFont(new Font(jLabel2.getFont().getName(), Font.PLAIN, 20));
        jLabel3.setFont(new Font(jLabel3.getFont().getName(), Font.PLAIN, 20));
        champ_email.setFont(new Font(champ_email.getFont().getName(), Font.PLAIN, 20));
        champ_mdp.setFont(new Font(champ_mdp.getFont().getName(), Font.PLAIN, 20));
        jButton1.setFont(new Font(jButton1.getFont().getName(), Font.PLAIN, 20));

        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 0.0;
		c.gridwidth = 1;

        jLabel2.setText("Adresse e-mail :");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(40, 20, 5, 20);
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		background.add(jLabel2, c);
        

        jLabel3.setText("Mot de passe :");
        //jLabel3.setBounds(207, 180, 85, 20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 20, 5, 20);
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 2;
		background.add(jLabel3, c);

        champ_email.setBackground(new Color(143, 202, 214));
        champ_email.setToolTipText("");
        champ_email.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102)));
        champ_email.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                champ_emailActionPerformed(evt);
            }
        });
        //champ_email.setBounds(165, 140, 170, 30);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 1;
		background.add(champ_email, c);

        champ_mdp.setBackground(new java.awt.Color(143, 202, 214));
        champ_mdp.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102)));
        champ_mdp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                champ_mdpActionPerformed(evt);
            }
        });
        //champ_mdp.setBounds(165, 205, 170, 30);
        c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 3;
		background.add(champ_mdp, c);

        jButton1.setText("Se connecter");
        jButton1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                try {
                    jButton1MouseClicked(evt);
                } catch (ParseException ex) {
                    Logger.getLogger(LoginPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        //jButton1.setBounds(185, 270, 130, 40);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 20, 20, 20);
        c.ipady = 0;
		c.weightx = 0.0;
		c.weighty = 0.2;
		c.gridx = 0;
		c.gridy = 5;
		background.add(jButton1, c);
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

    private void jButton1MouseClicked(MouseEvent evt) throws ParseException {
           if (!baseWindow.Connect(champ_email.getText(), champ_mdp.getText()))
           {
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.HORIZONTAL;
				c.insets = new Insets(40, 20, 5, 20);
				c.weighty = 0.0;
				c.gridwidth = 1;
				c.weightx = 0.5;
				c.gridx = 0;
				c.gridy = 4;
				background.add(new JLabel("Login or password incorrect"), c);
				validate();
           }
        	  
    }
}
