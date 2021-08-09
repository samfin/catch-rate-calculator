import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TreeNode {
	TreeNode(int val) {
		values = new ArrayList<Integer>();
		children = new TreeNode[256];
		ind = 0;
	}
	void reset() {
		ind = 0;
	}
	public void sort(int gen) {
		final int length = MovesetConstants.getLength(gen);
		Collections.sort(values, new Comparator<Integer>() {
		    public int compare(Integer a, Integer b) {
		        if(a == 0) return -1;
		        if(b == 0) return 1;
		        if(a <= length && b > length) return -1;
		        if(a > length && b <= length) return 1;
		        String s = Constants.names[a];
		        String t = Constants.names[b];
		        return s.compareTo(t);
		    }
		});
		for(TreeNode n : children) {
			if(n != null) n.sort(gen);
		}
	}
	void add(int val, String s, int k) {
		values.add(val);
		Collections.sort(values, new Comparator<Integer>() {
		    public int compare(Integer a, Integer b) {
		        if(a == 0) return -1;
		        if(b == 0) return 1;
		        String s = Constants.names[a];
		        String t = Constants.names[b];
		        return s.compareTo(t);
		    }
		});
		if(k == s.length()) return;
		int c = (int) s.charAt(k);
		if(children[c] == null)
			children[c] = new TreeNode(val);
		children[c].add(val, s, k+1);
	}
	int queryDepth(String s, int k) {
		if(k == s.length()) return s.length();
		int c = (int) s.charAt(k);
		if(children[c] == null) return k;
		else return children[c].queryDepth(s, k+1);
	}
	TreeNode query(String s, int k) {
		if(k == s.length()) return this;
		int c = (int) s.charAt(k);
		if(children[c] == null) return this;
		else return children[c].query(s, k+1);
	}
	int getVal() {
		return values.get(ind);
	}
	int getNext() {
		ind = (ind + 1) % values.size();
		return getVal();
	}
	int getPrev() {
		ind = (ind - 1 + values.size()) % values.size();
		return getVal();
	}
	
	ArrayList<Integer> values;
	TreeNode[] children;
	int ind;
}
