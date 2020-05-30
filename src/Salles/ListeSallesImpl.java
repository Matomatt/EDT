package Salles;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Donnees.ListeDonnees;
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

	@Override
	public void add(Salle salle) {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO `salle` (`ID`, `Nom`, `Capacite`, `ID_Site`) VALUES (NULL, '"+salle.getNom()+"', '"+salle.getCapacite()+"', '"+salle.getSite().getID()+"');", Statement.RETURN_GENERATED_KEYS);

			ResultSet keysResultSet = statement.getGeneratedKeys();
			if (keysResultSet != null && keysResultSet.next())
				salle.setID((int) keysResultSet.getLong(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Salle salle) {
		try {
			connection.createStatement().executeUpdate("UPDATE `salle` SET `Nom` = '"+salle.getNom()+"', `Capacite` = '"+salle.getCapacite()+"', `ID_Site` = '"+salle.getSite().getID()+"' WHERE `salle`.`ID` = " + salle.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Salle salle) {
		try {
			connection.createStatement().executeUpdate("DELETE FROM `salle` WHERE `salle`.`ID` = " + salle.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
