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
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import com.toedter.calendar.JDateChooser;

import Controllers.dataModifierWindowsControllers.dataModifierController;
import Donnees.Donnee;
import Groupes.Groupe;
import Salles.Salle;
import Seances.Seance;
import UI_Elements.Button;
import UI_Elements.JEditableComboBoxList;
import Utilisateurs.User;
import Utilisateurs.Utilisateur;

/**
 * @author BOCHER, CADOT et GAUTIER 
 * classe qui hérite de JFrame et qui ajoute une séance à la fenêtre
 */
public class addSeanceWindow extends JFrame
{
        /**
        * serialVersionUID : clé de hachage de la classe
        * user : utilisateur
        * controller : contrôleur des actions sur la fenêtre
        * heureDebutTextField : de type JFormattedTextField
        * heureFinTextField : de type JFormattedTextField
        * groupesComboBoxList : de type JEditableComboBoxList
        * enseignantsComboBoxList : de type JEditableComboBoxList
        * sallesComboBoxList : de type JEditableComboBoxList
        * etats = {"En cours de validation", "Validée", "Annulée"} : tableau de String
        * etatComboBox : de type JComboBox de type String
        * coursComboBox : de type JComboBox de type Object
        * typeDeCoursComboBox : de type JComboBox de type Object
        */
	private static final long serialVersionUID = 4318587476356190117L;
	
	User user = null;
	dataModifierController controller = null;
	
	private JDateChooser dateChooser = new JDateChooser();
    private JFormattedTextField heureDebutTextField = new JFormattedTextField();
    private JFormattedTextField heureFinTextField = new JFormattedTextField();
    private JEditableComboBoxList groupesComboBoxList = null;
    private JEditableComboBoxList enseignantsComboBoxList = null;
    private JEditableComboBoxList sallesComboBoxList = null;
    private static final String[] etats = {"En cours de validation", "Validée", "Annulée"};
    private JComboBox<String> etatComboBox = new JComboBox<String>(etats);
    private JComboBox<Object> coursComboBox = null;
    private JComboBox<Object> typeDeCoursComboBox = null;
    
        /**
         * Constructeur
         * @param user
         * @param controller 
         */
	public addSeanceWindow(User user, dataModifierController controller) 
	{
		this.user = user;
		this.controller = controller;
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setName("Adding a séance");
		this.setLayout(new GridBagLayout());
		this.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/2, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/2);
		
		initComponents();
		
		this.controller.setControlledView(this);
		
		this.setVisible(true);
		this.validate();
	}
	
        /**
        * Méthode qui initialise le contenu de la fenêtre d'ajout d'une séance    
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
		String[] componentsNames = {"Date", "Heure de début (hh:mm)", "Heure de fin (hh:mm)", "Etat", "Cours", "Type du cours", "Groupes", "Enseignants", "Salles"};
		for (String name : componentsNames) {
			this.add(new JLabel(name), constraints);
			constraints.gridy+=1;
		}
		
		dateChooser.setDate(new java.util.Date());
		
		heureDebutTextField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(new SimpleDateFormat("HH':'mm"))));
		heureFinTextField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(new SimpleDateFormat("HH':'mm"))));
		
		coursComboBox = new JComboBox<Object>( user.ListeCours().getAll().toArray() );
		coursComboBox.addActionListener(controller);
		typeDeCoursComboBox = new JComboBox<Object>( user.ListeType_cours().getAll().toArray() );
		
		groupesComboBoxList = new JEditableComboBoxList(user.ListeGroupes().getAll().toArray(), "a group");
		sallesComboBoxList = new JEditableComboBoxList(user.ListeSalles().getAll().toArray(), "a room");
		
		constraints.insets = new Insets(5, 0, 5, 0);
		constraints.gridx = 1;
		constraints.gridy = 0;
		this.add(dateChooser, constraints);
		constraints.gridy = 1;
		this.add(heureDebutTextField, constraints);
		constraints.gridy = 2;
		this.add(heureFinTextField, constraints);
		constraints.gridy = 3;
		this.add(etatComboBox, constraints);
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

        /**
         * Méthode qui récupère l'utilisateur connecté
         * @return user
         */
	public User getUser() { return user; }
	
        /**
         * Méthode qui récupère le numéro de la semaine
         * @return Integer.parseInt( new SimpleDateFormat("w").format(dateChooser.getDate()))
         * @throws NumberFormatException
         * @throws ParseException 
         */
	public int getSemaine() throws NumberFormatException, ParseException {
		return Integer.parseInt( new SimpleDateFormat("w").format(dateChooser.getDate()));
	}

        /**
         * Méthode qui récupère la date
         * @return new Date(dateChooser.getDate().getTime())
         */
	public Date getDate() {
		return new Date(dateChooser.getDate().getTime());
	}

        /**
         * Méthode qui récupère l'heure de début
         * @return Time.valueOf(heureDebutTextField.getText()+(heureDebutTextField.getText().length()<6?":00":""))
         */
	public Time getHeureDebut() {
		System.out.println(heureDebutTextField.getText());
		return Time.valueOf(heureDebutTextField.getText()+(heureDebutTextField.getText().length()<6?":00":""));
	}
	
        /**
         * Méthode qui récupère l'heure de fin 
         * @return Time.valueOf(heureFinTextField.getText()+(heureFinTextField.getText().length()<6?":00":""))
         */
	public Time getHeureFin() {
		return Time.valueOf(heureFinTextField.getText()+(heureFinTextField.getText().length()<6?":00":""));
	}

        /**
         * Méthode qui récupère l'état de la séance
         * @return etatComboBox.getSelectedIndex()+1
         */
	public int getEtat() {
		return etatComboBox.getSelectedIndex()+1;
	}

        /**
         * Méthode qui récupère le cours
         * @return coursComboBox.getSelectedItem()
         */
	public Donnee getCours() {
		return (Donnee) coursComboBox.getSelectedItem();
	}
        
        /**
         * Méthode qui récupère le type de cours
         * @return typeDeCoursComboBox.getSelectedItem()
         */
	public Donnee getTypeDeCours() {
		return (Donnee) typeDeCoursComboBox.getSelectedItem();
	}

        /**
         * Méthode qui récupère les groupes concernés
         * @return groupesComboBoxList.getSelectedItems().stream().map(x -> (Groupe)x).collect(Collectors.toList())
         */
	public List<Groupe> getGroupes() {
		return groupesComboBoxList.getSelectedItems().stream().map(x -> (Groupe)x).collect(Collectors.toList());
	}
	
        /**
         * Méthode qui récupère l'enseignant
         * @return enseignantsComboBoxList.getSelectedItems().stream().map(x -> (Utilisateur)x).collect(Collectors.toList())
         */
	public List<Utilisateur> getEnseignants() {
		return enseignantsComboBoxList.getSelectedItems().stream().map(x -> (Utilisateur)x).collect(Collectors.toList());
	}
	
        /**
         * Méthode qui change la liste des enseignats pour la séance
         */
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
		
		List<Utilisateur> listUtilisateurs = user.ListeUtilisateurs().getEnseignantsByCours((Donnee)coursComboBox.getSelectedItem());
		if (listUtilisateurs.isEmpty())
			listUtilisateurs = user.ListeUtilisateurs().getReferents();
		enseignantsComboBoxList = new JEditableComboBoxList(listUtilisateurs.toArray(), "a professor");
		
		this.add(enseignantsComboBoxList, constraints);
		
		validate();
	}
	
        /**
         * Méthode qui récupère la salle 
         * @return sallesComboBoxList.getSelectedItems().stream().map(x -> (Salle)x).collect(Collectors.toList())
         */
	public List<Salle> getSalles() {
		return sallesComboBoxList.getSelectedItems().stream().map(x -> (Salle)x).collect(Collectors.toList());
	}

        /**
         * Méthode qui complète les zones de texte correpondant aux informations sur la séance
         * @param seance 
         */
	public void fillFields(Seance seance) {
		dateChooser.setDate(seance.getDate());
	    heureDebutTextField.setText(seance.getDebut().toString());
	    heureFinTextField.setText(seance.getFin().toString());
	    coursComboBox.setSelectedItem(seance.getCours());
	    typeDeCoursComboBox.setSelectedItem(seance.getType());
	    etatComboBox.setSelectedIndex(seance.getEtat()-1);
	    groupesComboBoxList.setSelectedItems(seance.getGroupes().toArray());
	    enseignantsComboBoxList.setSelectedItems(seance.getEnseignants().toArray());
	    sallesComboBoxList.setSelectedItems(seance.getSalles().toArray());
	}
}
