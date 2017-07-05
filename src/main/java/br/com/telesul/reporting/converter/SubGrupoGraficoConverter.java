package br.com.telesul.reporting.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import br.com.telesul.reporting.dao.SubGrupoGraficoDAO;
import br.com.telesul.reporting.model.SubGrupoGrafico;


@FacesConverter(value="SubGrupoGraficoConverter", forClass=SubGrupoGrafico.class)  
public class SubGrupoGraficoConverter implements Converter{
	
	@Override  
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {  
		 if (value != null && StringUtils.isNotBlank(value) && !value.startsWith("-")) {
            try {
                return new SubGrupoGraficoDAO().buscar(Long.parseLong(value));    
            } catch (Exception e) {
                e.printStackTrace();
            }
        }  
        return null;  
    }  
    @Override  
    public String getAsString(FacesContext context, UIComponent component, Object object) throws ConverterException {  
    	 if (object != null && StringUtils.isNotBlank(object.toString())) {
            return ((SubGrupoGrafico)object).getCodigo().toString();  
        }  
        return null;  
    }  
	
}
