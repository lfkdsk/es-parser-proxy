package dashbase.ast.object;

import com.google.gson.JsonObject;
import dashbase.ast.AstQueryProgram;
import dashbase.ast.literal.BoolLiteral;
import dashbase.ast.primary.AstPrimaryProperty;
import org.junit.Assert;
import org.junit.Test;

import static dashbase.TestUtils.runGrammar;

public class AstObjectPropertyTest {

    @Test
    public void testAstObjectProperty() {
        JsonObject problem = new JsonObject();

        JsonObject object = new JsonObject();
        object.addProperty("lfkdsk", true);
        problem.add("lfkdsk", object);

        AstQueryProgram node = runGrammar(problem.toString());

        Assert.assertNotNull(node);
        Assert.assertTrue(node.object().property("lfkdsk") instanceof AstObjectProperty);

        AstObjectProperty astObjectProperty = (AstObjectProperty) node.object().property("lfkdsk");
        Assert.assertNotNull(astObjectProperty);
        Assert.assertEquals(astObjectProperty.object().propertyList().childCount(), 1);

        AstPrimaryProperty property = (AstPrimaryProperty) astObjectProperty.object().property("lfkdsk");
        Assert.assertNotNull(property);
        if (property.primaryType() == AstPrimaryProperty.PrimaryType.BOOL) {
            BoolLiteral literal = (BoolLiteral) property.literal();
            Assert.assertTrue(literal.value());
        }
    }

}