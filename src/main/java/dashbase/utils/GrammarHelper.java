package dashbase.utils;

import com.lfkdsk.justel.ast.base.AstNode;
import com.lfkdsk.justel.ast.function.Operator;
import com.lfkdsk.justel.ast.tree.AstBinaryExpr;
import com.lfkdsk.justel.parser.BnfCom;
import dashbase.ast.AstInnerLabelExpr;
import dashbase.ast.QueryAstList;
import dashbase.request.query.Query;

public class GrammarHelper {

    private static AstNode resetAstExpr(AstInnerLabelExpr expr, BnfCom.Operators operators) {
        String operator = expr.child(0).toString();
        BnfCom.Precedence precedence = operators.get(operator);
        if (precedence == null) {
            return expr;
        }

        BnfCom.Factory factory = precedence.factory;

        return factory.make(expr.getChildren());
    }


    public static AstNode transformAst(AstNode root, BnfCom.Operators operators) {
        for (int i = 0; i < root.childCount(); ++i) {
            AstNode child = root.child(i);
            if (child instanceof AstInnerLabelExpr) {
                child = resetAstExpr((AstInnerLabelExpr) child, operators);
                root.resetChild(i, child);
            }

            transformAst(child, operators);
        }

        return root;
    }

    public static <U extends QueryAstList, P extends Class<? extends QueryAstList>>
    boolean under(U under, P upperClass) {
        AstNode parent = under.getParentNode();
        while ((parent = parent.getParentNode()) != null) {
            if (upperClass.equals(parent.getClass())) {
                return true;
            }
        }

        return false;
    }
}
