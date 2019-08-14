package edu.sru.thangiah.zeus.vrptw;

//import the parent class
import edu.sru.thangiah.zeus.core.Attributes;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class VRPTWAttributes
    extends Attributes
    implements java.io.Serializable, java.lang.Cloneable {
	
	private double totalWaitTime;
	private double maxWaitTime;
	private double avgWaitTime;
	private double totalServiceTime;
	private double maxServiceTime;
	private double avgServiceTime;
	private double totalPenalty;
	
  public VRPTWAttributes()
  {
	  totalWaitTime = 0;
	  maxWaitTime = 0;
	  avgWaitTime = 0;
	  totalServiceTime = 0;
	  maxServiceTime = 0;
	  avgServiceTime = 0;  
  }
  
  public double getTotalPenalty()
  {
	  return this.totalPenalty;
  }
  
  public double getTotalWaitTime()
  {
	  return this.totalWaitTime;
  }
  
  public double getMaxWaitTime()
  {
	  return this.maxWaitTime;
  }
  
  public double getAvgWaitTime()
  {
	  return this.avgWaitTime;
  }
  
  public double getTotalServiceTime()
  {
	  return this.totalServiceTime;
  }
  
  public double getMaxServiceTime()
  {
	  return this.maxServiceTime;
  }
  
  public double getAvgServiceTime()
  {
	  return this.avgServiceTime;
  }
  
  public void setTotalPenalty(double p)
  {
	  this.totalPenalty = p;
  }
  
  public void setTotalWaitTime(double wt)
  {
	  this.totalWaitTime = wt;
  }
  
  public void setMaxWaitTime(double wt)
  {
	  this.maxWaitTime = wt;
  }
  
  public void setAvgWaitTime(double wt)
  {
	  this.avgWaitTime = wt;
  }
  
  public void setTotalServiceTime(double st)
  {
	  this.totalServiceTime = st;
  }
  
  public void setMaxServiceTime(double st)
  {
	  this.maxServiceTime = st;
  }
  
  public void setAvgServiceTime(double st)
  {
	  this.avgServiceTime = st;
  }
}
