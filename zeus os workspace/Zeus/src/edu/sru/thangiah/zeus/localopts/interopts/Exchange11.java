package edu.sru.thangiah.zeus.localopts.interopts;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.localopts.*;

/**
 * Runs the 11 optimization on the specified depot linked list.
 * <p>Title: Exchange11</p>
 * <p>Description: Runs the 11 optimization on the specified depot linked list.
 * Ex: Route 1: D1<-->P1<-->P2<-->P3<-->P4<-->P5<-->D3
 *     Route 2: D2<-->P6<-->P7<-->P8<-->P9<-->P10<-->D3
 *     Route 1: D1<-->P6<-->P2<-->P3<-->P4<-->P5<-->D3
 *     Route 2: D2<-->P7<-->P1<-->P8<-->P9<-->P10<-->D3</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class Exchange11
    extends InterOptimization {

  /**
   * Constructor
   */
  public Exchange11() {
    confirm = new UpperBoundConfirm(0);
  }

  public Exchange11(SwapConfirm confirm) {
    this.confirm = confirm;
  }

  /**
   * Attempts to remove one shipment from route p and insert it into route q
   * and also removes one shipment from route q and inserts it into route
   * p.  Exchange is successful as long as the new routes are feasible and the
   * new costs are lower than the best cost.
   * @param p route p
   * @param q route q
   * @return true - an exchange has occurred, false - an exchange didn't occurr
   */
  public int executeFirstFirst(NodesLinkedList p, NodesLinkedList q) {
    boolean isDiagnostic = true;
    boolean changeOccurred = false; //whether or not an exchange took place

    Nodes exchangeNodesOne; //first point cell in the exchange
    Nodes exchangeNodesTwo; //second point cell in the exchange

    Exchange11Unit current11 = null;

    //sizes of p and q routes
    int pSize = p.getSize();
    int qSize = q.getSize();

    //exchange cost variables
    double cost1 = Double.MAX_VALUE;
    double cost2 = Double.MAX_VALUE;
    double initCost1 = p.getCost();
    double initCost2 = q.getCost();

    //if either of the routes have less than two nodes, then just return
    //because no exchanges can take place
    if ( (pSize <= 2) || (qSize <= 2)) {
      return 0;
    }

    try {
      //successively remove node i from route p and node j from route q and
      //then insert node i into route q and node j into route p
      //for (int i = 0; i < (pSize - 2); i++) {
    	//for (int j = 0; j < (qSize - 2); j++) {
    	for (int i = 0; i < pSize; i++) {
    		for (int j = 0; j < qSize; j++) {
    			//get the point cells to be exchanged
    			exchangeNodesOne = p.getNodesAtPosition(i);
    			exchangeNodesTwo = q.getNodesAtPosition(j);

    			//calculate exchange cost to remove node i from route p and
          //to insert node j from route q
          cost1 = Exchange11Unit.calculateExchange11(p, exchangeNodesOne,
              exchangeNodesTwo);

          //calculate exchange cost to remove node j from route q and
          //to insert node i from route p
          cost2 = Exchange11Unit.calculateExchange11(q, exchangeNodesTwo,
              exchangeNodesOne);

          current11 = new Exchange11Unit(p, q, exchangeNodesOne,
                                         exchangeNodesTwo,
                                         ( (cost1 + cost2) -
                                          (initCost1 + initCost2)));
          if (isDiagnostic) {
            System.out.println("initCost1 = " + initCost1);
            System.out.println("initCost2 = " + initCost2);
            System.out.println("Cost1     = " + cost1);
            System.out.println("Cost2     = " + cost2);
            System.out.println("Cost difference = " + ( (cost1 + cost2) -
                (initCost1 + initCost2)));
          }
          //if exchange cost is less than the exchange threashold, then it is a
          //valid exchange and we must continue to do the actual exchange
          if (confirm.isGoodSwap(current11)) {
            //do the actual exchange
            current11.execute();
            Settings.printDebug(Settings.WARNING,
                                this.toString() + " (First-First 11) " +
                                current11.toString());
            //update the new costs for p and q
            ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(p);
            ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(q);
            return 1;
            //new search space, break out of exchange so we
            //can start over after this function is called again
          }
        }
      }
    }
    catch (Exception e) {
      System.err.println("Error in TruckLinkedList exchange11(): " + e);
      return 0;
    }
    return 0; //true if change occurred, false if no change occuurred
  }

  public int executeFirstBest(NodesLinkedList p, NodesLinkedList q) {
    boolean isDiagnostic = false;
    Nodes exchangeNodesOne; //first point cell in the exchange
    Nodes exchangeNodesTwo; //second point cell in the exchange

    Exchange11Unit current11 = null;
    Exchange11Unit best11 = null;

    //sizes of p and q routes
    int pSize = p.getSize();
    int qSize = q.getSize();

    //exchange cost variables
    double cost1 = Double.MAX_VALUE;
    double cost2 = Double.MAX_VALUE;
    double initCost1 = p.getCost();
    double initCost2 = q.getCost();

    //if either of the routes have less than two nodes, then just return
    //because no exchanges can take place
    if ( (pSize <= 2) || (qSize <= 2)) {
      return 0;
    }

    try {
      //successively remove node i from route p and node j from route q and
      //then insert node i into route q and node j into route p
      for (int i = 1; i < (pSize - 2); i++) {
        for (int j = 1; j < (qSize - 2); j++) {
          //get the point cells to be exchanged
          exchangeNodesOne = p.getNodesAtPosition(i);
          exchangeNodesTwo = q.getNodesAtPosition(j);

          //calculate exchange cost to remove node i from route p and
          //to insert node j from route q
          cost1 = Exchange11Unit.calculateExchange11(p, exchangeNodesOne,
              exchangeNodesTwo);

          //calculate exchange cost to remove node j from route q and
          //to insert node i from route p
          cost2 = Exchange11Unit.calculateExchange11(q, exchangeNodesTwo,
              exchangeNodesOne);

          current11 = new Exchange11Unit(p, q, exchangeNodesOne,
                                         exchangeNodesTwo,
                                         ( (cost1 + cost2) -
                                          (initCost1 + initCost2)));
          if (isDiagnostic) {
            System.out.println("initCost1 = " + initCost1);
            System.out.println("initCost1 = " + initCost2);
            System.out.println("Cost1     = " + cost1);
            System.out.println("Cost2     = " + cost2);
            System.out.println("Cost difference = " + ( (cost1 + cost2) -
                (initCost1 + initCost2)));
          }

          //if exchange cost is less than the exchange threashold, then it is a
          //valid exchange and we must continue to do the actual exchange
          if (confirm.isGoodSwap(current11)) {
            if (best11 == null) {
              best11 = current11;
            }
            else if (current11.getCostDifference() < best11.getCostDifference()) {
              best11 = current11;
            }
            if (isDiagnostic) {
              System.out.println("FirstBest 11 -" + best11);
            }
          }
        }
      }
    }
    catch (Exception e) {
      System.err.println("Error in TruckLinkedList exchange11(): " + e);
      return 0; //error, no change occured
    }

    if (best11 != null) {
      best11.execute();
      Settings.printDebug(Settings.WARNING,
                          this.toString() + " (First-Best 11) " +
                          best11.toString());
      //update the new costs for p and q
      ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(p);
      ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(q);
      return 1;
    }

    return 0; //no change occurred
  }

  public OptimizationUnit executeBestBest(NodesLinkedList p, NodesLinkedList q) {
    boolean isDiagnostic = false;
    Nodes exchangeNodesOne; //first point cell in the exchange
    Nodes exchangeNodesTwo; //second point cell in the exchange

    Exchange11Unit current11 = null;
    Exchange11Unit best11 = null;

    //sizes of p and q routes
    int pSize = p.getSize();
    int qSize = q.getSize();

    //exchange cost variables
    double cost1 = Double.MAX_VALUE;
    double cost2 = Double.MAX_VALUE;
    double initCost1 = p.getCost();
    double initCost2 = q.getCost();

    //if either of the routes have less than two nodes, then just return
    //because no exchanges can take place
    if ( (pSize <= 2) || (qSize <= 2)) {
      return null;
    }

    try {
      //successively remove node i from route p and node j from route q and
      //then insert node i into route q and node j into route p
      for (int i = 1; i < (pSize - 2); i++) {
        for (int j = 1; j < (qSize - 2); j++) {
          //get the point cells to be exchanged
          exchangeNodesOne = p.getNodesAtPosition(i);
          exchangeNodesTwo = q.getNodesAtPosition(j);

          //calculate exchange cost to remove node i from route p and
          //to insert node j from route q
          cost1 = Exchange11Unit.calculateExchange11(p, exchangeNodesOne,
              exchangeNodesTwo);

          //calculate exchange cost to remove node j from route q and
          //to insert node i from route p - costs are recalculated in inside calcualte
          cost2 = Exchange11Unit.calculateExchange11(q, exchangeNodesTwo,
              exchangeNodesOne);

          current11 = new Exchange11Unit(p, q, exchangeNodesOne,
                                         exchangeNodesTwo,
                                         ( (cost1 + cost2) -
                                          (initCost1 + initCost2)));

          //if exchange cost is less than the exchange threashold, then it is a
          //valid exchange and we must continue to do the actual exchange
          if (confirm.isGoodSwap(current11)) {
            if (best11 == null) {
              best11 = current11;
            }
            else if (current11.getCostDifference() < best11.getCostDifference()) {
              best11 = current11;
            }
            if (isDiagnostic) {
              System.out.println("BestBest 11 -" + best11);
            }
          }
        }
      }
    }
    catch (Exception e) {
      System.err.println("Error in TruckLinkedList exchange11(): " + e);
      return null; //error, no change occured
    }
    return best11; //always return false, exchange made at higher level

  }

  /**
   * Returns the name of the opt
   * @return opt name
   */
  public String toString() {
    return "Exchange 11";
  }

  /**
   * Returns short name for opt
   * @return short name
   */
  public String getShortName() {
    return "11";
  }
}
