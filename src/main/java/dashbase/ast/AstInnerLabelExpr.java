package dashbase.ast;

import com.lfkdsk.justel.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class AstInnerLabelExpr extends QueryAstList {
    public AstInnerLabelExpr(List<AstNode> children) {
        super(children, Tokens.INNER_LABEL);
    }

    public String key() {
        return child(0).toString();
    }

    public String value() {
        return child(1).toString();
    }
}
