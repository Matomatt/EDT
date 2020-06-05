package Seances;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
		long milli = new java.util.Date().getTime();
		
		List<Seance> list = new ArrayList<Seance>();
		
		ResultSet result;
		try {
			result = connection.createStatement().executeQuery(query + " ORDER BY Date ASC, Heure_Debut ASC");
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		
		List<Donnee> listeCours = cours.getAll();
		List<Donnee> listeTypeCours = type_cours.getAll();
 		
		try {
			while(result.next())
			{
				Donnee ceCours = null;
				try {
					ceCours = listeCours.stream().filter(c -> { try { return c.isOfID(result.getInt("ID_Cours")); }  catch (SQLException e) { return false; } }).findFirst().get();
				} catch (Exception e) {
					ceCours = cours.GetByID(result.getInt("ID_Cours"));
				}
				
				Donnee ceTypeCours = null;
				try {
					ceTypeCours = listeTypeCours.stream().filter(c -> { try { return c.isOfID(result.getInt("ID_Type")); }  catch (SQLException e) { return false; } }).findFirst().get();
				} catch (Exception e) {
					ceTypeCours = type_cours.GetByID(result.getInt("ID_Type"));
				}
				
				try {
					list.add(new Seance(result.getInt("ID"), result.getInt("Semaine"), result.getDate("Date"), result.getTime("Heure_Debut"), result.getTime("Heure_Fin"), 
							result.getInt("Etat"), ceCours, ceTypeCours, groupes.getBySeanceID(result.getInt("ID")),
							utilisateurs.getEnseignantsBySeanceID(result.getInt("ID")), salles.getBySeanceID(result.getInt("ID"))));
				}
				catch(Exception e) { 
					System.out.println("Erreur avec les dates, set at default");
					list.add(new Seance(result.getInt("ID"), result.getInt("Semaine"), new Date(0), new Time(0), new Time(0),
							result.getInt("Etat"), ceCours, ceTypeCours, groupes.getBySeanceID(result.getInt("ID")), 
							utilisateurs.getEnseignantsBySeanceID(result.getInt("ID")), salles.getBySeanceID(result.getInt("ID"))));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println((new java.util.Date().getTime()-milli) + "ms pour charger les salles libres");
		
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
	public List<Seance> getBySalleAtDate(Salle salle, Date date) {
		return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_salles Where ID_Salle="+salle.getID()+") AND Date='"+date+"'");
	}
	
	@Override
	public List<Seance> getBySalleAtWeek(Salle salle, int semaine) {
		return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_salles Where ID_Salle="+salle.getID()+") AND Semaine='"+semaine+"'");
	}
	
	@Override
	public List<Seance> getByPromo(Donnee promotion) {
		return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_groupes Where ID_Groupe IN (Select ID From groupe Where ID_Promotion="+promotion.getID()+"))");
	}
	
	@Override
	public List<Seance> getByPromoAtDate(Donnee promotion, Date date) {
		return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_groupes Where ID_Groupe IN (Select ID From groupe Where ID_Promotion="+promotion.getID()+")) AND Date='"+date+"'");
	}
        
        @Override
	public List<Seance> getByPromoAtWeek(Donnee promotion, int semaine) {
		return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_groupes Where ID_Groupe IN (Select ID From groupe Where ID_Promotion="+promotion.getID()+")) AND Semaine='"+semaine+"'");
	}
	
	@Override
	public List<Seance> getByGroupe(Groupe groupe) {
		return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_groupes Where ID_Groupe="+groupe.getID()+")");
	}
	
	@Override
	public List<Seance> getByGroupeAtDate(Groupe groupe, Date date) {
		return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_groupes Where ID_Groupe="+groupe.getID()+") AND Date='"+date+"'");
	}
        
        @Override
	public List<Seance> getByGroupeAtWeek(Groupe groupe,  int semaine) {
		return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_groupes Where ID_Groupe="+groupe.getID()+") AND Semaine='"+semaine+"'");
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
	public List<Seance> getByUtilisateurAtDate(Utilisateur utilisateur, Date date) {
		if (utilisateur.getType() == User.UserType.Etudiant)
			return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_groupes Where ID_Groupe IN (Select ID_Groupe From etudiant Where ID_Utilisateur="+utilisateur.getID()+")) AND Date='"+date+"'");
		else
			return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_enseignants Where ID_Enseignant="+utilisateur.getID()+") AND Date='"+date+"'");
	}
	
	@Override
	public List<Seance> getByUtilisateurAtWeek(Utilisateur utilisateur, int week) {
		System.out.println("Week " + week);
		if (utilisateur.getType() == User.UserType.Etudiant)
			return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_groupes Where ID_Groupe IN (Select ID_Groupe From etudiant Where ID_Utilisateur="+utilisateur.getID()+")) AND Semaine='"+week+"'");
		else
			return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_enseignants Where ID_Enseignant="+utilisateur.getID()+") AND Semaine='"+week+"'");
	}
	
	
	private Map<String, Double> getNombreHeure(Utilisateur utilisateur, String whereQuery)
	{
		Map<String, Double> map = new LinkedHashMap<String, Double>();
		String query = "Select cours.Nom as nomCours, TIMESTAMPDIFF(minute, CAST(Heure_Debut as Datetime), CAST(Heure_Fin as Datetime)) as duree From seance,cours Where ";
		
		if (utilisateur.getType() == User.UserType.Etudiant)
			query += "seance.ID IN (Select ID_Seance From seance_groupes Where ID_Groupe IN (Select ID_Groupe From etudiant Where ID_Utilisateur = "+utilisateur.getID()+") ) AND ";
		else if (utilisateur.getType() == User.UserType.Enseignant)
			query += "seance.ID IN (Select ID_Seance From seance_enseignants Where ID_Enseignant = "+utilisateur.getID()+") AND ";
		
		query += "cours.ID = seance.ID_Cours" + whereQuery + " ORDER BY cours.Nom ASC";
		
		ResultSet result;
		try {
			result = connection.createStatement().executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return map;
		}
		
		try {
			while(result.next())
			{
				String nomCours = result.getString("nomCours");
				if (map.containsKey(nomCours))
					map.put(nomCours, ((Double) map.get(nomCours)).doubleValue() + result.getDouble("duree")/60.0);
				else
					map.put(nomCours, result.getDouble("duree")/60.0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	@Override
	public Map<String, Double> getNombreHeureParCours(Utilisateur utilisateur) 
	{
		return getNombreHeure(utilisateur, "");
	}
	
	@Override
	public Map<String, Double> getNombreHeureEffectueeParCours(Utilisateur utilisateur) {
		return getNombreHeure(utilisateur, " AND seance.Date < '"+new Date(new java.util.Date().getTime())+"'");
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
	public void update(Seance seance) 
	{
		System.out.println("update");
		try {
			connection.createStatement().executeUpdate("UPDATE `seance` SET `Semaine` = '"+seance.getSemaine()+"', `Date` = '"+seance.getDate()+"', "
					+ "`Heure_Debut` = '"+seance.getDebut()+"', `Heure_Fin` = '"+seance.getFin()+"', `Etat` = '"+seance.getEtat()+"', "
					+ "`ID_Cours` = '"+seance.getCours().getID()+"', `ID_Type` = '"+seance.getType().getID()+"' WHERE `seance`.`ID` = "+seance.getID()+";");
			
			connection.createStatement().executeUpdate("DELETE From `seance_groupes` Where ID_Seance="+seance.getID());
			connection.createStatement().executeUpdate("DELETE From `seance_enseignants` Where ID_Seance="+seance.getID());
			connection.createStatement().executeUpdate("DELETE From `seance_salles` Where ID_Seance="+seance.getID());
			
			for (Groupe groupe : seance.getGroupes()) {
				System.out.println(groupe);
				connection.createStatement().executeUpdate("INSERT INTO `seance_groupes` (`ID_Seance`, `ID_Groupe`) VALUES ('"+seance.getID()+"', '"+groupe.getID()+"');");
			}
			for (Utilisateur enseignant : seance.getEnseignants()) {
				System.out.println(enseignant);
				connection.createStatement().executeUpdate("INSERT INTO `seance_enseignants` (`ID_Seance`, `ID_Enseignant`) VALUES ('"+seance.getID()+"', '"+enseignant.getID()+"');");
			}
			for (Salle salle : seance.getSalles()) {
				System.out.println(salle);
				System.out.println("INSERT INTO `seance_salles` (`ID_Seance`, `ID_Salle`) VALUES ('"+seance.getID()+"', '"+salle.getID()+"');");
				connection.createStatement().executeUpdate("INSERT INTO `seance_salles` (`ID_Seance`, `ID_Salle`) VALUES ('"+seance.getID()+"', '"+salle.getID()+"');");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(Seance seance) {
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

	@Override
	public boolean salleLibre(Salle salle, Time heureDebut, Time heureFin, Date date) 
	{
		
		for (Seance seance : getBySalleAtDate(salle, date)) {
			//System.out.println(date + "==" + seance.getDate());
			if (!(seance.getDebut().after(heureFin) || seance.getFin().before(heureDebut)) && date.equals(seance.getDate()))
				return false;
		}
		return true;
	}
	
	@Override
	public boolean promoLibre(Donnee promotion, Time heureDebut, Time heureFin, Date date) {
		for (Seance seance : getByPromoAtDate(promotion, date)) {
			//System.out.println(date + "==" + seance.getDate());
			if (!(seance.getDebut().after(heureFin) || seance.getFin().before(heureDebut)) && date.equals(seance.getDate()))
				return false;
		}
		return true;
	}
	
	@Override
	public boolean groupeLibre(Groupe groupe, Time heureDebut, Time heureFin, Date date) {
		for (Seance seance : getByGroupeAtDate(groupe, date)) {
			//System.out.println(seance.getDebut() + " / " + heureFin + " : " + seance.getDebut().after(heureFin) + " || " + seance.getFin() +" / " + heureDebut + " : " + seance.getFin().before(heureDebut) + " && " + date + "==" + seance.getDate() + " : " + date.equals(seance.getDate()));
			if (!(seance.getDebut().after(heureFin) || seance.getFin().before(heureDebut)) && date.equals(seance.getDate()))
				return false;
		}
		return true;
	}

	@Override
	public boolean utilisateurLibre(Utilisateur utilisateur, Time heureDebut, Time heureFin, Date date) {
		for (Seance seance : getByUtilisateurAtDate(utilisateur, date)) {
			//System.out.println(date + "==" + seance.getDate());
			if (!(seance.getDebut().after(heureFin) || seance.getFin().before(heureDebut)) && date.equals(seance.getDate()))
				return false;
		}
		return true;
	}
	
	@Override
	public boolean seancePossible(Seance seance) 
	{
		for (Groupe groupe : seance.getGroupes()) {
			if (!groupeLibre(groupe, seance.getDebut(), seance.getFin(), seance.getDate()))
				return false;
		}
		for (Utilisateur enseignant : seance.getEnseignants()) {
			if (!utilisateurLibre(enseignant, seance.getDebut(), seance.getFin(), seance.getDate()))
				return false;
		}
		for (Salle salle : seance.getSalles()) {
			if (!salleLibre(salle, seance.getDebut(), seance.getFin(), seance.getDate()))
				return false;
		}
		return true;
	}	
}