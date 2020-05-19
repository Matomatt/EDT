package Groupes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Donnees.ListeDonnees;

public class ListeGroupesImpl implements ListeGroupes {
	
	private Connection connection = null;
	private ListeDonnees promotions = null;
	
	public ListeGroupesImpl(Connection conn, ListeDonnees _promotions)
	{
		connection = conn;
		promotions = _promotions;
	}
	
	@Override
	public List<Groupe> getAll()
	{ 
		List<Groupe> list = new ArrayList<Groupe>();
		
		ResultSet result;
		try {
			result = connection.createStatement().executeQuery("Select * from groupe");
			
			while(result.next())
				list.add(new Groupe(result.getInt("ID"), result.getString("Nom"), promotions.GetByID(result.getInt("ID_Promotion"))));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return list; 
	}
}
