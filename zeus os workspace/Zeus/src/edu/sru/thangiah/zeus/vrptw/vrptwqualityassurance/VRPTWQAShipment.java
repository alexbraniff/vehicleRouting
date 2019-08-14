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
public class VRPTWQAShipment
    extends QAShipment
    implements java.io.Serializable, java.lang.Cloneable {
	
	private int e;
	private int l;
	private int duration;
	
  public VRPTWQAShipment() {
  }
  
  public int getEarly()
  {
	  return this.e;
  }
  
  public int getLate()
  {
	  return this.l;
  }
  
  public int getDuration()
  {
	  return this.duration;
  }
  
  public void setEarly(int e)
  {
	  this.e = e;
  }
  
  public void setLate(int l)
  {
	  this.l = l;
  }
  
  public void setDuration(int d)
  {
	  this.duration = d;
  }
}
