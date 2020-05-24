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
	
	public int getID() { return ID; }
	
	/**************
	 * PUBLIC METHODS
	 */
	
	public Donnee(String val) {
		value = val;
	}
	
	public String getValue() { return value; }

	public void setValue(String value) { this.value = value; }
	
	@Override
	public String toString() { return value; }
}
