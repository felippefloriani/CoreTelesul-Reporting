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
@Table(name = "tbl_subgruporelatorio")
public class SubGrupoRelatorio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "sub_codigo")
	private Long codigo;
	
	@Column(name="sub_nome", length = 35, nullable = false)
	private String nome;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_gruporelatorio_gru_codigo", referencedColumnName = "gru_codigo")	
	private GrupoRelatorio grupoRelatorio;
	
	
	public SubGrupoRelatorio(String nome, GrupoRelatorio grupoRelatorio){
		this.nome = nome;
		this.grupoRelatorio = grupoRelatorio;
	}
	
	public SubGrupoRelatorio(){
		
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

	public GrupoRelatorio getGrupoRelatorio() {
		return grupoRelatorio;
	}

	public void setGrupoRelatorio(GrupoRelatorio grupoRelatorio) {
		this.grupoRelatorio = grupoRelatorio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((grupoRelatorio == null) ? 0 : grupoRelatorio.hashCode());
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
		SubGrupoRelatorio other = (SubGrupoRelatorio) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (grupoRelatorio == null) {
			if (other.grupoRelatorio != null)
				return false;
		} else if (!grupoRelatorio.equals(other.grupoRelatorio))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	

}
