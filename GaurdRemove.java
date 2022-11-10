package com.ds.assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GaurdRemove {
	private ArrayList<GetShiftTime> listShifts = null;
	private int supersededIntervalCount = 0;
	private int totalCoverage = 0;

	public GaurdRemove(ArrayList<GetShiftTime> listShifts) {
		this.listShifts = listShifts;
	}

	//Finding lifeguard with minimum impact
	private int findLifeGaurd() {
		int size = this.listShifts.size();
		int maxCoverageSeenSoFar = -1;

		for (int counter = 0; counter < size; counter++) {
			int leftOverlap = 0, rightOverlap = 0;
			int currShiftStartTime = listShifts.get(counter).tStart;
			int currShiftEndTime = listShifts.get(counter).tEnd;
			int currShiftCoverage = listShifts.get(counter).timeCovered;

			if (counter == 0) {
				leftOverlap = 0;
			} else {
				int prevShiftStartTime = listShifts.get(counter-1).tStart;
				int prevShiftEndTime = listShifts.get(counter-1).tEnd; 
				leftOverlap = calcLeftOverlap(prevShiftStartTime, prevShiftEndTime, currShiftStartTime, currShiftEndTime);
			}

			if (counter == size - 1) {
				rightOverlap = 0;
			} else {
				int nextShiftStartTime = listShifts.get(counter+1).tStart;
				int nextShiftEndTime = listShifts.get(counter+1).tEnd;
				rightOverlap = calcRightOverlap(currShiftStartTime, currShiftEndTime, nextShiftStartTime, nextShiftEndTime);
			}

			
			int uniqueCoverageCurrShift = currShiftCoverage - (leftOverlap + rightOverlap);
			int coverage = totalCoverage - uniqueCoverageCurrShift;
			
			if (coverage > maxCoverageSeenSoFar)
				maxCoverageSeenSoFar = coverage;
		}

		return maxCoverageSeenSoFar;
	}
	
	
	public int maxCoverage() {

		ShiftStartTimeComparator startTimeComparator = new ShiftStartTimeComparator();

		// sort list
		Collections.sort(this.listShifts, startTimeComparator);

		calcSuperseded();

		if (this.supersededIntervalCount > 0) {
			return this.totalCoverage;
		}

		int maxCoverageAfterRemoval = findLifeGaurd();

		return maxCoverageAfterRemoval;
	}
	
	

	private boolean checkIntersection(int timeWindowStart, int timeWindowEnd, int currTimeStart, int currTimeEnd) {
		if (currTimeStart <= timeWindowEnd) {
			return true;
		}
		return false;
	}

	private boolean checkSupersededShift(int timeWindowStart, int timeWindowEnd, int currTimeStart, int currTimeEnd) {

		if ((timeWindowStart >= currTimeStart) && (timeWindowEnd <= currTimeEnd)) {
			return true;
		}
		return false;
	}

	private int calcLeftOverlap(int leftStartTime, int leftEndTime, int currStartTime, int currEndTime) {
		if (leftEndTime <= currStartTime) {
			return 0;
		}
		return leftEndTime - currStartTime;
	}

	private int calcRightOverlap(int currStartTime, int currEndTime, int rightStartTime, int rightEndTime) {
		if (rightStartTime >= currEndTime) {
			return 0;
		} 
		
		return currEndTime - rightStartTime;
	}
	
	private void calcSuperseded() {

		int size = this.listShifts.size();
		int timeWindowStart = this.listShifts.get(0).tStart;
		int timeWindowEnd = this.listShifts.get(0).tEnd;

		for (int counter = 1; counter < size; counter++) {
			int currTimeStart = this.listShifts.get(counter).tStart;
			int currTimeEnd = this.listShifts.get(counter).tEnd;

			if (checkSupersededShift(timeWindowStart, timeWindowEnd, currTimeStart, currTimeEnd)) {
				this.supersededIntervalCount = +1;
				timeWindowEnd = currTimeEnd;
				continue;
			}

			if (checkIntersection(timeWindowStart, timeWindowEnd, currTimeStart, currTimeEnd)) {
				timeWindowEnd = currTimeEnd;
				continue;
			}

			totalCoverage = totalCoverage + (timeWindowEnd - timeWindowStart);
			timeWindowStart = currTimeStart;
			timeWindowEnd = currTimeEnd;
		}
		totalCoverage = totalCoverage + (timeWindowEnd - timeWindowStart);
	}
	private class ShiftStartTimeComparator implements Comparator<GetShiftTime> {

		@Override
		public int compare(GetShiftTime s1, GetShiftTime s2) {

			if (s1.tStart == s2.tStart) {
				if (s1.tEnd == s2.tEnd) {
					return 0;
				}
				else if (s1.tEnd < s2.tEnd) {
					return -1;
				}
				else
					return 1;
			}

			if (s1.tStart < s2.tStart) {
				return -1;
			}

			return 1;
		}

	}
}