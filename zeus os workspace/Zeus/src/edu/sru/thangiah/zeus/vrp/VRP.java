package edu.sru.thangiah.zeus.vrp;

import java.io.*;
import java.util.*;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.vrp.vrpqualityassurance.*;
import edu.sru.thangiah.zeus.gui.*;
import edu.sru.thangiah.zeus.localopts.OptInfo;
import edu.sru.thangiah.zeus.localopts.interopts.Exchange11;
import edu.sru.thangiah.zeus.localopts.interopts.FirstBestInterSearch;
import edu.sru.thangiah.zeus.localopts.interopts.FirstFirstInterSearch;
import edu.sru.thangiah.zeus.metaheuristics.simulatedannealing.SimulatedAnnealing;


/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class VRP {

  int m = 0, //number of vehicles
      n = 0, //number of customers
      t = 0, //number of days(or depots)
      D = 0, //maximum duration of route
      Q = 0; //maximum capacity of vehicle

  long startTime, endTime; //track the CPU processing time
  private Vector mainOpts = new Vector(); //contains the collections of optimizations
  private Vector optInformation = new Vector(); //contains information about routes
  private VRPShipmentLinkedList mainShipments = new VRPShipmentLinkedList(); //customers read in from a file or database that are available
  private VRPDepotLinkedList mainDepots = new VRPDepotLinkedList(); //depots linked list for the VRP problem
  private VRPQualityAssurance vrpQA; //check the integrity and quality of the solution

  //constructor for the class
  public VRP(String dataFile) {

    //Truck types are placed into a vector
    ProblemInfo.truckTypes = new Vector();

    //Type of shipment insertion to be performed
    //ProblemInfo.insertShipType = new Object();

    boolean isDiagnostic = false;
    Shipment tempShip;
    Depot thisDepot;
    int type;
    int depotNo;
    int countAssignLoop;
    boolean status;
    String outputFileName;

    /** @todo  Need to put in a VRP file and read in VRP data. The readfile method will have to be changed to match the format of the
     * vrp file*/
    //read in the MDVRP data
    readDataFromFile(ProblemInfo.inputPath + dataFile);
    Settings.printDebug(Settings.COMMENT,
                        "Read Data File: " + ProblemInfo.inputPath + dataFile);
    printDataToConsole();
    
    mainOpts = new Vector(1);
    mainOpts.add(new FirstBestInterSearch(new Exchange11()));
    SimulatedAnnealing meta = new SimulatedAnnealing(0.1, 1.0, 1, 100);
    OptInfo optInfo = meta.anneal(mainDepots, mainOpts);
    System.out.println(optInfo.toString());
    
    writeDataFile(dataFile.substring(dataFile.lastIndexOf("/") + 1));

    //Ensure that the shipment linked list has been loaded with the data
    if (mainShipments.getVRPHead() == null) {
      Settings.printDebug(Settings.ERROR,
                          "VRP: Shipment linked list is empty");
    }

    //Set up the shipment selection type
    //ProblemInfo.selectShipType = new ClosestEuclideanDistToDepot();
    //Settings.printDebug(Settings.COMMENT,ClosestEuclideanDistToDepot.WhoAmI());
    ProblemInfo.selectShipType = new SmallestPolarAngleToDepot();
    Settings.printDebug(Settings.COMMENT, SmallestPolarAngleToDepot.WhoAmI());
    //ProblemInfo.selectShipType = new SmallestPolarAngleShortestDistToDepot();
    //Settings.printDebug(Settings.COMMENT,SmallestPolarAngleShortestDistToDepot.WhoAmI());

    //set up the shipment insertion type
    ProblemInfo.insertShipType = new LinearGreedyInsertShipment();
    Settings.printDebug(Settings.COMMENT, LinearGreedyInsertShipment.WhoAmI());

    //Capture the CPU time required for solving the problem
    startTime = System.currentTimeMillis();
    // captures the initial information on solving the problem
    // returns the total customer and total distance after the initial solution
    createInitialRoutes();
    System.out.println("Completed initial routes");

    //Get the initial solution
    //Depending on the Settings status, display information on the routes
    //Trucks used, total demand, dist, travel time and cost
    Settings.printDebug(Settings.COMMENT, "Created Initial Routes ");
    Settings.printDebug(Settings.COMMENT,
                        "Initial Stats: " + mainDepots.getSolutionString());
    //At this point all shipments have been assigned
    writeLongSolution(dataFile.substring(dataFile.lastIndexOf("/") + 1));
    //writeShortSolution(dataFile.substring(dataFile.lastIndexOf("/") + 1));

    //Check for the quality and integrity of the solution
    System.out.println("Starting QA");
    vrpQA = new VRPQualityAssurance(mainDepots, mainShipments);
    if (vrpQA.runQA() == false) {
      Settings.printDebug(Settings.ERROR, "QA FAILED!");
    }
    else {
      Settings.printDebug(Settings.COMMENT, "QA succeeded");
     
     
    }
/** @todo  GUI still needs to be implemented */
    //Call to the graphical user inter face
    //Vector emptyVector = new Vector(0);
    //VRPZeusGui gui = new VRPZeusGui(mainDepots, mainShipments, emptyVector);

     ZeusGui guiPost = new ZeusGui(mainDepots, mainShipments);

  } 

  /**
   * Creates the initial solution for the problem
   */
  public void createInitialRoutes() {
    //OptInfo has old and new attributes
    VRPDepot currDepot = null; //current depot
    VRPShipment currShip = null; //current shipment
    //int countLoop=0;

    //check if selection and insertion type methods have been selected
    if (ProblemInfo.selectShipType == null) {
      Settings.printDebug(Settings.ERROR,
                          "No selection shipment type has been assigned");

    }
    if (ProblemInfo.insertShipType == null) {
      Settings.printDebug(Settings.ERROR,
                          "No insertion shipment type has been assigned");
    }

 
    //countLoop=1;
    while (!mainShipments.isAllShipsAssigned()) {
      double x, y;
      int i = 0;
      //Get the x an y coordinate of the depot
      //Then use those to get the customer, that has not been allocated,
      // that is closest to the depot
      currDepot = (VRPDepot) mainDepots.getVRPHead().getNext();
      x = mainDepots.getHead().getXCoord();
      y = mainDepots.getHead().getYCoord();
      //Send the entire mainDepots and mainShipments to get the next shipment
      //to be inserted including the current depot
      VRPShipment theShipment = mainShipments.getNextInsertShipment(mainDepots,
          currDepot, mainShipments, currShip);

      if (theShipment == null) { //shipment is null, print error message
        Settings.printDebug(Settings.COMMENT, "No shipment was selected");
      }
      //The selected shipment will be inserted into the route
      if (!mainDepots.insertShipment(theShipment)) {
        Settings.printDebug(Settings.COMMENT, "The Shipment: <" + theShipment.getIndex() +
                            "> cannot be routed");
      }
      else {
        Settings.printDebug(Settings.COMMENT,
                            "The Shipment: <" + theShipment.getIndex() +// " " + theShipment +
                            "> was routed");
        //tag the shipment as being routed
        theShipment.setIsAssigned(true);
      }
    }

    ProblemInfo.depotLLLevelCostF.calculateTotalsStats(mainDepots);
  }
  
  // State information for reading in a file
  private enum STATE
  {
      ERROR, START, PROBLEM_INFO, TRUCK_TYPE_INFO, DEPOT_INFO, TRUCK_INFO, CUSTOMER_INFO, DONE
  }
  // Variables for file reading
  private FileInputStream fis;
  private InputStreamReader isr;
  private BufferedReader br;
  
  //read in the data from the requested file in token format
  public int readDataFromFile(String VRPFileName) {
    // read in the MDVRP data from the listed file and load the information
    // into the availShipments linked list

    //type = 0 (MDVRP)
    //     = 1 (PTSP)
    //     = 2 (PVRP)
	//     = 3 (VRPTW)
	//     = 4 (MDVRPTW)
	  
	char ch = 'x';
	int line = 0, // line index for file being read in
		index = 0, // column index for file bring read
		type = 0, // type of problem
		lastTruckType = 0, // ID# of last truck type read
		numTruckTypes = 0, // number of truck types to be read in
		maxCapacity = 0, // maximum quantity last truck type can hold; 0 == unlimited
		maxDistance = 0, // distance restriction; 0 == unlimited
		lastDepot = 0, // last depot read
		numDepots = 0, // number of depots to be read in
		xD = 0, // last depot x val
		yD = 0, // last depot y val
		depotOpenHour = 0, // '08'
		depotOpenMinute = 0, // '00' opens at 8:00am; trucks roll out
		depotCloseHour = 0, // '22'
		depotCloseMinute = 0, // '00' closes at 10:00pm; trucks must be back
		maxTravelTime = 0, // Depot imposed time restriction; 0 == unlimited
		lastTruck = 0, // ID# of last truck read for last depot
		numTrucks = 0, // number of trucks in last depot
		truckType = 0, // type of truck currently being read
		lastCustomer = 0, // ID# of last customer read
		numCustomers = 0, // number of customers to be read in
		xC = 0, // last customer x val
		yC = 0, // last customer y val
		demand = 0, // how much does last customer want from the depot
		twStartMonth = 0, // '09
		twStartDay = 0, // '20'
		twStartHour = 0, // '18'
		twStartMinute = 0, // '00' delivery time window starts 09/20 at 6:00pm
		twEndMonth = 0, // '09
		twEndDay = 0, // '20'
		twEndHour = 0, // '18'
		twEndMinute = 0; // '00' delivery time window starts 09/20 at 6:00pm
    
    //This section will be used to hold information from the data file
    String readLn;
    StringTokenizer st;
    
    int error_id = 0;
    // dont need a file open error. return 0 instead
    // 1 = problem with problem info line
    // 2 = problem with one or more tuck type lines
    // 3 = problem with values of a truck type line
    // 4 = problem with a depot info line
    // 5 = problem with values of a depot line
    // 6 = problem with a truck line
    // 7 = problem with value of from a truck line
    // 8 = problem with a customer line
    // 9 = problem with the values of a customer line
	
	STATE state = STATE.START;
	
	while(state != STATE.DONE && state != STATE.ERROR)
	{
		switch(state)
		{
		case START:
			// Init file read
		    try
		    {
		        fis = new FileInputStream(VRPFileName);
		        isr = new InputStreamReader(fis);
		        br = new BufferedReader(isr);
		    }
		    catch (Exception e)
		    {
		    	System.out.println("File is not present");
		        return 0;
		    }
		    // File is ready to be read
		    line = 0;
		    index = 0;
		    // First line should always be problem info
		    // Update State
		    state = STATE.PROBLEM_INFO;
			break;
		case PROBLEM_INFO:
			line += 1;
			// Read in the problem info
			try
			{
			readLn = br.readLine();
			// print out the line that was read
			System.out.println("This line is:" + readLn);

			st = new StringTokenizer(readLn);
			while(st.hasMoreTokens())
		    { // there are more tokens that need to be processed
				if(state == STATE.ERROR)
				{
					break;
				}
				switch (index)
				{
				  case 0:
				    type = Integer.parseInt(st.nextToken());
				    System.out.println("Problem Type: " + type);
				    
				    // Check that type is a good value
				    if(type < 0 || type > 3)
				    { // Hard-coded max problem types; maybe consider changing later
				    	state = STATE.ERROR;
				    	error_id = 1;
				    }
				    break;
				  case 1:
				    numTruckTypes = Integer.parseInt(st.nextToken());
				    System.out.println("# Truck Types: " + numTruckTypes);
				    
				    // Check that numTruckTypes is a good value
				    if(numTruckTypes <= 0)
				    {
				    	state = STATE.ERROR;
				    	error_id = 1;
				    }
				    break;
				  case 2:
				    numDepots = Integer.parseInt(st.nextToken());
				    System.out.println("# Depots: " + numDepots);
				    
				    // Check that numDepots is a good value
				    if(numDepots <= 0)
				    {
				    	state = STATE.ERROR;
				    	error_id = 1;
				    }
				    break;
				  case 3:
				    numCustomers = Integer.parseInt(st.nextToken());
				    System.out.println("# Customers: " + numCustomers);
				    
				    // Check that numCustomers is a good value
				    if(numCustomers <= 0)
				    {
				    	state = STATE.ERROR;
				    	error_id = 1;
				    }
				    break;
				  default:
					break;
				} // end problem info switch
				index += 1;
		      } // end problem info while
		      
		      /** @todo  Update problem info */
			


			    // Put the problem information into the ProblemInfo class
			    // set the problem info for the problem
			    ProblemInfo.numDepots = numDepots; // Set the number of depots
			    ProblemInfo.fileName = VRPFileName; //name of the file being read in
			    ProblemInfo.probType = type; //problem type
			    //ProblemInfo.noOfVehs = m; //number of vehicles
			    ProblemInfo.noOfShips = numCustomers; //number of shipments/customers
			    ProblemInfo.noOfDays = t; //number of days (horizon) or number of depots for MDVRP
		      
		    }
		    catch (Exception e)
		    {
		      System.out.println("Line could not be read");
		    }
		    if(type >= 0 && numTruckTypes > 0 && numDepots > 0 && numCustomers > 0)
		    {
		    	// Successfully read problem info
		    	// Next come the truck types. must be one or more
		    	// Update state
		    	state = STATE.TRUCK_TYPE_INFO;
		    }
		    else
		    {
		    	// one or more of the problem info data were invalid
		    	state = STATE.ERROR;
		    	error_id = 1;
		    }
			break; // break from problem info
		case TRUCK_TYPE_INFO:
			for(lastTruckType = 1; lastTruckType <= numTruckTypes; lastTruckType++)
			{
				line += 1;
				if(state == STATE.ERROR)
				{
					break;
				}
				index = 0;
				// Read in a truck type
			    try
			    {
			      readLn = br.readLine();
			      // print out the line that was read
			      System.out.println("This line is:" + readLn);

			      st = new StringTokenizer(readLn);
			      while(st.hasMoreTokens())
			      { //while there are more tokens
			        switch (index)
			        {
			          case 0:
			            maxCapacity = Integer.parseInt(st.nextToken());
			            break;
			          case 1:
			        	maxDistance = Integer.parseInt(st.nextToken());
			        	break;
			          default:
			        	// Should not have any more characters
			        	state = STATE.ERROR;
			        	error_id = 2;
						System.out.println("ERROR " + error_id + " on line " + line);
						break;
			        } // end problem info switch
			        index += 1;
			      } // end problem info while
			      
			      /** @todo  Add these truck types */
			      
			    }
			    catch (Exception e)
			    {
			      System.out.println("Line could not be read");
			    }
			}
			if(maxCapacity < 0 || maxDistance < 0)
			{
				state = STATE.ERROR;
				error_id = 3;
				System.out.println("ERROR " + error_id + " on line " + line);
				break;
			}
			if(state == STATE.ERROR)
			{
				break;
			}
			// Should have good truck type data
			
			/** @todo add truck types to problem */
			
			// Next line should be info for first depot
			// Update state
			state = STATE.DEPOT_INFO;
			
			break; // break from truck type info
		case DEPOT_INFO:
			line += 1;
			lastDepot += 1;
			index = 0;
			// Read in a depot info line
		    try
		    {
		      readLn = br.readLine();
		      // print out the line that was read
		      System.out.println("This line is:" + readLn);

		      st = new StringTokenizer(readLn);
		      while(st.hasMoreTokens())
		      { //while there are more tokens
		        switch (index)
		        {
		          case 0:
		            xD = Integer.parseInt(st.nextToken());
		            break;
		          case 1:
		        	yD = Integer.parseInt(st.nextToken());
		        	break;
		          case 2:
		        	numTrucks = Integer.parseInt(st.nextToken());
		        	break;
		          case 3:
		        	maxTravelTime = Integer.parseInt(st.nextToken());
		        	break;
		          default:
		        	// Should not have any more characters
		        	state = STATE.ERROR;
		        	error_id = 4;
					System.out.println("ERROR " + error_id + " on line " + line);
					break;
		        } // end problem info switch
		        index += 1;
		      } // end problem info while
		      
		      /** @todo  Add this depot info */
		      
		    }
		    catch (Exception e)
		    {
		      System.out.println("Line could not be read");
		    }
		    if(numTrucks < 0 || maxTravelTime < 0)
		    {
		    	state = STATE.ERROR;
		    	error_id = 5;
				System.out.println("ERROR " + error_id + " on line " + line);
				break;
		    }
		    // Should have good depot info
		    // Next is truck info for this depot
		    // Update state
		    state = STATE.TRUCK_INFO;
			break; // break from depot info
		case TRUCK_INFO:
			for(lastTruck = 1; lastTruck <= numTrucks; lastTruck++)
			{
				line += 1;
				if(state == STATE.ERROR)
				{
					break;
				}
				index = 0;
				// Read in a truck
			    try
			    {
			      readLn = br.readLine();
			      // print out the line that was read
			      System.out.println("This line is:" + readLn);

			      st = new StringTokenizer(readLn);
			      while(st.hasMoreTokens())
			      { //while there are more tokens
			        switch (index)
			        {
			          case 0:
			            truckType = Integer.parseInt(st.nextToken());
			            break;
			          default:
			        	// Should not have any more characters
			        	state = STATE.ERROR;
			        	error_id = 6;
						System.out.println("ERROR " + error_id + " on line " + line);
						break;
			        } // end problem info switch
			        index += 1;
			      } // end problem info while
			      
			      /** @todo  Add these truck types */
			      
			    }
			    catch (Exception e)
			    {
			      System.out.println("Line could not be read");
			    }
			}
			if(truckType <= 0 || truckType > numTruckTypes)
			{
				state = STATE.ERROR;
				error_id = 7;
				System.out.println("ERROR " + error_id + " on line " + line);
				break;
			}
			if(state == STATE.ERROR)
			{
				break;
			}
			// Should have good truck data
			
			/** @todo add truck to depots[lastDepot] */
			
			// Next line could be another depot
			// Or it could be the start of the customers
			if(lastDepot < numDepots)
			{
				// There are more depots to process
				// Update state
				state = STATE.DEPOT_INFO;
			}
			else
			{
				// That was the last depot
				// Update state
				state = STATE.CUSTOMER_INFO;
			}
			break; // break from truck info
		case CUSTOMER_INFO:
			for(lastCustomer = 1; lastCustomer <= numCustomers; lastCustomer++)
			{
				line += 1;
				if(state == STATE.ERROR)
				{
					break;
				}
				index = 0;
				// Read in a customer
			    try
			    {
			      readLn = br.readLine();
			      // print out the line that was read
			      System.out.println("This line is:" + readLn);

			      st = new StringTokenizer(readLn);
			      while(st.hasMoreTokens())
			      { //while there are more tokens
			        switch (index)
			        {
			          case 0:
				            xC = Integer.parseInt(st.nextToken());
				            break;
			          case 1:
				            yC = Integer.parseInt(st.nextToken());
				            break;
			          case 2:
				            demand = Integer.parseInt(st.nextToken());
				            break;
			          case 3:
				            twStartHour = Integer.parseInt(st.nextToken());
				            break;
			          case 4:
				            twEndHour = Integer.parseInt(st.nextToken());
				            break;
			          default:
				        	// Should not have any more characters
				        	state = STATE.ERROR;
				        	error_id = 8;
							System.out.println("ERROR " + error_id + " on line " + line);
				        	break;
			        } // end problem info switch

					if(index >= 2 && demand <= 0)
					{
						state = STATE.ERROR;
						error_id = 9;
						System.out.println("ERROR " + error_id + " on line " + line);
						break;
					}

					/** @todo add customer */
					
			        index += 1;
			      } // end problem info while
			      
			    }
			    catch (Exception e)
			    {
			      System.out.println("Line could not be read");
			    }
			}
			if(state == STATE.ERROR)
			{
				break;
			}
			
			// shouldnt be any more lines
			// Update state
			state = STATE.DONE;
			break; // break from customer info
		case DONE:
			break;
		default:
			break;
				
		}
	}
	/*
    char ch;
    String temp = "";
    int index = 0,
    	j = 0,
        type = 0; //type
    //m        = 0,                           //number of vehicles
    //n        = 0,                           //number of customers
    //t        = 0,                           //number of days(or depots)
    //D        = 0,                           //maximum duration of route
    //Q        = 0;                           //maximum load of vehicle
    int p = 3; //Np neighborhood size

    int depotIndex;

    //Open the requested file
    FileInputStream fis;
    InputStreamReader isr;
    BufferedReader br;
    try {
      fis = new FileInputStream(VRPFileName);
      isr = new InputStreamReader(fis);
      br = new BufferedReader(isr);
    }
    catch (Exception e) {
      System.out.println("File is not present");
      return 0;
    }
    
    //This section will get the initial information from the data file
    //Read in the first line from the file
    String readLn;
    StringTokenizer st;

    //read in the first line
    try {
      readLn = br.readLine();
      //print out the line that was read
      //System.out.println("This is s:" + s);

      st = new StringTokenizer(readLn);
      while (st.hasMoreTokens()) { //while there are more tokens
        //int shValue =  Integer.parseInt(st.nextToken());
        switch (index) {
          case 0:
            type = Integer.parseInt(st.nextToken());
            break;
          case 1:
            m = Integer.parseInt(st.nextToken());
            break;
          case 2:
            n = Integer.parseInt(st.nextToken());
            break;
          case 3:
            t = Integer.parseInt(st.nextToken());
            break;
          case 4:
            D = Integer.parseInt(st.nextToken());
            break;
          case 5:
            Q = Integer.parseInt(st.nextToken());
            break;
        } //end switch
        index += 1;
      } //end while
    }
    catch (Exception e) {
      System.out.println("Line could not be read in");
    }

    //Put the problem information into the ProblemInfo class
    //set the problem info for the problem
    ProblemInfo.numDepots = 1; //Set the number of depots to 1 for this problem
    ProblemInfo.fileName = VRPFileName; //name of the file being read in
    ProblemInfo.probType = type; //problem type
    ProblemInfo.noOfVehs = m; //number of vehicles
    ProblemInfo.noOfShips = n; //number of shipments
    ProblemInfo.noOfDays = t; //number of days (horizon) or number of depots for MDVRP
    if (Q == 0) { //if there is no maximum capacity, set it to a very large number
      Q = 999999999;
    }
    if (D == 0) { //if there is no travel time, set it to a very large number
      D = 999999999; //if there is not maximum distance, set it to a very large number
      //ProblemInfo.maxCapacity = Q;  //maximum capacity of a vehicle
      //ProblemInfo.maxDistance = D;  //maximum distance of a vehicle
    }
    
    
    */
    
    
    /** @todo  There three variables need to be defined at the beginning of
     * the method */
	
	/*
    float maxCapacity = Q; //maximum capacity of a vehicle
    float maxDistance = D; //maximum distance of a vehicle

    String serviceType = "1"; //serviceType is the trucktype. Should match with
    //required truck type
    //In some problems, different truck types might be present to solve
    //the problem. For this problem, we assume that there is only one
    //truck type that is available.
    //loop through each truck type and store each one in the vector
    int numTruckTypes = 1;
    for (int i = 0; i < numTruckTypes; i++) {
      VRPTruckType truckType = new VRPTruckType(i, maxDistance,
                                                maxCapacity, serviceType);
      ProblemInfo.truckTypes.add(truckType);
    }
    
    */

    /** @todo DO we really need customer types for the VRP problem? */
    //Some problems tend to have different customer types. In this problem
    //there is only one customter type. The integer value for the customer type
    //should match with the integer value for the truck type for the compatibiliy
    //check to work
    //read in the different customer types
	
	
	/*
    Vector custTypes = new Vector();
    //Obtain the different customer types
    for (int ct = 0; ct < 1; ct++) {
      custTypes.add(new Integer(1));
    }

    //place the number of depots and number of shipments in the linked list instance
    //These no longer seem to be needed for the shipment linked list. The total number of
    //shipments are tallied when they are inserted into the linked list
    //mainShipments.numShipments = n;
    //mainShipments.noDepots = t;
    //mainShipments.maxCapacity = Q;
    //mainShipments.maxDuration = D ;

    //display the information from the first line
    //System.out.println("typePvrp is       " + type);
    //System.out.println("numVeh is         " + m);
    //System.out.println("numCust is        " + n);
    //System.out.println("days is           " + t);
    //System.out.println("Depot duration is " + D);
    //System.out.println("capacity is       " + Q);

    if (type != 0) { //then it is not an MDVRP problem
      System.out.println("Problem is not an MDVRP problem");
      return 0;
    }

    //This section will get the depot x and y for the PVRP and the PTSP.
    float x = 0, //x coordinate
        y = 0; //y coordinate
    int i = 0, //customer number
        d = 0, //service duration
        q = 0, //demand
        f = 0, //frequency of visit
        a = 0, //number of combinations allowed
        vIndex = 1,
        custCnt = 0;
    int runTimes;

    //Use 1 less the maximum as the 0 index is not used
    //declare the total number of combinations
    int list[] = new int[ProblemInfo.MAX_COMBINATIONS];
    //array of 0'1 and 1's for the combinations
    int currentComb[][] = new int[ProblemInfo.MAX_HORIZON][ProblemInfo.
        MAX_COMBINATIONS];
    //if MDVRP problem, readn in n+t lines
    if (type == 0) {
      runTimes = n + t;
      //if  PVRP/PTSP, read in n+1 lines
    }
    else {
      runTimes = n + 1;

      //This section will get the customers/depots and related information
    }
    try {
      readLn = br.readLine();
      //print out the line that was read in
      //System.out.println("This is s:" + s);

      //The first for loop runtimes dependent upon how many lines are to be read
      //in
      //The next for loop reads the line into s.  Then the entire string in s
      //is processed until the the entire line is processed and there are no
      //more characters are to be processed. There is a case for each index
      //except for the combinations.  The combinations are processed
      //until the last character in s is processed

      for (int k = 0; k < runTimes; k++) {
        index = 0;
        temp = "";
        vIndex = 0;
        custCnt++;
        st = new StringTokenizer(readLn);
        if (k < n) { //it is a shipment
          while (st.hasMoreElements()) {
            switch (index) {
              case 0:
                i = Integer.parseInt(st.nextToken());
                //System.out.println("custNum is " + custNum);
                break;
              case 1: //x = Double.parseDouble(temp);
                x = Integer.parseInt(st.nextToken());
                //System.out.println("x is " + vertexX);
                break;
              case 2:
                y = Integer.parseInt(st.nextToken());
                //y = Double.parseDouble(temp);
                //System.out.println("y is " + vertexY);
                break;
              case 3:
                d = Integer.parseInt(st.nextToken());
                //System.out.println("duration is " + duration);
                break;
              case 4:
                q = Integer.parseInt(st.nextToken());
                //System.out.println("demand is " + demand);
                break;
              case 5:
                f = Integer.parseInt(st.nextToken());
                //System.out.println("frequency is " + frequency);
                break;
              case 6:
                a = Integer.parseInt(st.nextToken());
                //System.out.println("number of comb is " + numComb);
                break;
              default:
                list[vIndex] = Integer.parseInt(st.nextToken());
                //System.out.println("visitComb[" + vIndex +"] is " + visitComb[vIndex]);
                vIndex++;
                break;
            } //end switch
            index += 1;
          } //end while
          //Each combination gets its own set of 0 and 1 combinations
          //a = number of Combinations, list = [] of comb as ints,
          //l=index of combination to be decoded,
          //t = days in planning horizon or #depots
          for (int l = 0; l < a; l++) {
            currentComb[l] = mainShipments.getCurrentComb(list, l, t); // current visit comb

            //insert the customer data into the linked list
          }
          Integer custType = (Integer) custTypes.elementAt(0);
          mainShipments.insertShipment(i, x, y, q, d, f, a, custType.toString(),
                                       list, currentComb);

          //  type = (Integer) custTypes.elementAt(0);
          //       shipment = new Shipment(mainShipments.getNumShipments() +
          //                               1, x, y, 1, d, type.toString(), "" + i);

        }
        else { //it is a depot - add it to the depot linked list
          while (st.hasMoreElements() && index < 3) { //No more than three values are there for depot information
            switch (index) {
              case 0:
                i = Integer.parseInt(st.nextToken());
                //System.out.println("custNum is " + custNum);
                break;
              case 1: //x = Double.parseDouble(temp);
                x = Integer.parseInt(st.nextToken());
                //System.out.println("x is " + vertexX);
                break;
              case 2:
                y = Integer.parseInt(st.nextToken());
                //y = Double.parseDouble(temp);
                //System.out.println("y is " + vertexY);
                break;
              default:
                System.out.println("Default in reading the file was initiated");
                vIndex++;
            } //end switch
            index += 1;
          } //while

          //insert the depot into the depot linked list
          VRPDepot depot = new VRPDepot(i - n, x, y); //n is the number of customers
          mainDepots.insertDepotLast(depot);

          //Each depot has a mainTrucks. The different truck types available are
          //inserted into the mainTrucks type. For the VRP, there is only one truck type
          depot = (VRPDepot) mainDepots.getHead().getNext();
          for (i = 0; i < ProblemInfo.truckTypes.size(); i++) {
            VRPTruckType ttype = (VRPTruckType) ProblemInfo.truckTypes.
                elementAt(i);
            depot.getMainTrucks().insertTruckLast(new VRPTruck(ttype,
                depot.getXCoord(), depot.getYCoord()));
          }
        } //else
        //read the next line from the file
        try {
          readLn = br.readLine();
        }
        catch (Exception e) {
        	e.printStackTrace();
          System.out.println("Reading in the next line");
        } //try
        //System.out.println("This is s:" + s);
      } //end for
    }
    catch (Exception e) {
    	e.printStackTrace();
      System.out.println("Reading the line");
    }

    //print out the shipment numbers on command line
    //  mainShipments.printShipNos();
    //call method to send the data to file
    try {
      //availShipments.outputMDVRPShipData(type, t, MDVRPFileName, "outCust.txt");   //problem type, #days or depots
      //outputMDVRPShipData(type, t, MDVRPFileName, "outCust.txt");   //problem type, #days or depots
    }
    catch (Exception e) {
      System.out.println("Shipment information could not be sent to the file");
    }
    */
    return 1;
  }
  
  /**
   * Print  out the data to the console
   */

  public void printDataToConsole() {
    try {
      mainShipments.printVRPShipmentsToConsole();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Write out the data file that was read in
   * @param file name of file used for generating the data
   */

  public void writeDataFile(String file) {
    try {
      PrintStream ps = new PrintStream(new FileOutputStream(ProblemInfo.
              outputPath +file +"_students.txt"));
      mainShipments.writeVRPShipments(ps);
    }
    catch (IOException ioex) {
      ioex.printStackTrace();
    }
  }

  /**
   * Will write a long detailed solution for the problem
   * @param file name of the file to write to
   */
  public void writeLongSolution(String file) {
    try {
      PrintStream ps = new PrintStream(new FileOutputStream(ProblemInfo.
          outputPath + file + "_long.txt"));
      mainDepots.printDepotLinkedList(ps);
    }
    catch (IOException ioex) {
      ioex.printStackTrace();
    }
  }

  /**
   * Will write a short solution for the problem
   * @param file name of the file to write to
   */
  public void writeShortSolution(String file) {
    try {
      //PrintStream ps = new PrintStream(new FileOutputStream(ProblemInfo.
      //outputPath + "/" + file + "_short.txt"));
      PrintStream ps = new PrintStream(new FileOutputStream(ProblemInfo.
          outputPath + file + "_short.txt"));

      ps.println("File: " + file + " Num Depots: " +
                 ProblemInfo.numDepots + " Num Pick Up Points: " +
                 ProblemInfo.numCustomers + " Num Trucks: " +
                 ProblemInfo.numTrucks + " Processing Time: " +
                 (endTime - startTime) / 1000 + " seconds");
      ps.println(mainDepots.getAttributes().toDetailedString());
      ps.println();

      Depot depotHead = mainDepots.getHead();
      Depot depotTail = mainDepots.getTail();

      while (depotHead != depotTail) {
        Truck truckHead = depotHead.getMainTrucks().getHead();
        Truck truckTail = depotHead.getMainTrucks().getTail();

        while (truckHead != truckTail) {
          ps.print("Truck #" + truckHead.getTruckNum() + " MaxCap: " +
        		  truckHead.getTruckType().getMaxCapacity() + " Demand: " +
        		  truckHead.getAttributes().getTotalDemand() + " ROUTE:");

          Nodes nodesHead = truckHead.getMainNodes().getHead();
          Nodes nodesTail = truckHead.getMainNodes().getTail();

          while (nodesHead != nodesTail) {
            ps.print(nodesHead.getIndex() + " ");
            nodesHead = nodesHead.getNext();
          }

          ps.println();
          truckHead = truckHead.getNext();
        }

        ps.println();
        ps.println();
        depotHead = depotHead.getNext();
      }
      for (int i = 0; i < optInformation.size(); i++) {
        ps.println(optInformation.elementAt(i));
      }
    }
    catch (IOException ioex) {
      ioex.printStackTrace();
    }
  }

} //End of VRP file
