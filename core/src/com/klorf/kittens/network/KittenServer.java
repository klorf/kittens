package com.klorf.kittens.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by anidiot on 10/10/2015.
 */
public class KittenServer extends Server {

    private HashMap<Class, List<NetMessageHandler>> handlers = new HashMap<>();

    public void addHandler(Class networkMessage, NetMessageHandler handler)
    {
        if (!handlers.containsKey(networkMessage)) {
            handlers.put(networkMessage, new ArrayList<NetMessageHandler>());
        }

        List<NetMessageHandler> l = handlers.get(networkMessage);
        if (!l.contains(handler)) {
            l.add(handler);
        }
    }

    public void removeHandler(Class networkMessage, NetMessageHandler handler)
    {
        if (handlers.containsKey(networkMessage)) {
            List<NetMessageHandler> l = handlers.get(networkMessage);
            l.remove(handler);
            if (l.isEmpty()) {
                handlers.remove(networkMessage);
            }
        }
    }

    @Override
    public void start() {
        Common.Register(getKryo());
        addListener(new Listener() {
            @Override
            public void received(Connection connection, Object o) {
                super.received(connection, o);
                Class msgClass = o.getClass();
                if (handlers.containsKey(msgClass)) {
                    for (NetMessageHandler handler : handlers.get(msgClass)) {
                        handler.received(connection, o);
                    }
                }
            }
        });

        super.start();
    }
}
