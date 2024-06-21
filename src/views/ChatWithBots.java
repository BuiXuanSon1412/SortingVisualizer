package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatWithBots extends JPanel {

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    public ChatWithBots() {
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField(30);
        sendButton = new JButton("Send");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String userInput = inputField.getText().trim();
        if (!userInput.isEmpty()) {
            chatArea.append("You: " + userInput + "\n");
            inputField.setText("");

            // Call the ChatGPT API to get a response
            String response = getChatGPTResponse(userInput);
            chatArea.append("Bot: " + response + "\n");
        }
    }

    private String getChatGPTResponse(String input) {
        String apiKey = "###"; // Replace with your API key
        String endpoint = "https://api.openai.com/v1/engines/davinci-codex/completions";

        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setDoOutput(true);

            String requestBody = "{\"prompt\":\"" + input + "\",\"max_tokens\":100}";
            try (OutputStream os = connection.getOutputStream()) {
                byte[] inputBytes = requestBody.getBytes("utf-8");
                os.write(inputBytes, 0, inputBytes.length);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return parseChatGPTResponse(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to get response from ChatGPT.";
        }
    }

    private String parseChatGPTResponse(String jsonResponse) {
        // Simple parsing logic to extract the response text
        int startIndex = jsonResponse.indexOf("\"text\": \"") + 9;
        int endIndex = jsonResponse.indexOf("\"", startIndex);
        return jsonResponse.substring(startIndex, endIndex).replace("\\n", "\n").replace("\\\"", "\"");
    }
}
