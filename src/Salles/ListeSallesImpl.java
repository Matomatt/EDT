package Salles;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Donnees.Donnee;
import Donnees.ListeDonnees;
import Seances.Seance;
import Utilitaires.TimeComparison;

public class ListeSallesImpl implements ListeSalles
{
	private static final int nbCreneauxEnUneJournee = 7;
	Connection connection = null;
	ListeDonnees sites = null;
	
	
	public ListeSallesImpl(Connection conn, ListeDonnees _sites) {
		connection = conn;
		sites = _sites;
	}
	
	private List<Salle> ExecuteQuery(String query)
	{
		List<Salle> list = new ArrayList<Salle>();
		
		try {
			ResultSet result = connection.createStatement().executeQuery(query);
			
			while(result.next())
				list.add(new Salle(result.getInt("ID"), result.getString("Nom"), result.getInt("Capacite"), sites.GetByID(result.getInt("ID_Site")) ));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	@Override
	public List<Salle> getAll()
	{
		return ExecuteQuery("Select * from salle");
	}

	@Override
	public List<Salle> getBySeance(Seance seance) {
		return getBySeanceID(seance.getID());
	}

	@Override
	public List<Salle> getBySeanceID(int ID) {
		return ExecuteQuery("Select * From salle Where ID IN (Select ID_Salle From seance_salles Where ID_Seance="+ID+")");
	}
	
	@Override
	public LinkedHashMap<String, LinkedHashMap<Time[], List<Salle>>> getSallesLibresAtWeekStartingFrom(int week, java.util.Date date) 
	{
		long milli = new java.util.Date().getTime();
		
		LinkedHashMap<String, LinkedHashMap<Time[], List<Salle>>> sallesLibresMap = new LinkedHashMap<String, LinkedHashMap<Time[], List<Salle>>>();
		List<Salle> allSalles = getAll();
		
		try {
			Time nowTime = new Time(date.getTime());
			
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        int startingDay = calendar.get(Calendar.DAY_OF_WEEK);
	        
	        if (startingDay == 1) { startingDay = 2; calendar.add(Calendar.DATE, 1); }
	        
			for (int day = startingDay; day < 8; day++) 
			{
				if (day != startingDay)
					calendar.add(Calendar.DATE, 1);
				
				Date lookingForDate = new Date(calendar.getTime().getTime());
				LinkedHashMap<Time[], List<Salle>> map = new LinkedHashMap<Time[], List<Salle>>();
				
				Time heureDebut = new Time(0);
				Time heureFin = new Time(0);
				float h;
				
				List<SalleBeingUsed> salles = new ArrayList<SalleBeingUsed>();
				
				String query = "SELECT salle.*, seance.Heure_Debut, seance.Heure_Fin FROM salle, seance, seance_salles WHERE seance.ID=seance_salles.ID_Seance AND salle.ID=seance_salles.ID_Salle AND seance.Date='"+lookingForDate+"'";
				
				ResultSet result = connection.createStatement().executeQuery(query);
				
				while(result.next())
					salles.add( new SalleBeingUsed(new Salle(result.getInt("ID"), result.getString("Nom"), result.getInt("Capacite"), sites.GetByID(result.getInt("ID_Site"))), result.getTime("Heure_Debut"), result.getTime("Heure_Fin")));
				
				
				for (int j = 0; j < nbCreneauxEnUneJournee; j++)
				{
					//Heure début
					h = j*1.75f+8.5f;
					try { heureDebut = new Time(new SimpleDateFormat("HH:mm").parse((int)h+":"+(int)((h-(int)h)*60)).getTime()); } catch (ParseException e) { e.printStackTrace(); }
					
					//Heure fin
					h += 1.5f;
					try { heureFin = new Time(new SimpleDateFormat("HH:mm").parse((int)h+":"+(int)((h-(int)h)*60)).getTime()); } catch (ParseException e) { e.printStackTrace(); }

					if (h > 14 && day == 7) break;
					
					if (TimeComparison.Compare(heureFin, nowTime) || day != startingDay)
					{
						Time[] creneau = {heureDebut, heureFin};
						List<Integer> IdSallesOccupees = salles.stream().filter(s -> !(s.heureDebut.after(creneau[1]) || s.heureFin.before(creneau[0]))).map(s -> (int)s.salle.getID()).collect(Collectors.toList());
						
						List<Salle> sallesLibres = allSalles.stream().filter(s -> !IdSallesOccupees.contains(s.getID())).collect(Collectors.toList());
						
						map.put(creneau, sallesLibres);
					}
				}
				
				sallesLibresMap.put(new SimpleDateFormat("EEEE").format(calendar.getTime()), map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println((new java.util.Date().getTime()-milli) + "ms pour charger les salles libres");
		return sallesLibresMap;
	}
	
	@Override
	public Map<String, Double> getProportionCapacitePourSite(Donnee site) {
		Map<String, Double> map = new HashMap<String, Double>();
		
		try {
			ResultSet result = connection.createStatement().executeQuery("Select Capacite, COUNT(Capacite) as nbCapa From salle Where ID_Site="+site.getID()+" GROUP BY Capacite");
			
			while(result.next())
				map.put(result.getString("Capacite"), result.getDouble("nbCapa"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	@Override
	public Map<String, Double> getMoyenneOccupationPourSite(Donnee site) {
		Map<String, Double> map = new LinkedHashMap<String, Double>();
		
		try {
			ResultSet result = connection.createStatement().executeQuery("Select Semaine, SUM(TIMESTAMPDIFF(minute, CAST(Heure_Debut as Datetime), CAST(Heure_Fin as Datetime))) as duree From seance,salle,seance_salles Where ID_Salle=salle.ID AND ID_Seance=seance.ID AND ID_Site="+site.getID()+" GROUP BY Semaine");
			
			while(result.next())
				map.put("Semaine "+result.getString("Semaine"), result.getDouble("duree")/60.0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return map;
	}

	@Override
	public void add(Salle salle) {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO `salle` (`ID`, `Nom`, `Capacite`, `ID_Site`) VALUES (NULL, '"+salle.getNom()+"', '"+salle.getCapacite()+"', '"+salle.getSite().getID()+"');", Statement.RETURN_GENERATED_KEYS);

			ResultSet keysResultSet = statement.getGeneratedKeys();
			if (keysResultSet != null && keysResultSet.next())
				salle.setID((int) keysResultSet.getLong(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Salle salle) {
		try {
			connection.createStatement().executeUpdate("UPDATE `salle` SET `Nom` = '"+salle.getNom()+"', `Capacite` = '"+salle.getCapacite()+"', `ID_Site` = '"+salle.getSite().getID()+"' WHERE `salle`.`ID` = " + salle.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Salle salle) {
		try {
			connection.createStatement().executeUpdate("DELETE FROM `salle` WHERE `salle`.`ID` = " + salle.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}







//GARBAGE QUERY but like, I wanna keep it, I worked hard on it ;(
/*
String query1 = "SELECT * FROM `salle` WHERE ID NOT IN (Select ID_Salle from seance_salles WHERE ID_Seance IN (SELECT ID From seance Where "
	+ " (Heure_Debut > '"+heureFin+"' OR Heure_Fin < '"+heureDebut+"') AND Date='"+lookingForDate+"')) ORDER BY ID_Site, Nom";

Time[] creneau = {heureDebut, heureFin};
List<Salle> salles1 = ExecuteQuery(query1);
map.put(creneau, salles1);
*/
