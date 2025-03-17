package cmsc256;


public class MySong extends bridges.data_src_dependent.Song implements Comparable<MySong> {
    private String artist;
    private String song;
    private String album;
    private String lyrics;
    private String release_date;

    public MySong(String artist, String song, String album, String lyrics, String release_date) {
        this.artist = artist;
        this.song = song;
        this.album = album;
        this.lyrics = lyrics;
        this.release_date = release_date;
    }

    @Override
    public int compareTo(MySong o) {
        return this.song.compareTo(o.song);
    }
        public String toString() {
            return String.format("Title: %s  Album: %s", song, album);
        }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }

        public String getSong() {
            return song;
        }

        public void setSong(String song) {
            this.song = song;
        }

        public String getAlbum() {
            return album;
        }

        public void setAlbum(String album) {
            this.album = album;
        }

        public String getLyrics() {
            return lyrics;
        }

        public void setLyrics(String lyrics) {
            this.lyrics = lyrics;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

    }









