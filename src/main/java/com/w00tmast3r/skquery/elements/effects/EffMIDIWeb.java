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
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;

@Name("Play MIDI")
@Description("Plays a file with the extention .mid to a player.")
@Examples("on join:;->play midi \"login\" to player")
@Patterns("play midi from [(web[site]|link)] %string% to %players% [at [tempo] %-number%]")
public class EffMIDIWeb extends Effect {

    private Expression<String> link;
    private Expression<Player> players;
    private Expression<Number> tempo;

    @Override
    protected void execute(Event event) {
        String m = link.getSingle(event);
        if (m == null) return;
        HashSet<Player> pList = new HashSet<>();
        Collections.addAll(pList, players.getAll(event));
        InputStream input;
        Float tempoFinal = (Float) 1.0f;
        if (tempo != null) {
            Number num = tempo.getSingle(event);
            tempoFinal = num.floatValue();
        }
        try {
            input = new URL(link.getSingle(event)).openStream();
            try {
                MidiUtil.playMidi(input, tempoFinal, pList, m);
            } catch (InvalidMidiDataException e) {
                e.printStackTrace();
                return;
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
                return;
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "midi";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        link = (Expression<String>) expressions[0];
        players = (Expression<Player>) expressions[1];
        return true;
    }
}
