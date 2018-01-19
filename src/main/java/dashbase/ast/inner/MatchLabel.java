package dashbase.ast.inner;

import dashbase.ast.AstLabelList;
import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class MatchLabel extends QueryAstList {
    public MatchLabel(List<AstNode> children) {
        super(children, Tokens.MATCH);
    }

    public AstLabelList list() {
        return (AstLabelList) child(2);
    }

    public String text() {
        return child(1).toString();
    }
}
