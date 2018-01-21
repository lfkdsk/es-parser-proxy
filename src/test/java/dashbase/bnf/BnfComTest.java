package dashbase.bnf;

import dashbase.ast.literal.BoolLiteral;
import dashbase.ast.literal.NumberLiteral;
import dashbase.ast.literal.StringLiteral;
import dashbase.ast.primary.AstPrimary;
import org.junit.Assert;
import org.junit.Test;

import static dashbase.bnf.BnfCom.rule;

public class BnfComTest {

    @Test
    public void testSimpleBnfCom() {
        BnfCom number = rule().number(NumberLiteral.class);

        BnfCom string = rule().string(StringLiteral.class);

        BnfCom bool = rule().bool(BoolLiteral.class);

        BnfCom primary = rule(AstPrimary.class).or(
                number,
                string,
                bool
        );

        BnfCom primary2 = rule(AstPrimary.class).or(
                number,
                string,
                bool
        );

        Assert.assertEquals(primary, primary2);
    }
}