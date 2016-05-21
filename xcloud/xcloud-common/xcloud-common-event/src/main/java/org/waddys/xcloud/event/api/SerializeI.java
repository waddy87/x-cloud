package org.waddys.xcloud.event.api;

import java.io.IOException;

/**
 * 编码和解码工厂
 */
public interface SerializeI {

	byte[] serialize(Object obj) throws IOException;
	
	Object deSerialize(byte[] in) throws IOException;

}
