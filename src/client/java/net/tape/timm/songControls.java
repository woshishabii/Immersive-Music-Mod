package net.tape.timm;

import net.minecraft.client.MinecraftClient;
import net.tape.timm.audio.Sound;
import net.tape.timm.audio.Song;

import java.util.*;

import static net.tape.timm.timmMain.LOGGER;


public class songControls {

    public static MinecraftClient mc;
    public static Map<String, String[]> bp = biomePlaylists.biomePlaylists;
    public static Sound lastSound;

    public static Song lastSong;
    public static long timer;
    public static boolean inTimer;
    public static Random song_rng;
    public static boolean soundEngineStarted = false;


    public static void init() {
        mc = MinecraftClient.getInstance();
        lastSong = null;
        timer = 10;
        inTimer = true;
        song_rng = new Random();
    }

    public static void test() {
        Sound x = new Sound("/home/tape/Documents/cs/mods/The-Immersive-Music-Mod/run/music/TIMM/Swamp_001-final-2.ogg");
        x.play();
    }

    public static void play(Song song) {
        if (song != null) {
            if (lastSound.isPlaying()) {
                lastSound.stop();
            }
            lastSound = new Sound(song.getFilePath());
            lastSound.play();
            lastSong = song;
        }
    }

    public static void stop() {
        if (lastSound.isPlaying()) {
            lastSound.stop();

            // set timer and rng delay time
            inTimer = true;
            long x; // delay time
            if (lastSong == null) {
                x = 10;
            } else {
                // TODO: FIX THIS
                        x = pickDelay(modConfig.minMenuDelay, modConfig.maxMenuDelay, song_rng);
            }
            timer = x;

            // debug logging
            if (modConfig.debugLogging) {
                LOGGER.info("ticks until next song: ".concat(String.valueOf(x)));
            }

        }
    }

    public static void skip(Song next) {
        if (lastSound.isPlaying()) {
            stop();
        }
        if (inTimer) {
            inTimer = false;
            timer = 10;
        }
        play(next);
    }

    public static void skip() {
        Song song = pickSong();
        if (song != null) {
            if (inTimer) {
                inTimer = false;
            }
            play(song);
        }
    }

    public static long pickDelay(long min, long max, Random rng) {
        if (min == max) {
            return min;
        } else if (max < min) {
            return rng.nextLong(max, min);
        } else {
            return rng.nextLong(min, max);
        }
    }


    public static Song pickSong() {


        return new Song(configManager.config_dir.concat("/music/TIMM/desert_005.ogg"), "test", "nate");

        /*
        // determine playlist and set delay
        String playlistName = "menu";
        if (mc.world != null) {
            assert mc.player != null;
            Optional<RegistryKey<Biome>> temp = mc.world.getBiome(mc.player.getBlockPos()).getKey();
            if (temp.isPresent()) {
                playlistName = temp.get().getValue().toString();

                if (modConfig.debugLogging) {
                    timmMain.LOGGER.info(playlistName);
                }
            }
        }

        // get playlist
        List<String> playlist;
        try {
            playlist = Arrays.asList(bp.get(playlistName));
        } catch (NullPointerException e) {
            playlist = Arrays.asList(bp.get("fallback"));
        }
        if (playlist.isEmpty()) {
            playlist = Arrays.asList(bp.get("fallback"));
        }

        // pick song in playlist
        int index = Math.abs(song_rng.nextInt() % playlist.size());
        String songName = playlist.get(index);
        try {
            return new Song(SoundEvent.of(Identifier.tryParse(songName)), "", "Unknown");
        } catch (NullPointerException e) {
            timmMain.LOGGER.warn(String.format("song \"%s\" does not exist!", songName));
            return null;
        }

         */

    }

    public static String nowPlaying() {
        if (lastSound.isPlaying()) {
            return lastSound.getFilePath();
        } else {
            return null;
        }
    }

    public static ArrayList<Song> getPlaylists() {
        ArrayList<Song> selectionPool = new ArrayList<>();

        if (mc.world == null) {
            // game has not started, therefore must be in main menu

        }


        return selectionPool;
    }


    public static ArrayList<Song> getPlaylist(String name) {
        ArrayList<Song> playlist = new ArrayList<>();

        ArrayList<String> strings = new ArrayList<String>(List.of(bp.get(name)));


        return playlist;
    }

    public static Song songLookup(String name) {



    }











}
