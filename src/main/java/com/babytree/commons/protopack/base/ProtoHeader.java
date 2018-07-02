package com.babytree.commons.protopack.base;

import com.babytree.commons.protopack.exception.UnpackException;
import com.babytree.commons.protopack.util.MarshallUtils;

import java.util.Map;

/**
 * @author renmeng
 * @Description: 消息头
 * @date 2018-07-02
 */
public class ProtoHeader  {
    //版本号和协议信息,高四位表示协议信息，低四位表示版体信息
    private int crc = 0x11110101;

    //消息长度,占位符
    private int size = 1;

    //消息类型
    private int type;

    public int getCrc() {
        return crc;
    }

    public void setCrc(int crc) {
        this.crc = crc;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Map<String, String> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, String> attachment) {
        this.attachment = attachment;
    }

    //扩展信息
    private Map<String,String> attachment;

    public void marshal(Pack pack) {
        pack.putInt(crc);
        //临时占位
        pack.putInt(1);
        pack.putInt(type);
        MarshallUtils.packMap(pack, attachment, String.class, String.class);

    }

    public void unmarshal(Unpack unpack) {
        int byteSize = unpack.getBuffer().array().length;
        this.crc = unpack.popInt();
        this.size = unpack.popInt();
        if(byteSize!=size){
            throw  new UnpackException("size is wrong!");
        }
        this.type = unpack.popInt();
        this.attachment = MarshallUtils.unpackMap(unpack,String.class,String.class,false);
    }

}
