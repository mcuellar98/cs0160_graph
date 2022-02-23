package graph;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import support.graph.CS16Edge;
import support.graph.CS16Vertex;
import support.graph.Graph;
import java.util.Map;
import java.util.HashMap;

/**
 * This class tests the functionality of your PageRank algorithm on a
 * directed AdjacencyMatrixGraph. The general framework of a test case is:
 * - Name the test method descriptively,
 * - Mention what is being tested (it is ok to have slightly verbose method names here)
 * 
 * Some tips to keep in mind when writing test cases: 
 * - All pages' ranks should total to 1.
 * - It will be easier to start out by writing test cases on smaller graphs.
 *
 */
public class MyPageRankTest {

	// This is your margin of error for testing
	double _epsilon = 0.03;
 
	/**
     * A simple test with four pages. Each page only has one
	 * outgoing link to a different page, resulting in a square 
	 * shape or cycle when visualized. The pages' total ranks is 1.
     */
	@Test
	public void testFourEqualRanks() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");

		/**
		  * Inserting an edge with a null element since a weighted edge is not
		  * meaningful for the PageRank algorithm. 
		  */

		CS16Edge<String> e0 = adjMatrix.insertEdge(a,b,null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(b,c,null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(c,d,null);
		CS16Edge<String> e3 = adjMatrix.insertEdge(d,a,null);

		MyPageRank<String> pr = new MyPageRank<String>();

		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);

		// Check that the number of vertices returned by PageRank is 4
		assertEquals(output.size(), 4);
		double total = 0;
		for (double rank: output.values()) {
			total += rank;
		}

		// The weights of each vertex should be 0.25
		double expectedRankA = 0.25;
		double expectedRankB = 0.25;
		double expectedRankC = 0.25;
		double expectedRankD = 0.25;

		// The sum of weights should always be 1
		assertEquals(total, 1, _epsilon);

		// The Rank for each vertex should be 0.25 +/- epsilon 
		assertEquals(output.get(a), expectedRankA, _epsilon);
		assertEquals(output.get(b), expectedRankB, _epsilon);
		assertEquals(output.get(c), expectedRankC, _epsilon);
		assertEquals(output.get(d), expectedRankD, _epsilon);

	}
	
	/**
	* A simple test with three pages. Note that vertex A's 
	* rank is not 0 even though it has no incoming edges, 
	* demonstrating the effects of the damping factor and handling sinks.
	*/
	@Test
	public void simpleTestOne() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Edge<String> e0 = adjMatrix.insertEdge(a,b,null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(b,c,null);
	
		MyPageRank<String> pr = new MyPageRank<String>();
	
		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);
	
		assertEquals(output.size(), 3);
		double total = 0;
		for (double rank: output.values()) {
			total += rank;
		}
	
		// These are precomputed values
		double expectedRankA = 0.186;
		double expectedRankB = 0.342;
		double expectedRankC = 0.471;
		
		assertEquals(total, 1, _epsilon);
		assertEquals(output.get(a), expectedRankA, _epsilon);
		assertEquals(output.get(b), expectedRankB, _epsilon);
		assertEquals(output.get(c), expectedRankC, _epsilon);
	
	}
	
	
	/**
	  * TODO: Add your own tests here. Instead of checking for specific rank values, 
	  * make test cases comparing the relative ranks of pages (e.g. using an assertThat statement)!
	  */
	
	
	/**
	 * This tests that three vertices, each with one incoming and outgoing edge, will
	 * end up with the same amount of page rank.
	 */
	@Test
	public void testThreeVertexCycle() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Edge<String> e0 = adjMatrix.insertEdge(a,b,null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(b,c,null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(c,a,null);
	
		MyPageRank<String> pr = new MyPageRank<String>();
	
		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);
	
		assertEquals(output.size(), 3);
		double total = 0;
		for (double rank: output.values()) {
			total += rank;
		}
		
		//The values should be equal but are slightly different, so I 
		//use a the given margin of error to show that they are basically
		//equal
		assertEquals(total, 1, _epsilon);
		assertEquals(output.get(a), output.get(b), _epsilon);
		assertEquals(output.get(b), output.get(c), _epsilon);
		assertEquals(output.get(c), output.get(a), _epsilon);
	
	}
	
	/**
	 * Tests a graph with a single sink to make sure its handled correctly.
	 */
	@Test
	public void testSingleSink() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Edge<String> e0 = adjMatrix.insertEdge(a,b,null);
	
		MyPageRank<String> pr = new MyPageRank<String>();
	
		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);
	
		assertEquals(output.size(), 2);
		double total = 0;
		for (double rank: output.values()) {
			total += rank;
		}
	
		assertEquals(total, 1, _epsilon);
		assertThat(output.get(b) > output.get(a), is(true));
	
	}
	
	/**
	 * Tests a graph with many sinks.
	 */
	@Test
	public void testManySinks() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");
		CS16Vertex<String> e = adjMatrix.insertVertex("E");
	
		CS16Edge<String> e0 = adjMatrix.insertEdge(a,b,null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(a,c,null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(a,d,null);
		CS16Edge<String> e3 = adjMatrix.insertEdge(a,e,null);
	
		MyPageRank<String> pr = new MyPageRank<String>();
	
		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);
	
		assertEquals(output.size(), 5);
		double total = 0;
		for (double rank: output.values()) {
			total += rank;
		}
	
		assertEquals(total, 1, _epsilon);
		assertThat(output.get(b) > output.get(a), is(true));
		assertThat(output.get(c) > output.get(a), is(true));
		assertThat(output.get(d) > output.get(a), is(true));
		assertThat(output.get(e) > output.get(a), is(true));
	
	}
	
	/**
	 * Tests two vertices, each with an edge pointing to the other.
	 */
	@Test
	public void testTwoVerticesWithTwoEdges() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		
		CS16Edge<String> e0 = adjMatrix.insertEdge(a,b,null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(b,a,null);
		
		MyPageRank<String> pr = new MyPageRank<String>();
	
		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);
	
		assertEquals(output.size(), 2);
		double total = 0;
		for (double rank: output.values()) {
			total += rank;
		}
		assertEquals(total, 1, _epsilon);
		assertEquals(output.get(a),output.get(b), _epsilon);
	}
	
	/**
	 * This Takes four vertices, adds edges to them to form a square shape, and then 
	 * adds diagonal edges with in the vertex. This was just a way to make a slightly
	 * complex graph to test. I compared with the demo to see how mine did.
	 */
	@Test
	public void testBoxWithTwoEdgesInMiddle() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");
		
		CS16Edge<String> e0 = adjMatrix.insertEdge(a,b,null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(b,c,null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(d,c,null);
		CS16Edge<String> e3 = adjMatrix.insertEdge(a,d,null);
		CS16Edge<String> e4 = adjMatrix.insertEdge(a,c,null);
		CS16Edge<String> e5 = adjMatrix.insertEdge(d,b,null);
		
		MyPageRank<String> pr = new MyPageRank<String>();
	
		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);
	
		assertEquals(output.size(), 4);
		double total = 0;
		for (double rank: output.values()) {
			total += rank;
		}
		
		assertEquals(total, 1, _epsilon);
		assertThat(output.get(c) > output.get(b), is(true));
		assertThat(output.get(c) > output.get(d), is(true));
		assertThat(output.get(c) > output.get(a), is(true));
		assertThat(output.get(b) > output.get(d), is(true));
		assertThat(output.get(b) > output.get(a), is(true));
		assertThat(output.get(d) > output.get(a), is(true));
	
	}
	
	/**
	 * Tests same as above but with edges double edges, where two vertices point at
	 * each other, and a few sinks to make the graph a little more complex.
	 */
	@Test
	public void testBoxWithFourEdgesInMiddle() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");
		CS16Vertex<String> e = adjMatrix.insertVertex("E");
		CS16Vertex<String> f = adjMatrix.insertVertex("F");
		
		CS16Edge<String> e0 = adjMatrix.insertEdge(a,b,null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(b,c,null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(d,a,null);
		CS16Edge<String> e3 = adjMatrix.insertEdge(d,c,null);
		CS16Edge<String> e4 = adjMatrix.insertEdge(a,c,null);
		CS16Edge<String> e5 = adjMatrix.insertEdge(d,b,null);
		CS16Edge<String> e6 = adjMatrix.insertEdge(c,a,null);
		CS16Edge<String> e7 = adjMatrix.insertEdge(b,d,null);
		CS16Edge<String> e8 = adjMatrix.insertEdge(a,e,null);
		CS16Edge<String> e9 = adjMatrix.insertEdge(c,f,null);
		
		MyPageRank<String> pr = new MyPageRank<String>();
	
		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);
	
		assertEquals(output.size(), 6);
		double total = 0;
		for (double rank: output.values()) {
			total += rank;
		}
		
		assertEquals(total, 1, _epsilon);
		assertEquals(output.get(a),output.get(c), _epsilon);
		assertThat(output.get(a) > output.get(d), is(true));
		assertThat(output.get(a) > output.get(b), is(true));
		assertThat(output.get(a) > output.get(e), is(true));
		assertThat(output.get(a) > output.get(f), is(true));
		assertEquals(output.get(b), output.get(d), _epsilon);
		assertThat(output.get(f) > output.get(e), is(true));
		assertThat(output.get(b) > output.get(e), is(true ));
	
	}
	
	/**
	 * This tests a vertex with many incoming edges and one outgoing edge to a sink
	 * to make sure that those two vertices get more rank than the other vertices.
	 */
	@Test
	public void testGiantSinkWithBuddy() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");
		CS16Vertex<String> e = adjMatrix.insertVertex("E");
		CS16Vertex<String> f = adjMatrix.insertVertex("F");
		
		CS16Edge<String> e0 = adjMatrix.insertEdge(a,b,null);
		CS16Edge<String> e1 = adjMatrix.insertEdge(b,f,null);
		CS16Edge<String> e2 = adjMatrix.insertEdge(c,b,null);
		CS16Edge<String> e3 = adjMatrix.insertEdge(d,b,null);
		CS16Edge<String> e4 = adjMatrix.insertEdge(e,b,null);
		
		MyPageRank<String> pr = new MyPageRank<String>();
	
		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);
	
		assertEquals(output.size(), 6);
		double total = 0;
		for (double rank: output.values()) {
			total += rank;
		}
		
		assertEquals(total, 1, _epsilon);
		assertEquals(output.get(b), output.get(f), _epsilon);
		assertEquals(output.get(a), output.get(c), _epsilon);
		assertEquals(output.get(c), output.get(d), _epsilon);
		assertEquals(output.get(d), output.get(e), _epsilon);
	
	}
	
	/**
	 * Tests that for a graph with one vertex, no page rank is created or destroyed.
	 */
	@Test
	public void testSingleNode() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		
		MyPageRank<String> pr = new MyPageRank<String>();
		
		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);
	
		assertEquals(output.size(), 1);
		double total = 0;
		for (double rank: output.values()) {
			total += rank;
		}
		assertEquals(total, 1, _epsilon);
	}
	
	/**
	 * Tests a graph four vertices, where two feed into 1 sink and one has no edges at all.
	 */
	@Test
	public void testLonesomeVertex() {
		Graph<String> adjMatrix = new AdjacencyMatrixGraph<String>(true);
		CS16Vertex<String> a = adjMatrix.insertVertex("A");
		CS16Vertex<String> b = adjMatrix.insertVertex("B");
		CS16Vertex<String> c = adjMatrix.insertVertex("C");
		CS16Vertex<String> d = adjMatrix.insertVertex("D");
		
		adjMatrix.insertEdge(a,b,null);
		adjMatrix.insertEdge(c,b,null);
		
		MyPageRank<String> pr = new MyPageRank<String>();
		
		Map<CS16Vertex<String>, Double> output = pr.calcPageRank(adjMatrix);
	
		assertEquals(output.size(), 4);
		double total = 0;
		for (double rank: output.values()) {
			total += rank;
		}
		assertEquals(total, 1, _epsilon);
		assertThat(output.get(b) > output.get(a), is(true));
		assertThat(output.get(b) > output.get(c), is(true));
		assertThat(output.get(b) > output.get(d), is(true));
		assertEquals(output.get(c), output.get(a), _epsilon);
		assertEquals(output.get(c), output.get(d), _epsilon);
	}
	
}
