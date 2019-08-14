package edu.sru.thangiah.zeus.vrptw.vrptwqualityassurance;

import edu.sru.thangiah.zeus.qualityassurance.*;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
/** @todo Need to document the variables and the parameters */
public class VRPTWQADepotLinkedList
    extends QADepotLinkedList
    implements java.io.Serializable, java.lang.Cloneable {
  public VRPTWQADepotLinkedList() {
  }

  public boolean checkDistanceConstraint() {
    boolean status = true;
    for (int i = 0; i < getDepots().size(); i++) {
      VRPTWQADepot depot = (VRPTWQADepot) getDepots().elementAt(i);
      status = status && depot.checkDistanceConstraint(depot);
    }
    return status;
  }

  public boolean checkCapacityConstraint() {
   boolean status = true;
   for (int i = 0; i < getDepots().size(); i++) {
     VRPTWQADepot depot = (VRPTWQADepot) getDepots().elementAt(i);
     status = status && depot.checkCapacityConstraint();
   }
   return status;
 }


}
