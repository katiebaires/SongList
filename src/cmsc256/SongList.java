/*
SongList.java
Katie Martinez
Project 3
CMSC 256
Spring Semester
This code retrieves songs from a database and adds them to a custom LinkedList
 */



package cmsc256;
import bridges.base.SLelement;
import java.util.*;
import bridges.data_src_dependent.Song;
import bridges.connect.Bridges;
import bridges.connect.DataSource;

public class SongList implements List<MySong>, Iterable<MySong> {
    public static void main(String[] args) {
        Bridges bridges = new Bridges(3, "katiemartinez", "7707975508");
        DataSource ds = bridges.getDataSource();
        SongList songList = new SongList();
        ArrayList<Song> numSong = null;
        try {
            numSong = ds.getSongData();
        } catch (Exception e) {
            System.out.println("Unable to connect to Bridges.");
        }
        for (int i = 0; i < Objects.requireNonNull(numSong).size(); i++) {
            Song song = numSong.get(i);
            songList.add(new MySong(song.getArtist(), song.getSongTitle(), song.getAlbumTitle(), song.getLyrics(), song.getReleaseDate()));
        }
        Iterator<MySong> it = songList.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        System.out.println();
        System.out.println(songList.getSongsByArtist("young nudy"));
    }


    private String name;
    private SLelement<MySong> head;

    @Override
    public void clear() {
        name = "";
        head = null;

    }

        public String getSongsByArtist(String artist) {
            LinkedList<MySong> l = new LinkedList<>();
           for(MySong s : this){
                if (s.getArtist().equals(artist)) l.add(s);
                }
            Collections.sort(l);
            StringBuilder result = new StringBuilder();
            for (MySong mySong : l) {
                result.append(mySong.toString()).append("\n");
            }
            return result.length() != 0 ? result.toString() : String.format("There are no songs by %s in this playlist.", artist);
        }
    @Override
    public boolean insert(MySong it, int position) {
        if (position < 0 || position > size()) {
            throw new IndexOutOfBoundsException();
        }
        if (position == 0) {
            head = new SLelement<>(it, head);
        } else {
            SLelement<MySong> current = head;
            for (int i = 0; i < position - 1; i++) {
                current = current.getNext();
            }
            current.setNext(new SLelement<>(it, current.getNext()));
        }
        return true;
    }


    @Override
    public boolean add(MySong it) {
        SLelement<MySong> newNode = new SLelement<>(it);
        if (head == null) {
            head = newNode;
        } else {
            SLelement<MySong> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        return true;
    }

    @Override
    public MySong remove(int position) {
        SLelement<MySong> currNode = head;
        SLelement<MySong> prevNode = null;

        if (position == 0 && currNode != null) {
            MySong temp = head.getValue();
            head = currNode.getNext();
            return temp;
        }

        int currPos = 0;
        while (currNode != null && currPos < position) {
            prevNode = currNode;
            currNode = currNode.getNext();
            currPos++;
        }


        if (currNode != null && currPos == position) {
            MySong temp = currNode.getValue();
            prevNode.setNext(currNode.getNext());
            return temp;
        }

        throw new IndexOutOfBoundsException();
    }

    @Override
    public int size() {
        int amount = 0;
        for (SLelement<MySong> currNode = head; currNode != null; currNode = currNode.getNext()) {
            amount++;
        }

        return amount;
    }


    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public boolean contains(MySong target) {
        SLelement<MySong> currNode = head;
        while (currNode != null) {

            if (currNode.getValue().equals(target)) {
                return true;
            }
            currNode = currNode.getNext();
        }
        return false;
    }

    @Override
    public MySong getValue(int position) {

        if (position < 0 || position >= size()) {
            throw new IndexOutOfBoundsException();
        }
        SLelement<MySong> node = head;
        for (int i = 0; i < position; i++) {
            node = node.getNext();
        }

        return node.getValue();
    }


    @Override
    public Iterator<MySong> iterator() {
        return new SongIterator();
    }

    public String getPlaylistName() {
        return name;
    }

    public void setPlaylistName(String name) {
        this.name = name;
    }

    private class SongIterator implements Iterator<MySong> {
        private SLelement<MySong> currNode = head;

        @Override
        public boolean hasNext() {
            return currNode != null;
        }

        @Override
        public MySong next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            MySong value = currNode.getValue();
            currNode = currNode.getNext();
            return value;
        }
    }
}
