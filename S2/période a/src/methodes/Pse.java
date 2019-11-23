package methodes;

import java.util.ArrayList;
import java.util.List;

import systeme.Methode;
import systeme.Objet;
import systeme.Sac;

public class Pse implements Methode<Objet> {

	private List<Objet> objetsDeLArbre;
	private List<Objet> objetsRestants;

	private Pse arbreGauche, arbreDroit;

	private int profondeur;
	private double potentiel;

	private static float meilleureValeur;
	public static List<Objet> meilleureListe;

	private Sac<Objet> sac;

	public Pse() {}

	public Pse(Sac<Objet> sac, List<Objet> objs, int i) {
		this.objetsRestants = objs;
		this.sac = sac;

		if (i <= sac.récupérerListe().size()) {
			this.objetsDeLArbre = new ArrayList<Objet>();
			for (int j = 0; j < sac.récupérerListe().size(); ++j) {
				if (this.objetsRestants.size() > j) {
					if (this.objetsRestants.get(j) != null)
						this.objetsDeLArbre.add(this.objetsRestants.get(j));
				}
			}

			this.profondeur = i;
			this.potentiel = this.calculPotentiel(this.sac.récupérerListe());
			float valeurListe = this.calculValeurListe();

			if (valeurListe > Pse.meilleureValeur) {
				Pse.meilleureValeur = valeurListe;
			}

			if (this.profondeur != this.sac.récupérerListe().size()) {
				this.arbreGauche = new Pse(this.sac, new ArrayList<Objet>(this.objetsRestants), this.profondeur + 1);

				this.objetsRestants.set(this.profondeur, this.sac.récupérerListe().get(this.profondeur));

				if (this.calculPoidsListe(this.objetsRestants) <= this.sac.récupérerPoidsMaximal()
						&& this.potentiel > Pse.meilleureValeur) {
					this.arbreDroit = new Pse(this.sac, new ArrayList<Objet>(this.objetsRestants), this.profondeur + 1);
				}
			}
		}
	}

	@Override
	public String récupérerNomMethode() {
		return "pse";
	}

	@Override
	public List<Objet> résoudre(Sac<Objet> sac) {
		List<Objet> tableau = new ArrayList<Objet>();
		
		for(int i = 0; i < sac.récupérerListe().size(); i++) { 
			tableau.add(null);
		}
		
		Pse racine = new Pse(sac, tableau, 0);
		racine.recursifPse();
		
		return Pse.meilleureListe;
	}

	private void recursifPse() {
		if (this.profondeur <= this.sac.récupérerListe().size()) {
			if (this.calculValeurListe() == Pse.meilleureValeur) {
				Pse.meilleureListe = this.objetsDeLArbre;
			} else {
				if (this.arbreGauche != null) {
					this.arbreGauche.recursifPse();
				}

				if (this.arbreDroit != null) {
					this.arbreDroit.recursifPse();
				}
			}
		}
	}

	private float calculPotentiel(List<Objet> listeObjetsSac) {
		float potentiel = 0.0f;

		for (int i = 0; i < this.objetsDeLArbre.size(); ++i) {
			if (this.objetsDeLArbre.get(i) != null) {
				potentiel += this.objetsDeLArbre.get(i).getPrix();
			}
		}

		for (int i = this.profondeur; i < listeObjetsSac.size(); ++i) {
			potentiel += listeObjetsSac.get(i).getPrix();
		}

		return potentiel;
	}

	private float calculValeurListe() {
		float valeurTotal = 0.0f;
		for (int i = 0; i < this.objetsDeLArbre.size(); ++i) {
			if (this.objetsDeLArbre.get(i) != null) {
				valeurTotal += this.objetsDeLArbre.get(i).getPrix();
			}
		}
		return valeurTotal;
	}

	private float calculPoidsListe(List<Objet> listeObjets) {
		float poidsTotal = 0.0f;
		for (int i = 0; i < listeObjets.size(); ++i) {
			if (listeObjets.get(i) != null) {
				poidsTotal += listeObjets.get(i).getPoids();
			}
		}
		return poidsTotal;
	}

}
