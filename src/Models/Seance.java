package Models;

import java.sql.Time;
import java.util.Date;

public class Seance {

	private int ID;
	private int semaine;
	private Date date;
	private Time debut;
	private Time fin;
	private int etat;
	private int ID_Cours;
	private int ID_Type;
	private int ID_Groupe;
	private int ID_Enseignant;
	private int ID_Salle;
	
	public Seance(int _ID, int _semaine, Date _date, Time _debut, Time _fin, int _etat, int _ID_Cours, int _ID_Type, int _ID_Groupe, int _ID_Enseignant, int _ID_Salle)
	{
		ID = _ID;
		semaine = _semaine;
		date = _date;
		debut = _debut;
		fin =_fin;
		etat =_etat;
		ID_Cours = _ID_Cours;
		ID_Type =_ID_Type;
		ID_Groupe =_ID_Groupe;
		ID_Enseignant = _ID_Enseignant;
		ID_Salle =_ID_Salle;
	}

	public int getID() { return ID; }
	public int getSemaine() { return semaine; }
	public Date getDate() { return date; }
	public Time getDebut() { return debut; }
	public Time getFin() { return fin; }
	public int getEtat() { return etat; }
	public int getID_Cours() { return ID_Cours; }
	public int getID_Type() { return ID_Type; }
	public int getID_Groupe() { return ID_Groupe; }
	public int getID_Enseignant() { return ID_Enseignant; }
	public int getID_Salle() { return ID_Salle; }
	
	
}
