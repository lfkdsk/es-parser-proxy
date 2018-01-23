package dashbase.meta;

import com.google.gson.JsonObject;
import dashbase.ast.AstQueryProgram;
import dashbase.ast.object.AstObject;
import dashbase.ast.object.AstObjectProperty;
import dashbase.ast.property.AstPropertyList;
import dashbase.bnf.BnfCom;
import dashbase.env.Context;
import dashbase.lexer.JustLexer;
import dashbase.utils.GrammarHelper;
import org.junit.Assert;
import org.junit.Test;

public class BnfGeneratorTest {

    public static class FinderDemo {

        @Bind(name = "wrapper", mode = GrammarMode.WRAPPER)
        public void bind(AstQueryProgram property, Context context) {

        }

        @Bind(name = "query", mode = GrammarMode.OBJECT, prefix = {"wrapper"})
        public void test(AstObjectProperty property, Context context) {

        }
    }

    @Test
    public void testBnfDependencySorted() {
        BnfGenerator generator = new BnfGenerator();
        generator.register(FinderDemo.class);

        Assert.assertNotNull(generator);
        Assert.assertEquals(generator.getTokensBindMethods().size(), 2);


        generator.sortMapToTree();
        Assert.assertEquals(generator.getTokensBindMethods()
                                     .get("wrapper[WRAPPER]")
                                     .getDependencies()
                                     .get("query[OBJECT]")
                                     .getBindMethod()
                                     .getMode(), GrammarMode.OBJECT);
    }

    public static class FinderDemo2 {

        @Bind(name = "wrapper", mode = GrammarMode.WRAPPER)
        public void bind(AstQueryProgram property, Context context) {

        }

        @Bind(name = "query", mode = GrammarMode.OBJECT, prefix = {"wrapper"})
        public void test(AstObjectProperty property, Context context) {

        }

        @Bind(name = "filter", mode = GrammarMode.OBJECT, prefix = {"wrapper"})
        public void test1(AstObjectProperty property, Context context) {

        }
    }


    @Test
    public void testGenerate() {
        BnfGenerator generator = new BnfGenerator();
        generator.register(FinderDemo2.class);

        Assert.assertNotNull(generator);
        Assert.assertEquals(generator.getTokensBindMethods().size(), 3);


        JsonObject object = new JsonObject();
        object.add("filter", new JsonObject());
        object.add("query", new JsonObject());

        JustLexer lexer = new JustLexer(object.toString());
        lexer.reserved(("query"));
        lexer.reserved(("filter"));

        BnfCom bnfCom = generator.generate();
        AstQueryProgram program = (AstQueryProgram) GrammarHelper.transformAst(bnfCom.parse(lexer));

        Assert.assertNotNull(program);

        AstObject object1 = program.object();
        AstPropertyList list = object1.propertyList();
        Assert.assertEquals(2, list.childCount());
    }

}