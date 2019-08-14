package edu.sru.thangiah.zeus.localopts.intraopts;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.localopts.*;
import java.util.Vector;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class OneOptUnit
    extends OptimizationUnit {

  private NodesLinkedList theRoute = null; //best route to swap
  private Nodes theNode = null; //best node to move
  private Nodes oldBeforeNode = null; //node before where the best swap should be
  private Nodes newBeforeNode = null; //node before where the best swap should be
  private double theCost = Double.MAX_VALUE; //cost of this current best swap
  boolean isDiagnostic = false;

  public OneOptUnit() {
  }

  /**
   * Constructor for the OneOptUnit
   * @param route NodesLinkedList - linked list to perform one-opt upon
   * @param oldBeforeNode Nodes - node that the current node is after
   * @param node Nodes - the current node
   * @param newBeforeNode Nodes - current node will be placed after this node
   * @param cost double - the cost for this operation
   */
  public OneOptUnit(NodesLinkedList route, Nodes oldBeforeNode, Nodes node,
                    Nodes newBeforeNode, double cost) {
    theRoute = route;
    this.oldBeforeNode = oldBeforeNode;
    theNode = node;
    this.newBeforeNode = newBeforeNode;
    theCost = cost;
  }

  public double getCostDifference() {
    return theCost;
  }

  public void undo() {
    theRoute.removeNodes(theNode);
    theRoute.insertAfterNodes(theNode, oldBeforeNode);
    ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(theRoute); //update stats
  }

  public void execute() {
    if (isDiagnostic) {
      ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(theRoute);
      System.out.println("Route before change - " + theRoute.getRouteString());
      System.out.println("OneOptUnit: Cost before execute - " +
                         theRoute.getCost());
    }

    theRoute.removeNodes(theNode);
    theRoute.insertAfterNodes(theNode, newBeforeNode);
    ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(theRoute); //update stats

    if (isDiagnostic) {
      ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(theRoute);
      System.out.println("Route after change - " + theRoute.getRouteString());
      System.out.println("OneOptUnit: Cost after execute - " + theRoute.getCost());
    }

  }

  /**
   * toString method for debugging purposes
   * @return String
   */
  public String toString() {
    if (theNode != null && newBeforeNode != null) {
      return "placing " + theNode.getIndex() + " after " +
          newBeforeNode.getIndex() + " for $" + theCost;
    }
    else {
      return "null after null for $" + theCost;
    }
  }

  /**
   * Returns an array of the routes that are created after this exchange is
   * complete. For use in the Tabu search
   * @return String[] route string array
   */
  public String[] getRoutes() {
    String[] routes = new String[1];

    routes[0] = theRoute.getRouteString();
    return routes;
  }

  /**
   * Returns an array contain the index of all nodes associated with this swap.
   * @return int[]
   */
  public int[] getNodeIndexes() {
    int[] nodes = new int[1];
    nodes[0] = theNode.getIndex();
    return nodes;
  }

  /**
   * Returns the index of the nodes positioned to the left of the nodes
   * associated with this swap.
   * @return int
   */
  public int[] getLeftNeighborIndexes() {
    int[] nodes = new int[1];
    nodes[0] = newBeforeNode.getIndex();
    return nodes;
  }

  /**
   * Returns the indexes of the nodes positioned to the right of the nodes
   * associated with this swap.
   * @return int
   */
  public int[] getRightNeighborIndexes() {
    int[] nodes = new int[1];
    nodes[0] = newBeforeNode.getNext().getIndex();
    return nodes;
  }
}
