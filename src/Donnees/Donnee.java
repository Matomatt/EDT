package Donnees;

import java.sql.SQLException;
import java.sql.Statement;

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

	void Update(String table, Statement statement) throws SQLException
	{
		statement.executeUpdate("Update " + table + " Set Nom='" + value + "' Where ID=" + ID);
	}
	
	int getID() { return ID; }
	
	/**************
	 * PUBLIC METHODS
	 */
	
	public String getValue() { return value; }

	public void setValue(String value) { this.value = value; }
	
	@Override
	public String toString() { return value; }
}
