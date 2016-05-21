package org.waddys.xcloud.event.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.waddys.xcloud.event.api.SerializeI;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

public class HessionSerializeImpl implements SerializeI {

	private final Logger logger = Logger.getLogger(HessionSerializeImpl.class);

	@Override
	public byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream baos = null;
		HessianOutput output = null;
		try {
			baos = new ByteArrayOutputStream(1024);
			output = new HessianOutput(baos);
			output.startCall();
			output.writeObject(obj);
			output.completeCall();
		} catch (final IOException ex) {
			throw ex;
		} finally {
			if (output != null) {
				try {
					baos.close();
				} catch (final IOException ex) {
					this.logger.error("Failed to close stream.", ex);
				}
			}
		}
		return baos != null ? baos.toByteArray() : null;
	}

	@Override
	public Object deSerialize(byte[] in) throws IOException {
		Object obj = null;
		ByteArrayInputStream bais = null;
		HessianInput input = null;
		try {
			bais = new ByteArrayInputStream(in);
			input = new HessianInput(bais);
			input.startReply();
			obj = input.readObject();
			input.completeReply();
		} catch (final IOException ex) {
			throw ex;
		} catch (final Throwable e) {
			this.logger.error("Failed to decode object.", e);
		} finally {
			if (input != null) {
				try {
					bais.close();
				} catch (final IOException ex) {
					this.logger.error("Failed to close stream.", ex);
                }
            }
        }
        return obj;
	}

}
