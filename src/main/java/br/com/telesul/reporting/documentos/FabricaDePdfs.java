package br.com.telesul.reporting.documentos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import br.com.telesul.reporting.model.AtributosDaPesquisa;
import br.com.telesul.reporting.util.FormatadorDeStrings;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class FabricaDePdfs {
	
	private String file = "C://Users//toliveira//Desktop//FirstPdf.pdf";
	private Font catFont = new Font(Font.FontFamily.HELVETICA, 18,Font.BOLD);
    private Font smallBold = new Font(Font.FontFamily.HELVETICA, 12,Font.BOLD);
    private Font normalBold = new Font(Font.FontFamily.HELVETICA, 8,Font.NORMAL);
    private Font headerFont = new Font(Font.FontFamily.HELVETICA, 8,Font.BOLD);
    
    private Document document;
    
    private String title;
    private String description;
    private List<String[]> dataList;
    private Map<Integer,AtributosDaPesquisa> atributosPesquisaMap;
	
	 public FabricaDePdfs(String title, String description, List<String[]> dataList, Map<Integer,AtributosDaPesquisa> atributosPesquisaMap){
		   headerFont.setColor(BaseColor.WHITE);
		   this.title = title;
		   this.description = description;
		   this.dataList = dataList;
		   this.atributosPesquisaMap = atributosPesquisaMap;
		   validaCamposNulos();
		   createDocument();
	   }
	 
	 private void validaCamposNulos(){
		 if(StringUtils.isBlank(title)){
			 this.title = "";
		 }
		 if(StringUtils.isBlank(description)){
			 this.description = "";
		 }
	 }
	   
	   private void createDocument(){
		   try {
		       	document = new Document(PageSize.A4.rotate());
		        PdfWriter.getInstance(document, new FileOutputStream(file));
		        document.open();
		        addLogo();
		        addHeader();
		        addTable();
		        document.close();
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	   }
	   
	   private void addLogo() throws MalformedURLException, IOException, DocumentException{
		   Image img = Image.getInstance("C:\\Users\\toliveira\\Pictures\\logotelesul.png");
		   document.add(img);
	  }

	   private  void addHeader() throws DocumentException {
	        Paragraph preface = new Paragraph();
	        document.addTitle(title);
	        addEmptyLine(preface, 1);
	        preface.add(new Paragraph("Reporting", catFont));
	        addEmptyLine(preface, 1);
	        preface.add(new Paragraph(title + " " + FormatadorDeStrings.todaysDate(), smallBold));
	        addEmptyLine(preface, 1);
	        preface.add(new Paragraph(description));
	        addEmptyLine(preface, 2);
	        document.add(preface);
	    }
	    
	    private  void addTable() throws DocumentException, ParseException {
	    	PdfPTable table = new PdfPTable(atributosPesquisaMap.size());
	    	table.setWidthPercentage(100); 
	    	
	    	BaseColor lightBlue = new BaseColor(66,66,111);
	    	BaseColor lightGrey = new BaseColor(230,232,250);
	    	
	    	//cabeçalho   	
	    	PdfPCell cel;
	    	for (Map.Entry<Integer, AtributosDaPesquisa> entry : atributosPesquisaMap.entrySet()){
	    		if(entry.getValue().getMostraColuna().equals("mostrar")){
	    			cel = new PdfPCell(new Phrase(entry.getValue().getDummyAlias(), headerFont));
	    			cel.setHorizontalAlignment(Element.ALIGN_CENTER);
	    			cel.setBackgroundColor(lightBlue);
	    			table.addCell(cel);
	    		}
	        }
	        
	        //dados
	        for(int i=0; i< dataList.size(); i++){
	        	String[] arr = dataList.get(i);
	    	   for(int j=0; j< arr.length; j++){
	    		   AtributosDaPesquisa ap = atributosPesquisaMap.get(j);
	    		   if(ap.getMostraColuna().equals("mostrar")){
	    			   cel = new PdfPCell(new Phrase(FormatadorDeStrings.formataColuna(arr[j],ap.getTipo()), normalBold));
		    		   cel.setHorizontalAlignment(Element.ALIGN_CENTER);
		    		   if(i%2 != 0){
		    			   cel.setBackgroundColor(lightGrey);
		    		   }
		    		   table.addCell(cel);
	    		   }
	    		  
	    	   }
	    	}
	        
	        //rodape
	        for (Map.Entry<Integer, AtributosDaPesquisa> entry : atributosPesquisaMap.entrySet()){
	        	if(entry.getValue().getMostraColuna().equals("mostrar")){
	        		cel = new PdfPCell(new Phrase(FormatadorDeStrings.formataColuna(entry.getValue().getResultadoStr(), entry.getValue().getTipo()), headerFont));
	        		cel.setHorizontalAlignment(Element.ALIGN_CENTER);
	        		cel.setBackgroundColor(lightBlue);
	        		table.addCell(cel);
	        	}
	        }
	        
	        
	        document.add(table);
	    }

	    private void addEmptyLine(Paragraph paragraph, int number) {
	        for (int i = 0; i < number; i++) {
	            paragraph.add(new Paragraph(" "));
	        }
	    }
	    
	    
	}


