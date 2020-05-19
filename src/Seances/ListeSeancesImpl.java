package Seances;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Donnees.ListeDonnees;

public class ListeSeancesImpl implements ListeSeances {
	private List<Seance> list = new ArrayList<Seance>();
	
	private Statement statement = null;
	
	public ListeSeancesImpl(Statement _statement, ListeDonnees cours, ListeDonnees type_cours) throws SQLException
	{
		statement = _statement;
		
		ResultSet result = statement.executeQuery("Select * from seance");
		
		while(result.next())
			list.add(new Seance(result.getInt("ID"), result.getInt("Semaine"), result.getDate("Date"), result.getTime("Heure_Debut"), result.getTime("Heure_Fin"), result.getInt("Etat"), cours.GetByID(result.getInt("ID_Cours")), type_cours.GetByID(result.getInt("ID_Type"))));
	}
	
	@Override
	public List<Seance> getAll() {
		return list;
	}
}
