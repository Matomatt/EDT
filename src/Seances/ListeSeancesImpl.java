package Seances;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import Donnees.Donnee;
import Donnees.ListeDonnees;
import Groupes.Groupe;
import Groupes.ListeGroupes;
import Salles.ListeSalles;
import Salles.Salle;
import Utilisateurs.ListeUtilisateurs;
import Utilisateurs.User;
import Utilisateurs.Utilisateur;

public class ListeSeancesImpl implements ListeSeances {
	
	private Connection connection = null;
	private ListeDonnees cours = null;
	private ListeDonnees type_cours = null;
	private ListeGroupes groupes = null;
	private ListeUtilisateurs utilisateurs = null;
	private ListeSalles salles = null;
	
	public ListeSeancesImpl(Connection conn, ListeDonnees _cours, ListeDonnees _type_cours, ListeGroupes _groupes, ListeUtilisateurs _utilisateurs, ListeSalles _salles)
	{
		connection = conn;
		cours = _cours;
		type_cours = _type_cours;
		groupes = _groupes;
		utilisateurs = _utilisateurs;
		salles = _salles;
	}
	
	private List<Seance> ExecuteQuery(String query)
	{
		List<Seance> list = new ArrayList<Seance>();
		
		ResultSet result;
		try {
			result = connection.createStatement().executeQuery(query + " ORDER BY Date ASC, Heure_Debut ASC");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		try {
			while(result.next())
			{
				try {
					list.add(new Seance(result.getInt("ID"), result.getInt("Semaine"), result.getDate("Date"), result.getTime("Heure_Debut"), result.getTime("Heure_Fin"), 
							result.getInt("Etat"), cours.GetByID(result.getInt("ID_Cours")), type_cours.GetByID(result.getInt("ID_Type")),
							groupes.getBySeanceID(result.getInt("ID")), utilisateurs.getEnseignantsBySeanceID(result.getInt("ID")), salles.getBySeanceID(result.getInt("ID"))));
				}
				catch(Exception e) { 
					System.out.println("Erreur avec les dates, set at default");
					list.add(new Seance(result.getInt("ID"), result.getInt("Semaine"), new Date(0), new Time(0), new Time(0),
							result.getInt("Etat"), cours.GetByID(result.getInt("ID_Cours")), type_cours.GetByID(result.getInt("ID_Type")),
							groupes.getBySeanceID(result.getInt("ID")), utilisateurs.getEnseignantsBySeanceID(result.getInt("ID")), salles.getBySeanceID(result.getInt("ID"))));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
	public List<Seance> getByPromo(Donnee promotion) {
		return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_groupes Where ID_Groupe IN (Select ID From groupe Where ID_Promotion="+promotion.getID()+"))");
	}
	
	@Override
	public List<Seance> getByGroupe(Groupe groupe) {
		return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_groupes Where ID_Groupe="+groupe.getID()+")");
	}
	
	@Override
	public List<Seance> getByWeek(int week) {
		return ExecuteQuery("Select * From seance Where Semaine="+week);
	}
	
	@Override
	public List<Seance> getByUtilisateur(Utilisateur utilisateur) {
		if (utilisateur.getType() == User.UserType.Etudiant)
			return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_groupes Where ID_Groupe IN (Select ID_Groupe From etudiant Where ID_Utilisateur="+utilisateur.getID()+"))");
		else
			return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_enseignants Where ID_Enseignant="+utilisateur.getID()+")");
	}
	
	@Override
	public List<Seance> getByUtilisateurAtDate(Utilisateur utilisateur, String date) {
		if (java.sql.Date.valueOf(date) == null)
			return null;
		if (utilisateur.getType() == User.UserType.Etudiant)
			return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_groupes Where ID_Groupe IN (Select ID_Groupe From etudiant Where ID_Utilisateur="+utilisateur.getID()+")) AND Date='"+date+"'");
		else
			return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_enseignants Where ID_Enseignant="+utilisateur.getID()+") AND Date='"+date+"'");
	}
	
	@Override
	public List<Seance> getByUtilisateurAtWeek(Utilisateur utilisateur, int week) {
		if (utilisateur.getType() == User.UserType.Etudiant)
			return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_groupes Where ID_Groupe IN (Select ID_Groupe From etudiant Where ID_Utilisateur="+utilisateur.getID()+")) AND Semaine='"+week+"'");
		else
			return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_enseignants Where ID_Enseignant="+utilisateur.getID()+") AND Semaine='"+week+"'");
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
				connection.createStatement().executeUpdate("INSERT INTO `seance_enseignants` (`ID_Seance`, `ID_Enseignant`) VALUES ('"+newID+"', '"+enseignant.getID()+"');");
			}
			for (Salle salle : seance.getSalles()) {
				connection.createStatement().executeUpdate("INSERT INTO `seance_salles` (`ID_Seance`, `ID_Salle`) VALUES ('"+newID+"', '"+salle.getID()+"');");
			}
			
			seance.setID(newID);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean salleLibre(Salle salle, Time heureDebut, Time heureFin, Date date) 
	{
		
		for (Seance seance : getBySalle(salle)) {
			//System.out.println(date + "==" + seance.getDate());
			if (!(seance.getDebut().after(heureFin) || seance.getFin().before(heureDebut)) && date==seance.getDate())
				return false;
		}
		return true;
	}

	@Override
	public boolean promoLibre(Donnee promotion, Time heureDebut, Time heureFin, Date date) {
		for (Seance seance : getByPromo(promotion)) {
			//System.out.println(date + "==" + seance.getDate());
			if (!(seance.getDebut().after(heureFin) || seance.getFin().before(heureDebut)) && date==seance.getDate())
				return false;
		}
		return true;
	}
	
	@Override
	public boolean groupeLibre(Groupe groupe, Time heureDebut, Time heureFin, Date date) {
		for (Seance seance : getByGroupe(groupe)) {
			//System.out.println(date + "==" + seance.getDate());
			if (!(seance.getDebut().after(heureFin) || seance.getFin().before(heureDebut)) && date==seance.getDate())
				return false;
		}
		return true;
	}

	@Override
	public boolean utilisateurLibre(Utilisateur utilisateur, Time heureDebut, Time heureFin, Date date) {
		for (Seance seance : getByUtilisateur(utilisateur)) {
			//System.out.println(date + "==" + seance.getDate());
			if (!(seance.getDebut().after(heureFin) || seance.getFin().before(heureDebut)) && date==seance.getDate())
				return false;
		}
		return true;
	}

	@Override
	public void Delete(Seance seance) {
		try {
			connection.createStatement().executeUpdate("DELETE From seance Where ID="+seance.getID());
								
			for (Groupe groupe : seance.getGroupes()) {
				connection.createStatement().executeUpdate("DELETE From `seance_groupes` Where ID_Groupe="+groupe.getID()+" AND ID_Seance="+seance.getID());
			}
			for (Utilisateur enseignant : seance.getEnseignants()) {
				connection.createStatement().executeUpdate("DELETE From `seance_enseignants` Where ID_Enseignant="+enseignant.getID()+" AND ID_Seance="+seance.getID());
			}
			for (Salle salle : seance.getSalles()) {
				connection.createStatement().executeUpdate("DELETE From `seance_salles` Where ID_Salle="+salle.getID()+" AND ID_Seance="+seance.getID());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
