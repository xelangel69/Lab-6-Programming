import com.common.Deserializer;
import com.common.Request;
import com.common.Response;
import com.common.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public final class UDPClient {
    private final DatagramChannel channel;

    public UDPClient(String host, int port) throws IOException {
        InetSocketAddress serverAddress = new InetSocketAddress(host, port);
        this.channel = DatagramChannel.open();

        channel.configureBlocking(false);
        channel.connect(serverAddress);
    }

    public void sendRequest(Request request) throws IOException {
        byte[] sentData = Serializer.toBytes(request);

        ByteBuffer buffer = ByteBuffer.wrap(sentData);

        channel.write(buffer);
    }

    public Response receiveResponse() throws InterruptedException, IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(65536);
        int bytesRead = 0;
        int attempts = 0;

        while (bytesRead == 0 && attempts < 50) {
            bytesRead = channel.read(buffer);
            if (bytesRead == 0) {
                Thread.sleep(100);
                attempts++;
            }
        }

        if (bytesRead == 0) {
            throw new ServerUnavailableException("Сервер временно недоступен");
        }

        buffer.flip();

        byte[] receivedData = new byte[buffer.remaining()];

        buffer.get(receivedData);

        return (Response) Deserializer.toObject(receivedData);
    }
}
