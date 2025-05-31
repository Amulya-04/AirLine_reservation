import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;

public class ChatbotAPIClient {
    public static String getChatbotResponse(String userInput) {
        try {
            URL url = new URL("http://192.168.1.36:5000/chat"); // Ensure correct port
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = "{\"message\":\"" + userInput + "\"}";

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            return response.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return "Chatbot unavailable";
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Airline Chatbot");
        frame.setSize(400, 400);
        
        frame.setLayout(new BorderLayout());

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JTextField inputField = new JTextField();
        JButton sendButton = new JButton("Send");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        frame.add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userText = inputField.getText().trim();
                if (!userText.isEmpty()) {
                    chatArea.append("You: " + userText + "\n");
                    String botResponse = getChatbotResponse(userText);
                    chatArea.append("Bot: " + botResponse + "\n");
                    inputField.setText("");
                }
            }
        });

        frame.setVisible(true);
    }

    ChatbotAPIClient() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
