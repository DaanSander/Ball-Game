package com.daansander.ball.engine.model;

public class TexturedModel {
	
	public final int TEXTURE_ID;
	private RawModel model;
	
	public TexturedModel(int textureID, RawModel model) {
		this.TEXTURE_ID = textureID;
		this.model = model;
	}
	
	public RawModel getModel() {
		return model;
	}
}