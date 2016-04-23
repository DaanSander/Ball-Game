package com.daansander.ball.engine.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.daansander.ball.engine.model.RawModel;

public class Renderer {

	public void prepare() {
		glClear(GL_COLOR_BUFFER_BIT);
		glClearColor(0, 1, 1, 0);
	}
	
	public void render(RawModel model) {
		glBindVertexArray(model.VAOID);
		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLES, model.INDICESCOUNT, GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
	
}