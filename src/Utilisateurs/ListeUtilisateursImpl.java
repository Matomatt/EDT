package Utilisateurs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListeUtilisateursImpl implements ListeUtilisateurs {
	Connection connection = null;
	
	public ListeUtilisateursImpl(Connection conn)
	{
		connection = conn;
	}
	
	@Override
	public List<Utilisateur> getAll()
	{
		List<Utilisateur> list = new ArrayList<Utilisateur>();
		
		ResultSet result;
		try {
			result = connection.createStatement().executeQuery("Select * from utilisateur");
			
			while(result.next())
				list.add(new Utilisateur(result.getInt("ID"), result.getString("Email"), result.getString("Nom"), result.getString("Prenom"), result.getInt("Droit")));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return list;
	}

}
