package UI_Elements;

import java.util.List;
import java.util.stream.Collectors;

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
	
	private JList<Object> list = new JList<Object>();
	private List<Object> originalList;
	private DefaultListModel<Object> model = new DefaultListModel<Object>();
	
	public JScrollListe(ListeSeances listeSeances) {
		List<Seance> listSeances = listeSeances.getByWeek(25);
		originalList = listSeances.stream().map(x -> (Object)x).collect(Collectors.toList());
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
		
		originalList = listUtilisateurs.stream().map(x -> (Object)x).collect(Collectors.toList());
		Init();	
	}

	public JScrollListe(ListeSalles listeSalles) {
		List<Salle> listSalles = listeSalles.getAll();
		originalList = listSalles.stream().map(x -> (Object)x).collect(Collectors.toList());
		Init();	
	}

	public JScrollListe(ListeGroupes listeGroupes) {
		List<Groupe> listGroupes = listeGroupes.getAll();
		originalList = listGroupes.stream().map(x -> (Object)x).collect(Collectors.toList());
		Init();	
	}

	public JScrollListe(ListeDonnees listeDonnees, String string) {
		List<Donnee> listDonnees = listeDonnees.getAll();
		originalList = listDonnees.stream().map(x -> (Object)x).collect(Collectors.toList());
		Init();	
	}
	
	private void Init()
	{
		Filter("");
		list.setModel(model);
		this.setViewportView(list);
	}
	
	public void Filter(String filter)
	{
		for (Object object : originalList) {
			
			if (object.toString().contains(filter))
			{
				if (!model.contains(object))
					model.addElement(object);
			}
			else {
				if (model.contains(object))
					model.removeElement(object);
			}
		}
		
		this.revalidate();
	}
	
}
