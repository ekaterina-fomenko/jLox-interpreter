package com.craftinginterpreters.lox.interpreter;

import com.craftinginterpreters.lox.tokens.Token;

import java.util.List;

/**
 * Generated by GenerateAst.java class
 */

public abstract class Expr {
    public interface Visitor<R> {
        public R visitAssignExpr(Assign expr);

        public R visitBinaryExpr(Binary expr);

        public R visitGroupingExpr(Grouping expr);

        public R visitLiteralExpr(Literal expr);

        public R visitLogicalExpr(Logical expr);

        public R visitUnaryExpr(Unary expr);

        public R visitVariableExpr(Variable expr);

        public R visitCallExpr(Call expr);

        public R visitFunctionExpr(Function expr);

        public R visitGetExpr(Get expr);

        public R visitSetExpr(Set expr);

        public R visitThisExpr(This expr);

        public R visitSuperExpr(Super expr);
    }

    /**
     * assignment → identifier "=" assignment | logic_or ;
     */
    public static class Assign extends Expr {
        public Assign(Token name, Expr value) {
            this.name = name;
            this.value = value;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitAssignExpr(this);
        }

        public final Token name;
        public final Expr value;
    }

    /**
     * binary -> expression operator expression
     */
    public static class Binary extends Expr {
        public Binary(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinaryExpr(this);
        }

        public final Expr left;
        public final Token operator;
        public final Expr right;
    }

    /**
     * grouping -> "(" expression ")"
     */
    public static class Grouping extends Expr {
        public Grouping(Expr expression) {
            this.expression = expression;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitGroupingExpr(this);
        }

        public final Expr expression;
    }

    /**
     * literal -> NUMBER | STRING | "true" | "false" | "nil"
     */
    public static class Literal extends Expr {
        public Literal(Object value) {
            this.value = value;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralExpr(this);
        }

        public final Object value;
    }

    /**
     * logic_or   → logic_and ( "or" logic_and )* ;
     * logic_and  → equality ( "and" equality )* ;
     */
    public static class Logical extends Expr {
        public Logical(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLogicalExpr(this);
        }

        public final Expr left;
        public final Token operator;
        public final Expr right;
    }

    /**
     * unary ->  ( "-" | "!" ) expression
     */
    public static class Unary extends Expr {
        public Unary(Token operator, Expr right) {
            this.operator = operator;
            this.right = right;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitUnaryExpr(this);
        }

        public final Token operator;
        public final Expr right;
    }

    /**
     * varDecl → "var" IDENTIFIER ( "=" expression )? ";" ;
     */
    public static class Variable extends Expr {
        public Variable(Token name) {
            this.name = name;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitVariableExpr(this);
        }

        public final Token name;
    }

    /**
     * call  → primary ( "(" arguments? ")" )* ;
     */
    public static class Call extends Expr {
        public Call(Expr callee, Token paren, List<Expr> arguments) {
            this.callee = callee;
            this.paren = paren;
            this.arguments = arguments;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitCallExpr(this);
        }

        public final Expr callee;
        public final Token paren;
        public final List<Expr> arguments;
    }

    public static class Function extends Expr {
        public Function(Token name, List<Token> parameters, List<Stmt> body) {
            this.name = name;
            this.parameters = parameters;
            this.body = body;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitFunctionExpr(this);
        }

        public final Token name;
        public final List<Token> parameters;
        public final List<Stmt> body;
    }

    public static class Get extends Expr {
        public Get(Expr object, Token name) {
            this.object = object;
            this.name = name;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitGetExpr(this);
        }

        public final Expr object;
        public final Token name;
    }

    public static class Set extends Expr {
        public Set(Expr object, Token name, Expr value) {
            this.object = object;
            this.name = name;
            this.value = value;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitSetExpr(this);
        }

        public final Expr object;
        final Token name;
        public final Expr value;
    }

    public static class This extends Expr {
        public This(Token keyword) {
            this.keyword = keyword;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitThisExpr(this);
        }

        public final Token keyword;
    }

    public static class Super extends Expr {
        public Super(Token keyword, Token method) {
            this.keyword = keyword;
            this.method = method;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitSuperExpr(this);
        }

        public final Token keyword;
        final Token method;
    }

    public abstract <R> R accept(Visitor<R> visitor);
}
