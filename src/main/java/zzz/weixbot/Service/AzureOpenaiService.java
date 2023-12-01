package zzz.weixbot.Service;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.stereotype.Service;
import zzz.weixbot.domain.AzureOpenaiQna;
import zzz.weixbot.domain.vo.PromptVo;
import zzz.weixbot.redis.RedisService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static zzz.weixbot.common.Constant.SYSTEM_MSG;
import static zzz.weixbot.common.ResourceName.getResourceName;

@Service
public class AzureOpenaiService {
    @Resource
    private RedisService redisService;

    public static final String key = "digediao";
    /**
     * azure openai
     * @param azureOpenaiQna
     * @throws IOException
     */
    public String openaiQna(AzureOpenaiQna azureOpenaiQna){
//        获取azure openai参数
        String AZURE_OPENAI_ENDPOINT = getResourceName("AZURE_OPENAI_ENDPOINT");
        String AZURE_OPENAI_API_KEY = getResourceName("AZURE_OPENAI_API_KEY");
        String DEPLOYMENT_MODEL = getResourceName("DEPLOYMENT_MODEL");

        String answer = null;

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
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM, SYSTEM_MSG));

//        构建prompt
        if(!azureOpenaiQna.getPrompt().isEmpty()){
            for(PromptVo prompt : azureOpenaiQna.getPrompt()){
                chatMessages.add(new ChatMessage(ChatRole.USER, prompt.getPromptQuestion()));
                chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, prompt.getPromptAnswer()));
            }
        }
        getPrompt(chatMessages);

        chatMessages.add(new ChatMessage(ChatRole.USER, azureOpenaiQna.getQuestion()));

//        构建openai gpt模型
        ChatCompletionsOptions options = new ChatCompletionsOptions(chatMessages)
                .setFrequencyPenalty(0.0)
                .setMaxTokens(1500)
                .setTemperature(0.7);
        ChatCompletions chatCompletions = client.getChatCompletions(DEPLOYMENT_MODEL, options);

        // 检查是否成功获取到答案
        if (chatCompletions != null && chatCompletions.getChoices() != null && !chatCompletions.getChoices().isEmpty()) {
            for (ChatChoice choice : chatCompletions.getChoices()) {
                ChatMessage message = choice.getMessage();
                answer = message.getContent();
            }

//            将问答数据存入redis
            redisService.setCacheMapValue(key,azureOpenaiQna.getQuestion(),answer);
            redisService.expire(key,1, TimeUnit.DAYS);

            // 输出答案
            System.out.println("openai: " + answer);
        } else {
            // 调用失败或未返回答案
            System.out.println("调用azure openai失败");
        }
//        CompletionsUsage usage = chatCompletions.getUsage();
//        System.out.printf("prompt使用的token数： %d, " + "回复的token数： %d, 总的token数： %d.%n",
//                usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
        return answer;
    }
    private void getPrompt(List<ChatMessage> chatMessages){
        Map<String, String> cacheMap = redisService.getCacheMap(key);

        for(Map.Entry<String, String> entry : cacheMap.entrySet()){
            chatMessages.add(new ChatMessage(ChatRole.USER, entry.getKey()));
            chatMessages.add(new ChatMessage(ChatRole.ASSISTANT, entry.getValue()));
        }
    }

    /**
     * 清除当前对话窗口缓存
     */
    public void clearHistory(){
        redisService.deleteObject(key);
    }
}
