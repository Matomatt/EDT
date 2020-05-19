package Seances;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Donnees.ListeDonnees;

public class ListeSeancesImpl implements ListeSeances {
	
	private Connection connection = null;
	ListeDonnees cours;
	ListeDonnees type_cours;
	
	public ListeSeancesImpl(Connection conn, ListeDonnees _cours, ListeDonnees _type_cours)
	{
		connection = conn;
		cours = _cours;
		type_cours = _type_cours;
	}
	
	@Override
	public List<Seance> getAll() {
		List<Seance> list = new ArrayList<Seance>();
		
		ResultSet result;
		try {
			result = connection.createStatement().executeQuery("Select * from seance");
			
			while(result.next())
				list.add(new Seance(result.getInt("ID"), result.getInt("Semaine"), result.getDate("Date"), result.getTime("Heure_Debut"), result.getTime("Heure_Fin"), result.getInt("Etat"), cours.GetByID(result.getInt("ID_Cours")), type_cours.GetByID(result.getInt("ID_Type"))));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return list;
	}
}
