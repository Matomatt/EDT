package Salles;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Donnees.ListeDonnees;

public class ListeSallesImpl implements ListeSalles
{
	Connection connection = null;
	ListeDonnees sites = null;
	
	public ListeSallesImpl(Connection conn, ListeDonnees _sites) {
		connection = conn;
		sites = _sites;
	}
	
	@Override
	public List<Salle> getAll()
	{
		List<Salle> list = new ArrayList<Salle>();
		
		try {
			ResultSet result = connection.createStatement().executeQuery("Select * from salle");
			
			while(result.next())
				list.add(new Salle(result.getInt("ID"), result.getString("Nom"), result.getInt("Capacite"), sites.GetByID(result.getInt("ID_Site")) ));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
				
		return list;
	}
	
}
