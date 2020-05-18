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
	private int ID_Cours;
	private Donnee cours;
	private int ID_Type;
	private Donnee type;
	private List<Integer> IDs_Groupe = new ArrayList<Integer>();
	private List<Integer> IDs_Enseignant = new ArrayList<Integer>();
	private List<Integer> IDs_Salle = new ArrayList<Integer>();
	
	Seance(int _ID, int _semaine, Date _date, Time _debut, Time _fin, int _etat, int _ID_Cours, int _ID_Type)
	{
		ID = _ID;
		semaine = _semaine;
		date = _date;
		debut = _debut;
		fin =_fin;
		etat =_etat;
		ID_Cours = _ID_Cours;
		ID_Type =_ID_Type;
	}

	//public int getID() { return ID; }
	public int getSemaine() { return semaine; }
	public Date getDate() { return date; }
	public Time getDebut() { return debut; }
	public Time getFin() { return fin; }
	public int getEtat() { return etat; }
	int getID_Cours() { return ID_Cours; }
	public Donnee getCours() { return cours; }
	int getID_Type() { return ID_Type; }
	public Donnee getType() { return type; }
	
	void setCours(Donnee cours) { this.cours = cours; }
	void setType(Donnee type) { this.type = type; }
	
	/*
	public int getID_Groupe() { return ID_Groupe; }
	public int getID_Enseignant() { return ID_Enseignant; }
	public int getID_Salle() { return ID_Salle; }
	*/

	

	
	
}
