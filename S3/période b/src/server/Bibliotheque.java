package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import server.documents.AbstractDocument;
import server.documents.Membre;
import server.exceptions.DocumentNotFoundException;
import server.exceptions.MembreNotFoundException;
import server.mail.MailSender;
import server.types.Abonne;
import server.types.Document;
import server.types.EtatDocument;
import server.types.Serializable;
import server.utils.DocUtils;

public class Bibliotheque {

	private static Bibliotheque instance;
	private Map<Integer, Document> documents;
	private Map<Integer, Membre> membres;
	private List<Class<? extends AbstractDocument>> types;
	private Map<Integer, List<Abonne>> listeNotifs;
	private ExecutorService mailsPool;
	private Timer timer;
	private Map<Integer, Date> empruntsActifs;

	private static final String populateFolderPath = "populate";

	private Bibliotheque() {
		this.documents = new HashMap<Integer, Document>();
		this.types = new ArrayList<Class<? extends AbstractDocument>>();
		this.membres = new HashMap<Integer, Membre>();
		this.listeNotifs = new HashMap<Integer, List<Abonne>>();
		this.mailsPool = Executors.newFixedThreadPool(3);  
		this.timer = new Timer();
		this.empruntsActifs = new HashMap<Integer, Date>();
		
		this.timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());            
				calendar.add(Calendar.DAY_OF_YEAR, -14);
				Date d = calendar.getTime();
				
				for(Entry<Integer, Date> emprunt : Bibliotheque.getInstance().empruntsActifs.entrySet()) {
					if(d.compareTo(emprunt.getValue()) > 0) {
						try {
							Abonne abo = DocUtils.getDocumentPossesseur(Bibliotheque.getInstance().getDocument(emprunt.getKey()));
							if(!abo.estBannis()) {
								abo.bannir();
								System.out.println("Banissement automatique de " + abo.getNom() + " " + abo.getPrenom() + " car il a un retard de plus de deux semaines.");
							}
						} catch (DocumentNotFoundException e) {}
					}
				}
			}
		}, 0, 1000*60*60*24);
	}

	public static Bibliotheque getInstance() {
		if (instance == null)
			instance = new Bibliotheque();

		return instance;
	}

	/**
	 * Fonction obligatoire qui va permettre de définir les types de document qu'on souhaite traiter
	 * @param types
	 */
	public void setTypes(List<Class<? extends AbstractDocument>> types) {
		this.types = new ArrayList<Class<? extends AbstractDocument>>(types);
	}

	/*
	 * Fonction qui va permettre de charger des données suivants les types définis et voulu 
	 */
	public void populate() {
		for (Class<? extends Serializable> type : this.types) {
			this.populate(type);
		}
		
		this.populate(Membre.class);

		System.out.println("Loaded " + this.documents.size() + " documents");
	}
	
	/**
	 * Fonction qui va charger le fichier portant le nom de le classe dans le dossier populate, lire chaque ligne et appeller 
	 * pour chaque ligne la fonction serialize de cette classe.
	 * @param type
	 */
	private void populate(Class<? extends Serializable> type) {
		String typeName = type.getSimpleName();

		try {
			Serializable serializer = type.newInstance();

			try {
				Scanner in = new Scanner(
						new FileInputStream(populateFolderPath + "/" + typeName.toLowerCase() + ".txt"));
				while (in.hasNext()) {
					String line = in.nextLine();
					if (line.isEmpty() == false) {
						if (type == Membre.class) {
							Membre tmp = (Membre) serializer.serialize(line);
							if (!this.membres.containsKey(tmp.getNumero())) {
								this.membres.put(tmp.getNumero(), tmp);
							} else {
								System.err.println("Duplication numéro " + tmp.getNumero());
							}
						} else {
							Document tmp = (Document) serializer.serialize(line);
							if (!this.documents.containsKey(tmp.numero())) {
								this.documents.put(tmp.numero(), tmp);
							} else {
								System.err.println("Duplication numéro " + tmp.numero());
							}
						}
					}
				}
				in.close();
			} catch (FileNotFoundException e) {
				System.out.println("Impossible d'ouvrir le fichier");
				e.printStackTrace();
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Récupère un membre en fonction de son numéro
	 * @param numero
	 * @return membre
	 * @throws MembreNotFoundException si le membre n'existe pas
	 */
	public Membre getMembre(int numero) throws MembreNotFoundException {
		if(this.membres.containsKey(numero))
			return this.membres.get(numero);
		
		throw new MembreNotFoundException(numero);
	}
	
	/**
	 * Récupère un document en fonction de son numéro
	 * @param numero
	 * @return Document
	 * @throws DocumentNotFoundException si le document n'existe pas
	 */
	public Document getDocument(int numero) throws DocumentNotFoundException {
		if(this.documents.containsKey(numero))
			return this.documents.get(numero);
		
		throw new DocumentNotFoundException(numero);
	}
	
	/**
	 * Vérifier l'existence d'un document
	 * @param numero
	 * @return true si le document existe
	 */
	public boolean documentExist(int numero) {
		return this.documents.containsKey(numero);
	}
	
	/**
	 * Vérifier l'existence d'un membre
	 * @param numero
	 * @return true si le membre existe
	 */
	public boolean membreExist(int numero) {
		return this.membres.containsKey(numero);
	}
	
	/**
	 * Récupère la liste des types définis et voulu
	 * @return List<Class<? extends AbstractDocument>>
	 */
	public List<Class<? extends AbstractDocument>> getTypes(){
		return this.types;
	}
	
	/**
	 * Compte le nombre de document d'un certain type enregistré dans la bibliothèque
	 * @param type
	 * @return Integer
	 */
	public Integer countDocumentsOfType(Class<? extends Serializable> type) {
		return (int) this.documents.values().stream().filter((doc) -> doc.getClass() == type).count();
	}
	
	/**
	 * Récupère la liste de tous les documents d'un certain types
	 * @param type
	 * @return List<Document>
	 */
	public List<Document> getDocumentsOfTypes(Class<? extends Serializable> type){
		return this.documents.values().stream().filter((doc) -> doc.getClass() == type).collect(Collectors.toList());
	}
	
	/**
	 * Fonction qui va être appellée lorsque l'état d'un document est modifié. 
	 * La fonction envera les emails de notifications aux membres qui ont indiqué vouloir être notifié de la disponibilité d'un document
	 * @param doc
	 */
	public void etatDocumentModifié(Document doc) {
		System.out.println("L'état du document " + DocUtils.getDocumentTitre(doc) + " à été modifié, il est désormais " + DocUtils.getDocumentEtat(doc));
	
		if(this.listeNotifs.containsKey(doc.numero()) && DocUtils.getDocumentEtat(doc) == EtatDocument.DISPONIBLE) {
			List<Abonne> notif = this.listeNotifs.get(doc.numero());
			if(!notif.isEmpty()) {
				System.out.println(notif.size() + " personnes sont intéréssées par ce document");
				notif.forEach(abo -> mailsPool.execute(new MailSender(abo, doc)));
				this.listeNotifs.remove(doc.numero());
			}
		}
		
		if(DocUtils.getDocumentEtat(doc) == EtatDocument.EMPRUNTER) {
			this.empruntsActifs.put(doc.numero(), new Date());
		}
		
		if(DocUtils.getDocumentEtat(doc) == EtatDocument.DISPONIBLE) {
			if(this.empruntsActifs.containsKey(doc.numero())) {
				this.empruntsActifs.remove(doc.numero());
			}
		}
	}
	
	/**
	 * Ajoute l'abonné dans la liste de notification associé au numéro du document
	 * @param doc
	 * @param abo
	 */
	public void ajouterNotification(Integer doc, Abonne abo) {
		if(this.listeNotifs.containsKey(doc)) {
			List<Abonne> tmp = this.listeNotifs.get(doc);
			tmp.add(abo);
			this.listeNotifs.replace(doc, tmp);
		}else {
			List<Abonne> tmp = new ArrayList<Abonne>();
			tmp.add(abo);
			this.listeNotifs.put(doc, tmp);
		}
	}
	
	/**
	 * Macro pour enregistrer l'execution de la tache 
	 * @param task
	 * @param delay
	 */
	public void schedule(TimerTask task, long delay) {
		System.out.println("Schedule task");
		this.timer.schedule(task, delay);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Bibliothèque :\n");
		
		sb.append("Membres enregistrés (" + membres.size() + ") :\n");
		this.membres.forEach((id, membre) -> sb.append("- " + membre + "\n"));
		
		sb.append("Documents enregistrés (" + documents.size() + ") :\n");
		this.documents.forEach((id, doc) -> sb.append("- " + doc + "\n"));

		return sb.toString();
	}
	
	/**
	 * Récupère tous les emprunts actifs
	 * @return Map<Integer, Date> 
	 */
	public Map<Integer, Date> getEmpruntsActifs(){
		return this.empruntsActifs;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.mailsPool.shutdown();
		this.timer.cancel();
	}
}
