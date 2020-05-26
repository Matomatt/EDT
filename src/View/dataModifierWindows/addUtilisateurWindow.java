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
import Controllers.dataModifierWindowsControllers.addUtilisateurWindowController;
import Donnees.Donnee;
import Groupes.Groupe;
import UI_Elements.Button;
import UI_Elements.JEditableComboBoxList;
import Utilisateurs.User;
import Utilisateurs.User.UserType;

public class addUtilisateurWindow extends JFrame
{
	private static final long serialVersionUID = 4318587476356190117L;
	
	User user = null;
	addUtilisateurWindowController controller = null;
	
	private JComboBox<UserType> userTypeComboBox = null;
	private JTextField emailTextField = new JTextField();
    private JTextField nomTextField = new JTextField();
    private JTextField prenomTextField = new JTextField();
    private JComboBox<Object> groupeComboBox;
    private JEditableComboBoxList coursComboBoxList;
    
	public addUtilisateurWindow(User user, addUtilisateurWindowController controller) 
	{
		this.user = user;
		this.controller = controller;
		controller.setControlledView(this);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setName("Adding a séance");
		this.setLayout(new GridBagLayout());
		this.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/3, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/2);
		
		initComponents();
		
		this.setVisible(true);
		this.validate();
	}
	
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
		coursComboBoxList = new JEditableComboBoxList(user.ListeCours().getAll().toArray(), "un cours");
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
		constraints.gridy = 6;
		this.add(new Button("btAdd", "Add", controller), constraints);
	}
	
	public String getEmail() {
		return emailTextField.getText();
	}

	public String getNom() {
		return nomTextField.getText();
	}

	public String getPrenom() {
		return prenomTextField.getText();
	}

	public UserType getUserType() {
		return (UserType) userTypeComboBox.getSelectedItem();
	}
	
	public List<Donnee> getCours() {
		return coursComboBoxList.getSelectedItems().stream().map(x -> (Donnee)x).collect(Collectors.toList());
	}

	public Groupe getGroupe() {
		return (Groupe) groupeComboBox.getSelectedItem();
	}
	
	
}
