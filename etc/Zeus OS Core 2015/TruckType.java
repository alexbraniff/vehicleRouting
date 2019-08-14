package edu.sru.thangiah.zeus.core;

/**
 * Definition of a truck type
 * <p>Title: TruckType</p>
 * <p>Description: Definition of a truck type</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */
public class TruckType
    implements java.io.Serializable, java.lang.Cloneable {
  private int truckNo;
  private double fixedCost = 0;
  private double variableCost = 1;
  private double carrierCost = 1;
  private double maxDuration;
  private double maxDistance;
  private double maxTravelTime;
  private float maxCapacity;
  private float minCapacity;
  private float maxVolume;
  private float actualCapacity; //max capacity is a percent of the actual capacity
  private String serviceType;
  private String carrierName;
  private String carrierId;
  private String busId;
  private String routeNo;
  private String vinNo;
  private String yearOfBus;
  private String ageOfBus;
  
  //Added 3/7/2014
  //vehicle compatibility
  
  //contractor information
  private String  contractorName;
  private double contractorCost;
  
  //available date and time
  private String earliestAvailDate; 
  private String earliestAvailTime;
  private String latestAvailDate;
  private String latestAvailTime;
  
  //trailer information
  private int noOfTrailers;		   //has to be 1 and less than 2
  private String[] trailerType1;     //
  private int	trailer1Capacity;
  private String trailer1Dimension;
  private String[] trailerType2;
  private int	trailer2Capacity;
  private String trailer2Dimension;
  
  //GPS capability	
  private boolean truckTracking;		//GPS capable
  
  //maximum number of stops that can be made by the truck
  private int maxStops;

  /**
   * Default constructor.
   */
  public TruckType() {

  }

  /**
   * Constructor
   * @param N type number
   * @param D max duration
   * @param Q max capacity
   * @param s type of customers the truck can service
   */
  public TruckType(int N, int D, int Q, String s) {
    truckNo = N;
    serviceType = s;

    if (D == 0) {
      maxDuration = Integer.MAX_VALUE;
    }
    else {
      maxDuration = D;
    }

    if (Q == 0) {
      maxCapacity = Integer.MAX_VALUE;
    }
    else {
      maxCapacity = Q;
    }

    fixedCost = maxCapacity;
    variableCost = (double) maxCapacity / 1000;
  }

  /**
   * Returns the type number
   * @return the type number
   */
  public int getTruckNo() {
    return truckNo;
  }

  /**
   * Set the truck type number
   */
  public void setTruckNo(int tNo) {
    truckNo = tNo;
  }

  /**
   * Returns the type of customers this type of truck can service
   * @return service type
   */
  public String getServiceType() {
    return serviceType;
  }

  /**
   * Set this type's service type
   */
  public void setServiceType(String mSer) {
    serviceType = mSer;
  }

  /**
   * Returns this type's max duration
   * @return max duration
   */
  public double getMaxDuration() {
    return maxDuration;
  }

  /**
   * Set this type's max duration
   */
  public void setMaxDuration(double mDur) {
    maxDuration = mDur;
  }

  /**
   * Returns this type's max travel time
   * @return max travel time
   */
  public double getMaxTravelTime() {
    return maxTravelTime;
  }

  /**
   * Set this type's max travel time
   */
  public void setMaxTravelTime(double mTravTime) {
    maxTravelTime = mTravTime;
  }

  /**
    * Returns this type's max distance
    * @return max distance
    */
   public double getMaxDistance() {
     return maxDistance;
   }

   /**
    * Set this type's max distance
    */
   public void setMaxDistance(double mDist) {
     maxDistance = mDist;
   }



  /**
   * Returns this type's max capacity
   * @return max capacity
   */
  public float getMaxCapacity() {
    return maxCapacity;
  }

  /**
   * Set this type's max capacity
   */
  public void setMaxCapacity(float mCost) {
    maxCapacity = mCost;
  }

  public float getMaxVolume() {
	return maxVolume;
}

public void setMaxVolume(float maxVolume) {
	this.maxVolume = maxVolume;
}

/**
   * Returns this type's actual capacity
   * Max capacity is a percent of the actual capacity
   * @return max capacity
   */
  public float getActualCapacity() {
    return actualCapacity;
  }

  /**
   * Set this type's actual capacity
   * Max capacity is a percent of the actual capacity
   */
  public void setActualCapacity(float mCost) {
    actualCapacity = mCost;
  }


  /**
   * Returns this type's min capacity
   * @return min capacity
   */
  public float getMinCapacity() {
    return minCapacity;
  }

  /**
   * Set this type's min capacity
   */
  public void setMinCapacity(float mCost) {
    minCapacity = mCost;
  }

  /**
   * Returns the fixed cost for this truck type
   * @return fixed cost
   */
  public double getFixedCost() {
    return fixedCost;
  }

  /**
   * Set the fixed cost for this truck type
   */
  public void setFixedCost(double fixCost) {
    fixedCost = fixCost;
  }

  /**
   * Returns the contractor cost for this truck type
   * @return contractor cost
   */
  public double getContractorCost() {
    return carrierCost;
  }

  /**
   * Set the contractor cost for this truck type
   */
  public void setContractorCost(double contCost) {
	  carrierCost = contCost;
  }

  /**
   * Returns the variable cost for this truck type
   * @return variable cost
   */
  public double getVariableCost() {
    return variableCost;
  }

  /**
   * Set the contractor name for this truck type
   */
  public void setContractorName(String contName) {
	  carrierName = contName;
  }

  /**
   * Returns the contractor name for this truck type
   * @return contractor name
   */
  public String getContractorName() {
    return carrierName;
  }

  /**
  * Set the bus id for this truck type
  */
 public void setBusId(String bId) {
   busId = bId;
 }

 /**
  * Returns the bus id for this truck type
  * @return contractor name
  */
 public String getBusId() {
   return busId;
 }

 /**
  * Returns the route no for this truck type
  * @return contractor name
  */
 public String getRouteNo() {
   return routeNo;
 }

 /**
  * Returns the vin no for this truck type
  * @return contractor name
  */
 public String getVinNo() {
   return vinNo;
 }

 /**
  * Returns the year of the bus for this truck type
  * @return contractor name
  */
 public String getYearOfBus() {
   return yearOfBus;
 }

 /**
  * Returns the age of the bus for this truck type
  * @return contractor name
  */
 public String getAgeOfBus() {
   return ageOfBus;
 }


  /**
   * Set the variable cost for this truck type
   * @return fixed cost
   */
  public void setVariableCost(double varCost) {
    variableCost = varCost;
  }

  public double getCarrierCost() {
	return carrierCost;
}

public void setCarrierCost(double carrierCost) {
	this.carrierCost = carrierCost;
}

public String getCarrierName() {
	return carrierName;
}

public void setCarrierName(String carrierName) {
	this.carrierName = carrierName;
}

public String getCarrierId() {
	return carrierId;
}

public void setCarrierId(String carrierId) {
	this.carrierId = carrierId;
}

public String getEarliestAvailDate() {
	return earliestAvailDate;
}

public void setEarliestAvailDate(String earliestAvailDate) {
	this.earliestAvailDate = earliestAvailDate;
}

public String getEarliestAvailTime() {
	return earliestAvailTime;
}

public void setEarliestAvailTime(String earliestAvailTime) {
	this.earliestAvailTime = earliestAvailTime;
}

public String getLatestAvailDate() {
	return latestAvailDate;
}

public void setLatestAvailDate(String latestAvailDate) {
	this.latestAvailDate = latestAvailDate;
}

public String getLatestAvailTime() {
	return latestAvailTime;
}

public void setLatestAvailTime(String latestAvailTime) {
	this.latestAvailTime = latestAvailTime;
}

public int getNoOfTrailers() {
	return noOfTrailers;
}

public void setNoOfTrailers(int noOfTrailers) {
	this.noOfTrailers = noOfTrailers;
}

public String[] getTrailerType1() {
	return trailerType1;
}

public void setTrailerType1(String[] trailerType1) {
	this.trailerType1 = trailerType1;
}

public int getTrailer1Capacity() {
	return trailer1Capacity;
}

public void setTrailer1Capacity(int trailer1Capacity) {
	this.trailer1Capacity = trailer1Capacity;
}

public String getTrailer1Dimension() {
	return trailer1Dimension;
}

public void setTrailer1Dimension(String trailer1Dimension) {
	this.trailer1Dimension = trailer1Dimension;
}

public String[] getTrailerType2() {
	return trailerType2;
}

public void setTrailerType2(String[] trailerType2) {
	this.trailerType2 = trailerType2;
}

public int getTrailer2Capacity() {
	return trailer2Capacity;
}

public void setTrailer2Capacity(int trailer2Capacity) {
	this.trailer2Capacity = trailer2Capacity;
}

public String getTrailer2Dimension() {
	return trailer2Dimension;
}

public void setTrailer2Dimension(String trailer2Dimension) {
	this.trailer2Dimension = trailer2Dimension;
}

public boolean isTruckTracking() {
	return truckTracking;
}

public void setTruckTracking(boolean truckTracking) {
	this.truckTracking = truckTracking;
}

public int getMaxStops() {
	return maxStops;
}

public void setMaxStops(int maxStops) {
	this.maxStops = maxStops;
}

public void setRouteNo(String routeNo) {
	this.routeNo = routeNo;
}

public void setVinNo(String vinNo) {
	this.vinNo = vinNo;
}

public void setYearOfBus(String yearOfBus) {
	this.yearOfBus = yearOfBus;
}

public void setAgeOfBus(String ageOfBus) {
	this.ageOfBus = ageOfBus;
}

public String toXML() {
    String xml = "";

    xml += (" <TruckType fC=" + fixedCost + " vC=" + variableCost +
            " maxD=" + maxDuration + " maxC=" + maxCapacity + " sT=" +
            serviceType +
            "> </TruckType> ");

    return xml;
  }

  /**
   * Creates an exact copy of this object and returns it
   * @return Object truck type copy
   */
  public Object clone() {
    TruckType clonedTruckType = new TruckType();

    clonedTruckType.fixedCost = this.fixedCost;
    clonedTruckType.maxCapacity = this.maxCapacity;
    clonedTruckType.maxDuration = this.maxDuration;
    clonedTruckType.serviceType = this.serviceType;
    clonedTruckType.truckNo = this.truckNo;
    clonedTruckType.variableCost = this.variableCost;

    return clonedTruckType;
  }
}
