package Salles;

import Donnees.Donnee;

public class Salle {
	private int ID = 0;
	private String nom = "";
	private int capacite = 0;
	private Donnee site = null;
	
	public Salle(String _nom, int _capacite, Donnee _site) {
		setNom(_nom);
		setCapacite(_capacite);
		setSite(_site);
	}
	
	Salle(int _ID, String _nom, int _capacite, Donnee _site) {
		ID = _ID;
		setNom(_nom);
		setCapacite(_capacite);
		setSite(_site);
	}

	public int getID() { return ID; }
	public String getNom() { return nom; }
	public int getCapacite() { return capacite; }
	public Donnee getSite() { return site; }
	
	public void setNom(String nom) { this.nom = nom; }
	public void setCapacite(int capacite) { this.capacite = capacite; }
	public void setSite(Donnee site) { this.site = site; }

	@Override
	public String toString() {
		return nom + " (" + capacite + "p, " + site + ")";
	}
		
}
