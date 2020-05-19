package Filters;

import java.sql.Time;

import Donnees.Donnee;
import Salles.Salle;
import Utilisateurs.Utilisateur;

public class Filter {
	public enum Filters{Nom, Utilisateur, Semaine, Heure_Debut, Heure_Fin, Salle, Cours, TypeDeCours, EtatSeance, Promotion}
	
	private Filters filterType;
	private Object value;
	
	public Filter(Filters _filterType, Object _value) throws NonMatchingTypeAndValueException, NullPointerException {
		filterType = _filterType;
		value = _value;
		
		if (filterType == Filters.Nom)
		{
			try {
				if (((String)value)==null)
					throw new NullPointerException("The value given is null");
			} catch (Exception e) {
				throw new NonMatchingTypeAndValueException("The value object of filter type 'Nom' should be of type String");
			}
		}
		
		if (filterType == Filters.Utilisateur)
		{
			try {
				if (((Utilisateur)value)==null)
					throw new NullPointerException("The value given is null");
			} catch (Exception e) {
				throw new NonMatchingTypeAndValueException("The value object of filter type 'Utilisateur' should be of type Utilisateur");
			}
		}
		
		if (filterType == Filters.Semaine)
		{
			try {
				if (((Integer)value)==null)
					throw new NullPointerException("The value given is null");
			} catch (Exception e) {
				throw new NonMatchingTypeAndValueException("The value object of filter type 'Semaine' should be of type Integer");
			}
		}

		if (filterType == Filters.Heure_Debut)
		{
			try {
				if (((Time)value)==null)
					throw new NullPointerException("The value given is null");
			} catch (Exception e) {
				throw new NonMatchingTypeAndValueException("The value object of filter type 'Heure_Debut' should be of type Time");
			}
		}

		if (filterType == Filters.Heure_Fin)
		{
			try {
				if (((Time)value)==null)
					throw new NullPointerException("The value given is null");
			} catch (Exception e) {
				throw new NonMatchingTypeAndValueException("The value object of filter type 'Heure_Fin' should be of type Time");
			}
		}
		
		if (filterType == Filters.Salle)
		{
			try {
				if (((Salle)value)==null)
					throw new NullPointerException("The value given is null");
			} catch (Exception e) {
				throw new NonMatchingTypeAndValueException("The value object of filter type 'Salle' should be of type Salle");
			}
		}

		if (filterType == Filters.Cours)
		{
			try {
				if (((Donnee)value)==null)
					throw new NullPointerException("The value given is null");
			} catch (Exception e) {
				throw new NonMatchingTypeAndValueException("The value object of filter type 'Cours' should be of type Donnee");
			}
		}

		if (filterType == Filters.TypeDeCours)
		{
			try {
				if (((Donnee)value)==null)
					throw new NullPointerException("The value given is null");
			} catch (Exception e) {
				throw new NonMatchingTypeAndValueException("The value object of filter type 'TypeDeCours' should be of type Donnee");
			}
		}

		if (filterType == Filters.EtatSeance)
		{
			try {
				if (((Integer)value)==null)
					throw new NullPointerException("The value given is null");
			} catch (Exception e) {
				throw new NonMatchingTypeAndValueException("The value object of filter type 'EtatSeance' should be of type Integer");
			}
		}
		
		if (filterType == Filters.Promotion)
		{
			try {
				if (((Donnee)value)==null)
					throw new NullPointerException("The value given is null");
			} catch (Exception e) {
				throw new NonMatchingTypeAndValueException("The value object of filter type 'Promotion' should be of type Donnee");
			}
		}
	}

	public Filters Type() { return filterType; }
	public Object Value() { return value; }
}
