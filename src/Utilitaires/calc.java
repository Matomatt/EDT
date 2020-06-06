package Utilitaires;

public class calc {
	public static String heureParse(double h)
	{
		return (int)h+":"+(int)((h-(int)h)*60);
	}
}
