package zzz.weixbot;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
import com.azure.core.credential.AzureKeyCredential;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import zzz.weixbot.common.ResourceName;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static zzz.weixbot.common.ResourceName.getResourceName;


public class openai {
    public static void main(String[] args) throws Exception {
//        while (true){
//            Scanner scanner = new Scanner(System.in);
//            System.out.print("请输入问题:");
//            String question = scanner.nextLine();
//            try {
//                System.out.println(openai(question,customBotRequest(question)));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.printf("答案:%s%n", customBotRequest("低格调是谁"));
//        String question = "人事";
//        System.out.printf("openai回答：%s%n",openai(question,""));
    }

    /**
     * azure openai
     * @param question
     * @throws IOException
     */
    public static String openai(String question,String prompt_answer){
//        prompt_answer = "低格调是中国人，他是个网名";
//        获取azure openai参数
        String AZURE_OPENAI_ENDPOINT = getResourceName("AZURE_OPENAI_ENDPOINT");
        String AZURE_OPENAI_API_KEY = getResourceName("AZURE_OPENAI_API_KEY");
        String DEPLOYMENT_MODEL = getResourceName("DEPLOYMENT_MODEL");

//        创建openai的连接
        OpenAIClient client = new OpenAIClientBuilder()
                .endpoint(AZURE_OPENAI_ENDPOINT)
                .credential(new AzureKeyCredential(AZURE_OPENAI_API_KEY))
                .buildClient();

        /**
         * 创建消息角色
         */
        List<ChatMessage> chatMessages = new ArrayList<>();
//        系统角色
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, "你要服从你之前的回答，并对下一次用户的提问进行个性化回复。"));
        chatMessages.add(new ChatMessage(ChatRole.USER, question));
        chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, prompt_answer));
        chatMessages.add(new ChatMessage(ChatRole.USER, question));

//        构建openai gpt模型
        ChatCompletionsOptions options = new ChatCompletionsOptions(chatMessages)
                .setFrequencyPenalty(0.0)
                .setMaxTokens(1500)
                .setTemperature(0.7);
        ChatCompletions chatCompletions = client.getChatCompletions(DEPLOYMENT_MODEL, options);

//        System.out.printf("Model ID=%s 创建于： %s.%n", chatCompletions.getId(), chatCompletions.getCreatedAt());
        String answer = null;
        for (ChatChoice choice : chatCompletions.getChoices()) {
            ChatMessage message = choice.getMessage();
            answer = message.getContent();
        }
        CompletionsUsage usage = chatCompletions.getUsage();
//        System.out.printf("prompt使用的token数： %d, " + "回复的token数： %d, 总的token数： %d.%n",
//                usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());

        System.out.println("知识库回答："+prompt_answer);
        return answer;
    }

    @Test
    public void test1(){
        String document = "Circuit/System Verification\n" +
                "Verify that the stop lamps operate properly and that no B22 Brake Pedal Position Sensor related DTCs are set.\n" +
                "If brake pedal position sensor related DTCs are set\n" +
                "Refer to Diagnostic Trouble Code (DTC) List - Vehicle\n" +
                "\n" +
                "If no brake pedal position sensor related DTCs are set\n" +
                "Verify that no DTCs are set, except DTC P0571.\n" +
                "If any other DTC is set, except DTC P0571\n" +
                "Refer to Diagnostic Trouble Code (DTC) List - Vehicle.\n" +
                "\n" +
                "If only DTC P0571 is set\n" +
                "Replace the K20 Engine Control Module.\n" +
                "Verify DTC P0571 does not set when operating the vehicle within the conditions for running the DTC.\n" +
                "If the DTC sets\n" +
                "Replace the K9 Body Control Module.\n" +
                "\n" +
                "If the DTC does not set\n" +
                "All OK.";

        String question = "P0571";

        extractAndOutput(document, question);
    }
    public static void extractAndOutput(String document, String question) {
        // 利用正则表达式提取与问题相关的部分
        String patternString = question + "\\n(.*?)(?=\\n\\n\\w|\\Z)";
        Pattern pattern = Pattern.compile(patternString, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(document);

        // 格式化输出
        int count = 1;
        while (matcher.find()) {
            String match = matcher.group(1).trim();
            String[] solutionLines = match.split("\\n");
            System.out.println(count++ + "、");
            for (String line : solutionLines) {
                System.out.println("\"" + line.trim() + "\", ");
            }
            System.out.println();
        }
    }
}

