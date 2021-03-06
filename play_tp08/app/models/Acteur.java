package models;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

/**
 * Représente un Acteur disposant d'un nom et d'un prénom
 */
public class Acteur {

    /**
     * Le nom de l'Acteur
     */
    @Required
    @MinLength(1)
    @MaxLength(256)
    public String nom;

    /**
     * Le prénom de l'Acteur
     */
    @Required
    @MinLength(1)
    @MaxLength(256)
    public String prenom;

    /**
     * Constructeur publique sans paramètre d'un Acteur
     */
    public Acteur() {
        super();
    }

    /**
     * Constructeur publique avec le nom et le prénom de l'Acteur
     */
    public Acteur(String nom, String prenom) {
        super();
        this.nom = nom;
        this.prenom = prenom;
    }

    /**
     * Méthode permettant d'afficher dans les logs le contenu de l'Acteur
     */
    @Override
    public String toString() {
        return "Acteur [nom=" + nom + ", prenom=" + prenom + "]";
    }

}
