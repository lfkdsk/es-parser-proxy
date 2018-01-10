package dashbase.visitor;

import com.lfkdsk.justel.ast.base.AstNode;
import dashbase.ast.*;
import dashbase.ast.inner.MatchLabel;
import dashbase.ast.inner.QueryLabel;
import dashbase.request.query.Query;
import dashbase.request.Request;
import dashbase.request.query.StringQuery;
import dashbase.utils.visitor.AstVisitor;

import java.util.Iterator;

import static dashbase.utils.GrammarHelper.under;

public class EsVisitor implements AstVisitor<Request> {

    private Request request = new Request();

    @Override
    public Request visitAstArrayLabel(AstArrayLabel visitor) {
        throw new UnsupportedOperationException("cannot find method : " + "visit" + getClass().getSimpleName());
    }

    @Override
    public Request visitAstInnerLabelExpr(AstInnerLabelExpr visitor) {
        if (under(visitor, MatchLabel.class)) {
            StringQuery query = (StringQuery) request.getQuery();
            query.setQueryStr(String.format("%s:%s", visitor.key(), visitor.value()));
        }

        return request;
    }

    @Override
    public Request visitAstLabelExpr(AstLabelExpr visitor) {
        ((QueryAstList) visitor.child(0)).accept(this);
        return request;
    }

    @Override
    public Request visitAstObjectLabel(AstObjectLabel visitor) {
        for (AstNode node : visitor.labels()) {
            ((QueryAstList) node).accept(this);
        }
        return request;
    }

    @Override
    public Request visitAstPrimary(AstPrimary visitor) {
        throw new UnsupportedOperationException("cannot find method : " + "visit" + getClass().getSimpleName());
    }

    @Override
    public Request visitAstPrimaryList(AstPrimaryList visitor) {
        throw new UnsupportedOperationException("cannot find method : " + "visit" + getClass().getSimpleName());
    }

    @Override
    public Request visitAstQueryProgram(AstQueryProgram visitor) {
        visitor.program().accept(this);
        return request;
    }

    @Override
    public Request visitAstValueLabel(AstValueLabel visitor) {
        throw new UnsupportedOperationException("cannot find method : " + "visit" + getClass().getSimpleName());
    }

    @Override
    public Request visitAstLabelList(AstLabelList visitor) {
        for (AstNode node : visitor.labels()) {
            ((QueryAstList) node).accept(this);
        }
        return request;
    }

    @Override
    public Request visitQueryLabel(QueryLabel visitor) {
        visitor.query().accept(this);
        return request;
    }


    @Override
    public Request visitMatchLabel(MatchLabel visitor) {
        if (under(visitor, QueryLabel.class)) {
            StringQuery singleQuery = new StringQuery();
            singleQuery.setQueryType("string");
            singleQuery.setQueryStr(visitor.text());
            request.setQuery(singleQuery);
        }

        return request;
    }
}
