package server.documents;

import server.Bibliotheque;
import server.exceptions.EmpruntException;
import server.exceptions.RetourException;
import server.types.Abonne;
import server.types.Document;
import server.types.EtatDocument;
import server.types.Serializable;

public abstract class AbstractDocument extends Serializable implements Document {

	protected int numero;
	protected String titre;
	private Abonne possesseur;
	private EtatDocument etat;
	private Object moniteur = new Object();
	
	public AbstractDocument(int numero, String titre) {
		this.numero = numero;
		this.titre = titre;
		this.possesseur = null;
		this.etat = EtatDocument.DISPONIBLE;
	}
	
	/**
	 * Constructeur utilisé pour pouvoir appeller la fonction serialize des sous classes
	 * Ce constructeur ne doit être utilisé que dans ce but.
	 */
	public AbstractDocument() {}
	
	@Override
	public int numero() {
		return this.numero;
	}
	
	@Override
	public void reserver(Abonne ab) throws EmpruntException {
		synchronized (moniteur) {
			if(this.etat == EtatDocument.DISPONIBLE) {
				this.possesseur = ab;
				this.etat = EtatDocument.RESERVER;
				Bibliotheque.getInstance().etatDocumentModifié(this);
				return;
			}
			
			throw new EmpruntException("Ce document n'est pas disponible actuellement.");
		}
	}

	@Override
	public void emprunter(Abonne ab) throws EmpruntException {
		synchronized (moniteur) {
			if(this.etat == EtatDocument.RESERVER && this.possesseur.getNumero() == ab.getNumero()) {
				this.etat = EtatDocument.EMPRUNTER;
				Bibliotheque.getInstance().etatDocumentModifié(this);
				return;
			}
			
			if(this.etat == EtatDocument.DISPONIBLE) {
				this.etat = EtatDocument.EMPRUNTER;
				Bibliotheque.getInstance().etatDocumentModifié(this);
				this.possesseur = ab;
				return;
			}
			
			throw new EmpruntException("Ce document n'est pas disponible actuellement.");
		}
	}

	
	@Override
	public void retour() throws RetourException {
		synchronized (moniteur) {
			if(this.etat == EtatDocument.EMPRUNTER) {
				this.etat = EtatDocument.DISPONIBLE;
				Bibliotheque.getInstance().etatDocumentModifié(this);
				this.possesseur = null;
				return;
			}
			
			if(this.etat == EtatDocument.RESERVER) {
				this.etat = EtatDocument.DISPONIBLE;
				Bibliotheque.getInstance().etatDocumentModifié(this);
				this.possesseur = null;
				return;
			}
			
			throw new RetourException("Ce document est toujours dans nos rayons vous ne pouvez donc pas le retourner.");
		}
	}
}
