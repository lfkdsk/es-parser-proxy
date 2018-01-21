package dashbase.ast.property;

import dashbase.ast.AstQueryProgram;
import dashbase.ast.object.AstObjectProperty;
import dashbase.env.Context;
import dashbase.meta.Bind;

import static dashbase.meta.GrammarMode.OBJECT;
import static dashbase.meta.GrammarMode.WRAPPER;

public class PropertyTest {

    @Bind(name = "wrapper", mode = WRAPPER)
    public void wrapper(AstQueryProgram property, Context context) {

    }

    @Bind(name = "query", mode = OBJECT, prefix = "wrapper")
    public void query(AstObjectProperty property, Context context) {

    }

    @Bind(name = "match_all", mode = OBJECT, prefix = "query")
    public void matchAll(AstObjectProperty property, Context context) {

    }

    @Bind(name = "match", mode = OBJECT, prefix = "query")
    public void match(AstObjectProperty property, Context context) {

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


}