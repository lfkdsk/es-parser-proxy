package dashbase.ast.inner;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class TermLabel extends QueryAstList {
    public TermLabel(List<AstNode> children) {
        super(children, Tokens.TERM);
    }
}
