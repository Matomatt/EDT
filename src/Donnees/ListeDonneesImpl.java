package Donnees;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListeDonneesImpl implements ListeDonnees {
	Connection connection = null;
	String tableName = "";
	
	public ListeDonneesImpl(Connection conn, String _tableName) {
		connection = conn;
		tableName = _tableName;
	}
	
	@Override
	public List<Donnee> getAll() {
		List<Donnee> list = new ArrayList<Donnee>();
		ResultSet result;
		
		try 
		{
			result = connection.createStatement().executeQuery("Select * from " + tableName);
			
			while(result.next())
				list.add(new Donnee(result.getInt("ID"), result.getString("Nom")));
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
		
		return list;
	}

	@Override
	public boolean Update(Donnee d) {
		try {
			connection.createStatement().executeUpdate("Update " + tableName + " Set Nom='" + d.getValue() + "' Where ID=" + d.getID());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public Donnee GetByID(int ID) {
		Donnee donnee = null;
		ResultSet result;
		
		try 
		{
			result = connection.createStatement().executeQuery("Select * from " + tableName + " where ID=" + ID);
			
			if(result.next())
				donnee = new Donnee(result.getInt("ID"), result.getString("Nom"));
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
		
		return donnee;
	}

}
