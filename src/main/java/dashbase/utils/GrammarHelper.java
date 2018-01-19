package dashbase.utils;

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
}
