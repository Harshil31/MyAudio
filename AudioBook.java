package MyAudio;
import java.util.ArrayList;

/*
 * An AudioBook is a type of AudioContent.
 * It is a recording made available on the internet of a book being read aloud by a narrator
 * 
 */
public class AudioBook extends AudioContent
{
	public static final String TYPENAME =	"AUDIOBOOK";
	
	private String author;  
	private String narrator;
	private ArrayList<String> chapterTitles;
	private ArrayList<String> chapters;
	private int currentChapter = 0;

	
	public AudioBook(String title, int year, String id, String type, String audioFile, int length,
									String author, String narrator, ArrayList<String> chapterTitles, ArrayList<String> chapters)
	{
		// Make use of the constructor in the super class AudioContent. 
		// Initialize additional AudioBook instance variables. 

		//basic initializiation using super and then each instance variable.
		super(title,year,id,type,audioFile, length);
		this.author = author;
		this.narrator = narrator;
		this.chapterTitles = chapterTitles;
		this.chapters = chapters;

	}
	
	public String getType()
	{
		return TYPENAME;
	}

  // Print information about the audiobook. First print the basic information of the AudioContent 
	// by making use of the printInfo() method in superclass AudioContent and then print author and narrator
	// see the video
	public void printInfo()
	{

		//basic use of super and just printing the wanted format.
		super.printInfo();
		System.out.println("Author: " + author + " Narrated by: " + narrator);
	}
	
  // Play the audiobook by setting the audioFile to the current chapter title (from chapterTitles array list) 
	// followed by the current chapter (from chapters array list)
	// Then make use of the the play() method of the superclass
	public void play()
	{
		setAudioFile(chapterTitles.get(currentChapter) + ".\n" + chapters.get(currentChapter));
		super.play();
	}
	
	// Print the table of contents of the book - i.e. the list of chapter titles
	// See the video
	public void printTOC()
	{
		int index = 0; //created this variable for formatting purposes. (chapter numbers)
		
		//using enhanced for loop since we're just printing the chapters from a specific audiobook.
		for (String title : chapterTitles) 
		{
			index += 1; //increment index to ensure formatting is right.
			System.out.println("Chapter " + index + ". " + title + "\n"); //print chapter number followed by space and then the chapter's title itself.
			
		}
	}

	// Select a specific chapter to play - nothing to do here
	public void selectChapter(int chapter)
	{
		if (chapter >= 1 && chapter <= chapters.size())
		{
			currentChapter = chapter - 1;
		}
	}
	
	//Two AudioBooks are equal if their AudioContent information is equal and both the author and narrators are equal
	public boolean equals(Object other)
	{

		//created an instance of AudioBook and it casts other to an AudioBook type. 
		//Then, returning a boolean based on the comparison of the 2 audiobooks.
		AudioBook book2 = (AudioBook) other;
		return (super.equals(book2) && (this.author.equals(book2.author)) && (this.narrator.equals(book2.narrator)));
	}
	
	public int getNumberOfChapters()
	{
		return chapters.size();
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getNarrator()
	{
		return narrator;
	}

	public void setNarrator(String narrator)
	{
		this.narrator = narrator;
	}

	public ArrayList<String> getChapterTitles()
	{
		return chapterTitles;
	}

	public void setChapterTitles(ArrayList<String> chapterTitles)
	{
		this.chapterTitles = chapterTitles;
	}

	public ArrayList<String> getChapters()
	{
		return chapters;
	}

	public void setChapters(ArrayList<String> chapters)
	{
		this.chapters = chapters;
	}

}
