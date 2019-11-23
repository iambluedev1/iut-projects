package systeme;

import java.util.List;

public interface Methode<T> {

	String récupérerNomMethode();
	List<T> résoudre(Sac<T> sac);
	
}
