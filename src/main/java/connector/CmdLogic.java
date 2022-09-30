package connector;

import org.json.JSONObject;
import requestHandler.RequestHandler;
import java.util.Scanner;


public class CmdLogic {
    private Scanner input;
    private RequestHandler requestHandler;
    private String firstName;
    private String lastName;
    private String username;
    private String isBot;
    private String id;

    public CmdLogic(String firstName, String lastName, String username, String isBot, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.isBot = isBot;
        this.id = id;
        requestHandler = new RequestHandler();
        input = new Scanner(System.in);
    }

    public void responseForCDM() {
        while(true) {
            String messageFromCMD = input.nextLine();
            JSONObject info;
            StringBuilder answer = new StringBuilder("");

            if (messageFromCMD.charAt(0) == '/') {
                String userTextMessage = requestHandler.formatCommandFromTelegram(messageFromCMD);
                answer.append(requestHandler.useCommand(userTextMessage));
            } else {
                info = requestHandler.getInfo(firstName, lastName, username, isBot, id);
                answer.append(requestHandler.toAnswer(messageFromCMD, info));
            }

            System.out.println(answer);
        }
    }
}
