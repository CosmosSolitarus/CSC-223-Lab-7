/**
 * Implements the geometric definition of a line segment: 
 * a line bounded by two points
 * 
 * @authors Jack, Sage, Della 
 * @date 1/21/24
 */

package input.components.segment;

import input.components.ComponentNode;
import input.components.point.PointNode;
import input.visitor.ComponentNodeVisitor;

/**
 * A utility class only for representing ONE segment
 */
public class SegmentNode implements ComponentNode
{
	protected PointNode _point1;
	protected PointNode _point2;
	
	public PointNode getPoint1() { return _point1; }
	public PointNode getPoint2() { return _point2; }
	
	public SegmentNode(PointNode pt1, PointNode pt2)
	{
		_point1 = pt1;
		_point2 = pt2;
	}

	/**
	 * Determines if this segment equals a given segment.
	 * Two segments are equal if they share the same
	 * endpoints. Point order does not matter, nor do names.
	 * 
	 * @param obj - the given points
	 * @return boolean - if the points are equal
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof SegmentNode)) {
			return false;
		}
		
		SegmentNode that = (SegmentNode) obj;

		if (this._point1.equals(that._point1) && this._point2.equals(that._point2)) {
			return true;
		}

		if (this._point1.equals(that._point2) && this._point2.equals(that._point1)) {
			return true;
		}

		return false;
	}
	
	/**
	 * Allows for visitor design pattern.
	 */
	@Override
	public Object accept(ComponentNodeVisitor visitor, Object o) {
		return visitor.visitSegmentNode(this, o);
	}

	/**
	 * toString method for testing (and gets called by other classes)
	 */
	@Override
	public String toString() {
		return "[" + _point1.toString() + ", " + _point2.toString() + "]";
	}
}