package edu.sru.thangiah.zeus.localopts.interopts;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.localopts.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class Exchange11Unit
    extends OptimizationUnit {

  private NodesLinkedList p;
  private NodesLinkedList q;
  private Nodes node1;
  private Nodes node2;
  private double cost;

  public Exchange11Unit(NodesLinkedList p, NodesLinkedList q, Nodes node1,
                        Nodes node2, double cost) {
    this.p = p;
    this.q = q;
    this.node1 = node1;
    this.node2 = node2;
    this.cost = cost;
  }

  public double getCostDifference() {
    return cost;
  }

  public void undo() {
    p.removeNodes(node2);
    q.removeNodes(node1);
    p.insertNodes(node1);
    q.insertNodes(node2);
  }

  public void execute() {

    p.removeNodes(node1);
    q.removeNodes(node2);
    p.insertNodes(node2);
    q.insertNodes(node1);
  }

  public String toString() {
    return "Removing node " + node1.getIndex() + " from truck " + p.getTruckNum() +
        " placing into " + q.getTruckNum() +
        " and removing node " + node2.getIndex() + " from truck " +
        q.getTruckNum() + " placing into " + p.getTruckNum() +
        " cost is = " + this.cost;
  }

  /**
   * Calculate the exchange cost to remove the first specified Nodes from the route
   * and to insert the second specified Nodes into the route.  Route is
   * restored before return.
   * @param mainNodes the route to calculate the cost for
   * @param NodesOne Nodes to be removed from the route
   * @param NodesTwo Nodes to be inserted into the route
   * @return exchange cost
   */
  public static double calculateExchange11(NodesLinkedList mainNodes,
                                           Nodes NodesOne, Nodes NodesTwo) {
    boolean isDiagnostic = true;
    double initialCost = mainNodes.getCost(); //calculate initial cost of the route
    //double initialCost = mainNodes.getCost(); //calculate initial cost of the route
    double afterRemovalAndInsertionCost = Double.MAX_VALUE;
    ;
    double exchangeCost;
    Nodes pcBeforeRemovePC;

    //ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(mainNodes);
    //initialCost = mainNodes.getCost();

    //save initial links of the point cell that is going to be inserted
    Nodes initialBeforePCTwo = NodesTwo.getPrev();
    Nodes initialAfterPCTwo = NodesTwo.getNext();

    try {
      if (isDiagnostic) {
        System.out.println("---------------------- ");
        System.out.println("Node to be removed - " + NodesOne.getIndex());
        System.out.println("Node to be added   - " + NodesTwo.getIndex());
        System.out.println("Before cost = " + mainNodes.getCost());
        System.out.println("Before route ");
        System.out.println(mainNodes.getRouteString());
      }
      //remove the first point cell and get the point cell that was before it
      pcBeforeRemovePC = mainNodes.removeNodes(NodesOne);
      if (isDiagnostic) {
        System.out.println("Route after removal of node : ");
        System.out.println(mainNodes.getRouteString());
      }

      //make sure the first point cell was removed properly
      if (pcBeforeRemovePC != null) {
        //insert the second point cell and make sure that it was inserted
        if (mainNodes.insertNodes(NodesTwo) != null) {
          afterRemovalAndInsertionCost = ProblemInfo.nodesLLLevelCostF.
              getTotalCost(mainNodes); //calculate new cost of the route
          exchangeCost = afterRemovalAndInsertionCost - initialCost; //calculate cost difference
          if (isDiagnostic) {
            System.out.println("After cost = " + mainNodes.getCost());
            System.out.println("After route ");
            System.out.println(mainNodes.getRouteString());
            System.out.println("Exchange cost = " + exchangeCost);
          }
          //remove the point cell that was inserted into the route
          mainNodes.removeNodes(NodesTwo);
          if (isDiagnostic) {
            System.out.println("After removing the inserted node ");
            System.out.println(mainNodes.getRouteString());
          }
        }
        else {
          //second point cell was not inserted, set extremely high cost
          exchangeCost = Double.MAX_VALUE;
          afterRemovalAndInsertionCost = Double.MAX_VALUE;
        }
        //re-insert the point cell that was removed from the route
        mainNodes.insertAfterNodes(NodesOne, pcBeforeRemovePC);
        if (isDiagnostic) {
          System.out.println("After inserting the removed node ");
          System.out.println(mainNodes.getRouteString());
        }
      }
      else {
        //point cell could not be removed, set extremely high cost
        if (isDiagnostic) {
          System.out.println("Node could not be removed node ");
        }
        exchangeCost = Double.MAX_VALUE;
        afterRemovalAndInsertionCost = Double.MAX_VALUE;
      }
    }
    catch (Exception e) {
      //error occurred, set extremely high value
      exchangeCost = Double.MAX_VALUE;
      System.err.println(
          "Error in NodesLinkedList calculateExchange11(): " + e);
    }
    finally {
      //reset the initial links of the point cell that was inserted
      NodesTwo.setPrev(initialBeforePCTwo);
      NodesTwo.setNext(initialAfterPCTwo);

      ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(mainNodes); //update stats

      if (isDiagnostic) {
        System.out.println("Back to Original cost = " + mainNodes.getCost());
        System.out.println("Back to original route route ");
        System.out.println(mainNodes.getRouteString());
      }
    }

    //return exchangeCost;
    return afterRemovalAndInsertionCost;
  }

  /**
   * Returns an array of the routes that are created after this exchange is
   * complete. For use in the Tabu search
   * @return String[] route string array
   */
  public String[] getRoutes() {
    String[] routes = new String[2];

    routes[0] = p.getRouteString();
    routes[1] = q.getRouteString();

    return routes;
  }

  /**
   * Returns an array contain the index of all nodes associated with this swap.
   * @return int[]
   */
  public int[] getNodeIndexes() {
    int[] nodes = new int[2];
    nodes[0] = node1.getIndex();
    nodes[1] = node2.getIndex();
    return nodes;
  }

  /**
   * Returns the index of the nodes positioned to the left of the nodes
   * associated with this swap.
   * @return int
   */
  public int[] getLeftNeighborIndexes() {
    int[] nodes = new int[1];
    nodes[0] = q.getHead().getIndex();
    nodes[1] = p.getHead().getIndex();
    return nodes;
  }

  /**
   * Returns the indexes of the nodes positioned to the right of the nodes
   * associated with this swap.
   * @return int
   */
  public int[] getRightNeighborIndexes() {
    int[] nodes = new int[1];
    int temp0 = q.getHead().getNext().getIndex();
    nodes[0] = (temp0 != node2.getIndex())?(temp0):(node2.getNext().getIndex());
    int temp1 = p.getHead().getNext().getIndex();
    nodes[1] = (temp1 != node1.getIndex())?(temp1):(node1.getNext().getIndex());
    return nodes;
  }

}
