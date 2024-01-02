package com.notestudios.world;

public class Camera {
	public static int x = 100;
	public static int y = 100;

	public static int clamp(int xAtual, int xMin, int xMax) {

		if (xAtual < xMin) {
			xAtual = xMin;
		}

		if (xAtual > xMax) {
			xAtual = xMax;
		}

		return xAtual;
	}
}