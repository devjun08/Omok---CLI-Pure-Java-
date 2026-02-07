package network;

import java.io.*;
import java.net.*;

public class NetworkManager {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private ServerSocket serverSocket; // 자원 정리를 위해 필드로 승격

    private boolean isHost;
    private String targetIp;
    private final int PORT = 5001; // 포트 번호를 상수로 고정 (둘이 약속한 통로)

    // 1. OmokApplication에서 사용하는 생성자 추가
    public NetworkManager(boolean isHost, String targetIp) {
        this.isHost = isHost;
        this.targetIp = targetIp;
    }

    // 2. OmokApplication에서 호출하는 통 통합 연결 메서드
    public void connect() throws IOException {
        if (isHost) {
            startAsServer(PORT);
        } else {
            startAsClient(targetIp, PORT);
        }
    }

    // 서버 모드 (내부에서만 쓰이므로 private으로 변경 가능)
    private void startAsServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setReuseAddress(true);
        System.out.println("Host: 상대를 기다리는 중입니다... (Port: " + port + ")");
        this.socket = serverSocket.accept();
        System.out.println("Host: Guest 접속 완료!");
        setupStreams();
    }

    // 클라이언트 모드
    private void startAsClient(String ip, int port) throws IOException {
        System.out.println("Guest: Host(" + ip + ")에게 연결 시도 중...");
        this.socket = new Socket(ip, port);
        System.out.println("Guest: Host 연결 완료!");
        setupStreams();
    }

    private void setupStreams() throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void send(String message) {
        if (writer != null) writer.println(message);
    }

    public String receive() throws IOException {
        return (reader != null) ? reader.readLine() : null;
    }

    public void close() {
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null) socket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            System.err.println("Cleanup error: " + e.getMessage());
        }
    }
}