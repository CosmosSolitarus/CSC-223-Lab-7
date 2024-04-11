/**
 * Implements HashMap database for segment nodes.
 * 
 * @authors Jack, Sage, Della 
 * @date 1/26/24
 */

package input.components.segment;

import java.util.*;

import input.components.ComponentNode;
import input.components.point.PointNode;
import input.visitor.ComponentNodeVisitor;

public class SegmentNodeDatabase implements ComponentNode {
	protected Map<PointNode, Set<PointNode>> _adjLists;

	/**
	 * default segment node database constructor
	 */
	public SegmentNodeDatabase() {
		_adjLists = new HashMap<>();
	}

	/**
	 * Overloaded segment node database constructor
	 * 
	 * @param map initial mapping of segment nodes
	 */
	public SegmentNodeDatabase(Map<PointNode, Set<PointNode>> map) {
		_adjLists = new HashMap<>(map);
	}

	/**
	 * Counts and returns the number of undirected edges in the
	 * segment database. An edge is undirected if there is an
	 * edge from point1 to point2 and point2 to point1.
	 * 
	 * @return the number of undirected edges.
	 */
	public int numUndirectedEdges() {
		int count = 0;

		for (Set<PointNode> adjList : _adjLists.values()) {
			count += adjList.size();
		}

		// adjacencies are entered twice (A -> B and B -> A)
		// so divide by two to count total number of edges.
		return count / 2;
	}

	/**
	 * Adds a directed edge from point1 to point2
	 * 
	 * @param point1 the starting point
	 * @param point2 the ending pointt
	 */
	private void addDirectedEdge(PointNode point1, PointNode point2) {
		if (!_adjLists.containsKey(point1)) {
			_adjLists.put(point1, new HashSet<PointNode>());
		}
		_adjLists.get(point1).add(point2);
	}

	/**
	 * Adds an undirected edge by adding two directed edges in opposite directions
	 * 
	 * @param point1 first point for the edges
	 * @param point2 second point for the edges
	 */
	public void addUndirectedEdge(PointNode point1, PointNode point2) {
		addDirectedEdge(point1, point2);
		addDirectedEdge(point2, point1);
	}

	/**
	 * Adds a new point and its adjacencyList to database
	 * 
	 * @param point         the point
	 * @param adjacencyList the point's adjacency list
	 */
	public void addAdjacencyList(PointNode point, List<PointNode> adjacencyList) {
		_adjLists.put(point, new HashSet<PointNode>(adjacencyList));
	}

	/**
	 * Returns list of segments (not unique)
	 * 
	 * @return the list
	 */
	public List<SegmentNode> asSegmentList() {
		List<SegmentNode> snList = new ArrayList<SegmentNode>();

		for (PointNode pn1 : _adjLists.keySet()) {
			for (PointNode pn2 : _adjLists.get(pn1)) {
				snList.add(new SegmentNode(pn1, pn2));
			}
		}

		return snList;
	}

	/**
	 * Returns list of unique segments in segment node database
	 * 
	 * @return the list
	 */
	// brycen consulted here
	public List<SegmentNode> asUniqueSegmentList() {
		List<SegmentNode> snList = new ArrayList<SegmentNode>();

		for (SegmentNode node : asSegmentList()) {
			if (!snList.contains(node)) {
				snList.add(node);
			}
		}

		return snList;
	}

	/**
	 * Allows for visitor design pattern.
	 */
	@Override
	public Object accept(ComponentNodeVisitor visitor, Object o) {
		return visitor.visitSegmentDatabaseNode(this, o);
	}

	/**
	 * Basic toString method for testing. Unparse() should be used
	 * to manually verify parsing/unparsing is succesfull.
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		List<SegmentNode> snList = asSegmentList();

		for (SegmentNode sn : snList) {
			s.append(sn.toString());
			s.append('\n');
		}
		return s.toString();
	}

	/**
	 * Getter for internal adjacency list
	 */
	public Map<PointNode, Set<PointNode>> getAdjacencyMap() {
		return _adjLists;
	}
}
