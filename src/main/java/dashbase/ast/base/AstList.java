/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package dashbase.ast.base;

import java.util.Iterator;
import java.util.List;

/**
 * AST List
 *
 * @author liufengkai
 */
public class AstList extends AstNode {

    /**
     * List of Child Node
     */
    protected List<AstNode> children;

    public AstList(List<AstNode> children, int tag) {
        super(tag);
        this.children = children;
    }

    @Override
    public AstNode child(int index) {
        return children.get(index);
    }

    @Override
    public Iterator<AstNode> children() {
        return children.iterator();
    }

    public List<AstNode> getChildren() {
        return children;
    }

    @Override
    public int childCount() {
        return children.size();
    }

    @Override
    public String toString() {
        if (evalString == null) {

            StringBuilder builder = new StringBuilder();

            builder.append('(');

            String sep = "";

            for (AstNode node : children) {
                builder.append(sep);
                sep = " ";
                builder.append(node.toString());
            }

            evalString = builder.append(")").toString();
        }

        return evalString;
    }

    @Override
    public String location() {
        for (AstNode n : children) {
            String s = n.location();
            if (s != null) {
                return s;
            }
        }
        return null;
    }

    @Override
    public AstNode resetChild(int index, AstNode node) {
        node.setParentNode(this);
        return children.set(index, node);
    }
}
