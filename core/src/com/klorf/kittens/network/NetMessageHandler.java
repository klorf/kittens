package com.klorf.kittens.network;

import com.esotericsoftware.kryonet.Connection;

public interface NetMessageHandler<T> {
    void received(Connection c, T message);
}
