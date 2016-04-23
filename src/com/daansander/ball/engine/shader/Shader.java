package com.daansander.ball.engine.shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL20;

public class Shader {

	private int programID;
	private int vertexShader, fragmentShader;

	protected Shader(String vertexShader, String fragmentShader) {
		
	}
	
	public int loadShader(String file, int type) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(Shader.class.getResource(file).getPath())));
			String line;
			while((line = reader.readLine()) != null) {
				builder.append(line).append("\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		glShaderSource(type, builder);
		glCompileShader(type);
		if(glGetShaderi(type, GL_COMPILE_STATUS) == GL_FALSE) {
			
		}
	}
}