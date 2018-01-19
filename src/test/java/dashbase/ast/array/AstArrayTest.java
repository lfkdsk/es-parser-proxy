package dashbase.ast.array;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dashbase.ast.AstQueryProgram;
import dashbase.ast.property.AstProperty;
import org.junit.Assert;
import org.junit.Test;

import static dashbase.TestUtils.runGrammar;

public class AstArrayTest {

    @Test
    public void testAstArray() {
        JsonObject program = new JsonObject();
        JsonArray array = new JsonArray();
        array.add(1);
        array.add(2);
        array.add(3);

        program.add("lfkdsk", array);

        AstQueryProgram nodes = runGrammar(program.toString());

        Assert.assertNotNull(nodes);
        Assert.assertEquals(nodes.object().property("lfkdsk").type(), AstProperty.PropertyType.ARRAY);

        AstArrayProperty astArrayProperty = (AstArrayProperty) nodes.object().property("lfkdsk");
        Assert.assertNotNull(nodes);


    }
}