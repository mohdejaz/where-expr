package util;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import parser.EvalWhereVisitor;
import parser.LabeledWhereExprLexer;
import parser.LabeledWhereExprParser;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TestParser {

  public static void main(String[] args) throws Exception {
    CharStream input = CharStreams.fromString("(upd >= '2021-05-30') and (upd < '2021-06-07')");
    LabeledWhereExprLexer lexer = new LabeledWhereExprLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    LabeledWhereExprParser parser = new LabeledWhereExprParser(tokens);
    ParseTree tree = parser.start();
    System.out.println("tree: " + tree.toStringTree(parser));
    Map<String, Object> vars = new HashMap<>();
    vars.put("upd", LocalDate.parse("2021-06-06"));
    EvalWhereVisitor visitor = new EvalWhereVisitor(vars);
    System.out.println(visitor.visit(tree));
  }
}
