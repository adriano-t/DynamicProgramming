import java.util.Arrays;
import java.util.Scanner;

public class Commands implements Exercise
{
	private int[] costs = { 3, 8, 8, 3, 5, 1};
	private String source;
	private String dest;
	private int[][] cache;
	private int[][] commands;
	
	private int iterations;
	
	@Override
	public void launch(Scanner scanner)
	{
		System.out.print("Insert the word to transform: ");
		source = scanner.nextLine();
		if(source.length() == 0)
			source = "Hello, this is a test";
		
		System.out.println("In which word do you want to transform '" + source + "'?");
		System.out.print("Insert the target word: ");
		dest = scanner.nextLine();
		if(dest.length() == 0)
			dest = "Hello, is this a etts?";

		commands = new int[source.length() + 1][dest.length()];
		
		cache = new int[source.length() + 1][dest.length()];
		for(int i = 0; i < source.length() + 1; i++)
			for(int j = 0; j < dest.length(); j++)
			{
				cache[i][j] = Integer.MAX_VALUE;
				commands[i][j] = Integer.MAX_VALUE;
			}
				
		iterations = 0;
		int w = calculateWeight(0, 0, 0);
		System.out.println("The min cost is: " + w + "\nCalculated in " + iterations + " iterations");
		System.out.println("Expected iterations: " + (source.length() * dest.length()));


		System.out.println("\nThe cache matrix is");
		for(int i = 0; i < source.length() + 1; i++)
		{
			for(int j = 0; j < dest.length(); j++)
			{
				if(cache[i][j] == Integer.MAX_VALUE )
					System.out.print("    ");
				else
					System.out.print(String.format("%3d", cache[i][j]) + " ");
			}
			System.out.println();
		}
		

		int i = 0, j = 0;
		System.out.println("\nThe best command sequence is");
		
		do
		{
			switch(commands[i][j])
			{
			case 0:
				System.out.println("copy");
				i++;
				j++;
				break;
			case 1:
				System.out.println("replace");
				i++;
				j++;
				break;
			case 2:
				System.out.println("delete");
				i++;
				j++;
				break;
			case 3:
				System.out.println("insert");
				j++;
				break;
			case 4:
				System.out.println("swap");
				i += 2;
				j += 2;
				break;
			default:
				System.out.println("kill");
				break;
			}
		}
		while(i < source.length() + 1 &&  j < dest.length() && commands[i][j] != Integer.MAX_VALUE);
		
		
	}
	

	public int calculateWeight(int srcCursor, int dstCursor, int baseCost)
	{
		if(dstCursor == dest.length())
			return baseCost;

		if(cache[srcCursor][dstCursor] != Integer.MAX_VALUE)
			return baseCost + cache[srcCursor][dstCursor];
		
		iterations++;
		
		int[] values = new int[5];
		for(int i = 0; i < 5; i++)
			values[i] = Integer.MAX_VALUE;
		
		if(srcCursor < source.length())
		{
			//copy
			if(source.charAt(srcCursor) == dest.charAt(dstCursor))
				values[0] = calculateWeight(srcCursor + 1, dstCursor + 1, costs[0]);
			
			//replace
			values[1] = calculateWeight(srcCursor + 1, dstCursor + 1, costs[1]);
			
			//delete
			values[2] = calculateWeight(srcCursor + 1, dstCursor + 1, costs[2]);
		}
		
		//insert
		values[3] = calculateWeight(srcCursor, dstCursor + 1, costs[3]);

		//twiddle
		if(srcCursor < source.length() - 1 && dstCursor < dest.length() - 1
		&& source.charAt(srcCursor + 1) == dest.charAt(dstCursor)
		&& source.charAt(srcCursor) == dest.charAt(dstCursor + 1))
			values[4] = calculateWeight(srcCursor + 2, dstCursor + 2, costs[4]);
		

		int minCost = Integer.MAX_VALUE;
		int min = 0;
		for(int i = 0; i < 5; i++)
			if(values[i] < minCost)
			{
				min = i;
				minCost = values[i];
			}

		cache[srcCursor][dstCursor] = minCost;
		commands[srcCursor][dstCursor] = min;
		
		return baseCost + minCost;
	}

	@Override
	public String getTitle() 
	{
		return "Text editing commands";
	}

	@Override
	public String getDescription()
	{
		return "Write an algorithm that transforms the given string1 to string2 \n"+
				"using the following commands with generic weights \n" +
				"- copy: keep the pointed character \n" +
				"- replace(x): replace the current character with x \n" +
				"- delete: deletes the current character \n" +
				"- insert(x): adds x at cursorpos - 1\n" +
				"- swap: swaps the current character with the next one \n" +
				"- kill stops the command sequence and discard the following \n";
	}
}

/*
 * asdfjwdjifjweiofwjefiowjefiwoejfwioejfwioefiowjeiofjwefioweofwioejofweiofowefoweoifwoefwoefwoewefwefiowefwioewioefji
 * wioejfowefwioefowefwefwefwefwefwefwefwefwefwefwoejfopwkfpoqkopfkqefpqiejfwioejfiowjefoiwjeoifjowiejoifjwiefiowioij
 * 
 * */
 