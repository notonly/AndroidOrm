package jpa.features;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import jpa.JPAUtil;

import org.hibernate.Session;

import domain.Person;

public class JpaJdbc {
	public static void main(String[] args) throws SQLException {
		EntityManager em = JPAUtil.getEntityManager();
		Person goldie = new Person();
		goldie.setFirstName("Goldie");
		em.getTransaction().begin();
		em.persist(goldie);
		em.getTransaction().commit();
		em.close();
		
		em = JPAUtil.getEntityManager();
		System.out.println("Using EM: " + em.getClass().getName());
		@SuppressWarnings("deprecation")
		Connection conn = em.unwrap(Session.class).connection();
		PreparedStatement st = conn.prepareStatement("select * from person");
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			String firstname = rs.getString("firstname");
			String lastname = rs.getString("lastname");
			int id = rs.getInt("id");
			System.out.printf("Name %s, species %d, individual %d%n",
					firstname, lastname, id);
		}
	}
}