package my;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import my.gen.ArithmeticExpressionLexer;
import my.gen.ArithmeticExpressionParser;
import my.gen.CustomIndicatorExpressionLexer;
import my.gen.CustomIndicatorExpressionParser;


/**
 * antlr 相关工具类
 *
 * @author
 * @version 1.0.0
 * @since 2021/07/01 18:42
 */
public class AntlrUtils {

  public static ParseTree generateCustomIndicatorParseTreeFromString(String expression) {
    CustomIndicatorExpressionLexer lexer = new CustomIndicatorExpressionLexer(CharStreams.fromString(expression));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    CustomIndicatorExpressionParser parser = new CustomIndicatorExpressionParser(tokens);
    return parser.expr();
  }

  public static ParseTree generateArithmeticExpressionParseTreeFromString(String expression) {
    ArithmeticExpressionLexer lexer = new ArithmeticExpressionLexer(CharStreams.fromString(expression));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    ArithmeticExpressionParser parser = new ArithmeticExpressionParser(tokens);
    return parser.expr();
  }
}
