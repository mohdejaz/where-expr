// Generated from LabeledWhereExpr.g4 by ANTLR 4.9.2

package parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LabeledWhereExprParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LabeledWhereExprVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LabeledWhereExprParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(LabeledWhereExprParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by the {@code date}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate(LabeledWhereExprParser.DateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code add}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdd(LabeledWhereExprParser.AddContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(LabeledWhereExprParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mult}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMult(LabeledWhereExprParser.MultContext ctx);
	/**
	 * Visit a parse tree produced by the {@code or}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr(LabeledWhereExprParser.OrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code like}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLike(LabeledWhereExprParser.LikeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unminus}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnminus(LabeledWhereExprParser.UnminusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eq}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEq(LabeledWhereExprParser.EqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code int}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(LabeledWhereExprParser.IntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code str}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStr(LabeledWhereExprParser.StrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idin}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdin(LabeledWhereExprParser.IdinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code not}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNot(LabeledWhereExprParser.NotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atodate}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtodate(LabeledWhereExprParser.AtodateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atonum}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtonum(LabeledWhereExprParser.AtonumContext ctx);
	/**
	 * Visit a parse tree produced by the {@code and}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd(LabeledWhereExprParser.AndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rel}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRel(LabeledWhereExprParser.RelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tos}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTos(LabeledWhereExprParser.TosContext ctx);
	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link LabeledWhereExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(LabeledWhereExprParser.IdContext ctx);
}