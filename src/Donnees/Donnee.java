package Donnees;

public class Donnee {
	private int ID = 0;
	private String value = "";
	
	/*****************
	 * PACKAGE RESTRICTED METHOD
	 */
	
	Donnee(int _ID, String val) {
		setValue(val);
		ID = _ID;
	}
	
	void setID(int ID) { this.ID = ID; }
	
	
	/**************
	 * PUBLIC METHODS
	 */
	
	public Donnee(String val) {
		value = val;
	}
	
	public int getID() { return ID; }
	public String getValue() { return value; }
	
	public void setValue(String value) { this.value = value; }
	
	@Override
	public String toString() { return value; }
	
	@Override
	public boolean equals(Object obj) {
		try { if (((Donnee) obj) == null) return false; }
		catch (Exception e) { return false; }
		
		Donnee donnee = (Donnee) obj;
		
		return (value.contentEquals(donnee.getValue()));
	}

	public void copy(Donnee donnee) {
		value = donnee.getValue();
	}
}
