package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.jpa.JPA;

/**
 * Représente un Film disposant d'un titre, un genre, un nombre d’exemplaire de DVD disponible, un acteur principal et
 * une liste d'acteurs secondaires
 */
@Entity
public class Film {

	/**
	 * Id technique du Film
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	/**
	 * Le tire du Film
	 */
	@Required
	@MinLength(4)
	@MaxLength(256)
	public String titre;

	/**
	 * Le genre du Film Du type {@link Genre}
	 */
	@Required
	public Genre genre;

	/**
	 * Le nombre d'exemplaires du Film disponibles dans l'inventaire
	 */
	@Min(1)
	@Max(10000)
	public int nombreExemplaire;

	/**
	 * L'acteur principal du Film
	 */
	@Valid
	@Required
	@ManyToOne(cascade = CascadeType.ALL)
	public Acteur acteurPrincipal;

	/**
	 * La liste des acteurs secondaires du Film
	 */
	@Valid
	@NotEmpty
	@ManyToMany(cascade = CascadeType.ALL)
	public List<Acteur> acteurSecondaires = new ArrayList<Acteur>();

	/**
	 * Constructeur publique sans paramètre d'un Film. Incrémente un id en mémoire pour les besoins du tp.
	 */
	public Film() {
		super();
	}

	/**
	 * Constructeur publique avec le titre du Film. Incrémente un id en mémoire pour les besoins du tp.
	 */
	public Film(String titre) {
		super();
		this.titre = titre;
	}

	public void update(Long id) {
		Film filmToUpdate = findById(id);
		filmToUpdate.titre = this.titre;
		filmToUpdate.genre = this.genre;
		filmToUpdate.nombreExemplaire = this.nombreExemplaire;
		filmToUpdate.acteurPrincipal = this.acteurPrincipal;
		filmToUpdate.acteurSecondaires = this.acteurSecondaires;
	}

	/**
	 * Méthode permettant d'afficher dans les logs le contenu du Film
	 */
	@Override
	public String toString() {
		return "Film [id=" + id + ", titre=" + titre + ", genre=" + genre + ", nombreExemplaire=" + nombreExemplaire
				+ ", acteurPrincipal=" + acteurPrincipal + ", acteurSecondaires=" + acteurSecondaires + "]";
	}

	/**
	 * Helper permettant de créer un Film
	 */
	public static class FilmBuilder {

		private Film film = new Film();

		public Film build() {
			return film;
		}

		public FilmBuilder withTitre(String titre) {
			film.titre = titre;
			return this;
		}

		public FilmBuilder withGenre(Genre genre) {
			film.genre = genre;
			return this;
		}

		public FilmBuilder withNombreExemplaire(int nombreExemplaire) {
			film.nombreExemplaire = nombreExemplaire;
			return this;
		}

		public FilmBuilder withActeurPrincipal(Acteur acteurPrincipal) {
			film.acteurPrincipal = acteurPrincipal;
			return this;
		}

		public FilmBuilder withActeurSecondaire(Acteur acteurSecondaire) {
			film.acteurSecondaires.add(acteurSecondaire);
			return this;
		}
	}

	/*
	 * ############################################# JPA#############################################
	 */

	/**
	 * Récupération d'un Film par son identifiant
	 * 
	 * @param id identifiant du Film
	 * @return le Film correspondant à l'identifiant
	 */
	public static Film findById(Long id) {
		return JPA.em().find(Film.class, id);
	}

	/**
	 * Récupération de tous les Films
	 * 
	 * @return l'ensemble des Films
	 */
	public static List<Film> findAll() {
		TypedQuery<Film> query = JPA.em().createQuery("FROM Film", Film.class);
		return query.getResultList();
	}

	/**
	 * Sauvegarde ce Film.
	 */
	public void save() {
		JPA.em().persist(this);
	}

	/**
	 * Supprime ce Film.
	 */
	public void delete() {
		JPA.em().remove(this);
	}

}
