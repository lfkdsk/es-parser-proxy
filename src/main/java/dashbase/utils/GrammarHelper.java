package dashbase.utils;

import dashbase.ast.array.AstArray;
import dashbase.ast.base.AstNode;
import dashbase.ast.primary.AstPrimary;
import dashbase.ast.property.AstProperty;
import dashbase.ast.value.AstValue;
import dashbase.bnf.BnfCom;
import dashbase.exception.ParseException;
import dashbase.rules.QueryGrammar;

public class GrammarHelper {

    /**
     * Static Grammar
     */
    private static final QueryGrammar staticGrammar = new QueryGrammar();

    /**
     * AST Transformer
     * 精简 AST 结构
     *
     * @param root root-node
     * @return root node
     */
    public static AstNode transformAst(AstNode root) {
        for (int i = 0; i < root.childCount(); ++i) {
            AstNode child = root.child(i);

            if (child instanceof AstProperty) {
                // remove AstProperty.java Level
                root.resetChild(i, ((AstProperty) child).property());
            } else if (child instanceof AstPrimary) {
                // remove AstPrimary.java Level
                root.resetChild(i, ((AstPrimary) child).literal());
            } else if (child instanceof AstArray) {
                // remove AstArray.java Level
                root.resetChild(i, ((AstArray) child).list());
            } else if (child instanceof AstValue) {
                // remove AstValue.java Level
                root.resetChild(i, ((AstValue) child).value());
            }

            transformAst(child);
        }

        return root;
    }

    public static BnfCom addSpecToObj(BnfCom object) {
        BnfCom astObject = staticGrammar.getObject();
        if (!astObject.equals(object)) {
            throw new ParseException(" Cannot convert AstObject to Special AstObject. ");
        }

        return object.of(astObject);
    }
}
