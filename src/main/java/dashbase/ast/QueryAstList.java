package dashbase.ast;


import bnfgenast.ast.base.AstList;
import bnfgenast.ast.base.AstNode;
import dashbase.env.Context;
import dashbase.env.Evaluable;

import java.util.List;

public class QueryAstList extends AstList implements Evaluable {


    public QueryAstList(List<AstNode> children, int tag) {
        super(children, tag);
    }

    @Override
    public void eval(Context context) {

    }
}
