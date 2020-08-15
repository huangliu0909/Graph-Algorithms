import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Graph {
	
	int[][] edges;//邻接矩阵
	int[] vertex;
	Queue<Integer> queue4BFS;
	Stack<Integer> stack4DFS;
	int[] minPath;
	
	Graph(int n){
		edges = new int[n][n];
		vertex = new int[n];
		for(int i = 0; i < n; i ++) {
			vertex[i] = -1;
			for(int j = 0; j < n; j ++)
				edges[i][j] = -1;
		}
	}
	
	void addEdge(int n, int m, int w) {
		edges[n][m] = w;
		edges[m][n] = w;
	}
	
	void DFS() {
		stack4DFS = new Stack<Integer>();
		for(int i = 0; i < edges.length; i ++) 
			vertex[i] = -1;
		//start from 0
		stack4DFS.push(0);
		int[] res = new int[edges.length];
		int count = 0;
		while(!stack4DFS.isEmpty()) {
			int now = stack4DFS.pop();
			if(vertex[now] == -1) {
				vertex[now] = 1;
				res[count] = now;
				count ++;
				for(int i = 0; i < edges.length; i ++) {
					if(edges[now][i] > 0) {
						stack4DFS.push(i);
					}
				}
			}
		}
		System.out.print(Arrays.toString(res) + "\n");
	}
	
	void BFS() {
		queue4BFS = new LinkedList<Integer>();
		for(int i = 0; i < edges.length; i ++) 
			vertex[i] = -1;
		//start from 0
		queue4BFS.add(0);
		vertex[0] = 1;
		int[] res = new int[edges.length];
		int count = 0;
		while(!queue4BFS.isEmpty()) {
			int now = queue4BFS.poll();
			res[count] = now;
			//System.out.print(Arrays.toString(edges[now]));
			count ++;
			for(int i = 0; i < edges.length; i ++) {
				if(edges[now][i] > 0 && vertex[i] == -1) {
					vertex[i] = 1;
					queue4BFS.add(i);
				}
			}
		}
		System.out.print(Arrays.toString(res) + "\n");
	}
	
	void topological_sort() {
		for(int i = 0; i < edges.length; i ++) 
			vertex[i] = -1;
		int[] res = new int[edges.length];
		int count = 0;
		while(count != edges.length) {
			for(int i = 0; i < edges.length; i ++) {
				if(vertex[i] == -1) {
					int flag = 0;
					for(int j = 0; j < edges.length; j ++) {
						if(edges[j][i] != Integer.MAX_VALUE) {
							flag = 1;
							break;
						}
					}
					if(flag == 0) { //找到入度为0的结点i
						res[count] = i;
						vertex[i] = 1; //标记为已访问
						break;
					}
				}
			}
			for(int i = 0; i < edges.length; i ++) 
				edges[res[count]][i] = Integer.MAX_VALUE;
			count ++;
		}
		System.out.println(Arrays.toString(res));
	}
	
	int find_set(int[] p, int x) {
		if(p[x] == x) return x;
		return find_set(p, p[x]);
	}
	
	void union(int[] p, int x1, int x2) {
		int s1 = find_set(p, x1);
		int s2 = find_set(p, x2);
		p[s2] = s1; 
	}
	
	int[][] arrAppend(int[][] arr, int[] value) {
        arr = Arrays.copyOf(arr, arr.length + 1);
        arr[arr.length - 1] = value;
        return arr;
    }
	
	class edgeComp implements Comparator<int[]> 
	{ 
	    // Used for sorting in ascending order of 
	    // roll number 
	    public int compare(int[] a, int[] b) 
	    { 
	        return a[2] - b[2]; 
	    } 
	} 
	
	void Kruskal() {
		for(int i = 0; i < vertex.length; i ++) vertex[i] = i; //每个结点的父节点都初始化为自己
		int[][] e = new int[0][3]; //将边整理为（u，v，w）的形式
		for(int i = 0; i < edges.length; i ++) {
			for(int j = i + 1; j < edges.length; j ++) {
				if(edges[i][j] < Integer.MAX_VALUE)
					e = arrAppend(e, new int[] {i, j, edges[i][j]});
			}
		}
		 Arrays.sort(e, new edgeComp()); 
		 for(int i = 0; i < e.length; i ++) {
			 //System.out.println(Arrays.toString(e[i]));
			 if(find_set(vertex, e[i][0]) != find_set(vertex, e[i][1])) {
				 union(vertex, e[i][0], e[i][1]);
				 System.out.print(e[i][0]);
				 System.out.print(" -- ");
				 System.out.print(e[i][1]);
				 System.out.print(" : ");
				 System.out.print(e[i][2]);
				 System.out.println();
			 }
		 }
	}
	
	int findMin(int v, int[] visit, int[] e) {
		//找到e中与v相连的未访问过的距离最近的结点
		int min = Integer.MAX_VALUE;
		int index = -1;
		for(int i = 0; i < e.length; i ++) {
			if(e[i] < min && visit[i] == -1) {
				min = e[i];
				index = i;
			}
		}
		return index;
	}
	
	void Prim(int x) {
		minPath = new int[edges.length];//用来放已经选中的结点
		vertex = new int[edges.length];
		for(int i = 0; i < vertex.length; i ++) vertex[i] = -1;
		int count = 0;
		minPath[count] = x; //j假设从x开始找MST
		vertex[minPath[count]] = 1;
		count ++;
		while(count < edges.length) {
			int min = Integer.MAX_VALUE;
			int index_s = -1;
			int index = -1;
			for(int i = 0; i < count; i ++) {
				int e = minPath[i];
				int now = findMin(e, vertex, edges[e]);
				if(now >= 0 && edges[e][now] < min) {
					index_s = e;
					min = edges[e][now];
					index = now;
				}
			}
			System.out.print(index_s);
			System.out.print(" -- ");
			System.out.print(index);
			System.out.print(" : ");
			System.out.print(min);
			System.out.println();
			minPath[count] = index;
			vertex[index] = 1;
			count ++;
		}
	}
	
	void Dijkstra(int x) {
		int[] dis = new int[edges.length];
		int[] path = new int[edges.length];
		for(int i = 0; i < edges.length; i ++) dis[i] = Integer.MAX_VALUE;
		dis[x] = 0;//自己到自己的最短路径为0
		path[x] = -1;
		Set<Integer> set = new HashSet<>();
		int count = 0;
		while(count != edges.length) {
			int now = -1; //在未访问的结点中找到离x的路径最短的结点作为当前结点
			int min = Integer.MAX_VALUE;
			for(int i = 0; i < edges.length; i ++) {
				if(!set.contains(i) && dis[i] < min) {
					now = i;
					min = dis[i];
				}
			}
			//用当前结点now的临界边与dis中的路径长度作比较并进行更新
			for(int i = 0; i < edges.length; i ++) {
				if(edges[now][i] != Integer.MAX_VALUE && edges[now][i] + dis[now] < dis[i]) {
					dis[i] = edges[now][i] + dis[now];
					path[i] = now;
				}
			}
			//将now加入已访问的集合中
			set.add(now);
			count ++;
		}
		System.out.print("distance: " + Arrays.toString(dis) + "\n");
		System.out.print("last vertex: " + Arrays.toString(path) + "\n");
	}
	
	void Floyd_Warshall() {
		int[][] dis = new int[edges.length][edges.length];
		for(int i = 0; i < edges.length; i ++)
			for(int j = 0; j < edges.length; j ++)
				dis[i][j] = edges[i][j];
		for(int k = 0; k < edges.length; k ++)
			for(int i = 0; i < edges.length; i ++)
				for(int j = 0; j < edges.length; j ++)
					if(dis[i][k] != Integer.MAX_VALUE && dis[k][j] != Integer.MAX_VALUE)
						dis[i][j] = Math.min(dis[i][j], dis[i][k] + dis[k][j]);
		for(int[] x : dis)
			System.out.println(Arrays.toString(x));
	}

	void Bellman_Ford(int x){
		int[] dis = new int[edges.length];
		int[] path = new int[edges.length];
		path[x] = -1;
		for(int i = 0; i < edges.length; i ++) dis[i] = Integer.MAX_VALUE;
		dis[x] = 0;//自己到自己的最短路径为0
		for(int k = 0; k < edges.length - 1; k ++) {
			for(int i = 0; i < edges.length; i ++) {
				for(int j = 0; j < edges.length; j ++) {
					if( dis[i] != Integer.MAX_VALUE && edges[i][j] != Integer.MAX_VALUE) {	
						if(dis[j] > dis[i] + edges[i][j]) {
							dis[j] = dis[i] + edges[i][j];
							path[j] = i;
						}
					}
				}
			}
		}
		System.out.print("distance: " + Arrays.toString(dis) + "\n");
		System.out.print("last vertex: " + Arrays.toString(path) + "\n");
	}
	
	
	public static void main(String[] args) {
		 System.out.println("construct an undirected graph using addEdge()");
		 Graph g = new Graph(6);
		 g.addEdge(0, 1, 3);
		 g.addEdge(0, 3, 3);
		 g.addEdge(1, 2, 3);
		 g.addEdge(2, 3, 3);
		 g.addEdge(1, 3, 3);
		 g.addEdge(2, 4, 3);
		 g.addEdge(3, 4, 3);
		 g.addEdge(1, 5, 3);
		 g.addEdge(2, 5, 3);
		 System.out.println("BFS: ");
		 g.BFS();// [0, 1, 3, 2, 5, 4]
		 System.out.println("DFS: ");
		 g.DFS(); // [0, 3, 4, 2, 5, 1]
		 System.out.println("---------------------------------");
		 
		//拓扑排序
		 System.out.println("construct a Directed Acyclic Graph");
		 Graph dag = new Graph(6);
		 int max = Integer.MAX_VALUE;
		 dag.edges = new int[][]{ { max, max, max, max, max, max },
			   					  { max, max, max, max, max, max },
			   					  { max, max, max, 3, max, max },
			   					  { max, 8, max, max, max, max },
			   					  { 4, 7, max, max, max, max },
			   					  { 4, max, 7, max, max, max }};
		 System.out.println("find topological_sort of DAG: ");		   					  
		 dag.topological_sort();
		 System.out.println("---------------------------------");
		 
		 System.out.println("construct another undirected graph");
		 Graph mst = new Graph(5);
		 mst.edges = new int[][] { { 0, 2, max, 6, max },
			  					   { 2, 0, 3, 8, 5 },
			                       { max, 3, 0, max, 7 },
			                       { 6, 8, max, 0, 9 },
			                       { max, 5, 7, 9, 0 } };
		 System.out.println("find MST by Prim from 2:  ");	       
		 mst.Prim(2);
		 System.out.println("---------------------------------");
		 
		 System.out.println("find MST by Kruskal: ");	       
		 mst.Kruskal();
		 System.out.println("---------------------------------");
		 
		 System.out.println("find min path by Dijkstra from 2: ");	
		 mst.Dijkstra(2); //[5, 3, 0, 11, 7] [1, 2, -1, 1, 2]
		 System.out.println("---------------------------------");
		 
		 System.out.println("find min path by Floyd_Warshall for all pairs");	
		 mst.Floyd_Warshall();
		 System.out.println("---------------------------------");
		 
		 Graph mst_b = new Graph(5);
		 //带负数边的有向图
		 System.out.println("construct a directed graph with minus edge");
		 mst_b.edges = new int[][] { { 0, max, max, max, max },
			  					   { 2, 0, max, -2, max },
			                       { max, 3, 0, max, 7 },
			                       { 6, max, max, 0, 9},
			                       { max, 5, max, max, 0 } };  
		 System.out.println("find min path by Bellman–Ford from 2: ");	
		 // mst.Bellman_Ford(2); // [5, 3, 0, 11, 7] [1, 2, -1, 1, 2]
		 mst_b.Bellman_Ford(2); //[5, 3, 0, 1, 7] [1, 2, -1, 1, 2]
		 System.out.println("---------------------------------");
		 
		 
		 
		 
	}

}
