package dashbase.ast.property;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

/**
 * Use is { } object
 * {
 *     property - list
 * }
 * @author liufengkai
 */
public class AstPropertyList extends QueryAstList {
    public AstPropertyList(List<AstNode> children) {
        super(children, Tokens.PRIMARY_LIST);
    }
}
