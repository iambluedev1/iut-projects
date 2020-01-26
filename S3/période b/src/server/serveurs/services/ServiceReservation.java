package server.serveurs.services;

import java.io.IOException;
import java.net.Socket;
import java.util.TimerTask;

import server.Bibliotheque;
import server.documents.Membre;
import server.exceptions.DocumentNotFoundException;
import server.exceptions.EmpruntException;
import server.exceptions.MembreNotFoundException;
import server.exceptions.RetourException;
import server.types.Document;
import server.types.EtatDocument;
import server.types.Sexe;
import server.utils.Condition;
import server.utils.DocUtils;

public class ServiceReservation extends Service {

	public ServiceReservation(Socket socket) {
		super(socket);
	}

	@Override
	public void exec() throws IOException {
		Bibliotheque b = Bibliotheque.getInstance();
		final ServiceReservation instance = this;

		this.ecrire("######################################################");
		this.ecrire("Bienvenue sur le service de reservation de la bibliothèque");
		this.ecrire(this.centerString(54, "QLFBliblothèque"));
		this.ecrire("######################################################");
		this.sauterUneLigne();
		
		this.ecrire("Liste des catégories de documents disponibles en rayon :");

		b.getTypes().forEach((type) -> this
				.ecrire("- Taper " + (b.getTypes().indexOf(type) + 1) + " pour la catégorie " + type.getSimpleName()));

		String typeDocStr = "";

		do {
			if (typeDocStr == "")
				this.poserQuestion("Merci d'indiquer la catégorie de document que vous voulez reserver");
			else
				this.poserQuestion("Merci d'indiquer une catégorie correcte");

			typeDocStr = this.lire();
			if (typeDocStr == null)
				continue;
			if (typeDocStr.isEmpty())
				continue;
		} while (!this.check(typeDocStr, new Condition<Boolean>() {
			@Override
			public Boolean check(Object t) {
				if (!instance.isNumeric((String) t)) {
					return false;
				}

				Integer type = Integer.valueOf((String) t);
				Integer countTypes = Bibliotheque.getInstance().getTypes().size();

				if (type > 0 && type < countTypes + 1)
					return true;
				return false;
			}
		}));

		Integer typeDoc = Integer.valueOf(typeDocStr) - 1;

		this.ecrire("Vous avez choisit la catégorie " + b.getTypes().get(typeDoc).getSimpleName() + " qui contient "
				+ b.countDocumentsOfType(b.getTypes().get(typeDoc)) + " documents");

		this.poserQuestion(
				"Voulez vous afficher la liste des documents de cette catégorie ou directement indiquer la référence du document que vous souhaitez reserver ? (oui/non)");

		String rep1Str = this.lire();
		boolean afficherDocuments = this.formatBoolean(rep1Str);

		if (afficherDocuments) {
			b.getDocumentsOfTypes(b.getTypes().get(0)).forEach((doc) -> this
					.ecrire("- Taper " + (doc.numero()) + " pour le document " + DocUtils.getDocumentTitre(doc)));
		}

		String refDocStr = "";

		do {
			if (refDocStr.isEmpty())
				this.poserQuestion("Indiquer la référence du document que vous voulez reserver");
			else
				this.poserQuestion("Le code que vous avez rentré ne correspond a aucun document ou est incorrect");

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

		String refAboStr = "";

		do {
			if (refAboStr.isEmpty())
				this.poserQuestion("Pour finir, merci d'indiquer votre code d'indentification");
			else
				this.poserQuestion("Ce code ne correspond a aucun utilisateur ou est incorrect");

			refAboStr = this.lire();
		} while (!this.check(refAboStr, new Condition<Boolean>() {
			@Override
			public Boolean check(Object t) {
				if (!instance.isNumeric((String) t)) {
					return false;
				}

				Integer num = Integer.parseInt((String) t);
				return Bibliotheque.getInstance().membreExist(num);
			}
		}));

		Membre membre = null;
		Document doc = null;

		try {
			doc = b.getDocument(Integer.parseInt(refDocStr));
		} catch (NumberFormatException | DocumentNotFoundException e1) {
			this.ecrire("Un problème est survenue.");
			e1.printStackTrace();
		}

		try {
			membre = b.getMembre(Integer.parseInt(refAboStr));
		} catch (NumberFormatException | MembreNotFoundException e) {
			this.ecrire("Un problème est survenue.");
			e.printStackTrace();
		}

		String prenom = membre.getPrenom().substring(0, 1).toUpperCase() + membre.getPrenom().substring(1);

		this.sauterUneLigne();

		this.ecrire("Récapitulatif de votre réservation :");
		this.ecrire("Vous êtes : "
				+ ((membre.getSexe() == Sexe.HOMME) ? "Monsieur" : ((membre.getSexe() == Sexe.FEMME) ? "Madame" : ""))
				+ " " + prenom + " " + membre.getNom().toUpperCase());
		this.ecrire("Vous voulez reserver: " + DocUtils.getDocumentTitre(doc) + "(réf:" + doc.numero()
				+ ") dans la catégorie " + b.getTypes().get(typeDoc).getSimpleName());

		this.poserQuestion("Voulez-vous poursuivre ? (oui/non)");

		String rep2Str = this.lire();
		boolean poursuivre = this.formatBoolean(rep2Str);

		if (!poursuivre) {
			this.sauterUneLigne();
			this.ecrire("A très bientot");
			this.terminer();
			return;
		}

		try {
			doc.reserver(membre);
			this.ecrire("Vous avez réserver le document " + DocUtils.getDocumentTitre(doc) + " avec succès !");
			this.ecrire("A noter : la réservation de ce document ne dure que 2 heures.");
			final Document tmpDoc = doc;
			b.schedule(new TimerTask() {
				@Override
				public void run() {
					if(DocUtils.getDocumentEtat(tmpDoc) == EtatDocument.RESERVER) {
						System.out.println("Le document " + DocUtils.getDocumentTitre(tmpDoc) + " n'a pas été emprunter dans le temps impartis, annulation de la reservation en cours...");
						try {
							tmpDoc.retour();
						} catch (RetourException e) {}
					}
				}
			}, 1000*60);
		} catch (EmpruntException e) {
			this.ecrire(e.getMessage());
			
			if(!e.getMessage().startsWith("Vous n'avez pas l'age requis")) {
				this.poserQuestion("Voulez vous êtes notifié par email lorsque le livre sera de nouveau disponible en rayon ? (oui/non)");
				String rep3Str = this.lire();
				boolean etreNotifier = this.formatBoolean(rep3Str);

				if (etreNotifier) {
					this.ecrire("Parfait ! Vous recevrez un email lorsque le livre sera de nouveau disponible.");
					b.ajouterNotification(doc.numero(), membre);
				} else {
					this.ecrire("Vous avez choisit de ne pas être notifié.");
				}
			}
		}
		
		this.sauterUneLigne();
		this.ecrire("A très bientot");
		this.terminer();
	}
}
