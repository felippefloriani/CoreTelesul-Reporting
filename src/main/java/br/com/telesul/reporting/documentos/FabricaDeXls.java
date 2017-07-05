package br.com.telesul.reporting.documentos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import br.com.telesul.reporting.model.AtributosDaPesquisa;

public class FabricaDeXls{
	
	private String path;
	private FileOutputStream fos;
	private HSSFWorkbook workbook;
	private HSSFSheet worksheet;
	private HSSFRow row;
 	

	private int linha = 0;
	private String title;
	private String description;
	private List<String[]> dataList;
	private Map<Integer, AtributosDaPesquisa> atributosPesquisaMap;

	public FabricaDeXls(String title, String description, List<String[]> dataList, Map<Integer,AtributosDaPesquisa> atributosPesquisaMap) throws IOException {
		this.title = title;
		this.description = description;
		this.dataList = dataList;
		this.atributosPesquisaMap = atributosPesquisaMap;
		
		this.initialize();
	}
	
	private void initialize() throws IOException{
		this.createDocument();
		this.createTitle();
		this.createHeaderAndFooter('h');
		this.createData();
		this.createHeaderAndFooter('f');
		this.closeDocument();
	}
	
	 private void createDocument(){
		 workbook = new HSSFWorkbook();
		 worksheet = workbook.createSheet("Reporting");
	 }
	 
	 private void createTitle(){
		HSSFCell cell;
		row = worksheet.createRow(linha++);
	 	cell = row.createCell(0);
	 	cell.setCellValue(title);
	 	row = worksheet.createRow(linha++);
	 	cell = row.createCell(0);
	 	cell.setCellValue(description);
	 }
	 
	 private void createHeaderAndFooter(char headerOrFooter){
		HSSFCell cell;
	 	HSSFCellStyle cellStyle;
	 	
	 	cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		cellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	 	
 		
 		row = worksheet.createRow(linha++);
 		for(int i=0; i< atributosPesquisaMap.size(); i++){
	 		cell = row.createCell(i);
	 		AtributosDaPesquisa ap = atributosPesquisaMap.get(i);
	 		if(headerOrFooter == 'h'){
	 			cell.setCellValue(ap.getAlias());
	 		}else{
	 			cell.setCellValue(ap.getResultadoStr());
	 		}
	 	}
	}
	 
	 private void createData(){
		 HSSFCell cell;
		for(int i=0; i< dataList.size(); i++){
			row = worksheet.createRow(linha++);
			 String[] arr = dataList.get(i);
			 for(int j=0; j<arr.length; j++){
				 cell = row.createCell(i); 
				 cell.setCellValue(arr[j]);
			 }
		 }
	}
	 
	 private void closeDocument() throws IOException{
		 fos = new FileOutputStream(path);
		 workbook.write(fos);
	 }
}
