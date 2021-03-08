package com.gshell.server.to;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class BaseResp {
    public static final Integer SERVER_SUCCESS = 200;
    public static final Integer SERVER_ERROR = 500;
    private Integer server_status;
    private String server_error;

    public BaseResp() {
        this.server_status = SERVER_SUCCESS;
        this.server_error = null;
    }

    public static BaseResp success() {
        return process(ProcessFlagEnum.SUCCESS);
    }

    public static BaseResp fail() {
        return process(ProcessFlagEnum.FAIL);
    }

    public static BaseResp process(ProcessFlagEnum process) {
        return new BaseResp.ProcessResp(process);
    }

    public static BaseResp process(boolean process) {
        return new BaseResp.ProcessResp(process ? ProcessFlagEnum.SUCCESS : ProcessFlagEnum.FAIL);
    }

    public static BaseResp error() {
        return error(SERVER_ERROR, "通用异常！");
    }

    public static BaseResp error(String error) {
        return error(SERVER_ERROR, error);
    }

    public static BaseResp error(Integer status, String error) {
        BaseResp resp = process(ProcessFlagEnum.FAIL);
        resp.setServer_status(status);
        resp.setServer_error(error);
        return resp;
    }

    public static BaseResp.BaseRespMap map() {
        return new BaseResp.BaseRespMap();
    }

    public Integer getServer_status() {
        return this.server_status;
    }

    public void setServer_status(Integer server_status) {
        this.server_status = server_status;
    }

    public String getServer_error() {
        return this.server_error;
    }

    public void setServer_error(String server_error) {
        this.server_error = server_error;
    }

    public static class BaseRespMap extends BaseResp implements Map<String, Object> {
        private Map<String, Object> map = new LinkedHashMap();

        public BaseRespMap() {
            this.setServer_status(super.server_status);
            this.setServer_error(super.server_error);
        }

        public BaseResp.BaseRespMap append(String key, Object value) {
            this.map.put(key, value);
            return this;
        }

        public void setServer_status(Integer server_status) {
            super.setServer_status(server_status);
            this.append("server_status", server_status);
        }

        public void setServer_error(String server_error) {
            super.setServer_error(server_error);
            this.append("server_error", server_error);
        }

        public int size() {
            return this.map.size();
        }

        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        public boolean containsKey(Object key) {
            return this.map.containsKey(key);
        }

        public boolean containsValue(Object value) {
            return this.map.containsValue(value);
        }

        public Object get(Object key) {
            return this.map.get(key);
        }

        public Object put(String key, Object value) {
            return this.map.put(key, value);
        }

        public Object remove(Object key) {
            return this.map.remove(key);
        }

        public void putAll(Map<? extends String, ?> m) {
            this.map.putAll(m);
        }

        public void clear() {
            this.map.clear();
        }

        public Set<String> keySet() {
            return this.map.keySet();
        }

        public Collection<Object> values() {
            return this.map.values();
        }

        public Set<Entry<String, Object>> entrySet() {
            return this.map.entrySet();
        }
    }

    public static class ProcessResp extends BaseResp {
        private String process;

        public ProcessResp(ProcessFlagEnum process) {
            this.process = process.value();
        }

        public String getProcess() {
            return this.process;
        }

        public void setProcess(String process) {
            this.process = process;
        }
    }
}