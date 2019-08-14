package edu.sru.thangiah.zeus.localopts.intraopts;

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

public class FirstFirstIntraSearch
    extends SearchStrategy {

  private IntraOptimization intraOpt = null;

  public FirstFirstIntraSearch(IntraOptimization o) {
    intraOpt = o;
  }

  public OptInfo run(DepotLinkedList mainDepots) {

    OptInfo info = new OptInfo();
    info.setOldAttributes(mainDepots.getAttributes());

    Depot depot = mainDepots.getHead().getNext();
    Settings.printDebug(Settings.COMMENT,
                        this +" on depot " + depot.getDepotNum());
    while (depot != mainDepots.getTail()) {
      Settings.printDebug(Settings.COMMENT,
                          this +" on depot " + depot.getDepotNum());
      Truck truck = depot.getMainTrucks().getHead().getNext();
//System.out.println("count: " + depot.getMainTrucks().getSize());

      while (truck != depot.getMainTrucks().getTail()) {
        Settings.printDebug(Settings.COMMENT,
                            this +" on truck " + truck.getTruckNum());
        int changes = 0;
        do {
//SwapWorld.setCurrentTruck(truck.getTruckNum());
//System.out.println("nodes count: " + truck.getMainNodes().getSize());
          changes = intraOpt.executeFirstFirst(truck.getMainNodes());
          info.numChanges += changes;
        }
        while (changes > 0 && this.loopUntilNoChanges);

        truck = truck.getNext();
      }
      depot = depot.getNext();
    }

    ProblemInfo.depotLLLevelCostF.calculateTotalsStats(mainDepots);
    info.setNewAtributes(mainDepots.getAttributes());

    if (info.numChanges > 0) {
      info.didChange = true;

    }
    return info;
  }

  public String toString() {
    return intraOpt.toString() + " (First-First)";
  }

  public String getShortName() {
    return intraOpt.getShortName() + "(FF)";
  }

  public Optimization getOptimization() {
    return intraOpt;
  }

}
