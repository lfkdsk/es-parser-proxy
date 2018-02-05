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
public class AstProperty extends QueryAstList {

    public enum PropertyType {
        PRIMARY, OBJECT, ARRAY, ALL
    }

    public AstProperty(List<AstNode> children) {
        super(children, Tokens.PROPERTY);
    }

    public Property property() {
        return (Property) child(0);
    }

    public PropertyType type() {
        int tag = property().getTag();

        switch (tag) {
            case Tokens.AST_PRIMARY_PROPERTY: {
                return PropertyType.PRIMARY;
            }
            case Tokens.AST_ARRAY_PROPERTY: {
                return PropertyType.ARRAY;
            }
            default:
            case Tokens.AST_OBJECT_PROPERTY: {
                return PropertyType.OBJECT;
            }
            case Tokens.AST_ALL_PROPERTY: {
                return PropertyType.ALL;
            }
        }
    }

    @Override
    public void eval(Context context) {
        this.property().eval(context);
    }
}