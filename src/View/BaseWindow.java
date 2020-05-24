package View;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Seances.Seance;
import Utilisateurs.ConnectionViaUser;
import Utilisateurs.User;
import Utilitaires.ConnectionErrorException;
import Utilitaires.UserNotFoundException;

public class BaseWindow extends JFrame {

	private static final long serialVersionUID = 3528066671950303397L;
	
	JPanel mainWindow;
	User user = null;
	
	GridBagConstraints c = new GridBagConstraints();
	
	public BaseWindow() {
		this.setLayout(new GridBagLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		mainWindow = new LoginPanel(this);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		this.add(mainWindow, c);
		
		//addComponentsToPane();
		
		this.setVisible(true);
		this.pack();
	}
	
	public void addComponentsToPane() 
	{
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.weighty = 0.0;
		c.gridwidth = 1;
		c.gridy = 0;

        JButton button;
        
		button = new JButton("Emplois du Temps");
		c.gridx = 0;
		this.add(button, c);
	
		button = new JButton("Récapitulatif des Cours");
		c.gridx = 1;
		this.add(button, c);
	
		button = new JButton("Reporting");
		c.gridx = 2;
		this.add(button, c);
		
		//Automatiquement sur edt
    }

	public void SwitchPage(JPanel newPage)
	{
		this.remove(mainWindow);
		mainWindow = newPage;
		this.add(mainWindow, c);
	}
	
	public boolean Connect(String login, String password) {
		login = "admin";
		password = "pw";
		try {
			System.out.println(login + " " + password);
			user = new ConnectionViaUser(login, password);
		} catch (UserNotFoundException | ClassNotFoundException | ConnectionErrorException e) {
			return false;
		}
		
		for (Seance s : user.ListeSeances().getByUtilisateurAtDate(user.ListeUtilisateurs().getByID(1709), "2020-05-26")) {
			System.out.println(s);
		}
		
		SwitchPage(new ModifAdminPanel(user));
		addComponentsToPane();
		validate();
		System.out.println(user.getUtilisateurConnecte());
		
		
		
		return true;
	}
}
