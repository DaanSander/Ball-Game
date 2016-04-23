package com.daansander.ball;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import com.daansander.ball.engine.model.Loader;
import com.daansander.ball.engine.model.TexturedModel;
import com.daansander.ball.engine.render.Renderer;
import com.daansander.ball.engine.shader.Shader;
import com.daansander.ball.engine.shader.StaticShader;

public class Game implements Runnable {

	// http://stackoverflow.com/questions/10801016/lwjgl-textures-and-strings

	public final int WIDTH = 1280;
	public final int HEIGHT = 720;

	private boolean running = false;

	private Thread thread;
	private Loader loader = new Loader();
	private Renderer renderer = new Renderer();
	private TexturedModel texturedModel;
	private StaticShader shader;

	private long window;

	public Game() {

		start();
	}

	public void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}

	public void stop() {
		running = false;

		Shader.cleanUp();
		loader.cleanUp();

		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void init() {
		GLFWErrorCallback.createPrint(System.err).set();

		if (glfwInit() == GL_FALSE) {
			// TODO handle it
		}
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);

		window = glfwCreateWindow(WIDTH, HEIGHT, "Game", 0, 0);
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwSetWindowPos(window, (vidmode.width() - WIDTH) / 2, (vidmode.height() - HEIGHT) / 2);
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);

		GL.createCapabilities();

		float[] vertices = { -0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, 0.5f, 0.0f };
		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, -0 };
		int[] indices = { 0, 1, 2 };

		texturedModel = new TexturedModel(loader.getTexture("texture"),
				loader.loadModel(vertices, indices, textureCoords));

		shader = new StaticShader();

	}

	public void run() {
		init();
		while (running) {
			tick();
			render();
			if (glfwWindowShouldClose(window) == 1) {
				stop();
			}
		}
	}

	private void tick() {
		glfwPollEvents();
	}

	private void render() {
		glfwSwapBuffers(window);
		shader.start();
		renderer.prepare();
		renderer.render(texturedModel);
		shader.stop();
	}

	public static void main(String[] args) {
		new Game();
	}
}
