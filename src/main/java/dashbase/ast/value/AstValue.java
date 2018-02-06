package dashbase.ast.value;

import bnfgenast.ast.base.AstNode;
import dashbase.ast.QueryAstList;
import dashbase.ast.array.AstArray;
import dashbase.ast.primary.AstPrimary;
import dashbase.ast.token.Tokens;

import java.util.List;

/**
 * literal := literal | object | property
 * 中间层 不出现在 AST 中
 * @author liufengkai
 */
@Deprecated
public class AstValue extends QueryAstList {
    public AstValue(List<AstNode> children) {
        super(children, Tokens.AST_VALUE);
    }

    public AstNode value() {
        AstNode node = child(0);
        int tag = node.getTag();
        switch (tag) {
            case Tokens.AST_ARRAY: {
                return ((AstArray) node).list();
            }
            case Tokens.AST_PRIMARY: {
                return ((AstPrimary) node).literal();
            }
            default:
            case Tokens.AST_OBJECT: {
                return node;
            }
        }
    }
}
