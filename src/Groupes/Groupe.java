package Groupes;

import Donnees.Donnee;

public class Groupe {
	private int ID = 0;
	private String name = "";
	private Donnee promotion = null;
	
	Groupe(int _ID, String _name, Donnee _promotion) {
		ID = _ID;
		setName(_name);
		setPromotion(_promotion);
	}

	public Groupe(String _name, Donnee _promotion) {
		setName(_name);
		setPromotion(_promotion);
	}
	
	public String getName() { return name; }
	public Donnee getPromotion() { return promotion; }

	public void setName(String name) { this.name = name; }
	public void setPromotion(Donnee promotion) { this.promotion = promotion; }
	
	@Override
	public String toString() {
		return name + " promo " + promotion;
	}

}
