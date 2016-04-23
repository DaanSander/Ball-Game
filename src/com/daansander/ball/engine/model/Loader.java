package com.daansander.ball.engine.model;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Loader {

	private List<Integer> vaos = new ArrayList<>();
	private List<Integer> vbos = new ArrayList<>();
	private List<Integer> textures = new ArrayList<>();
	
	public RawModel loadModel(float[] vertices, int[] indices, float[] textureCoords) {
		int vaoID = glGenVertexArrays();
		vaos.add(vaoID);
		glBindVertexArray(vaoID);
		storeIndices(indices);
		storeDataInAttribf(0, 3, vertices);
		storeDataInAttribf(1, 2, textureCoords);
		glBindVertexArray(0);
		return new RawModel(vaoID, indices.length);
	}

	public void cleanUp() {
		for (int vao : vaos)
			glDeleteVertexArrays(vao);
		for (int vbo : vbos)
			glDeleteVertexArrays(vbo);
		for(int texture : textures)
			glDeleteTextures(texture);
	}

	public int getTexture(String name) {
		try {
			BufferedImage image = ImageIO.read(new File(Loader.class.getResource("/" + name + ".png").getPath()));
			
			int width = image.getWidth();
			int height = image.getHeight();
			
			int[] pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
			
			ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
			
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					int pixel = pixels[x + y * width];
					buffer.put((byte) ((pixel >> 24) & 0xff));
					buffer.put((byte) ((pixel >> 16) & 0xff));
					buffer.put((byte) ((pixel >> 8) & 0xff));
					buffer.put((byte) (pixel & 0xff));					
				}
			}
			
			buffer.flip();
			
			int textureID = glGenTextures();
			
			textures.add(textureID);
			
			glBindTexture(GL_TEXTURE_2D, textureID);
			
			//S,T = U,V
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			
			//Mipmapping
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			//Sending data to opengl
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			
			return textureID;
			
			
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public void storeIndices(int[] indices) {
		int vboID = glGenBuffers();
		vbos.add(vboID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = createIntBuffer(indices);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}

	public void storeDataInAttribf(int index, int dimensions, float[] data) {
		int vboID = glGenBuffers();
		vbos.add(vboID);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = createFloatBuffer(data);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(index, dimensions, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}