package com.babytree.commons.protopack;

import com.babytree.commons.protopack.base.Marshallable;
import com.babytree.commons.protopack.base.Pack;
import com.babytree.commons.protopack.base.Unpack;
import com.babytree.commons.protopack.bean.Person;
import com.babytree.commons.protopack.bean.TestBean;
import com.babytree.commons.protopack.marshal.*;
import com.babytree.commons.protopack.util.Uint;
import com.babytree.commons.protopack.util.Ulong;
import com.babytree.commons.protopack.util.Ushort;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author renmeng
 * @Description: 2018-07-02
 * @date 各种类型的打包解包测试用例
 */
public class MarshalTest {

    @Test
    public void testBooleanMarshal(){
        //编码
        BooleanMarshal booleanMarshal = new BooleanMarshal(true);
        byte[] bytes = serialize(booleanMarshal);
        //解码
        BooleanMarshal newMarshal= (BooleanMarshal)deserialize(bytes,BooleanMarshal.class);
        Assert.assertEquals(true,Boolean.valueOf(newMarshal.toString()));

    }

    @Test
    public void testByteArrayMarshal(){
        //编码
        ByteArrayMarshal byteArrayMarshal = new ByteArrayMarshal("babytree".getBytes());
        byte[] bytes = serialize(byteArrayMarshal);
        //解码
        ByteArrayMarshal newMarshal= (ByteArrayMarshal)deserialize(bytes,ByteArrayMarshal.class);
        Assert.assertEquals("babytree",new String((byte[]) newMarshal.getData()));


    }

    @Test
    public void testByteMarshal(){
        //编码
        ByteMarshal marshal = new ByteMarshal((byte)1);
        byte[] bytes = serialize(marshal);
        //解码
        ByteMarshal newMarshal= (ByteMarshal)deserialize(bytes,ByteMarshal.class);
        Assert.assertEquals("1",newMarshal.toString());

    }

    @Test
    public void testIntegerMarshal(){
        //编码
        IntegerMarshal marshal = new IntegerMarshal(1);
        byte[] bytes = serialize(marshal);
        //解码
        IntegerMarshal newMarshal= (IntegerMarshal)deserialize(bytes,IntegerMarshal.class);
        Assert.assertEquals("1",newMarshal.toString());

    }

    @Test
    public void testJsonMarshal(){
        //编码
        JSONObject json = new JSONObject();
        json.put("key","baby");
        JSONMarshal marshal = new JSONMarshal(json);
        byte[] bytes = serialize(marshal);
        //解码
        JSONMarshal newMarshal= (JSONMarshal)deserialize(bytes,JSONMarshal.class);
        Assert.assertEquals(json.getString("key"),newMarshal.getJSONObject().getString("key"));

    }

    @Test
    public void testLinkedMapMarshal(){
        //编码
        Map<String,String> map = new LinkedHashMap<>();
        map.put("1","baby");
        map.put("2","tree");
        LinkedMapMarshal mapMarshal = new LinkedMapMarshal(map,String.class,String.class);
        byte[] bytes = serialize(mapMarshal);
        //解码
        LinkedMapMarshal newMarshal= new LinkedMapMarshal(String.class,String.class);
        Unpack unpack = new Unpack(bytes);
        newMarshal.unmarshal(unpack);
        LinkedHashMap dataMap = (LinkedHashMap)newMarshal.getData();
        Assert.assertEquals("baby",dataMap.get("1"));

    }


    @Test
    public void testListMarshal(){
        //编码
        List<String> personList = new ArrayList<>();
        personList.add("babytree");
        personList.add("meitun");
        ListMarshal marshal = new ListMarshal(personList,String.class);
        byte[] bytes = serialize(marshal);
        //解码
        ListMarshal  newMarshal = new ListMarshal(String.class);
        Unpack unpack = new Unpack(bytes);
        newMarshal.unmarshal(unpack);
        List<String> newList = (List<String>)newMarshal.getData();
        Assert.assertEquals("babytree",newList.get(0));
    }

    @Test
    public void testLongMarshal(){
        //编码
        LongMarshal marshal = new LongMarshal(1);
        byte[] bytes = serialize(marshal);
        //解码
        LongMarshal newMarshal= (LongMarshal)deserialize(bytes,LongMarshal.class);
        Assert.assertEquals("1",newMarshal.toString());
    }

    @Test
    public void testShortMarshal(){
        //编码
        ShortMarshal marshal = new ShortMarshal((short)1);
        byte[] bytes = serialize(marshal);
        //解码
        ShortMarshal newMarshal= (ShortMarshal)deserialize(bytes,ShortMarshal.class);
        Assert.assertEquals("1",newMarshal.toString());
    }

    @Test
    public void testStringMarshal(){
        //编码
        StringMarshal marshal = new StringMarshal("babytree");
        byte[] bytes = serialize(marshal);
        //解码
        StringMarshal newMarshal= (StringMarshal)deserialize(bytes,StringMarshal.class);
        Assert.assertEquals("babytree",newMarshal.getData());
    }

    @Test
    public void testUintMarshal(){
        //编码
        UintMarshal marshal = new UintMarshal(new Uint(1));
        byte[] bytes = serialize(marshal);
        //解码
        UintMarshal newMarshal= (UintMarshal)deserialize(bytes,UintMarshal.class);
        Assert.assertEquals("1",newMarshal.toString());
    }

    @Test
    public void testUlongMarshal(){
        //编码
        UlongMarshal marshal = new UlongMarshal(new Ulong(1));
        byte[] bytes = serialize(marshal);
        //解码
        UlongMarshal newMarshal= (UlongMarshal)deserialize(bytes,UlongMarshal.class);
        Assert.assertEquals("1",newMarshal.toString());
    }


    @Test
    public void testUshortMarshal(){
        //编码
        UshortMarshal marshal = new UshortMarshal(new Ushort(1));
        byte[] bytes = serialize(marshal);
        //解码
        UshortMarshal newMarshal= (UshortMarshal)deserialize(bytes,UshortMarshal.class);
        Assert.assertEquals("1",newMarshal.toString());
    }

    @Test
    public void testObjectMarshal(){
        //编码
        TestBean inputBean = new TestBean();
        inputBean.setByteNum((byte)1);
        inputBean.setShortNum((short)2);
        inputBean.setIntNum(3);
        inputBean.setLongNum(4);
        inputBean.setVarBin("Whoes your daddy?");
        Person innerBean = new Person();
        innerBean.setAge(18);
        innerBean.setName("renmeng");
        innerBean.setNum(30000);
        List<Person> innerBeanList = new ArrayList<>();
        innerBeanList.add(innerBean);
        inputBean.setBeanList(innerBeanList);
        ObjectMarshal marshal = new ObjectMarshal(inputBean);
        Pack pack = new Pack();
        marshal.marshal(pack);
        byte[] bytes = pack.getBytes();

        //解码
        Unpack unpack = new Unpack(bytes);
        TestBean outputBean = new TestBean();
        ObjectMarshal outputMarshal = new ObjectMarshal(outputBean);
        outputMarshal.unmarshal(unpack);
        Assert.assertEquals("renmeng",outputBean.getBeanList().get(0).getName());

    }


    private byte[] serialize(Marshallable marshallable){
        Pack pack = new Pack();
        marshallable.marshal(pack);
        return pack.getBytes();
    }

    private Marshallable deserialize(byte[] bytes,Class<? extends Marshallable> clazz){
        Unpack unpack = new Unpack(bytes);
        Marshallable marshallable = null;
        try {
             marshallable = clazz.newInstance();
             marshallable.unmarshal(unpack);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return marshallable;

    }



}
