import java.util.Scanner;

public class Main
{
	private static Exercise[] exercises;

	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		
		exercises = new Exercise[]{
			new Commands(),
			new NonDecreasingSequence(),
			new Justifier()
		};


		int selected;
		do
		{
			selected = -1;
			do
			{
				System.out.println("0) Exit"); 
				int i = 0;
				for(Exercise ex : exercises) 
					System.out.println((++i) + ") " + ex.getTitle()); 
				

				System.out.print("Choose an option: ");

				if(scanner.hasNextInt())
					selected = scanner.nextInt();
				else
				{
					System.out.println("Error: Insert a numeric value.");
					System.out.println();
					scanner.nextLine();
				}
			}
			while(selected < 0 || selected > exercises.length);
			
	 
			if(selected != 0)
			{
				System.out.println("\nProblem "+ selected);
				System.out.println("---------------------------------------------------------"); 
				System.out.println(exercises[selected - 1].getDescription());
				System.out.println("---------------------------------------------------------");

				scanner.nextLine();
				exercises[selected - 1].launch(scanner);
				System.out.println();
			}
		}
		while(selected != 0);

		System.out.println("Program ended");
		scanner.close();
		
	}
	
	
	 
}
