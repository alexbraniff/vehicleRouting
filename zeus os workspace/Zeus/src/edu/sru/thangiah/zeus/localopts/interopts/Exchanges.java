package edu.sru.thangiah.zeus.localopts.interopts;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.localopts.*;

/**
 * Contains some utility functions used by the exchanges.
 * <p>Title: Exchanges</p>
 * <p>Description: Contains some utility functions used by the exchanges.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class Exchanges {
  /**
   * Calculate the exchange cost to remove the specified Nodes from the route.
   * Route is restored before return.
   * @param mainNodes linked list to calculate exchange on
   * @param Nodes Nodes to be removed from the route
   * @return exchange cost
   */
  public static double calculateExchange01(NodesLinkedList mainNodes,
                                           Nodes Nodes) {
    boolean isDiagnostic = false;
    double initialCost = mainNodes.getCost(); //calculate initial cost of the route
    double afterRemovalCost = 0;
    double exchangeCost;
    Nodes pcBeforeRemovePC;

    try {
      if (isDiagnostic) {
        System.out.println("before cost = " + initialCost);
      }

      //remove the point cell and get the point cell that was before it
      pcBeforeRemovePC = mainNodes.removeNodes(Nodes);

      //make sure the point cell was removed properly
      if (pcBeforeRemovePC != null) {
        //calculate new cost of the route
        afterRemovalCost = ProblemInfo.nodesLLLevelCostF.getTotalCost(mainNodes);
        exchangeCost = afterRemovalCost - initialCost; //calculate cost difference

        //re-insert the point cell that was removed from the route
        mainNodes.insertAfterNodes(Nodes, pcBeforeRemovePC);
      }
      else {
        //point cell could not be removed, set extremely high cost
        exchangeCost = Double.MAX_VALUE;
      }
    }
    catch (Exception e) {
      exchangeCost = Double.MAX_VALUE;
      System.err.println(
          "Error in NodesLinkedList calculateExchange01(): " + e);
    }
    finally {
      ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(mainNodes); //update stats
    }

    if (isDiagnostic) {
      System.out.println("exchange cost = " + exchangeCost);
    }

    return exchangeCost;
  }

  /**
   * Calculate the exchange cost to insert the specified Nodes into the route.
   * Route is restored before return.
   * @param mainNodes linked list to calculate exchange on
   * @param Nodes Nodes to be inserted into the route.
   * @return exchange cost
   */
  public static double calculateExchange10(NodesLinkedList mainNodes,
                                           Nodes Nodes) {
    boolean isDiagnostic = false;
    double initialCost = mainNodes.getCost(); //calculate initial cost of the route
    double afterInsertionCost = 0;
    double exchangeCost;

    if (isDiagnostic) {
      System.out.println("before cost = " + initialCost);
    }

    //save initial links of the point cell that is going to be inserted
    Nodes initialBeforePC = Nodes.getPrev();
    Nodes initialAfterPC = Nodes.getNext();

    try {
      //insert the point cell and make sure that it was inserted
      if (mainNodes.insertNodes(Nodes) != null) {
        //calculate new cost of the route
        afterInsertionCost = ProblemInfo.nodesLLLevelCostF.getTotalCost(mainNodes);
        exchangeCost = afterInsertionCost - initialCost; //calculate cost difference

        //remove the point cell that was inserted into the route
        mainNodes.removeNodes(Nodes);
      }
      else {
        //point cell was not inserted, set extremely high cost
        exchangeCost = Double.MAX_VALUE;
      }
    }
    catch (Exception e) {
      //error occurred, set extremely high cost
      exchangeCost = Double.MAX_VALUE;
      System.err.println(
          "Error in NodesLinkedList calculateExchange10(): " + e);
    }
    finally {
      //reset the initial links of the point cell that was inserted
      Nodes.setPrev(initialBeforePC);
      Nodes.setNext(initialAfterPC);

      ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(mainNodes); //update stats
    }

    if (isDiagnostic) {
      System.out.println("exchange cost = " + exchangeCost);
    }

    return exchangeCost;
  }

  /**
   * Calculate the exchange cost to remove the second and third specified Nodess from
   * the route and to insert the first specified point cell into the route.  Route
   * is restored before return.
   * @param mainNodes linked list to calculate exchange on
   * @param NodesOne Nodes to be inserted into the route
   * @param NodesTwo Nodes to be removed from the route
   * @param NodesThree Nodes to be removed from the route
   * @return exchange cost
   */
  public static double calculateExchange12(NodesLinkedList mainNodes,
                                           Nodes NodesOne, Nodes NodesTwo,
                                           Nodes NodesThree) {
    double initialCost = mainNodes.getCost(); //calculate initial cost of the route
    double afterTwoRemovalsAndOneInsertionCost;
    double exchangeCost;
    Nodes pcBeforeRemovePCTwo;

    //save initial links of the point cell that is going to be inserted
    Nodes initialBeforePCOne = NodesOne.getPrev();
    Nodes initialAfterPCOne = NodesOne.getNext();

    try {
      //remove the second point cell and get the point cell before it
      pcBeforeRemovePCTwo = mainNodes.removeNodes(NodesTwo);

      //make sure the second point cell was removed properly
      if (pcBeforeRemovePCTwo != null) {
        //remove the third point cell and make sure it was removed properly
        if (mainNodes.removeNodes(NodesThree) != null) {
          //insert the first point cell and make sure it was inserted
          if (mainNodes.insertNodes(NodesOne) != null) {
            afterTwoRemovalsAndOneInsertionCost = ProblemInfo.nodesLLLevelCostF.
                getTotalCost(mainNodes); //calculate new cost of the route
            exchangeCost = afterTwoRemovalsAndOneInsertionCost -
                initialCost; //calculate cost difference

            //remove the point cell that was inserted into the route
            mainNodes.removeNodes(NodesOne);

            //re-insert the point cells that were removed from the route
            mainNodes.insertAfterNodes(NodesTwo, pcBeforeRemovePCTwo);
            mainNodes.insertAfterNodes(NodesThree, NodesTwo);
          }
          else {
            //point cell was not inserted, set extremely high cost
            exchangeCost = Double.MAX_VALUE;

            //re-insert the point cells that were removed from the route
            mainNodes.insertAfterNodes(NodesTwo, pcBeforeRemovePCTwo);
            mainNodes.insertAfterNodes(NodesThree, NodesTwo);
          }
        }
        else {
          //second point cell could not be removed, set extremely high cost
          exchangeCost = Double.MAX_VALUE;

          //re-insert the second point cell that was removed from the route
          mainNodes.insertAfterNodes(NodesTwo, pcBeforeRemovePCTwo);
        }
      }
      else {
        //first point cell could not be removed, set extremely high cost
        exchangeCost = Double.MAX_VALUE;
      }
    }
    catch (Exception e) {
      //error occurred, set extremely high cost
      exchangeCost = Double.MAX_VALUE;
      System.err.println(
          "Error in NodesLinkedList calculateExchange12(): " + e);
    }
    finally {
      //reset the initial links of the point cell that was inserted
      NodesOne.setPrev(initialBeforePCOne);
      NodesOne.setNext(initialAfterPCOne);

      ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(mainNodes); //update stats
    }

    return exchangeCost;
  }

  /**
   * Calculate the exchange cost to remove the third specified Nodes from the route
   * and to insert the first and second specified Nodess into the route.  Route
   * is restored before return.
   * @param mainNodes linked list to calculate exchange on
   * @param NodesOne Nodes to be inserted into the route
   * @param NodesTwo Nodes to be inserted into the route
   * @param NodesThree Nodes to be removed from the route
   * @return exchange cost
   */
  public static double calculateExchange21(NodesLinkedList mainNodes,
                                           Nodes NodesOne, Nodes NodesTwo,
                                           Nodes NodesThree) {
    double initialCost = mainNodes.getCost(); //calculate initial cost of the route
    double afterOneRemovalAndTwoInsertionsCost;
    double exchangeCost;
    Nodes pcBeforeRemovePCThree;

    //save initial links of the point cells that are going to be inserted
    Nodes initialBeforePCOne = NodesOne.getPrev();
    Nodes initialAfterPCOne = NodesOne.getNext();
    Nodes initialBeforePCTwo = NodesTwo.getPrev();
    Nodes initialAfterPCTwo = NodesTwo.getNext();

    try {
      //remove the third point cell and get the point cell that was before it
      pcBeforeRemovePCThree = mainNodes.removeNodes(NodesThree);

      //make sure the third point cell was removed properly
      if (pcBeforeRemovePCThree != null) {
        //insert the first point cell and make sure it was inserted
        if (mainNodes.insertNodes(NodesOne) != null) {
          //insert the second point cell and make sure it was inserted
          if (mainNodes.insertNodes(NodesTwo) != null) {
            afterOneRemovalAndTwoInsertionsCost = ProblemInfo.nodesLLLevelCostF.
                getTotalCost(mainNodes); //calculate new cost of the route
            exchangeCost = afterOneRemovalAndTwoInsertionsCost -
                initialCost; //calculate cost difference

            //remove the second point cell that was inserted
            mainNodes.removeNodes(NodesTwo);
          }
          else {
            //second  point cell was not inserted, set extremely high cost
            exchangeCost = Double.MAX_VALUE;
          }

          //remove the first point cell that was inserted
          mainNodes.removeNodes(NodesOne);

          //re-insert the point cell that was removed from the route
          mainNodes.insertAfterNodes(NodesThree, pcBeforeRemovePCThree);
        }
        else {
          //first point cell was not inserted, set extremely high cost
          exchangeCost = Double.MAX_VALUE;

          //re-insert the point cell that was removed from the route
          mainNodes.insertAfterNodes(NodesThree, pcBeforeRemovePCThree);
        }
      }
      else {
        //third point cell could not be removed, set extremely high cost
        exchangeCost = Double.MAX_VALUE;
      }
    }
    catch (Exception e) {
      exchangeCost = Double.MAX_VALUE;
      System.err.println(
          "Error in NodesLinkedList calculateExchange21(): " + e);
    }
    finally {
      //reset the initial links of the point cells that were inserted
      NodesOne.setPrev(initialBeforePCOne);
      NodesOne.setNext(initialAfterPCOne);
      NodesTwo.setPrev(initialBeforePCTwo);
      NodesTwo.setNext(initialAfterPCTwo);

      ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(mainNodes); //update stats
    }

    return exchangeCost;
  }

  /**
   * Calculate the exchange cost to remove both of the specified Nodess
   * from the route.  Route is restored before return.
   * @param mainNodes linked list to calculate exchange on
   * @param NodesOne Nodes to be removed from the route
   * @param NodesTwo Nodes to be removed the route
   * @return exchange cost
   */
  public static double calculateExchange02(NodesLinkedList mainNodes,
                                           Nodes NodesOne, Nodes NodesTwo) {
    double initialCost = mainNodes.getCost(); //calculate initial cost of the route
    double afterRemovalCost;
    double exchangeCost;
    Nodes pcBeforeRemovePCOne;

    try {
      //remove the first point cell and get the point cell that was before it
      pcBeforeRemovePCOne = mainNodes.removeNodes(NodesOne);

      //make sure the first point cell was removed properly
      if (pcBeforeRemovePCOne != null) {
        //remove the second point cell and make sure it was removed properly
        if (mainNodes.removeNodes(NodesTwo) != null) {
          afterRemovalCost = ProblemInfo.nodesLLLevelCostF.getTotalCost(mainNodes); //calculate new cost of the route
          exchangeCost = afterRemovalCost - initialCost; //calculate cost difference

          //re-insert the point cells that were removed from the route
          mainNodes.insertAfterNodes(NodesOne, pcBeforeRemovePCOne);
          mainNodes.insertAfterNodes(NodesTwo, NodesOne);
        }
        else {
          //second point cell could not be removed, set extremely high cost
          exchangeCost = Double.MAX_VALUE;

          //re-insert the first point cell that was removed from the route
          mainNodes.insertAfterNodes(NodesOne, pcBeforeRemovePCOne);
        }
      }
      else {
        //first point cell could not be removed, set extremely high cost
        exchangeCost = Double.MAX_VALUE;
      }
    }
    catch (Exception e) {
      //error occurred, set extremely high cost
      exchangeCost = Double.MAX_VALUE;
      System.err.println(
          "Error in NodesLinkedList calculateExchange02(): " + e);
    }
    finally {
      ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(mainNodes); //update stats
    }

    return exchangeCost;
  }

  /**
   * Calculate the exchange cost to insert both the specified Nodess into
   * the route.  Route is restored before return.
   * @param mainNodes linked list to calculate exchange on
   * @param NodesOne Nodes to be inserted into the route
   * @param NodesTwo Nodes to be inserted into the route
   * @return exchange cost
   */
  public static double calculateExchange20(NodesLinkedList mainNodes,
                                           Nodes NodesOne, Nodes NodesTwo) {
    double initialCost = mainNodes.getCost(); //calculate initial cost of the route
    double afterInsertionCost;
    double exchangeCost;

    //save initial links of the point cells that are going to be inserted
    Nodes initialBeforePCOne = NodesOne.getPrev();
    Nodes initialAfterPCOne = NodesOne.getNext();
    Nodes initialBeforePCTwo = NodesTwo.getPrev();
    Nodes initialAfterPCTwo = NodesTwo.getNext();

    try {
      //insert the first point cell and make sure it was inserted
      if (mainNodes.insertNodes(NodesOne) != null) {
        //insert the second point cell and make sure it was inserted
        if (mainNodes.insertNodes(NodesTwo) != null) {
          afterInsertionCost = ProblemInfo.nodesLLLevelCostF.getTotalCost(
              mainNodes); //calculate new cost of the route
          exchangeCost = afterInsertionCost - initialCost; //calculate cost difference

          //remove the second point cell that was inserted
          mainNodes.removeNodes(NodesTwo);
        }
        else {
          //second  point cell was not inserted, set extremely high cost
          exchangeCost = Double.MAX_VALUE;
        }

        //remove the first point cell that was inserted
        mainNodes.removeNodes(NodesOne);
      }
      else {
        //first point cell was not inserted, set extremely high cost
        exchangeCost = Double.MAX_VALUE;
      }
    }
    catch (Exception e) {
      //error occurred, set extremely high cost
      exchangeCost = Double.MAX_VALUE;
      System.err.println(
          "Error in NodesLinkedList calculateExchange20(): " + e);
    }
    finally {
      //reset the initial links of the point cells that were inserted
      NodesOne.setPrev(initialBeforePCOne);
      NodesOne.setNext(initialAfterPCOne);
      NodesTwo.setPrev(initialBeforePCTwo);
      NodesTwo.setNext(initialAfterPCTwo);

      ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(mainNodes); //update stats
    }

    return exchangeCost;
  }
}
