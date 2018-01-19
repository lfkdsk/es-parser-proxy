package dashbase.ast.object;

import com.google.gson.JsonObject;
import dashbase.ast.AstQueryProgram;
import org.junit.Assert;
import org.junit.Test;

import static dashbase.TestUtils.runGrammar;

public class AstObjectTest {

    @Test
    public void testAstObject() {
        JsonObject problem = new JsonObject();
        problem.add("lfkdsk", new JsonObject());

        AstQueryProgram node = runGrammar(problem.toString());

        Assert.assertNotNull(node);
        Assert.assertTrue(node.object().property("lfkdsk") instanceof AstObjectProperty);
    }
}