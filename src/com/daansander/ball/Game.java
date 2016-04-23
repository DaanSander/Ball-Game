package com.daansander.ball;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import com.daansander.ball.engine.model.Loader;
import com.daansander.ball.engine.model.RawModel;
import com.daansander.ball.engine.render.Renderer;

public class Game extends Thread {

	//http://stackoverflow.com/questions/10801016/lwjgl-textures-and-strings
	
	public final int WIDTH = 1280;
	public final int HEIGHT = 720;

	private boolean running = false;

	private Thread thread;
	private Loader loader = new Loader();
	private Renderer renderer = new Renderer();
	private RawModel model;

	private long window;

	public Game() {

		start();
	}

	public void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}

	public void init() {
		if (glfwInit() == GL_FALSE) {
			// TODO handle it
		}

		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(WIDTH, HEIGHT, "Game", 0, 0);
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - WIDTH) / 2, (vidmode.height() - HEIGHT) / 2);
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);

		GL.createCapabilities();
		

		float[] vertices = { -0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, 0.5f, 0.0f };
		int[] indices = { 0, 1, 2 };

		model = loader.loadModel(vertices, indices);
	}

	public void run() {
		init();
		while (running) {
			tick();
			render();
			if (glfwWindowShouldClose(window) == 1) {
				running = false;
			}
		}
	}

	private void tick() {
		glfwPollEvents();
	}

	private void render() {
		glfwSwapBuffers(window);
		renderer.prepare();
		renderer.render(model);
	}

	public static void main(String[] args) {
		new Game();
	}
}
