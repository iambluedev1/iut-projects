package systeme;

import java.util.List;

public interface Methode<T> {

	String r�cup�rerNomMethode();
	List<T> r�soudre(Sac<T> sac);
	
}
