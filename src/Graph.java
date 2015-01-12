import java.io.*;
import java.util.*;


public class Graph {

	Person[] adjList;
	HashMap<String,ArrayList<String>> students = new HashMap<String,ArrayList<String>>();
	HashMap<String,Integer> peopleMap;
	
	
 
	public Graph(String file) throws FileNotFoundException 
	{
		Scanner sc = new Scanner(new File(file));
		int numPeople = Integer.parseInt(sc.nextLine().trim());
		adjList = new Person[numPeople];
		peopleMap = new HashMap<String, Integer>(numPeople,0.75f);
		int index1, index2;
		String name1, name2;
  
		for(int i=0; i<numPeople; i++)
		{
			Person x;
			boolean a = false;
			String person = sc.nextLine();
			person = person.trim();
			String school = "";
			int firstBar = person.indexOf("|");
			String name = person.substring(0, firstBar); 
			char yesNo = person.charAt(firstBar + 1);
			if(yesNo == 'y')
			{ 
				int secondBar = person.lastIndexOf("|");
				school = person.substring(secondBar + 1);
				a = true;
			}
			if(a == true)
			{
				x = new Person(name, school, i);
			}
			else
			{
				x = new Person(name, i); 
			}
   
			adjList[i] = x;
			peopleMap.put(name, i); 
			
			if(x.school != null) 
			{
				String addSchool = x.school.toLowerCase();
				if(!students.containsKey(addSchool)) {
					ArrayList<String> newSchool  = new ArrayList<String>();
					newSchool.add(name);
					students.put(addSchool, newSchool);
				}
				else
				{
					students.get(addSchool).add(name);
				}
			}
		}
  
		while(sc.hasNext())
		{
			String connection = sc.nextLine().trim(); 
   
			name1 = connection.substring(0, connection.indexOf("|")); 
			name2 = connection.substring(connection.indexOf("|") + 1); 
			
			index1 = peopleMap.get(name1);
			index2 = peopleMap.get(name2);
   
			adjList[index1].friends.add(index2);
			adjList[index2].friends.add(index1);  
		}  
  
	}
  
	public ArrayList<Person> getSchool(String school) 
	{
		
		
		
		if(!students.containsKey(school))
		{
			return new ArrayList<Person>();
		}
		ArrayList<String> list = students.get(school);
		ArrayList<Person> output = new ArrayList<Person>();
		for(int i = 0; i < list.size(); i++)
		{
			String currentName = list.get(i);
			int index = peopleMap.get(currentName);
			Person newPerson = adjList[index];
			output.add(newPerson);
			
			
		}
		for(int i = 0; i < output.size(); i++)
		{
			Person currentPerson = output.get(i);
			for(int a = 0; a < currentPerson.friends.size(); a++)
			{
				Person friend = adjList[currentPerson.friends.get(a)];
				if(friend.school == null)
				{
					currentPerson.friends.remove(a);
					a--;
				}
				else if(!friend.school.equals(school))
				{
					currentPerson.friends.remove(a);
					a--;
				}
			}
		}	 
		return output; 
 }
	
	public class subgraph{
		ArrayList<Person> subgraph;
		boolean[] visited;
		HashMap<Person,Integer> PI;
		
		public subgraph(String school){
			subgraph = getSchool(school);
			visited = new boolean[subgraph.size()];
			PI = new HashMap<Person,Integer>();
			
			for(int i=0; i<subgraph.size(); i++){
				PI.put(subgraph.get(i), i);
			}
		}
		
	}
	
	public class clique{
		 ArrayList<Person> clique;
		 
		 public clique(){
			 clique = new ArrayList<Person>();
		 }
	}
	
	public ArrayList<clique> cliques(String school){
		
		ArrayList<clique> a = new ArrayList<clique>();
		school = school.toLowerCase();
		
		if(!students.containsKey(school))
		{
			return a;
		}
		
		subgraph s = new subgraph(school);
		
		
		
		while(true){
			for(int i=0; i<s.visited.length; i++){
				if(s.visited[i] == false){
					clique z = new clique();
					a.add(bfs(i, s, z, school));
				}
				
			}
			
			break;
		}
		
		return a;
		
		
		
		
	}
	
	public clique bfs(int i, subgraph s, clique z, String school){
		s.visited[i]=true;
		z.clique.add(s.subgraph.get(i));
		for(int j=0; j<s.subgraph.get(i).friends.size(); j++){
			if(adjList[s.subgraph.get(i).friends.get(j)].school.equalsIgnoreCase(school) && s.visited[(s.PI.get(adjList[s.subgraph.get(i).friends.get(j)]))] == false)
			z = bfs(s.PI.get(adjList[s.subgraph.get(i).friends.get(j)]), s, z, school);
		}
		
		return z;
		
	}
	
	public ArrayList<Person> getShortestChain(String personOne, String personTwo) throws IOException
	{
		ArrayList<Person> reverse = new ArrayList<Person>();
		boolean[] isVisited = new boolean[adjList.length];
		Queue<Integer> queue = new LinkedList<Integer>();
		for(int i = 0; i < isVisited.length; i++)
		{
			isVisited[i] = false;
		}
		HashMap<Integer,Integer> previous = new HashMap<Integer,Integer>();
		Integer first = peopleMap.get(personOne.toLowerCase());
		Integer second = peopleMap.get(personTwo.toLowerCase());
		if(first == null || second == null || first == second)
		{
			throw new IOException();
		}
		int currentPerson = first;
		previous.put(currentPerson, first);
		queue.add(currentPerson);
		isVisited[currentPerson] = true;
		while(!queue.isEmpty()) 
		{
			currentPerson = queue.remove().intValue(); 
			if(currentPerson == second) 
			{
				break;
			} 
			else 
			{
				for(int i : adjList[currentPerson].friends) 
				{
					if(!isVisited[i]) 
					{
						queue.add(i);
						isVisited[i] = true;
						previous.put(i, currentPerson);
					}
				}
			}
		}
		if(currentPerson != second) {
			return null;
		}		
		for(int i = second; i != first; i = previous.get(i)) 
		{ 
			reverse.add(adjList[i]);
		}
		reverse.add(adjList[first]);
		ArrayList<Person> path = new ArrayList<Person>();
		for(int i = reverse.size() - 1; i >= 0; i--)
		{
			path.add(reverse.get(i));
		}
		return path;
	}
	
	ArrayList<String> connectors = new ArrayList<String>();
	int start = 0;
	int dfs = 0;
 
	public void connectors() {
		for(int i = 0; i < adjList.length; i++) {
			Person v = adjList[i];
			if(!v.visited && v.friends.size() == 1) {
				dfs = 0;
				start = i;
				DFS(v);
			}
		}
		
	}

	private void DFS(Person p) {
			dfs++;
			p.back = dfs;
			p.dfsNum = dfs;
			p.visited = true;

			for(int pNum : p.friends){
				Person w = adjList[pNum];
				if(!w.visited) {
					DFS(w);
						if(p.dfsNum <= w.back) {
							if(p.friends.size() <= 1) continue;
							else if(!connectors.contains(p.name)) connectors.add(p.name); 
						}
					if(p.dfsNum > w.back)
						p.back = Math.min(p.back, w.back);
					} else p.back = Math.min(p.back, w.dfsNum);
						
			}
		}
	
	
	
	
	
	
	
  public static void main(String[] args) throws IOException
  {
	  Graph test = new Graph("Test.txt");
	  for(int i = 0; i < test.adjList.length; i++)
	  {
		  System.out.print(test.adjList[i] + " ------- ");
		  for(int a = 0; a < test.adjList[i].friends.size(); a++)
		  {     
			  System.out.print(" " + test.adjList[test.adjList[i].friends.get(a)].name);
		  }
		  System.out.println();
	  }
  	}
}