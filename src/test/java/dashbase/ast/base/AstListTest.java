package dashbase.ast.base;

import bnfgenast.ast.base.AstList;
import bnfgenast.ast.base.AstNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class AstListTest {

    @Test
    public void testIterator() {
        AstList list = new AstList(Collections.singletonList(new AstList(null, -1)), 1000);

        for (AstNode node : list) {
            Assert.assertNotNull(node);
            Assert.assertEquals(node.getTag(), -1);
        }
    }
}