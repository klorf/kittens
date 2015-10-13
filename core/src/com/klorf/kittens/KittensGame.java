package com.klorf.kittens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.klorf.kittens.network.Common;
import com.klorf.kittens.network.KittenClient;
import com.klorf.kittens.network.NetMessageHandler;
import com.klorf.kittens.network.KittenServer;
import com.klorf.kittens.network.message.PlayerJoined;

public class KittensGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	public static final int NETWORK_MODE_SERVER = 1;
	public static final int NETWORK_MODE_CLIENT = 2;
	int networkMode;
	int networkPort = 31337;
	KittenServer server;

	public KittensGame(int networkMode)
	{
		super();
		this.networkMode = networkMode;
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		try {
			switch (networkMode) {
			default:
			case NETWORK_MODE_CLIENT :
				KittenClient c = new KittenClient();
				c.start();
				c.connect(5000, "localhost", networkPort);
				c.sendTCP(new PlayerJoined());
				break;
			case NETWORK_MODE_SERVER :
					server = new KittenServer();
					server.start();
					server.bind(networkPort);
					server.addHandler(PlayerJoined.class, new NetMessageHandler<PlayerJoined>() {
						@Override
						public void received(Connection c, PlayerJoined message) {
							Gdx.app.log("TAG", "CLIENT CONNECTED OMG");
						}
					});
				break;
			}
		} catch (Exception ex) {
			//todo: something
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
}
