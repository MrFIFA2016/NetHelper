package com.gshell.server.to;

public enum ProcessFlagEnum {
    SUCCESS("00"),
    FAIL("01");

    private String value;

    public String value() {
        return this.value;
    }

    private ProcessFlagEnum(String value) {
        this.value = value;
    }

    public static ProcessFlagEnum getEnum(String value) {
        ProcessFlagEnum[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ProcessFlagEnum type = var1[var3];
            if (type.value.equals(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("No matching constant for [" + value + "]");
    }

    public boolean equals(ProcessFlagEnum type) {
        return this.value.equals(type.value);
    }
}