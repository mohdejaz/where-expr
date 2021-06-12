package parser;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class EvalWhereVisitor extends LabeledWhereExprBaseVisitor<Object> {
  Map<String, Object> vars;
  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public EvalWhereVisitor(Map<String, Object> vars) {
    this.vars = vars;
  }

  @Override
  public Object visitAdd(LabeledWhereExprParser.AddContext ctx) {
    BigDecimal left = (BigDecimal) visit(ctx.expr(0));
    BigDecimal right = (BigDecimal) visit(ctx.expr(1));
    if (ctx.op.getType() == LabeledWhereExprParser.ADD) return left.add(right);
    return left.subtract(right);
  }

  @Override
  public Object visitMult(LabeledWhereExprParser.MultContext ctx) {
    BigDecimal left = (BigDecimal) visit(ctx.expr(0));
    BigDecimal right = (BigDecimal) visit(ctx.expr(1));
    if (ctx.op.getType() == LabeledWhereExprParser.MUL) return left.multiply(right);
    return left.divide(right);
  }

  @Override
  public Object visitOr(LabeledWhereExprParser.OrContext ctx) {
    boolean left = (boolean) visit(ctx.expr(0));
    boolean right = (boolean) visit(ctx.expr(1));
    return left || right;
  }

  @Override
  public Object visitAnd(LabeledWhereExprParser.AndContext ctx) {
    // log.info("And children: " + ctx.getChildCount());
    boolean left = (boolean) visit(ctx.expr(0));
    boolean right = (boolean) visit(ctx.expr(1));
    // log.info("And left: " + left + " right: " + right);
    return left && right;
  }

  @Override
  public Object visitRel(LabeledWhereExprParser.RelContext ctx) {
    Object lhs = visit(ctx.expr(0));
    Object rhs = visit(ctx.expr(1));

    if (lhs instanceof LocalDate || rhs instanceof LocalDate) {
      LocalDate left = (LocalDate) lhs;
      LocalDate right = (LocalDate) rhs;

      // log.info("left: " + left + " right: " + right);

      switch (ctx.op.getType()) {
        case LabeledWhereExprParser.GE: {
          return left.isEqual(right) || left.isAfter(right);
        }
        case LabeledWhereExprParser.GT: {
          return left.isAfter(right);
        }
        case LabeledWhereExprParser.LE: {
          return left.isEqual(right) || left.isBefore(right);
        }
        case LabeledWhereExprParser.LT: {
          return left.isBefore(right);
        }
      }
    }

    if (lhs instanceof BigDecimal || rhs instanceof BigDecimal) {
      BigDecimal left = (BigDecimal) lhs;
      BigDecimal right = (BigDecimal) rhs;

      switch (ctx.op.getType()) {
        case LabeledWhereExprParser.GE: {
          return left.compareTo(right) >= 0;
        }
        case LabeledWhereExprParser.GT: {
          return left.compareTo(right) > 0;
        }
        case LabeledWhereExprParser.LE: {
          return left.compareTo(right) <= 0;
        }
        case LabeledWhereExprParser.LT: {
          return left.compareTo(right) < 0;
        }
      }
    }

    return false;
  }

  @Override
  public Object visitId(LabeledWhereExprParser.IdContext ctx) {
    return vars.get(ctx.ID().getText());
  }

  @Override
  public Object visitEq(LabeledWhereExprParser.EqContext ctx) {
    Object lhs = visit(ctx.expr(0));
    Object rhs = visit(ctx.expr(1));

    if (lhs instanceof LocalDate || rhs instanceof LocalDate) {
      // Eval dates
      if (ctx.op.getType() == LabeledWhereExprParser.EQ) {
        return ((LocalDate) lhs).isEqual((LocalDate) rhs);
      }
      return !((LocalDate) lhs).isEqual((LocalDate) rhs);
    }

    if (lhs instanceof String || rhs instanceof String) {
      // Eval Strings
      if (ctx.op.getType() == LabeledWhereExprParser.EQ) {
        return lhs.equals(rhs);
      }
      return !lhs.equals(rhs);
    }

    // Eval Numbers
    if (ctx.op.getType() == LabeledWhereExprParser.EQ) {
      return ((BigDecimal) lhs).compareTo((BigDecimal) rhs) == 0;
    }
    return ((BigDecimal) lhs).compareTo((BigDecimal) rhs) != 0;
  }

  @Override
  public Object visitInt(LabeledWhereExprParser.IntContext ctx) {
    return new BigDecimal(ctx.INT().getText());
  }

  @Override
  public Object visitStr(LabeledWhereExprParser.StrContext ctx) {
    return removeQuotes(ctx.STR().getText());
  }

  @Override
  public Object visitUnminus(LabeledWhereExprParser.UnminusContext ctx) {
    BigDecimal child = (BigDecimal) visit(ctx.expr());
    return child.multiply(new BigDecimal("-1"));
  }

  @Override
  public Object visitNot(LabeledWhereExprParser.NotContext ctx) {
    boolean child = (Boolean) visit(ctx.expr());
    return !child;
  }

  @Override
  public Object visitIdin(LabeledWhereExprParser.IdinContext ctx) {
    Object left = vars.get(ctx.ID().getText());

    if (left == null) {
      log.warn("left is null!");
      return false;
    }

    List<LabeledWhereExprParser.ExprContext> ctxList = ctx.expr();

    if (left instanceof BigDecimal) {
      BigDecimal lhs = (BigDecimal) left;
      boolean result = false;
      for (LabeledWhereExprParser.ExprContext e : ctxList) {
        BigDecimal rhs = (BigDecimal) visit(e);
        if (lhs.compareTo(rhs) == 0) {
          result = true;
          break;
        }
      }
      return result;
    }

    if (left instanceof LocalDate) {
      LocalDate lhs = (LocalDate) left;
      boolean result = false;
      for (LabeledWhereExprParser.ExprContext e : ctxList) {
        LocalDate rhs = (LocalDate) visit(e);
        if (lhs.isEqual(rhs)) {
          result = true;
          break;
        }
      }
      return result;
    }

    String lhs = left.toString();
    boolean result = false;
    for (LabeledWhereExprParser.ExprContext e : ctxList) {
      String rhs = visit(e).toString();
      if (lhs.equals(rhs)) {
        result = true;
        break;
      }
    }
    return result;
  }

  @Override
  public Object visitFunc(LabeledWhereExprParser.FuncContext ctx) {
    String func = ctx.ID().getText();
    List<LabeledWhereExprParser.ExprContext> ctxList = ctx.expr();

    switch (func.toLowerCase()) {
      case "atodate": {
        String rhs = visit(ctxList.get(0)).toString();
        LocalDate date = LocalDate.parse(rhs.toString(), dtf);
        return date;
      }
      case "atonum": {
        String rhs = visit(ctxList.get(0)).toString();
        return new BigDecimal(rhs.toString());
      }
      case "tos": {
        return visit(ctxList.get(0)).toString();
      }
      case "abs": {
        String rhs = visit(ctxList.get(0)).toString();
        return new BigDecimal(rhs.toString()).abs();
      }
    }

    return null;
  }

  @Override
  public Object visitLike(LabeledWhereExprParser.LikeContext ctx) {
    String left = (String) visit(ctx.expr(0));
    String right = (String) visit(ctx.expr(1));
    // log.info("left: " + left + " right: " + right + " contains: " + right.contains(left));
    if (right.length() > left.length()) return right.contains(left);
    return left.contains(right);
  }

  @Override
  public Object visitDate(LabeledWhereExprParser.DateContext ctx) {
    return LocalDate.parse(ctx.DATE().getText().replace("'", ""), dtf);
  }

  private String removeQuotes(String str) {
    return str.replaceAll("^\"", "").replaceAll("\"$", "");
  }
}
