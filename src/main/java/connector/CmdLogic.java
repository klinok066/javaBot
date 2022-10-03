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
            StringBuilder answer = new StringBuilder("");
            JSONObject info = requestHandler.getInfo(firstName, lastName);

            answer.append(requestHandler.onUse(messageFromCMD, info));

            System.out.println(answer);
        }
    }
}
