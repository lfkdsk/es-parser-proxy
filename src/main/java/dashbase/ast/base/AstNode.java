/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package dashbase.ast.base;


import lombok.Getter;
import lombok.Setter;

import java.util.Iterator;

/**
 * AST Tree Basic Node
 *
 * @author liufengkai
 */
public abstract class AstNode implements Iterable<AstNode> {

    /**
     * Spec Tag for Ast Node
     */
    @Getter
    private final int tag;

    public static final int AMPERSAND_OP = 600;

    public static final int PRIMARY_EXPR = 601;

    public static final int BINARY_EXPR = 602;

    public static final int FUNC_ARGUMENT_EXPR = 603;

    public static final int ARRAY_INDEX_OP = 604;

    public static final int BIT_WISE_OP = 605;

    public static final int DOT_OP = 606;

    public static final int EQUAL_OP = 607;

    public static final int NEGATIVE_OP = 608;

    public static final int NOT_OP = 609;

    public static final int UN_EQUAL_OP = 610;

    public static final int FUNCTION_EXPR = 611;

    public static final int POSTFIX = 612;

    public static final int COND_EXPR = 613;

    public static final int EXTEND_FUNC = 614;

    public static final int PROGRAM = 700;

    /**
     * index of child
     */
    @Getter
    protected int childIndex = 0;

    /**
     * parent - node
     */
    @Getter
    @Setter
    protected AstNode parentNode;

    /**
     * result => toString
     */
    @Getter
    protected String evalString = null;

    /**
     * hashCode => evalString
     */
    @Getter
    protected int hash = 0;


    public AstNode(int tag) {
        this.tag = tag;
    }

    /**
     * Get Spec Child AstNode
     *
     * @param index index number
     * @return Child Node
     */
    public abstract AstNode child(int index);

    /**
     * Return Iterator of Node
     *
     * @return Iterator
     */
    public abstract Iterator<AstNode> children();

    /**
     * 子节点数目
     *
     * @return count
     */
    public abstract int childCount();

    /**
     * pos
     *
     * @return location
     */
    public abstract String location();

    /**
     * replace child spec index
     *
     * @param index index num
     * @param node  replace node
     * @return node
     */
    public abstract AstNode resetChild(int index, AstNode node);

    /**
     * iterator of list
     *
     * @return list of node
     */
    public Iterator<AstNode> iterator() {
        return children();
    }

    @Override
    public int hashCode() {
        String eval = toString();
        if (hash == 0 && eval.length() != 0) {
            hash = eval.hashCode();
        }

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }
}
