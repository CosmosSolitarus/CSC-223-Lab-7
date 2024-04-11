package input.visitor;

import java.util.AbstractMap;

import input.components.*;
import input.components.point.*;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;

//
// This file implements a Visitor (design pattern) with 
// the intent of building an unparsed, String representation
// of a geometry figure.
//
public class UnparseVisitor implements ComponentNodeVisitor {
	@Override
	public Object visitFigureNode(FigureNode node, Object o) {
		// Unpack the input object containing a Stringbuilder and an indentation level
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>) (o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();

		sb.append("Figure");
		sb.append('\n');

		sb.append("{");
		sb.append('\n');

		sb.append("\tDescription : ");
		sb.append(node.getDescription());
		sb.append('\n');

		this.visitPointNodeDatabase(node.getPointsDatabase(),
				new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, level + 1));

		sb.append('\n');

		this.visitSegmentDatabaseNode(node.getSegments(),
				new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, level + 1));

		sb.append('\n');

		sb.append("}");

		return o;
	}

	@Override
	public Object visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o) {
		// Unpack the input object containing a Stringbuilder and an indentation level
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>) (o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();

		for (int i = 0; i < level; i++) {
			sb.append("	");
		}

		sb.append("Segments : ");
		sb.append('\n');

		for (int i = 0; i < level; i++) {
			sb.append("	");
		}

		sb.append("{");
		sb.append('\n');

		for (PointNode fromPN : node.getAdjacencyMap().keySet()) {
			for (int i = 0; i < level + 1; i++) {
				sb.append("	");
			}

			sb.append(fromPN.getName());
			sb.append(" : ");

			for (PointNode toPN : node.getAdjacencyMap().get(fromPN)) {
				sb.append(toPN.getName());
				sb.append(" ");
			}

			sb.append('\n');
		}

		for (int i = 0; i < level; i++) {
			sb.append("	");
		}

		sb.append("}");

		return o;
	}

	/**
	 * This method should NOT be called since the segment database
	 * uses the Adjacency list representation
	 */
	@Override
	public Object visitSegmentNode(SegmentNode node, Object o) {

		return null;
	}

	@Override
	public Object visitPointNodeDatabase(PointNodeDatabase node, Object o) {
		// Unpack the input object containing a Stringbuilder and an indentation level
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>) (o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();

		for (int i = 0; i < level; i++) {
			sb.append("	");
		}

		sb.append("Points : ");
		sb.append('\n');

		for (int i = 0; i < level; i++) {
			sb.append("	");
		}

		sb.append("{");
		sb.append('\n');

		for (PointNode pn : node.getPoints()) {
			this.visitPointNode(pn, new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, level + 1));
			sb.append('\n');
		}

		for (int i = 0; i < level; i++) {
			sb.append("	");
		}

		sb.append("}");

		return o;
	}

	@Override
	public Object visitPointNode(PointNode node, Object o) {
		// Unpack the input object containing a Stringbuilder and an indentation level
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>) (o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();

		for (int i = 0; i < level; i++) {
			sb.append("	");
		}
		sb.append(node.toString());

		return o;
	}
}