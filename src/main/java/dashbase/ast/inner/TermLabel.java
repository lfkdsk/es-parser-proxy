package dashbase.ast.inner;

import com.lfkdsk.justel.ast.base.AstNode;
import dashbase.ast.QueryAstList;
import dashbase.token.Tokens;
import sun.tools.jstat.Token;

import java.util.List;

public class TermLabel extends QueryAstList {
    public TermLabel(List<AstNode> children) {
        super(children, Tokens.TERM);
    }
}
