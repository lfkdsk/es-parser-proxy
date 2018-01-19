/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package dashbase.ast.base;


import dashbase.ast.env.Context;
import dashbase.ast.env.Evaluable;
import dashbase.exception.EvalException;
import lombok.Getter;
import lombok.Setter;

import java.util.Iterator;

/**
 * AST Tree Basic Node
 *
 * @author liufengkai
 */
public abstract class AstNode implements Iterable<AstNode>, Evaluable {

    /**
     * Spec Tag for Ast Node
     */
    @Getter
    private final int tag;

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
    @Override
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

    @Override
    public void eval(Context context) {
        throw new EvalException("can not eval : " + toString(), this);
    }
}
