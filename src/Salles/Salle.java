package Salles;

import Donnees.Donnee;

public class Salle {
	private int ID = 0;
	private String nom = "";
	private int capacite = 0;
	private Donnee site = null;
	
	public Salle(String _nom, int _capacite, Donnee _site) {
		nom = _nom;
		capacite = _capacite;
		site = _site;
	}
	
	Salle(int _ID, String _nom, int _capacite, Donnee _site) {
		ID = _ID;
		nom = _nom;
		capacite = _capacite;
		site = _site;
	}

	public int getID() { return ID; }
	public String getNom() { return nom; }
	public int getCapacite() { return capacite; }
	public Donnee getSite() { return site; }
	
	void setID(int ID) { this.ID = ID; }

	@Override
	public String toString() {
		return nom + " (" + capacite + "p, " + site + ")";
	}

	@Override
	public boolean equals(Object obj) 
	{
		try { if (((Salle) obj) == null) return false; }
		catch (Exception e) { return false; }
		
		Salle salle = (Salle) obj;
		
		return (nom.contentEquals(salle.getNom()) && capacite == salle.getCapacite() && site.equals(salle.getSite()));
	}

	public void copy(Salle salle) {
		nom = salle.getNom();
		capacite = salle.getCapacite();
		site = salle.getSite();
	}
}
