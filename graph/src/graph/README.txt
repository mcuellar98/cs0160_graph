Handin: This will be my final hand in.


Design Choices: See below

Adjacency Matrix
- A linked list is used as a stand in for my queue because that was one of the suggestions when I 
  looked up how to make a queue in java.
- I used a queue instead of a stack to keep track of the vertex numbers just because of preference.
- I used a counter to keep give the vertices number until the counter hit the max. At that point the
  numbers were assigned based on what was in the queue. This kept the constructor run time down.
- For insertEdge() I only returned the first edge made regardless of the direction of the graph because
  I felt like that was what matched the demo best.
- All the methods are designed pretty simply around the runtime requirements. Where we were allowed
  to have linear runtime, I chose to iterate through a single column/row instead of use my other methods
  because I liked using for loops more than iterators.

MyDecorator
- I used a HashSet to implement the decorations because of it's constant set and get methods.

MyPrimJarnik
- I instantiated four different decorations to be used.
- The first decoration decorated vertices with their cost so the priority queue could used that value
  to set the priorities.
- The second decorated vertices with their entry in the priority queue so that I could decrease the key
  of an entry at the end of the algorithm.
- The third decorated a vertex with the a previous pointer to the vertex that has the shortest cost leading
  to it. This was done so that that previous vertex could be added to the msf.
- The last decorator decorated an edge with a boolean that represented whether it had already been 
  visited or not. This was prevent the algorithm from decreasing the key of an entry that was no longer
  in the priority queue.
- I instantiated a set that kept track of all the edges in the graph. A set was used because of its constant
  set and get methods. This set was used to check if the edge between two vertices was in the adjacency matrix
  edge set before being added to the msf. This became necessary because of the way edges are inserted into 
  my matrix. For an undirected graph, two edges are inserted between any two nodes, but only one is added to the
  set. That means the getOpposite() method can return either the edge thats not in the set or the one that is
  based on what what you choose as the beginning and end vertices for the inputs. Because I couldn't be sure 
  what the inputs would be, I decided to make the set of all the edges and check both outcomes of the getOpposite()
  method to see if they were in that set before I added one to the msf.
- The rest of the algorithm was done pretty standardly I think.

MyPageRank
- I have an extra hash map to keep track of previous rank values. This was used to calculate the what the 
  current rank values should be.
- array of ou
- I made a list of all the sinks so that I could iterate over them in the handleSinks() method.
- There's a point in my while loop where I loop through all the entries in the current ranks hash map to 
  copy those ranks over to the previous ranks hash map before the loops ends. Originally I tried to just set
  the previous ranks instance variable and equal to the current ranks variable but this resulted in a weird bug
  where the previous ranks hash map updated itself whenever the current rank hash map did, so their stored rank values
  were always the same and the program would stop early since they were within the convergence margin. So I 
  decided to copy over the values in a for loop instead because it got rid of the bug.
- I tried to store the outgoing edges for each node in a list or hash map so that I wouldn't have to use the 
  numOutgoingEdges() method to get the new rank for each vertex, since that would add to the runtime. However, I kept
  getting a bug that no matter what I did, the list or map of outgoing edges wouldn't give the correct value for the
  number of outgoing edges for a node, resulting in division by zero sometimes. I did by best to debug this with print
  statements but I'm still stumped. So as a result, while calculating the new rank, I so use the numOutgoingEdges() 
  method for every vertex.
  

Known Bugs: There are no bugs that I'm aware of.


Test Cases: 

GraphTest
- Every test for this class was done on both an undirected and directed graph, unless otherwise specified.
  for the sake of saving time and space, I will only list the undirected graph tests. But again, there is 
  another test with 'Directed' instead of 'Undirected' for each of these unless I mention otherwise.
- testInsertEdgesUndirected() tests that the insertEdges() method functions properly.
- testVerticesUndirected() tests that the vertices() method returns an iterator with all the graph's vertices.
- testEdgesIteratorUndirected tests that the edges() method returns an iterator with all the graph's edges.
- testInsertVertexAndGetNumVerticesUndirected() tests that insertVertex() and getNumVertices() function correctly
- testRemoveVertexUndirected() tests that removeVertex() removes a vertex logically from the graph.
- testRemoveVertexAndEdgesUndirected() tests that removing a vertex also logically removes all the edges incident 
  to it.
- testInsertAndRemoveEdgesUndirected() test that edges are correctly inserted and removed logically from the graph.
- testConnectingEdgeUndirected() tests that connectingEdge() will return the edge between two vertices.
- testIncomingEdgesUndirected() tests that incomingEdges() returns all the edges incident to the graph. 
- testIncomingEdgesDirected() tests that only incoming edges are returned from incomingEdges().
- testOutgoingEdgesUndirected() tests that outgoingEdges() returns all the edges incident to the graph. 
- testOutgoingEdgesDirected() tests that only outgoing edges are returned from outgoingEdges().
- testNumOutgoingEdges() only tests a directed graph and tests that the number of all the outgoing edges for
  an input vertex are returned.
- testOppositeUndirected() tests that the vertex opposite an input vertex along an input edge is correctly 
  returned.
- testEndVerticesUndirected() tests that the vertices at the end of an input edge are returned correctly.
- testAreAdjacentUndirected() tests that are adjacent correctly returns whether two vertices are adjacent.
- testToggleUndirected() tests that the toggle() clears the graph and changes it's direction.
- testClearUndirected() tests that clear method() logically removes all the 
- testSelfEdgeUndirected() tests that self loops can be implemented in the matrix.
- testVertexNumbersUndirected() tests that each vertex number is unique and that the matrix an be completely filled and a 
  vertex can be removed and added to a full matrix to show that the queue works properly in assigning a unique number.
- testClearVertexNumberUndirected() tests that clearing the matrix will also renew the vertex number counter.
- testGetNumVerticesUndirected() tests that getNumVertices() returns the correct number of vertices in the graph.
- testInsertEdgeErrorUndirected() tests that a null input for insertEdge() throws an InvalidEdgeException.
- testRemoveVertexErrorUndirected() test that a null input for removeVertex() throws an InvalidVertexException.
- testRemoveEdgeErrorUndirected() tests that a null input for removeEdge() throws an InvalidEdgeException.
- testNoConnectingEdgeErrorUndirected() tests that if there is no connecting edge exists between two vertices then 
  a NoSuchEdgeException is thrown.
- testNullFirstInputConnectingEdgeErrorUndirected() tests that if the first input is null that an InvalidVertexException
  is thrown.
- testNullSecondInputConnectingEdgeErrorUndirected() tests that if the second input is null that an InvalidVertexException
  is thrown.
- testNullBothInputsConnectingEdgeErrorUndirected() tests that if both inputs are null that an InvalidVertexException
  is thrown.
- testIncomingEdgeErrorUndirected() tests that incomingEdges() throws an error if the input is null.
- testOutgoingEdgeErrorUndirected() tests that outgoingEdges() throws an error if the input is null.
- testNumOutgoingEdgeNullError() tests that numOutgoingEdges() throws an error if the input is null.
- testNumOutgoingEdgeErrorUndirected() tests that if numOutgoinEdges() is called on an undirected graph it throws a
  DirectionException.
- testOppositeNullVertixUndirected() tests that if opposite() is passed a null vertex input it throws an InvalidVetexException.
- testOppositeNullEdgeUndirected() tests that if opposite() is passed a null edge input it throws an InvalidEdgeException.
- testOppositeNoSuchVertexUndirected() tests that if the input edge is not incident to the input vertex, a NoSuchVertexException.
- testEndVerticesErrorUndirected() tests that if the input for endVertices() is null, an InvalidEdgeException is thrown.
- testAreAdjacentFirstInputNullErrorUndirected() tests that if the first input is null, an InvalidVertexException is thrown.
- testAreAdjacentSecondInputNullErrorUndirected() tests that if the second input is null, an InvalidVertexException is thrown.
- testAreAdjacentBothInputsNullErrorUndirected() tests that if both inputs are null, an InvalidVertexException is thrown.

MsfTest
- twoVertexOneEdgeGraph() tests that two vertices with one edge between them will return 1 edge.
- testSmallGraph() tests that a small graph with many spanning trees will return the minimum spanning tree.
- testSimpleForrest() tests that a forest of two trees with one edge each will return those threes.
- testComplexForrest() tests that a forest with multiple spanning forests will return the minimum spanning forest.
- testNoVertexGraph() tests that an empty graph will return no edges.
- testSingleVertexGraph() tests that a graph with one edge and no nodes will return no edges.
- testTwoVertexForrest() tests that two vertices with no edges returns no edges.
- testEdgeDirectionality() tests that regardless of which vertex is chosen as the start or end vertex when inserting an edge,
  the right edge will be returned.

MyPageRankTest
- All of these were tested by looking at the demo for each test and comparing relative ranks of the vertices, as was suggested.
- threeVertexCycle() tests a where all nodes should end up with equal weight.
- testSingleSink() tests a graph with a single sink to test my programs handling of sinks.
- testManySinks() tests a graphs with many sinks for the same reason.
- testTwoVerticesWithTwoEdges() tests two vertices, each with an edge pointing to the other.
- testBoxWithTwoEdgesInMiddle() this was just testing a slightly more complex graph with four vertices with edges in the 
  shape of a square and then added two diagonal edges inside the square to make the graph a little more complicated.
- testBoxWithFourEdgesInMiddle() does the same as above but adds two edges to the middle and includes two sink as well
  to make test that covers goes over the previous test cases in one test.
- testGiantSinkWithBuddy() tests a vertex with a lot of incoming edges and one outgoing edge to a sink to see that both that
  vertex and the sink have a lot of rank.
- testSingleNode() tests that for a graph with one vertex, no page rank is created or destroyed.
- testLonesomeVertex() tests four vertices, two that feed into a sink and one with no edges, to see how the edges-less
  vertex's rank compares to the two feeder vertices.
  
  
Conceptual Questions:
To reduce a vertex's rank without deleting it, at the very start of my algorithm I would iterate through the vertices
that are opposite the 'bad' vertex's incomingEdges, delete the edge, and add a new edge going in the other direction. 
This would make it so that the 'bad' vertex has no incoming edges, and gives its rank out to all the vertices that were 
originally pointing to it. This also keeps the total rank at 1.

Extra Credit: 
I made a method called removeBlacklists() that implemented the program described above above to diminish the page
rank of any 'bad' vertices.






