package dashbase.ast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Test;

import static dashbase.TestUtils.runGrammar;

public class AstQueryProgramTest {

    @Test
    public void testQuery() {
        JsonObject object = new JsonObject();
        JsonObject query = new JsonObject();
        query.add("match_all", new JsonObject());
        object.add("query", query);

        AstQueryProgram program = runGrammar(object.toString());
    }

    @Test
    public void testQuery2() {
        JsonObject problem = new JsonObject();
        problem.addProperty("0", true);
        problem.addProperty("1", 1000);
        problem.addProperty("2", 1000.01e10);
        problem.addProperty("3", "this is a string");
        problem.add("4", new JsonObject());
        problem.add("5", new JsonArray());

        JsonArray array6 = new JsonArray();
        array6.add(100);
        array6.add(100);
        array6.add(100);
        array6.add(100);

        problem.add("6", array6);


        JsonObject object7 = new JsonObject();
        object7.add("match_all", new JsonObject());
        object7.addProperty("lfkdsk", true);
        problem.add("7", object7);

        AstQueryProgram program = runGrammar(problem.toString());
    }
}