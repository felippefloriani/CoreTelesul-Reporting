package br.com.telesul.core.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.codec.digest.DigestUtils;
import org.omnifaces.util.Messages;
import org.primefaces.model.chart.PieChartModel;

import br.com.telesul.core.dao.UsuarioDAO;
import br.com.telesul.core.model.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioBean  {

	private Usuario usuario;
	private List<Usuario> usuarios;
	
	
	//CHART - CRIA ATRIBUTO C/ import org.primefaces.model.chart.PieChartModel;
	private PieChartModel pieModel;
	
	
	public void salvar() {

		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuario.setSenha(DigestUtils.md5Hex(usuario.getSenha()));
			usuarioDAO.salvar(usuario);
			
			usuario = new Usuario();

			Messages.addGlobalInfo("Usuário salvo com sucesso!");
			//usuarios = usuarioDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar salvar o usuário!");
			erro.printStackTrace();
		}

	}

	public void novo() {
		usuario = new Usuario();
	}

	public void atualizar() {

	}

	@PostConstruct
	public void listar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarios = usuarioDAO.listar();
			
			//CHART - chama método graficar.
			graficar(usuarios);
			
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar os usuários");
			erro.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {

		try {

			usuario = (Usuario) evento.getComponent().getAttributes()
					.get("usuarioSelecionado");

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioDAO.excluir(usuario);

			usuarios = usuarioDAO.listar();

			Messages.addGlobalInfo("Usuário excluído com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o usuário!");
			erro.printStackTrace();
		}

	}
	
	
	public void editar(){
		
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			//Criptografia MD5
			usuario.setSenha(DigestUtils.md5Hex(usuario.getSenha()));
			Messages.addGlobalInfo("Usuário editado com sucesso!");
			usuarioDAO.editar(usuario);
			
		} catch (RuntimeException ex) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar editar o usuário!");
			ex.printStackTrace();
		}
	}

	/* Método sem o algoritmo de criptografia
	public void editar(ActionEvent evento) {

		try {

			usuario = (Usuario) evento.getComponent().getAttributes()
					.get("usuarioSelecionado");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar editar o usuário!");
			erro.printStackTrace();
		}

	}
	*/
	
	//CHART - cria método graficar
	public void graficar(List<Usuario> lista){
		
		pieModel = new PieChartModel();
		

		for(Usuario usu : lista){
			pieModel.set(usu.getNome(), usu.getCodigo());
		}
		
		pieModel.setTitle("Usuários");
		pieModel.setLegendPosition("c");
		pieModel.setFill(false);
		pieModel.setShowDataLabels(true);
		pieModel.setDiameter(150);
		
	}
	
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}

}
