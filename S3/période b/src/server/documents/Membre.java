package server.documents;

import java.util.Calendar;
import java.util.Date;

import server.types.Abonne;
import server.types.Serializable;
import server.types.Sexe;

public class Membre extends Serializable implements Abonne {

	private String nom;
	private String prenom;
	private String email;
	private Sexe sexe;
	private int num�ro;
	private int age;
	private Date estBannisJusqua = null;

	public Membre(String nom, String email, int num�ro, int age, String prenom, Sexe sexe) {
		this.nom = nom;
		this.email = email;
		this.num�ro = num�ro;
		this.age = age;
		this.prenom = prenom;
		this.sexe = sexe;
	}
	
	/**
	 * Constructeur utilis� pour pouvoir appeller la fonction serialize
	 * Ce constructeur ne doit �tre utilis� que dans ce but.
	 */
	public Membre() {}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public String getEmail() {
		return this.email;
	}

	@Override
	public int getNumero() {
		return this.num�ro;
	}

	@Override
	public Integer getAge() {
		return this.age;
	}

	@Override
	public Serializable serialize(String line) {
		String[] parts = line.split(";");
		return new Membre(parts[2].trim(), parts[5].trim(), Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[4].trim()), parts[1].trim(), Sexe.values()[Integer.parseInt(parts[3].trim())]);
	}

	@Override
	public String toString() {
		return "Membre [nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", sexe=" + sexe + ", num�ro="
				+ num�ro + ", age=" + age + "]";
	}

	@Override
	public String getPrenom() {
		return this.prenom;
	}

	@Override
	public Sexe getSexe() {
		return this.sexe;
	}

	@Override
	public void bannir() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());            
		calendar.add(Calendar.MONTH, 1);
		this.estBannisJusqua = calendar.getTime();
		System.out.println(this.nom + " " + this.prenom + " est bannis jusqu'au " + this.estBannisJusqua.toString());
	}

	@Override
	public boolean estBannis() {
		if(this.estBannisJusqua == null) return false;
		return (new Date()).compareTo(this.estBannisJusqua) < 0;
	}

	@Override
	public Date getBanJusqua() {
		return this.estBannisJusqua;
	}
	
}
