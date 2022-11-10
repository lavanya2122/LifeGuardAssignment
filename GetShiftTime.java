package com.ds.assignment;

public class GetShiftTime {
	public int tStart = -1;
	public int tEnd = -1;
	public int timeCovered = -1;

	public GetShiftTime(int start, int end) {
		this.tStart = start;
		this.tEnd = end;
		this.timeCovered = end - start;
		if (timeCovered <= 0)
			throw new IllegalArgumentException(
					"Endtime can't be less that starttime. StartTime: " + start + ", EndTime: " + end);
	}
}