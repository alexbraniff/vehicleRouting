package edu.sru.thangiah.zeus.vrptw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.sun.javafx.collections.MappingChange;
import com.sun.javafx.collections.MappingChange.Map;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.vrptw.vrptwcostfunctions.*;

public class VRPTWRoot {
  /**
   * Constructor. Runs the VRP and calculates the total CPU time
   */
  public VRPTWRoot() {

    //Settings for the ProblemInfo class
    //Problem info consists of a set of static values that are used by a number
    //of different classes. The following has to be set in order for the program
    //to function correctly.
    ProblemInfo.nodesLLLevelCostF = new VRPTWNodesLLCostFunctions();
    ProblemInfo.truckLevelCostF = new VRPTWTruckCostFunctions();
    ProblemInfo.truckLLLevelCostF = new VRPTWTruckLLCostFunctions();
    ProblemInfo.depotLevelCostF = new VRPTWDepotCostFunctions();
    ProblemInfo.depotLLLevelCostF = new VRPTWDepotLLCostFunctions();
    //Paths for temporary, input and output files
    //ProblemInfo.currDir gives the working directory of the program
    ProblemInfo.tempFileLocation = ProblemInfo.workingDirectory+"/temp";
    ProblemInfo.inputPath = ProblemInfo.workingDirectory+"/data";

    ProblemInfo.outputPath = ProblemInfo.workingDirectory+"/data";
    
    boolean isForSolutions = false;
    
	String rootFolder = "";
    
    if(!isForSolutions)
    {
		
		// One at a time
		int[] vrpHeuristics = 
		{
		
		};
	
		// One at a time
		int[] mdvrpHeuristics = 
		{
		
		};
	
		// One at a time
		int[] vrptwHeuristics =
		{
//			1
//			2
			3
		};
		
	
		// One at a time
		String[] vrpProblems =
		{
		
		};
	
		// One at a time
	    String[] vrptwProblems =
		{
//			"C1/c101"
//			"C1/c102"
//			"C1/c103"
//			"C1/c104"
//			"C1/c105"
//			"C1/c106"
//			"C1/c107"
//			"C1/c108"
//			"C1/c109"
//			"C2/c201"
//			"C2/c202"
//			"C2/c203"
//			"C2/c204"
//			"C2/c205"
//			"C2/c206"
//			"C2/c207"
//			"C2/c208"
//			"R1/r101"
//			"R1/r102"
//			"R1/r103"
//			"R1/r104"
//			"R1/r105"
//			"R1/r106"
//			"R1/r107"
//			"R1/r108"
//			"R1/r109"
//			"R1/r110"
//			"R1/r111"
//			"R1/r112"
//			"R2/r201"
//			"R2/r202"
//			"R2/r203"
//			"R2/r204"
//			"R2/r205"
//			"R2/r206"
//			"R2/r207"
//			"R2/r208"
//			"R2/r209"
//			"R2/r210"
//			"R2/r211"
//			"RC1/rc101"
//			"RC1/rc102"
//			"RC1/rc103"
			"RC1/rc104"
//			"RC1/rc105"
//			"RC1/rc106"
//			"RC1/rc107"
//			"RC1/rc108"
//			"RC2/rc201"
//			"RC2/rc202"
//			"RC2/rc203"
//			"RC2/rc204"
//			"RC2/rc205"
//			"RC2/rc206"
//			"RC2/rc207"
//			"RC2/rc208"
		};
	
		// One at a time
		String[] mdvrpProblems =
		{
		
		};
		
		if(vrpProblems.length == 1)
		{
			rootFolder = "VRP";
			String filename = ProblemInfo.inputPath + "/" + rootFolder + "/Problems/" + vrpProblems[0] + ".xlsx";
			//new VRPTW(vrpHeuristics[0], filename);
		}
	
		else if(vrptwProblems.length == 1)
		{
			rootFolder = "VRPTW";
			String filename = ProblemInfo.inputPath + "/" + rootFolder + "/Problems/" + vrptwProblems[0] + ".xlsx";
			new VRPTW(vrptwHeuristics[0], filename, isForSolutions);
		}
	
		else if(mdvrpProblems.length == 1)
		{
			rootFolder = "MDVRP";
			String filename = ProblemInfo.inputPath + "/" + rootFolder + "/Problems/" + mdvrpProblems[0] + ".xlsx";
			//new VRPTW(mdvrptwHeuristics[0], filename);
		}
    }
    else
    {
	
		// One at a time
		int[] vrptwHeuristics =
		{
			1,
			2,
			3
		};
	
		// One at a time
	    String[] vrptwProblems =
		{
			"C1/c101",
			"C1/c102",
			"C1/c103",
			"C1/c104",
			"C1/c105",
			"C1/c106",
			"C1/c107",
			"C1/c108",
			"C1/c109",
			"C2/c201",
			"C2/c202",
			"C2/c203",
			"C2/c204",
			"C2/c205",
			"C2/c206",
			"C2/c207",
			"C2/c208",
			"R1/r101",
			"R1/r102",
			"R1/r103",
			"R1/r104",
			"R1/r105",
			"R1/r106",
			"R1/r107",
			"R1/r108",
			"R1/r109",
			"R1/r110",
			"R1/r111",
			"R1/r112",
			"R2/r201",
			"R2/r202",
			"R2/r203",
			"R2/r204",
			"R2/r205",
			"R2/r206",
			"R2/r207",
			"R2/r208",
			"R2/r209",
			"R2/r210",
			"R2/r211",
			"RC1/rc101",
			"RC1/rc102",
			"RC1/rc103",
			"RC1/rc104",
			"RC1/rc105",
			"RC1/rc106",
			"RC1/rc107",
			"RC1/rc108",
			"RC2/rc201",
			"RC2/rc202",
			"RC2/rc203",
			"RC2/rc204",
			"RC2/rc205",
			"RC2/rc206",
			"RC2/rc207",
			"RC2/rc208"
		};
	    
	    for(int i = 0; i < vrptwProblems.length; i++)
		{
			rootFolder = "VRPTW";
			for(int j = 0; j < vrptwHeuristics.length; j++)
			{
				String filename = ProblemInfo.inputPath + "/" + rootFolder + "/Problems/" + vrptwProblems[i] + ".xlsx";
				new VRPTW(vrptwHeuristics[j], filename, isForSolutions);
			}
		}
    }
    
	/*
    // An array of arrays of strings representing folder paths
    // for different problem sets
    String[][] ps =
    	{
    			// Array
    			{
    				// Root of problem set inside of problems and results
    				"VRP"
	    			
	    			// Sub folders inside of root
    				
    				
    			},
    			{
    				// Root of problem set inside of problems and results
	    			"VRPTW",
	    			
	    			// Sub folders inside of root
		    		"C1",
		    		"C2",
		    		"R1",
		    		"R2",
		    		"RC1",
		    		"RC2"
    			},
    			{
    				// Root of problem set inside of problems and results
    				"MDVRP"
	    			
	    			// Sub folders inside of root
    				
    				
    			}
    	};
		*/
    
	/*
    for(int i = 0; i < ps.length; i++)
    {
		String rootFolder = null;
		Vector subFolders = new Vector<String>();
		
    	for(int j = 0; j < ps[i].length; j++)
    	{
    		
    		switch(j)
    		{
    		case 0:
    			rootFolder = ps[i][j];
    			break;
    		default:
    			subFolders.add(j - 1, ps[i][j]);
    			break;
    		}
    	}
    	
    	switch(rootFolder)
    	{
    	case "VRP":
    		
    		break;
    	case "VRPTW":
    		for(int sf = 0; sf < subFolders.size(); sf++)
    		{
    			String subFolder = (String) subFolders.elementAt(sf);
    			System.out.println("SUBFOLDER: " + subFolder);
	    		for(int heuristicMode = 1; heuristicMode <= 3; heuristicMode++)
	    		{
	    			System.out.println("HEURISTIC MODE: " + heuristicMode);
	    			String filepath = null;
	    			
	    			switch(subFolder)
	    			{
	    			case "C1":
	    				for(int problemNumber = 1; problemNumber <= 1; problemNumber++)
	    				{
	    					String pn = String.format("%02d", problemNumber);
	    	    			System.out.println("ROOT: sf=" + subFolder + ", rf=" + rootFolder + " // " + ProblemInfo.inputPath + "/" + rootFolder + "/Problems/" + subFolder + "/c10" + problemNumber + ".xlsx");
	    					filepath = ProblemInfo.inputPath + "/" + rootFolder + "/Problems/" + subFolder + "/c1" + pn + ".xlsx";
	    					new VRPTW(heuristicMode, filepath);
	    				}
	    				break;
	    			case "C2":
	    				for(int problemNumber = 1; problemNumber <= 8; problemNumber++)
	    				{
	    					String pn = String.format("%02d", problemNumber);
	    	    			System.out.println("ROOT: sf=" + subFolder + ", rf=" + rootFolder + " // " + ProblemInfo.inputPath + "/" + rootFolder + "/Problems/" + subFolder + "/c20" + problemNumber + ".xlsx");
	    					filepath = ProblemInfo.inputPath + "/" + rootFolder + "/Problems/" + subFolder + "/c2" + pn + ".xlsx";
	    					new VRPTW(heuristicMode, filepath);
	    				}
	    				break;
	    			case "R1":
	    				for(int problemNumber = 1; problemNumber <= 12; problemNumber++)
	    				{
	    					String pn = String.format("%02d", problemNumber);
	    	    			System.out.println("ROOT: sf=" + subFolder + ", rf=" + rootFolder + " // " + ProblemInfo.inputPath + "/" + rootFolder + "/Problems/" + subFolder + "/r10" + problemNumber + ".xlsx");
	    					filepath = ProblemInfo.inputPath + "/" + rootFolder + "/Problems/" + subFolder + "/r1" + pn + ".xlsx";
	    					new VRPTW(heuristicMode, filepath);
	    				}
	    				break;
	    			case "R2":
	    				for(int problemNumber = 1; problemNumber <= 11; problemNumber++)
	    				{
	    					String pn = String.format("%02d", problemNumber);
	    	    			System.out.println("ROOT: sf=" + subFolder + ", rf=" + rootFolder + " // " + ProblemInfo.inputPath + "/" + rootFolder + "/Problems/" + subFolder + "/r20" + problemNumber + ".xlsx");
	    					filepath = ProblemInfo.inputPath + "/" + rootFolder + "/Problems/" + subFolder + "/r2" + pn + ".xlsx";
	    					new VRPTW(heuristicMode, filepath);
	    				}
	    				break;
	    			case "RC1":
	    				for(int problemNumber = 1; problemNumber <= 8; problemNumber++)
	    				{
	    					String pn = String.format("%02d", problemNumber);
	    	    			System.out.println("ROOT: sf=" + subFolder + ", rf=" + rootFolder + " // " + ProblemInfo.inputPath + "/" + rootFolder + "/Problems/" + subFolder + "/rc10" + problemNumber + ".xlsx");
	    					filepath = ProblemInfo.inputPath + "/" + rootFolder + "/Problems/" + subFolder + "/rc1" + pn + ".xlsx";
	    					new VRPTW(heuristicMode, filepath);
	    				}
	    				break;
	    			case "RC2":
	    				for(int problemNumber = 1; problemNumber <= 8; problemNumber++)
	    				{
	    					String pn = String.format("%02d", problemNumber);
	    	    			System.out.println("ROOT: sf=" + subFolder + ", rf=" + rootFolder + " // " + ProblemInfo.inputPath + "/" + rootFolder + "/Problems/" + subFolder + "/rc20" + problemNumber + ".xlsx");
	    					filepath = ProblemInfo.inputPath + "/" + rootFolder + "/Problems/" + subFolder + "/rc2" + pn + ".xlsx";
	    					new VRPTW(heuristicMode, filepath);
	    				}
	    				break;
		    		}
	    			System.out.println("Out of switch");
	    		}
    			System.out.println("Finished all three heuristics on " + subFolder);
    		}
			System.out.println("Finished all heuristics on " + rootFolder);
    		break;
    	case "MDVRP":
    		
    		break;
    	default:
    		
    		break;
    	}
    }
	*/

  }
}
