package dashbase.ast.array;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dashbase.ast.AstQueryProgram;
import dashbase.ast.literal.NumberLiteral;
import dashbase.ast.object.AstObject;
import dashbase.ast.primary.AstPrimaryProperty;
import dashbase.ast.property.AstProperty;
import dashbase.ast.value.AstValueList;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static dashbase.TestUtils.runGrammar;

public class AstArrayPropertyTest {

    @Test
    public void testAstArrayPropertyTest() {
        JsonObject program = new JsonObject();
        JsonArray array = new JsonArray();
        array.add(1);
        array.add(2);
        array.add(3);

        JsonArray objectArray = new JsonArray();
        objectArray.add(new JsonObject());

        JsonObject o1 = new JsonObject();
        o1.addProperty("lfkdsk", true);

        objectArray.add(o1);

        program.add("lfkdsk", array);
        program.add("lfk", objectArray);

        AstQueryProgram nodes = runGrammar(program.toString());

        Assert.assertNotNull(nodes);
        Assert.assertEquals(nodes.object().property("lfkdsk").type(), AstProperty.PropertyType.ARRAY);
        Assert.assertEquals(nodes.object().propertyList().childCount(), 2);

        AstArrayProperty astArrayProperty = (AstArrayProperty) nodes.object().property("lfkdsk");

        Assert.assertNotNull(nodes);
        Assert.assertTrue(astArrayProperty.child(1) instanceof AstValueList);
        Assert.assertEquals(astArrayProperty.arrayType(), AstArrayProperty.ArrayType.NUMBER);

        List<NumberLiteral> literals = astArrayProperty.list().toLiterals();

        Assert.assertEquals(literals.size(), 3);
        Assert.assertEquals(literals.get(2).value(), 3);

        AstArrayProperty astObjectArray = (AstArrayProperty) nodes.object().property("lfk");

        List<AstObject> objects = astObjectArray.list().toObjects();
        Assert.assertEquals(objects.size(), 2);
        Assert.assertEquals(objects.get(1).property("lfkdsk").type(), AstProperty.PropertyType.PRIMARY);

        AstPrimaryProperty property = (AstPrimaryProperty) objects.get(1).property("lfkdsk");

        if (property.primaryType() == AstPrimaryProperty.PrimaryType.BOOL) {
            Assert.assertEquals(property.literal().value(), true);
        }
    }
}