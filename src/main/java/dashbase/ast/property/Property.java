package dashbase.ast.property;

import bnfgenast.ast.base.AstNode;
import dashbase.ast.QueryAstList;
import dashbase.ast.literal.StringLiteral;
import dashbase.env.Context;
import dashbase.token.Tokens;
import lombok.Setter;

import java.util.List;
import java.util.function.BiConsumer;

public abstract class Property extends QueryAstList {

    @Setter
    private BiConsumer<Property, Context> callback;

    public Property(List<AstNode> children, int tag) {
        super(children, tag);
    }

    public StringLiteral keyNode() {
        return (StringLiteral) child(0);
    }

    public AstNode valueNode() {
        return child(1);
    }

    public AstProperty.PropertyType type() {
        int tag = this.getTag();

        switch (tag) {
            case Tokens.AST_PRIMARY_PROPERTY: {
                return AstProperty.PropertyType.PRIMARY;
            }
            case Tokens.AST_ARRAY_PROPERTY: {
                return AstProperty.PropertyType.ARRAY;
            }
            default:
            case Tokens.AST_OBJECT_PROPERTY: {
                return AstProperty.PropertyType.OBJECT;
            }
            case Tokens.AST_ALL_PROPERTY: {
                return AstProperty.PropertyType.ALL;
            }
        }
    }

    @Override
    public void eval(Context context) {
//        String type;
//        switch (getTag()) {
//            case Tokens.AST_PRIMARY_PROPERTY: {
//                type = "[" + GrammarMode.PRIMARY.name() + "]";
//                break;
//            }
//            case Tokens.AST_ARRAY_PROPERTY: {
//                type = "[" + GrammarMode.ARRAY.name() + "]";
//                break;
//            }
//            default:
//            case Tokens.AST_OBJECT_PROPERTY: {
//                type = "[" + GrammarMode.OBJECT.name() + "]";
//                break;
//            }
//        }
//
//        Dependency method = context.getEvals().get(keyNode().value() + type);
//        if (method != null) {
//            try {
//                method.getBindMethod().getMethod().invoke(method.getBindMethod().getInstance(), this, context);
//            } catch (IllegalAccessException | InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        }
        if (callback != null) {
            callback.accept(this, context);
        }
    }
}
