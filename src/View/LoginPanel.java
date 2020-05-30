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
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Controllers.LoginPanelController;
import Utilitaires.ImageManager;
import Utilitaires.path;

public class LoginPanel extends JPanel {
    
	private static final long serialVersionUID = -7196042227727118997L;
	
	LoginPanelController controller = null;
	
	private JLabel background;
	private JTextField champ_email;
    private JPasswordField champ_mdp;
    private JButton jButton1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    
    public LoginPanel(LoginPanelController controller)
    {
    	controller.setControlledView(this);
    	this.controller = controller;
    	this.setLayout(new GridBagLayout());
    	
    	System.out.println(path.getImagePath("login.png"));
    	try {
    		background = new JLabel(new ImageIcon( ImageManager.LoadImage(path.getImagePath("login.png"), java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/2, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/2) ));
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
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 20, 5, 20);
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 2;
		background.add(jLabel3, c);

        champ_email.setBackground(new Color(143, 202, 214));
        champ_email.setToolTipText("");
        champ_email.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102)));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 1;
		background.add(champ_email, c);

        champ_mdp.setBackground(new java.awt.Color(143, 202, 214));
        champ_mdp.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102)));
        c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 3;
		background.add(champ_mdp, c);

        jButton1.setText("Se connecter");
        jButton1.setName("btLogin");
        jButton1.addActionListener(controller);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 20, 20, 20);
        c.ipady = 0;
		c.weightx = 0.0;
		c.weighty = 0.2;
		c.gridx = 0;
		c.gridy = 5;
		background.add(jButton1, c);
		
		champ_email.setText("Renaldo.Policastro@edu.ece.fr");
		champ_mdp.setText("IBwJZh}^JF");
    }

	public String getEmail() {
		return champ_email.getText();
	}
	public String getPassword() {
		return new String(champ_mdp.getPassword());
	}
	public JLabel getBackgroundPanel() {
		return background;
	}
}
