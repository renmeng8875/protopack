package com.babytree.commons.protopack;

import com.babytree.commons.protopack.base.Marshallable;
import com.babytree.commons.protopack.base.Packet;
import com.babytree.commons.protopack.base.ProtoHeader;
import com.babytree.commons.protopack.base.Unpack;
import com.babytree.commons.protopack.bean.Person;
import com.babytree.commons.protopack.marshal.ObjectMarshal;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author renmeng
 * @Description: 协议包测试类
 * @date 2018-07-02
 */
public class PacketTest {

    @Test
    public void PacketTest(){
        ProtoHeader header = new ProtoHeader();
        Map<String, String> attachment = new HashMap<>();
        attachment.put("md5", UUID.randomUUID().toString());
        attachment.put("key1","baby");
        attachment.put("key2","tree");
        header.setType(1);
        header.setAttachment(attachment);
        List<Marshallable> contents = new ArrayList();
        Person p = new Person();
        p.setAge(18);
        p.setName("girl");
        p.setNum(10000);
        ObjectMarshal bean = new ObjectMarshal(p);
        contents.add(bean);

        Packet inPacket = new Packet(header,contents);
        inPacket.marshal();
        byte[] inBytes = inPacket.getPack().getBytes();

        ProtoHeader outHeader = new ProtoHeader();
        List<Marshallable> outContents = new ArrayList<>();
        Person outP = new Person();
        ObjectMarshal outBean = new ObjectMarshal(outP);
        outContents.add(outBean);


        Packet outPacket = new Packet(outHeader,new Unpack(inBytes),outContents);
        outPacket.unmarshal();
        String value1 = outHeader.getAttachment().get("key1");
        Assert.assertEquals("baby",value1);
        ObjectMarshal objectMarshal = (ObjectMarshal)outContents.get(0);
        outP = (Person)objectMarshal.getData();
        Assert.assertEquals(outP.getName(),"girl");



    }


}
