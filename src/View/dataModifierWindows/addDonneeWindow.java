package View.dataModifierWindows;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Controllers.dataModifierWindowsControllers.dataModifierController;
import Donnees.Donnee;
import UI_Elements.Button;
import Utilisateurs.User;

/**
 * @author BOCHER, CADOT et GAUTIER 
 * classe qui hérite de JFrame et qui ajoute des données à la fenêtre
 */
public class addDonneeWindow extends JFrame
{
        /**
        * serialVersionUID : clé de hachage de la classe
        * user : utilisateur
        * controller : contrôleur des actions sur la fenêtre
        * type : de type String
        * nomTextField : de type JTextField
        */
	private static final long serialVersionUID = -1858197361063216417L;
	
	User user = null;
	dataModifierController controller = null;
	String type = "";
	
	private JTextField nomTextField = new JTextField();

        /**
         * Constructeur
         * @param user
         * @param controller
         * @param type 
         */
	public addDonneeWindow(User user, dataModifierController controller, String type) {
		this.user = user;
		this.controller = controller;
		this.type = type;

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setName("Adding a " + type);
		this.setLayout(new GridBagLayout());
		this.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/3, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/6);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 10, 0, 0);
		constraints.weightx = 0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		
		this.add(new JLabel(type), constraints);
		
		constraints.weightx = 1.0;
		constraints.gridx = 1;
		constraints.gridy = 0;
		this.add(nomTextField, constraints);
		
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 1;
		this.add(new Button("btAdd", "Add", controller), constraints);
		
		this.controller.setControlledView(this);
		
		this.setVisible(true);
		this.validate();
	}

        /**
         * Méthode qui récupère le type de donnée
         * @return type
         */
	public String getTypeDonnee() { return type; }
        
        /**
         * Méthode qui récupère le texte renseigné
         * @return nomTextField.getText()
         */
	public String getValue() { return nomTextField.getText(); }

        /**
         * Méthode qui rempli les zones de texte
         * @param donnee 
         */
	public void fillFields(Donnee donnee) {
		nomTextField.setText(donnee.getValue());
	}
}
