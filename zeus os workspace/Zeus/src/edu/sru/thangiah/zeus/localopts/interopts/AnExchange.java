package edu.sru.thangiah.zeus.localopts.interopts;

import edu.sru.thangiah.zeus.core.*;
import java.util.Vector;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class AnExchange {
  public NodesLinkedList p;
  public NodesLinkedList q;

  public Vector nodesFromPtoQ;
  public Vector nodesFromQtoP;

  public double cost;

  public AnExchange() {
    nodesFromPtoQ = new Vector();
    nodesFromQtoP = new Vector();
    cost = Double.MAX_VALUE;
  }

  public void performExchange() throws Exception {
    Nodes retNode = null; //this is used to check if the node exists in the route

    for (int i = 0; i < nodesFromPtoQ.size(); i++) {
      Nodes pToq = (Nodes) nodesFromPtoQ.elementAt(i);
      if (pToq == null) {
        throw new Exception("null node in nodesFromPtoQ");
      }

      //remove node from p
      retNode = p.removeNodes(pToq);
      //check for an error
      if (retNode == null) {
        throw new Exception("node " + pToq.getIndex() + " not in " + p.toString());
      }
    }

    for (int i = 0; i < nodesFromQtoP.size(); i++) {
      Nodes qTop = (Nodes) nodesFromQtoP.elementAt(i);
      if (qTop == null) {
        throw new Exception("null node in nodesFromPtoQ");
      }

      //remove node from q
      retNode = q.removeNodes(qTop);
      //check for an error
      if (retNode == null) {
        throw new Exception("node " + qTop.getIndex() +
                            " not in " + q);
      }
    }

    for (int i = 0; i < nodesFromPtoQ.size(); i++) {
      Nodes pToq = (Nodes) nodesFromPtoQ.elementAt(i);
      if (pToq == null) {
        throw new Exception("null node in nodesFromPtoQ");
      }

      //insert the node to q
      retNode = q.insertNodes(pToq);
      //check for an error
      if (retNode == null) {
        throw new Exception("node " + pToq.getIndex() +
                            " not inserted into " + q);
      }
    }

    for (int i = 0; i < nodesFromQtoP.size(); i++) {
      Nodes qTop = (Nodes) nodesFromQtoP.elementAt(i);
      if (qTop == null) {
        throw new Exception("null node in nodesFromPtoQ");
      }

      //insert the node to p
      retNode = p.insertNodes(qTop);
      //check for an error
      if (retNode == null) {
        throw new Exception("node " + qTop.getIndex() +
                            " not inserted into " + p);
      }
    }

    String qtopString = "", ptoqString = "";
    for (int i = 0; i < nodesFromQtoP.size(); i++) {
      Nodes qTop = (Nodes) nodesFromQtoP.elementAt(i);
      qtopString += " " + qTop.getIndex();
    }
    for (int i = 0; i < nodesFromPtoQ.size(); i++) {
      Nodes pToq = (Nodes) nodesFromPtoQ.elementAt(i);
      ptoqString += pToq.getIndex();
    }

    ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(p);
    ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(q);
  }

  /**
   * This will determine if one exchange is equal to another
   * @param exchange AnExchange exchange to compare
   * @return boolean true-same, false-different
   */
  public boolean equals(AnExchange exchange) {
    if (p != exchange.p ||
        q != exchange.q) {
      return false;
    }

    if (nodesFromPtoQ.size() == exchange.nodesFromPtoQ.size()) {
      //compare the contents for equality
      for (int i = 0; i < nodesFromPtoQ.size(); i++) {
        if (nodesFromPtoQ.elementAt(i) != exchange.nodesFromPtoQ.elementAt(i)) {
          return false;
        }
      }
    }
    else {
      return false;
    }

    if (nodesFromQtoP.size() == exchange.nodesFromQtoP.size()) {
      for (int i = 0; i < nodesFromQtoP.size(); i++) {
        if (nodesFromQtoP.elementAt(i) != exchange.nodesFromQtoP.elementAt(i)) {
          return false;
        }
      }
    }
    else {
      return false;
    }

    return true;
  }
}
