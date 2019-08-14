package edu.sru.thangiah.zeus.excelimport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelImport {
	private DataFields data;
	
	public ExcelImport(String filename, int problemType) throws IOException {
		// TODO Auto-generated method stub
		
		System.out.println(filename);
		
		FileInputStream thisxls;
		XSSFWorkbook wb;
		XSSFSheet sheet;
		XSSFRow curRow;
		
		data = new DataFields();
		switch(problemType)
		{

	    //type = 0 (MDVRP)
	    //     = 1 (PTSP)
	    //     = 2 (PVRP)
		//     = 3 (VRPTW)
		//     = 4 (MDVRPTW)
		case 0:
			// VRP
			
			break;
		case 1:
			// VRPTW
			// This is what we should work on for now
			boolean failFlag = false;
			try
			{
				thisxls = new FileInputStream(filename);
				try
				{
					wb = new XSSFWorkbook(thisxls);

					sheet = wb.getSheetAt(0);
					
					int rowOffsetBeforeNodes = 0;
					int rowCount = 0;
					int depotCount = 0;
					int nodeCount = 0;
					
					curRow = sheet.getRow(rowCount);
					while(curRow.getRowNum() < sheet.getLastRowNum())
					{
						curRow = sheet.getRow(rowCount);
						switch(rowCount)
						{ // Account for special cases in rows
						case 0:
							// Column label row; skip
							
							rowOffsetBeforeNodes++;
							break;
						case 1:
							// Problem data row
							//data.setDepotX(1, checkIntType(curRow.getCell(0)));
							//data.setDepotY(1, checkIntType(curRow.getCell(1)));
							data.setNodes(checkIntType(curRow.getCell(2)));
							nodeCount = data.getNodes();
							data.setDepots(checkIntType(curRow.getCell(3)));
							depotCount = data.getDepots();
							data.setCapacity(checkIntType(curRow.getCell(4)));
							data.setDuration(checkIntType(curRow.getCell(5)));

							rowOffsetBeforeNodes++;
							break;
						case 2:
							// Column label row; skip

							rowOffsetBeforeNodes++;
							break;
						default:
							// Either a node or a depot
							if(rowCount >= (nodeCount + rowOffsetBeforeNodes))
							{ // This is a depot
								data.appendDepotX(checkIntType(curRow.getCell(0)));
								data.appendDepotY(checkIntType(curRow.getCell(1)));
							}
							else
							{ // This is a node
								data.appendNodeX(checkIntType(curRow.getCell(0)));
								data.appendNodeY(checkIntType(curRow.getCell(1)));
								data.appendNodeDemand(checkIntType(curRow.getCell(2)));
								// Dont need node id, would be redundant
								data.appendNodeEarly(checkIntType(curRow.getCell(4)));
								data.appendNodeLate(checkIntType(curRow.getCell(5)));
								data.appendNodeTime(checkIntType(curRow.getCell(6)));
								
							}
						}
						
						rowCount++;
					}
				}
				catch(IOException ioe)
				{
					failFlag = true;
					System.out.println("Error: Could not process file as excel doc");
				}
			}
			catch(FileNotFoundException e)
			{
				failFlag = true;
				System.out.println("Error: File not found");
			}
			
			if(failFlag)
			{
				return;
			}
			break;
		case 2:
			// MDVRP
			
			break;
		default:
			// Not supported problem type
			
			break;
		}
	}

	public DataFields getData()
	{
		return data;
	}
	
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
}
