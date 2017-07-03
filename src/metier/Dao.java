package metier;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import modele.Etudiant;
import modele.Formateur;
import modele.Personne;
import modele.Projet;
import modele.SessionFormation;

public class Dao {
	private EntityManager em;

	public Dao() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProjectENS");
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

	public List<Projet> getProjets() {
		String requete = "SELECT g FROM Projet g";
		TypedQuery<Projet> query = em.createQuery(requete, Projet.class);
		List<Projet> liste = query.getResultList();
		return liste;
	}

	// Concernant les sessions de formations
	public SessionFormation getSessionFormation(int id) {
		SessionFormation s1 = null;
		String requete = "SELECT g FROM SessionFormation g WHERE g.idSessionFormation = " + id;
		TypedQuery<SessionFormation> query = em.createQuery(requete, SessionFormation.class);
		if (query.getResultList().size() != 0) {
			s1 = query.getSingleResult();
			System.out.println("Formateur trouvé : " + s1.getIdSessionFormation());
		}
		return s1;
	}

	public List<SessionFormation> getLesSessions() {
		String requete = "SELECT g FROM SessionFormation g";
		TypedQuery<SessionFormation> query = em.createQuery(requete, SessionFormation.class);
		List<SessionFormation> liste = query.getResultList();
		return liste;
	}

	/*
	 * public Formateur verifierFormateur(String nom, String mdp){ //String
	 * requete =
	 * "SELECT g FROM Formateur g JOIN g.personne p ON g.id_formateur = p.id_personne AND g.nom = '"
	 * + nom +"'"; //String requete = "SELECT g FROM Personne g WHERE g.nom = '"
	 * + nom +"'"; String requete = "SELECT g FROM Formateur g WHERE g.nom = '"
	 * + nom +"'"; Formateur i1 = null; TypedQuery<Formateur> query =
	 * em.createQuery(requete, Formateur.class); if(query.getResultList().size()
	 * != 0){ i1 = query.getSingleResult();
	 * System.out.println("Formateur trouvé : " + i1.getNom()); requete +=
	 * " AND g.mot_de_passe = '"+ mdp +"'"; TypedQuery<Personne> query1 =
	 * em.createQuery(requete, Personne.class); if(query1.getResultList().size()
	 * != 0){ System.out.println("Mot de passe ok"); return
	 * query.getSingleResult(); } else{ System.out.println("Mot de passe faux");
	 * return null; } } else{ System.out.println("Personne inconnue"); return
	 * null; } }
	 */
	public Formateur verifierFormateur(String nom, String mdp) {
		//String requetePersonne = "SELECT g FROM Personne g WHERE g.nom = '" + nom + "' AND g.mot_de_passe = '" + mdp + "'";
		String requetePersonne = "SELECT g FROM Personne g WHERE g.nom = '"+nom+ "' AND g.motDePasse = '" + mdp + "'";
		Personne p1 = null;
		TypedQuery<Personne> queryPersonne = em.createQuery(requetePersonne, Personne.class);
		if (queryPersonne.getResultList().size() != 0) {
			p1 = queryPersonne.getSingleResult();
			System.out.println("Persone trouvée : " + p1.getNom());
			String requeteFormateur = "SELECT g FROM Formateur g WHERE g.idFormateur = '" + p1.getIdPersonne() + "'";
			Formateur f1 = null;
			TypedQuery<Formateur> queryFormateur = em.createQuery(requeteFormateur, Formateur.class);
			if(queryFormateur.getResultList().size() != 0){
				f1 = queryFormateur.getSingleResult();
				System.out.println("Formateur trouvé : " + f1.getNom());
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
			System.out.println("Persone trouvée : " + p1.getNom());
			String requeteEtudiant = "SELECT g FROM Etudiant g WHERE g.idEtudiant = '" + p1.getIdPersonne() + "'";
			Etudiant e1 = null;
			TypedQuery<Etudiant> queryEtudiant = em.createQuery(requeteEtudiant, Etudiant.class);
			if(queryEtudiant.getResultList().size() != 0){
				e1 = queryEtudiant.getSingleResult();
				System.out.println("Formateur trouvé : " + e1.getNom());
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
}

