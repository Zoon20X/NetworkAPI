package me.zoon20x.network.Packets;

import java.io.Serializable;
import java.util.HashMap;

public class DataPacket implements Serializable {
    private String id;

    private HashMap<String, Object> data ;

    public DataPacket(String id){
        this.id = id;
        this.data = new HashMap<>();
    }

    public DataPacket addData(String s, Object data){
        this.data.put(s, data);
        return this;
    }

    public Object getData(String id){
        return data.get(id);
    }

    public HashMap<String, Object> getDataMap() {
        return data;
    }
}
