
public class GenIICalculator extends Formula {

	@Override
	double calculate(int poke, double hp, int status, int ball, int level) {
		double ball_factor = 1;
		if(ball == Constants.GREAT_BALL)
			ball_factor = 1.5;
		else if(ball == Constants.ULTRA_BALL)
			ball_factor = 2.0;
		int S = 0;
		if(status == Constants.FREEZE || status == Constants.SLEEP)
			S = 10;
		int C = (int) (ball_factor * Constants.rates[poke]);
		if(C > 255) C = 255;
		if(C < 1) C = 1;

		double avg = 0;
		int ntrials = 16;
		int base_hp = Constants.stats[poke][0];
		for(int hp_iv = 0; hp_iv <= 15; hp_iv++) {
			int max_hp = ((base_hp + hp_iv + 50) * level) / 50 + 10;
			int cur_hp = (int) Math.round(max_hp * hp);
			int M = max_hp * 3;
			int H = cur_hp * 2;
			int X = Math.max(((M - H) * C) / M, 1) + S;
			if(X > 255) X = 255;
			double p = (X + 1) / 256.0;
			avg += p;
		}
		
		return avg / ntrials;
	}
}
