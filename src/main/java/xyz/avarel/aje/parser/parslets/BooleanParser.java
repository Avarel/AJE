package xyz.avarel.aje.parser.parslets;

import xyz.avarel.aje.parser.AJEParser;
import xyz.avarel.aje.parser.PrefixParser;
import xyz.avarel.aje.parser.lexer.Token;
import xyz.avarel.aje.types.Any;
import xyz.avarel.aje.types.others.Truth;
import xyz.avarel.aje.types.others.Undefined;

public class BooleanParser implements PrefixParser<Any> {
    @Override
    public Any parse(AJEParser parser, Token token) {
        switch(token.getText()) {
            case "true": return Truth.TRUE;
            case "false": return Truth.FALSE;
            default: return Undefined.VALUE;
        }
    }
}