package fr.sokchalons.crazycircus.utils;

import java.util.Formatter;

/**
 * La classe CustomStringBuilder
 */
public class CustomStringBuilder {

	/**
	 * Retourner la chaine de caract�res correspondante a un format (�quivalent au printf)
	 *
	 * @param format : le format souhait�
	 * @param datas : toutes les donn�es
	 * @return la chaine de caract�res
	 */
	public String appendFormat(String format, Object... datas) {
		Formatter formatter = new Formatter();
		formatter.format(format, datas);
		
		String tmp = formatter.toString();
		
		formatter.close();
		
		return tmp;
	}
	
}
