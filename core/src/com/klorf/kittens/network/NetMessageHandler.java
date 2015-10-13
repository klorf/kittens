package com.klorf.kittens.network;

import com.esotericsoftware.kryonet.Connection;

/**
 * Created by anidiot on 10/10/2015.
 */
public interface NetMessageHandler<T> {
    void received(Connection c, T message);
}
