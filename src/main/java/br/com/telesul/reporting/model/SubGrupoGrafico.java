package br.com.telesul.reporting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_subgrupografico")
public class SubGrupoGrafico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "sub_codigo")
	private Long codigo;
	
	@Column(name="sub_nome", length = 35, nullable = false)
	private String nome;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_grupografico_gru_codigo", referencedColumnName = "gru_codigo")	
	private GrupoGrafico grupoGrafico;

	public SubGrupoGrafico(String nome, GrupoGrafico grupoGrafico) {
		super();
		this.nome = nome;
		this.grupoGrafico = grupoGrafico;
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

	public GrupoGrafico getGrupoGrafico() {
		return grupoGrafico;
	}

	public void setGrupoGrafico(GrupoGrafico grupoGrafico) {
		this.grupoGrafico = grupoGrafico;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((grupoGrafico == null) ? 0 : grupoGrafico.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubGrupoGrafico other = (SubGrupoGrafico) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (grupoGrafico == null) {
			if (other.grupoGrafico != null)
				return false;
		} else if (!grupoGrafico.equals(other.grupoGrafico))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SubGrupoGrafico [codigo=" + codigo + ", nome=" + nome + ", grupoGrafico=" + grupoGrafico + "]";
	}
	
	
	
	
	
	
}
