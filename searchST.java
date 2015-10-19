import java.util.*;

/**
 * This is the Graph class which is used to construct graphs 
 * 
 * @author Niharika Bandla
 *
 */
class Graph {
	Map<Integer, LinkedHashSet<Integer>> links = new HashMap();
	public void addNode(Integer node1, Integer node2) {
		LinkedHashSet<Integer> adjacent = links.get(node1);
		if(adjacent==null) {
			adjacent = new LinkedHashSet();
			links.put(node1, adjacent);
		}
		adjacent.add(node2);
	}
	
	/**
	 * Method to add the undirected graph with two way nodes 
	 * 
	 * @param node1 vertex 1
	 * @param node2 vertex 2
	 */
	public void addUndirected(Integer node1, Integer node2) {
		addNode(node1, node2);
		addNode(node2, node1);
	}

	/**
	 * Method to check if they are connected  
	 * 
	 * @param node1 vertex 1
	 * @param node2 vertex 2
	 */
	public boolean isConnected(String node1, String node2) {
		Set adjacent = links.get(node1);
		if(adjacent==null) {
			return false;
		}
		return adjacent.contains(node2);
	}

	/**
	 * Method to add the undirected graph to calculate the adjacent nodes  
	 * 
	 * @param last present node 
	 */
	public LinkedList<Integer> adjacentNodes(Integer last) {
		LinkedHashSet<Integer> adjacent = links.get(last);
		if(adjacent==null) {
			return new LinkedList();
		}
		return new LinkedList<Integer>(adjacent);
	}
}

/**
 * This class is to find the common shortest path between the different graphs
 * if there is no common shortest path it will print none
 * 
 * @author Niharika Bandla 
 */
class searchST{
	static int s,t,n,m;
	static ArrayList<ArrayList<String>> allPaths;
	static  ArrayList<String> temp;
	
	/**
	 * Method to calculat the shortest path using bfs 
	 * 
	 * @param graph
	 * @param visited
	 */
	private void breadthFirst(Graph graph, LinkedList<Integer> visited) {
		LinkedList<Integer> nodes = graph.adjacentNodes(visited.getLast());

		for (int node : nodes) {
			if (visited.contains(node)) {
				continue;
			}
			if (node==t) {
				visited.add(node);
				CalcPath(visited,temp);
				visited.removeLast();
				break;
			}
		}
		//loop to use bfs to find all the paths 
		for (int node : nodes) {
			if (visited.contains(node) || node==t) {
				continue;
			}
			visited.addLast(node);
			breadthFirst(graph, visited);
			visited.removeLast();
		}
	}
	private void CalcPath(LinkedList<Integer> visited,ArrayList<String> temp) {
		String str="";
		for (int node : visited) {
			str+=node;
		}
		if(!str.isEmpty())
			temp.add(str);
	}


	private static Random rand = new Random();
	static int vertices, edges,v,e;
	static int[][] adj;
	private boolean isWeighted, isDirected;

	/**
	 * This is the random input generator which selects the number of inputs
	 * 
	 * @param isWeighted to check whether we are generating a weighted graph 
	 * @param isDirected to check whether we are using a directed or undirected graph
	 */
	public void inputGenerator(boolean isWeighted,boolean isDirected,Graph g) {
		adj = new int[v][v];
		this.vertices = v;
		this.isWeighted = isWeighted;
		this.isDirected = isDirected;
		this.edges = isDirected ? Math.min(e, v * (v - 1)) : Math.min(e,v* (v - 1) / 2);
		int count = 0;
		while (count < edges) {
			int v1 = rand.nextInt(vertices);
			int v2 = v1;
			while (v2 == v1) {
				v2 = rand.nextInt(vertices);
			}
			if (addtoList(v1, v2,g))
				count += 1;
		}
	}
	/**
	 * This method is to add the edges into list inorder to send these input 
	 * graphs 
	 * 
	 * @param v1 vertex 1 
	 * @param v2 vertex 2
	 * 
	 * @return returns boolean value if an edge is added if not false 
	 */
	private boolean addtoList(int v1, int v2,Graph g2) {
		if (adj[v1][v2] == 0 && adj[v2][v1]==0) {
			int e = 1;
			if (isWeighted) {
				e += rand.nextInt(edges * edges / 2);
			}
			adj[v1][v2] = e;
			g2.addUndirected(Integer.parseInt(v1+""), Integer.parseInt(v2+""));
			if (!isDirected){
				adj[v2][v1] = e;
			}
			return true;
		}
		return false;
	}

	/**
	 * This method is to print the graph that has been generated 
	 */
	public void genGraphInput() {
		System.out.println(vertices + " " + edges);
		for (int i = 0; i < adj.length; i++) {
			for (int j = isDirected ? 0 : i; j <adj.length;j++){
				if (adj[i][j] != 0) {
					System.out.println(i + " " + j
							+
							((isWeighted) ? " " + adj[i][j] : ""));
				}
			}
		}
	}
	static Graph g[];
	
	/**
	 * this is the main method to find the common shortest path 
	 * 
	 * @param args no command line arguments 
	 */
	public static void main(String [] args){
		searchST obj= new searchST(); 
		System.out.println("Enter number of graphs");
		Scanner ss= new Scanner(System.in);
		int numOfGraphs=ss.nextInt();
		g= new Graph[numOfGraphs];
		allPaths= new ArrayList<ArrayList<String>>(numOfGraphs);
		System.out.println("Enter number of nodes");
		n=ss.nextInt();
		v=n;
		System.out.println("Enter source and destination");
		s=ss.nextInt();
		t=ss.nextInt();
		e=Math.abs(rand.nextInt(20));
		System.out.println(e);
		for(int i=0;i<numOfGraphs;i++){
			g[i]=new Graph();
			obj.inputGenerator(false, false, g[i]);
		}
		for(int i=0;i<numOfGraphs;i++){
			System.out.println(g[i].links);
		}
		
		//to find the bfs for all the graphs 
		for(int i=0;i<numOfGraphs;i++){
			LinkedList<Integer> visited = new LinkedList();
			visited.add(s);
			temp= new ArrayList<String>();
			obj.breadthFirst(g[i], visited);
			for(int j=0;j<temp.size()-1;j++){
				for(int k=0;k<(temp.size()-j-1);k++){
					if(temp.get(k).length()>temp.get(k+1).length()){
						String temp2=temp.get(k);
						temp.set(k, temp.get(k+1));
						temp.set(k+1, temp2);
					}
				}
			}
			allPaths.add(obj.temp);
		}
		for(int i=0;i<allPaths.size();i++){
			System.out.println(allPaths.get(i));
		}
		List<String> tempList=allPaths.get(0);
		String finalPath="";
		//find the common path 
		for(int i=0;i<tempList.size();i++){
			boolean found=false;
			for(int j=1;j<allPaths.size();j++){
				List<String> insideList=allPaths.get(j);
				for(int k=0;k<insideList.size();k++){
					if(tempList.get(i).equals(insideList.get(k))){
						found=true;
						break;
					}
					found=false;
				}
				if(found==false)
					break;
			}
			if(found==true|| numOfGraphs==1){
				finalPath=tempList.get(i);
				break;
			}
		}
		System.out.println();
		if(!finalPath.isEmpty())
			System.out.println(finalPath+" Final Path");
		else
			System.out.println("No common Shortest Path");
	}
}