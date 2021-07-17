package hello;


import hello.gen.HelloLexer;
import hello.gen.HelloParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * @author xzh
 * @version 1.0
 * @description:
 * @date 2021/7/9 1:17 下午
 */
public class Main {
  public static void main(String[] args) {
    // first, form input charstream from string
    CharStream input = CharStreams.fromString("hello #");

    // 1. Lexer-Lexical analysis
    // Create a lexer that feeds off of input CharStream.
    HelloLexer lexer = new HelloLexer(input);

    // 2. Create a buffer of tokens pulled from the lexer.
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    // 3. Parser-Syntax analysis
    // Create a parser that feeds off the tokens buffer.
    HelloParser parser = new HelloParser(tokens);

    // 4. Begin parsing at "select" rule.
    ParseTree tree = parser.heel();
    int total = tree.getChildCount();
    int start = 0;
    while (start < total) {
      ParseTree child = tree.getChild(start);
      System.out.println(child.toStringTree());
      start++;
    }
    System.out.println(tree.toStringTree(parser));
  }
}
