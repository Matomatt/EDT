package Groupes;

import java.util.List;

import Filters.Filter;

public interface ListeGroupes {
	public List<Groupe> getAll();
	public List<Groupe> getFilteredBy(Filter[] filters);
	public boolean Update(Groupe groupe);
}
