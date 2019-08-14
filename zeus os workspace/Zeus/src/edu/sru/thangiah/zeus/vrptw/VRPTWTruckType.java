package edu.sru.thangiah.zeus.vrptw;

//import the parent class
import edu.sru.thangiah.zeus.core.TruckType;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class VRPTWTruckType
    extends TruckType
    implements java.io.Serializable, java.lang.Cloneable {
  public VRPTWTruckType() {
  }

  /**
   * Constructor
   * @param N type number
   * @param Q max capacity
   * @param D max duration
   */
  public VRPTWTruckType(int n, float Q, float D) {
    truckNo = n;

    if (Q == 0) {
      maxCapacity = Integer.MAX_VALUE;
    }
    else {
      maxCapacity = Q;
    }

    if (D == 0) {
      maxDuration = Integer.MAX_VALUE;
    }
    else {
      maxDuration = D;
    }

    fixedCost = maxCapacity;
    variableCost = (double) maxCapacity / 1000;
  }

}
