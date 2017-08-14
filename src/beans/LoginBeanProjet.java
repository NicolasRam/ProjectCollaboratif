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
				return "accueil_formateur";
			} else
				return "inconnu";
		} else {
			setEtudiant(dao.verifierEtudiant(nom, mdp));
			if (etudiant != null) {
				return "accueil_etudiant";
			} else
				return "inconnu";
		}
	}
	
	// Pour le moment, retourne directement à l'accueil après l'enregistrement
	// Il faut voir comment on gère l'histoire du mail et de la validation
	public String enregistrementEtudiant(){
		return "accueil";
	}

	public String creerProjet() {
		System.out.println("Session n° " + sessionFormation.getIdSessionFormation() + ", Sujet du projet : "
				+ leProjet.getSujet());
		for (SessionFormation s : lesSessions) {
			if (s.getIdSessionFormation() == sessionFormation.getIdSessionFormation()) {
				dao.creerProjet(leProjet, formateur, s);
				return "succes_creation_projet";
			}
		}
		return "erreur_creation_projet";
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

	public SessionFormation getSessionConverter(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("no id provided");
		}
		for (SessionFormation session : lesSessions) {
			if (id.equals(session.getIdSessionFormation())) {
				return session;
			}
		}
		return null;
	}
}
