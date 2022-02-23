package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import net.datastructures.Entry;
import support.graph.CS16AdaptableHeapPriorityQueue;
import support.graph.CS16Edge;
import support.graph.CS16GraphVisualizer;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.GraphVertex;
import support.graph.MinSpanForest;

/**
 * In this class you will implement a slightly modified version
 * of the Prim-Jarnik algorithm for generating Minimum Spanning trees.
 * The original version of this algorithm will only generate the 
 * minimum spanning tree of the connected vertices in a graph, given
 * a starting vertex. Like Kruskal's, this algorithm can be modified to 
 * produce a minimum spanning forest with very little effort.
 *
 * See the handout for details on Prim-Jarnik's algorithm.
 * Like Kruskal's algorithm this algorithm makes extensive use of 
 * the decorator pattern, so make sure you know it.
 */
public class MyPrimJarnik<V> implements MinSpanForest<V> {
	
	private CS16Vertex<V> _startVertex;
	
    /** 
     * This method implements Prim-Jarnik's algorithm and extends 
     * it slightly to account for disconnected graphs. You must return 
     * the collection of edges of the Minimum Spanning Forest (MSF) for 
     * the given graph, g.
     * 
     * This algorithm must run in O((|E| + |V|)log(|V|)) time
     * @param g Your graph
     * @param v Only used if you implement the optional animation.
     * @return returns a data structure that contains the edges of your MSF that implements java.util.Collection
     */
    @Override
    public Collection<CS16Edge<V>> genMinSpanForest(Graph<V> g, CS16GraphVisualizer<V> visualizer) {
    	//create a set of all edges to account make sure we add the right edge to mst
    	Iterator<CS16Edge<V>> graphEdges = g.edges();
    	HashSet<CS16Edge<V>> edgeSet = new HashSet<CS16Edge<V>>();
    	while (graphEdges.hasNext()) {
        	CS16Edge<V> next = graphEdges.next();
        	edgeSet.add(next);
    	}
    	//set all vertices to infinite cost 
    	int inf = Integer.MAX_VALUE;
    	MyDecorator<CS16Vertex<V>, Integer> vertexCost = new MyDecorator<>();
    	MyDecorator<CS16Vertex<V>, Entry<Integer, CS16Vertex<V>>> vertexEntry = new MyDecorator<>();
    	MyDecorator<CS16Vertex<V>, CS16Vertex<V>> vertexPrev = new MyDecorator<>();
    	MyDecorator<CS16Edge<V>, Boolean> edgeHasBeenVisited = new MyDecorator<>();
    	//set all vertices to have no previous pointer and to have infinite distance
    	//also choose the starting node 
    	Iterator<CS16Vertex<V>> itr = g.vertices();
    	int i = 0;
        while (itr.hasNext()) {
        	CS16Vertex<V> next = itr.next();
        	vertexCost.setDecoration(next, inf);
        	vertexPrev.setDecoration(next, null);
        	if (i == 0) {
        		_startVertex = next;
        	}
        	i++;
        }
        //Set edges to not have been visited
        Iterator<CS16Edge<V>> itrEdges = g.edges();
        while (itrEdges.hasNext()) {
        	CS16Edge<V> edge = itrEdges.next();
        	edgeHasBeenVisited.setDecoration(edge, false);
        	}
        //set source node cost to zero
        vertexCost.setDecoration(_startVertex,0);
        //make list for MST
        ArrayList<CS16Edge<V>> mst = new ArrayList<CS16Edge<V>>();
        //Instantiate priority queue
        CS16AdaptableHeapPriorityQueue<Integer, CS16Vertex<V>> pq = new CS16AdaptableHeapPriorityQueue<Integer, CS16Vertex<V>>();
        Iterator<CS16Vertex<V>> itr2 = g.vertices();
        while (itr2.hasNext()) {
        	CS16Vertex<V> next = itr2.next();
        	Entry<Integer, CS16Vertex<V>> entry = pq.insert(vertexCost.getDecoration(next), next);
        	vertexEntry.setDecoration(next, entry);
        }
        while (pq.isEmpty() == false) {
        	Entry<Integer, CS16Vertex<V>> v = pq.removeMin();
        	if (vertexPrev.getDecoration(v.getValue()) != null) {
        		if (edgeSet.contains(g.connectingEdge(vertexPrev.getDecoration(v.getValue()),v.getValue()))) {
        			mst.add(g.connectingEdge(vertexPrev.getDecoration(v.getValue()),v.getValue()));
        		}
        		if (edgeSet.contains(g.connectingEdge(v.getValue(),vertexPrev.getDecoration(v.getValue())))) {
        			mst.add(g.connectingEdge(v.getValue(),vertexPrev.getDecoration(v.getValue())));
        		}
        	}
        	//iterate through all edges incident to v
        	Iterator<CS16Edge<V>> itr3 = g.incomingEdges(v.getValue());
        	while (itr3.hasNext()) {
        		CS16Edge<V> edge = itr3.next();
        		if (edgeHasBeenVisited.getDecoration(edge) == false) {
        			edgeHasBeenVisited.setDecoration(edge, true);
        			CS16Vertex<V> u = g.opposite(v.getValue(), edge);
        			if (vertexCost.getDecoration(u) > edge.element()) {
        				vertexCost.setDecoration(u, edge.element()); 
        				vertexPrev.setDecoration(u, v.getValue());
        				pq.replaceKey(vertexEntry.getDecoration(u), vertexCost.getDecoration(u));
        			}
        	}
        	}
        }
    	return mst;
      }
    }
