package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ChatWithBots extends JPanel {

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    public ChatWithBots() {
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true); // Thêm dòng này để tự động xuống dòng
        chatArea.setWrapStyleWord(true); // Thêm dòng này để tự động xuống dòng theo từ
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

            // Check if the question is related to sorting
            if (isSortingQuestion(userInput)) {
                // Call the ChatGPT API to get a response
                String response = getChatGPTResponse(userInput);
                chatArea.append("Bot: " + response + "\n");
            } else {
                chatArea.append("Bot: I can only answer questions related to sorting algorithms.\n");
            }
        }
    }

    private boolean isSortingQuestion(String question) {
        String[] sortingKeywords = { "sort", "sorting", "bubble sort", "quick sort", "merge sort", "selection sort",
                "insertion sort" };
        for (String keyword : sortingKeywords) {
            if (question.toLowerCase().contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private String getChatGPTResponse(String input) {
        String apiKey = "xxx";
        String endpoint = "https://chatgpt-api8.p.rapidapi.com/";
        HttpClient client = HttpClient.newHttpClient();

        String requestBody = "[{\"content\":\"Hello! I'm an AI assistant bot based on ChatGPT 3. How may I help you?\",\"role\":\"system\"},{\"content\":\""
                + input + "\",\"role\":\"user\"}]";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", "chatgpt-api8.p.rapidapi.com")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return parseChatGPTResponse(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to get response from ChatGPT.";
        }
    }

    private String parseChatGPTResponse(String jsonResponse) {
        // Simple parsing logic to extract the response text
        int startIndex = jsonResponse.indexOf("\"content\":\"") + 11;
        int endIndex = jsonResponse.indexOf("\"", startIndex);
        return jsonResponse.substring(startIndex, endIndex).replace("\\n", "\n").replace("\\\"", "\"");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat with Bots");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.add(new ChatWithBots());
        frame.setVisible(true);
    }
}
