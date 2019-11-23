

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import systeme.Methode;
import systeme.Objet;
import systeme.Sac;

public class SacADos implements Sac<Objet> {

	private Methode<Objet> m;
	private String chemin;
	private float poidsMaximal;
	private List<Objet> objets;
	
	public SacADos(String chemin, float poidsMaximal) {
		this.chemin = chemin;
		this.poidsMaximal = poidsMaximal;
		this.objets = new ArrayList<Objet>();
		
		if(!chemin.equals("")) {
			for(Objet obj : this.récupérerObjets()) {
				this.ajouterItem(obj);
			}	
		}
	}
	
	private List<String> lireFichier(){
		List<String> lines = new ArrayList<>();
		try {
			Scanner in = new Scanner(new FileInputStream(this.chemin));
			while (in.hasNext()) {
				String a = in.nextLine();
				if(a.isEmpty() == false) {
					lines.add(a);
				}
			} 
			in.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Impossible d'ouvrir le fichier");
		}
		return lines;
	}
	
	private List<Objet> récupérerObjets(){
		List<Objet> objets = new ArrayList<Objet>();
		
		for(String line : this.lireFichier()) {
			String[] parts = line.split(" ; ");
			objets.add(new Objet(parts[0], Float.valueOf(parts[1]), Float.valueOf(parts[2])));
		}
		
		return objets;
	}
	
	@Override
	public void résoudre() {
		this.objets = m.résoudre(this);
	}

	@Override
	public void définirMethodeResolution(Methode<Objet> m) {
		this.m = m;
	}

	@Override
	public void ajouterItem(Objet t) {
		this.objets.add(t);
	}

	@Override
	public float récupérerPoidsMaximal() {
		return this.poidsMaximal;
	}
	
	@Override
	public String toString(){
		float poidsTotal = 0.0f, prixTotal = 0.0f;
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		sb1.append("[SAC-A-DOS]\n");
		sb1.append("- Taille maximum : " + this.poidsMaximal + "\n");
		sb2.append("Contenu du sac à dos : \n");
		for (int i = 0; i <= this.objets.size() - 1; i++) {
			sb2.append(this.objets.get(i).toString() + "\n");
			poidsTotal += this.objets.get(i).getPoids();
			prixTotal += this.objets.get(i).getPrix();
		}
		sb1.append("- Poids total : " + poidsTotal + " kg\n");
		sb1.append("- Prix total : " + prixTotal + " €\n");
		sb1.append(sb2.toString());
		return sb1.toString();
		
	}

	@Override
	public List<Objet> récupérerListe() {
		return this.objets;
	}

}
