package dashbase.visitor;

import dashbase.ast.*;

public interface AstVisitor<T> {
    T visitAstArrayLabel(AstArrayLabel visitor);

    T visitAstInnerLabelExpr(AstInnerLabelExpr visitor);

    T visitAstLabelExpr(AstLabelExpr visitor);

    T visitAstObjectLabel(AstObjectLabel visitor);

    T visitAstPrimary(AstPrimary visitor);

    T visitAstPrimaryList(AstPrimaryList visitor);

    T visitAstQueryProgram(AstQueryProgram visitor);

    T visitAstValueLabel(AstValueLabel visitor);
}
