
public class GenIIICalculator extends Formula {
	// Also supports gen 4
	@Override
	double calculate(int poke, double hp, int status, int ball, int level) {
		double B = 1;
		if(ball == Constants.GREAT_BALL)
			B = 1.5;
		else if(ball == Constants.ULTRA_BALL)
			B = 2.0;
		else if(ball == Constants.DUSK_BALL)
			B = 3.5;
		else if(ball == Constants.QUICK_BALL)
			B = 4.0;
		else if(ball == Constants.TIMER_BALL)
			B = 4.0;
		double S = 1.0;
		if(status == Constants.FREEZE || status == Constants.SLEEP)
			S = 2.0;
		else if(status == Constants.BURN || status == Constants.POISON || status == Constants.PARALYSIS)
			S = 1.5;
		int C = (int) Constants.rates[poke];
		if(C > 255) C = 255;
		if(C < 1) C = 1;

		int ntrials = 32;
		double avg = 0;
		int base_hp = Constants.stats[poke][0];
		for(int hp_iv = 0; hp_iv <= 31; hp_iv++) {
			int max_hp = ((2 * base_hp + hp_iv + 100) * level) / 100 + 10;
			int cur_hp = (int) Math.round(max_hp * hp);
			int M = max_hp;
			int H = cur_hp;
			int X = (int) ((((3 * M - 2 * H) * (C * B)) / (3 * M)) * S);
			if(X > 255) X = 255;
			if(X < 1) X = 1;
			int Y = 16711680 / X;
			Y = (int) Math.sqrt(Y);
			Y = (int) Math.sqrt(Y);
			Y = 1048560 / Y;
			double p = (Y / 65536.0);
			p = p*p;
			p = p*p;
			avg += p;
		}
		
		return avg / ntrials;
	}
}
