package dashbase.ast.property;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;

import java.util.List;

public class AstProperty extends QueryAstList{
    public AstProperty(List<AstNode> children) {
        super(children, -1);
    }
}
