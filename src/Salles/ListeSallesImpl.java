package Salles;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
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
		LinkedHashMap<String, LinkedHashMap<Time[], List<Salle>>> sallesLibresMap = new LinkedHashMap<String, LinkedHashMap<Time[], List<Salle>>>();
		
		try {
			Time nowTime = new Time(date.getTime());
			System.out.println(nowTime);
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        int startingDay = calendar.get(Calendar.DAY_OF_WEEK);

			for (int day = startingDay; day < 8; day++) 
			{
				if (day != startingDay)
					calendar.add(Calendar.DATE, 1);
				
				Date lookingForDate = new Date(calendar.getTime().getTime());
				LinkedHashMap<Time[], List<Salle>> map = new LinkedHashMap<Time[], List<Salle>>();
				
				for (int j = 0; j < nbCreneauxEnUneJournee; j++) 
				{
					float h; int m;
					h = j*1.75f+8.5f;
					m = (int) ((h-(int)h)*60);
					@SuppressWarnings("deprecation")
					Time heureDebut = new Time((int) h,m,0);
					h += 1.5f;
					m = (int) ((h-(int)h)*60);
					@SuppressWarnings("deprecation")
					Time heureFin = new Time((int) h,m,0);
					System.out.println(heureFin);
					if (TimeComparison.Compare(heureFin, nowTime) || day != startingDay)
					{
						String query = "SELECT * FROM `salle` WHERE ID NOT IN (Select ID_Salle from seance_salles WHERE ID_Seance IN (SELECT ID From seance Where "
							+ " (Heure_Debut > '"+heureFin+"' OR Heure_Fin < '"+heureDebut+"') AND Date='"+lookingForDate+"')) ORDER BY ID_Site, Nom";
						//System.out.println(query);
						ResultSet result = connection.createStatement().executeQuery(query);
						
						Time[] creneau = {heureDebut, heureFin};
						List<Salle> salles = new ArrayList<Salle>();
						
						while(result.next())
							salles.add(new Salle(result.getInt("ID"), result.getString("Nom"), result.getInt("Capacite"), sites.GetByID(result.getInt("ID_Site")) ));
						
						map.put(creneau, salles);
					}
					
				}
				
				sallesLibresMap.put(new SimpleDateFormat("EEEE").format(calendar.getTime()), map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return sallesLibresMap;
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
