package dashbase.bnf;

import bnfgenast.bnf.BnfCom;
import dashbase.ast.literal.BoolLiteral;
import dashbase.ast.literal.NumberLiteral;
import dashbase.ast.literal.StringLiteral;
import org.junit.Assert;
import org.junit.Test;

import static bnfgenast.bnf.BnfCom.rule;
import static bnfgenast.bnf.BnfCom.wrapper;


public class BnfComTest {

    @Test
    public void testSimpleBnfCom() {
        BnfCom number = rule().number(NumberLiteral.class);

        BnfCom string = rule().string(StringLiteral.class);

        BnfCom bool = rule().bool(BoolLiteral.class);

        BnfCom primary = wrapper().or(
                number,
                string,
                bool
        );

        BnfCom primary2 = wrapper().or(
                number,
                string,
                bool
        );

        Assert.assertEquals(primary, primary2);
    }
}