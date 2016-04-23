package com.daansander.ball.engine.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.daansander.ball.engine.model.RawModel;
import com.daansander.ball.engine.model.TexturedModel;

public class Renderer {

	public void prepare() {
		glClear(GL_COLOR_BUFFER_BIT);
		glClearColor(0, 1, 1, 0);
	}
	
	public void render(TexturedModel texturedModel) {
		RawModel model = texturedModel.getModel();
		glBindVertexArray(model.VAOID);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texturedModel.TEXTURE_ID);
		glDrawElements(GL_TRIANGLES, model.INDICESCOUNT, GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
	}
	
}