package edu.sru.thangiah.zeus.localopts.interopts;

import edu.sru.thangiah.zeus.localopts.OptInfo;
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

public class FirstBestInterSearch
    extends SearchStrategy {
  private boolean DEBUG = true;

  private InterOptimization interOpt = null;

  public FirstBestInterSearch(InterOptimization interOpt) {
    this.interOpt = interOpt;
  }

  /**
   * Performs a first-best search on the specified inter opt
   * @param depots the depot linked list
   * @return information about the optimization
   */
  public OptInfo run(DepotLinkedList mainDepots) {
    Depot d1, d2;
    Truck t1, t2;
    OptInfo optInfo = new OptInfo();

    ///get info for effectiveness of the opt
    optInfo.setOldAttributes(mainDepots.getAttributes());

    //First run for all the trucks in a depot
    d1 = mainDepots.getHead().getNext();
    while (d1 != mainDepots.getTail()) {

      t1 = d1.getMainTrucks().getHead().getNext();
      while (t1 != d1.getMainTrucks().getTail()) {
        if (DEBUG) {
          System.out.println("Inspecting depot " + d1.getDepotNum());
        }
        t2 = t1.getNext();
        while (t2 != d1.getMainTrucks().getTail() && t1.getTruckNum() != t2.getTruckNum()) {
          if (DEBUG) {
            System.out.println("Inspecting truck " + t1.getTruckNum() +
                               " and truck " + t2.getTruckNum());
          }
          int changes = 0;
          do {
            changes = interOpt.executeFirstBest(t1.getMainNodes(),
                                                 t2.getMainNodes());
            optInfo.numChanges += changes;
          }
          while (changes > 0 && this.loopUntilNoChanges);
          t2 = t2.getNext();
        }
        t1 = t1.getNext();
      }
      d1 = d1.getNext();
    }

    d1 = mainDepots.getHead().getNext();
    while (d1 != mainDepots.getTail()) {

      t1 = d1.getMainTrucks().getHead().getNext();
      while (t1 != d1.getMainTrucks().getTail()) {

        d2 = mainDepots.getHead().getNext();
        while (d2 != mainDepots.getTail()) {
          t2 = d2.getMainTrucks().getHead().getNext();
          while (t2 != d2.getMainTrucks().getTail() && t1.getTruckNum() != t2.getTruckNum()
                 && t1.equals(t2)) {
            int changes = 0;
            do {
              changes = interOpt.executeFirstBest(t1.getMainNodes(),
                  t2.getMainNodes());
              optInfo.numChanges += changes;
            }
            while (changes > 0 && this.loopUntilNoChanges);
            t2 = t2.getNext();
          }
          d2 = d2.getNext();
        }
        t1 = t1.getNext();
      }
      d1 = d1.getNext();
    }

    //calculate the new stats for the depot linked list
    ProblemInfo.depotLLLevelCostF.calculateTotalsStats(mainDepots);

    //save the new stats in the opt info
    optInfo.setNewAtributes(mainDepots.getAttributes());

    return optInfo;
  }

  public String toString() {
    return interOpt.toString() + " (First-Best)";
  }

  public String getShortName() {
    return interOpt.getShortName() + "(FB)";
  }

  public Optimization getOptimization() {
    return interOpt;
  }

}
