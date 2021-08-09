
public class GenICalculator extends Formula {
	
	@Override
	double calculate(int poke, double hp, int status, int ball, int level) {
		int C = Constants.rates[poke];
		int B = 255;
		if(ball == Constants.GREAT_BALL)
			B = 200;
		else if(ball == Constants.ULTRA_BALL)
			B = 150;
		int S = 0;
		if(status == Constants.FREEZE || status == Constants.SLEEP)
			S = 25;
		else if(status == Constants.BURN || status == Constants.POISON || status == Constants.PARALYSIS)
			S = 12;
		int G = (ball == Constants.GREAT_BALL)? 8 : 12;
		// Calculate HP factor
		double avg = 0;
		int ntrials = 16;
		int base_hp = Constants.stats[poke][0];
		for(int hp_iv = 0; hp_iv <= 15; hp_iv++) {
			int max_hp = ((base_hp + hp_iv + 50) * level) / 50 + 10;
			int cur_hp = (int) Math.round(max_hp * hp);
			int F = (max_hp * 255) / G;
			cur_hp /= 4;
			if(cur_hp > 0) F /= cur_hp;
			if(F > 255) F = 255;
			
			double p = (S + Math.min(C + 1, B - S) * (F + 1) / 256.0) / B;
			avg += p;
		}
		return avg / ntrials;
	}

}
