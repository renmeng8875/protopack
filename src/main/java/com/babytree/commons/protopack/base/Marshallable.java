package com.babytree.commons.protopack.base;


/**
 * @describe: 打包解包接口
 * @author: renmeng
 * @date: 2018/5/31
 **/
public interface Marshallable
{
    void marshal(Pack pack);
	
    void unmarshal(Unpack unpack);

    Object getData();


}