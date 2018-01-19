package dashbase.ast;

import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import static dashbase.TestUtils.runGrammar;

public class AstPrimaryTest {
    @Test
    public void testPrimaryFunction() {
        JsonObject object = new JsonObject();
        object.addProperty("1", "lfkdsk");
        object.addProperty("2", 1);
        object.addProperty("3", true);
        AstQueryProgram program = runGrammar(object.toString());

        Assert.assertNotNull(program);
        Assert.assertEquals(program.program().childCount(), 3);
    }
}