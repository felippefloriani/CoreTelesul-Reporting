package br.com.telesul.reporting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_relatorio")
@NamedQueries({
	 @NamedQuery(name = "Relatorio.listar", query = "SELECT relatorio FROM Relatorio relatorio")
	})
public class Relatorio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "rel_codigo")
	private Long codigo;
	
	@Column(name="rel_nome", length = 35, nullable = false)
	private String nome;
	
	@Column(name="rel_descricao", length = 50, nullable = false)
	private String descricao;
	
	@Column(name="rel_query", nullable = false)
	private String query;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_subgruporelatorio_sub_codigo", referencedColumnName = "sub_codigo")	
	private SubGrupoRelatorio subGrupoRelatorio;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_conexao_codigo", referencedColumnName = "codigo")	
	private Conexao conexao;


	
	
	public Relatorio(){
		
	}
	
	
	public Relatorio(SubGrupoRelatorio subGrupoRelatorio,Conexao conexao, String nome, String descricao, String query) {
		this.subGrupoRelatorio = subGrupoRelatorio;
		this.conexao = conexao;
		this.nome = nome;
		this.descricao = descricao;
		this.query = query;
	}


	public Long getCodigo() {
		return codigo;
	}


	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}

	
	

	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getQuery() {
		return query;
	}


	public void setQuery(String query) {
		this.query = query;
	}


	public SubGrupoRelatorio getSubGrupoRelatorio() {
		return subGrupoRelatorio;
	}


	public void setSubGrupoRelatorio(SubGrupoRelatorio subGrupoRelatorio) {
		this.subGrupoRelatorio = subGrupoRelatorio;
	}


	


	public Conexao getConexao() {
		return conexao;
	}


	public void setConexao(Conexao conexao) {
		this.conexao = conexao;
	}
	
	


}
