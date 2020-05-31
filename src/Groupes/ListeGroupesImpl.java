package Groupes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Donnees.Donnee;
import Donnees.ListeDonnees;
import Filters.Filter;
import Filters.Filter.Filters;
import Seances.Seance;

public class ListeGroupesImpl implements ListeGroupes {
	
	private Connection connection = null;
	private ListeDonnees promotions = null;
	
	public ListeGroupesImpl(Connection conn, ListeDonnees _promotions)
	{
		connection = conn;
		promotions = _promotions;
	}
	
	private List<Groupe> ExecuteQuery(String query)
	{
		List<Groupe> list = new ArrayList<Groupe>();
		
		ResultSet result;
		try {
			result = connection.createStatement().executeQuery(query);
			
			while(result.next())
				list.add(new Groupe(result.getInt("ID"), result.getString("Nom"), promotions.GetByID(result.getInt("ID_Promotion"))));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list; 
	}
	
	@Override
	public List<Groupe> getAll()
	{ 
		return ExecuteQuery("Select * from groupe");
	}
	
	@Override
	public List<Groupe> getByPromotion(Donnee promotion) {
		return ExecuteQuery("Select * from groupe Where ID_Promotion="+promotion.getID());
	}

	@Override
	public List<Groupe> getFilteredBy(Filter[] filters)
	{
		if (filters == null)
			return null;
		
		int nbFilterNoms = 0;
		String whereQueryNom = "";
		int nbFilterPromo = 0;
		String whereQueryPromo = "";
		
		for (Filter filter : filters) {
			if (filter.Type() == Filters.Nom)
			{
				whereQueryNom += ((nbFilterNoms==0)?"":" OR ") + "Nom='" + filter.Value().toString() + "'";
				nbFilterNoms ++;
			}
			if (filter.Type() == Filters.Promotion)
			{
				whereQueryPromo += ((nbFilterPromo==0)?"":" OR ") + "ID_Promotion=" + ((Donnee)filter.Value()).getID();
				nbFilterPromo ++;
			}
		}
		whereQueryNom = "("+whereQueryNom+")";
		whereQueryPromo = "("+whereQueryPromo+")";
		String whereQuery = ((whereQueryNom.isEmpty() && whereQueryPromo.isEmpty())?"":" Where ") + (whereQueryNom.isEmpty()?"":whereQueryNom+(whereQueryPromo.isEmpty()?"":" AND ")) + (whereQueryPromo.isEmpty()?"":whereQueryPromo);

		//System.out.println(whereQuery);
		return ExecuteQuery("Select * from groupe" + whereQuery);
	}

	@Override
	public Groupe getByID(int ID) {
		Groupe groupe = null;
		ResultSet result;
		
		try 
		{
			result = connection.createStatement().executeQuery("Select * from groupe where ID=" + ID);
			
			if(result.next())
				groupe = new Groupe(result.getInt("ID"), result.getString("Nom"), promotions.GetByID(result.getInt("ID_Promotion")));
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
		
		return groupe;
	}

	@Override
	public List<Groupe> getBySeance(Seance seance) {
		return getBySeanceID(seance.getID());
	}

	@Override
	public List<Groupe> getBySeanceID(int ID) {
		return ExecuteQuery("Select * From groupe Where ID IN (Select ID_Groupe From seance_groupes Where ID_Seance="+ID+")");
	}

	@Override
	public void add(Groupe groupe) {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO `groupe` (`ID`, `Nom`, `ID_Promotion`) VALUES (NULL, '"+groupe.getName()+"', '"+groupe.getPromotion().getID()+"');", Statement.RETURN_GENERATED_KEYS);

			ResultSet keysResultSet = statement.getGeneratedKeys();
			if (keysResultSet != null && keysResultSet.next())
				groupe.setID((int) keysResultSet.getLong(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean Update(Groupe groupe) 
	{
		try {
			connection.createStatement().executeUpdate("Update groupe Set Nom='" + groupe.getName() + "', ID_Promotion="+ groupe.getPromotion().getID() +" Where ID=" + groupe.getID());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public void delete(Groupe groupe) {
		try {
			connection.createStatement().executeUpdate("DELETE FROM `groupe` WHERE `groupe`.`ID` = " + groupe.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
