package dashbase.meta;

import dashbase.env.Context;
import dashbase.ast.object.AstObjectProperty;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BindMethodFinderTest {

    public static class FinderDemo {

        @Bind(name = "lfkdsk", mode = GrammarMode.OBJECT, prefix = {"query"})
        public void bind(AstObjectProperty property, Context context) {

        }

        public void test(AstObjectProperty property, Context context) {

        }
    }

    @Test
    public void testFinderDemo() {
        BindMethodFinder finder = new BindMethodFinder();
        List<BindMethod> list = finder.findBindMethods(FinderDemo.class);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 1);

        BindMethod method = list.get(0);
        Assert.assertNotNull(method);
        Assert.assertEquals(method.getName(), "lfkdsk");
    }
}