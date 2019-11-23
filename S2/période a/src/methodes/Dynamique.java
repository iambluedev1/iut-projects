package methodes;

import java.util.ArrayList;
import java.util.List;

import systeme.Methode;
import systeme.Objet;
import systeme.Sac;

public class Dynamique implements Methode<Objet>{

	private float[][] matrice;
	private int maxContenance;
	private static int convert = 10;
	
	@Override
	public String récupérerNomMethode() {
		return "dynamique";
	}

	@Override
	public List<Objet> résoudre(Sac<Objet> sac) {
		this.maxContenance = (int) ((sac.récupérerPoidsMaximal()*convert)+1);
		this.matrice = new float[sac.récupérerListe().size()][maxContenance]; // [ligne][colonne]
		
		this.addObjetMatricePremiereLigne(sac);
		this.addObjetMatrice(sac);
		int i = matrice.length - 1;
		int j = matrice[0].length - 1;
		List<Objet> liste = new ArrayList<Objet>();
		while (matrice[i][j] == matrice[i][j - 1]) {
			j--;
		}
		while (j > 0) {
			while (i > 0 && matrice[i][j] == matrice[i - 1][j]) {
				i--;
			}
			j -= (int) (sac.récupérerListe().get(i).getPoids()* convert);
			if (j >= 0) {
				liste.add(sac.récupérerListe().get(i));
			}
			i--;
		}
		return liste;
	}
	
	private void addObjetMatricePremiereLigne(Sac<Objet> sac) {
		int j = 0;
		while (j < this.maxContenance) {
			if ((sac.récupérerListe().get(0).getPoids() * convert) > j) {
				this.matrice[0][j] = 0;
			} else {
				this.matrice[0][j] = (int)sac.récupérerListe().get(0).getPrix();
			}
			j++;
		}
	}

	private void addObjetMatrice(Sac<Objet> sac) {
		for (int i = 1; i < sac.récupérerListe().size(); i++) {
			for (int j = 0; j < this.maxContenance; j++) {
				if (sac.récupérerListe().get(i).getPoids() * convert > j) {
					this.matrice[i][j] = this.matrice[i - 1][j];
				} else {
					this.matrice[i][j] = Math.max(this.matrice[i-1][j], this.matrice[i - 1][(int) (j - (sac.récupérerListe().get(i).getPoids() * convert))] + sac.récupérerListe().get(i).getPrix());
				}
			}
		}
	}

}
