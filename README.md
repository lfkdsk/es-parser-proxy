# EtD Proxy Demo

> Demo 包含成品基本的语法结构，和 Lexer && Parser，暂时未包含 Converter 的具体实现。

## Grammar Define

``` java
	// ... more in QueryGrammar.java
    ///////////////////////////////////////////////////////////////////////////
    // label := value | object | array
    ///////////////////////////////////////////////////////////////////////////

    @Getter
    private BnfCom label = label0.reset(AstLabelExpr.class).or(
            valueLabel,
            objectLabel,
            arrayLabel
    );

    ///////////////////////////////////////////////////////////////////////////
    // program = {
    //      labelList
    // } EOL (end of line)
    ///////////////////////////////////////////////////////////////////////////

    @Getter
    private BnfCom program = rule(AstQueryProgram.class).token("{").maybe(innerLabelList).token("}").sep(EOL);

```

可以在代码中直接使用 Combinator 进行语法定义，修改也只需要修改一处。

> \# todo 更多功能的 Combinators



## Converter

使用不同的 Visitor 实现类，实现对不同目标语言语法的转换支持。

``` java
public interface AstVisitor<T> {
    T visitAstArrayLabel(AstArrayLabel visitor);

    T visitAstInnerLabelExpr(AstInnerLabelExpr visitor);
	// more... in AstVisitor.java
}

```

