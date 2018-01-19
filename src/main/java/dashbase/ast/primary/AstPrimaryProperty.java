package dashbase.ast.primary;

import dashbase.ast.value.AstValue;
import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.ast.literal.StringLiteral;
import dashbase.token.Tokens;

import java.util.List;

public class AstPrimaryProperty extends QueryAstList {
    public AstPrimaryProperty(List<AstNode> children) {
        super(children, Tokens.AST_PRIMARY_PROPERTY);
    }

    public StringLiteral keyNode() {
        return (StringLiteral) child(0);
    }

    public AstValue valueNode() {
        return (AstValue) child(1);
    }
}
