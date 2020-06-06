package View.dataModifierWindows;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import Controllers.dataModifierWindowsControllers.dataModifierController;
import Donnees.Donnee;
import Salles.Salle;
import UI_Elements.Button;
import Utilisateurs.User;

/**
 * @author BOCHER, CADOT et GAUTIER 
 * classe qui hérite de JFrame et qui ajoute une salle à la fenêtre
 */
public class addSalleWindow extends JFrame
{
        /**
        * serialVersionUID : clé de hachage de la classe
        * user : utilisateur
        * controller : contrôleur des actions sur la fenêtre
        * nomTextField : de type JTextField
        * capaciteSpinner : type JSpinner
        * siteComboBox : type JComboBox de type Object
        */
	private static final long serialVersionUID = -6636132992670415922L;
	
	User user = null;
	dataModifierController controller = null;
	
	private JTextField nomTextField = new JTextField();
	private JSpinner capaciteSpinner = new JSpinner();
	private JComboBox<Object> siteComboBox = null;

        /**
         * Constructeur
         * @param user
         * @param controller 
         */
	public addSalleWindow(User user, dataModifierController controller) {
		this.user = user;
		this.controller = controller;

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setName("Adding a salle");
		this.setLayout(new GridBagLayout());
		this.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/3, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/3);
		
		initComponents();
		
		this.controller.setControlledView(this);
		
		this.setVisible(true);
		this.validate();
	}
	
        /**
        * Méthode qui initialise le contenu de la fenêtre d'ajout d'une salle     
        */
	private void initComponents()
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 10, 0, 0);
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		
		String[] componentsNames = {"Nom", "Capacité", "Site"};
		for (String name : componentsNames) {
			this.add(new JLabel(name), constraints);
			constraints.gridy+=1;
		}
		
		siteComboBox = new JComboBox<Object>( user.ListeSite().getAll().toArray() );
		
		constraints.insets = new Insets(5, 0, 5, 0);
		constraints.gridx = 1;
		constraints.gridy = 0;
		this.add(nomTextField, constraints);
		constraints.gridy = 1;
		this.add(capaciteSpinner, constraints);
		constraints.gridy = 2;
		this.add(siteComboBox, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		this.add(new Button("btAdd", "Add", controller), constraints);
	}
	
        /**
         * Méthode qui récupère le numéro de la salle
         * @return nomTextField.getText()
         */
	public String getNom() {
		return nomTextField.getText();
	}
	
        /**
         * Méthode qui récupère la capacité de la salle
         * @return Integer.parseInt(capaciteSpinner.getValue().toString())
         */
	public int getCapacite() {
		return Integer.parseInt(capaciteSpinner.getValue().toString());
	}
	
        /**
         * Méthode qui récupère le site où se trouve la salle
         * @return siteComboBox.getSelectedItem()
         */
	public Donnee getSite() {
		return (Donnee) siteComboBox.getSelectedItem();
	}

        /**
         * Méthode qui complète les zones de texte qui correspondents aux informations sur les salles
         * @param salle 
         */
	public void fillFields(Salle salle) {
		nomTextField.setText(salle.getNom());
		capaciteSpinner.setValue(salle.getCapacite());
		siteComboBox.setSelectedItem(salle.getSite());
	}

}
