package connector;

import org.json.JSONObject;
import requestHandler.RequestHandler;
import java.util.Scanner;


public class CmdLogic {
    private Scanner input;
    private RequestHandler requestHandler;
    private String firstName;
    private String lastName;

    public CmdLogic(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        requestHandler = new RequestHandler();
        input = new Scanner(System.in);
    }

    public void responseForCDM() {
        while(true) {
            String messageFromCMD = input.nextLine();
            JSONObject info;
            StringBuilder answer = new StringBuilder("");
            String[] messageWords = messageFromCMD.split(" ");
            if (messageWords[0].charAt(0) == '/') {
                if(messageWords.length == 1){
                String userTextMessage = requestHandler.formatCommandFromTelegram(messageWords[0]);
                answer.append(requestHandler.useCommand(userTextMessage));
                } else{
                    // реализовать передачу остальных слов из запроса в метод useCommand
                    continue;
                }
            }else {
                info = requestHandler.getInfo(firstName, lastName);
                answer.append(requestHandler.toAnswer(messageFromCMD, info));
            }

            System.out.println(answer);
        }
    }
}
