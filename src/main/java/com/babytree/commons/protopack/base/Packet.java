package com.babytree.commons.protopack.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 协议包，包括包头和包体，并提供基础的打包方法
 * @author: renmeng
 * @date: 2018/5/31
 */
public class Packet implements Marshallable{

	protected ProtoHeader ProtoHeader;

	protected List<Marshallable> contents = new ArrayList<Marshallable>();

	protected Unpack unpack ;

	protected Pack pack = new Pack();

	public Packet(){}

	public Packet(ProtoHeader ProtoHeader){
		this.ProtoHeader = ProtoHeader;
	}
	public Packet(ProtoHeader ProtoHeader,Unpack unpack){
		this.ProtoHeader = ProtoHeader;
		this.unpack = unpack;
	}

	public Packet(ProtoHeader ProtoHeader, Marshallable... contents){
		this(ProtoHeader, Arrays.asList(contents));
	}

	public Packet(ProtoHeader ProtoHeader, List<Marshallable> contents){
		this.ProtoHeader = ProtoHeader;
		this.contents = contents;
	}

	public Packet(ProtoHeader ProtoHeader, Unpack unpack, Marshallable... contents){
		this(ProtoHeader, unpack, Arrays.asList(contents));
	}

	public Packet(ProtoHeader ProtoHeader, Unpack unpack, List<Marshallable> contents){
		this.ProtoHeader = ProtoHeader;
		this.unpack = unpack;
		this.contents = contents;
	}

	public void marshalProtoHeader(Pack pack){
		if (ProtoHeader != null) {
			ProtoHeader.marshal(pack);
		}
	}

	public void unmarshalProtoHeader(Unpack unpack){
		if (ProtoHeader != null) {
			ProtoHeader.unmarshal(unpack);
		}
	}

	public void unmarshalContent(Unpack unpack)
	{
		if(contents!=null && contents.size()!=0){
			int length = contents.size();
			for (int i = 0; i < length; i++) {
				Marshallable mar = contents.get(i);
				unpack.popMarshallable(mar);
			}
		}
	}

	public void marshalContent(Pack pack)
	{
		if(contents!=null && contents.size()!=0){
			for(Marshallable mar : contents)
			{
				pack.putMarshallable(mar);
			}
		}
	}

	public void marshalProtoHeader(){
		marshalProtoHeader(pack);
	}

	public void unmarshalProtoHeader(){
		unmarshalProtoHeader(unpack);
	}

	public void unmarshalContent()
	{
		unmarshalContent(unpack);
	}

	public void marshalContent()
	{
		marshalContent(pack);
	}

	public ProtoHeader getProtoHeader() {
		return ProtoHeader;
	}

	public void setProtoHeader(ProtoHeader ProtoHeader) {
		this.ProtoHeader = ProtoHeader;
	}

	public List<Marshallable> getContents() {
		return contents;
	}

	public void setContents(List<Marshallable> contents) {
		this.contents = contents;
	}

	public void putContent(Marshallable content){
		this.contents.add(content);
	}

	public void putContents(List<Marshallable> contents){
		this.contents.addAll(contents);
	}

	public void popContent(Marshallable content,Unpack unpack){
		unpack.popMarshallable(content);
		this.contents.add(content);
	}

	public void popContents(List<Marshallable> contents,Unpack unpack){
		if(contents!=null && contents.size()!=0){
			for(Marshallable mar : contents)
			{
				unpack.popMarshallable(mar);
				this.contents.add(mar);
			}
		}
	}

	public void marshal() {
		marshal(pack);
		int size = pack.size();
		//将数据长度写入到包头的第二个字节
		pack.replaceInt(4,size);
	}

	public void unmarshal() {
		unmarshal(unpack);
	}

	@Override
	public void marshal(Pack pack) {
		marshalProtoHeader(pack);
		marshalContent(pack);
	}

	@Override
	public void unmarshal(Unpack unpack) {
		unmarshalProtoHeader(unpack);
		unmarshalContent(unpack);
	}

	public Pack getPack() {
		return pack;
	}

	@Override
	public String toString() {
		String str = super.toString()+ " packet ProtoHeader:" + ProtoHeader + " contents:" + contents;
		return str;
	}

    @Override
    public Object getData() {
        return null;
    }
}
