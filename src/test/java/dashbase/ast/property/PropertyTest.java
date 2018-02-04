package dashbase.ast.property;

import bnfgenast.bnf.BnfCom;
import bnfgenast.lexer.Lexer;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dashbase.ast.AstQueryProgram;
import dashbase.ast.object.AstObjectProperty;
import dashbase.env.Context;
import dashbase.lexer.JustLexer;
import dashbase.meta.Bind;
import dashbase.meta.BnfGenerator;
import logger.Logger;
import org.junit.Test;

import static dashbase.meta.GrammarMode.OBJECT;
import static dashbase.meta.GrammarMode.WRAPPER;

public class PropertyTest {

    @Bind(name = "wrapper", mode = WRAPPER)
    public void wrapper(AstQueryProgram property, Context context) {
        Logger.v(" wrapper attach ");
    }

    @Bind(name = "query", mode = OBJECT, prefix = "wrapper")
    public void query(AstObjectProperty property, Context context) {
        Logger.v(" query attach ");
    }

    @Bind(name = "match_all", mode = OBJECT, prefix = "query")
    public void matchAll(AstObjectProperty property, Context context) {
        Logger.v(" match_all attach ");
    }

    @Bind(name = "match", mode = OBJECT, prefix = "query")
    public void match(AstObjectProperty property, Context context) {
        System.out.println(" match ");
    }

    @Bind(name = "match_phrase", mode = OBJECT, prefix = "query")
    public void matchPhrase(AstObjectProperty property, Context context) {

    }

    @Bind(name = "common", mode = OBJECT, prefix = "query")
    public void common(AstObjectProperty property, Context context) {

    }

    @Bind(name = "simple_query_string", mode = OBJECT, prefix = "query")
    public void simpleQueryString(AstObjectProperty property, Context context) {

    }

    @Bind(name = "term", mode = OBJECT, prefix = "query")
    public void term(AstObjectProperty property, Context context) {

    }

    @Bind(name = "range", mode = OBJECT, prefix = "query")
    public void range(AstObjectProperty property, Context context) {

    }

    @Bind(name = "exists", mode = OBJECT, prefix = "query")
    public void exists(AstObjectProperty property, Context context) {

    }

    @Bind(name = "wildcard", mode = OBJECT, prefix = "query")
    public void wildcard(AstObjectProperty property, Context context) {

    }

    @Bind(name = "regexp", mode = OBJECT, prefix = "query")
    public void regexp(AstObjectProperty property, Context context) {

    }

    @Bind(name = "ids", mode = OBJECT, prefix = "query")
    public void ids(AstObjectProperty property, Context context) {

    }

    @Bind(name = "constant_score", mode = OBJECT, prefix = "query")
    public void constantScore(AstObjectProperty property, Context context) {

    }

    @Bind(name = "bool", mode = OBJECT, prefix = "query")
    public void bool(AstObjectProperty property, Context context) {

    }

    @Bind(name = "dis_max", mode = OBJECT, prefix = "query")
    public void disMax(AstObjectProperty property, Context context) {

    }

    @Bind(name = "boosting", mode = OBJECT, prefix = "query")
    public void boosting(AstObjectProperty property, Context context) {

    }

    public static class ProxyDemo {
        @Bind(name = "wrapper", mode = WRAPPER)
        public void wrapper(AstQueryProgram property, Context context) {
            System.out.println(" Call Wrapper ");
        }

        @Bind(name = "query", mode = OBJECT, prefix = "wrapper")
        public void query(AstObjectProperty property, Context context) {
            System.out.println(" Call Query ");
        }

        @Bind(name = "term", mode = OBJECT, prefix = "query")
        public void term(AstObjectProperty property, Context context) {
            System.out.println(" Call Term ");
        }

        @Bind(name = "bool", mode = OBJECT, prefix = "query")
        public void bool(AstObjectProperty property, Context context) {
            System.out.println(" Call Bool ");
        }

        @Bind(name = "must", mode = OBJECT, prefix = "bool", insert = "query")
        public void must(AstObjectProperty property, Context context) {
            System.out.println(" Call Must ");
            System.out.println(property.object()
                                       .property("term")
                                       .valueNode()
                                       .toString());
        }

        @Bind(name = "filter", mode = OBJECT, prefix = "bool", insert = "query")
        public void filter(AstObjectProperty property, Context context) {
            System.out.println(" Call Filter ");
            System.out.println(property.object()
                                       .property("term")
                                       .valueNode()
                                       .toString());
        }
    }

    @Test
    public void testProperty() {
        BnfGenerator generator = new BnfGenerator();
        generator.register(getClass());
        Logger.init();

        JsonObject wrapper = new JsonObject();
        JsonObject query = new JsonObject();
        JsonObject match_all = new JsonObject();

        query.add("match_all", match_all);
        wrapper.add("query", query);

        Logger.v(new GsonBuilder().setPrettyPrinting().create().toJson(wrapper));
        Lexer lexer = new JustLexer(wrapper.toString());
        lexer.reserved("query");
        lexer.reserved("match_all");

        BnfCom bnfCom = generator.generate();
        AstQueryProgram program = (AstQueryProgram) (bnfCom.parse(lexer));
        program.eval(generator.context());
    }

    @Test
    public void testProperty1() {
        BnfGenerator generator = new BnfGenerator();
        generator.register(ProxyDemo.class);
        Logger.init();

        String demo = "{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"must\" : {\n" +
                "        \"term\" : { \"user\" : \"kimchy\" }\n" +
                "      },\n" +
                "      \"filter\": {\n" +
                "        \"term\" : { \"tag\" : \"tech\" }\n" +
                "      },\n" +
                "    }\n" +
                "  }\n" +
                "}";

        Logger.v(demo);
        Lexer lexer = new JustLexer(demo);
        lexer.reserved("query");
        lexer.reserved("match_all");
        lexer.reserved("must");
        lexer.reserved("bool");
        lexer.reserved("term");
        lexer.reserved("filter");

        BnfCom bnfCom = generator.generate();
        AstQueryProgram program = (AstQueryProgram) (bnfCom.parse(lexer));
        program.eval(generator.context());
    }
}