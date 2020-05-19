package Groupes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Donnees.ListeDonnees;

public class ListeGroupesImpl implements ListeGroupes {
	private List<Groupe> list = new ArrayList<Groupe>();

	private Statement statement = null;
	
	public ListeGroupesImpl(Statement _statement, ListeDonnees promotions) throws SQLException
	{
		statement = _statement;
		
		ResultSet result = statement.executeQuery("Select * from groupe");
		
		while(result.next())
			list.add(new Groupe(result.getInt("ID"), result.getString("Nom"), promotions.GetByID(result.getInt("ID_Promotion"))));
	}
	
	@Override
	public List<Groupe> getAll() { return list; }
}
