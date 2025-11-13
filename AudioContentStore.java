package MyAudio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


// Simulation of audio content in an online store


public class AudioContentStore
{
	
	private static ArrayList<AudioContent> contents;		// create the ArrayList that will hold the AudioContent objects.
	private Map<String, Integer> titles;					//making the map for titles.
	private Map<String, ArrayList<Integer>> artists;		//making the map for artists.
	private Map<Song.Genre, ArrayList<Integer>> genres;		//making the map for genres.

	//Contructor. 
	public AudioContentStore()
	{
		//Everything in a try block as per instructions. 
		try
		{
			// set the previously made variables as new instances of arraylist / treemap. 
			contents = new ArrayList<AudioContent>();
			titles = new TreeMap<String, Integer>();
			artists = new TreeMap<String, ArrayList<Integer>>();
			genres = new TreeMap<Song.Genre, ArrayList<Integer>>();
			
			// call the private method we make below and set contents to whatever it returns.
			contents = readFile();

			// first I simply add the titles of the content to the titles map with a corresponding index starting from 1.
			for (int i  = 0; i < contents.size(); i++)
			{
				titles.put(contents.get(i).getTitle(), i+1);
			}
			

			// now using a new loop with the same range as before (contents.size()). 
			for (int i = 0; i < contents.size(); i ++)
			{
				if (contents.get(i).getType().equalsIgnoreCase("SONG")) 	// check if the content pulled is a Song. 
				{	
					// if so, create a Song object of that content. (only way to use .getArtist() and .getGenre())
					Song currentSong = (Song) contents.get(i);
					String artist = currentSong.getArtist();		// save the artist.
					Song.Genre genre = currentSong.getGenre();		// save the genre.


					// check if the artists map contains the artist already. 
					if (!artists.containsKey(artist)) 		// if it doesn't then the following happens.
					{
						ArrayList<Integer> artistIndices = new ArrayList<Integer>();	// create new ArrayList.
						artistIndices.add(i+1);					// add the index of the current song to this new ArrayList.
						artists.put(artist, artistIndices);		// put the artist and this ArrayList into the artists map. 
					}

					// if the artist is already in the map then: 
					else
					{
						artists.get(artist).add(i+1);		// simply add the current index to the arraylist and updates it in the map.
					}

					// check if the genre map contains the genre already. same process as above.
					if(!genres.containsKey(genre))
					{
						ArrayList<Integer> genreIndices = new ArrayList<Integer>();
						genreIndices.add(i+1);
						genres.put(genre, genreIndices);
					}

					// in the case that it does, we just update the ArrayList for the specific genre. 
					else
					{
						genres.get(genre).add(i+1);
					}
				}

				// check if the current content is an audiobook.
				if (contents.get(i).getType().equalsIgnoreCase("AUDIOBOOK"))
				{	
					AudioBook currentAB = (AudioBook) contents.get(i);		// if it is, we create an instance of AudioBook and cast the content as an AB.
					String author = currentAB.getAuthor();		// save the author into a variable. 

					// check if the artists/authors map has this author already. 
					if (!artists.containsKey(author)) 
					{
						// if it doesn't, we create a new ArrayList. 
						ArrayList<Integer> artistIndices = new ArrayList<Integer>();
						artistIndices.add(i+1);		// add to the ArrayList the index of the current content. 
						artists.put(author, artistIndices);		// put this author and the arraylist as its value in the map. 
					}

					// if author already exists, just update it's list with the newest index being added to it. 
					else
					{
						artists.get(author).add(i+1);
					}
				}
			}
		}

		// catch any input/output exceptions after the try block.
		catch (IOException e) 
		{	
			// print message and exit based on instructions. 
			System.out.println(e.getMessage());
			System.exit(1);
		}

	}

	// created a private method that throws IOException as per instructions. 
	private static ArrayList<AudioContent> readFile() throws IOException
	{	
		// create an arraylist of audiocontent objects. 
		ArrayList<AudioContent> contentItems = new ArrayList<AudioContent>() ;
		File file = new File("store.txt");		// open the file store.txt. 
		
		// create a scanner for the content of the file. 
		Scanner scan = new Scanner(file);

		// run a while loop that ends when all the lines in the file have been run through. 
		while(scan.hasNextLine())
		{
			// the first line for each iteration contains the type of content so save that in a variable. 
			String type = scan.nextLine();

			// first check if the type is a song. 
			if (type.equals("SONG"))
			{	
				// if it is, create the necessary variables that hold the different attributes of a song. 
				String id = scan.nextLine();
				String title = scan.nextLine();
				int year = scan.nextInt();
				scan.nextLine();
				int length = scan.nextInt();
				scan.nextLine();
				String artist = scan.nextLine(); 
				String composer = scan.nextLine();
				
				// set genre as null since we can't cast a string as a Song.Genre.
				Song.Genre genre = null;

				// using a switch case to determine what genre is given in the file and setting the variable accordingly. 
				switch(scan.nextLine()) 
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

				int linesOfLyrics = scan.nextInt(); 	// taking in number of lyrics. 
				scan.nextLine();
				String lyrics = "";		// initializing lyrics variable. 

				for (int i = 0; i < linesOfLyrics; i ++) 
				{
					lyrics += scan.nextLine() + "\n";		// save the lines of lyrics in the variable. 
				}

				// create a new Song object and pass in all the paramaters it requires that we create.
				Song song = new Song(title, year, id, type, lyrics, length, artist, composer, genre, lyrics);
				contentItems.add(song);		// add this song object to the ArrayList of audiocontent objects created at the top.

			}

			// check if content is an audiobook. 
			if (type.equals("AUDIOBOOK"))
			{
				// if it is, create necessary variables that hold the different attributes of an audiobook. 
				String id = scan.nextLine();
				String title = scan.nextLine();
				int year = scan.nextInt();
				scan.nextLine();
				int length = scan.nextInt();
				scan.nextLine();
				String author = scan.nextLine(); 
				String narrator = scan.nextLine();
				int numOfChapters = scan.nextInt();
				scan.nextLine();

				// create 2 arraylists of string type to hold the chapter titles and content of each.
				ArrayList<String> chapterTitles = new ArrayList<String>();
				ArrayList<String> chapterContent = new ArrayList<String>();

				// run loop to add all the chapter titles to its corresponding arraylist.
				for (int i = 0; i < numOfChapters; i ++) 
				{
					chapterTitles.add(scan.nextLine());
				}

				// new loop for however many chapters there are. 
				for (int i = 0; i < numOfChapters; i++) {
					int numOfLines = scan.nextInt();
					scan.nextLine();

					String line = "";		// initialize a String variable. 
					for (int j = 0; j < numOfLines; j++) 	// new loop for however many lines each chapter has. 
					{
						line += scan.nextLine();		// add the line to the String variable. 
						
					}
				
				// then, once all the content of that chapter has been saved, add it to chapterContent arraylist.
				chapterContent.add(line);

				}

				// create a new Audiobook object and pass in all the paramaters it requires that we create.
				AudioBook AB = new AudioBook(title, year, id, type,"", length, author, narrator, chapterTitles, chapterContent);
				contentItems.add(AB); 		// add the object to the arraylist of audiocontent objects. 
			}		
		}
		scan.close(); 	// close the scanner since we no longer need to run through the file. 
	
	// return the list of audiocontent objects and then this method ends.
	return contentItems;

}
		
	// method exists from A1. 
	public AudioContent getContent(int index)
	{
		if (index < 1 || index > contents.size())
		{
			return null;
		}
		return contents.get(index-1);
	}
	
	// method exists from A1. 
	public void listAll()
	{
		for (int i = 0; i < contents.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			contents.get(i).printInfo();
			System.out.println();
		}
	}

	// made 3 new methods that simply return the titles, artists and genres maps. (required since we use them in MyAudioUI.)
	public Map<String, Integer> getTitles()
	{
		return this.titles;
	}

	public Map<String, ArrayList<Integer>> getArtists()
	{
		return this.artists;
	}

	public Map<Song.Genre, ArrayList<Integer>> getGenres()
	{
		return this.genres;
	}

}