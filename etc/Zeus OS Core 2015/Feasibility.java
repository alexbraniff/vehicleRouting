package edu.sru.thangiah.zeus.core;

/**
 * This class will determine at any time if the route is feasible.
 * <p>Title: Feasibility</p>
 * <p>Description: This class will determine at any time if the route is
 * feasible. The purpose of this class is to abstract out the feasibility
 * of the routes so they can be easily modified.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0

 */
public class Feasibility
    implements java.io.Serializable, java.lang.Cloneable {
 private double maxDuration;
 private double maxDistance;
 private double maxTravelTime;
 private double maxCapacity;
 private int maxStops;
 private NodesLinkedList thisRoute;

  /**
   * Default constructor
   */
  public Feasibility() {

  }

  /**
   * Constructor for the feasibilty, will send the constants as well as a
   * pointer to the route that will be checked
   * @param thisR the route
   */
  public Feasibility(NodesLinkedList thisR) {
    thisRoute = thisR;
  }

  /**
   * Constructor for the feasibilty, will send the constants as well as a
   * pointer to the route that will be checked
   * @param maxd max duration
   * @param maxq max capacity
   * @param thisR the route
   */
  public Feasibility(int maxd, int maxq, NodesLinkedList thisR) {
    //maxDuration = maxd;
    //maxCapacity = maxq;
    thisRoute = thisR;
  }

  /**
   * This function checks the feasibility of the route.
   * @return true if feasible, false if not.
   */
 /* public boolean isFeasible() {
    double currentDistance;
    double currentDemand;

    currentDistance = ProblemInfo.nodesLLLevelCostF.getTotalDistance(thisRoute);
    currentDemand = ProblemInfo.nodesLLLevelCostF.getTotalDemand(thisRoute);

    if ( (currentDistance <= maxDuration) && (currentDemand <= maxCapacity)) {
      return true;
    }
    else {
      return false;
    }
  }*/

 /**
   * This function checks the feasibility of the route.
   * This has to be defined by the inheriting class
   * @return true if feasible, false if not.
   */
public boolean isFeasible() {
  System.out.println("Error: Is Feasible is being called from the Feasibility class");
  return false;

  }

  public double getMaxDistance() {
	return maxDistance;
}

public void setMaxDistance(double maxDistance) {
	this.maxDistance = maxDistance;
}

public double getMaxTravelTime() {
	return maxTravelTime;
}

public void setMaxTravelTime(double maxTravelTime) {
	this.maxTravelTime = maxTravelTime;
}

public double getMaxCapacity() {
	return maxCapacity;
}

public void setMaxCapacity(double maxCapacity) {
	this.maxCapacity = maxCapacity;
}

public int getMaxStops() {
	return maxStops;
}

public void setMaxStops(int maxStops) {
	this.maxStops = maxStops;
}

public NodesLinkedList getRoute() {
	return thisRoute;
}

public void setRoute(NodesLinkedList thisRoute) {
	this.thisRoute = thisRoute;
}

/**
   * Creates a copy of the feasibility object.
   * This will not create a clone of the nodes linked list, because it will
   * create infinate recursion in the nodes linked list's clone() funciton.
   * The setRoute() function must be used after cloning.
   * @return Object feasibility clone
   */
  public Object clone() {
    Feasibility clonedFeasibility = new Feasibility();

    clonedFeasibility.maxCapacity = this.maxCapacity;
    clonedFeasibility.maxDuration = this.maxDuration;

    return clonedFeasibility;
  }
}
