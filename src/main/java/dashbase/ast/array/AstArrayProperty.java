package dashbase.ast.array;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;

import java.util.List;

public class AstArrayProperty extends QueryAstList{
    public AstArrayProperty(List<AstNode> children) {
        super(children, -1);
    }
}
