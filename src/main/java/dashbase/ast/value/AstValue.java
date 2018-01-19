package dashbase.ast.value;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

/**
 * literal := literal | object | property
 *
 * @author liufengkai
 */
public class AstValue extends QueryAstList {
    public AstValue(List<AstNode> children) {
        super(children, Tokens.AST_VALUE);
    }


}
