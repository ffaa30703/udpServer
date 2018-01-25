import java.io.IOException;
import java.net.*;

/**
 * Created by keep on 2018/1/25.
 /***
 * UDPclientClient端
 ***/
public class UdpClient {

    private String sendStr = "SendString";

//    private String netAddress = "172.18.27.133";
//    private final int PORT_NUM = 10001;

    private String netAddress = "192.168.11.100";
//    private String netAddress = "127.0.0.1";
    private final int PORT_NUM = 9201;

    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;

    public UdpClient(){
        try {

            /*** 发送数据***/
            // 初始化datagramSocket,注意与前面Server端实现的差别
            datagramSocket = new DatagramSocket();
            // 使用DatagramPacket(byte buf[], int length, InetAddress address, int port)函数组装发送UDP数据报
//            byte[] buf = sendStr.getBytes();
            byte[] buf = new byte[]{(byte) 0xd2,0x00,0x00,0x00};

            InetAddress address = InetAddress.getByName(netAddress);
            datagramPacket = new DatagramPacket(buf, buf.length, address, PORT_NUM);
            // 发送数据
            datagramSocket.send(datagramPacket);

            /*** 接收数据***/
            byte[] receBuf = new byte[1024];
            DatagramPacket recePacket = new DatagramPacket(receBuf, receBuf.length);
            datagramSocket.receive(recePacket);

            String receStr = new String(recePacket.getData(), 0 , recePacket.getLength());
            System.out.println("Client Rece Ack:" + receStr);
            System.out.println(recePacket.getPort());


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭socket
            if(datagramSocket != null){
                datagramSocket.close();
            }
        }
    }

    public static void main(String[] args) {
        UdpClient udpClient=new UdpClient();
    }
}