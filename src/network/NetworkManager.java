package network;

import java.io.*;
import java.net.*;

/**
 * TCP 소켓을 이용한 네트워크 통신을 관리하는 클래스입니다.
 * 서버(Host) 모드와 클라이언트(Guest) 모드를 모두 지원합니다.
 */
public class NetworkManager {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private ServerSocket serverSocket;

    private final boolean isHost;
    private final String targetIp;
    private final int PORT = 5001; // 통신에 사용할 고정 포트 번호

    /**
     * @param isHost Host(서버) 여부
     * @param targetIp Guest인 경우 접속할 Host의 IP 주소
     */
    public NetworkManager(boolean isHost, String targetIp) {
        this.isHost = isHost;
        this.targetIp = targetIp;
    }

    /**
     * 설정된 모드에 따라 네트워크 연결을 시도합니다.
     * @throws IOException 연결 중 오류 발생 시
     */
    public void connect() throws IOException {
        if (isHost) {
            startAsServer(PORT);
        } else {
            startAsClient(targetIp, PORT);
        }
    }

    /** 서버 모드로 실행하여 Guest의 접속을 기다립니다. */
    private void startAsServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setReuseAddress(true);
        System.out.println("Host: 상대를 기다리는 중입니다... (Port: " + port + ")");
        this.socket = serverSocket.accept();
        System.out.println("Host: Guest 접속 완료!");
        setupStreams();
    }

    /** 클라이언트 모드로 실행하여 Host에 접속합니다. */
    private void startAsClient(String ip, int port) throws IOException {
        System.out.println("Guest: Host(" + ip + ")에게 연결 시도 중...");
        this.socket = new Socket(ip, port);
        System.out.println("Guest: Host 연결 완료!");
        setupStreams();
    }

    /** 입출력 스트림을 설정합니다. */
    private void setupStreams() throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    /** 문자열 메시지를 상대방에게 전송합니다. */
    public void send(String message) {
        if (writer != null) writer.println(message);
    }

    /** 상대방이 보낸 문자열 메시지를 수신할 때까지 대기합니다. */
    public String receive() throws IOException {
        return (reader != null) ? reader.readLine() : null;
    }

    /** 소켓 및 스트림 자원을 해제합니다. */
    public void close() {
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null) socket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            System.err.println("자원 해제 중 오류 발생: " + e.getMessage());
        }
    }
}