

import java.util.ArrayList;
import java.util.List;

import methodes.Dynamique;
import methodes.Gloutonne;
import methodes.Pse;
import systeme.Methode;
import systeme.Objet;
import systeme.Sac;

public class Main {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Nombre de param�tre incorrecte");
			System.out.println("syntaxe: resoudre-sac-a-dos chemin poids m�thode");
			return;
		}

		String chemin = args[0];
		float poidsMaximal = Float.valueOf(args[1]);
		String m�thode = args[2];
		
		if (poidsMaximal == 0.0F) {
			System.err.println("Le poid ne peut �tre �gale � 0");
			return;
		}

		Sac<Objet> sac = new SacADos(chemin, poidsMaximal);
		boolean m�thodeExiste = false;
		List<Methode<Objet>> m�thodesDisponibles = new ArrayList<Methode<Objet>>();
		m�thodesDisponibles.add(new Pse());
		m�thodesDisponibles.add(new Dynamique());
		m�thodesDisponibles.add(new Gloutonne());
		
		
		for (Methode<Objet> m : m�thodesDisponibles) {
			if (m.r�cup�rerNomMethode().equalsIgnoreCase(m�thode)) {
				sac.d�finirMethodeResolution(m);
				m�thodeExiste = true;
			}
		}
		
		if(!m�thodeExiste) {
			System.err.println("La m�thode n'existe pas");
			System.out.println("M�thode(s) disponible(s) :");
			for (Methode<Objet> m : m�thodesDisponibles) {
				System.out.println(m.r�cup�rerNomMethode());
			}
			return;
		}
		
		sac.r�soudre();
		System.out.println(sac);
	}

}
