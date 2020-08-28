/*
 Copyright [2020] [Accso - Accelerated Solutions GmbH]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

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


