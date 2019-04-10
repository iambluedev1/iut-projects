package fr.sokchalons.crazycircus.party;

import java.util.ArrayList;
import java.util.List;

/**
 * L'énumération Mouvement
 */
public enum EnumMovement {
	
	KI("BLEU --> ROUGE"),
	NI("BLEU  ^"),
	LO("BLEU <-- ROUGE"),
	MA("ROUGE ^"),
	SO("BLEU <-> ROUGE");
	
	/** Le mouvement */
	private String movement;

	/**
	 * Constructeur de l'énumération permet d'instancier un nouveau mouvement
	 *
	 * @param movement : le mouvement effectué
	 */
	private EnumMovement(String movement) {
		this.movement = movement;
	}

	/**
	 * Analyser la commande et retourner les mouvements associées
	 *
	 * @param command : la commande
	 * @return list : la liste de mouvements
	 */
	public static List<EnumMovement> parseCommand(String command) {
		List<EnumMovement> mvs = new ArrayList<EnumMovement>();
		
		if (command.length() % 2 != 0) {
			return null;
		}

		String tmp = command.toUpperCase();
		
		
		List<String> values = new ArrayList<String>();
		
		for(int x = 0; x < tmp.length(); x+=2) {
			values.add(tmp.substring(x, x+2));
		}
		
		for (String s : values) {
			try {
				if (EnumMovement.valueOf(s) == null) {
					return null;
				} else {
					mvs.add(EnumMovement.valueOf(s));
				}
			}catch(Exception e) {
				return null;
			}
		}

		return mvs;
	}
	
	/**
	 * Retourne une aide liée au jeu
	 *
	 * @return string : l'aide contenue dans une chaine de caractères
	 */
	public static String showHelp() {
		StringBuilder sb = new StringBuilder();
		sb.append("--------------------------------------------\n");
		
		for(int i = 0; i < EnumMovement.values().length; i++) {
			if((i+1) % 2 == 0) {
				sb.append("      " + EnumMovement.values()[i] + " : " + EnumMovement.values()[i].getMovement() + "\n");
			}else {
				sb.append(EnumMovement.values()[i] + " : " + EnumMovement.values()[i].getMovement());
				
			}
		}
		
		sb.append("\n\n");
		
		return sb.toString();
	}
	
	/**
	 * Gets the movement.
	 *
	 * @return the movement
	 */
	public String getMovement() {
		return this.movement;
	}
}
