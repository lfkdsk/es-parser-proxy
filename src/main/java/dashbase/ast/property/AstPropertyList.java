package dashbase.ast.property;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.ast.env.Context;
import dashbase.token.Tokens;

import java.util.List;

/**
 * Use is { } object
 * {
 * property - list
 * }
 * property list := [ property ]
 *
 * @author liufengkai
 */
public class AstPropertyList extends QueryAstList {
    public AstPropertyList(List<AstNode> children) {
        super(children, Tokens.PROPERTY_LIST);
    }

    @Override
    public void eval(Context context) {
        for (AstNode node : this) {
            node.eval(context);
        }
    }
}
