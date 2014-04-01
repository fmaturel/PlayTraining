package models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Représente le Genre d'un {@link Film}
 */
public enum Genre {

	COMEDIE, DRAME, ACTION;

	/**
	 * Transforme une enum en {@link Map} pour les besoins d'affichage dans les templates Play!
	 * 
	 * @return une {@link Map} affichable par un template Play!
	 */
	public static Map<String, String> options() {
		LinkedHashMap<String, String> vals = new LinkedHashMap<String, String>();
		for (Genre genre : Genre.values()) {
			vals.put(genre.toString(), genre.toString());
		}
		return vals;
	}
}
