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
	public String r�cup�rerNomMethode() {
		return "dynamique";
	}

	@Override
	public List<Objet> r�soudre(Sac<Objet> sac) {
		this.maxContenance = (int) ((sac.r�cup�rerPoidsMaximal()*convert)+1);
		this.matrice = new float[sac.r�cup�rerListe().size()][maxContenance]; // [ligne][colonne]
		
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
			j -= (int) (sac.r�cup�rerListe().get(i).getPoids()* convert);
			if (j >= 0) {
				liste.add(sac.r�cup�rerListe().get(i));
			}
			i--;
		}
		return liste;
	}
	
	private void addObjetMatricePremiereLigne(Sac<Objet> sac) {
		int j = 0;
		while (j < this.maxContenance) {
			if ((sac.r�cup�rerListe().get(0).getPoids() * convert) > j) {
				this.matrice[0][j] = 0;
			} else {
				this.matrice[0][j] = (int)sac.r�cup�rerListe().get(0).getPrix();
			}
			j++;
		}
	}

	private void addObjetMatrice(Sac<Objet> sac) {
		for (int i = 1; i < sac.r�cup�rerListe().size(); i++) {
			for (int j = 0; j < this.maxContenance; j++) {
				if (sac.r�cup�rerListe().get(i).getPoids() * convert > j) {
					this.matrice[i][j] = this.matrice[i - 1][j];
				} else {
					this.matrice[i][j] = Math.max(this.matrice[i-1][j], this.matrice[i - 1][(int) (j - (sac.r�cup�rerListe().get(i).getPoids() * convert))] + sac.r�cup�rerListe().get(i).getPrix());
				}
			}
		}
	}

}
