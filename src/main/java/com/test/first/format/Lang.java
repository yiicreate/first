package com.test.first.format;

public interface Lang {
    default Integer getCode(){return 0;}
    default String getInfo(){return "succ";};
}
