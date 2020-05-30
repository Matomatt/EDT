package UI_Elements;

import java.text.SimpleDateFormat;
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
	
	private String nameString = "";
	private Class<? extends Object> objectClass = null;
	private UserType userType = UserType.none;
	
	public JScrollListe(ListeSeances listeSeances) {
		List<Seance> listSeances = listeSeances.getByWeek(Integer.parseInt(new SimpleDateFormat("w").format(new java.util.Date())));
		originalList = listSeances.stream().map(x -> (Object)x).collect(Collectors.toList());
		Init();
    }
	
	public JScrollListe(ListeUtilisateurs listeUtilisateurs, UserType userType) 
	{
		this.userType = userType;
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
		nameString = string;
		List<Donnee> listDonnees = listeDonnees.getAll();
		originalList = listDonnees.stream().map(x -> (Object)x).collect(Collectors.toList());
		Init();	
	}
	
	private void Init()
	{
		if (originalList.size()>0)
			objectClass = originalList.get(0).getClass();
		Filter("");
		list.setModel(model);
		this.setViewportView(list);
		this.setBackground(new java.awt.Color(255, 255, 255));
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

	public void changeWeek(ListeSeances listeSeances ,Integer value) 
	{
		if (objectClass == Seance.class)
		{
			originalList.removeAll(originalList);
			model.removeAllElements();
			List<Seance> listSeances = listeSeances.getByWeek(value);
			originalList = listSeances.stream().map(x -> (Object)x).collect(Collectors.toList());
			Init();
			validate();
			repaint();
		}
	}

	public Class<? extends Object> getObjectClass() { return objectClass; }
	
	public UserType getUserType() { return userType; }
	
	public String getNameString() { return nameString; }
	
	public List<Object> getSelectedValues() { return list.getSelectedValuesList(); }

	public void Delete(Object o) {
		System.out.println(o.toString() + " deleted !");
		if (originalList.contains(o))
			originalList.remove(o);
		if(model.contains(o))
			model.removeElement(o);
		
		this.revalidate();
	}
	
	public void addObject(Object o) {
		if (!originalList.contains(o))
			originalList.add(o);
		if(!model.contains(o))
			model.addElement(o);
		
		this.revalidate();
	}
	
	public void validateCustom()
	{
		this.revalidate();
		list.revalidate();
		list.repaint();
		this.repaint();
	}
}
