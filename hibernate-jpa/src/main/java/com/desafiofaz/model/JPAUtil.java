package com.desafiofaz.model;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe que permite uma conexão persistente com o banco de dados
 * @author Andreina Díaz- andreinadc@gmail.com 
 */
public class JPAUtil {
	
	private static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE";
	private static EntityManagerFactory factory;

	public static EntityManagerFactory getEntityManagerFactory() {
		if (factory==null) {
			factory=Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		return factory;				
	}
	
	public static void shutdown() {
		if (factory!=null) {
			factory.close();
		}		
	}

}

