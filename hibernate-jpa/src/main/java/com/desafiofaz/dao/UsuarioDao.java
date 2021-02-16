package com.desafiofaz.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.desafiofaz.model.JPAUtil;
import com.desafiofaz.model.Usuario;

/**
 * Classe encarregada de fornecer uma interface para a manipulação do objeto do usuário dentro do banco de dados
 * @author Andreina Díaz- andreinadc@gmail.com 
 */
public class UsuarioDao {

	EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();
	
	/**
	 * Validar se existe um usuário cadastrado com o email e a senha enviados
	 * @param String email, email do usuário /  String senha, senha do usuario
	 * @return usuario cadastrado, se o objeto não for encontrado o retorno e null
	 */
	public Usuario login(String email, String senha) {
		Usuario usuario = null;
		try {
			Query query = entity
					.createQuery("SELECT u FROM Usuario u WHERE u.email = ?1 AND u.senha = ?2", Usuario.class)
					.setParameter(1, email).setParameter(2, senha);
			List<Usuario> listaUsuario = query.getResultList();
			if (!listaUsuario.isEmpty()) {
				usuario = listaUsuario.get(0);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			entity.close();
		}
		return usuario;
	}

	/**
	 * Armazena um objeto do tipo usuário no banco
	 * @param Usuario usuario, objeto do tipo usuário
	 * @return true, se salvou no banco/ false, se não salvou no banco
	 */
	public boolean guardar(Usuario usuario) {
		boolean guardo= false;
		try {
			if(buscarEmail(usuario.getEmail())) {
				entity.getTransaction().begin();
				entity.persist(usuario);
				entity.getTransaction().commit();
				guardo = true;
			}			
		} catch (Exception e) {
			throw e;
		} finally {
			entity.close();
		}
		return guardo;
	}

	/**
	 * Edita um objeto do tipo usuário no banco
	 * @param Usuario usuario, objeto do tipo usuário
	 * @return Nenhum
	 */
	public void editar(Usuario usuario) {
		try {
			entity.getTransaction().begin();
			entity.merge(usuario);
			entity.getTransaction().commit();
		} catch (Exception e) {
			throw e;
		} finally {
			entity.close();
		}

	}

	/**
	 * Busca o email no banco que o email recebeu
	 * @param String email, email do usuario
	 * @return true, se existe o email no banco/ false, se não existe email no banco
	 */
	public boolean buscarEmail(String email) {
		boolean existe = true;
		try {
			Query query = entity.createQuery("SELECT u FROM Usuario u WHERE u.email = ?1 ", Usuario.class)
					.setParameter(1, email);
			List<Usuario> listaUsuario = query.getResultList();
			if (!listaUsuario.isEmpty()) {
				existe = false;
			}
		} catch (Exception e) {
			throw e;
		} 		
		return existe;
	}

	/**
	 * Busca no banco um usuário por seu id
	 * @param Sint id, id do usuário
	 * @return usuario encontrado, se o objeto não for encontrado estará vazio
	 */
	public Usuario buscar(int id) {
		Usuario usuario = new Usuario();
		try {
			usuario = entity.find(Usuario.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			entity.close();
		}
		return usuario;
	}

	/**
	 * Apaga no banco um usuário por seu id
	 * @param Sint id, id do usuário
	 * @return Nenhum
	 */
	public void eliminar(int id) {
		Usuario usuario = new Usuario();
		try {
			usuario = entity.find(Usuario.class, id);
			entity.getTransaction().begin();
			entity.remove(usuario);
			entity.getTransaction().commit();
		} catch (Exception e) {
			throw e;
		} finally {
			entity.close();
		}

	}

	/**
	 * Pesquise o banco para todos os usuários registrados
	 * @param Nenhum
	 * @return Lista de usuários cadastrados no banco
	 */
	public List<Usuario> Listar() {
		List<Usuario> listaUsuario = new ArrayList<>();
		try {
			Query query = entity.createQuery("from Usuario", Usuario.class);
			listaUsuario = query.getResultList();
		} catch (Exception e) {
			throw e;
		} finally {
			entity.close();
		}
		return listaUsuario;

	}

}