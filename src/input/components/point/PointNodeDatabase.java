/**
 * Implements LinkedHashSet database for point nodes.
 * 
 * @authors Jack, Sage, Della 
 * @date 1/26/24
 */

package input.components.point;

import java.util.*;

import input.components.ComponentNode;
import input.visitor.ComponentNodeVisitor;

public class PointNodeDatabase implements ComponentNode {
	protected Set<PointNode> _points;

	/**
	 * Default PointNodeDatabase Constructor
	 */
	public PointNodeDatabase() {
		_points = new LinkedHashSet<>();
	}

	/**
	 * Overloaded PointNodeDatabase Constructor
	 * 
	 * @param pns list of points to add to the database
	 */
	public PointNodeDatabase(List<PointNode> pns) {
		_points = new LinkedHashSet<>(pns);
	}

	/**
	 * Adds new PointNode to database of points
	 * 
	 * @param pn the point
	 */
	public void put(PointNode pn) {
		_points.add(pn);
	}

	/**
	 * Determines whether a point is contained in the database
	 * 
	 * @param pn the point
	 * @return boolean whether point is contained
	 */
	public boolean contains(PointNode pn) {
		return _points.contains(pn);
	}

	/**
	 * Determines whether a point (by x-y coords) is in the database
	 * 
	 * @param x x-coordinate of the point
	 * @param y y-coordinate of the point
	 * @return boolean whether the point is contained
	 */
	public boolean contains(double x, double y) {
		return _points.contains(new PointNode(x, y));
	}

	/**
	 * Finds and returns name of given point
	 * 
	 * @param pn the point
	 * @return the name of the point (or null if doesnt exist)
	 */
	public String getName(PointNode pn) {
		for (PointNode _pn : _points) {
			if (_pn.equals(pn)) {
				return _pn.getName();
			}
		}

		return null;
		// return getPoint(pn)._name;
	}

	/**
	 * Finds and returns name of given point by x-y coords.
	 * 
	 * @param x x-coordinate of the point
	 * @param y y-coordinate of the point
	 * @return the point (or null if it doesnt exist)
	 */
	public String getName(double x, double y) {
		return getName(new PointNode(x, y));
	}

	/**
	 * Finds and returns a point given the point
	 * Useful for internal calls
	 * 
	 * @param pn the point
	 * @return the point (or null if it doesnt exist)
	 */
	public PointNode getPoint(PointNode pn) {
		for (PointNode _pn : _points) {
			if (_pn.equals(pn)) {
				return _pn;
			}
		}

		return null;
	}

	/**
	 * Finds and returns a point given the point's name
	 * Necessary for parsing
	 * 
	 * @param name name of the desired point
	 * @return the point (or null if it doesnt exist)
	 */
	public PointNode getPoint(String name) {
		for (PointNode _pn : _points) {
			if (_pn.getName().equals(name)) {
				return _pn;
			}
		}
		return null;
	}

	/**
	 * Returns a new PointNode given x-y coordinates
	 * 
	 * @param x x-coordinate of the new point
	 * @param y y-coordinate of the new point
	 * @return the new point
	 */
	public PointNode getPoint(double x, double y) {
		return getPoint(new PointNode(x, y));
	}

	/**
	 * Allows for visitor design pattern.
	 */
	@Override
	public Object accept(ComponentNodeVisitor visitor, Object o) {
		return visitor.visitPointNodeDatabase(this, o);
	}

	/**
	 * toString method for testing
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (PointNode pn : _points) {
			s.append(pn.toString());
			s.append('\n');
		}
		return s.toString();
	}

	/**
	 * Return the full list of points
	 */
	public Set<PointNode> getPoints() {
		return _points;
	}
}
