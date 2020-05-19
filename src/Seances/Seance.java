package Seances;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Donnees.Donnee;

public class Seance {

	private int ID;
	private int semaine;
	private Date date;
	private Time debut;
	private Time fin;
	private int etat;
	private Donnee cours;
	private Donnee type;
	private List<Integer> IDs_Groupe = new ArrayList<Integer>();
	private List<Integer> IDs_Enseignant = new ArrayList<Integer>();
	private List<Integer> IDs_Salle = new ArrayList<Integer>();
	
	Seance(int _ID, int _semaine, Date _date, Time _debut, Time _fin, int _etat, Donnee _cours, Donnee _type)
	{
		ID = _ID;
		semaine = _semaine;
		date = _date;
		debut = _debut;
		fin =_fin;
		etat =_etat;
		cours = _cours;
		type =_type;
	}

	//public int getID() { return ID; }
	public int getSemaine() { return semaine; }
	public Date getDate() { return date; }
	public Time getDebut() { return debut; }
	public Time getFin() { return fin; }
	public int getEtat() { return etat; }
	public Donnee getCours() { return cours; }
	public Donnee getType() { return type; }
	
	void setCours(Donnee cours) { this.cours = cours; }
	void setType(Donnee type) { this.type = type; }
	
	/*
	public int getID_Groupe() { return ID_Groupe; }
	public int getID_Enseignant() { return ID_Enseignant; }
	public int getID_Salle() { return ID_Salle; }
	*/

	@Override
	public String toString() {
		return semaine + ", " + date + ", " + debut + ", " + fin + ", " + etat + ", " + cours + ", " + type;
	}

	
	
}
