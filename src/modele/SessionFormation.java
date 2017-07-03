package modele;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the session_formation database table.
 * 
 */
@Entity
@Table(name="session_formation")
@NamedQuery(name="SessionFormation.findAll", query="SELECT s FROM SessionFormation s")
public class SessionFormation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_session_formation")
	private int idSessionFormation;

	//bi-directional many-to-many association to Etudiant
	@ManyToMany
	@JoinTable(
		name="membre_session_formation"
		, joinColumns={
			@JoinColumn(name="id_session_formation")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_personne")
			}
		)
	private List<Etudiant> etudiants;

	//bi-directional many-to-one association to Projet
	@OneToMany(mappedBy="sessionFormation")
	private List<Projet> projets;

	public SessionFormation() {
	}

	public int getIdSessionFormation() {
		return this.idSessionFormation;
	}

	public void setIdSessionFormation(int idSessionFormation) {
		this.idSessionFormation = idSessionFormation;
	}

	public List<Etudiant> getEtudiants() {
		return this.etudiants;
	}

	public void setEtudiants(List<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}

	public List<Projet> getProjets() {
		return this.projets;
	}

	public void setProjets(List<Projet> projets) {
		this.projets = projets;
	}

	public Projet addProjet(Projet projet) {
		getProjets().add(projet);
		projet.setSessionFormation(this);

		return projet;
	}

	public Projet removeProjet(Projet projet) {
		getProjets().remove(projet);
		projet.setSessionFormation(null);

		return projet;
	}

}