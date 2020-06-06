package View;

import java.awt.GridBagConstraints;
import java.text.SimpleDateFormat;

import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Controllers.Controller;
import UI_Elements.Button;
import UI_Elements.JScrollListe;
import Utilisateurs.User;
import Utilisateurs.User.UserType;

/**
 * @author BOCHER, CADOT et GAUTIER 
 * classe qui hérite de Panel et qui créer la fenêtre de modification de l'administrateur 
 */
public class ModifAdminPanel extends Panel
{
        /**
        * serialVersionUID : clé de hachage de la classe
        * tabbedPanes : création d'une JTabbedPane
        */
	private static final long serialVersionUID = 3376270138742171143L;
	
	JTabbedPane tabbedPanes = new JTabbedPane();
	
        /**
        * Constructeur     
        * @param user
        * @param controller
        */
	public ModifAdminPanel(User user, Controller controller) 
	{
		super(user, controller);
		
        initComponents();
	}

        /**
        * Méthode qui initialise le contenu de la fenêtre de modifier     
        */
	private void initComponents() 
    {
		tabbedPanes.setBackground(new java.awt.Color(255, 255, 255));
		
    	JScrollListe seancesScrollListe = new JScrollListe(user.ListeSeances());
        tabbedPanes.addTab("Séances", seancesScrollListe);

        tabbedPanes.addTab("Référants", new JScrollListe(user.ListeUtilisateurs(), UserType.Referent_pedagogique));

        tabbedPanes.addTab("Enseignants", new JScrollListe(user.ListeUtilisateurs(), UserType.Enseignant));

        tabbedPanes.addTab("Etudiants", new JScrollListe(user.ListeUtilisateurs(), UserType.Etudiant));

        tabbedPanes.addTab("Salles", new JScrollListe(user.ListeSalles()));

        tabbedPanes.addTab("Groupes", new JScrollListe(user.ListeGroupes()));

        tabbedPanes.addTab("Promotions", new JScrollListe(user.ListePromotion(), "promotion"));

        tabbedPanes.addTab("Cours", new JScrollListe(user.ListeCours(), "cours"));

        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 1;
		
        this.add(tabbedPanes, c);
        

        JToolBar toolBar  = new JToolBar();
        
        Button bAjouter = new Button("btAjouter", " Ajouter ", controller);
        Button bSupprimer = new Button("btSupprimer", " Supprimer ", controller);
        Button bModifier = new Button("btModifier", " Modifier ", controller);
        
        JTextField textField = new JTextField();
        textField.setText("Rechercher");
        textField.getDocument().addDocumentListener(new DocumentListener(){
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void changedUpdate(DocumentEvent e) {}
            private void filter() {
                String filter = textField.getText();
                ((JScrollListe) tabbedPanes.getSelectedComponent()).Filter(filter);
            }
        });
        
        JSpinner chosenWeek = new JSpinner();
        chosenWeek.setFont(bAjouter.getFont());
        chosenWeek.setValue(Integer.parseInt( new SimpleDateFormat("w").format(new java.util.Date()) ));
        chosenWeek.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner s = (JSpinner) e.getSource();
                seancesScrollListe.changeWeek(user.ListeSeances(), (Integer) s.getValue());
            }
        });
        
        toolBar.setRollover(false);
        toolBar.setFloatable(false);
        toolBar.setBackground(new java.awt.Color(255, 255, 255));
        toolBar.add(bAjouter);
        toolBar.add(bSupprimer);
        toolBar.add(bModifier);
        toolBar.add(textField);
        toolBar.add(chosenWeek);

        c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 0;
        this.add(toolBar, c);

        validate();
    }
	
        /**
        * Méthode qui récupère la fanêtre tabbedPanes
        * @return tabbedPanes
        */
	public JTabbedPane getTabbedPanes() { return tabbedPanes; }
}

