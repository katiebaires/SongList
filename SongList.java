/*
 * SongList.java
 * Project 3
 * CMSC 256
 * Spring Semester
 *
 * Grabs songs from a song database, and puts them in a custom-made LinkedList
 */

package cmsc256;

import bridges.base.SLelement;
import bridges.connect.Bridges;
import bridges.connect.DataSource;
import bridges.data_src_dependent.Song;

import java.util.*;

public class SongList implements List<MySong>, Iterable<MySong> {
    public static void main(String[] args) {
        Bridges bridges = new Bridges(3, "samuelsarzaba", "519002424079");
        DataSource ds = bridges.getDataSource();
        SongList songList = new SongList();
        ArrayList<Song> songData = null;
        try {
            songData = ds.getSongData();
        } catch (Exception e) {
            System.out.println("Unable to connect to Bridges.");
        }
        for (int i = 0; i < Objects.requireNonNull(songData).size(); i++) {
            Song song = songData.get(i);
            songList.add(new MySong(song.getArtist(), song.getSongTitle(), song.getAlbumTitle(), song.getLyrics(), song.getReleaseDate()));
        }
        Iterator<MySong> it = songList.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        System.out.println();
        System.out.println(songList.getSongsByArtist("ur mom"));
    }

    private SLelement<MySong> head = null;
    private String name = "";

    public SongList() {
        // Creates empty linked-list
    }

    /**
     * Clears the contents of the SongList
     */
    @Override
    public void clear() {
        // Sets head node to null, cutting off connections to any other nodes
        head = null;
    }

    /**
     * Finds songs with matching artist, and lists them in alphabetical order by title
     *
     * @param artist Artist that the user wishes to search for
     * @return Formatted String listing the songs in alphabetical order
     */
    public String getSongsByArtist(String artist) {
        // Create LinkedList so that you can use Collections
        LinkedList<MySong> list = new LinkedList<>();
        // Iterate through each MySong in the SongList
        for (MySong mySong : this) {
            // If the MySong is made by the desired artist, add it to the list
            if (mySong.getArtist().equals(artist)) list.add(mySong);
        }
        // Sort the list using the custom comparator in MySong (sorts by title)
        Collections.sort(list);
        // Declare StringBuilder (more efficient than a String)
        StringBuilder result = new StringBuilder();
        // Iterate through each mySong in the list
        for (MySong mySong : list) {
            // Append their details to the result String using the custom toString method
            result.append(mySong.toString()).append("\n");
        }
        // If the result isn't empty, return the result String, otherwise return an error
        return result.length() != 0 ? result.toString() : String.format("There are no songs by %s in this playlist.", artist);
    }

    /**
     * Inserts a MySong object in a desired position
     *
     * @param it       The MySong object
     * @param position Where the user wishes to place it
     * @return Whether the method was executed successfully
     */
    @Override
    public boolean insert(MySong it, int position) {
        // Throw Exception if invalid position
        if (position < 0) throw new IllegalArgumentException();
        // If the position is position 0
        if (position == 0) {
            // Create new node containing the MySong
            SLelement<MySong> newNode = new SLelement<>(it);
            // Set its next node to the current head
            newNode.setNext(this.head);
            // Update the head to equal the new node
            head = newNode;
        } else {
            // While its lower position isn't negative
            while (position-- != -1) {
                // If it's in the first position
                if (position == 0) {
                    // Add node at required position
                    SLelement<MySong> newNode = new SLelement<>(it);
                    // Make the new node to point to the old node at the same position
                    newNode.setNext(this.head.getNext());
                    // Replace current with new node to the old node to point to the new node
                    this.head.setNext(newNode);
                    break;
                }
                // Set head equal to the next node
                this.head = this.head.getNext();
            }
            // If the position isn't at the 0, throw Exception
            if (position != 0) throw new IllegalArgumentException();
        }
        // Return true if it ran successfully
        return true;
    }

    /**
     * Adds MySong to the end of the SongList
     *
     * @param it The MySong the user wishes to add
     * @return Whether the method was executed successfully
     */
    @Override
    public boolean add(MySong it) {
        SLelement<MySong> newNode = new SLelement<>(it);
        // If the SongList is empty, then make the new node as the head
        if (head == null) {
            head = newNode;
        } else {
            // Else traverse until the last node and insert the newNode there
            SLelement<MySong> last = head;
            while (last.getNext() != null) last = last.getNext();
            // Insert the newNode at last node
            last.setNext(newNode);
        }
        // Return true if it runs correctly
        return true;
    }

    /**
     * Removes MySong at the given position
     *
     * @param position The position where the user wishes to remove the MySong
     * @return Whether the method was executed successfully
     */
    @Override
    public MySong remove(int position) {
        SLelement<MySong> currNode = head;
        SLelement<MySong> prev = null;
        // If index is 0, then head node itself is to be deleted
        if (position == 0 && currNode != null) {
            MySong temp = head.getValue();
            // Changed head
            head = currNode.getNext();
            // Return the MySong that was removed
            return temp;
        }
        // If the index is greater than 0 but less than the size of SongList
        int counter = 0;
        // Count for the index to be deleted, keep track of the previous node
        while (currNode != null) {
            if (counter == position) {
                // Since the currNode is the required position unlink currNode from SongList
                MySong temp = prev.getNext().getValue();
                prev.setNext(currNode.getNext());
                return temp;
            } else {
                // If current position is not the index continue to next node
                prev = currNode;
                currNode = currNode.getNext();
                counter++;
            }
        }
        // If index is greater than the size of the SongList the currNode should be null
        throw new NoSuchElementException();
    }

    /**
     * Size of the SongList
     *
     * @return The size of the SongList
     */
    @Override
    public int size() {
        SLelement<MySong> currNode = head;
        int count = 0;
        // Traverse through the SongList
        while (currNode != null) {
            // Increment count
            count++;
            // Go to next node
            currNode = currNode.getNext();
        }
        // Return the count of objects
        return count;
    }

    /**
     * Whether the SongList is empty
     *
     * @return Whether the SongList is empty
     */
    @Override
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Whether SongList contains a certain MySong
     *
     * @param target The MySong that's being searched for
     * @return Whether SongList contains the MySong
     */
    @Override
    public boolean contains(MySong target) {
        SLelement<MySong> currNode = head;
        // Traverse through the SongList
        while (currNode != null) {
            // Return true of target is found
            if (currNode.getValue() == target) return true;
            // Otherwise keep searching
            currNode = currNode.getNext();
        }
        // If the while loops finishes, assume target isn't found, return false
        return false;
    }

    /**
     * Find MySong at given position
     *
     * @param position The position you're looking at
     * @return MySong at the given position
     */
    @Override
    public MySong getValue(int position) {
        // Throw Exception if out of bounds
        if (!(position >= 0 && position < this.size())) throw new IllegalArgumentException();
        // Declare pointer
        SLelement<MySong> currNode = head;
        // Traverse through SongList position amount of times
        for (int i = 0; i < position; i++) {
            currNode = currNode.getNext();
        }
        // Return the currNode value
        return currNode.getValue();
    }

    /**
     * Creates an Iterator for SongList
     *
     * @return The Iterator for SongList
     */
    @Override
    public Iterator<MySong> iterator() {
        // Returns the SongIterator object
        return new SongIterator();
    }

    /**
     * Gets the name of the playlist
     *
     * @return The name of the playlist
     */
    public String getPlaylistName() {
        return name;
    }

    /**
     * Sets the name of the playlist
     *
     * @param name Sets the name
     */
    public void setPlaylistName(String name) {
        this.name = name;
    }

    private class SongIterator implements Iterator<MySong> {
        private SLelement<MySong> currNode = head;

        /**
         * Whether there is a next value in the Iterator
         *
         * @return Whether there is a next value in the Iterator
         */
        @Override
        public boolean hasNext() {
            return currNode != null;
        }

        /**
         * The next MySong in the Iterator
         *
         * @return The next MySong in the Iterator
         */
        @Override
        public MySong next() {
            // If there isn't a next, throw Exception
            if (!hasNext()) throw new NoSuchElementException();
            // Return current node, and update it to the next node
            SLelement<MySong> temp = currNode;
            currNode = currNode.getNext();
            return temp.getValue();
        }
    }
}
