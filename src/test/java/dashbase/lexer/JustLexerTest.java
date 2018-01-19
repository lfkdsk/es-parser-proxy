package dashbase.lexer;

import dashbase.token.Token;
import org.junit.Assert;
import org.junit.Test;

import java.util.Queue;

public class JustLexerTest {

    @Test
    public void testTokens() {
        Queue<Token> tokens = new JustLexer("tokens tokens").tokens();
        Assert.assertNotNull(tokens);
        Assert.assertEquals(tokens.size(), 2);
    }
}