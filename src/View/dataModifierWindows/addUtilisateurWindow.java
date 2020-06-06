package View.dataModifierWindows;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import Controllers.dataModifierWindowsControllers.dataModifierController;
import Donnees.Donnee;
import Groupes.Groupe;
import UI_Elements.Button;
import UI_Elements.JEditableComboBoxList;
import Utilisateurs.User;
import Utilisateurs.User.UserType;
import Utilisateurs.Utilisateur;

/**
 * @author BOCHER, CADOT et GAUTIER 
 * classe qui hérite de JFrame et qui ajoute un utilisateur à la fenêtre
 */
public class addUtilisateurWindow extends JFrame
{
    /**
        * serialVersionUID : clé de hachage de la classe
        * user : utilisateur
        * controller : contrôleur des actions sur la fenêtre
        * userTypeComboBox : de type JComboBox de type UserType
        * emailTextField : de type JTextField
        * nomTextField : de type JTextField
        * prenomTextField : de type JTextField
        * groupeComboBox : de type JComboBox de type Object
        * coursComboBoxList : de type JEditableComboBoxList
        */
	private static final long serialVersionUID = 4318587476356190117L;
	
	User user = null;
	dataModifierController controller = null;
	
	private JComboBox<UserType> userTypeComboBox = null;
	private JTextField emailTextField = new JTextField();
    private JTextField nomTextField = new JTextField();
    private JTextField prenomTextField = new JTextField();
    private JComboBox<Object> groupeComboBox;
    private JEditableComboBoxList coursComboBoxList;
    
        /**
         * Constructeur
         * @param user
         * @param controller 
         */
	public addUtilisateurWindow(User user, dataModifierController controller) 
	{
		this.user = user;
		this.controller = controller;

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setName("Adding a user");
		this.setLayout(new GridBagLayout());
		this.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/3, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/2);
		
		initComponents();
		
		this.controller.setControlledView(this);
		
		this.setVisible(true);
		this.validate();
	}
	
        /**
        * Méthode qui initialise le contenu de la fenêtre d'ajout d'une utilisateur    
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
		String[] componentsNames = {"Droit", "Email", "Nom", "Prenom"};
		for (String name : componentsNames) {
			this.add(new JLabel(name), constraints);
			constraints.gridy+=1;
		}
		
		UserType[] types = {UserType.Etudiant, UserType.Enseignant, UserType.Referent_pedagogique};
		userTypeComboBox = new JComboBox<User.UserType>(types);
		userTypeComboBox.addActionListener(controller);
		
		groupeComboBox = new JComboBox<Object>(user.ListeGroupes().getAll().toArray());
		coursComboBoxList = new JEditableComboBoxList(user.ListeCours().getAll().toArray(), "a course");
		coursComboBoxList.setVisible(true);
		
		constraints.insets = new Insets(5, 0, 5, 0);
		constraints.gridx = 1;
		constraints.gridy = 0;
		this.add(userTypeComboBox, constraints);
		constraints.gridy = 1;
		this.add(emailTextField, constraints);
		constraints.gridy = 2;
		this.add(nomTextField, constraints);
		constraints.gridy = 3;
		this.add(prenomTextField, constraints);
		
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		
		constraints.gridy = 4;
		this.add(groupeComboBox, constraints);
		constraints.gridy = 5;
		this.add(coursComboBoxList, constraints);
		coursComboBoxList.setVisible(false);
		constraints.gridy = 6;
		this.add(new Button("btAdd", "Add", controller), constraints);
	}
	
        /**
         * Méthode qui rend visible ou invisible une JComboBox
         */
	public void ToggleJComboBoxLists() {
		groupeComboBox.setVisible((userTypeComboBox.getSelectedItem() == UserType.Etudiant));
		coursComboBoxList.setVisible(!(userTypeComboBox.getSelectedItem() == UserType.Etudiant));
	}	
	
        /**
         * Méthode qui récupère l'email de l'utilisateur
         * @return emailTextField.getText()
         */
	public String getEmail() {
		return emailTextField.getText();
	}
        
        /**
         * Méthode qui récupère le nom de l'utilisateur
         * @return nomTextField.getText()
         */
	public String getNom() {
		return nomTextField.getText();
	}

        /**
         * Méthode qui récupère le prénom de l'utilisateur
         * @return prenomTextField.getText()
         */
	public String getPrenom() {
		return prenomTextField.getText();
	}

        /**
         * Méthode qui récupère le type de l'utilisateur
         * @return userTypeComboBox.getSelectedItem()
         */
	public UserType getUserType() {
		return (UserType) userTypeComboBox.getSelectedItem();
	}
	
        /**
         * Méthode qui récupère les cours
         * @return coursComboBoxList.getSelectedItems().stream().map(x -> (Donnee)x).collect(Collectors.toList())
         */
	public List<Donnee> getCours() {
		return coursComboBoxList.getSelectedItems().stream().map(x -> (Donnee)x).collect(Collectors.toList());
	}

        /**
         * Méthode qui récupère le groupe
         * @return groupeComboBox.getSelectedItem()
         */
	public Groupe getGroupe() {
		return (Groupe) groupeComboBox.getSelectedItem();
	}
	
        /**
         * Méthode qui remplis les zones de texte avec les informations relatives à l'utilisateur
         * @param utilisateur 
         */
	public void fillFields(Utilisateur utilisateur)
	{
		userTypeComboBox.setSelectedItem(utilisateur.getType());
		userTypeComboBox.setEnabled(false);
		emailTextField.setText(utilisateur.getEmail());
	    nomTextField.setText(utilisateur.getNom());
	    prenomTextField.setText(utilisateur.getPrenom());
	    groupeComboBox.setSelectedItem(utilisateur.getGroupe());
	    coursComboBoxList.setSelectedItems(utilisateur.getCoursDonnes().toArray());
	    ToggleJComboBoxLists();
	}
}
