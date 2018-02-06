package dashbase.ast.property;

import bnfgenast.ast.base.AstNode;
import dashbase.ast.QueryAstList;
import dashbase.ast.literal.StringLiteral;
import dashbase.env.Context;
import dashbase.ast.token.Tokens;
import lombok.Setter;

import java.util.List;
import java.util.function.BiConsumer;

public abstract class Property extends QueryAstList {

    public enum PropertyType {
        PRIMARY, OBJECT, ARRAY, ALL
    }

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

    public PropertyType type() {
        int tag = this.getTag();

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
        if (callback != null) {
            callback.accept(this, context);
        }
    }
}
