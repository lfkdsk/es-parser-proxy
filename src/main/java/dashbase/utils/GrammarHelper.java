package dashbase.utils;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.ast.property.AstProperty;

public class GrammarHelper {


    public static AstNode transformAst(AstNode root) {
        for (int i = 0; i < root.childCount(); ++i) {
            AstNode child = root.child(i);
            if (child instanceof AstProperty) {
                // remove AstProperty.java Level
                root.resetChild(i, ((AstProperty) child).property());
            }
            transformAst(child);
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
