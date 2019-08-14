package excelImport;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;



import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class main {

	static String checkStringType(XSSFCell testCell)
	{
		if(testCell.getCellType() == 0)
		{
			return Integer.toString((int)testCell.getNumericCellValue());
		}
		return testCell.getStringCellValue();
	}
	static int checkIntType(XSSFCell testCell)
	{
		if(testCell.getCellType() == 1)
		{
			return Integer.parseInt(testCell.getStringCellValue());
		}
		return (int)testCell.getNumericCellValue();
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		 ArrayList<DataFields> dataList = new ArrayList<DataFields>();
		
		
		FileInputStream thisxls;
		 XSSFWorkbook wb;
		 XSSFSheet sheet;
		 XSSFRow curRow;
 
		 

		thisxls = new FileInputStream("Shurtech_History.xlsx");
		wb = new XSSFWorkbook(thisxls);
		 sheet = wb.getSheetAt(0);
		 curRow = sheet.getRow(1);
		 
		 int count = 1;
		 while(curRow.getRowNum() < sheet.getLastRowNum())
		 {
			 count++;
				String tclient = checkStringType(curRow.getCell(0));
				String tcarrier = checkStringType(curRow.getCell(1));
				String tscac = checkStringType(curRow.getCell(2));
				String tclientMode = checkStringType(curRow.getCell(3));
				String tshipDate = checkStringType(curRow.getCell(4));
				String tfreightNum = checkStringType(curRow.getCell(5));
				String tpaidAmount = checkStringType(curRow.getCell(6));
				String tterms = checkStringType(curRow.getCell(7));
				int tcomClass = checkIntType(curRow.getCell(8));
				int tpieces = checkIntType(curRow.getCell(9));
				int tweight = checkIntType(curRow.getCell(10));
				String tshipCity = checkStringType(curRow.getCell(11));
				String tshipState = checkStringType(curRow.getCell(12));
				String tshipZip = checkStringType(curRow.getCell(13));
				String tcosCity = checkStringType(curRow.getCell(14));
				String tcosState = checkStringType(curRow.getCell(15));
				String tcosZip = checkStringType(curRow.getCell(16));
			 DataFields curField = new DataFields(tclient, tcarrier,tscac,tclientMode,tshipDate,tfreightNum,tpaidAmount,tterms,
					 tcomClass,tpieces,tweight,tshipCity,tshipState,tshipZip,tcosCity,tcosState,tcosZip);
			 dataList.add(curField);
			 curRow = sheet.getRow(count);
		 }

		 for(int i = 0; i < 100; i++)
		 {
		 System.out.println(dataList.get(i).getClient());
		 System.out.println(dataList.get(i).getCarrier());
		 System.out.println(dataList.get(i).getScac());
		 System.out.println(dataList.get(i).getClientMode());
		 System.out.println(dataList.get(i).getShipDate());
		 System.out.println(dataList.get(i).getFreightNum());
		 System.out.println(dataList.get(i).getPaidAmount());
		 System.out.println(dataList.get(i).getTerms());
		 System.out.println(dataList.get(i).getComClass());
		 System.out.println(dataList.get(i).getPieces());
		 System.out.println(dataList.get(i).getWeight());
		 System.out.println(dataList.get(i).getShipCity());
		 System.out.println(dataList.get(i).getShipState());
		 System.out.println(dataList.get(i).getShipZip());
		 System.out.println(dataList.get(i).getCosCity());
		 System.out.println(dataList.get(i).getCosState());
		 System.out.println(dataList.get(i).getCosZip());
		 }
	}

}
