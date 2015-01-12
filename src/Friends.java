
	import java.io.*;
	import java.util.*;

	public class Friends
	{
		//Friendship Graph Project - Vincent Xie and Jacob Rizer
		
		static Graph friends;
		
		public static void main(String[] args) throws IOException
		{
			System.out.println("Input file name: ");
			String file = IO.readString();
			while(true)
			{
				if(IO.openFile(file))
				{
					friends = new Graph(file);
					System.out.println();
					break;
				}
				else
				{
					System.out.println("Input a valid file name: ");
					file = IO.readString();
				}
			}
			while (true)
			{
				int choice = getChoice();
				if (choice == 1) // students at a school
				{
					
					HashMap <String, Boolean> connections = new HashMap <String, Boolean>();
					
					System.out.println("Which school do you want to see? ");
					String school = IO.readString().toLowerCase();
					if(!friends.students.containsKey(school))
					{
						System.out.println("Not a valid school");
					}
					else
					{
						System.out.println();
						ArrayList<Person> output = friends.getSchool(school);
						System.out.println(output.size());
						for(int i=0; i<output.size(); i++){
							System.out.println(output.get(i).name + "|" + "y" + "|" + school);	
						}
					 	for(int i = 0; i < output.size(); i++)
					 	{
					 		ArrayList<Integer> studentFriends = output.get(i).friends;
					 		if(studentFriends.size() > 0)
					 		{
					 		
					 			for(int a = 0; a < studentFriends.size(); a++)
					 			{
					 				if(connections.get(friends.adjList[studentFriends.get(a)].name + "|" + output.get(i).name) == null){
					 					connections.put(output.get(i).name + "|" + friends.adjList[studentFriends.get(a)].name, true);
					 					String name = friends.adjList[studentFriends.get(a)].name; 
					 					System.out.println(output.get(i).name + "|" + name);
					 				}
					 				
					 			}
					 			
					 		}
					 		
					 	}
					 }
				}
				else if (choice == 2) // shortest intro chain
				{
					System.out.println("First person:  ");
					String first = IO.readString();
					System.out.println("Second person: ");
					String second = IO.readString();
					System.out.println();
					try
					{
						ArrayList<Person> output = friends.getShortestChain(first, second);
						if(output == null)
						{
							System.out.println("No intro chain");
						}
						else
						{
							for(int i = 0; i < output.size() - 1; i++)
						 	{
						 		System.out.print(output.get(i).name + ", ");
						 	}
						 	System.out.print(output.get(output.size() - 1).name);
						}
						System.out.println();
					} 
					catch(IOException e)
					{
						System.out.println("Bad Input");
					}
					
				}
				else if (choice == 3) // cliques at school
				{
					
					HashMap <String, Boolean> connections = new HashMap <String, Boolean>();
					
					System.out.println("Which school? ");
					String school = IO.readString();
					System.out.println();
					ArrayList<Graph.clique> a = new ArrayList<Graph.clique>();
					a = friends.cliques(school);
					for(int i=0; i<a.size(); i++)
					{
						System.out.println("Clique " + (i+1) + ": ");
						System.out.println();
						System.out.println(a.get(i).clique.size());
						for(int j=0; j<a.get(i).clique.size(); j++)
						{
							System.out.println(a.get(i).clique.get(j).name + "|" + "y" + "|" + school);
						}
						for(int x=0; x<a.get(i).clique.size(); x++){
							ArrayList<Integer> studentFriends = a.get(i).clique.get(x).friends;
					 		if(studentFriends.size() > 0)
					 		{
					 		
					 			for(int z = 0; z < studentFriends.size(); z++)
					 			{
					 				if(connections.get(friends.adjList[studentFriends.get(z)].name + "|" + a.get(i).clique.get(x).name) == null){
					 					connections.put(a.get(i).clique.get(x).name + "|" + friends.adjList[studentFriends.get(z)].name, true);
					 					String name = friends.adjList[studentFriends.get(z)].name; 
					 					System.out.println(a.get(i).clique.get(x).name + "|" + name);
					 				}
					 				
					 			}
					 			
					 		}
						}
						
						System.out.println();
						
					}
				}
				else if (choice == 4) // connectors
				{
					System.out.println();
					friends.connectors();
					
					if(friends.connectors.size() > 0){
					for(int i=0; i<friends.connectors.size() - 1; i++){
						System.out.print(friends.connectors.get(i) + ", ");
					}
					System.out.print(friends.connectors.get(friends.connectors.size() - 1));
					}
					System.out.println();
					
				}
				else if (choice == 5) // Quit
				{
					return;
				}
			}
		}
		public static int getChoice() 
		{
			System.out.println();
			System.out.println("Menu:");
			System.out.println("1. Students at a school");
			System.out.println("2. Shortest intro chain");
			System.out.println("3. Cliques at school");
			System.out.println("4. Connectors");
			System.out.println("5. Quit");
			System.out.println();
			System.out.print("Input 1-5: ");
			int num = IO.readInt();
			while (num < 1 || num > 5) 
			{
				System.out.print("That is not a valid option. Input 1-5. ");
				num = IO.readInt();
			}
			return num;
		}
	}
	
	
	
	
	
	
	

