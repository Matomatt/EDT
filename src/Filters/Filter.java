package Filters;

public class Filter {
	public enum Filters{Nom, Utilisateur, Heure_Debut, Heure_Fin}
	
	private Filters filterType;
	private Object value;
	
	public Filter(Filters _filterType, Object _value) {
		filterType = _filterType;
		value = _value;
	}

	public Filters Type() { return filterType; }
	public Object Value() { return value; }
}
