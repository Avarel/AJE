/*
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package xyz.avarel.aje.bytecode;

import xyz.avarel.aje.exceptions.InvalidBytecodeException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import static xyz.avarel.aje.bytecode.BytecodeUtils.toHex;

/**
 * @author AdrianTodt
 */
public class AJEBytecode {
    public static byte[] IDENTIFIER = {'A', 'J', 'E'};
    public static byte BYTECODE_VERSION_MAJOR = 1, BYTECODE_VERSION_MINOR = 0;

    public static void initialize(DataOutput output) throws IOException {
        output.write(IDENTIFIER);
        output.write(BYTECODE_VERSION_MAJOR);
        output.write(BYTECODE_VERSION_MINOR);
    }

    public static void validateInit(DataInput input) throws IOException {
        byte a = input.readByte(), j = input.readByte(), e = input.readByte();

        if (a != 'A' || j != 'J' || e != 'E') {
            String hexAJE = "0x" + toHex(new byte[]{a, j, e});
            String rightHex = "0x" + toHex(IDENTIFIER);

            throw new InvalidBytecodeException("Invalid Header " + hexAJE + ", was expecting " + rightHex + " (AJE)");
        }
    }

    public static void validateVersion(DataInput input) throws IOException {
        int versionMajor = input.readByte(), versionMinor = input.readByte();

        if (versionMajor != BYTECODE_VERSION_MAJOR || versionMinor > BYTECODE_VERSION_MINOR) {
            throw new InvalidBytecodeException(String.format(
                    "Unsupported Bytecode Version (Library Version: DAB%d.[0-%d]; Bytecode: DAB%d.%d)",
                    BYTECODE_VERSION_MAJOR, BYTECODE_VERSION_MINOR,
                    versionMajor, versionMinor
            ));
        }
    }

    public static String identifier(DataInput input) throws IOException {
        validateInit(input);

        int versionMajor = input.readByte(), versionMinor = input.readByte();

        return "AJE" + versionMajor + "." + versionMinor;
    }

    public static void finalize(DataOutput output) throws IOException {
        //END -1
        output.writeByte(0);
        output.writeShort(-1);
    }
}