package input.components;

import input.visitor.ComponentNodeVisitor;

/**
 * Allows for visitor design pattern.
 */
public interface ComponentNode {
	Object accept(ComponentNodeVisitor visitor, Object o);
}
