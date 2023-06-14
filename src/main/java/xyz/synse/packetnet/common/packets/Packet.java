package xyz.synse.packetnet.common.packets;

import java.io.*;
import java.nio.ByteBuffer;

public class Packet {
    private final short id;
    private final byte[] data;

    public Packet(short id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public short getID() {
        return id;
    }

    public byte[] toByteArray() {
        try (
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(byteOut)
        ) {
            out.writeShort(id);
            out.writeInt(data.length);
            out.write(data);

            return byteOut.toByteArray();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Packet fromByteBuffer(ByteBuffer buffer) {
        short id = buffer.getShort();
        int len = buffer.getInt();
        byte[] data = new byte[len];
        buffer.get(data);

        return new Packet(id, data);
    }

    public static Packet fromByteArray(byte[] packet) {
        try (
                ByteArrayInputStream byteIn = new ByteArrayInputStream(packet);
                DataInputStream dataIn = new DataInputStream(byteIn);
        ) {
            short id = dataIn.readShort();
            int len = dataIn.readInt();
            byte[] data = dataIn.readNBytes(len);

            return new Packet(id, data);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getShortString() {
        return "id: " + id + ", data: " + data.length + "bytes";
    }
}
