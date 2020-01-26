package systeme;

import java.util.List;

public interface Sac<T> {

	void ajouterItem(T t);

	void r�soudre();

	void d�finirMethodeResolution(Methode<T> m);

	float r�cup�rerPoidsMaximal();
	
	List<T> r�cup�rerListe();

}
