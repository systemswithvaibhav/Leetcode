import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        
        // 1. Build the original tree
        for (int i = 1; i < n; i++) {
            adj.get(parent[i]).add(i);
        }
        
        int[] newParent = Arrays.copyOf(parent, n);
        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen, -1);
        
        // 2. Find closest matching ancestors via DFS
        findNewParents(0, adj, s, lastSeen, newParent);
        
        // 3. Build the modified tree
        List<List<Integer>> newAdj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            newAdj.add(new ArrayList<>());
        }
        for (int i = 1; i < n; i++) {
            newAdj.get(newParent[i]).add(i);
        }
        
        // 4. Calculate subtree sizes
        int[] ans = new int[n];
        getSubtreeSizes(0, newAdj, ans);
        
        return ans;
    }
    
    private void findNewParents(int u, List<List<Integer>> adj, String s, int[] lastSeen, int[] newParent) {
        int charIdx = s.charAt(u) - 'a';
        int prevAncestor = lastSeen[charIdx];
        
        // If a valid ancestor with the same character exists, update newParent
        if (prevAncestor != -1) {
            newParent[u] = prevAncestor;
        }
        
        // Mark current node as the most recent ancestor for this character
        lastSeen[charIdx] = u;
        
        for (int v : adj.get(u)) {
            findNewParents(v, adj, s, lastSeen, newParent);
        }
        
        // Backtrack: restore the previous ancestor state
        lastSeen[charIdx] = prevAncestor;
    }
    
    private int getSubtreeSizes(int u, List<List<Integer>> newAdj, int[] ans) {
        int size = 1;
        for (int v : newAdj.get(u)) {
            size += getSubtreeSizes(v, newAdj, ans);
        }
        ans[u] = size;
        return size;
    }
}