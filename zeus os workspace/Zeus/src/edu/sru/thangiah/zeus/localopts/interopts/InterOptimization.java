package edu.sru.thangiah.zeus.localopts.interopts;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.localopts.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public abstract class InterOptimization
    extends Optimization {
  public abstract int executeFirstFirst(NodesLinkedList route1,
                                        NodesLinkedList route2);

  public abstract int executeFirstBest(NodesLinkedList route1,
                                       NodesLinkedList route2);

  public abstract OptimizationUnit executeBestBest(NodesLinkedList route1,
      NodesLinkedList route2);
}
