package kafdrop.util;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.xnio.IoUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

public class B64CompressDeserializer implements MessageDeserializer {

    @Override
    public String deserializeMessage(ByteBuffer buffer) {
        InputStreamReader inputStreamReader = null;
        try {
            byte[] base64decoded = Base64.decodeBase64(buffer.array());
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(base64decoded);
            inputStreamReader = new InputStreamReader(
                    new CompressorStreamFactory().createCompressorInputStream(byteArrayInputStream), Charsets.UTF_8);
            return CharStreams.toString(inputStreamReader);
        } catch (Exception e) {
            return new String(buffer.array(), Charsets.UTF_8);
        } finally {
            IoUtils.safeClose(inputStreamReader);
        }
    }
}


