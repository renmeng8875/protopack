package com.babytree.commons.protopack.base;

import com.babytree.commons.protopack.exception.PackException;
import com.babytree.commons.protopack.util.StringUtils;
import com.babytree.commons.protopack.util.Uint;
import com.babytree.commons.protopack.util.Ulong;
import com.babytree.commons.protopack.util.Ushort;

import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


/**
 * 将各种数据写入ByteBuffer
 * @author: renmeng
 * @date: 2018/5/31
 */
public class Pack
{
	private ByteBuffer buffer;

	private int m_maxCapacity = 2 * 1024 * 1024;

	public Pack()
	{
		buffer = ByteBuffer.allocate(16);
		//定死打包和解包都用小字节序
		buffer.order(ByteOrder.LITTLE_ENDIAN);
	}

	public Pack(int size)
	{
		buffer = ByteBuffer.allocate(size);
        //定死打包和解包都用小字节序
		buffer.order(ByteOrder.LITTLE_ENDIAN);
	}

	protected Object attachment;

	public void setAttachment(Object obj){
		this.attachment = obj;
	}

	public Object getAttachment(){
		return this.attachment;
	}

	public int size()
	{
		return buffer.position();
	}

	public void clear(){
		buffer.clear();
	}

	public void mark(){
		buffer.mark();
	}

	public void reset(){
		buffer.reset();
	}

	/**
	 * Get buffer of Pack with flip()
	 */
	public ByteBuffer getBuffer()
	{
		ByteBuffer dup = buffer.duplicate();
		dup.flip();
		return dup;
	}

	public Pack putFetch(byte[] bytes)
	{
		try
		{
			ensureCapacity(bytes.length);
			buffer.put(bytes);
			return this;
		}
		catch(BufferOverflowException bex)
		{
			throw new PackException("pack too many bytes", bex);
		}
	}

	public Pack putByte(byte bt)
	{
		try
		{
			ensureCapacity(1);
			buffer.put(bt);
			return this;
		}
		catch(BufferOverflowException bex)
		{
			throw new PackException("pack too many bytes", bex);
		}
	}


	public Pack putInt(int val)
	{
			ensureCapacity(4);
			buffer.putInt(val);
			return this;
	}

	public Pack putUInt(Uint val)
	{
		return putInt(val.intValue());
	}

	public Pack putUshort(Ushort val)
	{
		return putShort(val.shortValue());
	}

	public Pack putUlong(Ulong val)
	{
		return putLong(val.longValue());
	}

	public Pack putBoolean(boolean val)
	{
		try
		{
			ensureCapacity(1);
			buffer.put((byte)(val?1:0));
			return this;
		}
		catch(BufferOverflowException bex)
		{
			throw new PackException("pack too many bytes", bex);
		}
	}

	public Pack putLong(long val)
	{
		try
		{
			ensureCapacity(8);
			buffer.putLong(val);
			return this;
		}
		catch(BufferOverflowException bex)
		{
			throw new PackException("pack too many bytes", bex);
		}
	}

	public Pack putShort(short val)
	{
		try
		{
			ensureCapacity(2);
			buffer.putShort(val);
			return this;
		}
		catch(BufferOverflowException bex)
		{
			throw new PackException("pack too many bytes", bex);
		}
	}

	// 16位的
	public Pack putVarbin(byte[] bytes)
	{
		try
		{
			ensureCapacity(2 + bytes.length);
			buffer.putShort((short)bytes.length);
			buffer.put(bytes);
			return this;
		}
		catch(BufferOverflowException bex)
		{
			throw new PackException("required too many bytes :" + bytes.length, bex);
		}
	}

	// 32位的
	public Pack putVarbin32(byte[] bytes)
	{
		try
		{
			ensureCapacity(4 + bytes.length);
			buffer.putInt(bytes.length);
			buffer.put(bytes);
			return this;
		}
		catch(BufferOverflowException bex)
		{
			throw new PackException("pack too many bytes", bex);
		}
	}

	public Pack putVarstr(String str)
	{
		try
		{
			if(str==null) {
                str = "";
            }
			return putVarbin(str.getBytes("utf-8"));
		}
		catch(UnsupportedEncodingException codeEx)
		{
			throw new PackException(codeEx);
		}
	}

	/**
	 * 打包String
	 *
	 * @param str
	 * @return
	 */
	public Pack putVarstr32(String str)
	{
		try
		{
			if(str==null)
			{
				str = "";
			}
			return putVarbin32(str.getBytes("utf-8"));
		}
		catch(UnsupportedEncodingException codeEx)
		{
			throw new PackException(codeEx);
		}
	}

	public Pack putMarshallable(Marshallable mar)
	{
		mar.marshal(this);
		return this;
	}

	public Pack putBuffer(ByteBuffer buf)
	{
		try
		{
			ensureCapacity(buf.remaining());
			buffer.put(buf);
			return this;
		}
		catch(BufferOverflowException bex)
		{
			throw new PackException(bex);
		}
	}

	public void replaceShort(int off, short val)
	{
		try
		{
			int pos = buffer.position();
			buffer.position(off);
			buffer.putShort(val).position(pos);
		}
		catch(BufferOverflowException bex)
		{
			throw new PackException("pack too many bytes", bex);
		}
		catch(IllegalArgumentException iex)
		{
			throw new PackException(iex);
		}
	}

	public void replaceInt(int off, int val)
	{
		try
		{
			int pos = buffer.position();
			buffer.position(off);
			buffer.putInt(val).position(pos);
		}
		catch(BufferOverflowException bex)
		{
			throw new PackException("pack too many bytes", bex);
		}
		catch(IllegalArgumentException iex)
		{
			throw new PackException(iex);
		}
	}

	/**
	 * Ensures that a the buffer can hold up the new increment
	 *
	 * @throws Exception
	 */
	public void ensureCapacity(int increament)
		throws BufferOverflowException
	{
		if(buffer.remaining() >= increament)
		{
			return;
		}

		int requiredCapacity = buffer.capacity() + increament
			- buffer.remaining();

		if(requiredCapacity > m_maxCapacity)
		{
			throw new BufferOverflowException();
		}

		int tmp = Math.max(requiredCapacity, buffer.capacity() * 2);
		int newCapacity = Math.min(tmp, m_maxCapacity);

		ByteBuffer newBuffer = ByteBuffer.allocate(newCapacity);
		newBuffer.order(buffer.order());
		buffer.flip();
		newBuffer.put(buffer);
		buffer = newBuffer;
	}

	@Override
	public String toString()
	{
		byte[] byteArray = buffer.array();
		StringBuilder sb = new StringBuilder(size() * 8);
		for(int i = 0; i < size(); i++){
			sb.append(StringUtils.byteToBitString(byteArray[i]));
		}
		return buffer.toString() + " Size " + size() + " and binary bits : " + sb.toString();
	}

	public byte[] getBytes(){
	    return  buffer.array();
    }

}
