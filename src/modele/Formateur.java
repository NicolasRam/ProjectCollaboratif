package modele;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the formateur database table.
 * 
 */
@Entity
@PrimaryKeyJoinColumn(name = "id_formateur")
@DiscriminatorValue("f")
@MappedSuperclass
@NamedQuery(name = "Formateur.findAll", query = "SELECT f FROM Formateur f")
public class Formateur extends Personne implements Serializable {
	private static final long serialVersionUID = 1L;

	// @Id
	// @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_formateur")
	private int idFormateur;

	// bi-directional one-to-one association to Personne
	@OneToOne
	@JoinColumn(name = "id_formateur", insertable = false, updatable = false, referencedColumnName = "id_personne")
	private Personne personne;

	// bi-directional many-to-one association to Projet
	@OneToMany(mappedBy = "formateur")
	private List<Projet> projets;

	public Formateur() {
	}

	public int getIdFormateur() {
		return this.idFormateur;
	}

	public void setIdFormateur(int idFormateur) {
		this.idFormateur = idFormateur;
	}

	public Personne getPersonne() {
		return this.personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public List<Projet> getProjets() {
		return this.projets;
	}

	public void setProjets(List<Projet> projets) {
		this.projets = projets;
	}

	public Projet addProjet(Projet projet) {
		getProjets().add(projet);
		projet.setFormateur(this);

		return projet;
	}

	public Projet removeProjet(Projet projet) {
		getProjets().remove(projet);
		projet.setFormateur(null);

		return projet;
	}

}