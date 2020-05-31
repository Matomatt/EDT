package Groupes;

import Donnees.Donnee;

public class Groupe {
	private int ID = 0;
	private String name = "";
	private Donnee promotion = null;
	
	Groupe(int _ID, String _name, Donnee _promotion) {
		ID = _ID;
		name = _name;
		promotion = _promotion;
	}

	public Groupe(String _name, Donnee _promotion) {
		name = _name;
		promotion = _promotion;
	}
	
	public int getID() { return ID; }
	public String getName() { return name; }
	public Donnee getPromotion() { return promotion; }

	void setID(int ID) { this.ID = ID; }
	
	@Override
	public String toString() {
		return name + " promo " + promotion;
	}
	
	@Override
	public boolean equals(Object obj) {
		try { if (((Groupe) obj) == null) return false; }
		catch (Exception e) { return false; }
		
		Groupe gr = (Groupe) obj;
		
		return (name.contentEquals(gr.getName()) && promotion.equals(gr.getPromotion()));
	}

	public void copy(Groupe groupe) {
		name = groupe.getName();
		promotion = groupe.getPromotion();
	}
}
