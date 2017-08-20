package modele;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the etudiant database table.
 * 
 */
@Entity
@PrimaryKeyJoinColumn(name = "id_etudiant")
@DiscriminatorValue("e")
@MappedSuperclass
@NamedQuery(name = "Etudiant.findAll", query = "SELECT e FROM Etudiant e")
public class Etudiant extends Personne implements Serializable {
	private static final long serialVersionUID = 1L;

	// @Id
	// @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_etudiant")
	private int idEtudiant;

	// bi-directional many-to-one association to Equipe
	@OneToMany(mappedBy = "etudiant")
	private List<Equipe> equipes1;

	// bi-directional one-to-one association to Personne
	@OneToOne
	@JoinColumn(name = "id_etudiant", insertable = false, updatable = false, referencedColumnName = "id_personne")
	private Personne personne;

	// bi-directional many-to-many association to Equipe
	@ManyToMany
	@JoinTable(name = "membre_equipe", joinColumns = { @JoinColumn(name = "id_personne") }, inverseJoinColumns = {
			@JoinColumn(name = "id_equipe") })
	private List<Equipe> equipes2;

	// bi-directional many-to-many association to SessionFormation
	@ManyToMany(mappedBy = "etudiants")
	private List<SessionFormation> sessionFormations;

	public Etudiant() {
	}

	//Pour créer notre nouvel Étudiant
	public Etudiant(String nom, String prenom, String tel, String email, String mdp, String type) {
		super(nom, prenom, tel, email, mdp, type);
	}
	
	public int getIdEtudiant() {
		return this.idEtudiant;
	}

	public void setIdEtudiant(int idEtudiant) {
		this.idEtudiant = idEtudiant;
	}

	public List<Equipe> getEquipes1() {
		return this.equipes1;
	}

	public void setEquipes1(List<Equipe> equipes1) {
		this.equipes1 = equipes1;
	}

	public Equipe addEquipes1(Equipe equipes1) {
		getEquipes1().add(equipes1);
		equipes1.setEtudiant(this);

		return equipes1;
	}

	public Equipe removeEquipes1(Equipe equipes1) {
		getEquipes1().remove(equipes1);
		equipes1.setEtudiant(null);

		return equipes1;
	}

	public Personne getPersonne() {
		return this.personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public List<Equipe> getEquipes2() {
		return this.equipes2;
	}

	public void setEquipes2(List<Equipe> equipes2) {
		this.equipes2 = equipes2;
	}

	public List<SessionFormation> getSessionFormations() {
		return this.sessionFormations;
	}

	public void setSessionFormations(List<SessionFormation> sessionFormations) {
		this.sessionFormations = sessionFormations;
	}

}