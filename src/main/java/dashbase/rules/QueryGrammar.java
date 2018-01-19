package dashbase.rules;

import dashbase.ast.AstQueryProgram;
import dashbase.ast.array.AstArray;
import dashbase.ast.array.AstArrayProperty;
import dashbase.ast.literal.BoolLiteral;
import dashbase.ast.literal.IDLiteral;
import dashbase.ast.literal.NumberLiteral;
import dashbase.ast.literal.StringLiteral;
import dashbase.ast.object.AstObject;
import dashbase.ast.object.AstObjectProperty;
import dashbase.ast.primary.AstPrimary;
import dashbase.ast.primary.AstPrimaryProperty;
import dashbase.ast.property.AstProperty;
import dashbase.ast.property.AstPropertyList;
import dashbase.ast.value.AstValue;
import dashbase.ast.value.AstValueList;
import dashbase.bnf.BnfCom;
import dashbase.lexer.JustLexer;
import lombok.Getter;

import static dashbase.bnf.BnfCom.rule;
import static dashbase.token.ReservedToken.reservedToken;

public class QueryGrammar {

    private BnfCom valueList0 = rule();
    private BnfCom propertyList0 = rule();

    ///////////////////////////////////////////////////////////////////////////
    // value
    ///////////////////////////////////////////////////////////////////////////

    private BnfCom number = rule().number(NumberLiteral.class);

    private BnfCom id = rule().identifier(IDLiteral.class, reservedToken);

    private BnfCom string = rule().string(StringLiteral.class);

    private BnfCom bool = rule().bool(BoolLiteral.class);

    /**
     * PRIMARY TYPE:
     * primary := number | id | string | boolean
     * primaryProperty := string : primary
     * eq: "lfkdsk": "lfkdsk"
     */

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
    // primary property := property name : primary
    ///////////////////////////////////////////////////////////////////////////

    private BnfCom primaryProperty = rule(AstPrimaryProperty.class).ast(string).sep(":").ast(primary);

    /**
     * OBJECT TYPE:
     * object := { inner-property }
     * objectProperty := string : object
     */

    ///////////////////////////////////////////////////////////////////////////
    // object value := {
    //      value, value
    // }
    ///////////////////////////////////////////////////////////////////////////

    private BnfCom object = rule(AstObject.class).sep("{")
                                                 .maybe(propertyList0)
                                                 .sep("}");

    ///////////////////////////////////////////////////////////////////////////
    // object property := property name : object
    ///////////////////////////////////////////////////////////////////////////

    private BnfCom objectProperty = rule(AstObjectProperty.class).ast(string).sep(":").ast(object);

    /**
     * ARRAY TYPE:
     * array := [ valueList ]
     * arrayProperty := string : array
     */

    ///////////////////////////////////////////////////////////////////////////
    // arrayProperty value := [
    //      value list
    // ]
    ///////////////////////////////////////////////////////////////////////////

    private BnfCom array = rule(AstArray.class).sep("[")
                                               .maybe(valueList0)
                                               .sep("]");

    ///////////////////////////////////////////////////////////////////////////
    // array property := string : array
    ///////////////////////////////////////////////////////////////////////////
    
    private BnfCom arrayProperty = rule(AstArrayProperty.class).ast(string).sep(":").ast(array);


    ///////////////////////////////////////////////////////////////////////////
    // value := value | object | property
    ///////////////////////////////////////////////////////////////////////////

    private BnfCom value = rule(AstValue.class).or(
            primary,
            object,
            array
    );

    ///////////////////////////////////////////////////////////////////////////
    // property : primary | array | object
    ///////////////////////////////////////////////////////////////////////////

    private BnfCom property = rule(AstProperty.class).prefix(
            primaryProperty,
            arrayProperty,
            objectProperty
    );

    ///////////////////////////////////////////////////////////////////////////
    // property value list := property [, property] *
    // use in { }
    ///////////////////////////////////////////////////////////////////////////

    private BnfCom valueList = valueList0.reset(AstValueList.class).ast(value).repeat(
            rule().sep(",").repeat(value)
    );

    ///////////////////////////////////////////////////////////////////////////
    // value list := value [, value] *
    // use in [  ]
    ///////////////////////////////////////////////////////////////////////////

    private BnfCom propertyList = propertyList0.reset(AstPropertyList.class).ast(property).repeat(
            rule().sep(",").repeat(property)
    );

//    private BnfCom matchAll = rule().wrap("match_all").sep(":").ast(object);
//
//    private BnfCom matchNone = rule().wrap("match_none").sep(":").sep("{").sep("}");
//
//    private BnfCom match = rule(MatchLabel.class).wrap("match").sep(":").sep("{").ast(primaryProperty).sep("}");
//
//    private BnfCom matchQueryPhrase = rule().wrap("match_phrase").sep(":").sep("{").ast(primaryProperty).sep("}");
//
//    private BnfCom matchPhrasePrefix = rule().wrap("match_phrase_prefix").sep(":").sep("{").ast(primaryProperty).sep("}");
//
//    private BnfCom multiMatch = rule().wrap("multi_match").sep(":").sep("{").ast(primaryProperty).sep("}");
//
//    private BnfCom boolQuery = rule().wrap("bool").sep(":").sep("{").ast(object).sep("}");
//
//    private BnfCom commonQuery = rule().wrap("common").sep(":").sep("{").ast(object).sep("}");
//
//    private BnfCom queryString = rule().wrap("query_string").sep(":").sep("{").ast(primaryProperty).sep("}");
//
//    @Getter
//    private BnfCom query = rule(QueryLabel.class).wrap("query").sep(":").sep("{").or(
//            match,
//            matchAll,
//            matchNone,
//            matchQueryPhrase,
//            matchPhrasePrefix,
//            multiMatch,
//            boolQuery,
//            commonQuery,
//            queryString
//    ).sep("}");
//
//    private BnfCom innerProgram = rule().maybe(query);


    ///////////////////////////////////////////////////////////////////////////
    // program = {
    //      valueList
    // } EOL (end of line)
    ///////////////////////////////////////////////////////////////////////////

    private BnfCom program = rule(AstQueryProgram.class).ast(object);

    public AstQueryProgram parse(JustLexer lexer) {
        return (AstQueryProgram) program.parse(lexer);
    }
}
