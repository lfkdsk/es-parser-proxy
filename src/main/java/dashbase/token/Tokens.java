package dashbase.token;

import dashbase.ast.base.AstNode;
import dashbase.ast.inner.MatchLabel;
import dashbase.ast.inner.QueryLabel;
import dashbase.ast.inner.TermLabel;
import dashbase.bnf.BnfCom;

public class Tokens {
    public static final int AST_PRIMARY_LABEL = 3000;
    public static final int AST_VALUE_LABEL = 3001;
    public static final int AST_OBJECT_LABEL = 3002;
    public static final int AST_ARRAY_LABEL = 3003;

    public static final int AST_PRIMARY = 3004;
    public static final int PRIMARY_LIST = 3005;
    public static final int OBJECT_LIST = 3006;

    public static final int QUERY_PROBLEM = 3007;
    public static final int INNER_LABEL = 3008;
    public static final int QUERY = 3009;
    public static final int MATCH = 3010;
    public static final int BOOL = 3011;
    public static final int TERM = 3012;
}
