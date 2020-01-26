package server.serveurs.services;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

import server.Bibliotheque;
import server.exceptions.DocumentNotFoundException;
import server.exceptions.RetourException;
import server.types.Abonne;
import server.types.Document;
import server.utils.Condition;
import server.utils.DocUtils;

public class ServiceRetour extends Service {

	public ServiceRetour(Socket socket) {
		super(socket);
	}

	@Override
	public void exec() throws IOException {
		Bibliotheque b = Bibliotheque.getInstance();
		final ServiceRetour instance = this;

		this.ecrire("######################################################");
		this.ecrire("Bienvenue sur le service de retour de la biblioth�que");
		this.ecrire(this.centerString(54, "QLFBlibloth�que"));
		this.ecrire("######################################################");
		this.sauterUneLigne();
		
		String refDocStr = "";

		do {
			if (refDocStr.isEmpty())
				this.poserQuestion("Indiquer la r�f�rence du document que vous voulez retourner");
			else
				this.poserQuestion("Le code que vous avez rentr� ne correspond a aucun document ou est incorrect");

			refDocStr = this.lire();
		} while (!this.check(refDocStr, new Condition<Boolean>() {
			@Override
			public Boolean check(Object t) {
				if (!instance.isNumeric((String) t)) {
					return false;
				}

				Integer num = Integer.parseInt((String) t);
				return Bibliotheque.getInstance().documentExist(num);
			}
		}));
		
		Document doc = null;

		try {
			doc = b.getDocument(Integer.parseInt(refDocStr));
		} catch (NumberFormatException | DocumentNotFoundException e1) {
			this.ecrire("Un probl�me est survenue.");
			e1.printStackTrace();
		}
		
		Abonne m = DocUtils.getDocumentPossesseur(doc);
		
		try {
			doc.retour();
			this.ecrire("Le document " + DocUtils.getDocumentTitre(doc) + " � bien �t� retourn�.");
		} catch (RetourException e) {
			this.ecrire(e.getMessage());
		}
		
		Random r = new Random();
		int valeur = 1 + r.nextInt(2 - 1);
		
		if(valeur == 1) {
			this.ecrire("Oups. Apr�s v�rification du document, le document est s�v�rement d�grad�. Vous �tes d�sormais bannis.");
			m.bannir();
			this.ecrire("Vous �tes bannis jusqu'au " + m.getBanJusqua().toString() + " vous ne pourrez plus emprunter durant cette p�riode.");
		}
		
		this.sauterUneLigne();
		this.ecrire("A tr�s bientot");
		this.terminer();
	}
}
