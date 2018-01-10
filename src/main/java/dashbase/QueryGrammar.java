package dashbase;

import com.lfkdsk.justel.literal.BoolLiteral;
import com.lfkdsk.justel.literal.IDLiteral;
import com.lfkdsk.justel.literal.NumberLiteral;
import com.lfkdsk.justel.literal.StringLiteral;
import dashbase.ast.inner.MatchLabel;
import dashbase.ast.inner.QueryLabel;
import dashbase.bnf.BnfCom;
import com.lfkdsk.justel.token.Token;
import dashbase.ast.*;
import dashbase.request.query.Query;
import dashbase.token.Tokens;
import dashbase.utils.GrammarHelper;
import lombok.Getter;

import java.util.Queue;

import static dashbase.bnf.BnfCom.rule;
import static com.lfkdsk.justel.token.ReservedToken.reservedToken;
import static com.lfkdsk.justel.token.Token.EOL;

public class QueryGrammar {

    private BnfCom label0 = rule();


    ///////////////////////////////////////////////////////////////////////////
    // value
    ///////////////////////////////////////////////////////////////////////////

    private BnfCom number = rule().number(NumberLiteral.class);

    private BnfCom id = rule().identifier(IDLiteral.class, reservedToken);

    private BnfCom string = rule().string(StringLiteral.class);

    private BnfCom bool = rule().bool(BoolLiteral.class);


    ///////////////////////////////////////////////////////////////////////////
    // primary value := number | id | string | boolean
    ///////////////////////////////////////////////////////////////////////////

    @Getter
    private BnfCom primary = rule(AstPrimary.class).or(
            number,
            string,
            bool
    );

    ///////////////////////////////////////////////////////////////////////////
    // value label := primary
    ///////////////////////////////////////////////////////////////////////////

    private BnfCom valueLabel = rule(AstValueLabel.class).ast(primary);

    ///////////////////////////////////////////////////////////////////////////
    // inner label := label name : label
    ///////////////////////////////////////////////////////////////////////////

    @Getter
    private BnfCom innerLabel = rule(AstInnerLabelExpr.class).ast(string).sep(":").ast(label0);

    ///////////////////////////////////////////////////////////////////////////
    // inner label list := innerLabel [, innerLabel] *
    ///////////////////////////////////////////////////////////////////////////

    private BnfCom innerLabelList = rule(AstLabelList.class).ast(innerLabel).repeat(
            rule().sep(",").repeat(innerLabel)
    );

    ///////////////////////////////////////////////////////////////////////////
    // label list := label [, label] *
    ///////////////////////////////////////////////////////////////////////////

    private BnfCom labelList = rule(AstPrimaryList.class).ast(label0).repeat(
            rule().sep(",").repeat(label0)
    );

    ///////////////////////////////////////////////////////////////////////////
    // object label := {
    //      label, label
    // }
    ///////////////////////////////////////////////////////////////////////////

    @Getter
    private BnfCom objectLabel = rule(AstObjectLabel.class).sep("{")
                                                           .maybe(innerLabelList)
                                                           .sep("}");

    ///////////////////////////////////////////////////////////////////////////
    // array label := [
    //      label list
    // ]
    ///////////////////////////////////////////////////////////////////////////

    @Getter
    private BnfCom arrayLabel = rule(AstArrayLabel.class).sep("[")
                                                         .maybe(labelList)
                                                         .sep("]");

    ///////////////////////////////////////////////////////////////////////////
    // label := value | object | array
    ///////////////////////////////////////////////////////////////////////////

    @Getter
    private BnfCom label = label0.reset(AstLabelExpr.class).or(
            valueLabel,
            objectLabel,
            arrayLabel
    );

    private BnfCom matchAll = rule().wrap("match_all").sep(":").ast(objectLabel);

    private BnfCom matchNone = rule().wrap("match_none").sep(":").sep("{").sep("}");

    private BnfCom match = rule(MatchLabel.class).wrap("match").sep(":").sep("{").ast(innerLabel).sep("}");

    private BnfCom matchQueryPhrase = rule().wrap("match_phrase").sep(":").sep("{").ast(innerLabel).sep("}");

    private BnfCom matchPhrasePrefix = rule().wrap("match_phrase_prefix").sep(":").sep("{").ast(innerLabel).sep("}");

    private BnfCom multiMatch = rule().wrap("multi_match").sep(":").sep("{").ast(innerLabel).sep("}");

    private BnfCom boolQuery = rule().wrap("bool").sep(":").sep("{").ast(objectLabel).sep("}");

    private BnfCom commonQuery = rule().wrap("common").sep(":").sep("{").ast(objectLabel).sep("}");

    private BnfCom queryString = rule().wrap("query_string").sep(":").sep("{").ast(innerLabel).sep("}");

    @Getter
    private BnfCom query = rule(QueryLabel.class).wrap("query").sep(":").sep("{").or(
            match,
            matchAll,
            matchNone,
            matchQueryPhrase,
            matchPhrasePrefix,
            multiMatch,
            boolQuery,
            commonQuery,
            queryString
    ).sep("}");

    private BnfCom innerProgram = rule().maybe(query);


    ///////////////////////////////////////////////////////////////////////////
    // program = {
    //      labelList
    // } EOL (end of line)
    ///////////////////////////////////////////////////////////////////////////

    @Getter
    private BnfCom program = rule(AstQueryProgram.class).sep("{").maybe(innerProgram).sep("}").sep(EOL);

    public AstQueryProgram getAst(Queue<Token> tokens) {
        return (AstQueryProgram) GrammarHelper.transformAst(getProgram().parse(tokens), Tokens.labels);
    }
}
