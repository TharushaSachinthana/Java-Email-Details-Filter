import java.io.*;
import java.util.regex.*;

public class FilterDetails {
    public static void main(String[] args) {
        try {
            // Initialize the output file
            String outputFile = "filtered_details.txt";
            PrintWriter writer = new PrintWriter(new FileWriter(outputFile));

            // Read 10 email files and filter details
            for (int i = 1; i <= 10; i++) {
                String fileName = "email" + i + ".txt";
                String filteredDetails = filterEmailDetails(fileName);
                writer.println(filteredDetails);
            }

            // Close the output file
            writer.close();
            System.out.println("Filtered details saved to " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String filterEmailDetails(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder details = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("From:")) {
                // Extract sender name and email
                String sender = line.split(":")[1].trim();
                Matcher senderMatcher = Pattern.compile("(.+)<([^>]+)>").matcher(sender);
                if (senderMatcher.find()) {
                    String senderName = senderMatcher.group(1).trim();
                    String senderEmail = senderMatcher.group(2).trim();
                    details.append("Sender Name: ").append(senderName).append("\n");
                    details.append("Sender Email ID: ").append(senderEmail).append("\n");
                }
            } else if (line.startsWith("To:")) {
                // Extract receiver name and email
                String receiver = line.split(":")[1].trim();
                Matcher receiverMatcher = Pattern.compile("(.+)<([^>]+)>").matcher(receiver);
                if (receiverMatcher.find()) {
                    String receiverName = receiverMatcher.group(1).trim();
                    String receiverEmail = receiverMatcher.group(2).trim();
                    details.append("Receiver Name: ").append(receiverName).append("\n");
                    details.append("Receiver Email ID: ").append(receiverEmail).append("\n");
                }
            } else if (line.startsWith("Sent:")) {
                // Extract email date
                String emailDate = line.split(":")[1].trim();
                details.append("Email Date: ").append(emailDate).append("\n");
            } else if (line.contains("Lab Reschedule")) {
                // Extract course code, semester, lab no, and lab name
                String nextLine = reader.readLine();
                if (nextLine != null) {
                    Matcher courseMatcher = Pattern.compile("for ([A-Z0-9]+)\\.").matcher(nextLine);
                    if (courseMatcher.find()) {
                        String courseCode = courseMatcher.group(1);
                        details.append("Course Code: ").append(courseCode).append("\n");
                    }
                }
            }
        }

        reader.close();
        return details.toString();
    }
}
