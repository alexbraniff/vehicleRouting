package edu.sru.thangiah.zeus.localopts.intraopts;

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

public abstract class IntraOptimization
    extends Optimization {

  public abstract int executeFirstFirst(NodesLinkedList mainNodes);

  public abstract int executeFirstBest(NodesLinkedList mainNodes);

  public abstract OptimizationUnit executeBestBest(NodesLinkedList mainNodes);
}
