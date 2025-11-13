package MyAudio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		
		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			// keeping everything in a try block so that any exceptions can be caught after it. 
			try {
				// this is all code from A1 that didn't need to be modified. 
				String action = scanner.nextLine();

				if (action == null || action.equals("")) 
				{
					System.out.print("\n>");
					continue;
				}
				else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;
				
				else if (action.equalsIgnoreCase("STORE"))	// List all songs
				{
					store.listAll(); 
				}
				else if (action.equalsIgnoreCase("SONGS"))	// List all songs
				{
					mylibrary.listAllSongs(); 
				}
				else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
				{
					mylibrary.listAllAudioBooks(); 
				}
				
				else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
				{
					mylibrary.listAllArtists(); 
				}
				else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
				{
					mylibrary.listAllPlaylists(); 
				}

				// Download audiocontent (song/audiobook/podcast) from the store 
				// modified download method. 
				else if (action.equalsIgnoreCase("DOWNLOAD")) 
				{
					// creating 2 variables to hold the from index and to index. 
					int fromIndex = 0;
					int toIndex = 0;

					System.out.print("From Store Content #: ");	// formatting.
					if (scanner.hasNextInt())
					{
						fromIndex = scanner.nextInt();		// take first input. 
						scanner.nextLine(); 
					}

					// if the input wasn't valid (not a number), throw an exception with an appropriate message.
					else
					{	
						scanner.nextLine();
						throw new IndexNotFoundException("Invalid input. Please type a valid index.");
					}

					System.out.print("To Store Content #: ");		// formatting.
					if (scanner.hasNextInt())
					{
						toIndex = scanner.nextInt();		// take second input.
						if(fromIndex > toIndex) {			// check if this second input is less than the first. 
							scanner.nextLine();				// if it is, it's invalid and an exception is thrown with appropriate message.
							throw new IndexNotFoundException("Invalid index. To must be less than From.");
						}
						scanner.nextLine();
					}

					// if the input wasn't a number at all, throw an appropriate exception. 
					else
					{
						scanner.nextLine();
						throw new IndexNotFoundException("Invalid input. Please type a valid index.");
					}

					// run a loop from the first index up to and including the second. 
					for (int i = fromIndex; i <= toIndex; i++) 
					{
						AudioContent content = store.getContent(i); 	// create an AudioContent object and save the first content to it. 
						
						// check if content is null. 
						if (content == null) {
							System.out.println("Content Not Found in Store");		// if so, print an error. 
							continue;
						}
						
						// if no error up till now, try to download the content. 
						try 
							{
								mylibrary.download(content);
							} 
						catch(Exception e)  	// if download doesn't work for any reason, an exception will be caught. 
							{
								System.out.println(e.getMessage());
							}			
					}
					
				}

				// new action DOWNLOADA created. 
				else if (action.equalsIgnoreCase("DOWNLOADA"))
				{
					String artName = "";	// create empty string to hold the artists name (input).

					System.out.print("Artist Name: ");	// formatting.
					if (scanner.hasNext())
					{
						artName = scanner.nextLine();		// save artist inputted. 

						if(store.getArtists().containsKey(artName)) 	// check if the map of artists already has the artist.
						{
							ArrayList<Integer> content_indices = store.getArtists().get(artName);	// if it does, make an arraylist that gets the index of the content made by that artist. 

							// run loop for however many indexes (basically amount of content) there are. 
							for (int i = 0; i < content_indices.size(); i++)
							{
								AudioContent cont = store.getContent(content_indices.get(i)); 	// create AudioContent object which gets the content at the index in the arraylist.
								
								// check if content is null. If it is, print out a message. 
								if(cont == null)
									System.out.println("Content Not Found");

								// if no errors have occurred, try to download the content. 
								try 
								{
									mylibrary.download(cont);
								} 
								catch(Exception e) 	// if try doesn't work, we'll catch an exception. 
								{
									System.out.println(e.getMessage());		// print out the message of whatever exception is caught.
								}
							}
						}
						// if the artist inputted was never found in the map, we throw the corresponding exception and print a msg.
						else 
						{
							throw new ArtistNotFoundException(artName + " not found."); 
						}
					}
				}

				// created new action DOWNLOADG. 
				else if (action.equalsIgnoreCase("DOWNLOADG"))
				{
					// set a Song.Genre genre variable as null. 
					Song.Genre genre = null;

					System.out.print("Genre: ");		// formatting. 
					if (scanner.hasNext())
					{
						String temp = scanner.nextLine();		// taking in the input as a String since we can't cast it as Song.Genre. 

						// use a switch case to check what genre was inputted and then the original genre variable will be set to it. 
						switch(temp) 
						{
							case "POP":
								genre = Song.Genre.POP;
								break;
							case "ROCK":
								genre = Song.Genre.ROCK;
								break;
							case "JAZZ":
								genre = Song.Genre.JAZZ;
								break;
							case "HIPHOP":
								genre = Song.Genre.HIPHOP;
								break;
							case "RAP":
								genre = Song.Genre.RAP;
								break;
							case "CLASSICAL":
								genre = Song.Genre.CLASSICAL;
								break;	
						}	
						
						// check if genre is null (meaning the input didn't match any existing genres). 
						if (genre == null) {
							
							throw new GenreNotFoundException(temp + " not found."); } 	// if so, throw exception. 
							
						// if genre has been set properly, the following happens. 
						else
						{
							// make an arraylist for the indices of the content for this specific genre. 
							ArrayList<Integer> content_indices = store.getGenres().get(genre);

							// run the loop for however many content objects there are.
							for (int i = 0; i < content_indices.size(); i++)
							{
								AudioContent cont = store.getContent(content_indices.get(i));	// create AudioContent object which holds the content at the specific index.
								
								// if the content we just stored in the line above is null, print out accordingly.
								if(cont == null)
									System.out.println("Genre Not Found");

								// if no errors so far, try to download the content of this genre type.
								try 
								{
									mylibrary.download(cont);
								} 
								catch(Exception e) 		// if it doesn't work, exception will be caught and error msg is printed. 
								{
									System.out.println(e.getMessage());
								}
							}
						}
					}	
				}

				// created new SEARCH action. 
				else if (action.equalsIgnoreCase("SEARCH"))
				{
					// initialize string variable "title". 
					String title = "";
					System.out.print("Title: ");		//formatting.
					
					if (scanner.hasNext()) 				//getting user input.
					{			
						title = scanner.nextLine();			//saving the input to the string defined above.

						// check if the titles map contains the title inputted by the user. 
						if (store.getTitles().containsKey(title)) 
						{
								System.out.print(store.getTitles().get(title) + ". ");		// if so, first print the index it's at. 
								store.getContent(store.getTitles().get(title)).printInfo();		// then print it's info. 
						}

						// if it isn't in the map, throw the corresponding exception. 
						else {
							throw new SongNotFoundException("No matches for " + title + " found."); }
							
					}
				}

				// created new  SEARCHA action. 
				else if (action.equalsIgnoreCase("SEARCHA"))
				{
					String art = "";	// created new string variable "art". 

					System.out.print("Artist: ");		//formatting.
					
					if (scanner.hasNext()) 				//getting user input.
					{			
						art = scanner.nextLine();		//saving the input to the string defined above.
						
						// check if the artists map has the artist inputted by user. 
						if (store.getArtists().containsKey(art)) 
						{	
							// if so, get the arraylist containing the indices of content that is made by this artist. 
							ArrayList<Integer> artIndices = store.getArtists().get(art);

							// run loop for however many indices there are. 
							for (int i = 0; i < artIndices.size();i++)
							{
								System.out.print(artIndices.get(i) + ". ");			// print out the index. 
								store.getContent(artIndices.get(i)).printInfo();	// print out the info of the content.
								System.out.println();
								
							}
						}
						
						// if artist isn't found, throw corresponding exception.
						else {
							throw new ArtistNotFoundException("No matches for " + art + " found."); }
					}

				}	

				// created new SEARCHG action.
				else if (action.equalsIgnoreCase("SEARCHG"))
				{
					System.out.print("Genre: ");		// formatting.
					
					Song.Genre genre = null;			// create a Song.Genre variable "genre" initialized to null.

					String temp = "";					// create string variable "temp" initialized to empty string.

					if (scanner.hasNext()) 				
					{	
						temp = scanner.nextLine(); 		// save user input as a string into temp.
						
						// use a switch case to compare temp to the possible genres and set the genre variable accordingly.
						switch(temp) 
						{
							case "POP":
								genre = Song.Genre.POP;
								break;
							case "ROCK":
								genre = Song.Genre.ROCK;
								break;
							case "JAZZ":
								genre = Song.Genre.JAZZ;
								break;
							case "HIPHOP":
								genre = Song.Genre.HIPHOP;
								break;
							case "RAP":
								genre = Song.Genre.RAP;
								break;
							case "CLASSICAL":
								genre = Song.Genre.CLASSICAL;
								break;	
						}	
							
						// if genre remains null, that means an incorrect genre was inputted and an exception must be thrown.
						if (genre == null){
							throw new GenreNotFoundException("No matches for " + temp + " found."); }
						
						// otherwise, do the same process as we did for printing out the content based on artist.
						else
						{
							ArrayList<Integer> genIndices = store.getGenres().get(genre);
							for (int i = 0; i < genIndices.size();i++)
							{
								System.out.print(genIndices.get(i) + ". ");
								store.getContent(genIndices.get(i)).printInfo();
								System.out.println();
							}
						}

					}
				}
					

				// Get the *library* index (index of a song based on the songs list)
				// of a song from the keyboard and play the song 
				else if (action.equalsIgnoreCase("PLAYSONG")) 
				{
					// using same code structure as the one given above for DOWNLOAD action. 
					int index = 0;

					System.out.print("Song number: ");	//formatting. 
					if (scanner.hasNextInt()) {		//get user input.
						index = scanner.nextInt();		//set index to the user input.
						scanner.nextLine();		
					}

					// if calling the playSong method on myLibrary returns false originally, then an error is printed.
					mylibrary.playSong(index);
						

				}
				// Print the table of contents (TOC) of an audiobook that
				// has been downloaded to the library. Get the desired book index
				// from the keyboard - the index is based on the list of books in the library
				else if (action.equalsIgnoreCase("BOOKTOC")) 
				{
					
					// same structure as before.
					int index = 0;

					System.out.print("Audio Book number: ");		//formatting
					if (scanner.hasNextInt()) {			//get user input.
						index = scanner.nextInt();		//save user input to the index variable.
						scanner.nextLine();
					}
					
					// if calling the printAudioBookTOC method on mylibrary returns false, then we print the error. 
					mylibrary.printAudioBookTOC(index);

				}
				// Similar to playsong above except for audio book
				// In addition to the book index, read the chapter 
				// number from the keyboard - see class Library
				else if (action.equalsIgnoreCase("PLAYBOOK")) 
				{

					//create 2 int variables. 
					int ABnum = 0;
					int chap = 0;

					System.out.print("Audio Book number: ");		//formatting.
					if (scanner.hasNextInt()) {			//get first user input. 
						ABnum = scanner.nextInt();		//save that input to first variable.
						scanner.nextLine();
					}

					System.out.print("Chapter: ");		//formatting.
					if(scanner.hasNextInt()) {			//get second user input.
						chap = scanner.nextInt();		//save this second input to second variable. 
						scanner.nextLine();
					}

					// call the playAudioBook method on mylibrary and pass in the 2 variables since that's what the method takes in.
					// after executing code in the Library file and subclasses, if the return is false, print errorMsg.
					mylibrary.playAudioBook(ABnum, chap);
						
					
				}
				
				// Specify a playlist title (string) 
				// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYALLPL")) 
				{

					//created empty string variable.
					String playlist = "";

					System.out.print("Playlist Title: ");		//formatting.
					if (scanner.hasNext()) {			//getting user input.
						playlist = scanner.next();		//saving the input to the string defined above.
						scanner.nextLine();
					}
					
					//calling the playPlaylist method with parameter of playlist variable on mylibrary. 
					mylibrary.playPlaylist(playlist);		
						
				}
				// Specify a playlist title (string) 
				// Read the index of a song/audiobook/podcast in the playist from the keyboard 
				// Play all the audio content 
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYPL")) 
				{
					
					//created 2 variables, one string, one int. (since playPlaylist method takes in 2 parameters)
					String playlist = "";
					int contentNum = 0;

					System.out.print("Playlist Title: ");		//formatting. 
					if (scanner.hasNext()) {			//getting user input (string)
						playlist = scanner.next();		//saving the input.
						scanner.nextLine();
					}

					System.out.print("Content Number: ");
					if(scanner.hasNextInt()) {				//getting 2nd user input (int this time)
						contentNum = scanner.nextInt();		//saving the input to 2nd variable.
						scanner.nextLine();
					}

					// call the playPlaylist method in Library file to do the testing of inputs.
					mylibrary.playPlaylist(playlist, contentNum);		//like always, if false, print error message.

				}
				// Delete a song from the list of songs in mylibrary and any play lists it belongs to
				// Read a song index from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELSONG")) 
				{
					//created int variable.
					int contentNum = 0;

					System.out.print("Library Content Number: ");	
					if(scanner.hasNextInt()) {		//getting user input. 
						contentNum = scanner.nextInt();		//saving it to variable made above.
						scanner.nextLine();
					}
					
					//calling method deleteSong to check if we can delete the song specified. 
					mylibrary.deleteSong(contentNum);		//if method returns false, print error message.
						
				}
				// Read a title string from the keyboard and make a playlist
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("MAKEPL")) 
				{
					//same logic as the previous methods. Just getting different inputs and calling different method to try and perform certain action. 
					String playlist = "";

					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						playlist = scanner.next();
						scanner.nextLine();
					}

					mylibrary.makePlaylist(playlist);
					

				}
				// Print the content information (songs, audiobooks, podcasts) in the playlist
				// Read a playlist title string from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
				{

					//once again, same logic as previous methods. (don't want to make comments repetitive)
					String playlist = "";

					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						playlist = scanner.next();
						scanner.nextLine();
					}

					mylibrary.printPlaylist(playlist);
						
				}
				// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
				// Read the playlist title, the type of content ("song" "audiobook" "podcast")
				// and the index of the content (based on song list, audiobook list etc) from the keyboard
			// see class Library for the method to call
				else if (action.equalsIgnoreCase("ADDTOPL")) 
				{
					//created 3 variables. 2 strings and 1 int. (for 3 different inputs)
					String playlist = "";
					String type = "";
					int index = 0;

					//getting and saving first input.
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						playlist = scanner.next();
						scanner.nextLine();
					}

					//getting and saving 2nd input.
					System.out.print("Content Type [SONG, AUDIOBOOK]: ");
					if(scanner.hasNext()) {
						type = scanner.next();
						scanner.nextLine();
					}

					//getting and saving 3rd input.
					System.out.print("Library Content Number: ");
					if(scanner.hasNextInt()) {
						index = scanner.nextInt();
						scanner.nextLine();
					}

					//calling the addContentToPlaylist method with the 3 variables as parameteres to try and add content to playlist.
					mylibrary.addContentToPlaylist(type,index,playlist);	
				}

				// Delete content from play list based on index from the playlist
				// Read the playlist title string and the playlist index
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELFROMPL")) 
				{

					// same logic as previous method but with 2 variables. 
					String playlist = "";
					int contNum = 0;
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						playlist = scanner.next();
						scanner.nextLine();
					}

					System.out.print("Library Content Number: ");
					if(scanner.hasNextInt()) {
						contNum = scanner.nextInt();
						scanner.nextLine();
					}

					mylibrary.delContentFromPlaylist(contNum, playlist);
				}

				
				else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
				{
					mylibrary.sortSongsByYear();
				}
				else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
				{
					mylibrary.sortSongsByName();
				}
				else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
				{
					mylibrary.sortSongsByLength();
				}

				System.out.print("\n>");

			}	
			
			catch(Exception e) 
			{
				System.out.println(e.getMessage());
				System.out.print("\n>");
			}
			
		}
	}
}

