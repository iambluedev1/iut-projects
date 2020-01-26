package server.types;

import java.util.Date;

public interface Abonne {

	String getNom();
	
	String getPrenom();
	
	Sexe getSexe();
	
	String getEmail();
	
	Integer getAge();
	
	void bannir();
	
	boolean estBannis();
	
	Date getBanJusqua();
	
	int getNumero();
	
}
