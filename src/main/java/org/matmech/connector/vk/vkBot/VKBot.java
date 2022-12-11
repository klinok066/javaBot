package org.matmech.connector.vk.vkBot;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.users.User;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import org.matmech.connector.Connector;
import org.matmech.userData.UserData;
import org.matmech.requests.requestHandler.RequestHandler;

import java.util.List;
import java.util.Random;

public class VKBot implements Connector {
    private final int GROUP_ID;
    private final String ACCESS_TOKEN;
    private final RequestHandler REQUEST_HANDLER;

    public VKBot(int groupId, String accessToken, RequestHandler requestHandler) {
        GROUP_ID = groupId;
        ACCESS_TOKEN = accessToken;
        REQUEST_HANDLER = requestHandler;
    }

    /**
     *
     */
    @Override
    public void start() {
        try {
            TransportClient transportClient = new HttpTransportClient();
            VkApiClient vk = new VkApiClient(transportClient);
            Random random = new Random();
            GroupActor actor = new GroupActor(GROUP_ID, ACCESS_TOKEN);
            Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();

            while (true) {
                MessagesGetLongPollHistoryQuery historyQuery = vk.messages().getLongPollHistory(actor).ts(ts);
                List<Message> messages = historyQuery.execute().getMessages().getItems();

                if (!messages.isEmpty()) {
                    messages.forEach(message -> {
                        System.out.println(message.toString());

                        try {
                            User user = vk.users().get(actor).userIds(
                                    String.valueOf(message.getFromId())
                            ).execute().get(0);

                            UserData data = new UserData(
                                    user.getFirstName(),
                                    user.getLastName(),
                                    user.getFirstName() + user.getLastName() + user.getId(),
                                    message.getPeerId()
                            );

                            List<String> messagesTexts = REQUEST_HANDLER.execute(message.getText(), data);

                            for (String messageTxt : messagesTexts) {
                                vk.messages().send(actor).message(messageTxt)
                                        .userId(message.getFromId())
                                        .randomId(random.nextInt(10000))
                                        .execute();
                            }
                        } catch (ClientException | ApiException e) {
                            System.out.println(e.getMessage());
                        }
                    });
                }

                ts = vk.messages().getLongPollServer(actor).execute().getTs();
                Thread.sleep(500);
            }
        } catch (ClientException | ApiException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
