package br.com.telesul.reporting.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import br.com.telesul.reporting.dao.SubGrupoRelatorioDAO;
import br.com.telesul.reporting.model.SubGrupoRelatorio;

@FacesConverter(value="SubGrupoConverter", forClass=SubGrupoRelatorio.class)  
public class SubGrupoConverter implements Converter{
	
	@Override  
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {  
		 if (value != null && StringUtils.isNotBlank(value) && !value.startsWith("-")) {
            try {
                return new SubGrupoRelatorioDAO().buscar(Long.parseLong(value));    
            } catch (Exception e) {
                e.printStackTrace();
            }
        }  
        return null;  
    }  
    @Override  
    public String getAsString(FacesContext context, UIComponent component, Object object) throws ConverterException {  
    	 if (object != null && StringUtils.isNotBlank(object.toString())) {
            return ((SubGrupoRelatorio)object).getCodigo().toString();  
        }  
        return null;  
    }  
}


