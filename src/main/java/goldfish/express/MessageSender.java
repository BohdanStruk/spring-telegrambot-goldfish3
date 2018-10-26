package goldfish.express;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MessageSender {
    private String url;
    List<NameValuePair> nameValuePairs;
    private static final String TOKEN = "put your token";
    private  String webHookUrl = "https://api.telegram.org/bot"+ TOKEN +"/setWebhook?url=";
    private static final String BASEURL = "https://api.telegram.org/bot";
    private static final String PATH = "sendmessage";
    private static final String CHATID_FIELD = "chat_id";
    private static final String TEXT_FIELD = "text";
    private static final String KEYBOARD_FIELD = "keyboard";
    private static List<List<String>> keyboard;
    private static final String RESIZEKEYBOARD_FIELD = "resize_keyboard";
    private static Boolean resizeKeyboard;
    private static final String ONETIMEKEYBOARD_FIELD = "one_time_keyboard";
    private static Boolean oneTimeKeyboad;
    private static final String SELECTIVE_FIELD = "selective";
    private static Boolean selective;


    /**
    Цей метод створює MessageSender для приєднянна webHook, він приймає посилання сервера.
     */
    MessageSender(String serverGetMessageUrl) {
        nameValuePairs = new ArrayList<>();
        if (!serverGetMessageUrl.isEmpty()){
            webHookUrl = webHookUrl + serverGetMessageUrl;
            url = BASEURL + TOKEN + "/" + PATH;
            url = webHookUrl;
        }
    }

    /**
     * Цей метод створює MessageSender і готує його до запитів для телеграму щоб він відправляв повідомлення.
     */
    public MessageSender() {
        nameValuePairs = new ArrayList<>();
        url = BASEURL + TOKEN + "/" + PATH;
    }



    JSONObject jsonObject = new JSONObject();
      public void execute() {

        try {
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
            httppost.addHeader("charset", "UTF-8");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();


            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    System.out.println(entity.toString());
                } finally {
                    instream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Creates a JSONObject with the content of a ReplyMarkupKeyboard
     */
    public static JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        /// Convert the List<List<String>> to JSONArray
        JSONArray jsonkeyboard = new JSONArray();
        for (List<String> innerRow : keyboard) {
            JSONArray innerJSONKeyboard = new JSONArray();
            for (String element : innerRow) {
                innerJSONKeyboard.put(element);
            }
            jsonkeyboard.put(innerJSONKeyboard);
        }
        /// Add the converted list to final JSON object
        jsonObject.put(KEYBOARD_FIELD, jsonkeyboard);

        /// Add oneTimeKeyboard if necessary
        if (oneTimeKeyboad != null) {
            jsonObject.put(ONETIMEKEYBOARD_FIELD, oneTimeKeyboad);
        }
        /// Add ResizeKeyboard if necessary
        if (resizeKeyboard != null) {
            jsonObject.put(RESIZEKEYBOARD_FIELD, resizeKeyboard);
        }
        /// Add selective if necessary
        if (selective != null) {
            jsonObject.put(SELECTIVE_FIELD, selective);
        }

        return jsonObject;
    }

    public void enableMarkdown(boolean b) {
  //    if(b)  nameValuePairs.add(new BasicNameValuePair(REPLYMARKUP_FIELD, toJson().toString()));
    }

    public void setChatId(String chatId) {
        nameValuePairs.add(new BasicNameValuePair(CHATID_FIELD, chatId));
    }

   public void setReplyToMessageId(Long message_id) {
//     jsonObject.put(REPLYTOMESSAGEID_FIELD, message_id);
    }

    public void setText(String text) {
        nameValuePairs.add(new BasicNameValuePair(TEXT_FIELD, text));

    }
}

