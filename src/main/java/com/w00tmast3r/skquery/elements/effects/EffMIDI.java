package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.custom.note.MidiUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;

@Name("Play MIDI")
@Description("Plays a file with the extention .mid to a player.")
@Examples("on join:;->play midi \"login\" to player")
@Patterns("play midi %string% to %players% [at [tempo] %-number%]")
public class EffMIDI extends Effect {

    private Expression<String> midi;
    private Expression<Player> players;
    private Expression<Number> tempo;

    @Override
    protected void execute(Event event) {
        String m = midi.getSingle(event);
        if (m == null) return;
        File f = new File(Skript.getInstance().getDataFolder().getAbsolutePath() + File.separator + Skript.SCRIPTSFOLDER + File.separator + m + ".mid");
        HashSet<Player> pList = new HashSet<>();
        Float tempoFinal = (Float) 1.0f;
        if (tempo != null) {
            Number num = tempo.getSingle(event);
            tempoFinal = num.floatValue();
        }
        if (f.exists()) {
            Collections.addAll(pList, players.getAll(event));
            try {
                MidiUtil.playMidi(f, tempoFinal, pList, m);
            } catch (InvalidMidiDataException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        } else {
            Bukkit.getLogger().warning("Could not find midi file " + m + ".mid in the scripts folder");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "midi";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        midi = (Expression<String>) expressions[0];
        players = (Expression<Player>) expressions[1];
        return true;
    }
}
