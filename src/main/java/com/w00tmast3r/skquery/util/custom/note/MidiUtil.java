package com.w00tmast3r.skquery.util.custom.note;


import org.bukkit.Sound;
import org.bukkit.entity.Player;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

/**
 * Utility for playing midi files for players to hear.
 *
 * @author authorblues
 */
public class MidiUtil {

    // provided by github.com/sk89q/craftbook
    private static final int[] instruments = {
            0, 0, 0, 0, 0, 0, 0, 5, //   8
            6, 0, 0, 0, 0, 0, 0, 0, //  16
            0, 0, 0, 0, 0, 0, 0, 5, //  24
            5, 5, 5, 5, 5, 5, 5, 5, //  32
            6, 6, 6, 6, 6, 6, 6, 6, //  40
            5, 5, 5, 5, 5, 5, 5, 2, //  48
            5, 5, 5, 5, 0, 0, 0, 0, //  56
            0, 0, 0, 0, 0, 0, 0, 0, //  64
            0, 0, 0, 0, 0, 0, 0, 0, //  72
            0, 0, 0, 0, 0, 0, 0, 0, //  80
            0, 0, 0, 0, 0, 0, 0, 0, //  88
            0, 0, 0, 0, 0, 0, 0, 0, //  96
            0, 0, 0, 0, 0, 0, 0, 0, // 104
            0, 0, 0, 0, 0, 0, 0, 0, // 112
            1, 1, 1, 3, 1, 1, 1, 5, // 120
            1, 1, 1, 1, 1, 2, 4, 3, // 128
    };
    private static HashMap<String, NoteBlockReceiver> playing = new HashMap<>();

    private static void playMidi(Sequence seq, float tempo, Set<Player> listeners, String ID)
            throws InvalidMidiDataException, IOException, MidiUnavailableException {
        Sequencer sequencer = MidiSystem.getSequencer(false);
        sequencer.setSequence(seq);
        sequencer.open();

        // slow it down just a bit
        sequencer.setTempoFactor(tempo);

        NoteBlockReceiver noteblockRecv = new NoteBlockReceiver(listeners);
        sequencer.getTransmitter().setReceiver(noteblockRecv);
        sequencer.start();
        playing.put(ID, noteblockRecv);
    }

    public static void dump() {
        playing.clear();
    }

    public static void playMidi(File file, float tempo, Set<Player> listeners, String ID)
            throws InvalidMidiDataException, IOException, MidiUnavailableException {
        playMidi(MidiSystem.getSequence(file), tempo, listeners, ID);
    }

    public static void playMidi(InputStream stream, float tempo, Set<Player> listeners, String ID)
            throws InvalidMidiDataException, IOException, MidiUnavailableException {
        playMidi(MidiSystem.getSequence(stream), tempo, listeners, ID);
    }

    public static boolean playMidiQuietly(File file, float tempo, Set<Player> listeners, String ID) {
        try {
            MidiUtil.playMidi(file, tempo, listeners, ID);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
            return false;
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean playMidiQuietly(InputStream stream, float tempo, Set<Player> listeners, String ID) {
        try {
            MidiUtil.playMidi(stream, tempo, listeners, ID);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
            return false;
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean playMidiQuietly(File file, Set<Player> listeners, String ID) {
        return playMidiQuietly(file, 1.0f, listeners, ID);
    }

    public static boolean playMidiQuietly(InputStream stream, Set<Player> listeners, String ID) {
        return playMidiQuietly(stream, 1.0f, listeners, ID);
    }

    public static boolean isPlaying(String ID) {
        if (playing.containsKey(ID)) {
            return true;
        }
        return false;
    }

    public static void stopMidi(String ID) {
        if (playing.containsKey(ID)) {
            playing.get(ID).close();
            playing.remove(ID);
        }
    }

    public static Sound patchToInstrument(int patch) {
        // look up the instrument matching the patch
        switch (instruments[patch]) {
            case 1:
                return Sound.BLOCK_NOTE_HAT;
            case 2:
                return Sound.BLOCK_NOTE_SNARE;
            case 3:
                return Sound.BLOCK_NOTE_HARP;
            case 4:
                return Sound.BLOCK_NOTE_BASEDRUM;
            case 5:
                return Sound.BLOCK_NOTE_PLING;
            case 6:
                return Sound.BLOCK_NOTE_BASS;
        }

        // if no instrument match is found, use piano
        return Sound.BLOCK_NOTE_BASS;
    }
}

