package graph;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static support.graph.Constants.MAX_VERTICES;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.DirectionException;
import support.graph.Graph;
import support.graph.InvalidEdgeException;
import support.graph.InvalidVertexException;
import support.graph.NoSuchEdgeException;
import support.graph.NoSuchVertexException;

/**
 * This class tests the functionality of a Graph based on a 'String' type
 * parameter for the vertices. Edge elements are Integers. The general framework
 * of a test case is: - Name the test method descriptively, mentioning what is
 * being tested (it is ok to have slightly verbose method names here) - Set-up
 * the program state (ex: instantiate a heap and insert K,V pairs into it) - Use
 * assertions to validate that the program is in the state you expect it to be
 * See header comments over tests for what each test does
 * 
 * Before each test is run, you can assume that the '_graph' variable is reset to
 * a new instance of the a Graph<String> that you can simply use 'as is'
 * via the methods under the 'DO NOT MODIFY ANYTHING BELOW THIS LINE' line.
 * Of course, please do not modify anything below that, or the above 
 * assumptions may be broken.
 */
@RunWith(Parameterized.class)
public class GraphTest {
    

    // Undirected Graph instance variable
    private Graph<String> _graph;
    // Directed Graph instance variable
    private Graph<String> _dirGraph;
    private String _graphClassName;
	
    /**
     * A simple test for insertVertex, that adds 3 vertices and then checks to
     * make sure they were added by accessing them through the vertices
     * iterator.
     */
    @Test(timeout = 10000)
    public void testInsertVertex() {
        // insert vertices
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");

        // use the vertex iterator to get a list of the vertices in the actual
        // graph
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it = _graph.vertices();
        while (it.hasNext()) {
            actualVertices.add(it.next());
        }

        // assert that the graph state is consistent with what you expect
        assertThat(actualVertices.size(), is(3));
        assertThat(actualVertices.contains(A), is(true));
        assertThat(actualVertices.contains(B), is(true));
        assertThat(actualVertices.contains(C), is(true));
    }

    // Same test as above, but with a directed graph
    @Test(timeout = 10000)
    public void testInsertVertexDirected() {
        // insert vertices
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");

        // use the vertex iterator to get a list of the vertices in the actual
        // graph
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it = _dirGraph.vertices();
        while (it.hasNext()) {
            actualVertices.add(it.next());
        }

        // assert that the graph state is consistent with what you expect
        assertThat(actualVertices.size(), is(3));
        assertThat(actualVertices.contains(A), is(true));
        assertThat(actualVertices.contains(B), is(true));
        assertThat(actualVertices.contains(C), is(true));
    }


    /**
     * A simple test for insertEdges that adds 3 vertices, adds two edges to the
     * graph and then asserts that both edges were in fact added using the edge
     * iterator as well as checks to make sure their from and to vertices were
     * set correctly.
     */
    @Test(timeout = 10000)
    public void testInsertEdgesUndirected() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");

        // use the edge iterator to get a list of the edges in the actual graph.
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 2);

        // use the edge iterator to get a list of the edges in the actual graph.
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _graph.edges();
        while (it.hasNext()) {
            actualEdges.add(it.next());
        }

        // assert that the graph state is consistent with what you expect.
        assertThat(actualEdges.size(), is(2));
        assertThat(actualEdges.contains(ab), is(true));
        assertThat(actualEdges.contains(bc), is(true));
    }


    // Same test as above, but with a directed graph
    @Test(timeout = 10000)
    public void testInsertEdgesDirected() {
        CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");

        // use the edge iterator to get a list of the edges in the actual graph.
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);

        // use the edge iterator to get a list of the edges in the actual graph.
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _dirGraph.edges();
        while (it.hasNext()) {
            actualEdges.add(it.next());
        }

        // assert that the graph state is consistent with what you expect.
        assertThat(actualEdges.size(), is(2));
        assertThat(actualEdges.contains(ab), is(true));
        assertThat(actualEdges.contains(bc), is(true));
    }
    
    /**
     * Tests that three vertices that are added to an undirected graph are all in the iterator
     * returned by the vertices() method.
     */
    @Test(timeout = 10000)
    public void testVerticesUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it = _graph.vertices();
        while (it.hasNext()) {
            actualVertices.add(it.next());
        }
        assertThat(actualVertices.contains(A), is(true));
        assertThat(actualVertices.contains(B), is(true));
        assertThat(actualVertices.contains(C), is(true));
    }
    
    
    // Same test as above but for directed graph.
    @Test(timeout = 10000)
    public void testVerticesDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it = _dirGraph.vertices();
        while (it.hasNext()) {
            actualVertices.add(it.next());
        }
        assertThat(actualVertices.contains(A), is(true));
        assertThat(actualVertices.contains(B), is(true));
        assertThat(actualVertices.contains(C), is(true));
    }
    
    /**
     * Test that all the edges that are inserted in an undirected graph are present
     * in the iterator returned by the edges() method. Will also check that removing
     * an edge from the graph removes that edge from the set.
     */
    @Test(timeout = 10000)
    public void testEdgesIteratorUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 2);
        CS16Edge<String> ac = _graph.insertEdge(A, C, 4);
        
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _graph.edges();
        while (it.hasNext()) {
            actualEdges.add(it.next());
        }
        assertThat(actualEdges.contains(ab), is(true));
        assertThat(actualEdges.contains(bc), is(true));
        assertThat(actualEdges.contains(ac), is(true));
        
      //Check that removing an edge removes it from the set
        _graph.removeEdge(bc);
        
        List<CS16Edge<String>> actualEdges2 = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it2 = _graph.edges();
        while (it2.hasNext()) {
            actualEdges2.add(it2.next());
        }
        assertThat(actualEdges2.contains(ab), is(true));
        assertThat(actualEdges2.contains(ac), is(true));
        assertThat(actualEdges2.contains(bc), is(false));
    }
    
    //Same test as above but with a directed graph
    @Test(timeout = 10000)
    public void testEdgesIteratorDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);
        CS16Edge<String> ac = _dirGraph.insertEdge(A, C, 4);
        
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _dirGraph.edges();
        while (it.hasNext()) {
            actualEdges.add(it.next());
        }
        assertThat(actualEdges.contains(ab), is(true));
        assertThat(actualEdges.contains(bc), is(true));
        assertThat(actualEdges.contains(ac), is(true));
        
        //Check that removing an edge removes it from the set
        _dirGraph.removeEdge(bc);
        
        List<CS16Edge<String>> actualEdges2 = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it2 = _dirGraph.edges();
        while (it2.hasNext()) {
            actualEdges2.add(it2.next());
        }
        assertThat(actualEdges2.contains(ab), is(true));
        assertThat(actualEdges2.contains(ac), is(true));
        assertThat(actualEdges2.contains(bc), is(false));
           
    }
    
    /**
     * This will test that both insertVertex() and getNumVertices() will work by having a simple graph
     * of three vertices and checking the number before and after each insertion. It will
     * also check that all the vertices are in the vertex set. This will be for an undirected graph.
     */
    @Test(timeout = 10000)
    public void testInsertVertexAndGetNumVerticesUndirected() {
    	assertThat(_graph.getNumVertices(), is(0));
    	CS16Vertex<String> A = _graph.insertVertex("A");
    	
    	assertThat(_graph.getNumVertices(), is(1));
        CS16Vertex<String> B = _graph.insertVertex("B");
        
    	assertThat(_graph.getNumVertices(), is(2));
        CS16Vertex<String> C = _graph.insertVertex("C");
        
    	assertThat(_graph.getNumVertices(), is(3));
        
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it = _graph.vertices();
        while (it.hasNext()) {
        	actualVertices.add(it.next());
        }
        assertThat(actualVertices.contains(A), is(true));
        assertThat(actualVertices.contains(B), is(true));
        assertThat(actualVertices.contains(C), is(true));
        
        //Check that removing a vertex removes makes getNumVertices return 1 less number 
        _graph.removeVertex(A);
        assertThat(_graph.getNumVertices(), is(2));
           
    }
    
    //Tests the same as above but with a directed graph
    @Test(timeout = 10000)
    public void testInsertVertexAndGetNumVerticesDirected() {
    	assertThat(_dirGraph.getNumVertices(), is(0));
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
    	
    	assertThat(_dirGraph.getNumVertices(), is(1));
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        
    	assertThat(_dirGraph.getNumVertices(), is(2));
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        
    	assertThat(_dirGraph.getNumVertices(), is(3));
        
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it = _dirGraph.vertices();
        while (it.hasNext()) {
        	actualVertices.add(it.next());
        }
        assertThat(actualVertices.contains(A), is(true));
        assertThat(actualVertices.contains(B), is(true));
        assertThat(actualVertices.contains(C), is(true));
        
        //Check that removing a vertex removes makes getNumVertices return 1 less number 
        _dirGraph.removeVertex(A);
        assertThat(_dirGraph.getNumVertices(), is(2));
           
    }
    
  	/**
  	 * Test the removeVertex() method by using the getNumVertices() methods and 
  	 * looking a the vertex set to make sure a vertex was removed. This will be
  	 * for an undirected graph.
  	 */
    @Test(timeout = 10000)
    public void testRemoveVertexUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        
    	assertThat(_graph.getNumVertices(), is(3));
        
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it = _graph.vertices();
        while (it.hasNext()) {
        	actualVertices.add(it.next());
        }
        assertThat(actualVertices.contains(A), is(true));
        assertThat(actualVertices.contains(B), is(true));
        assertThat(actualVertices.contains(C), is(true));
        
        //Check that removing a vertex removes makes getNumVertices return 1 less number 
        //and removes it from the set
        _graph.removeVertex(A);
        
        List<CS16Vertex<String>> actualVertices2 = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it2 = _graph.vertices();
        while (it2.hasNext()) {
        	actualVertices2.add(it2.next());
        }
        assertThat(actualVertices2.contains(A), is(false));
        assertThat(actualVertices2.contains(B), is(true));
        assertThat(actualVertices2.contains(C), is(true));
        assertThat(_graph.getNumVertices(), is(2));
           
    }
    
    //Tests same as above but for directed graph
    @Test(timeout = 10000)
    public void testRemoveVertexDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        
    	assertThat(_dirGraph.getNumVertices(), is(3));
        
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it = _dirGraph.vertices();
        while (it.hasNext()) {
        	actualVertices.add(it.next());
        }
        assertThat(actualVertices.contains(A), is(true));
        assertThat(actualVertices.contains(B), is(true));
        assertThat(actualVertices.contains(C), is(true));
        
        //Check that removing an edge removes makes getNumVertices return 1 less number 
        //and removes it from the set
        _dirGraph.removeVertex(A);
        
        List<CS16Vertex<String>> actualVertices2 = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it2 = _dirGraph.vertices();
        while (it2.hasNext()) {
        	actualVertices2.add(it2.next());
        }
        assertThat(actualVertices2.contains(A), is(false));
        assertThat(actualVertices2.contains(B), is(true));
        assertThat(actualVertices2.contains(C), is(true));
        assertThat(_dirGraph.getNumVertices(), is(2));
           
    }
    
    /**
     * This will test that removing a vertex will remove all of the edges
     * incident to it for an undirected graph.
     */
    @Test(timeout = 10000)
    public void testRemoveVertexAndEdgesUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
    	CS16Vertex<String> B = _graph.insertVertex("B");
    	CS16Vertex<String> C = _graph.insertVertex("C");
    	
    	_graph.insertEdge(A, B, 1);
    	_graph.insertEdge(B, C, 1);
    	
    	//check that there are two edges connected to in the
    	//graph (both are connected to B)
    	assertThat(_graph.edges().hasNext(), is(true));
    	//removing B should remove all edges in graph
    	_graph.removeVertex(B);
    	assertThat(_graph.edges().hasNext(), is(false));
    }
    
    //Tests same as above but for directed graph
    @Test(timeout = 10000)
    public void testRemoveVertexAndEdgesDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
    	CS16Vertex<String> B = _dirGraph.insertVertex("B");
    	CS16Vertex<String> C = _dirGraph.insertVertex("C");
    	
    	_dirGraph.insertEdge(A, B, 1);
    	_dirGraph.insertEdge(B, C, 1);
    	
    	//check that there are two edges connected to in the
    	//graph (both are connected to B)
    	assertThat(_dirGraph.edges().hasNext(), is(true));
    	//removing B should remove all edges in graph
    	_dirGraph.removeVertex(B);
    	assertThat(_dirGraph.edges().hasNext(), is(false));
    }
    
    /**
     * This will test that inserting and removing edges works for an 
     * undirected graph by using the edge set to keep track of edges
     * in the graph.
     */
    @Test(timeout = 10000)
    public void testInsertAndRemoveEdgesUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 2);
        CS16Edge<String> ac = _graph.insertEdge(A, C, 4);
        
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _graph.edges();
        while (it.hasNext()) {
            actualEdges.add(it.next());
        }
        assertThat(actualEdges.contains(ab), is(true));
        assertThat(actualEdges.contains(bc), is(true));
        assertThat(actualEdges.contains(ac), is(true));
        
        //Check that removing an edge removes it from the set
        _graph.removeEdge(bc);
        _graph.removeEdge(ab);
        
        List<CS16Edge<String>> actualEdges2 = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it2 = _graph.edges();
        while (it2.hasNext()) {
            actualEdges2.add(it2.next());
        }
        assertThat(actualEdges2.contains(ab), is(false));
        assertThat(actualEdges2.contains(ac), is(true));
        assertThat(actualEdges2.contains(bc), is(false));
           
    }
    
    //Tests the same as above but with a directed graph.
    @Test(timeout = 10000)
    public void testInsertAndRemoveEdgesDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);
        CS16Edge<String> ac = _dirGraph.insertEdge(A, C, 4);
        
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _dirGraph.edges();
        while (it.hasNext()) {
            actualEdges.add(it.next());
        }
        assertThat(actualEdges.contains(ab), is(true));
        assertThat(actualEdges.contains(bc), is(true));
        assertThat(actualEdges.contains(ac), is(true));
        
        //Check that removing an edge removes it from the set
        _dirGraph.removeEdge(bc);
        _dirGraph.removeEdge(ab);
        
        List<CS16Edge<String>> actualEdges2 = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it2 = _dirGraph.edges();
        while (it2.hasNext()) {
            actualEdges2.add(it2.next());
        }
        assertThat(actualEdges2.contains(ab), is(false));
        assertThat(actualEdges2.contains(ac), is(true));
        assertThat(actualEdges2.contains(bc), is(false));
           
    }
    
    /**
     * Tests that connectingEdge() will return the edge between two vertices in an
     * undirected graph. For an undirected graph, it should only return the edge from 
     * the first to the second vertex, which will be tested as well.
     */
    @Test(timeout = 10000)
    public void testConnectingEdgeUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 2); 
        CS16Edge<String> ac = _graph.insertEdge(A, C, 4);
        
        assertThat(_graph.connectingEdge(A, B), is(ab));
        assertTrue(_graph.connectingEdge(B, A) != ab && _graph.connectingEdge(B, A) != null);
        assertThat(_graph.connectingEdge(B, C), is(bc));
        assertTrue(_graph.connectingEdge(C, B) != bc &&  _graph.connectingEdge(C, B) != null);
        assertThat(_graph.connectingEdge(A, C), is(ac));
        assertTrue(_graph.connectingEdge(C, A) != ac && _graph.connectingEdge(C, A) != null);
           
    }
    
    //Tests the same as above but for a directed graph.
    @Test(timeout = 10000)
    public void testConnectingEdgeDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);
        CS16Edge<String> ac = _dirGraph.insertEdge(A, C, 4);
        
        assertThat(_dirGraph.connectingEdge(A, B), is(ab));
        assertThat(_dirGraph.connectingEdge(B, C), is(bc));
        assertThat(_dirGraph.connectingEdge(A, C), is(ac));
           
    }
    
    /**
     * This will test that the incomingEdges returns an iterator holding all 
     * the adjacent edges for a vertex (C in this case) for an undirected graph.
     */
    @Test(timeout = 10000)
    public void testIncomingEdgesUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Vertex<String> D = _graph.insertVertex("D");
        CS16Vertex<String> E = _graph.insertVertex("E");
        
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 2);
        CS16Edge<String> ac = _graph.insertEdge(A, C, 4);
        CS16Edge<String> cd = _graph.insertEdge(C, D, 4);
        CS16Edge<String> ce = _graph.insertEdge(C, E, 3);
        
        List<CS16Edge<String>> incomingEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _graph.incomingEdges(C);
        while (it.hasNext()) {
            incomingEdges.add(it.next());
        }
        
        assertThat(incomingEdges.size(), is(4));
        assertThat(incomingEdges.contains(ab), is(false));
        assertThat(incomingEdges.contains(bc), is(true));
        assertThat(incomingEdges.contains(ac), is(true));
        assertThat(incomingEdges.contains(cd), is(true));
        assertThat(incomingEdges.contains(ce), is(true));
           
    }
    
    /*
     * This tests incomingEdges() for a directed graph, so only edges pointing to
     * the input vertex (C in this case) should be returned in the iterator.
     */
    @Test(timeout = 10000)
    public void testIncomingEdgesDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        CS16Vertex<String> D = _dirGraph.insertVertex("D");
        CS16Vertex<String> E = _dirGraph.insertVertex("E");
        
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);
        CS16Edge<String> ac = _dirGraph.insertEdge(A, C, 4);
        CS16Edge<String> cd = _dirGraph.insertEdge(C, D, 4);
        CS16Edge<String> ce = _dirGraph.insertEdge(C, E, 3);
        
        List<CS16Edge<String>> incomingEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _dirGraph.incomingEdges(C);
        while (it.hasNext()) {
            incomingEdges.add(it.next());
        }
        
        assertThat(incomingEdges.size(), is(2));
        assertThat(incomingEdges.contains(ab), is(false));
        assertThat(incomingEdges.contains(bc), is(true));
        assertThat(incomingEdges.contains(ac), is(true));
        assertThat(incomingEdges.contains(cd), is(false));
        assertThat(incomingEdges.contains(ce), is(false));
           
    }
    
    /**
     * This will test that the outgoingEdges returns an iterator holding all 
     * the adjacent edges for a vertex (C in this case) for an undirected graph.
     */
    @Test(timeout = 10000)
    public void testOutgoingEdgesUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Vertex<String> D = _graph.insertVertex("D");
        CS16Vertex<String> E = _graph.insertVertex("E");
        
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 2);
        CS16Edge<String> ac = _graph.insertEdge(A, C, 4);
        CS16Edge<String> cd = _graph.insertEdge(C, D, 4);
        CS16Edge<String> ce = _graph.insertEdge(C, E, 3);
        
        List<CS16Edge<String>> outgoingEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _graph.outgoingEdges(C);
        while (it.hasNext()) {
        	outgoingEdges.add(it.next());
        }
        
        assertThat(outgoingEdges.size(), is(4));
        assertThat(outgoingEdges.contains(ab), is(false));
        assertThat(outgoingEdges.contains(bc), is(true));
        assertThat(outgoingEdges.contains(ac), is(true));
        assertThat(outgoingEdges.contains(cd), is(true));
        assertThat(outgoingEdges.contains(ce), is(true));
        
    }
    
    /*
     * This will test that the outgoingEdges() method for a directed graph will
     * only return the edges that are leaving the input vertex (C in this case).
     */
    @Test(timeout = 10000)
    public void testOutgoingEdgesDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        CS16Vertex<String> D = _dirGraph.insertVertex("D");
        CS16Vertex<String> E = _dirGraph.insertVertex("E");
        
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);
        CS16Edge<String> ac = _dirGraph.insertEdge(A, C, 4);
        CS16Edge<String> cd = _dirGraph.insertEdge(C, D, 4);
        CS16Edge<String> ce = _dirGraph.insertEdge(C, E, 3);
        
        List<CS16Edge<String>> outgoingEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _dirGraph.outgoingEdges(C);
        while (it.hasNext()) {
        	outgoingEdges.add(it.next());
        }
        
        assertThat(outgoingEdges.size(), is(2));
        assertThat(outgoingEdges.contains(ab), is(false));
        assertThat(outgoingEdges.contains(bc), is(false));
        assertThat(outgoingEdges.contains(ac), is(false));
        assertThat(outgoingEdges.contains(cd), is(true));
        assertThat(outgoingEdges.contains(ce), is(true));
        
    }
    
    /**
     * This will test numOutgoingEdges() method for a directed graph will
     * only return the edges that are leaving the input vertex (C in this case).
     */
    @Test(timeout = 10000)
    public void testNumOutgoingEdges() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        CS16Vertex<String> D = _dirGraph.insertVertex("D");
        CS16Vertex<String> E = _dirGraph.insertVertex("E");
        
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);
        CS16Edge<String> ac = _dirGraph.insertEdge(A, C, 4);
        CS16Edge<String> cd = _dirGraph.insertEdge(C, D, 4);
        CS16Edge<String> ce = _dirGraph.insertEdge(C, E, 3);
        
        assertThat(_dirGraph.numOutgoingEdges(A), is(2));
        assertThat(_dirGraph.numOutgoingEdges(B), is(1));
        assertThat(_dirGraph.numOutgoingEdges(C), is(2));
        assertThat(_dirGraph.numOutgoingEdges(D), is(0));
        assertThat(_dirGraph.numOutgoingEdges(E), is(0));
        
        //And if we remove an edge from A to B then we should expect A to only hav
        //one outgoing edge.
        _dirGraph.removeEdge(ab);
        assertThat(_dirGraph.numOutgoingEdges(A), is(1));
    }
    
    /**
     * Tests the opposite() method on an undirected graph
     */
    @Test(timeout = 10000)
    public void testOppositeUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Vertex<String> D = _graph.insertVertex("D");
        CS16Vertex<String> E = _graph.insertVertex("E");
        
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 2);
        CS16Edge<String> ac = _graph.insertEdge(A, C, 4);
        CS16Edge<String> cd = _graph.insertEdge(C, D, 4);
        CS16Edge<String> ce = _graph.insertEdge(C, E, 3);
        
        assertThat(_graph.opposite(A, ac), is(C));
        assertThat(_graph.opposite(B,bc), is(C));
        assertThat(_graph.opposite(C,ac), is(A));
        assertThat(_graph.opposite(D,cd), is(C));
        assertThat(_graph.opposite(E,ce), is(C));
        
    }
    
    //Test the same as above but on a directed graph
    public void testOppositeDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        CS16Vertex<String> D = _dirGraph.insertVertex("D");
        CS16Vertex<String> E = _dirGraph.insertVertex("E");
        
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);
        CS16Edge<String> ac = _dirGraph.insertEdge(A, C, 4);
        CS16Edge<String> cd = _dirGraph.insertEdge(C, D, 4);
        CS16Edge<String> ce = _dirGraph.insertEdge(C, E, 3);
        
        assertThat(_dirGraph.opposite(A, ac), is(C));
        assertThat(_dirGraph.opposite(B,bc), is(C));
        assertThat(_dirGraph.opposite(C,ac), is(A));
        assertThat(_dirGraph.opposite(D,cd), is(C));
        assertThat(_dirGraph.opposite(E,ce), is(C));
        
    }
    
    /**
     * Tests the endVertices() method for an undirected graph
     */
    @Test(timeout = 10000)
    public void testEndVerticesUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        
        CS16Edge<String> ab = _graph.insertEdge(A, B, 4);
        
        //checks that the items in the list returned by endVertices are
        //the two vertices on either side of the input edge and that the
        //two items are different.
        List<CS16Vertex<String>> endVs= _graph.endVertices(ab);
        assertThat(endVs.get(0) == A || endVs.get(0) == B, is(true));
        assertThat(endVs.get(1) == A || endVs.get(1) == B, is(true));
        assertThat(endVs.get(0) != endVs.get(1), is(true));
    }
    
    //Tests the same as above but for a directed graph
    @Test(timeout = 10000)
    public void testEndVerticesDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 4);
        
        //checks that the items in the list returned by endVertices are
        //the two vertices on either side of the input edge and that the
        //two items are different.
        List<CS16Vertex<String>> endVs= _dirGraph.endVertices(ab);
        assertThat(endVs.get(0) == A || endVs.get(0) == B, is(true));
        assertThat(endVs.get(1) == A || endVs.get(1) == B, is(true));
        assertThat(endVs.get(0) != endVs.get(1), is(true));
    }
    
    /**
     * Tests the areAdjacent() method for an undirected graph
     */
    @Test(timeout = 10000)
    public void testAreAdjacentUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        
        CS16Edge<String> ab = _graph.insertEdge(A, B, 4);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 4);
        
        //Make sure that the method returns true if and edge exists from v1 to v2
        assertThat(_graph.areAdjacent(A, B), is(true));
        assertThat(_graph.areAdjacent(B, C), is(true));
        //Make sure method returns false if there is no edge at all
        assertThat(_graph.areAdjacent(A, C), is(false));
        //Make sure method returns true if the edge is from v2 to v1
        assertThat(_graph.areAdjacent(B, A), is(true));
        assertThat(_graph.areAdjacent(C, B), is(true));
    }
    
    /**
     * This tests that areAdjacent() for a directed graph only works
     * if the edge goes from v1 to v2
     */
    @Test(timeout = 10000)
    public void testAreAdjacentDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 4);
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 4);
        
        //Make sure that the method returns true if and edge exists from v1 to v2
        assertThat(_dirGraph.areAdjacent(A, B), is(true));
        assertThat(_dirGraph.areAdjacent(B, C), is(true));
        //Make sure method returns false if there is no edge at all
        assertThat(_dirGraph.areAdjacent(A, C), is(false));
        //Make sure method returns false if the edge is from v2 to v1
        assertThat(_dirGraph.areAdjacent(B, A), is(false));
        assertThat(_dirGraph.areAdjacent(C, B), is(false));
        
    }
    
    /**
     * Tests the opposite() method on an undirected graph to make sure
     * it changes the boolean and clears the matrix.
     */
    @Test(timeout = 10000)
    public void testToggleUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Vertex<String> D = _graph.insertVertex("D");
        CS16Vertex<String> E = _graph.insertVertex("E");
        
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 2);
        CS16Edge<String> ac = _graph.insertEdge(A, C, 4);
        CS16Edge<String> cd = _graph.insertEdge(C, D, 4);
        CS16Edge<String> ce = _graph.insertEdge(C, E, 3);
        
        _graph.toggleDirected();
        assertThat(_graph.getNumVertices(), is(0));
        
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _graph.edges();
        while (it.hasNext()) {
            actualEdges.add(it.next());
        }
        assertThat(actualEdges.isEmpty(), is(true));
        
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it2 = _graph.vertices();
        while (it2.hasNext()) {
            actualVertices.add(it2.next());
        }
        assertThat(actualVertices.isEmpty(), is(true));
        
    }
    
    //Tests the same as above but on a directed graph.
    @Test(timeout = 10000)
    public void testToggleDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        CS16Vertex<String> D = _dirGraph.insertVertex("D");
        CS16Vertex<String> E = _dirGraph.insertVertex("E");
        
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);
        CS16Edge<String> ac = _dirGraph.insertEdge(A, C, 4);
        CS16Edge<String> cd = _dirGraph.insertEdge(C, D, 4);
        CS16Edge<String> ce = _dirGraph.insertEdge(C, E, 3);
        
        _dirGraph.toggleDirected();
        assertThat(_dirGraph.getNumVertices(), is(0));
        
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _dirGraph.edges();
        while (it.hasNext()) {
            actualEdges.add(it.next());
        }
        assertThat(actualEdges.isEmpty(), is(true));
        
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it2 = _dirGraph.vertices();
        while (it2.hasNext()) {
            actualVertices.add(it2.next());
        }
        assertThat(actualVertices.isEmpty(), is(true));
        
    }
    
    /**
     * Tests the clear() method for an undirected graph by seeing the number of
     * vertices before and after clearing, as well as checking that the vertex and
     * edge iterators are empty.
     */
    @Test(timeout = 10000)
    public void testClearUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Vertex<String> D = _graph.insertVertex("D");
        CS16Vertex<String> E = _graph.insertVertex("E");
        
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 2);
        CS16Edge<String> ac = _graph.insertEdge(A, C, 4);
        CS16Edge<String> cd = _graph.insertEdge(C, D, 4);
        CS16Edge<String> ce = _graph.insertEdge(C, E, 3);
        
        assertThat(_graph.getNumVertices(), is(5));
        _graph.clear();
        assertThat(_graph.getNumVertices(), is(0));
        
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _graph.edges();
        while (it.hasNext()) {
            actualEdges.add(it.next());
        }
        assertThat(actualEdges.isEmpty(), is(true));
        
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it2 = _graph.vertices();
        while (it2.hasNext()) {
            actualVertices.add(it2.next());
        }
        assertThat(actualVertices.isEmpty(), is(true));
        
    }
    
    @Test(timeout = 10000)
    public void testClearDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        CS16Vertex<String> D = _dirGraph.insertVertex("D");
        CS16Vertex<String> E = _dirGraph.insertVertex("E");
        
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _dirGraph.insertEdge(B, C, 2);
        CS16Edge<String> ac = _dirGraph.insertEdge(A, C, 4);
        CS16Edge<String> cd = _dirGraph.insertEdge(C, D, 4);
        CS16Edge<String> ce = _dirGraph.insertEdge(C, E, 3);
        
        assertThat(_dirGraph.getNumVertices(), is(5));
        _dirGraph.clear();
        assertThat(_dirGraph.getNumVertices(), is(0));
        
        List<CS16Edge<String>> actualEdges = new ArrayList<CS16Edge<String>>();
        Iterator<CS16Edge<String>> it = _dirGraph.edges();
        while (it.hasNext()) {
            actualEdges.add(it.next());
        }
        assertThat(actualEdges.isEmpty(), is(true));
        
        List<CS16Vertex<String>> actualVertices = new ArrayList<CS16Vertex<String>>();
        Iterator<CS16Vertex<String>> it2 = _dirGraph.vertices();
        while (it2.hasNext()) {
            actualVertices.add(it2.next());
        }
        assertThat(actualVertices.isEmpty(), is(true));
        
    }
    
    /**
     * Tests that loop self-edges can be inserted in an undirected graph.
     */
    @Test(timeout = 10000)
    public void testSelfEdgeUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Edge<String> aa = _graph.insertEdge(A, A, 1);
        assertThat(_graph.areAdjacent(A, A), is(true));
        assertThat(_graph.connectingEdge(A, A), is(aa));
        assertThat(_graph.endVertices(aa).get(0), is(A));
        assertThat(_graph.endVertices(aa).get(1), is(A));
    }
    
    /**
     * Tests that loop self-edges can be inserted in a directed graph.
     */
    @Test(timeout = 10000)
    public void testSelfEdgeDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Edge<String> aa = _dirGraph.insertEdge(A, A, 1);
        assertThat(_dirGraph.areAdjacent(A, A), is(true));
        assertThat(_dirGraph.connectingEdge(A, A), is(aa));
        assertThat(_dirGraph.endVertices(aa).get(0), is(A));
        assertThat(_dirGraph.endVertices(aa).get(1), is(A));
        assertThat(_dirGraph.numOutgoingEdges(A), is(1));
    }
    
    /**
     * Tests the case where the maximum number of vertices are entered into
     * the matrix and then one is removed and another is added to show that 
     * the vertex numbers are reused and don't exceed the index of the matrix.
     * It also checks that the matrix can hold the max number of vertices 
     * required. Since no error is brought up filling the matrix the first time
     * and after removing and adding a vertex, I believe it proves that the 
     * vertex numbers are all unique.
     */
    @Test(timeout = 10000)
    public void testVertexNumbersUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
    	for (int i = 1; i < MAX_VERTICES; i++) {
    		_graph.insertVertex(Integer.toString(i));
    	}
    	assertTrue(_graph.getNumVertices() == MAX_VERTICES);
    	int number = A.getVertexNumber();
    	_graph.removeVertex(A);
    	//If no error is brought up after we insert another
    	//vertex and we are able to check the number of vertices
    	//in the graph then a new number has been successfully 
    	//assigned that is within the size of the matrix
    	CS16Vertex<String> B = _graph.insertVertex("B");
    	assertTrue(B.getVertexNumber() == number);
    	assertTrue(_graph.getNumVertices() == MAX_VERTICES);
    }
    
    //Tests same as above but for directed graph.
    @Test(timeout = 10000)
    public void testVertexNumbersDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
    	for (int i = 1; i < MAX_VERTICES; i++) {
    		_dirGraph.insertVertex(Integer.toString(i));
    	}
    	assertTrue(_dirGraph.getNumVertices() == MAX_VERTICES);
    	int number = A.getVertexNumber();
    	_dirGraph.removeVertex(A);
    	CS16Vertex<String> B = _dirGraph.insertVertex("B");
    	assertTrue(B.getVertexNumber() == number);
    	assertTrue(_dirGraph.getNumVertices() == MAX_VERTICES);
    }
    
    /**
     * Tests that the clearing the graph resets the unique number couter for 
     * the vertex numbers
     */
    @Test(timeout = 10000)
    public void testClearVertexNumberUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
    	int num1 = A.getVertexNumber();
    	_graph.clear();
    	CS16Vertex<String> B = _graph.insertVertex("B");
    	int num2 = B.getVertexNumber();
    	assertTrue(num1 == num2);
    }
    
    //Tests same as above but for directed graph
    @Test(timeout = 10000)
    public void testClearVertexNumberDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
    	int num1 = A.getVertexNumber();
    	_dirGraph.clear();
    	CS16Vertex<String> B = _dirGraph.insertVertex("B");
    	int num2 = B.getVertexNumber();
    	assertTrue(num1 == num2);
    }
    
    /**
     * This will test the functionality of getNumVertices() for 
     * an undirected graph.
     */
    @Test(timeout = 10000)
    public void testGetNumVerticesUndirected() {
    	assertThat(_graph.getNumVertices(), is(0));
    	CS16Vertex<String> A = _graph.insertVertex("A");
    	assertThat(_graph.getNumVertices(), is(1));

    	CS16Vertex<String> B = _graph.insertVertex("B");
    	assertThat(_graph.getNumVertices(), is(2));
    	
    	CS16Vertex<String> C = _graph.insertVertex("C");
    	assertThat(_graph.getNumVertices(), is(3));
    	
    	_graph.removeVertex(A);
    	assertThat(_graph.getNumVertices(), is(2));

    }
    
    //Tests same as above but for directed graph
    @Test(timeout = 10000)
    public void testGetNumVerticesDirected() {
    	assertThat(_dirGraph.getNumVertices(), is(0));
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
    	assertThat(_dirGraph.getNumVertices(), is(1));

    	CS16Vertex<String> B = _dirGraph.insertVertex("B");
    	assertThat(_dirGraph.getNumVertices(), is(2));
    	
    	CS16Vertex<String> C = _dirGraph.insertVertex("C");
    	assertThat(_dirGraph.getNumVertices(), is(3));
    	
    	_dirGraph.removeVertex(A);
    	assertThat(_dirGraph.getNumVertices(), is(2));

    }
    
    /**
     * Tests that an invalid vertex exception is thrown for insertEdge() if
     * the either vertex input is null, for an undirected graph.
     */
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testInsertEdgeErrorUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        
        CS16Edge<String> ab = _graph.insertEdge(A, null, 1);
        CS16Edge<String> ba = _graph.insertEdge(null, B, 1);
    	
    }
    
    //Tests same as above but for directed graph. Also changes tehe null input
    //to the second input to see that it still works
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testInsertEdgeErrorDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        
        CS16Edge<String> ab = _dirGraph.insertEdge(A, null, 1);
        CS16Edge<String> ba = _dirGraph.insertEdge(null, B, 1);
        
    }
    
    /**
     * Tests that an invalid vertex exception is thrown for removeVertex() if
     * the input is null, for an undirected graph.
     */
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testRemoveVertexErrorUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        
        _graph.removeVertex(null);
    	
    }
    
    //Tests same as above but for directed graph.
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testRemoveVertexErrorDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        
        _dirGraph.removeVertex(null);
        
    }
    
    /**
     * Tests that removeEdge() with a null input will throw an
     * InvalidEdgeException or an undirected graph.
     */
    @Test(timeout = 10000, expected = InvalidEdgeException.class)
    public void testRemoveEdgeErrorUndirected() {
    	_graph.removeEdge(null);
    }
    
    //This will do the same as above but for a directed graph.
    @Test(timeout = 10000, expected = InvalidEdgeException.class)
    public void testRemoveEdgeErrorDirected() {
    	_dirGraph.removeEdge(null);
    }
    
    /**
     * Tests that a NoSuchEdge exception is raised if there is no 
     * edge between the input vertices for an undirected graph.
     */
    @Test(timeout = 10000, expected = NoSuchEdgeException.class)
    public void testNoConnectingEdgeErrorUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        _graph.connectingEdge(A, B);
        
    }
    
    //Tests same as above but for directed graph.
    @Test(timeout = 10000, expected = NoSuchEdgeException.class)
    public void testNoConnectingEdgeErrorDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        _dirGraph.connectingEdge(A, B);
        
    }
    
    /**
     * Tests that an invalid first input will raise an invalidVertexException
     * for an undirected graph for the connectingEdge() method.
     */
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testNullFirstInputConnectingEdgeErrorUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        _graph.connectingEdge(null, B);
        
    }
    
    //Tests same thing as above but for directed graph.
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testNullFirstInputConnectingEdgeErroDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        _dirGraph.connectingEdge(null, B);
        
    }
    
    /**
     * Tests that an invalid second input will raise an invalidVertexException
     * for an undirected graph for the connectingEdge() method.
     */
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testNullSecondInputConnectingEdgeErrorUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        _graph.connectingEdge(A, null);
        
    }
    
  //Tests same thing as above but for directed graph.
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testNullSecondInputConnectingEdgeErroDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        _dirGraph.connectingEdge(A, null);
        
    }
    
    /**
     * Tests that two invalid inputs will raise an invalidVertexException
     * for an undirected graph for the connectingEdge() method.
     */
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testNullBothInputsConnectingEdgeErrorUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        _graph.connectingEdge(null, null);
        
    }
    
  //Tests same thing as above but for directed graph.
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testNullBothInputsConnectingEdgeErroDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        _dirGraph.connectingEdge(null, null);
        
    }
    
    /**
     * Tests that a null input for incomingEdges() will throw
     * an InvalidVertexException for an undirected graph.
     */
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testIncomingEdgeErrorUndirected() {
    	_graph.incomingEdges(null);
    }
    
    //Tests the same as above but for a directed graph
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testIncomingEdgeErrorDirected() {
    	_dirGraph.incomingEdges(null);
    }
    
    /**
     * Tests that a null input for outgoingEdges() will throw
     * an InvalidVertexException for an undirected graph.
     */
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testOutgoingEdgeErrorUndirected() {
    	_graph.outgoingEdges(null);
    }
    
    //Tests the same as above but for a directed graph
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testOutgoingEdgeErrorDirected() {
    	_dirGraph.outgoingEdges(null);
    }
    
    /**
     * Tests that a null input for numOutgoingEdges() will throw
     * an InvalidVertexException.
     */
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testNumOutgoingEdgeNullError() {
    	_dirGraph.numOutgoingEdges(null);
    }
    
    /**
     * Tests that calling numOutgingEdges on a directed graph will
     * throw a DirectionException
     */
    @Test(timeout = 10000, expected = DirectionException.class)
    public void testNumOutgoingEdgeErrorUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
    	_graph.numOutgoingEdges(A);
    }
    
    /**
     * Tests that opposite() with a null input for the vertex will 
     * return an InvalidVertexException() for an undirected graph.
     */
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testOppositeNullVertixUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        
    	_graph.opposite(null, ab);
    }
    
    //Tests same as above but for directed graph
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testOppositeNullVertixDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        
    	_dirGraph.opposite(null, ab);
    }
    
    /**
     * Tests that opposite() with a null input for the edge will 
     * return an InvalidEdgeException() for an undirected graph.
     */
    @Test(timeout = 10000, expected = InvalidEdgeException.class)
    public void testOppositeNullEdgeUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        
    	_graph.opposite(A, null);
    }
    
    //Tests same as above for directed graph
    @Test(timeout = 10000, expected = InvalidEdgeException.class)
    public void testOppositeNullEdgeDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        
        _dirGraph.opposite(A, null);
    }
    
    /**
     * Tests that opposite() will return a NoSuchVertex exception if
     * the edge is not incident to the vertex.
     */
    @Test(timeout = 10000, expected = NoSuchVertexException.class)
    public void testOppositeNoSuchVertexUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Vertex<String> D = _graph.insertVertex("D");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> cd = _graph.insertEdge(C, D, 1);
        
    	_graph.opposite(A, cd);
    }
    
    //Tests same as above but for directed graph.
    @Test(timeout = 10000, expected = NoSuchVertexException.class)
    public void testOppositeNoSuchEdgeDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        CS16Vertex<String> D = _dirGraph.insertVertex("D");
        CS16Edge<String> ab = _dirGraph.insertEdge(A, B, 1);
        CS16Edge<String> cd = _dirGraph.insertEdge(C, D, 1);
        
        _dirGraph.opposite(A, cd);
    }
    
    /**
     * Tests that endVertices() throws an InvalidEdgeException
     * when the input edge is null for undirected graph.
     */
    @Test(timeout = 10000, expected = InvalidEdgeException.class)
    public void testEndVerticesErrorUndirected() {
    	_graph.endVertices(null);
    }   
    
    //Tests same as above but for directed graph.
    @Test(timeout = 10000, expected = InvalidEdgeException.class)
    public void testEndVerticesErrorDirected() {
    	_dirGraph.endVertices(null);
    }   
    
    /**
     * Tests that areAdjacent() throws an InvalidVertexException
     * when first input vertex is null for undirected graph.
     */
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testAreAdjacentFirstInputNullErrorUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
    	_graph.areAdjacent(null,B);
    }
    
  //Tests same as above but for directed graph.
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testAreAdjacentFirstInputNullErrorDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        _dirGraph.areAdjacent(null,B);
    }
    
    /**
     * Tests that areAdjacent() throws an InvalidVertexException
     * when first second vertex is null for undirected graph.
     */
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testAreAdjacentSecondInputNullErrorUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
    	_graph.areAdjacent(A,null);
    }
    
  //Tests same as above but for directed graph.
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testAreAdjacentSecondInputNullErrorDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        _dirGraph.areAdjacent(A,null);
    }
    
    /**
     * Tests that areAdjacent() throws an InvalidVertexException
     * when first input vertex is null for undirected graph.
     */
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testAreAdjacentBothInputsNullErrorUndirected() {
    	CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
    	_graph.areAdjacent(null,null);
    }
    
  //Tests same as above but for directed graph.
    @Test(timeout = 10000, expected = InvalidVertexException.class)
    public void testAreAdjacentBothInputsNullErrorDirected() {
    	CS16Vertex<String> A = _dirGraph.insertVertex("A");
        CS16Vertex<String> B = _dirGraph.insertVertex("B");
        CS16Vertex<String> C = _dirGraph.insertVertex("C");
        _dirGraph.areAdjacent(null,null);
    }
    
    /*
     * List of graphs for testing!
     */
    @Parameters(name = "with graph: {0}")
    public static Collection<String> graphs() {
        List<String> names = new ArrayList<>();
        names.add("graph.AdjacencyMatrixGraph");
        return names;
    }
    
    /*
     * ####################################################
     * 
     * DO NOT MODIFY ANYTHING BELOW THIS LINE
     * 
     * ####################################################
     */
	
	
    @SuppressWarnings("unchecked")
    @Before
	public void makeGraph() {
        Class<?> graphClass = null;
        try {
            graphClass = Class.forName(_graphClassName);
            Constructor<?> constructor = graphClass.getConstructors()[0];
            _graph = (Graph<String>) constructor.newInstance(false);
	    _dirGraph = (Graph<String>) constructor.newInstance(true);
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | IllegalArgumentException e) {
            System.err.println("Exception while instantiating Graph class in GraphTest.");
            e.printStackTrace();
        }
	}
	
    public GraphTest(String graphClassName) {
	    this._graphClassName = graphClassName;
	}
}
