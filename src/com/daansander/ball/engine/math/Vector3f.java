package com.daansander.ball.engine.math;

public class Vector3f {

	public int x;
	public int y;
	public int z;
	
	public Vector3f() {
		this.x = 0;
		this.y = 0;
		this.z = 0;	
	}
	
	public Vector3f(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void multiply(int value) {
		this.x *= value;
		this.y *= value;
		this.z *= value;
	}
	
	public void devide(int value) {
		this.x /= value;
		this.y /= value;
		this.z /= value;	
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
}