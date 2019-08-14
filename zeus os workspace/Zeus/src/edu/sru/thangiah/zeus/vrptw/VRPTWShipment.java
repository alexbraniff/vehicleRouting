package edu.sru.thangiah.zeus.vrptw;

//import the parent class
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

public class VRPTWShipment
    extends Shipment
    implements java.io.Serializable, java.lang.Cloneable
    {

  private int early; // earliest arrival time
  private int late; // latest arrival time
  private int duration; // time spent at node

  public VRPTWShipment()
  {
  }

  /**
   * Constructor
   * @param i index
   * @param x x-coordinate
   * @param y y-coordinate
   * @param q demand
   * @param e earliest arrival time
   * @param l latest arrival time
   * @param t time at node
   */
  public VRPTWShipment(int i, int x, int y, int q, int e, int l, int d)
  {
    //super(i, x, y, q, d, t, p);
    setIndex(i);
    setxCoord(x);
    setyCoord(y);
    setDemand(q);
    setEarly(e);
    setLate(l);
    setDuration(d);
  }
  
  public VRPTWShipment getVRPTWNext()
  {
	  return (VRPTWShipment) super.getNext();
  }

  /**
   * Returns the value of early
   * @return early int
   */
  public int getEarly()
  {
    return early;
  }

  /**
   * Returns the value of late
   * @return late int
   */
  public int getLate()
  {
    return late;
  }

  /**
   * Returns the value of duration
   * @return duration int
   */
  public int getDuration()
  {
    return duration;
  }

  /**
   * Sets the value of early
   * @param e int
   */
  public void setEarly(int e)
  {
    early = e;
  }

  /**
   * Sets the value of late
   * @param l int
   */
  public void setLate(int l)
  {
    late = l;
  }

  /**
   * Sets the value of duration
   * @param d int
   */
  public void setDuration(int d)
  {
    duration = d;
  }
  
  public String toString()
  {
	  /*
	    setIndex(i);
	    setxCoord(x);
	    setyCoord(y);
	    setDemand(q);
	    setEarly(e);
	    setLate(l);
	    setDuration(d);
	    */
	  String result = "";
	  result += "Shipment #" + this.getIndex();
	  result += ":\n\tx: " + this.getXCoord();
	  result += "\n\ty: " + this.getYCoord();
	  result += "\n\tq: " + this.getDemand();
	  result += "\n\te: " + this.getEarly();
	  result += "\n\tl: " + this.getLate();
	  result += "\n\td: " + this.getDuration();
	  return result;
  }
  
  public Object clone()
  {
	VRPTWShipment clonedShipment = new VRPTWShipment(this.getIndex(), (int) this.getXCoord(), (int) this.getYCoord(), this.getDemand(), this.getEarly(), this.getLate(), this.getDuration());
  	return clonedShipment;
  }

}