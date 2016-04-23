package com.daansander.ball.engine.shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Shader {

	private int programID;
	private int vertexShader, fragmentShader;

	private static List<Shader> shaders = new ArrayList<>();
	
	protected Shader(String vertexShader, String fragmentShader) {
		this.programID = glCreateProgram();
		this.vertexShader = loadShader(vertexShader, GL_VERTEX_SHADER);
		this.fragmentShader = loadShader(vertexShader, GL_FRAGMENT_SHADER);
		glAttachShader(programID, this.vertexShader);
		glAttachShader(programID, this.fragmentShader);
		bindAllAttribs();
		glLinkProgram(programID);
		glValidateProgram(programID);
		getAllUniforms();
		shaders.add(this);
	}

	protected abstract void bindAllAttribs();
	
	protected abstract void getAllUniforms();
	
	protected void bindAttrib(int index, String name) {
		glBindAttribLocation(programID, index, name);
	}
	
	protected int getUniform(String name) {
		return glGetUniformLocation(programID, name);
	}
	
	public void start() {
		glUseProgram(programID);
	}
	
	public void stop() {
		glUseProgram(0);
	}
	
	public static void cleanUp() {
		for(int i = 0; i < shaders.size(); i++) {
			Shader shader = shaders.get(i);
			glDeleteProgram(shader.programID);
			glDeleteShader(shader.vertexShader);
			glDeleteShader(shader.fragmentShader);
		}
	}
	
	public int loadShader(String file, int type) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(new File(Shader.class.getResource("/shaders/" + file).getPath())));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line).append("\n");
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, builder);
		glCompileShader(shaderID);
		if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Shader compilation of: " + file + " failed!");
			System.err.println(glGetShaderInfoLog(shaderID, 500));
		}
		return shaderID;
	}
}