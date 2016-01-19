
import java.util.Scanner;
import java.nio.CharBuffer;

public class Justifier implements Exercise
{
	String[] words;
	int[] path;
	int[] costs;
	int charPerLine = 50;
	
	public void launch(Scanner scanner)
	{
		System.out.print("Insert your text:");
		String line = scanner.nextLine();
		System.out.println(justify(line, charPerLine));
	}
	
	private int calculateMinCost(int startWord)
	{
		if(startWord >= words.length)
			return Integer.MAX_VALUE;
		
		if(costs[startWord] != Integer.MAX_VALUE)
			return costs[startWord];
		
		int wordsCount = 2;
		int minCost = Integer.MAX_VALUE;
		int length;
		
		while((length = lineLength(startWord, startWord + wordsCount)) <= charPerLine)
		{
			int endWord = startWord + wordsCount; 
			int cost = lineCost(startWord, endWord, charPerLine - length);
			int remaining =  calculateMinCost(endWord + 1);
			
			if(cost + remaining < minCost) 
				minCost = cost + remaining;
			
			wordsCount++;
		}

		path[startWord] = wordsCount;
		costs[startWord] = minCost;
		return minCost;
	}
	

	/**
	 * Creates a string of spaces that is 'spaces' spaces long.
	 *
	 * @param spaces The number of spaces to add to the string.
	 */
	private String generateSpaces(int spaces)
	{
		return CharBuffer.allocate( spaces ).toString().replace( '\0', ' ' );
	}
	
	/**
	 * Justify the given text
	 * @param text the text to justify
	 * @param lineLength the length of the line in characters
	 * @return the justified text
	 */
	public String justify(String text, int lineLength)
	{
		charPerLine = lineLength;
		
		words = text.split("\\s+");
		
		if(words.length < 2)
			return text;
		
		path = new int[words.length];
		costs = new int[words.length];
		for(int i = 0; i < costs.length; i++)
			costs[i] = Integer.MAX_VALUE;
		
		// get the path with the minimum cost
		calculateMinCost(0);
		String result = "";
		
		// reconstruct the text
		int pos = 0;
		while(pos + path[pos] < path.length)
		{
			int count = path[pos];
			int length = wordsLength(pos, pos + count);
			int spaces = charPerLine - length;
			if(count - 1 != 0)
			{
				int s = spaces / (count - 1);
				int r = spaces % (count - 1);
				int i;
				for(i = pos; i < pos + (count - 1); i++)
				{
					if(r > 0)
					{
						r--;
						result += words[i] + generateSpaces(s + 1);
					}
					else 
						result += words[i] + generateSpaces(s);
				}
				result += words[i];
			}
			pos += count;
			result += "\n";
		} 
		return result;

	}
	
	private int wordsLength(int start, int end)
	{
		int result = 0;
		for(int i = start; i < end; i++)
			result += words[i].length();
		return result;
	}
	
	private int lineLength(int start, int end)
	{
		if (start > end  || start < 0 || end >= words.length)
			return Integer.MAX_VALUE;

		int result = 0;
		for(int i = start; i <= end; i++)
			result += words[i].length() + 1;
		result -= 1;//remove the last space
		return result; 		
	}
	
	private int lineCost(int start, int end, int extraSpaces)
	{
		int count = end - start - 1;
		int[] spaces = new int[count];
		for(int i = 0; i < count; i++)
			spaces[i] = 1;
		
		int i = 0;
		while(extraSpaces > 0)
		{
			spaces[i]++;
			i = (i + 1) % count;
			extraSpaces--;
		}
		
		int cost = 0;
		for(int s : spaces) 
			cost += costFunction(s); 
		return cost;
	}
	
	private int costFunction(int spaces)
	{
		return spaces * spaces * spaces;
	}
	
	public String getTitle()
	{
		return "Justify text";
	}

	public String getDescription()
	{
		return " Wraps a line of text at specified length and justifies the" +
				"output on all but the last line.";
	}

}