package dashbase.visitor;

import dashbase.ast.*;
import dashbase.utils.visitor.AstVisitor;

public class EsVisitor implements AstVisitor<String> {
    @Override
    public String visitAstArrayLabel(AstArrayLabel visitor) {
        return null;
    }

    @Override
    public String visitAstInnerLabelExpr(AstInnerLabelExpr visitor) {
        return null;
    }

    @Override
    public String visitAstLabelExpr(AstLabelExpr visitor) {
        return null;
    }

    @Override
    public String visitAstObjectLabel(AstObjectLabel visitor) {
        return null;
    }

    @Override
    public String visitAstPrimary(AstPrimary visitor) {
        return null;
    }

    @Override
    public String visitAstPrimaryList(AstPrimaryList visitor) {
        return null;
    }

    @Override
    public String visitAstQueryProgram(AstQueryProgram visitor) {
        return null;
    }

    @Override
    public String visitAstValueLabel(AstValueLabel visitor) {
        return null;
    }
}
