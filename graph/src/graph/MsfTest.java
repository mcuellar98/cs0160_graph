package graph;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.Graph;
import support.graph.MinSpanForest;

/**
 * This class tests the functionality of your MSF algorithms on an AdjacencyMatrixGraph
 * with a 'String' type parameter for the vertices. Edge elements are Integers.
 * The general framework of a test case is: - Name the test method descriptively,
 * mentioning what is  being tested (it is ok to have slightly verbose method names here)
 * Set-up the program state (ex: instantiate a heap and insert K,V pairs into it) - Use
 * assertions to validate that the progam is in the state you expect it to be
 * See header comments over tests for what each test does
 * 
 * Before each test is run, you can assume that the '_graph' variable is reset to
 * a new instance of the a Graph<String> that you can simply use 'as is', as
 * well as the '_msf' variable.
 *
 * Of course, please do not modify anything below the 'DO NOT MODIFY ANYTHING BELOW THIS LINE'
 * line, or the above assumptions may be broken.
 */
@RunWith(Parameterized.class)
public class MsfTest {

    private String _msfClassName;
    private MinSpanForest<String> _msf;
    private Graph<String> _graph;
    
    @Test
    public void simpleTest() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");

        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 1);
        CS16Edge<String> ca = _graph.insertEdge(A, C, 10);
        Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);
        
        assertThat(MSF.size(), is(2));
        assertThat(MSF.contains(ab), is(true));
        assertThat(MSF.contains(bc), is(true));
        assertThat(MSF.contains(ca), is(false));
    }
    
    /**
     * This tests a graph with just two vertices and one edge to make sure
     * that one edge is returned.
     */
    @Test
    public void twoVertexOneEdgeGraph() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");

        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);
        
        assertThat(MSF.size(), is(1));
        assertThat(MSF.contains(ab), is(true));
        
    }
    
    /**
     * This tests a small graph with many edges and possible spanning
     * trees to see make sure the algorithm gets the right one.
     */
    @Test
    public void testSmallGraph() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Vertex<String> D = _graph.insertVertex("D");
        CS16Vertex<String> E = _graph.insertVertex("E");

        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 4);
        CS16Edge<String> ca = _graph.insertEdge(C, A, 7);
        CS16Edge<String> ed = _graph.insertEdge(E, D, 5);
        CS16Edge<String> ae = _graph.insertEdge(A, E, 6);
        CS16Edge<String> db = _graph.insertEdge(D, B, 9);
        Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);
        
        assertThat(MSF.size(), is(4));
        assertThat(MSF.contains(ab), is(true));
        assertThat(MSF.contains(bc), is(true));
        assertThat(MSF.contains(ae), is(true));
        assertThat(MSF.contains(ed), is(true));
        assertThat(MSF.contains(ca), is(false));
        assertThat(MSF.contains(db), is(false));
        
    }
    
    /**
     * Tests a simple forests, each consisting of two vertices and 
     * one edge, to make sure that both edges are returned.
     */
    @Test
    public void testSimplestForrest() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Vertex<String> D = _graph.insertVertex("D");

        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        CS16Edge<String> cd = _graph.insertEdge(C, D, 7);
        Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);
        
        assertThat(MSF.size(), is(2));
        assertThat(MSF.contains(ab), is(true));
        assertThat(MSF.contains(cd), is(true));
        
    }
    
    /**
     * This test two forests with multiple spanning trees to make sure that 
     * the list returned by the algorithm has the edges for the mst's for
     * both.
     */
    @Test
    public void testComplexForrest() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Vertex<String> C = _graph.insertVertex("C");
        CS16Vertex<String> D = _graph.insertVertex("D");
        CS16Vertex<String> E = _graph.insertVertex("E");
        
        CS16Vertex<String> F = _graph.insertVertex("F");
        CS16Vertex<String> G = _graph.insertVertex("G");
        CS16Vertex<String> H = _graph.insertVertex("H");
        CS16Vertex<String> I = _graph.insertVertex("I");
        CS16Vertex<String> J = _graph.insertVertex("J");

        CS16Edge<String> ab = _graph.insertEdge(A, B, 4);
        CS16Edge<String> bc = _graph.insertEdge(B, C, 8);
        CS16Edge<String> ca = _graph.insertEdge(C, A, 7);
        CS16Edge<String> ed = _graph.insertEdge(E, D, 4);
        CS16Edge<String> ae = _graph.insertEdge(A, E, 9);
        CS16Edge<String> be = _graph.insertEdge(B, E, 12);
        
        
        CS16Edge<String> fh = _graph.insertEdge(F, H, 12);
        CS16Edge<String> ij = _graph.insertEdge(I, J, 3);
        CS16Edge<String> hj = _graph.insertEdge(H, J, 11);
        CS16Edge<String> gf = _graph.insertEdge(G, F, 9);
        CS16Edge<String> ig = _graph.insertEdge(I, G, 6);
        CS16Edge<String> gj = _graph.insertEdge(G, J, 15);
        
        Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);
        
        assertThat(MSF.size(), is(8));
        assertThat(MSF.contains(ab), is(true));
        assertThat(MSF.contains(bc), is(false));
        assertThat(MSF.contains(ae), is(true));
        assertThat(MSF.contains(ed), is(true));
        assertThat(MSF.contains(ca), is(true));
        assertThat(MSF.contains(be), is(false));
        
        assertThat(MSF.contains(fh), is(false));
        assertThat(MSF.contains(ij), is(true));
        assertThat(MSF.contains(hj), is(true));
        assertThat(MSF.contains(gf), is(true));
        assertThat(MSF.contains(ig), is(true));
        assertThat(MSF.contains(gj), is(false));
        
    }
    
    /**
     * Tests that the algorithm returns an empty list for an empty graph 
     */
    @Test
    public void testNoVertexGraph() {
        Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);
        assertThat(MSF.size(), is(0));
        
    }
    
    /**
     * Tests that an empty list is returned for a graph with one vertex and
     * no edges.
     */
    @Test
    public void testSingleVertexGraph() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);
        assertThat(MSF.size(), is(0));
        
    }
    
    /**
     * Tests that an empty list is returned from a graph with two vertices
     * and no edges.
     */
    @Test
    public void testTwoVertexForrest() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);
        assertThat(MSF.size(), is(0));
        
    }
    
    /**
     * Tests that regardless of how edge is inserted (which vertex is chosen
     * as start or end), the algorithm returns the correct edge. This was a problem
     * I saw earlier in testing so that's why it's included.
     */
    @Test
    public void testEdgeDirectionality() {
        CS16Vertex<String> A = _graph.insertVertex("A");
        CS16Vertex<String> B = _graph.insertVertex("B");
        CS16Edge<String> ab = _graph.insertEdge(A, B, 1);
        Collection<CS16Edge<String>> MSF = _msf.genMinSpanForest(_graph, null);
        assertThat(MSF.size(), is(1));
        assertThat(MSF.contains(ab), is(true));
        
        _graph.removeEdge(ab);
        CS16Edge<String> ba = _graph.insertEdge(B, A, 1);
        Collection<CS16Edge<String>> MSF2 = _msf.genMinSpanForest(_graph, null);
        assertThat(MSF2.size(), is(1));
        assertThat(MSF2.contains(ba), is(true));
    }
    
    /*
     * This is the method that, using junit magic, provides the list of MSF algorithms
     * that should be created and be tested via the methods above.
     * By default, all of the above tests will be run on MyPrimJarnik algorithm implementations.
     * If you're interested in testing the methods on just one of the
     * algorithms, comment out the one you don't want in the method below!
     */
    @Parameters(name = "with msf algo: {0}")
    public static Collection<String> msts() {
        List<String> algoNames = new ArrayList<>();
        algoNames.add("graph.MyPrimJarnik");
        return algoNames;
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
    public void setup() {
        Class<?> msfClass = null;
        try {
            msfClass = Class.forName(_msfClassName);
            Constructor<?> constructor = msfClass.getConstructors()[0];
            _msf = (MinSpanForest<String>) constructor.newInstance();
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException
                | IllegalArgumentException e) {
            System.err.println("Exception while instantiating msf class " + _msfClassName + " from test.");
            e.printStackTrace();
        }
        _graph = new AdjacencyMatrixGraph<>(false);
    }

    public MsfTest(String msfClassName) {
        _msfClassName = msfClassName;
    }
}
