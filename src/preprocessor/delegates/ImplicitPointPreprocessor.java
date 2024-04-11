package preprocessor.delegates;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import geometry_objects.Segment;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;

public class ImplicitPointPreprocessor
{
	/**
	 * It is possible that some of the defined segments intersect
	 * and points that are not named; we need to capture those
	 * points and name them.
	 * 
	 * Algorithm:
	 *    TODO
	 */
	public static Set<Point> compute(PointDatabase givenPoints, List<Segment> givenSegments)
	{
		Set<Point> implicitPoints = new LinkedHashSet<Point>();
		int size = givenPoints.size();
		Point pt;

        for (int i = 0; i < size; i++) {
			for (int j = i; j < size; j++) {
				pt = givenSegments.get(i).segmentIntersection(givenSegments.get(j));

				if (pt != null) {
					implicitPoints.add(pt);
					givenPoints.put(pt.getName(), pt.getX(), pt.getY());
				}
			}
		}

		return implicitPoints;
	}

}
