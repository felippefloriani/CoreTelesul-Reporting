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
@Table(name = "tbl_grafico")
@NamedQueries({
	 @NamedQuery(name = "Grafico.listar", query = "SELECT grafico FROM Grafico grafico")
	})
public class Grafico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "gra_codigo")
	private Long codigo;
	
	@Column(name="gra_nome", length = 35, nullable = false)
	private String nome;
	
	@Column(name="gra_descricao", length = 50, nullable = false)
	private String descricao;
	
	@Column(name="gra_query", length = 255, nullable = false)
	private String query;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_subgrupografico_sub_codigo", referencedColumnName = "sub_codigo")	
	private SubGrupoGrafico subGrupoGrafico;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_conexao_codigo", referencedColumnName = "codigo")	
	private Conexao conexao;
	
	public Grafico(){
		
	}

	public Grafico(Long codigo, String nome, String descricao, String query, SubGrupoGrafico subGrupoGrafico,
			Conexao conexao) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.descricao = descricao;
		this.query = query;
		this.subGrupoGrafico = subGrupoGrafico;
		this.conexao = conexao;
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

	public SubGrupoGrafico getSubGrupoGrafico() {
		return subGrupoGrafico;
	}

	public void setSubGrupoGrafico(SubGrupoGrafico subGrupoGrafico) {
		this.subGrupoGrafico = subGrupoGrafico;
	}

	public Conexao getConexao() {
		return conexao;
	}

	public void setConexao(Conexao conexao) {
		this.conexao = conexao;
	}

	@Override
	public String toString() {
		return "Grafico [codigo=" + codigo + ", nome=" + nome + ", descricao=" + descricao + ", query=" + query
				+ ", subGrupoGrafico=" + subGrupoGrafico + ", conexao=" + conexao + "]";
	}


}
