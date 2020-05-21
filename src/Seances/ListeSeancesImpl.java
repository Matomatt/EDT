package Seances;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import Donnees.ListeDonnees;
import Groupes.Groupe;
import Salles.ListeSalles;
import Salles.Salle;
import Utilisateurs.Utilisateur;

public class ListeSeancesImpl implements ListeSeances {
	
	private Connection connection = null;
	private ListeDonnees cours = null;
	private ListeDonnees type_cours = null;
	private ListeSalles salles = null;
	
	public ListeSeancesImpl(Connection conn, ListeDonnees _cours, ListeDonnees _type_cours, ListeSalles _salles)
	{
		connection = conn;
		cours = _cours;
		type_cours = _type_cours;
		salles = _salles;
	}
	
	private List<Seance> ExecuteQuery(String query)
	{
		List<Seance> list = new ArrayList<Seance>();
		
		ResultSet result;
		try {
			result = connection.createStatement().executeQuery(query);
			
			while(result.next())
				list.add(new Seance(result.getInt("ID"), result.getInt("Semaine"), result.getDate("Date"), result.getTime("Heure_Debut"), result.getTime("Heure_Fin"), result.getInt("Etat"), cours.GetByID(result.getInt("ID_Cours")), type_cours.GetByID(result.getInt("ID_Type"))));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return list;
	}
	
	@Override
	public List<Seance> getAll() {
		return ExecuteQuery("Select * From seance");
	}

	@Override
	public List<Seance> getBySalle(Salle salle) {
		return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_salles Where ID_Salle="+salle.getID()+")");
	}
	
	@Override
	public boolean addSeance(Seance seance) 
	{
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO `seance` (`ID`, `Semaine`, `Date`, `Heure_Debut`, `Heure_Fin`, `Etat`, `ID_Cours`, `ID_Type`) VALUES "
					+ "(NULL, '"+seance.getSemaine()+"', '"+seance.getDate()+"', '"+seance.getDebut()+"', '"+seance.getFin()+"', '"+seance.getEtat()+"', '"+seance.getCours().getID()+"', '"+seance.getType().getID()+"');", Statement.RETURN_GENERATED_KEYS);
			ResultSet keysResultSet = statement.getGeneratedKeys();
			long newID = 0;
			if (keysResultSet != null && keysResultSet.next())
				newID = keysResultSet.getLong(1);
			
					
			for (Groupe groupe : seance.getGroupes()) {
				connection.createStatement().executeUpdate("INSERT INTO `seance_groupes` (`ID_Seance`, `ID_Groupe`) VALUES ('"+newID+"', '"+groupe.getID()+"');");
			}
			for (Utilisateur enseignant : seance.getEnseignants()) {
				connection.createStatement().executeUpdate("INSERT INTO `seance_enseigants` (`ID_Seance`, `ID_Enseignant`) VALUES ('"+newID+"', '"+enseignant.getID()+"');");
			}
			for (Salle salle : seance.getSalles()) {
				connection.createStatement().executeUpdate("INSERT INTO `seance_salles` (`ID_Seance`, `ID_Salle`) VALUES ('"+newID+"', '"+salle.getID()+"');");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean salleLibre(Salle salle, Time heureDebut, Time heureFin) 
	{
		for (Seance seance : getBySalle(salle)) {
			if (!(seance.getDebut().after(heureFin) || seance.getFin().before(heureDebut)))
				return false;
		}
		return true;
	}

	
}
