package MyAudio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 		songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 

	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  
	}
	

	public void download(AudioContent content)
	{

		//check if the content given is a song. 
		if (content.getType().equals(Song.TYPENAME))
			if (songs.contains(content)) { 		// check if the content is already in the songs arraylist.
				 
				// if so, throw the corresponding exception.
				throw new SongAlreadyDownloadedException("Song " + content.getTitle() + " already downloaded.");
			}		
			
			else{ 							// executed if the content is not already in the songs arraylist. 
				songs.add((Song)content); 	//add the song.
				System.out.println("Song " + content.getTitle() + " was downloaded successfully."); 
			}

		//check if the content given is a audiobook.
		else if (content.getType().equals(AudioBook.TYPENAME)) 	
				if (audiobooks.contains(content)) {					// checking if audiobook is already in the audiobooks arraylist.
					
					// throw corresponding exception.
					throw new ABAlreadyDownloadedException("Audiobook " + content.getTitle() + " already downloaded.");
				}		
				
				else { 		// if content isn't already in the list, add it.
					audiobooks.add((AudioBook)content);
					System.out.println("Audiobook " + content.getTitle() + " was downloaded successfully."); 
					}
		
		
		
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		//loop runs for however many songs are in the songs list.
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1; 						//create index variable which will keep incrementing by 1 as the loop runs.
			System.out.print("" + index + ". "); 	//formatting line
			songs.get(i).printInfo();		//call the printInfo method on the song in the songs list.
			System.out.println();			//printing empty line.
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		// loop runs for the size of audiobooks. 
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;						//create index variable which will keep incrementing by 1 as the loop runs.
			System.out.print("" + index + ". ");	//formatting line
			audiobooks.get(i).printInfo();		//call the printInfo method on the audiobook in the audiobooks list.
			System.out.println();				//printing empty line.
		}
		
	}
	
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		// loop runs for however many items the playlist list has. 
		for (int i = 0; i < playlists.size(); i++)
		{	
			//same logic as previous listAll methods. 
			int index = i + 1;
			System.out.print("" + index + ". ");
			System.out.print(playlists.get(i).getTitle());
			System.out.println();	
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arraylist is complete, print the artists names
		
		ArrayList<String> artists = new ArrayList<String>();	// created a new arraylist named artists.

		// loop runs for however many songs the songs list has. 
		for (int i = 0; i < songs.size(); i++) {
			if (!artists.contains(songs.get(i).getArtist()))	// check if the artists arraylist already has the artist in it or not.
				artists.add(songs.get(i).getArtist());		// if it doesn't, add the artist to the list.
		}

		// loop runs for the number of artists in the artists list.
		for (int i = 0; i < artists.size(); i++) {
			int ind = i+1;		//create ind variable to keep track of index. Increments by 1 each time loop runs.
			System.out.print("" + ind + ". ");		//formatting line
			System.out.print(artists.get(i));		//print the artist's name.
			System.out.println();					//print empty line.
		}

	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index)
	{
		
		//checking if library is empty. If so, throw an exception with appropriate message. 
		if (songs.size() == 0) {
			
			throw new SongNotFoundException("Library is empty.");	// throw corresponding exception.
		}

		//removing from playlist (if possible) before removing from library so that a return statement doesn't prevent this code from being skipped.
		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getContent().contains(songs.get(index-1)))		//checks if the specific playlist contains the song that is to be deleted.
				playlists.get(i).getContent().remove(songs.get(index-1));		//if it does, remove the song from that playlist.
		}

		//validate the index first. 
		if (index > 0 && index <= songs.size()) {
			songs.remove(index-1); 		// if index is valid (index given is in the range of songs), remove it from the library.
			
		}

		// if none of the above conditions are met, it means that the song index isn't vaild. 
		else {
			throw new IndexNotFoundException("Index not valid.");	// throw corresponding action instead of returning boolean.
		}

	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator());
	
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{

		// creating int to compare the 2 songs.
		public int compare(Song x, Song y) {

			// comparing and returning a specific number based on learning from lecture slides. 
			if (x.getYear() < y.getYear()) 		// if first song (x) is less than second song (y), return -1 meaning they need to switch positions.
				return -1;

			else if (x.getYear() > y.getYear()) 	// if it's opposite from condition above, return 1 which doesn't perform any change.
				return 1;

			return 0; 	// return 0 if they're the same.
		}
		
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort()
		Collections.sort(songs, new SongLengthComparator());
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		// exact same logic as previous Comparator for comparing by year. 
		public int compare(Song x, Song y) {
			if (x.getLength() < y.getLength())
				return -1;

			else if (x.getLength() > y.getLength())
				return 1;

			return 0;
		}	
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code

		Collections.sort(songs);
	}
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		if (index < 1 || index > songs.size()) 	// validate the index given as parameter.
		{
			// if index is invalid, throw the exception. 
			throw new IndexNotFoundException("Invalid index.");	
		}

		//otherwise, call the play method on songs.get(index-1) and then return true. 
		songs.get(index-1).play();
		
	}
	
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		
		if (index < 1 || index > audiobooks.size()) 	// validate index.
		{ 
			throw new IndexNotFoundException("Book not found at this index.");	// if index is invalid, throw exception.
			
		}

		else if (chapter < 1 || chapter > audiobooks.get(index-1).getChapterTitles().size()) 	// check if the chapter given is valid.
		{
			throw new ChapterNotFoundException("Chapter not found");		// if chapter isn't valid, throw exception accordingly.
			
		}

		// if the index and chapter are valid, select the chapter and play it. 
		audiobooks.get(index-1).selectChapter(chapter);
		audiobooks.get(index-1).play();						
		

	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{

		// validate index as we have been doing.
		if (index < 1 || index > audiobooks.size()) {
			throw new IndexNotFoundException("Invalid index.");		// if not valid, throw exception. 
		}

		// if we get to this code, it means index is valid.
		audiobooks.get(index-1).printTOC();		//so, get the audiobook we want from audiobooks list and printTOC.
	
	}


	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{

		// created new playlist instance with the title given.
		Playlist playL = new Playlist(title);

		if (!playlists.contains(playL)) {		// check if the list of playlists already has the new playlist created.
			playlists.add(playL);			// if it doesn't, we add it to the playlists list.
			
		}

		//otherwise, this would mean that a playlist with that name already exists.
		else {
			throw new ExistingPlaylistException("Playlist already exists.");		//throw corresponding exception.
		}

	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{
		
		// running loop for however many playlists there are.
		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().equals(title)) 		// check if the playlist in current iteration is the one the user wants to print info about.
			{
				playlists.get(i).printContents();		// if titles match, print the content of the playlist.
			}
			
			// otherwise, the playlist given doesn't exist. 
			else {
				throw new PlaylistNotFound("Playlist does not exist."); }		// throw exception for this case.
		}

	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{

		// loop runs for number of playlists in the list of playlists.
		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().equals(playlistTitle)) 		// check if the playlist in current loop iteration matches the given based on titles. 
			{
				playlists.get(i).playAll();		// if it does, call playAll on this playlist.
			}

			else
				throw new PlaylistNotFound("Playlist does not exist.");		//throw corresponding exception.
		}

	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		// same loop condition as previous methods. 
		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().equals(playlistTitle)) 		// check if the title of playlist in current iteration is equal to the one specified.
			{

				// check if index is valid or not. 
				if (indexInPL > 0 && indexInPL <= playlists.get(i).getContent().size()) {
					playlists.get(i).play(indexInPL);		//if index is valid, play the playlist specified.
					
				}

				// if index isn't valid, exception will be thrown for this case. 
				else if (indexInPL < 1 || indexInPL > playlists.get(i).getContent().size()) {
					throw new IndexNotFoundException("Invalid index.");	
				}
			}	
			
			// if the title given doesn't match any playlist in the arraylist of playlists: 
			else 
				throw new PlaylistNotFound("Playlist does not exist.");		//throw corresponding exception.
		}
		
	}
	
	// Add a song/audiobook from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{

		// using enhanced for loop. each iteration gets a playlist from the list.
		for (Playlist PL : playlists) {

			if (PL.getTitle().equals(playlistTitle)) 	// check if the current playlist has the same title given as parameter (user's input).
			{

				if (type.equalsIgnoreCase(Song.TYPENAME)) {		// if titles match, check if the content user wants to add is a song.
					if (index < 1 || index > songs.size()) {	// validate index. 
						throw new IndexNotFoundException("Song not found for specified index.");		// if invalid index, throw exception.
					}

					// if index was valid and song isn't already in playlist, then we add the song to the playlist.
					if(!PL.getContent().contains(songs.get(index-1)))
						PL.addContent(songs.get(index-1));
					
					// otherwise, this means song is already in the playlist so throw an exception. 
					else
						throw new SongAlreadyDownloadedException("Song already in playlist.");
				}
				
				// check if the type is an audiobook. if so, the same conditions are checked as above.
				else if (type.equalsIgnoreCase(AudioBook.TYPENAME)) 	//check if the content user wants to add is an audiobook.
				{
					if (index < 1 || index > audiobooks.size()) 
					{	// validate index.
						throw new IndexNotFoundException("Audiobook not found for specified index.");	//throw exception.
					}

					// add the audiobook to the playlist if previous conditions didn't stop the method from continuing.
					if(!PL.getContent().contains(audiobooks.get(index-1)))
					PL.addContent(audiobooks.get(index-1));
					
					else
						throw new ABAlreadyDownloadedException("Audiobook already in playlist.");
				}

			}
			
			// if the main "if" statement isn't met at all, this means there isn't a playlist with the title given by user. 
			else {
				throw new PlaylistNotFound("Playlist does not exist.");		//throw corresponding exception.
			}
		}			

	}

  	// Delete a song/audiobook from a playlist with the given title
	
	public void delContentFromPlaylist(int index, String title)
	{
		
		// using enhanced for loop again. 
		for (Playlist PL : playlists) {

			if (PL.getTitle().equals(title))		// check if playlist title matches the one given as parameter.
			{		

				if (index < 1 || index > PL.getContent().size()) {				// validate index. 
					throw new IndexNotFoundException("Invalid index.");		// if index is invalid, throw exception.
				}

				// if index was valid, we delete the content from playlist.
				PL.deleteContent(index);
			}
			
			// otherwise, this means that the playlist inputted by user doesn't exist. 
			else
				throw new PlaylistNotFound("Playlist does not exist.");		//throw corresponding exception.
		}
	}
}



// Created the custom exceptions as mentioned in the instructions.
class SongAlreadyDownloadedException extends RuntimeException {

	public SongAlreadyDownloadedException() {}

	public SongAlreadyDownloadedException(String msg) 
	{
		super(msg);
	}
}

class ABAlreadyDownloadedException extends RuntimeException {

	public ABAlreadyDownloadedException() {}

	public ABAlreadyDownloadedException(String msg) 
	{
		super(msg);
	}
}

class SongNotFoundException extends RuntimeException {

	public SongNotFoundException() {}

	public SongNotFoundException(String msg) 
	{
		super(msg);
	}
}

class ABNotFoundException extends RuntimeException {

	public ABNotFoundException() {}

	public ABNotFoundException(String msg) 
	{
		super(msg);
	}
}

class ArtistNotFoundException extends RuntimeException {

	public ArtistNotFoundException() {}

	public ArtistNotFoundException(String msg) 
	{
		super(msg);
	}
}

class GenreNotFoundException extends RuntimeException {

	public GenreNotFoundException() {}

	public GenreNotFoundException(String msg) 
	{
		super(msg);
	}
}

class ExistingPlaylistException extends RuntimeException {

	public ExistingPlaylistException() {}

	public ExistingPlaylistException(String msg) 
	{
		super(msg);
	}
}

class PlaylistNotFound extends RuntimeException {

	public PlaylistNotFound() {}

	public PlaylistNotFound(String msg) 
	{
		super(msg);
	}
}

class ChapterNotFoundException extends RuntimeException {

	public ChapterNotFoundException() {}

	public ChapterNotFoundException(String msg) 
	{
		super(msg);
	}
}

class IndexNotFoundException extends RuntimeException {

	public IndexNotFoundException() {}

	public IndexNotFoundException(String msg) 
	{
		super(msg);
	}
}

