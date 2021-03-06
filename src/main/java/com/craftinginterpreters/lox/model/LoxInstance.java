package com.craftinginterpreters.lox.model;

import com.craftinginterpreters.lox.interpreter.errors.RuntimeError;
import com.craftinginterpreters.lox.tokens.Token;

import java.util.HashMap;
import java.util.Map;

/**
 * For creating new instances of classes
 */
public class LoxInstance {
    private LoxClass klass;
    private final Map<String, Object> fields = new HashMap<>();

    LoxInstance(LoxClass klass) {
        this.klass = klass;
    }

    @Override
    public String toString() {
        return klass.name + " instance";
    }

    public Object get(Token name) {
        if (fields.containsKey(name.lexeme)) {
            return fields.get(name.lexeme);
        }
        LoxFunction method = klass.findMethod(this, name.lexeme);
        if (method != null) {
            return method;
        }

        throw new RuntimeError(name,
                "Undefined property '" + name.lexeme + "'.");
    }

    public void set(Token name, Object value) {
        fields.put(name.lexeme, value);
    }
}
