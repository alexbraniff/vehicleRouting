package edu.sru.thangiah.zeus.localopts.intraopts;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.localopts.*;

/**
 * Performs a local one opt on the depot linked list
 * <p>Title: LocalOneOpt</p>
 * <p>Description: Performs a local one opt on the depot linked list by removing
 * one customer and finding a better place for it in the route.
 * Ex: D1<-->P1<-->P2<-->P3<-->P4<-->P5<-->D2
 *     D1<-->P2<-->P1<-->P3<-->P4<-->P5<-->D2
 *     D1<-->P2<-->P3<-->P1<-->P4<-->P5<-->D2
 *     D1<-->P2<-->P3<-->P4<-->P1<-->P5<-->D2
 *     D1<-->P2<-->P3<-->P4<-->P5<-->P1<-->D2</p>
 * <p><b>Settings:</b>
 * Loop Until No changes: will repeat the method until no changes are made
 *
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class OneOpt
    extends IntraOptimization {

  /**
   * Constructor
   */
  public OneOpt() {
    //set the upperbound for the optimization to 0
    confirm = new UpperBoundConfirm(0);
  }

  public OneOpt(SwapConfirm confirm) {
    this.confirm = confirm;
  }

  public OptimizationUnit executeBestBest(NodesLinkedList mainNodes) {
    boolean isDiagnostic = true;
    OneOptUnit bestOneOpt = null;
    OneOptUnit currentOneOpt = null;
    int routeSize = mainNodes.getSize(); //size of this bus route
    Nodes currentPC;
    Nodes pcBeforeRemovePC;
    Nodes pcBeforeInsertPC;

    double newCost;
    double initialCost = mainNodes.getCost();

    if (isDiagnostic) {
      System.out.println("Initial Cost is " + initialCost);
    }

    //if size of the bus route is less than 4, then it is impossible to
    //optimize using the local one opt.  so just return true that no change
    //has taken place.
    if (routeSize < 4) {
      return null;
    }

    try {

      //go through each of the pick up point cells in the route and attempt
      //to re-insert them into each location of the route
      for (int i = 1; i < (routeSize - 1); i++) {
        //remove current pick up point cell and record the point cell that was before it
        currentPC = mainNodes.getNodesAtPosition(i);
        pcBeforeRemovePC = mainNodes.removeNodes(currentPC);

        //insert the current pick up point cell into each location of
        //the route
        for (int j = 0; j < (routeSize - 2); j++) {
          //don't bother inserting pick up point cell back into it's
          //original location
          if (j != (i - 1)) {
            pcBeforeInsertPC = mainNodes.getNodesAtPosition(j);
            if (isDiagnostic) {
              System.out.println("Node removed " + currentPC);
              System.out.println("Before Node removal " +
                                 mainNodes.getRouteString());
            }

            mainNodes.insertAfterNodes(currentPC, pcBeforeInsertPC);
            newCost = ProblemInfo.nodesLLLevelCostF.getTotalCost(mainNodes);

            if (isDiagnostic) {
              System.out.println("After Insertion " + mainNodes.getRouteString());
              System.out.println("Cost of before and after insertion " +
                                 initialCost + " " + newCost);
            }
            //currentOneOpt = new OneOptUnit(mainNodes, pcBeforeRemovePC,
            //                               currentPC, pcBeforeInsertPC,
            //                               (initialCost - newCost));
            currentOneOpt = new OneOptUnit(mainNodes, pcBeforeRemovePC,
                                           currentPC, pcBeforeInsertPC,
                                           (newCost - initialCost));

            //if (isDiagnostic){
            //  System.out.println("CurrentOneOpt " +currentOneOpt);
            //}

            if (confirm.isGoodSwap(currentOneOpt)) {
              if (isDiagnostic) {
                System.out.println("CurrentOneOpt " + currentOneOpt);
                System.out.println("After Insertion " +
                                   mainNodes.getRouteString());
              }
              Settings.printDebug(Settings.COMMENT,
                                  "OneOpt(BB):REGISTER - " +
                                  currentOneOpt.toString());
              if (bestOneOpt == null) {
                bestOneOpt = currentOneOpt;
              }
              else if (currentOneOpt.getCostDifference() <
                       bestOneOpt.getCostDifference()) {
                bestOneOpt = currentOneOpt;
              }
              if (isDiagnostic) {
                System.out.println("BestBestOneOpt " + bestOneOpt);
              }
            }
            //remove current pick up point cell
            mainNodes.removeNodes(currentPC);
            if (isDiagnostic) {
              System.out.println("Remove Current Pickuppoint Cell " +
                                 mainNodes.getRouteString());
            }

          }
        }
        //re-insert current pick up point cell into it's original location
        mainNodes.insertAfterNodes(currentPC, pcBeforeRemovePC);
        //reecompute the cost to set the attributes to the correct cost
        ProblemInfo.nodesLLLevelCostF.getTotalCost(mainNodes);
        if (isDiagnostic) {
          System.out.println("Reinsert Pickuppoint Cell " +
                             mainNodes.getRouteString());
        }

        //just like the first-best, but do not make the change at this level
      }

    }
    catch (Exception e) {
      System.err.println("Error in NodesLinkedList localOneOptimization(): " +
                         e);
    }

    return bestOneOpt; //return the best from here
  }

  /**
   * This will perform the first-best version of the local one-opt. This will
   * search for the best swap that can be made in this route and make it. It
   * will then return from the method with the change it made (or didn't make).
   * Ex: D1<-->P1<-->P2<-->P3<-->P4<-->P5<-->D2
   *     D1<-->P2<-->P1<-->P3<-->P4<-->P5<-->D2
   *     D1<-->P2<-->P3<-->P1<-->P4<-->P5<-->D2
   *     D1<-->P2<-->P3<-->P4<-->P1<-->P5<-->D2
   *     D1<-->P2<-->P3<-->P4<-->P5<-->P1<-->D2
   *
   * @param mainNodes NodesLinkedList the route to optimiza
   * @return int 1 - swap made, 0 - no swap made
   */
  public int executeFirstBest(NodesLinkedList mainNodes) {
    boolean isDiagnostic = false;
    OneOptUnit bestOneOpt = null;
    OneOptUnit currentOneOpt = null;
    int routeSize = mainNodes.getSize(); //size of this bus route
    Nodes currentPC;
    Nodes pcBeforeRemovePC;
    Nodes pcBeforeInsertPC;

    double newCost;
    double initialCost = mainNodes.getCost();
    int numLocalOpts = 0;

    //if size of the bus route is less than 4, then it is impossible to
    //optimize using the local one opt.  so just return true that no change
    //has taken place.
    if (routeSize < 4) {
      return 0;
    }

    try {
      //go through each of the pick up point cells in the route and attempt
      //to re-insert them into each location of the route
      for (int i = 1; i < (routeSize - 1); i++) {
        //remove current pick up point cell and record the point cell that was before it
        currentPC = mainNodes.getNodesAtPosition(i);
        if (isDiagnostic) {
          System.out.println("Before Node removal " + mainNodes.getRouteString());
          //System.out.println(pcBeforeRemovePC);
        }

        pcBeforeRemovePC = mainNodes.removeNodes(currentPC);
        if (isDiagnostic) {
          System.out.println("Node removed " + currentPC);
          System.out.println("After Node removal " + mainNodes.getRouteString());
          //System.out.println(pcBeforeRemovePC);
        }

        //insert the current pick up point cell into each location of
        //the route
        for (int j = 0; j < (routeSize - 2); j++) {
          //don't bother inserting pick up point cell back into it's
          //original location
          if (j != (i - 1)) {
            pcBeforeInsertPC = mainNodes.getNodesAtPosition(j);
            mainNodes.insertAfterNodes(currentPC, pcBeforeInsertPC);

            newCost = ProblemInfo.nodesLLLevelCostF.getTotalCost(mainNodes);
            if (isDiagnostic) {
              System.out.println("After Insertion " + mainNodes.getRouteString());
              System.out.println("Cost of before and after insertion " +
                                 initialCost + " " + newCost);
              //System.out.println(pcBeforeRemovePC);
            }
            //currentOneOpt = new OneOptUnit(mainNodes, pcBeforeRemovePC,
            //                                currentPC, pcBeforeInsertPC,
            //                               (initialCost - newCost));
            currentOneOpt = new OneOptUnit(mainNodes, pcBeforeRemovePC,
                                           currentPC, pcBeforeInsertPC,
                                           (newCost - initialCost));

            //check to see if this should be examined
            if (confirm.isGoodSwap(currentOneOpt)) {
              Settings.printDebug(Settings.COMMENT,
                                  "OneOpt(BF):REGISTER - " +
                                  currentOneOpt.toString());
              if (bestOneOpt == null) {
                bestOneOpt = currentOneOpt;
              }
              else if (currentOneOpt.getCostDifference() <
                       bestOneOpt.getCostDifference()) {
                bestOneOpt = currentOneOpt;
              }
              if (isDiagnostic) {
                System.out.println("FirstBestOneOpt -" + bestOneOpt);
              }
            }

            //remove current pick up point cell
            mainNodes.removeNodes(currentPC);
          }
        }

        //re-insert current pick up point cell into it's original location
        mainNodes.insertAfterNodes(currentPC, pcBeforeRemovePC);
        ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(mainNodes); //after insertion, reset cost
      }

      //if a new best cost route was found, then perform the permanent
      //changes
      if (bestOneOpt != null) {
        bestOneOpt.execute();
        Settings.printDebug(Settings.WARNING, "OneOpt(FB):" + bestOneOpt);

      }
    }

    catch (Exception e) {
      System.err.println("Error in NodesLinkedList localOneOptimization(): " +
                         e);
    }

    return numLocalOpts; //return true if change occurred, false if
    //no change occurred
  }

  /**
   * This stored procedure will execute the first-first version of the local
   * one-opt. This will make the first good swap it finds and exit from the
   * method
   * @param mainNodes NodesLinkedList the route to optimize
   * @return int 0-no changes OR 1-a change made
   */
  public int executeFirstFirst(NodesLinkedList mainNodes) {
    //NodesLinkedList mainNodes = (NodesLinkedList) mainObjNodes;
    boolean isDiagnostic = true;
    int routeSize = mainNodes.getSize(); //size of this bus route
    Nodes currentPC;
    Nodes pcBeforeRemovePC;
    Nodes pcBeforeInsertPC;
    double newCost;
    double initialCost = mainNodes.getCost();

    //if size of the bus route is less than 4, then it is impossible to
    //optimize using the local one opt.  so just return true that no change
    //has taken place.
    if (routeSize < 4) {
      return 0;
    }
    try {

      //go through each of the pick up point cells in the route and attempt
      //to re-insert them into each location of the route
      //for (int i = 1; i < (routeSize - 1); i++) {
      for (int i = 0; i < (routeSize - 1); i++) {
        //remove current pick up point cell and record the point cell that was before it
        currentPC = mainNodes.getNodesAtPosition(i);
        if (isDiagnostic) {
          System.out.println("Before Node removal " + mainNodes.getRouteString());
          //System.out.println(pcBeforeRemovePC);
        }
        pcBeforeRemovePC = mainNodes.removeNodes(currentPC);
        if (isDiagnostic) {
          System.out.println("Node removed " + currentPC.getIndex());
          System.out.println("After Node removal " + mainNodes.getRouteString());
          //System.out.println(pcBeforeRemovePC);
        }
        //insert the current pick up point cell into each location of
        //the route
        for (int j = 0; j < (routeSize - 1); j++) {
          //don't bother inserting pick up point cell back into it's
          //original location
         // if (j != (i - 1)) {
         // if (j != i) {
            pcBeforeInsertPC = mainNodes.getNodesAtPosition(j);

            mainNodes.insertAfterNodes(currentPC, pcBeforeInsertPC);

            newCost = ProblemInfo.nodesLLLevelCostF.getTotalCost(mainNodes);
            if (isDiagnostic) {
              System.out.println("After Insertion " + mainNodes.getRouteString());
              System.out.println("Cost of before and after insertion " +
                                 initialCost + " " + newCost);
              //System.out.println(pcBeforeRemovePC);
            }

            //OneOptUnit aOneOpt = new OneOptUnit(mainNodes, pcBeforeRemovePC,
            //                                    currentPC,
            //                                    pcBeforeInsertPC,
            //                                    (initialCost - newCost));
            OneOptUnit aOneOpt = new OneOptUnit(mainNodes, pcBeforeRemovePC,
                                                currentPC,
                                                pcBeforeInsertPC,
                                                (newCost - initialCost));

            //if the new cost is less than the old cost, then save this as
            //the first good swap and return from the method
            if (confirm.isGoodSwap(aOneOpt)) {
              //leave the route configuration as is to save it
              Settings.printDebug(Settings.COMMENT,
                                  "OneOpt(First First):REGISTER - " +
                                  aOneOpt.toString());
              Settings.printDebug(Settings.WARNING,
                                  "OneOpt(First First):" + aOneOpt);
              //update stats
              ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(mainNodes);

              return 1; //return 1 change
            }

            //remove current pick up point cell
            mainNodes.removeNodes(currentPC);
          //}
        }
        //re-insert current pick up point cell into it's original location
        mainNodes.insertAfterNodes(currentPC, pcBeforeRemovePC);
        ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(mainNodes); //after insertion, reset cost
      }
    }

    catch (Exception e) {
      System.err.println("Error in NodesLinkedList localOneOptimization(): " +
                         e);
      e.printStackTrace();
    }
    return 0; //return no changes
  }

  /**
   * Returns the name of the opt
   * @return opt name
   */
  public String toString() {
    return "Local One Opt";
  }

  /**
   * Returns short name for opt
   * @return short name
   */
  public String getShortName() {
    return "1-Opt";
  }
}
