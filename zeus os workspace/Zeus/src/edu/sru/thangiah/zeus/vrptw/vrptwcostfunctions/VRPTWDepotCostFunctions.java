package edu.sru.thangiah.zeus.vrptw.vrptwcostfunctions;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.vrptw.VRPTWDepot;

/**
 * Cost Functions specific to the VRPTW Depot level.
 * <p>Title: Zeus - A Unified Object Oriented Model for VRPTW's</p>
 * <p>Description: cost functions specific to VRPTWDepot level</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0

 */
public class VRPTWDepotCostFunctions
    extends VRPTWAbstractCostFunctions implements
    java.io.Serializable {

  public double getTotalCost(Object o) {
    VRPTWDepot depot = (VRPTWDepot) o;
    setTotalCost(o);

    return depot.getAttributes().getTotalCost();
  }

  /*public double getTotalConstraintCost(Object o) {
    VRPDepot depot = (VRPDepot) o;
    setTotalConstraintCost(o);

    return depot.getAttributes().totalConstraintCost;
     }*/

  /*public double getTotalCrossRoadPenaltyCost(Object o) {
    VRPDepot depot = (VRPDepot) o;
    setTotalCrossRoadPenaltyCost(o);

    return depot.getAttributes().totalCrossRoadPenaltyCost;
     }*/

  /*public double getTotalTurnAroundPenaltyCost(Object o) {
    VRPDepot depot = (VRPDepot) o;
    setTotalTurnAroundPenaltyCost(o);

    return depot.getAttributes().totalTurnAroundPenaltyCost;
     }*/

  public float getTotalDemand(Object o) {
    VRPTWDepot depot = (VRPTWDepot) o;
    setTotalDemand(o);

    return (int) depot.getAttributes().getTotalDemand();
  }

  public double getTotalDistance(Object o) {
    VRPTWDepot depot = (VRPTWDepot) o;
    setTotalDistance(o);

    return depot.getAttributes().getTotalDistance();
  }

  public double getTotalTravelTime(Object o) {
    VRPTWDepot depot = (VRPTWDepot) o;
    setTotalTravelTime(o);

    return depot.getAttributes().getTotalTravelTime();
  }

  public double getMaxTravelTime(Object o) {
    VRPTWDepot depot = (VRPTWDepot) o;
    setMaxTravelTime(o);

    return depot.getAttributes().getMaxTravelTime();
  }

  public double getAvgTravelTime(Object o) {
    VRPTWDepot depot = (VRPTWDepot) o;
    setAvgTravelTime(o);

    return depot.getAttributes().getAvgTravelTime();
  }

  public void setTotalCost(Object o) {
    VRPTWDepot depot = (VRPTWDepot) o;
    depot.getAttributes().setTotalCost(ProblemInfo.truckLLLevelCostF.getTotalCost(
        depot.getMainTrucks()));
  }

  /*public void setTotalConstraintCost(Object o) {
    VRPDepot depot = (VRPDepot) o;
    depot.getAttributes().totalConstraintCost = ProblemInfo.truckLLLevelCostF.
        getTotalConstraintCost(depot.getMainTrucks());
     }*/

  /*public void setTotalCrossRoadPenaltyCost(Object o) {
    VRPDepot depot = (VRPDepot) o;
    depot.getAttributes().totalCrossRoadPenaltyCost = ProblemInfo.truckLLLevelCostF.
        getTotalCrossRoadPenaltyCost(depot.getMainTrucks());
     }*/

  /* public void setTotalTurnAroundPenaltyCost(Object o) {
     VRPDepot depot = (VRPDepot) o;
   depot.getAttributes().totalTurnAroundPenaltyCost = ProblemInfo.truckLLLevelCostF.
         getTotalTurnAroundPenaltyCost(depot.getMainTrucks());
   }*/

  public void setTotalDemand(Object o) {
    VRPTWDepot depot = (VRPTWDepot) o;
    depot.getAttributes().setTotalDemand((int) ProblemInfo.truckLLLevelCostF.
        getTotalDemand(depot.getMainTrucks()));
  }

  public void setTotalDistance(Object o) {
    VRPTWDepot depot = (VRPTWDepot) o;
    depot.getAttributes().setTotalDistance((float) ProblemInfo.truckLLLevelCostF.
        getTotalDistance(depot.getMainTrucks()));
  }

  public void setTotalTravelTime(Object o) {
    VRPTWDepot depot = (VRPTWDepot) o;
    depot.getAttributes().setTotalTravelTime( ProblemInfo.truckLLLevelCostF.
        getTotalTravelTime(depot.getMainTrucks()));
  }

  public void setMaxTravelTime(Object o) {
    VRPTWDepot depot = (VRPTWDepot) o;
    depot.getAttributes().setMaxTravelTime(ProblemInfo.truckLLLevelCostF.
        getMaxTravelTime(depot.getMainTrucks()));
  }

  public void setAvgTravelTime(Object o) {
    VRPTWDepot depot = (VRPTWDepot) o;
    depot.getAttributes().setAvgTravelTime(ProblemInfo.truckLLLevelCostF.
        getAvgTravelTime(depot.getMainTrucks()));
  }

  /** @todo Might not need CrossRoadPenaltyCost and TurnAroundPenaltyCost */
  public void calculateTotalsStats(Object o) {
    setTotalDemand(o);
    setTotalDistance(o);
    setTotalTravelTime(o);
    setMaxTravelTime(o);
    setAvgTravelTime(o);
    //setTotalCrossRoadPenaltyCost(o);
    // setTotalTurnAroundPenaltyCost(o);
    setTotalCost(o);
    //setTotalConstraintCost(o);
  }
}
