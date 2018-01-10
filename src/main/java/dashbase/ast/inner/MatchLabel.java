package dashbase.ast.inner;

import com.lfkdsk.justel.ast.base.AstNode;
import dashbase.ast.AstLabelList;
import dashbase.ast.AstObjectLabel;
import dashbase.ast.AstPrimary;
import dashbase.ast.QueryAstList;
import dashbase.token.Tokens;

import java.util.List;

public class MatchLabel extends QueryAstList {
    public MatchLabel(List<AstNode> children) {
        super(children, Tokens.MATCH);
    }

    public AstLabelList list() {
        return (AstLabelList) child(2);
    }

    public AstObjectLabel match() {
        return (AstObjectLabel) list().child(0);
    }

    public String text() {
        return child(2).toString();
    }
}
