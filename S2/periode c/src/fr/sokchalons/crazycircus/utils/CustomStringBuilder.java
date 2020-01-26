package fr.sokchalons.crazycircus.utils;

import java.util.Formatter;

/**
 * La classe CustomStringBuilder
 */
public class CustomStringBuilder {

	/**
	 * Retourner la chaine de caractères correspondante a un format (équivalent au printf)
	 *
	 * @param format : le format souhaité
	 * @param datas : toutes les données
	 * @return la chaine de caractères
	 */
	public String appendFormat(String format, Object... datas) {
		Formatter formatter = new Formatter();
		formatter.format(format, datas);
		
		String tmp = formatter.toString();
		
		formatter.close();
		
		return tmp;
	}
	
}
