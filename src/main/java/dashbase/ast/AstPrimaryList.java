package dashbase.ast;

import com.lfkdsk.justel.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class AstPrimaryList extends QueryAstList{
    public AstPrimaryList(List<AstNode> children) {
        super(children, Tokens.PRIMARY_LIST);
    }
}
