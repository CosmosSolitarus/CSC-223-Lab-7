/**
 * Implements a point as a two-dimensional geometry object 
 * defined by its coordinates x and y, denoted Name(x, y).
 * 
 * @authors Jack, Sage, Della
 * @date 1/21/24
 */

package input.components.point;

import input.components.ComponentNode;
import input.visitor.ComponentNodeVisitor;
import utilities.math.MathUtilities;

/**
 * A named 2D Point: [Name](x, y).
 */
public class PointNode implements ComponentNode {
	protected static final String ANONYMOUS = "__UNNAMED";

	protected double _x;

	public double getX() {
		return this._x;
	}

	protected double _y;

	public double getY() {
		return this._y;
	}

	protected String _name;

	public String getName() {
		return _name;
	}

	/**
	 * Create a new Point with the specified coordinates.
	 * 
	 * @param x -- The X coordinate
	 * @param y -- The Y coordinate
	 */
	public PointNode(double x, double y) {
		_name = ANONYMOUS;
		_x = MathUtilities.removeLessEpsilon(x);
		_y = MathUtilities.removeLessEpsilon(y);
	}

	/**
	 * Create a new Point with the specified coordinates and a name.
	 * 
	 * @param name -- The name of the point. (Assigned by the UI)
	 * @param x    -- The X coordinate
	 * @param y    -- The Y coordinate
	 */
	public PointNode(String name, double x, double y) {
		// new String(name) is necessary to allow "__UNNAMED"
		// to be passed in as a name for the point.
		_name = new String(name);
		_x = MathUtilities.removeLessEpsilon(x);
		_y = MathUtilities.removeLessEpsilon(y);
	}

	@Override
	public int hashCode() {
		return Double.valueOf(_x).hashCode() + Double.valueOf(_y).hashCode();
	}

	/**
	 * Determines if this point equals a given point.
	 * Two points are equal if they share the same
	 * x-values and y-values. Name does not matter.
	 * 
	 * @param obj -- the given points
	 * @return boolean -- if the points are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PointNode)) {
			return false;
		}

		PointNode that = (PointNode) obj;

		return MathUtilities.doubleEquals(_x, that.getX()) && MathUtilities.doubleEquals(_y, that.getY());
	}

	/**
	 * Allows for visitor design pattern.
	 */
	@Override
	public Object accept(ComponentNodeVisitor visitor, Object o) {
		return visitor.visitPointNode(this, o);
	}

	@Override
	public String toString() {
		String outName = _name;
		String outX = _x + "";
		String outY = _y + "";

		// Formatting
		// ----------------------------------------------------------------
		// check if outname points to ANONYMOUS for empty name of point
		if (outName == ANONYMOUS) {
			outName = "";
		}

		// removes ".0" from integers stored as doubles for x-value
		if (_x == (int) _x) {
			outX = (int) _x + "";
		}

		// removes ".0" from integers stored as doubles for y-value
		if (_y == (int) _y) {
			outY = (int) _y + "";
		}
		// ----------------------------------------------------------------

		return outName + "(" + outX + ", " + outY + ")";
	}
}