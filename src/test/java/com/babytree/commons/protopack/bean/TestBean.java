package com.babytree.commons.protopack.bean;

import com.babytree.commons.protopack.bean.Person;

import java.util.List;



public class TestBean{
    public TestBean(){

    }
    private byte byteNum;

    private short shortNum;

    private int intNum;

    private long longNum;

    private String varBin;

    private List<Person> beanList;


    public byte getByteNum() {
        return byteNum;
    }

    public void setByteNum(byte byteNum) {
        this.byteNum = byteNum;
    }

    public short getShortNum() {
        return shortNum;
    }

    public void setShortNum(short shortNum) {
        this.shortNum = shortNum;
    }

    public int getIntNum() {
        return intNum;
    }

    public void setIntNum(int intNum) {
        this.intNum = intNum;
    }

    public long getLongNum() {
        return longNum;
    }

    public void setLongNum(long longNum) {
        this.longNum = longNum;
    }

    public String getVarBin() {
        return varBin;
    }

    public void setVarBin(String varBin) {
        this.varBin = varBin;
    }

    public List<Person> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<Person> beanList) {
        this.beanList = beanList;
    }
}

