package metier;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import modele.Equipe;
import modele.Etudiant;
import modele.Formateur;
import modele.Personne;
import modele.Projet;
import modele.SessionFormation;

public class Dao {
	private EntityManager em;

	public Dao() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProjectCollaboratif");
		em = factory.createEntityManager();
	}

	// Concernant les projets
	public boolean creerProjet(Projet projet, Formateur formateur, SessionFormation session) {
		projet.setFormateur(formateur);
		projet.setSessionFormation(session);
		em.getTransaction().begin();
		em.persist(projet);
		em.getTransaction().commit();
		return true;
	}

	public List<Projet> getProjetsParFormateur(Formateur formateur) {
		List<Projet> lesProjets = null;
		Query q = em.createQuery("SELECT s FROM Projet s WHERE s.formateur = :var");
		q.setParameter("var", formateur);

		lesProjets = q.getResultList();

		return lesProjets;
	}
	
	public List<Equipe> getEquipesParEtudiant(Etudiant etudiant) {
		List<Equipe> lesEquipes = null;
		Query q = em.createQuery("SELECT e FROM Equipe e WHERE e.etudiant = :var");
		q.setParameter("var", etudiant);

		lesEquipes = q.getResultList();

		return lesEquipes;
	}

	public List<Projet> getProjets() {
		String requete = "SELECT g FROM Projet g";
		TypedQuery<Projet> query = em.createQuery(requete, Projet.class);
		List<Projet> liste = query.getResultList();
		return liste;
	}
	
	public List<Equipe> getEquipes() {
		String requete = "SELECT e FROM Equipe e";
		TypedQuery<Equipe> query = em.createQuery(requete, Equipe.class);
		List<Equipe> liste = query.getResultList();
		return liste;
	}

	// Concernant les sessions de formations
	public SessionFormation getSessionFormation(int id) {
		SessionFormation s1 = null;
		String requete = "SELECT g FROM SessionFormation g WHERE g.idSessionFormation = " + id;
		TypedQuery<SessionFormation> query = em.createQuery(requete, SessionFormation.class);
		if (query.getResultList().size() != 0) {
			s1 = query.getSingleResult();
			System.out.println("Formateur trouv� : " + s1.getIdSessionFormation());
		}
		return s1;
	}

	public List<SessionFormation> getLesSessions() {
		String requete = "SELECT g FROM SessionFormation g";
		TypedQuery<SessionFormation> query = em.createQuery(requete, SessionFormation.class);
		List<SessionFormation> liste = query.getResultList();
		return liste;
	}

	public Formateur verifierFormateur(String nom, String mdp) {
		//String requetePersonne = "SELECT g FROM Personne g WHERE g.nom = '" + nom + "' AND g.mot_de_passe = '" + mdp + "'";
		String requetePersonne = "SELECT g FROM Personne g WHERE g.nom = '"+nom+ "' AND g.motDePasse = '" + mdp + "'";
		Personne p1 = null;
		TypedQuery<Personne> queryPersonne = em.createQuery(requetePersonne, Personne.class);
		if (queryPersonne.getResultList().size() != 0) {
			p1 = queryPersonne.getSingleResult();
			System.out.println("Persone trouv�e : " + p1.getNom());
			String requeteFormateur = "SELECT g FROM Formateur g WHERE g.idFormateur = '" + p1.getIdPersonne() + "'";
			Formateur f1 = null;
			TypedQuery<Formateur> queryFormateur = em.createQuery(requeteFormateur, Formateur.class);
			if(queryFormateur.getResultList().size() != 0){
				f1 = queryFormateur.getSingleResult();
				System.out.println("Formateur trouv� : " + f1.getNom());
				return queryFormateur.getSingleResult();
			}
			else{
				return null;
			}
		} else {
			System.out.println("Personne inconnue");
			return null;
		}
	}
	public Etudiant verifierEtudiant(String nom, String mdp) {
		String requetePersonne = "SELECT g FROM Personne g WHERE g.nom = '" + nom + "' AND g.motDePasse = '" + mdp + "'";
		Personne p1 = null;
		TypedQuery<Personne> queryPersonne = em.createQuery(requetePersonne, Personne.class);
		if (queryPersonne.getResultList().size() != 0) {
			p1 = queryPersonne.getSingleResult();
			System.out.println("Persone trouv�e : " + p1.getNom());
			String requeteEtudiant = "SELECT g FROM Etudiant g WHERE g.idEtudiant = '" + p1.getIdPersonne() + "'";
			Etudiant e1 = null;
			TypedQuery<Etudiant> queryEtudiant = em.createQuery(requeteEtudiant, Etudiant.class);
			if(queryEtudiant.getResultList().size() != 0){
				e1 = queryEtudiant.getSingleResult();
				System.out.println("Formateur trouv� : " + e1.getNom());
				return queryEtudiant.getSingleResult();
			}
			else{
				return null;
			}
		} else {
			System.out.println("Personne inconnue");
			return null;
		}
	}
	
	//On v�rifier si la personne n'est pas d�j� pr�sente en BDD (via le nom et l'email)
	public boolean presencePersonne(String nom, String email){
		String requetePersonne = "SELECT p FROM Personne p WHERE p.nom = '" + nom 
				+ "' OR p.email = '" + email + "'";
		TypedQuery<Personne> queryPersonne = em.createQuery(requetePersonne, Personne.class);
		if(queryPersonne.getResultList().size() != 0){
			return true;
		}
		return false;
	}
	
	//On enregistre la personne
	public boolean creerPersonne(Personne personne){
		em.getTransaction().begin();
		em.persist(personne);
		em.getTransaction().commit();
		return true;
	}
}

