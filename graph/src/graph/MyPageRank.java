package graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.PageRank;

import java.lang.Math; 

/**
 * In this class you will implement one of many different versions
 * of the PageRank algorithm. This algorithm will only work on
 * directed graphs. Keep in mind that there are many different ways
 * to handle sinks.
 *
 * Make sure you review the help slides and handout for details on
 * the PageRank algorithm.
 *
 */
public class MyPageRank<V> implements PageRank<V>  {
	private Graph<V> _g;
	private List<CS16Vertex<V>> _vertices;
	private Map<CS16Vertex<V>, Double> _vertsToRanks;
	private static final double _dampingFactor = 0.85;
	private static final int _maxIterations = 100;
	private static final double _error = 0.01;

	/**
	 * TODO: Feel free to add in anything else necessary to store the information
	 * needed to calculate the rank. Maybe make something to keep track of sinks,
	 * your ranks, and your outgoing edges?
	 */
	private Map<CS16Vertex<V>, Double> _previousRank;
	private List<CS16Vertex<V>> _sinks;
	
	/**
	 * The main method that does the calculations! You'll want to call the methods
	 * that initialize your variables here. You'll also want to decide on a
	 * type of loop - for loop, do while, or while loop - for your calculations.
	 *
	 * @return A Map of every Vertex to its corresponding rank
	 *
	 */
	
	@Override
	public Map<CS16Vertex<V>, Double> calcPageRank(Graph<V> g) {
		this.initializePrivateVariables(g);
		this.setInitialValues();
		this.removeBlacklists(PageRank.blacklist);
		int i = 0;
		this.handleSinks();
		while (i < _maxIterations) {
			for (int j = 0; j < _vertices.size(); j++) {
				_vertsToRanks.put(_vertices.get(j), this.getNewRank(_vertices.get(j)));
			}
			if (this.withinMargin() == true) {
				return _vertsToRanks;
			}
			for (int k = 0; k < _vertices.size(); k++) {
				_previousRank.put(_vertices.get(k), _vertsToRanks.get(_vertices.get(k)));
				_vertsToRanks.put(_vertices.get(k), 0.0);
			}
			i++;	
		}
		return _previousRank;
	}

	/**
	 * Method used to account for sink pages (those with no outgoing
	 * edges). There are multiple ways you can implement this, check
	 * the lecture and help slides!
	 */
	private void handleSinks() {
		for (int i = 0; i < _sinks.size(); i++) {
			for (int j = 0; j < _vertices.size(); j++) {
				_g.insertEdge(_sinks.get(i),_vertices.get(j),null); 
			}
		}
	}

	// Feel free to add helper methods below.
	
	/**
	 * Takes in a graph and initializes all the instance variables.
	 * @param g
	 */
	private void initializePrivateVariables(Graph<V> g) {
		_g = g;
		_vertsToRanks = new HashMap<CS16Vertex<V>, Double>();
		_previousRank = new HashMap<CS16Vertex<V>, Double>();
		_sinks = new ArrayList<CS16Vertex<V>>();
		_vertices = new ArrayList<CS16Vertex<V>>();
	}
	
	/**
	 * Gives values to all the instance variables.
	 */
	private void setInitialValues() {
		Iterator<CS16Vertex<V>> vertices = _g.vertices();
    	while (vertices.hasNext()) {
    		CS16Vertex<V> vertex = vertices.next();
        	_vertices.add(vertex);
        	_previousRank.put(vertex, (1.0/_g.getNumVertices()));
        	if (_g.numOutgoingEdges(vertex) == 0) {
        		_sinks.add(vertex);
        	}
    	}
	}
	
	/**
	 * Returns a boolean that is true if the every vertex's new rank is within the 
	 * margin of convergence and false otherwise.
	 * @return
	 */
	private Boolean withinMargin() {
		double total = 0;
		for (int i = 0; i < _vertices.size(); i++) {
			total = total + _vertsToRanks.get(_vertices.get(i));
			if (Math.abs(_vertsToRanks.get(_vertices.get(i)) - _previousRank.get(_vertices.get(i))) > _error) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Takes in a vertex and calculates it's new rank and returns that double value.
	 * @param vert
	 * @return
	 */
	private double getNewRank(CS16Vertex<V> vert) {
		double rankPart1 = (1.0-_dampingFactor)/(_g.getNumVertices());
		double rankPart2 = 0;
		Iterator<CS16Edge<V>> edges = _g.incomingEdges(vert);
		while (edges.hasNext()) {
			CS16Edge<V> next = edges.next();
			CS16Vertex<V> u = _g.opposite(vert, next);
			rankPart2 = rankPart2 + _previousRank.get(u)/_g.numOutgoingEdges(u);
		}
		double newRank = rankPart1 + (_dampingFactor * rankPart2);
		return newRank;
	}
	
	/**
	 * For the extra credit portion:
	 * Takes in a set of names and if that name is in the vertices of the graph,
	 * all their incoming edges will be changed to outgoing edges to make their
	 * page rank the least in the graph.
	 * @param vert
	 */
	private void removeBlacklists(Set<String> names) {
		for (int i = 0; i < _vertices.size(); i++) {
			if (names.contains(_vertices.get(i).getVertexName())) {
				CS16Vertex<V> badVertex = _vertices.get(i);
				Iterator<CS16Edge<V>> itr = _g.incomingEdges(badVertex);
				while (itr.hasNext()) {
				CS16Edge<V> incomingEdge = itr.next();
				CS16Vertex<V> opVertex = _g.opposite(badVertex, incomingEdge);
				_g.removeEdge(incomingEdge);
				_g.insertEdge(badVertex, opVertex, null);
				}
			}
		}
	}
	
}
