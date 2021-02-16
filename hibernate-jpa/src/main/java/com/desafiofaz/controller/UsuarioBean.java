package com.desafiofaz.controller;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import javax.faces.bean.SessionScoped;

import com.desafiofaz.dao.UsuarioDao;
import com.desafiofaz.model.Usuario;

/**
 * Classe de interface entre a classe DAO e a interface do usuário
 * @author Andreina Díaz- andreinadc@gmail.com 
 */
@ManagedBean(name = "usuarioBean")
@SessionScoped
public class UsuarioBean {
	public String email;
	public String senha;
	
	public UsuarioBean() {
		super();

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * Gerenciar a ação do botão de <login> na tela <index.xhtml>
	 * @param Nenhum
	 * @return Caminho da próxima tela para carregar <listarUsuario.xhtml>
	 */
	public String login() {
		UsuarioDao usuarioDAO = new UsuarioDao();
		Usuario usuario = new Usuario();
		usuario = usuarioDAO.login(email, senha);
		if (usuario == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", " Usuário não encontrado!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return null;
		} else {
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessionMap.put("usuarioLogin", usuario);
			System.out.println(usuario);
			return "/faces/listarUsuario.xhtml";
		}

	}


	/**
	 * Gerenciar a ação do botão de <Novo> na tela <index.xhtml>
	 * @param Nenhum
	 * @return Caminho da próxima tela para carregar <novoUsuario.xhtml>
	 */
	public String nuevo() {
		Usuario usuario = new Usuario();
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("usuario", usuario);
		return "/faces/novoUsuario.xhtml";
	}


	/**
	 * Gerenciar a ação do botão de <Guardar> na tela <nuevoUsuario.xhtml>
	 * @param Objeto de usuário a ser armazenado
	 * @return Caminho da próxima tela para carregar <index.xhtml>
	 */
	public String guardar(Usuario usuario) {
		UsuarioDao usuarioDAO = new UsuarioDao();		
		if(!usuarioDAO.guardar(usuario)) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", " Email cadastrado!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return null;
		}
		else {
			return "/faces/index.xhtml";
		}
	}

	/**
	 * Gerenciar os dados necesarios para carregar a tela <listarUsuario.xhtml>
	 * @param id do registro para editar
	 * @return Lista de usuários registrados
	 */
	public List<Usuario> ObtenerUsuario() {
		UsuarioDao usuarioDAO = new UsuarioDao();
		return usuarioDAO.Listar();
	}

	/**
	 * Gerenciar a ação do botão de <Editar> na tela <listarUsuario.xhtml>
	 * @param id do registro para editar
	 * @return Caminho da próxima tela para carregar <index.xhtml>
	 */
	public String editarUsuario(int id) {
		UsuarioDao usuarioDAO = new UsuarioDao();
		Usuario usuario = new Usuario();
		usuario = usuarioDAO.buscar(id);
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("usuario", usuario);
		return "/faces/editarUsuario.xhtml";
	}

	/**
	 * Gerenciar a ação do botão de <Actualizar> na tela <editarUsuario.xhtml>
	 * @param Objeto de usuário a ser atualizado
	 * @return Caminho da próxima tela para carregar <index.xhtml>
	 */
	public String actualizar(Usuario usuario) {
		UsuarioDao usuarioDAO = new UsuarioDao();
		usuarioDAO.editar(usuario);
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("usuarioLogin", usuario);
		return "/faces/index.xhtml";
	}

	/**
	 * Gerenciar a ação do botão de <Eliminar> na tela <listarUsuario.xhtml>
	 * @param id do registro para excluir
	 * @return Caminho da próxima tela para carregar <index.xhtml>
	 */
	public String eliminar(int id) {
		UsuarioDao usuarioDAO = new UsuarioDao();
		usuarioDAO.eliminar(id);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Usuário excluído com sucesso!");
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return "/faces/index.xhtml";
	}

}