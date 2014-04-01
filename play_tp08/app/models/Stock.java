package models;

import java.util.HashSet;

import models.Film.FilmBuilder;
import play.Logger;

/**
 * Représente le Stock (inventaire) de {@link Film} de notre boutique en ligne
 */
public class Stock {

    private static HashSet<Film> listeDesFilms = new HashSet<Film>();

    /**
     * Récupère la liste de {@link Film} du Stock
     * 
     * @return la liste de {@link Film} du Stock
     */
    public static HashSet<Film> getListeDesFilms() {
        return listeDesFilms;
    }

    /**
     * Récupère un {@link Film} du Stock
     * 
     * @param id l'identifiant technique du {@link Film} à récupérer
     * @return le film correspondant à l'identifiant technique
     */
    public static Film getFilm(long id) {
        for (Film film : listeDesFilms) {
            if (id == film.id) {
                return film;
            }
        }
        return null;
    }

    /**
     * Supprime un {@link Film} du Stock
     * 
     * @param id l'identifiant technique du {@link Film} à supprimer
     * @return le film qui vient d'être supprimé
     */
    public static Film removeFilm(Long id) {
        Film film = getFilm(id);
        listeDesFilms.remove(film);
        return film;
    }

    /**
     * Enregistre en mémoire plusieurs films pour les besoins des tps.
     * 
     * @return la liste des films enregistrés en mémoire
     */
    public static HashSet<Film> populate() {

        Film film1 = new FilmBuilder().withTitre("La grande vadrouille") //
                .withGenre(Genre.COMEDIE) //
                .withNombreExemplaire(10) //
                .withActeurPrincipal(new Acteur("De Funes", "Louis")) //
                .withActeurSecondaire(new Acteur("Bourvil", "André")) //
                .build();

        Film film2 = new FilmBuilder().withTitre("Men In Black III") //
                .withGenre(Genre.COMEDIE) //
                .withNombreExemplaire(50) //
                .withActeurPrincipal(new Acteur("Smith", "Will")) //
                .withActeurSecondaire(new Acteur("Jones", "Tommy Lee")) //
                .withActeurSecondaire(new Acteur("Brolin", "Josh")) //
                .build();

        Film film3 = new FilmBuilder().withTitre("Gravity") //
                .withGenre(Genre.ACTION) //
                .withNombreExemplaire(30) //
                .withActeurPrincipal(new Acteur("Bullock", "Sandra")) //
                .withActeurSecondaire(new Acteur("Clooney", "Georges")) //
                .build();

        HashSet<Film> liste = Stock.getListeDesFilms();

        liste.add(film1);
        liste.add(film2);
        liste.add(film3);

        Logger.info("Films ajoutés: {} et {} et {}", film1, film2, film3);

        return liste;
    }

}
