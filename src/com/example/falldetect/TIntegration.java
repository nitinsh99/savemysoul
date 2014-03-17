//Trapezoid integration rule: 1/2 h * (f0 + f1)
package com.example.falldetect;

public class TIntegration {
	
	private static double tRule (int size, double[] x, double[] y)
	{
		double sum = 0.0,counter;

		for ( int i = 1; i < size; i++ )
		{
			counter = 0.5 * (x[i]-x[i-1]) * (y[i]+y[i-1]);
			sum += counter;
		}
		return sum;
	}

	public static double integrate(double[] x, double y[]) {
		return tRule(x.length, x, y);

	}

}
