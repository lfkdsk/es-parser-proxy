package dashbase.ast;

import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class AstPrimaryList extends QueryAstList{
    public AstPrimaryList(List<AstNode> children) {
        super(children, Tokens.PRIMARY_LIST);
    }
}
