package methodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import systeme.Methode;
import systeme.Objet;
import systeme.Sac;

public class Gloutonne implements Methode<Objet> {

	@Override
	public String r�cup�rerNomMethode() {
		return "gloutonne";
	}

	@Override
	public List<Objet> r�soudre(Sac<Objet> sac) {
		List<Objet> liste = new ArrayList<Objet>(sac.r�cup�rerListe());
		List<Objet> listeTrie = new ArrayList<Objet>(tri(liste));
		liste.clear();
		float sommePoids = 0;
		
		for (int i = 0; i<= listeTrie.size() - 1; i++) {
			if((sommePoids + listeTrie.get(i).getPoids()) < sac.r�cup�rerPoidsMaximal()){
				sommePoids += listeTrie.get(i).getPoids();
				liste.add(listeTrie.get(i));
			}
		}
		
		return liste;
	}
	
	private List<Objet> tri(List<Objet> list){
		List<Objet> tmp = new ArrayList<Objet>(list);
		int tailleListe = list.size() - 1;	
		
		for(int i = 0; i <= tailleListe; i++) {
			for (int j = 0; j <= (tailleListe - 1); j++) {
				if(tmp.get(j).getRapport() < tmp.get(i).getRapport()) {
					Collections.swap(tmp, i, j);
				}
			}
		}
		
		return tmp;
	}

}
