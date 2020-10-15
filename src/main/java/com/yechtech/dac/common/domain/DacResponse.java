package com.yechtech.dac.common.domain;

import java.util.HashMap;

public class DacResponse extends HashMap<String,Object> {

    private static final long serialVersionUID = -6238981092735873192L;

    public DacResponse(){
        this.put("message","Success");
        this.put("code", 200);
    }

    public DacResponse message(String message){
        this.put("message",message);
        this.put("code", 500);
        return this;
    }

    public DacResponse data(Object data){
        this.put("data",data);
        return this;
    }

    @Override
    public DacResponse put(String key, Object value){
        super.put(key,value);
        return this;
    }

}
