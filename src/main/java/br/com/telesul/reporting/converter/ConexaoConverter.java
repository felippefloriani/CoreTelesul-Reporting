package br.com.telesul.reporting.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import br.com.telesul.core.dao.ConexaoLocalDAO;
import br.com.telesul.reporting.model.Conexao;

@FacesConverter(value = "ConexaoConverter", forClass=Conexao.class)
public class ConexaoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,String value) {
		 if (value != null && StringUtils.isNotBlank(value)&& !value.startsWith("-")) {
			try {
				Conexao con = new ConexaoLocalDAO().buscarPorCodigo(Long.parseLong(value));  
				return con;  
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	    }  
	    return null;  
	}  
	
	@Override
	public String getAsString(FacesContext context, UIComponent component,Object object) {
		 if (object != null && StringUtils.isNotBlank(object.toString())) {
			  	String codigo = ((Conexao)object).getCodigo().toString(); 
	            return  codigo;
	        }  
	        return null;  
	    }  

}