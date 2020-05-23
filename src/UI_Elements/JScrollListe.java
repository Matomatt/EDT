package UI_Elements;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import Donnees.Donnee;
import Donnees.ListeDonnees;
import Groupes.Groupe;
import Groupes.ListeGroupes;
import Salles.ListeSalles;
import Salles.Salle;
import Seances.ListeSeances;
import Seances.Seance;
import Utilisateurs.ListeUtilisateurs;
import Utilisateurs.Utilisateur;
import Utilisateurs.User.UserType;

public class JScrollListe extends JScrollPane
{
	private static final long serialVersionUID = -2054706871757546641L;
	
	final DefaultListModel<String> listModel = new DefaultListModel<String>();
	final DefaultListModel<String> filteredListModel = new DefaultListModel<String>();
	private JList<String> listString = new javax.swing.JList<>();
	private List<Object> listObjects = new ArrayList<Object>();
	
	public JScrollListe(ListeSeances listeSeances) 
	{
		for (Seance seance : listeSeances.getAll()) {
			listModel.addElement(seance.toString());
			filteredListModel.addElement(seance.toString());
			listObjects.add(seance);
		}
		Init();
    }
	
	public JScrollListe(ListeUtilisateurs listeUtilisateurs, UserType userType) 
	{
		List<Utilisateur> listUtilisateurs; 
		switch (userType) {
			case Referent_pedagogique: listUtilisateurs = listeUtilisateurs.getReferents(); break;
				
			case Enseignant: listUtilisateurs = listeUtilisateurs.getEnseignants(); break;
				
			case Etudiant: listUtilisateurs = listeUtilisateurs.getEtudiants(); break;
	
			default: listUtilisateurs = listeUtilisateurs.getAll(); break;
		}
		
		for (Utilisateur utilisateur : listUtilisateurs) {
			listModel.addElement(utilisateur.toString());
			filteredListModel.addElement(utilisateur.toString());
			listObjects.add(utilisateur);
		}
		Init();
	}

	public JScrollListe(ListeSalles listeSalles) {
		for (Salle salle : listeSalles.getAll()) {
			listModel.addElement(salle.toString());
			filteredListModel.addElement(salle.toString());
			listObjects.add(salle);
		}
		Init();	}

	public JScrollListe(ListeGroupes listeGroupes) {
		for (Groupe groupe : listeGroupes.getAll()) {
			listModel.addElement(groupe.toString());
			filteredListModel.addElement(groupe.toString());
			listObjects.add(groupe);
		}
		Init();	}

	public JScrollListe(ListeDonnees listeDonnees, String string) {
		for (Donnee donnee : listeDonnees.getAll()) {
			listModel.addElement(donnee.toString());
			filteredListModel.addElement(donnee.toString());
			listObjects.add(donnee);
		}
		Init();
	}

	private void Init()
	{
		listString.setModel(filteredListModel);
        this.setViewportView(listString);
	}
	
	public List<Object> getSelectedObjects()
	{
		return null;
	}
}
