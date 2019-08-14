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
public class VRPTWQADepot
    extends QADepot
    implements java.io.Serializable, java.lang.Cloneable {
  public VRPTWQADepot() {
  }

  public boolean checkDistanceConstraint(VRPTWQADepot depot) {
    boolean status = true;
    for (int i = 0; i < getTrucks().size(); i++) {
      VRPTWQATruck truck = (VRPTWQATruck) getTrucks().elementAt(i);
      truck.checkDistanceConstraint(truck);
    }
    return status;
  }

  public boolean checkCapacityConstraint() {
    boolean status = true;
    for (int i = 0; i < getTrucks().size(); i++) {
      VRPTWQATruck truck = (VRPTWQATruck) getTrucks().elementAt(i);
      truck.checkCapacityConstraint(truck);
    }
    return status;
  }


}
