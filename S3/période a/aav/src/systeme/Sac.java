package systeme;

import java.util.List;

public interface Sac<T> {

	void ajouterItem(T t);

	void résoudre();

	void définirMethodeResolution(Methode<T> m);

	float récupérerPoidsMaximal();
	
	List<T> récupérerListe();

}
