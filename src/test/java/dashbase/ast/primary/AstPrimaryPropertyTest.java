package dashbase.ast.primary;

import com.google.gson.JsonObject;
import dashbase.ast.AstQueryProgram;
import dashbase.ast.literal.StringLiteral;
import dashbase.ast.property.AstProperty;
import org.junit.Assert;
import org.junit.Test;

import static dashbase.TestUtils.runGrammar;

public class AstPrimaryPropertyTest {

    /**
     * Ast Structure
     * problem - object - property-list - properties - primary-property => [ StringLiteral , Literal ]
     */
    @Test
    public void testPrimaryFunction() {
        JsonObject object = new JsonObject();
        object.addProperty("1", "lfkdsk");
        object.addProperty("2", 1);
        object.addProperty("3", true);
        AstQueryProgram program = runGrammar(object.toString());

        Assert.assertNotNull(program);
        Assert.assertEquals(program.object().propertyList().childCount(), 3);
        Assert.assertNotNull(program.object());

        Assert.assertEquals(program.object().property("1").type(), AstProperty.PropertyType.PRIMARY);
        Assert.assertEquals(program.object().property("2").type(), AstProperty.PropertyType.PRIMARY);
        Assert.assertEquals(program.object().property("3").type(), AstProperty.PropertyType.PRIMARY);

        AstPrimaryProperty property1 = (AstPrimaryProperty) program.object().property("1");
        Assert.assertNotNull(property1);

        if (property1.primaryType() == AstPrimaryProperty.PrimaryType.STRING) {
            StringLiteral literal = (StringLiteral) property1.literal();
            Assert.assertEquals(literal.value(), "lfkdsk");
        }
    }
}