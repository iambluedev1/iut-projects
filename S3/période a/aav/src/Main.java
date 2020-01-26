

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
			System.err.println("Nombre de paramètre incorrecte");
			System.out.println("syntaxe: resoudre-sac-a-dos chemin poids méthode");
			return;
		}

		String chemin = args[0];
		float poidsMaximal = Float.valueOf(args[1]);
		String méthode = args[2];
		
		if (poidsMaximal == 0.0F) {
			System.err.println("Le poid ne peut être égale à 0");
			return;
		}

		Sac<Objet> sac = new SacADos(chemin, poidsMaximal);
		boolean méthodeExiste = false;
		List<Methode<Objet>> méthodesDisponibles = new ArrayList<Methode<Objet>>();
		méthodesDisponibles.add(new Pse());
		méthodesDisponibles.add(new Dynamique());
		méthodesDisponibles.add(new Gloutonne());
		
		
		for (Methode<Objet> m : méthodesDisponibles) {
			if (m.récupérerNomMethode().equalsIgnoreCase(méthode)) {
				sac.définirMethodeResolution(m);
				méthodeExiste = true;
			}
		}
		
		if(!méthodeExiste) {
			System.err.println("La méthode n'existe pas");
			System.out.println("Méthode(s) disponible(s) :");
			for (Methode<Objet> m : méthodesDisponibles) {
				System.out.println(m.récupérerNomMethode());
			}
			return;
		}
		
		sac.résoudre();
		System.out.println(sac);
	}

}
