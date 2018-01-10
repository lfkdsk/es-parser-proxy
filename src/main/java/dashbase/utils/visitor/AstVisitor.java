package dashbase.utils.visitor;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import dashbase.ast.*;
import dashbase.ast.inner.MatchLabel;
import dashbase.ast.inner.QueryLabel;

public interface AstVisitor<T> {
    T visitAstArrayLabel(AstArrayLabel visitor);

    T visitAstInnerLabelExpr(AstInnerLabelExpr visitor);

    T visitAstLabelExpr(AstLabelExpr visitor);

    T visitAstObjectLabel(AstObjectLabel visitor);

    T visitAstPrimary(AstPrimary visitor);

    T visitAstPrimaryList(AstPrimaryList visitor);

    T visitAstQueryProgram(AstQueryProgram visitor);

    T visitAstValueLabel(AstValueLabel visitor);

    T visitAstLabelList(AstLabelList visitor);

    ///////////////////////////////////////////////////////////////////////////
    // inner label
    ///////////////////////////////////////////////////////////////////////////

    T visitQueryLabel(QueryLabel visitor);

    T visitMatchLabel(MatchLabel visitor);
}
