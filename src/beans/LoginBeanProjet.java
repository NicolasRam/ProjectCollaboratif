package beans;

import java.util.List;

import metier.Dao;
import modele.Etudiant;
import modele.Formateur;
import modele.Personne;
import modele.Projet;
import modele.SessionFormation;

public class LoginBeanProjet {
	private Dao dao;

	private String nom;
	private String mdp;
	private Formateur formateur;
	private Etudiant etudiant;
	private String type;

	// Variables ajoutées pour l'enregistrement d'une personne
	private String email;
	private String prenom;
	private String tel;
	// Les champs pour vérifier le mdp et l'email (je savais pas comment faire
	// autrement)
	private String confirmationEmail;
	private String confirmationMdp;
	private Personne personne;
	private String titre;

	private List<Projet> listeProjets;
	private Projet leProjet;

	private List<SessionFormation> lesSessions;
	private SessionFormation sessionFormation;
	private int idSession;

	public LoginBeanProjet() {
		dao = new Dao();
		setLesSessions(dao.getLesSessions());
		setListeProjets(dao.getProjets());
		sessionFormation = new SessionFormation();
		leProjet = new Projet();
	}

	public String validationMembre() {
		if (type.equals("formateur")) {
			setFormateur(dao.verifierFormateur(nom, mdp));
			if (formateur != null) {
				listeProjets = dao.getProjetsParFormateur(formateur);
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
	public String enregistrementPersonne() {
		// Si la personne est déjà présente en BDD, olala pas bon
		if (dao.presencePersonne(nom, email)) {
			System.out.println("Une personne avec le nom ou le même email est déjà présant dans la BDD");
			return "enregistrement_personne";
		}
		// Si la confirmation de mdp ou d'email n'est pas bonne, oulala grosse
		// Mauduit
		if (!email.equals(confirmationEmail) || !mdp.equals(confirmationMdp)) {
			System.out.println("Confirmation mdp ou email pas conformes");
			return "enregistrement_personne";
		}
		if (type.equals("formateur")) {
			formateur = new Formateur(nom, prenom, tel, email, mdp, "f");
			dao.creerPersonne(formateur);
			System.out.println("Bienvenue nouveau formateur !!!");
			return "accueil_formateur";
		}
		if (type.equals("etudiant")) {
			etudiant = new Etudiant(nom, prenom, tel, email, mdp, "e");
			dao.creerPersonne(etudiant);
			System.out.println("Bienvenue nouvel étudiant !!!");
			return "accueil_etudiant";
		}
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	// Juste un getter, pas besoin du setter
	public String getConfirmationEmail() {
		return confirmationEmail;
	}

	public void setConfirmationEmail(String confirmationEmail) {
		this.confirmationEmail = confirmationEmail;
	}

	public String getConfirmationMdp() {
		return confirmationMdp;
	}

	public void setConfirmationMdp(String confirmationMdp) {
		this.confirmationMdp = confirmationMdp;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
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

	//Méthode pour retrouver les infos du projet en question, marche pas
	public String returnProjet() {
		//Le titre vaut toujours null, je n'arrive pas à le récupérer du commandLink
		System.out.println(titre);
		return "espace_projet";
	}
}
