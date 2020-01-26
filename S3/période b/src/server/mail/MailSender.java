package server.mail;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.MessagingException;

import server.types.Abonne;
import server.types.Document;
import server.types.Sexe;
import server.utils.DocUtils;

public class MailSender implements Runnable {
	
	private Abonne membre;
	private Document doc;
	
	public MailSender(Abonne membre, Document doc) {
		this.membre = membre;
		this.doc = doc;
	}

	@Override
	public void run() {
		Map<String, Object> infosDocs = DocUtils.getDocumentInfos(this.doc);
		
		System.out.println("Sending email to " + this.membre.getEmail() + " for " + infosDocs.get("type") + " : " + infosDocs.get("titre"));
		
		String prenom = this.membre.getPrenom().substring(0, 1).toUpperCase() + this.membre.getPrenom().substring(1);
		
		StringBuilder sbDocs = new StringBuilder();
		
		sbDocs.append("\n\nInformations du document : \n");
		infosDocs.forEach((name, value) -> {
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			sbDocs.append("- " + name + ": " + value + "\n");
		});
		
		try {
			(new MailBuilder())
			.setAuth("", "")
			.setFrom("")
			.setTo(this.membre.getEmail())
			.setHost("smtp.gmail.com", 587)
			.setSubject("Le " + infosDocs.get("type") + " '" + infosDocs.get("titre") + "' est disponible en rayon")
			.setContent("Nous avons une très bonne nouvelle pour vous " + ((this.membre.getSexe() == Sexe.HOMME) ? "Monsieur" : ((this.membre.getSexe() == Sexe.FEMME) ? "Madame" : "")) + " " + prenom + " " + this.membre.getNom().toUpperCase() +  " !\n\nLe " + infosDocs.get("type") + " '" + infosDocs.get("titre") + "' est disponible en rayon. \nVous pouvez donc le réserver via notre application ou directement venir l'emprunter sur place. " + sbDocs.toString() + "\n\nTrès bonne journée ! ")
			.send();
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}
	}

}
