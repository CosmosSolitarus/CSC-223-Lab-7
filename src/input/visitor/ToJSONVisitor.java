package input.visitor;

import java.util.Set;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;
import input.parser.JSON_Constants;

public class ToJSONVisitor implements ComponentNodeVisitor {

    /**
     * Implementation of visitor design pattern for FigureNode
     */
    @Override
    public JSONObject visitFigureNode(FigureNode node, Object o) {
        return new JSONObject().put(
                JSON_Constants.JSON_FIGURE,
                new JSONObject()
                        .put(JSON_Constants.JSON_DESCRIPTION, node.getDescription())
                        .put(
                                JSON_Constants.JSON_POINT_S,
                                this.visitPointNodeDatabase(
                                        node.getPointsDatabase(),
                                        null))
                        .put(
                                JSON_Constants.JSON_SEGMENTS,
                                this.visitSegmentDatabaseNode(
                                        node.getSegments(),
                                        null)));
    }

    /**
     * Implementation of visitor design pattern for PointNode
     */
    @Override
    public JSONObject visitPointNode(PointNode node, Object o) {
        return new JSONObject().put(JSON_Constants.JSON_NAME, node.getName()).put(JSON_Constants.JSON_X, node.getX())
                .put(JSON_Constants.JSON_Y, node.getY());
    }

    /**
     * Implementation of visitor design pattern for PointNodeDatabase
     */
    @Override
    public JSONArray visitPointNodeDatabase(PointNodeDatabase node, Object o) {
        JSONArray points = new JSONArray();

        for (PointNode point : node.getPoints()) {
            points.put(this.visitPointNode(point, null));
        }

        return points;
    }

    /**
     * Implementation of visitor design pattern for SegmentNodeDatabase
     */
    @Override
    public JSONArray visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o) {
        JSONArray segmentMaps = new JSONArray();

        for (Entry<PointNode, Set<PointNode>> entry : node.getAdjacencyMap().entrySet()) {
            Set<PointNode> list = entry.getValue();

            JSONArray pointList = new JSONArray();

            for (PointNode pn : list) {
                pointList.put(pn.getName());
            }

            segmentMaps.put(new JSONObject().put(entry.getKey().getName(), pointList));
        }

        return segmentMaps;
    }

    /**
     * Implementation of visitor design pattern for SegmentNode
     * (left  as null for now)
     */
    @Override
    public Object visitSegmentNode(SegmentNode node, Object o) {
        return null;
    }
}
