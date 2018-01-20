package dashbase.ast;

import dashbase.ast.base.AstList;
import dashbase.ast.base.AstNode;

import java.util.List;

public class QueryAstList extends AstList {


    public QueryAstList(List<AstNode> children, int tag) {
        super(children, tag);
    }

}
