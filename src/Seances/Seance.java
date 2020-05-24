package Seances;

import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import Donnees.Donnee;
import Groupes.Groupe;
import Salles.Salle;
import Utilisateurs.Utilisateur;

public class Seance {

	private int ID;
	private int semaine;
	private Date date;
	private Time debut;
	private Time fin;
	private int etat;
	private Donnee cours;
	private Donnee type;
	private List<Groupe> groupes = new ArrayList<Groupe>();
	private List<Utilisateur> enseignants = new ArrayList<Utilisateur>();
	private List<Salle> salles = new ArrayList<Salle>();
	
	Seance(int _ID, int _semaine, Date _date, Time _debut, Time _fin, int _etat, Donnee _cours, Donnee _type, List<Groupe> _groupes, List<Utilisateur> _enseignants, List<Salle> _salles)
	{
		ID = _ID;
		semaine = _semaine;
		date = _date;
		debut = _debut;
		fin =_fin;
		etat =_etat;
		cours = _cours;
		type =_type;
		groupes = _groupes;
		enseignants = _enseignants;
		salles = _salles;
	}
	
	public Seance(int _semaine, Date _date, Time _debut, Time _fin, int _etat, Donnee _cours, Donnee _type)
	{
		ID = 0;
		semaine = _semaine;
		date = _date;
		debut = _debut;
		fin =_fin;
		etat =_etat;
		cours = _cours;
		type =_type;
	}
	
	public Seance(int _semaine, Date _date, Time _debut, Time _fin, int _etat, Donnee _cours, Donnee _type, Groupe groupe, Utilisateur enseignant, Salle salle)
	{
		ID = 0;
		semaine = _semaine;
		date = _date;
		debut = _debut;
		fin =_fin;
		etat =_etat;
		cours = _cours;
		type =_type;
		
		groupes.add(groupe);
		enseignants.add(enseignant);
		salles.add(salle);
	}

	public Seance(int _semaine, Date _date, Time _debut, Time _fin, int _etat, Donnee _cours, Donnee _type, List<Groupe> _groupes, List<Utilisateur> _enseignants, List<Salle> _salles) 
	{
		ID = 0;
		semaine = _semaine;
		date = _date;
		debut = _debut;
		fin = _fin;
		etat = _etat;
		cours = _cours;
		type =_type;
		
		groupes = _groupes;
		enseignants = _enseignants;
		salles = _salles;
	}

	public int getID() { return ID; }
	public int getSemaine() { return semaine; }
	public Date getDate() { return date; }
	public Time getDebut() { return debut; }
	public Time getFin() { return fin; }
	public int getEtat() { return etat; }
	public Donnee getCours() { return cours; }
	public Donnee getType() { return type; }
	public List<Groupe> getGroupes() { return groupes; }
	public List<Utilisateur> getEnseignants() { return enseignants; }
	public List<Salle> getSalles() { return salles; }
	
	void setID(long newID) { ID = (int) newID; }
	void setCours(Donnee cours) { this.cours = cours; }
	void setType(Donnee type) { this.type = type; }
	

	@Override
	public String toString() {
		String toReturnString = semaine + ", " + date + ", " + debut + ", " + fin + ", " + etat + ", " + cours + ", " + type + " avec ";
		for (Groupe groupe : groupes) {
			toReturnString+=groupe+" ";
		}
		toReturnString+=" par ";
		for (Utilisateur enseignant : enseignants) {
			toReturnString+=enseignant+" ";
		}
		toReturnString+=" en ";
		for (Salle salle : salles) {
			toReturnString+=salle+" ";
		}
		return toReturnString;
	}
}
