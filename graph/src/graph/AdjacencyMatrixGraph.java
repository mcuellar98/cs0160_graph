package graph;

import static support.graph.Constants.MAX_VERTICES;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.GraphEdge;
import support.graph.GraphVertex;
import support.graph.DirectionException;
import support.graph.InvalidEdgeException;
import support.graph.InvalidVertexException;
import support.graph.NoSuchEdgeException;
import support.graph.NoSuchVertexException;
import support.graph.NodeSequence;
    
/**
 * This class defines a Graph that tracks its edges through the use of an
 * adjacency matrix. Please review the lecture slides and the book before
 * attempting to write this program. An adjacency matrix consists of a 2D array
 * of Vertices, with each vertex of the graph appearing in both dimensions.
 *
 * Since we are using an adjacency matrix, each vertex must have a 'number', so
 * that it can represent an index in one of the dimensional arrays. This
 * assignment is not as trivial as it may appear. Remember that your arrays have
 * a maximum index. Thus, you cannot just up the number for each vertex. Why
 * not? Think about what happens when you constantly add and delete new
 * vertices. You will soon exceed the size of your adjacency matrix array. Note
 * further that this number must be unique.
 * 
 * Make sure your AdjacencyMatrixGraph can be both directed and undirected!
 *
 * Good luck, and as always, start early, start today, start yesterday!
 */
public class AdjacencyMatrixGraph<V> implements Graph<V> {

    // The underlying data structure of your graph: the adjacency matrix
    private CS16Edge<V>[][] _adjMatrix;
    // Sets to store the vertices and edges of your graph
    private Set<CS16Vertex<V>> _vertices;
    private Set<CS16Edge<V>> _edges;
    //number of vertices
    private int _numVertices;
    // boolean that keeps track of directedness of graph
    private boolean _directed;
    private LinkedList<Integer> _vertexNumberQueue;
    private Integer _vertexNumber;

    /**
     * Constructor for your Graph, where among other things, you will most
     * likely want to instantiate your matrix array and your Sets.
     *
     * Takes in a boolean that represents whether the graph will be directed.
     *
     * This must run in O(1) time.
     */
    public AdjacencyMatrixGraph(boolean directed) {
        _adjMatrix = this.makeEmptyEdgeArray();
        _directed = directed;
        _vertices = new HashSet<CS16Vertex<V>>();
        _edges = new HashSet<CS16Edge<V>>();
       _vertexNumberQueue = new LinkedList<Integer>();
       _vertexNumber = 0;
    }

    /**
     * Returns an iterator holding all the Vertices of the graph.
     *
     * <p>
     * This must run in O(1) time.
     * </p>
     * * Note that the visualizer uses this method to display the graph's
     * vertices, so you should implement it first.
     *
     * @return an Iterator containing the vertices of the Graph.
     */
    @Override
    public Iterator<CS16Vertex<V>> vertices() {
    	return _vertices.iterator();
    }

    /**
     * Returns an iterator holding all the edges of the graph.
     *
     * <p>
     * This must run in O(|1|) time.
     * </p>
     *
     * Note that the visualizer uses this method to display the graph's edges,
     * so you should implement it first.
     *
     * @return an Iterator containing the edges of the Graph.
     */
    @Override
    public Iterator<CS16Edge<V>> edges() {
        return _edges.iterator();
    }

    /**
     * Inserts a new Vertex into your Graph. You will want to first generate a
     * unique number for your vertex that falls within the range of your
     * adjacency array. You will then have to add the Vertex to your set of
     * vertices.
     *
     * <p>
     * You will not have to worry about the case where *more* than MAX_VERTICES
     * vertices are in your graph. Your code should, however, be able to hold
     * MAX_VERTICES vertices at any time.
     * </p>
     *
     * <p>
     * This must run in O(1) time.
     * </p>
     * 
     * @param vertElement
     *            the element to be added to the graph as a vertex
     */
    @Override
    public CS16Vertex<V> insertVertex(V vertElement) {
    	CS16Vertex<V> vertex = new GraphVertex<V>(vertElement);
    	if (_vertexNumber < MAX_VERTICES) {
    		vertex.setVertexNumber(_vertexNumber);
    		_vertexNumber++;
    	} else {
    		vertex.setVertexNumber(_vertexNumberQueue.removeFirst());
    	}
    	_vertices.add(vertex);
    	_numVertices++;
        return vertex;
    }

    /**
     * Inserts a new Edge into your Graph. You need to update your adjacency
     * matrix to reflect this new added Edge. In addition, the Edge needs to be
     * added to the edge set. 
     *
     * If the graph is directed, you will only want an edge
     * starting from the first vertex ending at the second vertex. If the graph is
     * undirected, you will want an edge both ways.
     * 
     * <p>
     * This must run in O(1) time.
     * </p>
     * 
     * @param v1
     *            The first vertex of the edge connection.
     * @param v2
     *            The second vertex of the edge connection.
     * @param edgeElement
     *            The element of the newly inserted edge.
     * @return Returns the newly inserted Edge.
     * @throws InvalidVertexException
     *             Thrown when either Vertex is null.
     */
    @Override
    public CS16Edge<V> insertEdge(CS16Vertex<V> v1, CS16Vertex<V> v2, Integer edgeElement) 
    		throws InvalidVertexException {
    	if (v1 == null || v2 == null) {
    		throw new InvalidVertexException("Neither input vertex can be null");
    	} 
    	CS16Edge<V> edge1 = new GraphEdge<V>(edgeElement,v1,v2);
        _edges.add(edge1);
        _adjMatrix[v1.getVertexNumber()][v2.getVertexNumber()] = edge1;
    	if (_directed == false) {
    		CS16Edge<V> edge2 = new GraphEdge<V>(edgeElement,v2,v1);
        	_adjMatrix[v2.getVertexNumber()][v1.getVertexNumber()] = edge2;
        }
    	return edge1;
    }

    /**
     * Removes a Vertex from your graph. You will first have to remove all edges
     * that are connected to this Vertex. (Perhaps you can use other methods you
     * will eventually write to make this easier?) Finally, remove the Vertex
     * from the vertex set.
     * <p>
     * This must run in O(|V|) time.
     * </p>
     *
     * @param vert
     *            The Vertex to remove.
     * @return The element of the removed Vertex.
     * @throws InvalidVertexException
     *             Thrown when the Vertex is null.
     */
    @Override
    public V removeVertex(CS16Vertex<V> vert) throws InvalidVertexException {
    	if (vert == null) {
    		throw new InvalidVertexException("Vertex cannot be null");
    	}
    	for (int i = 0; i < MAX_VERTICES; i++) {
    		_edges.remove(_adjMatrix[i][vert.getVertexNumber()]);
    		_adjMatrix[i][vert.getVertexNumber()] = null;
    		_edges.remove(_adjMatrix[vert.getVertexNumber()][i]);
    		_adjMatrix[vert.getVertexNumber()][i] = null;
    	}
    	_vertexNumberQueue.push(vert.getVertexNumber());
    	_vertices.remove(vert);
    	_numVertices--;
        return vert.element(); 
    }

    /**
     * Removes an Edge from your Graph. You will want to remove all references
     * to it from your adjacency matrix. Don't forget to remove it from the edge
     * set. Make sure to remove only the correct edge if the graph is directed.
     *
     * <p>
     * This must run in O(1) time.
     * </p>
     *
     * @param edge
     *            The Edge to remove.
     * @return The element of the removed Edge.
     * @throws InvalidEdgeException
     *             Thrown when the Edge is null.
     */
    @Override
    public Integer removeEdge(CS16Edge<V> edge) throws InvalidEdgeException {
    	if (edge == null) {
    		throw new InvalidEdgeException("Edge cannot be null");
    	}
    	_adjMatrix[edge.getVertexOne().getVertexNumber()][edge.getVertexTwo().getVertexNumber()] = null;
    	_adjMatrix[edge.getVertexTwo().getVertexNumber()][edge.getVertexOne().getVertexNumber()] = null;
    	_edges.remove(edge);
        return edge.element();
    }

    /**
     * Returns the edge that connects the two vertices. You will want to consult
     * your adjacency matrix to see if they are connected. If so, return that
     * edge, otherwise throw a NoSuchEdgeException.
     * 
     * If the graph is directed, then two nodes are connected if there is an
     * edge from the first vertex to the second. 
     * If the graph is undirected, then two nodes are connected if there is an
     * edge from the first vertex to the second and vice versa. 
     *
     * <p>
     * This must run in O(1) time.
     * </p>
     *
     * @param v1
     *            The first vertex that may be connected.
     * @param v2
     *            The second vertex that may be connected.
     * @return The edge that connects the first and second vertices.
     * @throws InvalidVertexException
     *             Thrown when either vertex is null.
     * @throws NoSuchEdgeException
     *             Thrown when no edge connects the vertices.
     */
    @Override
    public CS16Edge<V> connectingEdge(CS16Vertex<V> v1, CS16Vertex<V> v2)
            throws InvalidVertexException, NoSuchEdgeException {
    	if (v1 == null || v2 == null) {
    		throw new InvalidVertexException("Vertex is null");
    	}
    	if (_directed == true) {
    		if (_adjMatrix[v1.getVertexNumber()][v2.getVertexNumber()] == null) {
    			throw new NoSuchEdgeException("There is no connecting edge");
    		}else {
    			return _adjMatrix[v1.getVertexNumber()][v2.getVertexNumber()];
    		}
    	}else {
    		if (_adjMatrix[v1.getVertexNumber()][v2.getVertexNumber()] == null || 
    				_adjMatrix[v2.getVertexNumber()][v1.getVertexNumber()] == null) {
    			throw new NoSuchEdgeException("There is no connecting edge");
    		} else{
    			return _adjMatrix[v1.getVertexNumber()][v2.getVertexNumber()];
    		}
    	}
    }

    /**
     * Returns an Iterator over all the Edges that are incoming to this Vertex.
     * <p>
     * This must run in O(|V|) time;
     * </p>
     * 
     *
     * @param vert
     *            The vertex to find the incoming edges on.
     * @return Returns an Iterator holding the incoming edges on v.
     * @throws InvalidVertexException
     *             Thrown when the Vertex is null.
     */
    @Override
    public Iterator<CS16Edge<V>> incomingEdges(CS16Vertex<V> vert) throws InvalidVertexException {
    	if (vert == null) {
    		throw new InvalidVertexException("Vertex cannot be null");
    	}
    	Set<CS16Edge<V>> set = new HashSet<CS16Edge<V>>();
    	for (int i = 0; i < MAX_VERTICES; i++) {
    		if (_directed == true) {
    			if (_adjMatrix[i][vert.getVertexNumber()] != null) {
    				set.add(_adjMatrix[i][vert.getVertexNumber()]);
    			}
    		} else{
    			if (_edges.contains(_adjMatrix[i][vert.getVertexNumber()])) {
    				set.add(_adjMatrix[i][vert.getVertexNumber()]);
    			}
    			if (_edges.contains(_adjMatrix[vert.getVertexNumber()][i])){
    				set.add(_adjMatrix[vert.getVertexNumber()][i]);
    			}
    		}
    	}
        return set.iterator();
    }
    
    /** 
     * Returns an Iterator of all the Edges that are outgoing from this vertex.
     * <p>
     * This must run in O(|V|) time;
     * </p>

     * @param vert
     *            The vertex to find the outgoing edges on.
     * @return Returns an Iterator holding the outgoing edges on v.
     * @throws InvalidVertexException
     *             Thrown when the Vertex is null.
     */
    @Override
    public Iterator<CS16Edge<V>> outgoingEdges(CS16Vertex vert) throws InvalidVertexException {
    	if (vert == null) {
    		throw new InvalidVertexException("Vertex cannot be null");
    	}
    	Set<CS16Edge<V>> set = new HashSet<CS16Edge<V>>();
    	for (int i = 0; i < MAX_VERTICES; i++) {
    		if (_directed == true) {
    			if (_adjMatrix[vert.getVertexNumber()][i] != null) {
    				set.add(_adjMatrix[vert.getVertexNumber()][i]);
    			}
    		} else {
    			if (_edges.contains(_adjMatrix[vert.getVertexNumber()][i])) {
    				set.add(_adjMatrix[vert.getVertexNumber()][i]);
    			}
    			if (_edges.contains(_adjMatrix[i][vert.getVertexNumber()])) {
					set.add(_adjMatrix[i][vert.getVertexNumber()]);
    			}
    		}
    	}
        return set.iterator();
    }

    /**
     * Returns an int of the number Edges that are leaving from this Vertex. This should only
     * work if called on a directed graph. This method will be used in MyPageRank.
     * 
     * @param vert
     *            The vertex to to find the outgoing edges on.
     * @return an int
     * @throws InvalidVertexException
     *             Thrown when the Vertex is not valid.
     * @throws DirectionException
     *             Thrown when this method is called on an undirected graph.
     */
    @Override
    public int numOutgoingEdges(CS16Vertex<V> vert) throws InvalidVertexException, DirectionException {
        if (vert == null) {
        	throw new InvalidVertexException("Vertex cannot be null");
        }
        if (_directed == false) {
        	throw new DirectionException("Graph must be directed");
        }
        int outgoingEdges = 0;
        for (int i = 0; i < MAX_VERTICES; i++) {
    		if (_adjMatrix[vert.getVertexNumber()][i] != null) {
    			outgoingEdges++;
    		}
    	}
    	return outgoingEdges;
    }

    /**
     * Returns the Vertex that is on the other side of Edge e opposite of Vertex
     * v. Consulting the adjacency matrix may result in a running time that is
     * too high.
     * 
     * If the edge is not incident on v, then throw a NoSuchVertexException.
     *
     * <p>
     * This must run in O(1) time.
     * </p>
     *
     * @param vert
     *            The first vertex on Edge e.
     * @param edge
     *            The edge connecting Vertex v and the unknown opposite Vertex.
     * @return The opposite Vertex of v across Edge e.
     * @throws InvalidVertexException
     *             Thrown when the Vertex is not valid.
     * @throws InvalidEdgeException
     *             Thrown when the Edge is not valid.
     * @throws NoSuchVertexException
     *             Thrown when Edge e is not incident on v.
     */
    @Override
    public CS16Vertex<V> opposite(CS16Vertex<V> vert, CS16Edge<V> edge)
            throws InvalidVertexException, InvalidEdgeException, NoSuchVertexException {
    	if (vert == null) {
        	throw new InvalidVertexException("Vertex cannot be null");
        }
    	if (edge == null) {
        	throw new InvalidEdgeException("Edge cannot be null");
        }
    	if (edge.getVertexOne()== vert) {
    		return edge.getVertexTwo();
    	} else if (edge.getVertexTwo() == vert) {
    		return edge.getVertexOne();
    	} else{
    		throw new NoSuchVertexException("Edge is not incident to vertex");
    	}
    }

    /**
     * Returns the two Vertices that the Edge e is connected to.
     * 
     * Checking the adjacency matrix may be too costly for this method.
     *
     * <p>
     * This must run in O(1) time.
     * </p>
     * 
     * Note that the visualizer uses this method to display the graph's edges.
     *
     * @param e
     *            The edge to find the connecting Vertex's on.
     * @return a list of Vertex's holding the two connecting vertices.
     * @throws InvalidEdgeException
     *             Thrown when the Edge e is null.
     */
    @Override
    public List<CS16Vertex<V>> endVertices(CS16Edge<V> e) throws InvalidEdgeException {
    	if (e == null) {
    		throw new InvalidEdgeException("Edge cannot be null");
    	}
    	List<CS16Vertex<V>> vertices = new ArrayList<CS16Vertex<V>>();
    	vertices.add(e.getVertexOne());
    	vertices.add(e.getVertexTwo());
    	return vertices;
    }

    /**
     * Returns true if there exists an Edge that starts from Vertex v1 and ends
     * at Vertex v2 for both a directed and undirected graph. For a directed graph
     * two vertices are adjacent if there is an edge from the first vertex to the 
     * second vertex.
     * 
     * <p>
     * This must run in O(1) time.
     * </p>
     * 
     * @param v1
     *            The first Vertex to test adjacency.
     * @param v2
     *            The second Vertex to test adjacency.
     * @return Returns true if the vertices are adjacent.
     * @throws InvalidVertexException
     *             Thrown if either vertex is null.
     */
    @Override
    public boolean areAdjacent(CS16Vertex<V> v1, CS16Vertex<V> v2) throws InvalidVertexException {
    	if (v1 == null || v2 == null) {
    		throw new InvalidVertexException("Vertex cannot be null");
    	}
        return _adjMatrix[v1.getVertexNumber()][v2.getVertexNumber()] != null;
    }

    /**
     * Toggles the directedness of the graph.
     */
    @Override
    public void toggleDirected() {
    	this.clear();
    	_directed = !_directed;
    }

    /**
     * Clears all the vertices and edges from the graph. You will want to also
     * clear the adjacency matrix. Remember the power of Java's garbage
     * collection mechanism. If you re-instantiate something, the instance of
     * what that Object used to be disappears.
     *
     * <p>
     * This must run in O(1) time.
     * </p>
     */
    @Override
    public void clear() {
    	_vertices.clear();
    	_edges.clear();
    	_numVertices = 0;
    	_vertexNumber = 0;
        _vertexNumberQueue = new LinkedList<Integer>();
    	_adjMatrix = this.makeEmptyEdgeArray();
    }

    /**
     * Returns the number of vertices in the graph.
     */
    @Override
	public int getNumVertices() {
		return _numVertices;
	}

    // Do not change this method!
    @SuppressWarnings("unchecked")
    private CS16Edge<V>[][] makeEmptyEdgeArray() {
        return new CS16Edge[MAX_VERTICES][MAX_VERTICES];
    }
}
