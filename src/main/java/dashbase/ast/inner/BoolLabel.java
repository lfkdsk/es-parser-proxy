package dashbase.ast.inner;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class BoolLabel extends QueryAstList{
    public BoolLabel(List<AstNode> children) {
        super(children, Tokens.BOOL);
    }


}
