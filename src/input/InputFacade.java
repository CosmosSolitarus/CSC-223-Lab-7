package input;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import geometry_objects.Segment;
import input.builder.GeometryBuilder;
import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.segment.SegmentNode;
import input.parser.JSONParser;

/**
 * InputFacade implements the Facade design pattern. Purpose is to simplify interface to
 * nodes and GeometryFigures.
 * 
 * @author	Jack, Sage
 * @data	3/26/24
 */

public class InputFacade
{
	/**
	 * A utility method to acquire a figure from the given JSON file:
	 *     Constructs a parser
	 *     Acquries an input file string.
	 *     Parses the file.
     *
	 * @param filepath -- the path/name defining the input file
	 * @return a FigureNode object corresponding to the input file.
	 */
	public static FigureNode extractFigure(String filepath)
	{
        JSONParser parser = new JSONParser(new GeometryBuilder());

		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filepath);

		return (FigureNode) parser.parse(figureStr);
	}
	
	/**
	 * 1) Convert the PointNode and SegmentNode objects to a Point and Segment objects 
	 *    (those classes have more meaningful, geometric functionality).
	 * 2) Return the points and segments as a pair.
     *
	 * @param fig -- a populated FigureNode object corresponding to a geometry figure
	 * @return a point database and a set of segments
	 */
	public static Map.Entry<PointDatabase, Set<Segment>> toGeometryRepresentation(FigureNode fig)
	{
		return new AbstractMap.SimpleEntry<>(figToPointDatabase(fig), figToSegmentSet(fig));
	}

	/**
	 * Helper method for toGeometryRepresentation(). Transfomrs 
	 * PointNodeDatabase to PointDatabase.
	 * 
	 * @param fig	a populated FigureNode object corresponding to a geometry figure
	 * @return		the PointDatabase
	 */
	private static PointDatabase figToPointDatabase(FigureNode fig) {
		Set<PointNode> pns = fig.getPointsDatabase().getPoints();
		List<Point> ps = new ArrayList<>();

		for (PointNode pn : pns) {
			ps.add(new Point(pn.getName(), pn.getX(), pn.getY()));
		}

		return new PointDatabase(ps);
	}

	/**
	 * Helper method for toGeometryRepresentation(). Transforms
	 * SegmentNodeDatabase to SegmentDatabase.
	 * @param fig	a populated FigureNode object corresponding to a geometry figure
	 * @return		the SegmentDatabase
	 */
	private static Set<Segment> figToSegmentSet(FigureNode fig) {
		List<SegmentNode> sns = fig.getSegments().asSegmentList();
		Set<Segment> out = new LinkedHashSet<>();

		for (SegmentNode sn : sns) {
			PointNode pn0 = sn.getPoint1();
			PointNode pn1 = sn.getPoint2();

			Point p0 = new Point(pn0.getName(), pn0.getX(), pn0.getY());
			Point p1 = new Point(pn1.getName(), pn1.getX(), pn1.getY());

			out.add(new Segment(p0, p1));
		}

		return out;
	}
}
