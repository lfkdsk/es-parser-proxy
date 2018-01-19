package dashbase.lexer;

import dashbase.token.Token;
import org.junit.Assert;
import org.junit.Test;

import java.util.Queue;

public class JustLexerTest {

    @Test
    public void testTokens() {
        Queue<Token> tokens = new JustLexer("tokens tokens \"lfkdsk\" true 100 100.101e-100").tokens();
        Assert.assertNotNull(tokens);
        Assert.assertEquals(tokens.size(), 6);
    }
}