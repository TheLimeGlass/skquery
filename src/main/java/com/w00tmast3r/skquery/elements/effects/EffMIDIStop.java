package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.custom.note.MidiUtil;
import org.bukkit.event.Event;

@Name("Stop MIDI")
@Description("Stops a midi file that is playing.")
@Examples("on join:;->play midi \"login\" to player;wait 5 seconds;stop midi \"login\"")
@Patterns("stop midi [id] %string%")
public class EffMIDIStop extends Effect {

    private Expression<String> midi;

    @Override
    protected void execute(Event event) {
        String m = midi.getSingle(event);
        if (m == null) return;
        if (MidiUtil.isPlaying(m)) {
            MidiUtil.stopMidi(m);
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "stop midi";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        midi = (Expression<String>) expressions[0];
        return true;
    }
}
