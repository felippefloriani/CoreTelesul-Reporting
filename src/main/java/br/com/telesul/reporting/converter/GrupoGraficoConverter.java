package br.com.telesul.reporting.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import org.apache.commons.lang3.StringUtils;
import javax.faces.convert.Converter;
import br.com.telesul.reporting.dao.GrupoGraficoDAO;
import br.com.telesul.reporting.model.GrupoGrafico;



@FacesConverter(value="GrupoGraficoConverter", forClass=GrupoGrafico.class)  
public class GrupoGraficoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,String value) {
		 if (value != null && StringUtils.isNotBlank(value) && !value.startsWith("-")) {
			try {
				GrupoGrafico gr = new GrupoGraficoDAO().buscar(Long.parseLong(value));  
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
			  	String codigo = ((GrupoGrafico)object).getCodigo().toString(); 
	            return  codigo;
	        }  
	        return null;  
	    }  

	
	
}
