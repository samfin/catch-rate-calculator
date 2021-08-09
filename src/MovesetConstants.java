import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MovesetConstants {
	static int[][][] gen1red = null, gen1yellow = null, gen2gold = null, gen2crystal = null, gen3emerald = null, gen3firered = null;
	static int[][][] gen4diamond = null, gen4heartgold = null, gen5black = null, gen5black2 = null;
	private static int[][][] getData(String filename) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(MovesetConstants.class.getResource("/resources/" + filename).openStream()));
			String text = in.readLine(); 
			int n = Integer.parseInt(text);
			int[][][] output = new int[n+1][][];
			for(int i = 1; i <= n; i++) {
				String[] moves = in.readLine().split("\\s+");
				int k = moves.length / 2;
				output[i] = new int[k][2];
				for(int j = 0; j < k; j++) {
					output[i][j][0] = Integer.parseInt(moves[2*j]);
					output[i][j][1] = Integer.parseInt(moves[2*j + 1]);
				}
			}
			in.close();
			return output;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	static void init() {
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(MovesetConstants.class.getResource("/resources/movesets.txt").openStream()));
			String text = in.readLine(); 
			int n = Integer.parseInt(text);
			gen1red = new int[n+1][][];
			for(int i = 1; i <= n; i++) {
				String[] moves = in.readLine().split("\\s+");
				int k = moves.length / 2;
				gen1red[i] = new int[k][2];
				for(int j = 0; j < k; j++) {
					gen1red[i][j][0] = Integer.parseInt(moves[2*j]);
					gen1red[i][j][1] = Integer.parseInt(moves[2*j + 1]);
				}
			}
			text = in.readLine(); 
			n = Integer.parseInt(text);
			gen2crystal = new int[n+1][][];
			for(int i = 1; i <= n; i++) {
				String[] moves = in.readLine().split("\\s+");
				int k = moves.length / 2;
				gen2crystal[i] = new int[k][2];
				for(int j = 0; j < k; j++) {
					gen2crystal[i][j][0] = Integer.parseInt(moves[2*j]);
					gen2crystal[i][j][1] = Integer.parseInt(moves[2*j + 1]);
				}
			}
			text = in.readLine(); 
			n = Integer.parseInt(text);
			gen3emerald = new int[n+1][][];
			for(int i = 1; i <= n; i++) {
				String[] moves = in.readLine().split("\\s+");
				int k = moves.length / 2;
				gen3emerald[i] = new int[k][2];
				for(int j = 0; j < k; j++) {
					gen3emerald[i][j][0] = Integer.parseInt(moves[2*j]);
					gen3emerald[i][j][1] = Integer.parseInt(moves[2*j + 1]);
				}
			}
			gen1yellow = getData("yellow.txt");
			gen2gold = getData("gold.txt");
			gen3firered = getData("firered.txt");
			gen4diamond = getData("diamond.txt");
			gen4heartgold = getData("heartgold.txt");
			gen5black = getData("black.txt");
			gen5black2 = getData("black2.txt");
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}	
	public static int getLength(int gen) {
		int[][][][] data = {gen1red, gen2crystal, gen3emerald, gen4heartgold, gen5black2};
		return data[gen-1].length - 1;
	}
	static int[][] get(int poke, int gen, int type) {
		int[][][][] data = {gen1red, gen1yellow, gen2gold, gen2crystal, gen3emerald, gen3firered, gen4diamond, gen4heartgold, gen5black, gen5black2};
		if(gen >= 1 && gen <= 5 && type >= 0 && type <= 1) {
			int k = 2 * gen + type - 2;
			int[][][] movesets = data[k];
			if(movesets == null) return null;
			if(poke < movesets.length && poke > 0)
				return movesets[poke];
		}
		return null;
	}
}
