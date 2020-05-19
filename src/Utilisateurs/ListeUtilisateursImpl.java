package Utilisateurs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Seances.Seance;

public class ListeUtilisateursImpl implements ListeUtilisateurs {
	private List<Utilisateur> list = new ArrayList<Utilisateur>();
	
	Statement statement = null;
	
	public ListeUtilisateursImpl(Statement _statement) throws SQLException
	{
		statement = _statement;
		
		ResultSet result = statement.executeQuery("Select * from utilisateur");
		
		while(result.next())
			list.add(new Utilisateur(result.getInt("ID"), result.getString("Email"), result.getString("Nom"), result.getString("Prenom"), result.getInt("Droit")));

	}
	@Override
	public List<Utilisateur> getAll() { return list; }

}
