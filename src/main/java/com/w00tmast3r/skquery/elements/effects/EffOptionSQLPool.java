package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.SkriptParser;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.elements.effects.base.OptionsPragma;
import com.w00tmast3r.skquery.sql.ScriptCredentials;

import java.io.File;

@Patterns("$ pool <.+>")
public class EffOptionSQLPool extends OptionsPragma {
    @Override
    protected void register(File executingScript, SkriptParser.ParseResult parseResult) {
        ScriptCredentials.currentPool = parseResult.regexes.get(0).group();
    }
}
