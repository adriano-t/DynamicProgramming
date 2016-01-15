import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class NonDecreasingSequence implements Exercise
{

	@Override
	public void launch(Scanner scanner)
	{
		System.out.print("Number of elements:");
		int size = scanner.nextInt();
		if(size < 1 || size > 10000)
		{
			System.out.println("Wrong value");
			return;
		}
		
		Random rand = new Random();
		int[] values = new int[size];
		for(int i = 0; i < size; i++)
			values[i] = rand.nextInt(size * size * 2);

		System.out.println(Arrays.toString(values));
		
		int[] sequence =  findSequence(values);
		System.out.println("The sequence lenght is: " + sequence.length);
		System.out.println("The sequence is: " + Arrays.toString(sequence));
	}

	public int[] findSequence(int[] values)
	{
		int[] longest = new int[values.length];
		int[] previous = new int[values.length];
		int biggest = 0;
		for(int i = 0; i < values.length; i++)
		{
			//find the max previous sequence compatible with me
			int max = 0; 
			for(int j = i; j > 0; j--)
			{
				if(values[j] <= values[i])
					if(longest[j] > longest[max]) 
						max = j;				
			}
			
			longest[i] = 1 + longest[max];
			previous[i] = max;
			if(longest[i] > longest[biggest])
			{
				biggest = i;
			}
		}
		
		// recreate the sequence
		int[] sequence = new int[longest[biggest]];
		int i = biggest;
		int j = longest[biggest] - 1;
		while(j > 0)
		{
			sequence[j--] = values[i];
			i = previous[i];
		}
 
		return sequence;
	}
	
	@Override
	public String getTitle() {
		return "Longest non-decreasing sequence";
		
	}

	@Override
	public String getDescription() {
		return "Given a sequence of N numbers – A[1] , A[2] , …, A[N].\n"+
			       "Find the length of the longest non-decreasing sequence.";
	}

}
