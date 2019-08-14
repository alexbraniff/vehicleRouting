package edu.sru.thangiah.zeus.localopts.interopts;

import edu.sru.thangiah.zeus.localopts.OptInfo;
import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.localopts.*;
import java.util.HashMap;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class BestBestInterSearch
    extends SearchStrategy {

  InterOptimization interOpt = null;

  public BestBestInterSearch(InterOptimization interOpt) {
    this.interOpt = interOpt;
  }

  /**
   * Performs a best-best search on the specified inter opt
   * @param depots the depot linked list
   * @return if a change occured
   */
  public OptInfo run(DepotLinkedList mainDepots) {
    OptimizationUnit best = null;
    HashMap completedOpts = new HashMap();
    OptInfo optInfo = new OptInfo();

    ///get info for effectiveness of the opt
    optInfo.setOldAttributes(mainDepots.getAttributes());

    boolean didChange = false;

    do {
      didChange = false;
      Depot d1 = mainDepots.getHead().getNext();
      while (d1 != mainDepots.getTail()) {

        Truck t1 = d1.getMainTrucks().getHead().getNext();
        while (t1 != d1.getMainTrucks().getTail()) {

          Depot d2 = mainDepots.getHead().getNext();
          while (d2 != mainDepots.getTail()) {
            Truck t2 = d2.getMainTrucks().getHead().getNext();
            while (t2 != d2.getMainTrucks().getTail() && t1.getTruckNum() != t2.getTruckNum()
                   && t1.equals(t2)) {

              OptimizationUnit unit = interOpt.executeBestBest(t1.getMainNodes(),
                  t2.getMainNodes());

              if (unit != null) {
                if (best == null) {
                  best = unit;
                }
                else if (unit.getCostDifference() < best.getCostDifference()) {
                  best = unit;
                }
              }
              t2 = t2.getNext();
            }
            d2 = d2.getNext();
          }
          t1 = t1.getNext();
        }
        d1 = d1.getNext();
      }

      if (best != null) {
        if (interOpt.getConfirm().isGoodSwap(best)) {
          if (!completedOpts.containsKey(best.toString())) {
            //record this opt as already done (avoids thrashing between the same
            //two opts)
            didChange = true;
            completedOpts.put(best.toString(), "");
            best.execute();
            Settings.printDebug(Settings.WARNING, this +" " + best);
            optInfo.numChanges++;
            optInfo.didChange = true;
          }
        }
      }

      //calculate the new stats for the depot linked list
      ProblemInfo.depotLLLevelCostF.calculateTotalsStats(mainDepots);
    }
    while (didChange && loopUntilNoChanges);

    //save the new stats in the opt info
    optInfo.setNewAtributes(mainDepots.getAttributes());

    return optInfo;
  }

  /**
   * Returns the name of the opt
   * @return opt name
   */
  public String toString() {
    return interOpt + " (Best-Best)";
  }

  /**
   * Returns short name for opt
   * @return short name
   */
  public String getShortName() {
    return "BB";
  }

  public Optimization getOptimization() {
    return interOpt;
  }

}
