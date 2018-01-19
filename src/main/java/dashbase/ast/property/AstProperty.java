package dashbase.ast.property;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class AstProperty extends QueryAstList{
    public AstProperty(List<AstNode> children) {
        super(children, Tokens.PROPERTY);
    }
}
