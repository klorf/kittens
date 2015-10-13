package com.klorf.kittens.network;

import com.esotericsoftware.kryo.Kryo;
import com.klorf.kittens.network.message.PlayerJoined;

/**
 * Created by anidiot on 10/10/2015.
 */
public class Common {
    public static void Register(Kryo k) {
        k.register(PlayerJoined.class);
    }

}
