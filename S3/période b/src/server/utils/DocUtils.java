package server.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.types.Abonne;
import server.types.Document;
import server.types.EtatDocument;

public class DocUtils {

	/**
	 * R�cup�re les variables minimales du document et de son parent
	 * @param doc
	 * @return
	 */
	public static Map<String, Object> getDocumentInfos(Document doc) {
		return getDocumentInfos(doc, false);
	}
	
	/**
	 * R�cup�re toutes les variables du document et de son parent.
	 * Pour les variables longues, il mettra un espace devant chaque majuscule.
	 * @param doc
	 * @param all (true si on veut avoir toutes les variables de la classe et de son parent)
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> getDocumentInfos(Document doc, boolean all) {
		Map<String, Object> infosDocs = new HashMap<String, Object>();
		infosDocs.put("type", doc.getClass().getSimpleName());

		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(doc.getClass().getDeclaredFields()));
		fields.addAll(Arrays.asList(doc.getClass().getSuperclass().getDeclaredFields()));

		for (Field f : fields) {
			f.setAccessible(true);
			try {
				Object value = f.get(doc);
				String name = f.getName();
				
				if(!all) if(name.equals("etat")  || name.equals("possesseur") || name.equals("moniteur")) continue;
				//https://stackoverflow.com/a/20677443
				name = name.replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2");
				infosDocs.put((name.equals("numero")) ? "r�f�rence" : name, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return infosDocs;
	}

	/**
	 * R�cup�re le titre du document
	 * @param doc
	 * @return String
	 */
	public static String getDocumentTitre(Document doc) {
		return (String) getDocumentInfos(doc).get("titre");
	}
	
	/**
	 * R�cup�re l'�tat du document
	 * @param doc
	 * @return EtatDocument
	 */
	public static EtatDocument getDocumentEtat(Document doc) {
		return (EtatDocument) getDocumentInfos(doc, true).get("etat");
	}
	
	/**
	 * R�cup�re le possesseur du document actuel
	 * Peut retourner null si le document n'est pas poss�d� (reserver ou emprunter)
	 * @param doc
	 * @return Abonne
	 */
	public static Abonne getDocumentPossesseur(Document doc) {
		return (Abonne) getDocumentInfos(doc, true).get("possesseur");
	}
}
