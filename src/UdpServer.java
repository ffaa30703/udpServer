import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

/**
 * Created by keep on 2018/1/25.
 */
public class UdpServer {
    // 定义一些常量
    private final int MAX_LENGTH = 1024; // 最大接收字节长度
    private final int PORT_NUM   = 9202;   // port号
    // 用以存放接收数据的字节数组
    private byte[] receMsgs = new byte[MAX_LENGTH];
    // 数据报套接字
    private DatagramSocket datagramSocket;
    // 用以接收数据报
    private DatagramPacket datagramPacket;

    public UdpServer(){
        try {
            /******* 接收数据流程**/
            // 创建一个数据报套接字，并将其绑定到指定port上
            datagramSocket = new DatagramSocket(PORT_NUM);
            // DatagramPacket(byte buf[], int length),建立一个字节数组来接收UDP包
            datagramPacket = new DatagramPacket(receMsgs, receMsgs.length);
            // receive()来等待接收UDP数据报
            System.out.println("start");
            datagramSocket.receive(datagramPacket);
            byte[] data=datagramPacket.getData();
            byte[] datar=new byte[datagramPacket.getLength()];
            System.arraycopy(data,0,datar,0,datagramPacket.getLength());

            /****** 解析数据报****/
            String receStr = new String(datagramPacket.getData(), 0 , datagramPacket.getLength());
            System.out.println("Server Rece:" + receStr);
            System.out.println("Server Port:" + datagramPacket.getPort());
            System.out.println("Server Port:" + DatatypeConverter.printHexBinary(datar));


            /***** 返回ACK消息数据报*/
            // 组装数据报
            byte[] buf = "I receive the message".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, datagramPacket.getAddress(), datagramPacket.getPort());
            System.out.println("IP:"+datagramPacket.getAddress()+"  PORT:"+datagramPacket.getPort());
            // 发送消息
            datagramSocket.send(sendPacket);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭socket
            if (datagramSocket != null) {
                datagramSocket.close();
            }
        }
    }
}