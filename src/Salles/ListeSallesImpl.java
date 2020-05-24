package Salles;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import Donnees.ListeDonnees;
import Seances.ListeSeances;
import Seances.Seance;

public class ListeSallesImpl implements ListeSalles
{
	Connection connection = null;
	ListeDonnees sites = null;
	
	public ListeSallesImpl(Connection conn, ListeDonnees _sites) {
		connection = conn;
		sites = _sites;
	}
	
	private List<Salle> ExecuteQuery(String query)
	{
		List<Salle> list = new ArrayList<Salle>();
		
		try {
			ResultSet result = connection.createStatement().executeQuery(query);
			
			while(result.next())
				list.add(new Salle(result.getInt("ID"), result.getString("Nom"), result.getInt("Capacite"), sites.GetByID(result.getInt("ID_Site")) ));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
				
		return list;
	}
	@Override
	public List<Salle> getAll()
	{
		return ExecuteQuery("Select * from salle");
	}

	@Override
	public List<Salle> getBySeance(Seance seance) {
		return getBySeanceID(seance.getID());
	}

	@Override
	public List<Salle> getBySeanceID(int ID) {
		return ExecuteQuery("Select * From salle Where ID IN (Select ID_Salle From seance_salles Where ID_Seance="+ID+")");
	}
	
}
