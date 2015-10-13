package com.klorf.kittens.network;

import com.esotericsoftware.kryo.Kryo;
import com.klorf.kittens.network.message.PlayerJoined;

public class Common {
    public static void Register(Kryo k) {
        k.register(PlayerJoined.class);
    }

}
