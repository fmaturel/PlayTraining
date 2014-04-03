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

import com.google.common.collect.ImmutableListMultimap;

import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.jpa.JPA;
import utils.PerfLogger;
import controllers.Frontoffice.Order;
import controllers.Frontoffice.Order.Item;

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
    @Min(0)
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
     * Récupération de tous les Films par critère
     * 
     * @return l'ensemble des Films
     */
    public static List<Film> findBy(String genre, int maxResult) {

        play.Logger.info("Demande du genre [{}] et max [{}]", genre, maxResult);

        if (genre.equals("tous")) {
            List<Film> list = findAll();
            return list.subList(0, Math.min(list.size(), maxResult));
        }

        TypedQuery<Film> query = JPA.em().createQuery("FROM Film where genre = :genre", Film.class);
        query.setParameter("genre", Genre.valueOf(genre));
        query.setMaxResults(maxResult);

        return query.getResultList();
    }

    public static List<Film> search(String q) {
        TypedQuery<Film> query = JPA.em().createQuery("FROM Film f where f.titre like :q", Film.class);
        query.setParameter("q", "%" + q + "%");
        return query.getResultList();
    }

    public static void order(Order order) {
        TypedQuery<Film> query = JPA.em().createQuery("FROM Film f where f.id in :ids", Film.class);
        ImmutableListMultimap<Long, Item> itemsByIds = order.getItemsByIds();

        query.setParameter("ids", itemsByIds.keys());
        List<Film> films = query.getResultList();

        for (Film film : films) {
            int nbFilmOrdered = 0;
            for (Item item : itemsByIds.get(film.id)) {
                nbFilmOrdered += item.nb;
            }
            int stock = film.nombreExemplaire - nbFilmOrdered;
            film.nombreExemplaire = stock < 0 ? 0 : stock;
            film.save();
        }
    }

    /**
     * Sauvegarde ce Film.
     */
    public void save() {
        //Server.talk("Le film " + titre + " est disponible pour la vente!");

        PerfLogger perfSave = PerfLogger.start("database.film.save[{}]", this);
        JPA.em().persist(this);
        perfSave.stop();
    }

    /**
     * Supprime ce Film.
     */
    public void delete() {
        JPA.em().remove(this);
    }

}
