package edu.sru.thangiah.zeus.localopts.intraopts;

import java.util.*;

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

public class BestBestIntraSearch
    extends SearchStrategy {
  IntraOptimization intraOpt;

  public BestBestIntraSearch(IntraOptimization o) {
    //set it to the name of the intra optimization
    intraOpt = o;
  }

  public OptInfo run(DepotLinkedList mainDepots) {
    OptimizationUnit best = null;
    HashMap completedOpts = new HashMap();

    //create a structure to hold the optimization info
    OptInfo info = new OptInfo();
    //get the current information on the routes
    info.setOldAttributes(mainDepots.getAttributes());

    boolean madeSwap = false;

    do {
      madeSwap = false;
      best = null;
      OptimizationUnit opt = null;

      //get access to the DepotLinked list with all the depots
      Depot depot = mainDepots.getHead().getNext();
      Settings.printDebug(Settings.COMMENT,
                          this +" on depot " + depot.getDepotNum());
      while (depot != mainDepots.getTail()) {
        Settings.printDebug(Settings.COMMENT,
                            this +" on depot " + depot.getDepotNum());
        Truck truck = depot.getMainTrucks().getHead().getNext();
        while (truck != depot.getMainTrucks().getTail()) {

          Settings.printDebug(Settings.COMMENT,
                              this +" on truck " + truck.getTruckNum());

          opt = intraOpt.executeBestBest(truck.getMainNodes());
          if (opt != null) {
            if (best == null) {
              best = opt;
            }
            else if (opt.getCostDifference() < best.getCostDifference()) {
              best = opt;
            }
          }

          truck = truck.getNext();
        }
        depot = depot.getNext();
      }

      if (best != null) {
        if (intraOpt.getConfirm().isGoodSwap(best)) {
          // if (!completedOpts.containsKey(best.toString())) {

          //record this opt as already done (avoids thrashing between the same
          //two opts)
          madeSwap = true;
          completedOpts.put(best.toString(), "");
          best.execute();
          Settings.printDebug(Settings.WARNING, this +" " + best);
          info.numChanges++;
          info.didChange = true;
        }
        //}
      }
    }
    while (madeSwap && this.loopUntilNoChanges);

    ProblemInfo.depotLLLevelCostF.calculateTotalsStats(mainDepots);
    info.setNewAtributes(mainDepots.getAttributes());

    if (info.numChanges > 0) {
      info.didChange = true;

    }
    return info;
  }

  public String toString() {
    return intraOpt.toString() + " (Best-Best)";
  }

  public String getShortName() {
    return intraOpt.getShortName() + "(BB)";
  }

  public Optimization getOptimization() {
    return intraOpt;
  }

}
