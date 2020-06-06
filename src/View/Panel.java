package View;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import Controllers.Controller;
import Utilisateurs.User;

/**
 * @author BOCHER, CADOT et GAUTIER 
 * classe qui hérite de JPanel et qui créer la fenêtre générale 
 */
public class Panel extends JPanel 
{
        /**
        * serialVersionUID : clé de hachage de la classe
        * user : utilisateur
        * controller : contrôleur des actions sur la fenêtre
        */
	private static final long serialVersionUID = 8254914084932068228L;
	
	User user = null;
	Controller controller = null;
	
        /**
        * Constructeur     
        * @param user
        * @param controller
        */
	public Panel(User user, Controller controller)
	{
		this.controller = controller;
		this.controller.setControlledView(this);
		
		this.user = user;
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.setBackground(Color.white);
        
        this.setVisible(true);
        validate();
	}
	
        /**
        * Méthode qui retourne l'utilisateur connecté     
        * @return user
        */
	public User getUser() { return user; }
}
