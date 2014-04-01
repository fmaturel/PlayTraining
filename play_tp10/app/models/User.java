package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

/**
 * User entity managed by Ebean
 */
@Entity
public class User {

	@Id
	@Constraints.Required
	@Formats.NonEmpty
	public String email;

	@Constraints.Required
	public String name;

	@Constraints.Required
	public String password;

	/**
	 * Récupération d'un Utilisateur par son email
	 * 
	 * @param email email de l'Utilisateur
	 * @return l'Utilisateur correspondant à l'email
	 */
	public static User findByEmail(Long email) {
		return JPA.em().find(User.class, email);
	}

	/**
	 * Récupération de tous les Utilisateurs
	 * 
	 * @return l'ensemble des Utilisateurs
	 */
	public static List<User> findAll() {
		TypedQuery<User> query = JPA.em().createQuery("FROM User", User.class);
		return query.getResultList();
	}

	/**
	 * Authentification de l'Utilisateur
	 */
	public static User authenticate(String email, String password) {
		TypedQuery<User> query = JPA.em().createQuery(//
				"FROM User u where u.email = :email and u.password = :password", User.class)//
				.setParameter("email", email)//
				.setParameter("password", password);
		List<User> result = query.getResultList();
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + ", password=" + "****" + "]";
	}

}
