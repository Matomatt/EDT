package Donnees;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Filters.Filter;
import Filters.Filter.Filters;

/**
 * Se connecte a la bdd pour gérer les promo, cours, sites, type_cours
 */
public class ListeDonneesImpl implements ListeDonnees {
	Connection connection = null;
	String tableName = "";
	
	/**
	 * Constructeur, recupère le nom de la table contenant les données dans la bdd
	 * @param conn
	 * @param _tableName
	 */
	public ListeDonneesImpl(Connection conn, String _tableName) {
		connection = conn;
		tableName = _tableName;
	}
	
	/**
	 * Execute la query indiquée et renvoie la List avec toutes les données trouvées
	 * @param query
	 * @return
	 */
	public List<Donnee> ExecuteQuery(String query)
	{
		List<Donnee> list = new ArrayList<Donnee>();
		ResultSet result;
		
		try 
		{
			result = connection.createStatement().executeQuery(query + " ORDER BY Nom ASC");
			
			while(result.next())
				list.add(new Donnee(result.getInt("ID"), result.getString("Nom")));
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public List<Donnee> getAll() {
		return ExecuteQuery("Select * from " + tableName);
	}
	
	@Override
	public List<Donnee> getFilteredBy(Filter[] filters) 
	{
		if (filters == null)
			return null;
		
		int nbFilterNoms = 0;
		String whereQuery = "";
		
		for (Filter filter : filters) {
			if (filter.Type() == Filters.Nom)
			{
				whereQuery += ((nbFilterNoms==0)?" Where ":" OR ") + "Nom='" + filter.Value().toString() + "'";
				nbFilterNoms ++;
			}
		}
		
		return ExecuteQuery("Select * from " + tableName + whereQuery);
	}
	
	@Override
	public void add(Donnee donnee) {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO `"+tableName+"` (`ID`, `Nom`) VALUES (NULL, '"+donnee.getValue()+"')", Statement.RETURN_GENERATED_KEYS);
			
			ResultSet keysResultSet = statement.getGeneratedKeys();
			if (keysResultSet != null && keysResultSet.next())
				donnee.setID((int) keysResultSet.getLong(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean Update(Donnee d) 
	{
		try {
			connection.createStatement().executeUpdate("Update " + tableName + " Set Nom='" + d.getValue() + "' Where ID=" + d.getID());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public void delete(Donnee donnee) {
		try {
			connection.createStatement().executeUpdate("DELETE FROM `promotion` WHERE `promotion`.`ID` = " + donnee.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	@Override
	public Donnee getByNom(String nom) {
		try {
			return ExecuteQuery("Select * from " + tableName + " Where Nom='"+nom+"'").get(0);
		}
		catch (Exception e) {
			return null;
		}
		
	}
}
