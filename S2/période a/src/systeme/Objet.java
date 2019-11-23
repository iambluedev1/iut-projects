package systeme;

public class Objet {
	private String nom;
	private float prix;
	private float poids;
	private float rapport;
	
	public Objet(String nom, float p, float v) {
		this.nom = nom;
		this.prix = v;
		this.poids = p;
		this.rapport = this.poids/this.prix;
	}

	public float getPrix() {
		return prix;
	}

	public float getRapport() {
		return rapport;
	}

	public void setRapport(float rapport) {
		this.rapport = rapport;
	}

	public void setPrix(float valeur) {
		this.prix = valeur;
	}

	public float getPoids() {
		return poids;
	}

	public void setPoids(float poids) {
		this.poids = poids;
	}

	@Override
	public String toString() {
		return "Objet [nom=" + nom + ", prix=" + prix + ", poids=" + poids + "]";
	}
}