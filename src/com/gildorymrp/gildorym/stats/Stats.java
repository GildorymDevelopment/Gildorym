package com.gildorymrp.gildorym.stats;

public class Stats {

	public int getBabPoor(int level){

		if (level == 1) {
			return 1;
		} else if (level > 1 && level <= 20) {
			return (int) Math.floor(((double) level) * 0.5D);
		} else if (level > 20)
			return 10;
		return 0;
	}
	
	public int getBabAvg(int level){
		if (level == 1){
			return 1;
		} else if (level > 1 && level <= 20){
			return (int) Math.floor(((double) level) *0.75D);
		}else if (level > 20)
			return 15;
		
		return 0;
	}
	
	public int getBabGood(int level){
		if (level <= 20)
			return level;
			
		return 20;
	}
	
	public int getSaveGood(int level){
		return 2 + level / 2;		
	}
	
	public int getSaveBad(int level){
		return level / 3;
	}

}
