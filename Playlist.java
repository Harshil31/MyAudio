package MyAudio;

import java.util.ArrayList;

/*
 * A Playlist contains an array list of AudioContent (i.e. Song, AudioBooks) from the library
 */
public class Playlist
{
	private String title;
	private ArrayList<AudioContent> contents;
	
	public Playlist(String title)
	{
		this.title = title;
		contents = new ArrayList<AudioContent>();
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void addContent(AudioContent content)
	{
		contents.add(content);
	}
	
	public ArrayList<AudioContent> getContent()
	{
		return contents;
	}

	public void setContent(ArrayList<AudioContent> contents)
	{
		this.contents = contents;
	}
	
	/*
	 * Print the information of each audio content object (song, audiobook)
	 * in the contents array list. Print the index of the audio content object first
	 * followed by ". " then make use of the printInfo() method of each audio content object
	 * Make sure the index starts at 1
	 */
	public void printContents()
	{

		for (int i = 0; i < contents.size(); i++)
		{
			int index = i + 1; //incrementing for both formatting and iterating purposes.
			System.out.print("" + index + ". "); //formatting
			contents.get(i).printInfo();
			System.out.println();	

		}
		
	}

	// Play all the AudioContent in the contents list
	public void playAll()
	{
		//using enhanced for loop to get the actual content in arraylist of contents.
		for (AudioContent content : contents) {
			content.play(); 	//play the content.
			System.out.println();
			
		}
	}
	
	// Play the specific AudioContent from the contents array list.
	// First make sure the index is in the correct range. 
	public void play(int index)
	{
		//validate the index. Make sure it's within the range of total content in contents arraylist.
		if (index > 0 && index <= contents.size())
			contents.get(index-1).play(); //if valid, we get the content at index-1 (since indexing starts at 0) and play it.
		
	}
	
	public boolean contains(int index)
	{
		return index >= 1 && index <= contents.size(); 
	}
	
	// Two Playlists are equal if their titles are equal
	public boolean equals(Object other)
	{
		//create an instance of Playlist which is essentially converting other to Playlist.
		Playlist playL2 = (Playlist) other;
		return (this.title.equals(playL2.title)); //check if the playlists are equal by comparing their titles.
	}
	
	// Given an index of an audio content object in contents array list,
	// remove the audio content object from the array list
	// Hint: use the contains() method above to check if the index is valid
	// The given index is 1-indexed so convert to 0-indexing before removing
	public void deleteContent(int index)
	{
		if (!contains(index)) return;
		contents.remove(index-1);
	}
	
	
}
