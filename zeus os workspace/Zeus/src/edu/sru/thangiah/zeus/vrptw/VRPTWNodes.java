package edu.sru.thangiah.zeus.vrptw;

//import the parent class
import edu.sru.thangiah.zeus.core.Nodes;
import edu.sru.thangiah.zeus.core.Shipment;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class VRPTWNodes
    extends Nodes
    implements java.io.Serializable, java.lang.Cloneable {
	
	private VRPTWShipment theShipment;
	private Double b;
	private Double s;
	private Double e;
	private Double l;
	
  public VRPTWNodes() {
	  this.theShipment = null;
	  this.b = null;
	  this.e = null;
	  this.l = null;
	  this.s = null;
  }

  /**
   * Constructor
   * @param s shipment containing this cells information
   */
  public VRPTWNodes(VRPTWShipment s) {
    this.theShipment = s;
    this.b = null;
    this.e = (Double) (double) this.theShipment.getEarly();
    this.l = (Double) (double) this.theShipment.getLate();
    this.s = (Double) (double) this.theShipment.getDuration();
  }

  /**
   * Returns the next point cell in the linked list
   * @return next point cell
   */
  public VRPTWNodes getVRPTWNext() {
    return (VRPTWNodes) next;
  }
  
  public double getBeginTime()
  {
	  if(this.b != null)
		  return this.b;
	  else
		  return -1.0;
  }
  
  public double getEarly()
  {
	  return this.e;
  }
  
  public double getLate()
  {
	  return this.l;
  }
  
  public double getServiceTime()
  {
	  return this.s;
  }
  
  public VRPTWShipment getShipment()
  {
	  return this.theShipment;
  }
  
  public void setShipment(VRPTWShipment s)
  {
	  this.theShipment = s;
  }
  
  public int getIndex()
  {
	  return this.theShipment.getIndex();
  }
  
  public int getDemand()
  {
	  return this.theShipment.getDemand();
  }
  
  

  
  public void setBeginTime(double newB)
  {
	  this.b = newB;
  }
  
  public void setEarly(double newE)
  {
	  this.e = newE;
  }
  
  public void setLate(double newL)
  {
	  this.l = newL;
  }
  
  public void setServiceTime(double newS)
  {
	  this.s = newS;
  }

  /**
   * Creates a copy of this node and returns it. It will not create the next
   * and prev links, because this would cause infinate recursion. These are
   * set in the nodes linked list clone() function.
   * @return Object node clone
   */
  public Object clone() {
    VRPTWNodes clonedNode = new VRPTWNodes();

    clonedNode.theShipment = (VRPTWShipment) this.theShipment.clone();
    clonedNode.b = this.getBeginTime();
    clonedNode.s = this.getServiceTime();
    clonedNode.e = this.getEarly();
    clonedNode.l = this.getLate();

    return clonedNode;
  }

}
