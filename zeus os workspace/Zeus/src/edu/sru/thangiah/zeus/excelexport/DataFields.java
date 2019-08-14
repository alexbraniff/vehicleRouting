package edu.sru.thangiah.zeus.excelexport;

import java.util.Vector;

public class DataFields {
	
	private int nodes;
	
	private int depots;
	
	// Keep track of each depot's x y coords
	private Vector dx;
	private Vector dy;
	
	private int capacity;
	private int duration;
	
	private Vector nx;
	private Vector ny;
	private Vector nDemand;
	private Vector nEarly;
	private Vector nLate;
	private Vector nTime;

	public DataFields()
	{
		dx = new Vector();
		dy = new Vector();
		
		nx = new Vector();
		ny = new Vector();
		nDemand = new Vector();
		nEarly = new Vector();
		nLate = new Vector();
		nTime = new Vector();
	}
	
	public int getDepotX(int i)
	{
		return (int) dx.get(i);
	}
	
	public Vector getAllDepotX()
	{
		return dx;
	}
	
	public void setDepotX(int i, int x)
	{
		dx.add(i, x);
	}
	
	public void appendDepotX(int x)
	{
		// Insert x into the last position of vector
		dx.add(x);
	}
	
	public void setAllDepotX(Vector xvector)
	{
		dx = xvector;
	}
	
	public int getDepotY(int i)
	{
		return (int) dy.get(i);
	}
	
	public Vector getAllDepoty()
	{
		return dy;
	}
	
	public void setDepotY(int i, int y)
	{
		dy.add(i, y);
	}
	
	public void appendDepotY(int y)
	{
		// Insert y into the last position of vector
		dy.add(y);
	}
	
	public void setAllDepotY(Vector yvector)
	{
		dy = yvector;
	}
	
	public int getNumNodes()
	{
		// Return size of whichever depot coordinate Vector
		return this.nx.size();
	}
	
	public int getNodes()
	{
		return nodes;
	}
	
	public void setNodes(int n)
	{
		nodes = n;
	}
	
	public int getNumDepots()
	{
		// Return size of whichever depot coordinate Vector
		return this.dx.size();
	}

	public int getDepots()
	{
		return depots;
	}
	
	public void setDepots(int d)
	{
		depots = d;
	}
	
	public int getCapacity()
	{
		return capacity;
	}
	
	public void setCapacity(int c)
	{
		capacity = c;
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	public void setDuration(int D)
	{
		duration = D;
	}
	
	public int getNodeX(int i)
	{
		return (int) nx.get(i - 1);
	}
	
	public Vector getAllNodeX()
	{
		return nx;
	}
	
	public void setNodeX(int i, int x)
	{
		nx.add(i - 1, x);
	}
	
	public void appendNodeX(int x)
	{
		nx.add(x);
	}
	
	public void setAllNodeX(Vector xvector)
	{
		nx = xvector;
	}
	
	public int getNodeY(int i)
	{
		return (int) ny.get(i - 1);
	}
	
	public Vector getAllNodeY()
	{
		return ny;
	}
	
	public void setNodeY(int i, int y)
	{
		ny.add(i - 1, y);
	}
	
	public void appendNodeY(int y)
	{
		ny.add(y);
	}
	
	public void setAllNodeY(Vector yvector)
	{
		ny = yvector;
	}
	
	public int getNodeDemand(int i)
	{
		return (int) nDemand.get(i - 1);
	}
	
	public Vector getAllNodeDemand()
	{
		return nDemand;
	}
	
	public void setNodeDemand(int i, int cd)
	{
		nDemand.add(i - 1, cd);
	}
	
	public void appendNodeDemand(int cd)
	{
		nDemand.add(cd);
	}
	
	public void setAllNodeDemand(Vector dvector)
	{
		nDemand = dvector;	
	}
	
	public int getNodeEarly(int i)
	{
		return (int) nEarly.get(i - 1);
	}
	
	public Vector getAllNodeEarly()
	{
		return nEarly;
	}
	
	public void setNodeEarly(int i, int ce)
	{
		nEarly.add(i - 1, ce);
	}
	
	public void appendNodeEarly(int ce)
	{
		nEarly.add(ce);
	}
	
	public void setAllNodeEarly(Vector evector)
	{
		nEarly = evector;	
	}
	
	public int getNodeLate(int i)
	{
		return (int) nLate.get(i - 1);
	}
	
	public Vector getAllNodeLate()
	{
		return nLate;
	}
	
	public void setNodeLate(int i, int cl)
	{
		nLate.add(i - 1, cl);
	}
	
	public void appendNodeLate(int cl)
	{
		nLate.add(cl);
	}
	
	public void setAllNodeLate(Vector lvector)
	{
		nLate = lvector;	
	}
	
	public int getNodeTime(int i)
	{
		return (int) nTime.get(i - 1);
	}
	
	public Vector getAllNodeTime()
	{
		return nTime;
	}
	
	public void setNodeTime(int i, int ct)
	{
		nTime.add(i - 1, ct);
	}
	
	public void appendNodeTime(int ct)
	{
		nTime.add(ct);
	}
	
	public void setAllNodeTime(Vector tvector)
	{
		nTime = tvector;	
	}
	
	public boolean isValid()
	{
		return true;
	}
	
	public String toString()
	{
		String result = "";

		result += "#Nodes(" + getNodes() + ")\n";
		result += "#Depots(" + getDepots() + ")\n";
		result += "Max Capacity(" + getCapacity() + ")\n";
		result += "Max Duration(" + getDuration() + ")\n";
		result += "\n";
		for(int i = 0; i < getNodes(); i++)
		{
			result += "Node#" + i;
			result += "(x:" + getNodeX(i) + ",";
			result += " y:" + getNodeY(i) + ",";
			result += " demand:" + getNodeDemand(i) + ",";
			result += " ready:" + getNodeEarly(i) + ",";
			result += " due:" + getNodeLate(i) + ",";
			result += " time:" + getNodeTime(i) + ")";
			result += "\n";
		}
		result += "\n";
		for(int i = 0; i < getDepots(); i++)
		{
			result += "Depot#" + i;
			result += "(x:" + getDepotX(i) + ",";
			result += " y:" + getDepotY(i) + ")";
		}
		return result;
	}
}
