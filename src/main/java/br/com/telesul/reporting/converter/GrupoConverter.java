package br.com.telesul.reporting.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import br.com.telesul.reporting.dao.GrupoRelatorioDAO;
import br.com.telesul.reporting.model.GrupoRelatorio;

@FacesConverter(value="GrupoConverter", forClass=GrupoRelatorio.class)  
public class GrupoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,String value) {
		 if (value != null && StringUtils.isNotBlank(value) && !value.startsWith("-")) {
			try {
				GrupoRelatorio gr = new GrupoRelatorioDAO().buscar(Long.parseLong(value));  
				return gr;  
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	    }  
	    return null;  
	}  
	
	@Override
	public String getAsString(FacesContext context, UIComponent component,Object object) {
		  if (object != null && StringUtils.isNotBlank(object.toString())) {
			  	String codigo = ((GrupoRelatorio)object).getCodigo().toString(); 
	            return  codigo;
	        }  
	        return null;  
	    }  

}
