package dashbase.rules;

import bnfgenast.bnf.BnfCom;
import bnfgenast.lexer.Lexer;
import dashbase.ast.AstQueryProgram;
import dashbase.ast.array.AstArrayProperty;
import dashbase.ast.literal.BoolLiteral;
import dashbase.ast.literal.NumberLiteral;
import dashbase.ast.literal.StringLiteral;
import dashbase.ast.object.AstObject;
import dashbase.ast.object.AstObjectProperty;
import dashbase.ast.primary.AstPrimaryProperty;
import dashbase.ast.property.AstPropertyList;
import dashbase.ast.value.AstValueList;
import lombok.Getter;

import static bnfgenast.bnf.BnfCom.rule;
import static bnfgenast.bnf.BnfCom.wrapper;

public class QueryGrammar {

    private BnfCom valueList0 = rule();
    private BnfCom propertyList0 = rule();

    ///////////////////////////////////////////////////////////////////////////
    // literal
    ///////////////////////////////////////////////////////////////////////////

    private BnfCom number = rule().number(NumberLiteral.class);

//    private BnfCom id = rule().identifier(IDLiteral.class, reservedToken);

    private BnfCom string = rule().string(StringLiteral.class);

    private BnfCom bool = rule().bool(BoolLiteral.class);

    /**
     * PRIMARY TYPE:
     * primary := number | id | string | boolean
     * primaryProperty := string : primary
     * eq: "lfkdsk": "lfkdsk"
     */

    ///////////////////////////////////////////////////////////////////////////
    // primary literal := number | id | string | boolean
    ///////////////////////////////////////////////////////////////////////////

    @Getter
    private BnfCom primary = wrapper().or( // remove AstPrimary.class
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
    // object literal := {
    //      literal, literal
    // }
    ///////////////////////////////////////////////////////////////////////////

    @Getter
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
    // arrayProperty literal := [
    //      literal list
    // ]
    ///////////////////////////////////////////////////////////////////////////

    @Getter
    private BnfCom array = wrapper().sep("[")
                                    .maybe(valueList0)
                                    .sep("]");

    ///////////////////////////////////////////////////////////////////////////
    // array property := string : array
    ///////////////////////////////////////////////////////////////////////////

    @Getter
    private BnfCom arrayProperty = rule(AstArrayProperty.class).ast(string).sep(":").ast(array);


    ///////////////////////////////////////////////////////////////////////////
    // literal := literal | object | property
    ///////////////////////////////////////////////////////////////////////////

    @Getter
    private BnfCom value = wrapper().or(
            primary,
            object,
            array
    );

    ///////////////////////////////////////////////////////////////////////////
    // property : primary | array | object
    ///////////////////////////////////////////////////////////////////////////

    @Getter
    private BnfCom property = wrapper().prefix( // remove AstProperty.class
            primaryProperty,
            arrayProperty,
            objectProperty
    );

    ///////////////////////////////////////////////////////////////////////////
    // property literal list := property [, property] *
    // use in { }
    ///////////////////////////////////////////////////////////////////////////

    private BnfCom valueList = valueList0.reset(AstValueList.class).ast(value).repeat(
            rule().sep(",").repeat(value)
    );

    ///////////////////////////////////////////////////////////////////////////
    // literal list := literal [, literal] *
    // use in [  ]
    ///////////////////////////////////////////////////////////////////////////

    @Getter
    private BnfCom propertyList = propertyList0.reset(AstPropertyList.class).ast(property).repeat(
            rule().sep(",").repeat(property)
    );

    ///////////////////////////////////////////////////////////////////////////
    // object = {
    //      valueList
    // } EOL (end of line)
    ///////////////////////////////////////////////////////////////////////////

    private BnfCom program = rule(AstQueryProgram.class).ast(object);

    public AstQueryProgram parse(Lexer lexer) {
        return (AstQueryProgram) program.parse(lexer);
    }
}
