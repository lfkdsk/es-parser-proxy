package dashbase.ast.inner;

import com.lfkdsk.justel.ast.base.AstNode;
import dashbase.ast.QueryAstList;
import dashbase.token.Tokens;

import java.util.List;

public class BoolLabel extends QueryAstList{
    public BoolLabel(List<AstNode> children) {
        super(children, Tokens.BOOL);
    }


}
