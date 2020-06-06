package Donnees;

public class Donnee {
	private int ID = 0;
	private String value = "";
	
	/*****************
	 * PACKAGE RESTRICTED METHOD
	 */
	
	/**
	 * Constructeur dédié a l'implémentation DAO
	 * @param _ID
	 * @param val
	 */
	Donnee(int _ID, String val) {
		setValue(val);
		ID = _ID;
	}
	
	void setID(int ID) { this.ID = ID; }
	
	
	/**************
	 * PUBLIC METHODS
	 */
	
	/**
	 * Constructeur public
	 * @param val
	 */
	public Donnee(String val) {
		value = val;
	}
	
	public int getID() { return ID; }
	public String getValue() { return value; }
	
	public void setValue(String value) { this.value = value; }
	
	@Override
	public String toString() { return value; }
	
	/**
	 * Check si tous les attributs (sauf l'id) sont égaux
	 */
	@Override
	public boolean equals(Object obj) {
		try { if (((Donnee) obj) == null) return false; }
		catch (Exception e) { return false; }
		
		Donnee donnee = (Donnee) obj;
		
		return (value.contentEquals(donnee.getValue()));
	}

	/**
	 * Copie les attributs de la donnée indiquée
	 * @param donnee
	 */
	public void copy(Donnee donnee) {
		value = donnee.getValue();
	}

	public boolean isOfID(int ID) {
		return (this.ID == ID);
	}
}
