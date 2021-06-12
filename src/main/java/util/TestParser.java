package util;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import parser.EvalWhereVisitor;
import parser.LabeledWhereExprLexer;
import parser.LabeledWhereExprParser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TestParser {

  public static void main(String[] args) throws Exception {
    CharStream input = CharStreams.fromString("abs(atonum(a)) < 10");
    LabeledWhereExprLexer lexer = new LabeledWhereExprLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    LabeledWhereExprParser parser = new LabeledWhereExprParser(tokens);
    ParseTree tree = parser.start();
    System.out.println("tree: " + tree.toStringTree(parser));
    Map<String, Object> vars = new HashMap<>();
    vars.put("a", new BigDecimal("-1.23"));
    EvalWhereVisitor visitor = new EvalWhereVisitor(vars);
    System.out.println(visitor.visit(tree));
  }
}
