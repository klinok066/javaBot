package org.matmech;

import requestHandler.RequestHandler;
import java.util.Scanner;


public class cmdLogic {
    Scanner in = new Scanner(System.in);
    String answer = "";


    public void responceForCDM(){
        RequestHandler requestHandler = new RequestHandler();

            while(true){
                String messageFromCMD = in.nextLine();
                String[] words = messageFromCMD.split(" ");
                if (words[0].charAt(0) == '/' && words.length == 1){
                    words[0] = requestHandler.formatCommandFromTelegram(words[0]);
                }
                answer = requestHandler.useCommand(words[0]);
                System.out.println(answer);
        }
    }
}
