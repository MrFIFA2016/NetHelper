package com.app.helper.pojo;

import com.alibaba.fastjson.util.TypeUtils;

public class Param implements Comparable<Param> {

    private String type;

    private Object value;

    private Integer order;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        switch (type) {
            case "int":
                value = TypeUtils.castToInt(value);
                break;
            case "bool":
                value = TypeUtils.castToBoolean(value);
                break;
            case "double":
                value = TypeUtils.castToDouble(value);
                break;
            case "float":
                value = TypeUtils.castToFloat(value);
                break;
            case "byte":
                value = TypeUtils.castToByte(value);
                break;
            case "long":
                value = TypeUtils.castToLong(value);
                break;
            case "char":
                value = TypeUtils.castToChar(value);
                break;
            case "bytes":
                value = TypeUtils.castToBytes(value);
                break;
            case "date":
                value = TypeUtils.castToDate(value);
                break;
            case "string":
                value = TypeUtils.castToString(value);
                break;
            case "":
                value = "none";
                break;
            default:
                value = null;
                break;
        }
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public int compareTo(Param o) {
        if (order - o.order > 0)
            return 1;
        else if (order - o.order == 0)
            return 0;
        else
            return -1;
    }
}
