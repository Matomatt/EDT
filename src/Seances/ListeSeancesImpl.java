package Seances;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
import Utilisateurs.User.UserType;

/**
 * Se connecte a la bdd pour gérer les séances
 */
public class ListeSeancesImpl implements ListeSeances {
	
	private Connection connection = null;
	private ListeDonnees cours = null;
	private ListeDonnees type_cours = null;
	private ListeGroupes groupes = null;
	private ListeUtilisateurs utilisateurs = null;
	private ListeSalles salles = null;
	
	/**
	 * Constructeur, recupère l'interface qui accède aux cours, type de cours, groupes, utilisateurs et salles
	 * @param conn
	 * @param _cours
	 * @param _type_cours
	 * @param _groupes
	 * @param _utilisateurs
	 * @param _salles
	 */
	public ListeSeancesImpl(Connection conn, ListeDonnees _cours, ListeDonnees _type_cours, ListeGroupes _groupes, ListeUtilisateurs _utilisateurs, ListeSalles _salles)
	{
		connection = conn;
		cours = _cours;
		type_cours = _type_cours;
		groupes = _groupes;
		utilisateurs = _utilisateurs;
		salles = _salles;
	}
	
	/**
	 * Execute la query indiquée et renvoie la List avec toutes les séances trouvées les cours, groupes tout ça sont récupérés automatiquement
	 * @param query
	 * @return
	 */
	private List<Seance> ExecuteQuery(String query)
	{
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
	public List<Seance> getBySalleAtWeek(Utilisateur utilisateur, Salle salle, int semaine) {
		if (utilisateur.getType() == UserType.Etudiant)
			return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_salles Where ID_Salle="+salle.getID()+") AND ID IN (Select ID_Seance From seance_groupes Where ID_Groupe IN (Select ID_Groupe FROM etudiant WHERE ID_Utilisateur = "+utilisateur.getID()+")) AND Semaine='"+semaine+"'");
		else if (utilisateur.getType() == UserType.Enseignant || utilisateur.getType() == UserType.Referent_pedagogique)
			return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_salles Where ID_Salle="+salle.getID()+") AND ID IN (Select ID_Seance FROM seance_enseignants WHERE ID_Enseignant="+utilisateur.getID()+") AND Semaine='"+semaine+"'");

		return getBySalleAtWeek(salle, semaine);
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
    
    /**
     * Uniquement pour un enseignant qui veut voir ses cours avec tel promo
     */
	@Override
	public List<Seance> getByPromoAtWeek(Utilisateur utilisateur, Donnee promotion, int semaine) {
		return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_groupes Where ID_Groupe IN (Select ID From groupe Where ID_Promotion="+promotion.getID()+")) AND ID IN (Select ID_Seance FROM seance_enseignants WHERE ID_Enseignant="+utilisateur.getID()+") AND Semaine='"+semaine+"'");
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
	public List<Seance> getByGroupeAtWeek(Utilisateur utilisateur, Groupe groupe, int semaine) {
    	if (utilisateur.getType() == UserType.Etudiant)
			return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_groupes Where ID_Groupe="+groupe.getID()+") AND ID IN (Select ID_Seance From seance_groupes Where ID_Groupe IN (Select ID_Groupe FROM etudiant WHERE ID_Utilisateur = "+utilisateur.getID()+")) AND Semaine='"+semaine+"'");
		else if (utilisateur.getType() == UserType.Enseignant || utilisateur.getType() == UserType.Referent_pedagogique)
			return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_groupes Where ID_Groupe="+groupe.getID()+") AND ID IN (Select ID_Seance FROM seance_enseignants WHERE ID_Enseignant="+utilisateur.getID()+") AND Semaine='"+semaine+"'");

		return getByGroupeAtWeek(groupe, semaine);
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
	
	/**
	 * Cette fonction est forcément que pour un etudiant qui cherche pour un prof
	 */
	@Override
	public List<Seance> getByUtilisateurAtWeek(Utilisateur utilisateurConnecte, Utilisateur utilisateur, int semaine) {
		return ExecuteQuery("Select * From seance Where ID IN (Select ID_Seance From seance_groupes Where ID_Groupe IN (Select ID_Groupe From etudiant Where ID_Utilisateur="+utilisateurConnecte.getID()+")) AND ID IN (Select ID_Seance FROM seance_enseignants WHERE ID_Enseignant="+utilisateur.getID()+") AND Semaine='"+semaine+"'");
	}
	
	/**
	 * Get le nombre d'heure par cours avec les conditions indiquées
	 * @param utilisateur
	 * @param whereQuery
	 * @return
	 */
	private Map<String, Double> getNombreHeure(Utilisateur utilisateur, String whereQuery)
	{
		Map<String, Double> map = new LinkedHashMap<String, Double>();
		String query = "Select cours.Nom as nomCours, SUM(TIMESTAMPDIFF(minute, CAST(Heure_Debut as Datetime), CAST(Heure_Fin as Datetime)))/60 as duree From seance,cours Where ";
		
		if (utilisateur.getType() == User.UserType.Etudiant)
			query += "seance.ID IN (Select ID_Seance From seance_groupes Where ID_Groupe IN (Select ID_Groupe From etudiant Where ID_Utilisateur = "+utilisateur.getID()+") ) AND ";
		else if (utilisateur.getType() == User.UserType.Enseignant)
			query += "seance.ID IN (Select ID_Seance From seance_enseignants Where ID_Enseignant = "+utilisateur.getID()+") AND ";
		
		query += "cours.ID = seance.ID_Cours" + whereQuery + " GROUP BY cours.ID ORDER BY cours.Nom";
		
		
		try {
			ResultSet result = connection.createStatement().executeQuery(query);
			
			while(result.next())
				map.put(result.getString("nomCours"), result.getDouble("duree"));
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
	
	/**
	 * Get toutes les infos pour le recap en seulement 2 query !
	 */
	@Override
	public Map<List<String>, List<String>> getRecap(Utilisateur utilisateur, Date debut, Date fin) 
	{
		Map<List<String>, List<String>> map = new LinkedHashMap<List<String>, List<String>>();
		
		String query = "Select cours.Nom as nomCours, cours.ID as ID_Cours, SUM(TIMESTAMPDIFF(minute, CAST(Heure_Debut as Datetime), CAST(Heure_Fin as Datetime)))/60 as duree, COUNT(seance.ID) as nb";
		
		if (utilisateur.getType() == User.UserType.Etudiant)
			query += " From seance,cours Where seance.ID IN (Select ID_Seance From seance_groupes Where ID_Groupe IN (Select ID_Groupe From etudiant Where ID_Utilisateur = "+utilisateur.getID()+") ) AND "
					+ "cours.ID = seance.ID_Cours" + " AND Date>='"+ debut + "'" + "AND Date<='"+ fin + "' GROUP BY cours.Nom ORDER BY cours.Nom";
		else if (utilisateur.getType() == User.UserType.Enseignant)
			query += ",groupe.Nom as nomGroupe, promotion.Nom as nomPromo, groupe.ID as ID_Groupe, promotion.ID as ID_Promo "
					+ "From seance, cours, seance_groupes, groupe, promotion "
					+ "Where seance.ID IN (Select ID_Seance From seance_enseignants Where ID_Enseignant = "+utilisateur.getID()+") AND "
					+ "cours.ID = seance.ID_Cours AND seance_groupes.ID_Seance=seance.ID AND seance_groupes.ID_Groupe=groupe.ID AND groupe.ID_Promotion=promotion.ID" 
					+ " AND Date>='"+ debut + "'" + "AND Date<='"+ fin + "' GROUP BY cours.Nom, groupe.ID ORDER BY cours.Nom, promotion.Nom, groupe.Nom";
		else
			query += ",groupe.Nom as nomGroupe, promotion.Nom as nomPromo, groupe.ID as ID_Groupe, promotion.ID as ID_Promo "
					+ "From seance, cours, seance_groupes, groupe, promotion Where cours.ID = seance.ID_Cours "
					+ "AND seance_groupes.ID_Seance=seance.ID AND seance_groupes.ID_Groupe=groupe.ID AND groupe.ID_Promotion=promotion.ID" 
					+ " AND Date>='"+ debut + "'" + "AND Date<='"+ fin + "' GROUP BY cours.ID, groupe.ID ORDER BY cours.Nom, promotion.Nom, groupe.Nom";
		
		try {
			ResultSet result = connection.createStatement().executeQuery(query);
			
			while(result.next())
			{
				List<String> detailsList = new LinkedList<String>();
				
				String queryDetails = "Select Date, Heure_Debut, Heure_Fin, TIMESTAMPDIFF(minute, CAST(Heure_Debut as Datetime), CAST(Heure_Fin as Datetime))/60 as duree FROM seance";
				
				if (utilisateur.getType() == User.UserType.Etudiant)
					queryDetails += " Where seance.ID IN (Select ID_Seance From seance_groupes Where ID_Groupe IN (Select ID_Groupe From etudiant Where ID_Utilisateur = "+utilisateur.getID()+") ) AND "
								+ "ID_Cours="+result.getLong("ID_Cours") + " AND Date>='"+ debut + "'" + "AND Date<='"+ fin + "' ORDER BY Date ASC, Heure_Debut ASC";
				else if (utilisateur.getType() == User.UserType.Enseignant)
					queryDetails += ",seance_groupes, groupe, promotion Where seance.ID IN (Select ID_Seance From seance_enseignants Where ID_Enseignant = "+utilisateur.getID()+") AND "
								+ "seance_groupes.ID_Seance=seance.ID AND seance_groupes.ID_Groupe=groupe.ID and groupe.ID_Promotion=promotion.ID AND "
								+ "ID_Cours=" + result.getLong("ID_Cours") + " AND groupe.ID=" + result.getLong("ID_Groupe") + " AND promotion.ID=" + result.getLong("ID_Promo")
								+ " AND Date>='"+ debut + "'" + "AND Date<='"+ fin + "' ORDER BY Date ASC, Heure_Debut ASC";
				else
					queryDetails += ",seance_groupes, groupe, promotion Where seance_groupes.ID_Seance=seance.ID AND seance_groupes.ID_Groupe=groupe.ID and groupe.ID_Promotion=promotion.ID AND "
								+ "ID_Cours=" + result.getLong("ID_Cours") + " AND groupe.ID=" + result.getLong("ID_Groupe") + " AND promotion.ID=" + result.getLong("ID_Promo")
								+ " AND Date>='"+ debut + "'" + "AND Date<='"+ fin + "' ORDER BY Date ASC, Heure_Debut ASC";
				
				ResultSet resultDetails = connection.createStatement().executeQuery(queryDetails);
				
				while(resultDetails.next())
					detailsList.add(resultDetails.getString("Date") + " De " +  resultDetails.getString("Heure_Debut") + " à " +  resultDetails.getString("Heure_Fin") + " ("+resultDetails.getDouble("duree")+"h)");
				
				if (detailsList.size()>0)
					map.put(new LinkedList<String>(Arrays.asList(new String[] {result.getString("nomCours") + (utilisateur.getType()==UserType.Etudiant?"":" "+result.getString("nomGroupe")+" "+result.getString("nomPromo")), 
							detailsList.get(0) , detailsList.get(detailsList.size()-1), Double.toString(result.getDouble("duree")), result.getString("nb")})), detailsList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return map;
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
