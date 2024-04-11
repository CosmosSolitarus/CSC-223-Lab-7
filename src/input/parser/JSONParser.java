/**
 * creates a FigureNode from a given JSON file
 * 
 * @authors Della, Jack, Sage 
 * @date 2/20/24
 */

package input.parser;

import java.util.ArrayList;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import input.builder.DefaultBuilder;
import input.components.*;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNodeDatabase;
import input.exception.ParseException;

public class JSONParser {
	protected ComponentNode _astRoot;
	private DefaultBuilder builder;

	/**
	 * Default constructor, will not build
	 */
	public JSONParser() {
		this(new DefaultBuilder());
	}

	/**
	 * Updated constructor with builder
	 */
	public JSONParser(DefaultBuilder builder) {
		_astRoot = null;
		this.builder = builder;
	}

	/**
	 * method that throws a ParseException
	 */
	private void error(String message) {
		throw new ParseException("Parse error: " + message);
	}

	/**
	 * creates a FigureNode from the information within the JSON file
	 * 
	 * @param str (JSON file)
	 * @return FigureNode
	 * @throws ParseException if JSONException is thrown
	 */
	public ComponentNode parse(String str) throws ParseException {
		// Parsing is accomplished via the JSONTokenizer class.
		try {
			JSONTokener tokenizer = new JSONTokener(str);
			JSONObject JSONroot = (JSONObject) tokenizer.nextValue();
			JSONObject jsonFigure = (JSONObject) JSONroot.get("Figure");

			String description = getDescription(jsonFigure);
			PointNodeDatabase pointsDatabase = getPointNodeDatabase(jsonFigure);

			SegmentNodeDatabase segmentsDatabase = getSegmentNodeDatabase(jsonFigure, pointsDatabase);

			return builder.buildFigureNode(description, pointsDatabase, segmentsDatabase);
		} catch (JSONException e) {
			error("");
		}

		return null; // if an exception is thrown
	}

	/**
	 * retrieves the description from the JSON file
	 * 
	 * @param figure
	 * @return String of description
	 */
	private String getDescription(JSONObject figure) {
		return figure.getString("Description");
	}

	/**
	 * retrieves the points from the JSON file
	 * 
	 * @param figure
	 * @return PointNodeDatabase of all the points
	 */
	private PointNodeDatabase getPointNodeDatabase(JSONObject figure) {
		JSONArray jsonPoints = (JSONArray) figure.getJSONArray("Points");
		ArrayList<PointNode> points = new ArrayList<>();

		for (int i = 0; i < jsonPoints.length(); i++) {
			JSONObject point = jsonPoints.getJSONObject(i);

			String name = point.getString("name");
			double x = point.getDouble("x");
			double y = point.getDouble("y");

			PointNode pn = builder.buildPointNode(name, x, y);

			points.add(pn);
		}

		return builder.buildPointDatabaseNode(points);
	}

	/**
	 * retrieves the segments from the JSON file
	 * 
	 * @param figure
	 * @return SegmentNodeDatabase of all the possible segments
	 */
	private SegmentNodeDatabase getSegmentNodeDatabase(JSONObject figure, PointNodeDatabase pointNodeDatabase) {
		JSONArray jsonSegments = (JSONArray) figure.getJSONArray("Segments");

		// may be null
		SegmentNodeDatabase segmentDatabase = builder.buildSegmentNodeDatabase();

		for (int i = 0; i < jsonSegments.length(); i++) {
			JSONObject curr = jsonSegments.getJSONObject(i);
			// the set only contains the key of curr
			Set<String> keySet = curr.keySet();

			String keyName = keySet.iterator().next(); // the key as a String from the set
			PointNode fromPN = pointNodeDatabase.getPoint(keyName);
			JSONArray adjList = curr.getJSONArray(keyName); // the values associated with the key

			// for every node connected to the key
			for (int i2 = 0; i2 < adjList.length(); i2++) {
				PointNode toPN = pointNodeDatabase.getPoint(adjList.getString(i2));

				builder.addSegmentToDatabase(segmentDatabase, fromPN, toPN);
			}
		}

		return segmentDatabase;
	}
}