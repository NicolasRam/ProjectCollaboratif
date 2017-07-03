package beans;

import java.util.List;

import metier.Dao;
import modele.Etudiant;
import modele.Formateur;
import modele.Projet;
import modele.SessionFormation;

public class LoginBeanProjet {
	private Dao dao;

	private String nom;
	private String mdp;
	private Formateur formateur;
	private Etudiant etudiant;
	private String type;

	private List<Projet> listeProjets;
	private Projet leProjet;

	private List<SessionFormation> lesSessions;
	private SessionFormation sessionFormation;
	private int idSession;

	public LoginBeanProjet() {
		dao = new Dao();
		setLesSessions(dao.getLesSessions());
		setListeProjets(dao.getProjets());
		// leProjet = new Projet();
		sessionFormation = new SessionFormation();
		// formateur = new Formateur();
	}

	/*
	 * public String validationMembre() { if
	 * (typeOfPersonne.equals("formateur")) {
	 * setFormateur(dao.verifierFormateur(nom, mdp)); if (formateur != null) {
	 * listeProjets = dao.getProjetsParFormateur(formateur); leProjet = new
	 * Projet(); return "creationprojet"; } else return "inconnu"; } else{
	 * 
	 * } }
	 */
	public String validationMembre() {
		if (type.equals("formateur")) {
			setFormateur(dao.verifierFormateur(nom, mdp));
			if (formateur != null) {
				listeProjets = dao.getProjetsParFormateur(formateur);
				leProjet = new Projet();
				return "creationprojet";
			} else
				return "inconnu";
		} else {
			setEtudiant(dao.verifierEtudiant(nom, mdp));
			if(etudiant != null){
				return "accueiletudiant";
			}
			else
				return "inconnu";
		}
	}

	public String creerProjet() {
		System.out.println("Session n° " + sessionFormation.getIdSessionFormation() + ", Sujet du projet : "
				+ leProjet.getSujet());
		dao.creerProjet(leProjet, formateur, sessionFormation);
		return "succescreationprojet";
	}

	/// Getters and setters pour un projet
	public Projet getLeProjet() {
		return leProjet;
	}

	public void setLeProjet(Projet leProjet) {
		this.leProjet = leProjet;
	}

	// Getter et setter pour une liste de projets
	public List<Projet> getListeProjets() {
		return listeProjets;
	}

	public void setListeProjets(List<Projet> listeProjets) {
		this.listeProjets = listeProjets;
	}

	// Getters et setters pour un formateur
	public Formateur getFormateur() {
		return formateur;
	}

	public void setFormateur(Formateur formateur) {
		this.formateur = formateur;
	}
	
	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	// Getters et setters pour une session
	public SessionFormation getSessionFormation() {
		return sessionFormation;
	}

	public void setSessionFormation(SessionFormation sessionFormation) {
		this.sessionFormation = sessionFormation;
	}

	public int getIdSession() {
		return idSession;
	}

	public void setIdSession(int idSession) {
		this.idSession = idSession;
	}

	// Getter et setter pour une liste de sessions
	public List<SessionFormation> getLesSessions() {
		return lesSessions;
	}

	public void setLesSessions(List<SessionFormation> listeFormations) {
		this.lesSessions = listeFormations;
	}

	// Getter et setter pour le type de personne (via le radiobutton)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

