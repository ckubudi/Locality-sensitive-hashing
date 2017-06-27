package lsh.utils;

public class UnionFind {
	private int[] parent;
	private int[] rank;
	
	public UnionFind(int size) {
		this.parent = new int[size];
		this.rank = new int[size];
		for(int i = 0; i < size; i++) {
			parent[i] = i;
		}
	}
	public int find(int i) {
		int p = parent[i];
		if(i == p) {
			return i;
		}
		parent[i] = find(p);
		return parent[i];
	}
	public void union(int i, int j) {
		int root1 = find(i);
		int root2 = find(j);
		
		if(root1 != root2) {
			if(rank[root1] > rank[root2]) {
				parent[root2] = root1;
			}else if(rank[root2] > rank[root1]) {
				parent[root1] = root2;
			}else {
				parent[root2] = root1;
				rank[root1]++;
			}
		}
	}
	public int[] getParents() {
		return parent;
	}
	
}
