package View.dataModifierWindows;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import Controllers.dataModifierWindowsControllers.addSeanceWindowController;
import Donnees.Donnee;
import Groupes.Groupe;
import Salles.Salle;
import UI_Elements.Button;
import UI_Elements.JEditableComboBoxList;
import Utilisateurs.User;
import Utilisateurs.Utilisateur;

public class addSeanceWindow extends JFrame
{
	private static final long serialVersionUID = 4318587476356190117L;
	
	User user = null;
	addSeanceWindowController controller = null;
	
	private JFormattedTextField dateTextField = new JFormattedTextField(new SimpleDateFormat("yyyy-mm-dd"));
    private JFormattedTextField heureDebutTextField = new JFormattedTextField();
    private JFormattedTextField heureFinTextField = new JFormattedTextField();
    private JEditableComboBoxList groupesComboBoxList = null;
    private JEditableComboBoxList enseignantsComboBoxList = null;
    private JEditableComboBoxList sallesComboBoxList = null;
    private JSpinner etatSpinner = new JSpinner();
    private JComboBox<Object> coursComboBox = null;
    private JComboBox<Object> typeDeCoursComboBox = null;
    
	public addSeanceWindow(User user, addSeanceWindowController controller) 
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
		String[] componentsNames = {"Date", "Heure de début", "Heure de fin", "Etat", "Cours", "Type du cours", "Groupes", "Enseignants", "Salles"};
		for (String name : componentsNames) {
			this.add(new JLabel(name), constraints);
			constraints.gridy+=1;
		}
		
		heureDebutTextField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(new SimpleDateFormat("HH':'mm"))));
		heureFinTextField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(new SimpleDateFormat("HH':'mm"))));
		
		coursComboBox = new JComboBox<Object>( user.ListeCours().getAll().toArray() );
		coursComboBox.addActionListener(controller);
		typeDeCoursComboBox = new JComboBox<Object>( user.ListeType_cours().getAll().toArray() );
		
		groupesComboBoxList = new JEditableComboBoxList(user.ListeGroupes().getAll().toArray(), "a course");
		sallesComboBoxList = new JEditableComboBoxList(user.ListeSalles().getAll().toArray(), "a room");
		
		constraints.insets = new Insets(5, 0, 5, 0);
		constraints.gridx = 1;
		constraints.gridy = 0;
		this.add(dateTextField, constraints);
		constraints.gridy = 1;
		this.add(heureDebutTextField, constraints);
		constraints.gridy = 2;
		this.add(heureFinTextField, constraints);
		constraints.gridy = 3;
		this.add(etatSpinner, constraints);
		constraints.gridy = 4;
		this.add(coursComboBox, constraints);
		constraints.gridy = 5;
		this.add(typeDeCoursComboBox, constraints);
		constraints.gridy = 6;
		this.add(groupesComboBoxList, constraints);
		
		ChangeListEnseignant();
		
		constraints.gridy = 8;
		this.add(sallesComboBoxList, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 9;
		constraints.gridwidth = 2;
		this.add(new Button("btAdd", "Add", controller), constraints);
	}

	public User getUser() { return user; }
	
	public int getSemaine() throws NumberFormatException, ParseException {
		return Integer.parseInt( new SimpleDateFormat("w").format(new SimpleDateFormat("yyyy-MM-dd").parse(dateTextField.getText())) );
	}

	public Date getDate() {
		return Date.valueOf(dateTextField.getText());
	}

	public Time getHeureDebut() {
		System.out.println(heureDebutTextField.getText());
		return Time.valueOf(heureDebutTextField.getText()+":00");
	}
	
	public Time getHeureFin() {
		return Time.valueOf(heureFinTextField.getText()+":00");
	}

	public int getEtat() {
		return Integer.parseInt(etatSpinner.getValue().toString());
	}

	public Donnee getCours() {
		return (Donnee) coursComboBox.getSelectedItem();
	}
	public Donnee getTypeDeCours() {
		return (Donnee) typeDeCoursComboBox.getSelectedItem();
	}

	public List<Groupe> getGroupes() {
		return groupesComboBoxList.getSelectedItems().stream().map(x -> (Groupe)x).collect(Collectors.toList());
	}
	
	public List<Utilisateur> getEnseignants() {
		return enseignantsComboBoxList.getSelectedItems().stream().map(x -> (Utilisateur)x).collect(Collectors.toList());
	}
	
	public void ChangeListEnseignant()
	{
		if (enseignantsComboBoxList != null)
			this.remove(enseignantsComboBoxList);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(5, 0, 5, 0);
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 1;
		constraints.gridy = 7;
		
		enseignantsComboBoxList = new JEditableComboBoxList(user.ListeUtilisateurs().getEnseignantsByCours((Donnee)coursComboBox.getSelectedItem()).toArray(), "a professor");
		
		this.add(enseignantsComboBoxList, constraints);
		
		validate();
	}
	
	public List<Salle> getSalles() {
		return sallesComboBoxList.getSelectedItems().stream().map(x -> (Salle)x).collect(Collectors.toList());
	}
}