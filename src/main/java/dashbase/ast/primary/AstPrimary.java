package dashbase.ast.primary;

import bnfgenast.ast.base.AstNode;
import dashbase.ast.QueryAstList;
import dashbase.ast.literal.Literal;
import dashbase.ast.token.Tokens;

import java.util.List;

/**
 * AstPrimary 指的就是具体的值类型
 * 中间层 不会出现在 AST 中
 * 支持的值类型有 string | bool | number (int/float/long/double)
 *
 * @author liufengkai
 */
@Deprecated
public class AstPrimary extends QueryAstList {

    public AstPrimary(List<AstNode> children) {
        super(children, Tokens.AST_PRIMARY);
    }

    public Literal literal() {
        return (Literal) child(0);
    }

    public Object value() {
        return literal().value();
    }
}
