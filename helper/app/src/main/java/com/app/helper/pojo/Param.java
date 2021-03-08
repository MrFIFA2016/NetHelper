package com.app.helper.pojo;

import com.alibaba.fastjson.util.TypeUtils;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Param implements Comparable<Param> {

    private String type;

    private Object value;

    private Integer order;

    private Class primitiveClass;

    public Object getValue() {
        switch (type) {
            case "int":
                value = TypeUtils.castToInt(value);
                primitiveClass = int.class;
                break;
            case "bool":
                value = TypeUtils.castToBoolean(value);
                primitiveClass = boolean.class;
                break;
            case "double":
                value = TypeUtils.castToDouble(value);
                primitiveClass = double.class;
                break;
            case "float":
                value = TypeUtils.castToFloat(value);
                primitiveClass = float.class;
                break;
            case "byte":
                value = TypeUtils.castToByte(value);
                primitiveClass = byte.class;
                break;
            case "long":
                value = TypeUtils.castToLong(value);
                primitiveClass = long.class;
                break;
            case "char":
                value = TypeUtils.castToChar(value);
                primitiveClass = char.class;
                break;
            case "bytes":
                value = TypeUtils.castToBytes(value);
                primitiveClass = byte[].class;
                break;
            case "date":
                value = TypeUtils.castToDate(value);
                primitiveClass = Date.class;
                break;
            case "string":
                value = TypeUtils.castToString(value);
                primitiveClass = String.class;
                break;
            case "":
                value = "none";
                break;
            default:
                value = null;
                try {
                    primitiveClass = Class.forName(type);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
        return value;
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
