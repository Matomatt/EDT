package Donnees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListeDonneesImpl implements ListeDonnees {
	Statement statement = null;
	List<Donnee> list = new ArrayList<Donnee>();
	String tableName = "";
	
	public ListeDonneesImpl(Statement _statement, String _tableName) throws SQLException {
		statement = _statement;
		tableName = _tableName;
		
		ResultSet result = statement.executeQuery("Select * from " + tableName);
		
		while(result.next())
			list.add(new Donnee(result.getInt("ID"), result.getString("Nom")));
	}
	
	@Override
	public List<Donnee> getAll() {
		return list;
	}

	@Override
	public void Update(Donnee d) throws SQLException {
		d.Update(tableName, statement);
	}

	@Override
	public Donnee GetByID(int ID) {
		for (Donnee donnee : list) {
			if (donnee.getID() == ID)
				return donnee;
		}
		return null;
	}

}
