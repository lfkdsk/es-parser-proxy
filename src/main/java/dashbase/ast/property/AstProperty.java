package dashbase.ast.property;

import bnfgenast.ast.base.AstNode;
import dashbase.ast.QueryAstList;
import dashbase.env.Context;
import dashbase.ast.token.Tokens;

import java.util.List;

/**
 * Property Parent Node
 * property := primary | object | array
 * 这个类不会出现在 AST 结构当中
 *
 * @author liufengkai
 * @see dashbase.ast.primary.AstPrimaryProperty
 * @see dashbase.ast.object.AstObjectProperty
 * @see dashbase.ast.array.AstArrayProperty
 */
@Deprecated
public class AstProperty extends QueryAstList {

    public AstProperty(List<AstNode> children) {
        super(children, Tokens.PROPERTY);
    }

    public Property property() {
        return (Property) child(0);
    }

    @Override
    public void eval(Context context) {
        this.property().eval(context);
    }
}