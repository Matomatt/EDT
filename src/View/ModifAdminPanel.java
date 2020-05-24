package View;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import UI_Elements.Button;
import UI_Elements.JScrollListe;
import Utilisateurs.User;
import Utilisateurs.User.UserType;

public class ModifAdminPanel extends JPanel 
{
	
	private static final long serialVersionUID = 3736956335101565252L;
	User user = null;

	public ModifAdminPanel(User _user) {
		user = _user;
		this.setLayout(new GridBagLayout());

        initComponents();
        
        this.setVisible(true);
        
        validate();
    }
	
	private void initComponents() 
    {
    	JTabbedPane tabbedPanes = new JTabbedPane();
    	
        tabbedPanes.addTab("Séances", new JScrollListe(user.ListeSeances()));

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
        Button bAjouter = new Button(" Ajouter ");
        Button bSupprimer = new Button(" Supprimer ");
        Button bModifier = new Button(" Modifier ");
        JTextField textField = new JTextField();
        Button bRechercher = new Button(" Rechercher ");
        bRechercher.setFont(new Font(bRechercher.getFont().getFontName(), Font.PLAIN, (int) (bRechercher.getFont().getSize()/1.5)));
        
        textField.getDocument().addDocumentListener(new DocumentListener(){
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void changedUpdate(DocumentEvent e) {}
            private void filter() {
                String filter = textField.getText();
                ((JScrollListe) tabbedPanes.getSelectedComponent()).Filter(filter);
            }
        });
        
        toolBar.setRollover(false);
        toolBar.setFloatable(false);
        toolBar.setBackground(new java.awt.Color(255, 255, 255));
        toolBar.add(bAjouter);
        toolBar.add(bSupprimer);
        toolBar.add(bModifier);
        toolBar.add(textField);
        toolBar.add(bRechercher);

        c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 0;
        this.add(toolBar, c);

        validate();
    }
}

