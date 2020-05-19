package Donnees;

import java.sql.SQLException;
import java.util.List;

public interface ListeDonnees {
	public List<Donnee> getAll();
	public boolean Update(Donnee d) throws SQLException;
	public Donnee GetByID(int ID);
}
