package edu.sru.thangiah.zeus.vrptw;

import edu.sru.thangiah.zeus.core.Attributes;
//import the parent class
import edu.sru.thangiah.zeus.core.Depot;
import edu.sru.thangiah.zeus.core.TruckLinkedList;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class VRPTWDepot
    extends Depot
    implements java.io.Serializable, java.lang.Cloneable {
	
  public VRPTWDepot() {
    //set the attributes and the truck linked list
    setAttributes (new VRPTWAttributes());
    setMainTrucks(new VRPTWTruckLinkedList());
  }

  /**
   * Constructor. Creates the depot
   * @param d depot number
   * @param x x-coordinate
   * @param y y-coordinate
   */
  public VRPTWDepot(int d, float x, float y) {
    //The x,y and d (index number)
    //super(d, x, y);
    setDepotNum(d);
    setXCoord(x);
    setYCoord(y);

    setAttributes(new VRPTWAttributes());
    setMainTrucks(new VRPTWTruckLinkedList());
  }
  
  /**
   * Returns the truck linked list
   * @return main trucks
   */
  public VRPTWTruckLinkedList getMainTrucks() {
    return (VRPTWTruckLinkedList)super.getMainTrucks();
  }

  /**
 * Returns the next depot in the linked list
 * @return next depot
 */
public VRPTWDepot getNext() {
  return (VRPTWDepot)super.getNext();
}


}
