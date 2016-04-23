package com.daansander.ball.engine.shader;

public class StaticShader extends Shader {

	public static final String VERTEX_SHADER = "vertexShader.txt";
	public static final String FRAGMENT_SHADER = "fragmentShader.txt";
	
	public StaticShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER);
	}

	@Override
	protected void bindAllAttribs() {
		super.bindAttrib(0, "position");
		super.bindAttrib(1, "textureCoord");
	}

	@Override
	protected void getAllUniforms() {
		
	}
}
