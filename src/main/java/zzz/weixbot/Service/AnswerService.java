package zzz.weixbot.Service;

import cn.hutool.http.HttpRequest;
//import org.apache.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zzz.weixbot.redis.RedisService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static zzz.weixbot.common.ResourceName.getResourceName;

//@Service
//@Slf4j
public class AnswerService {
    @Resource
    private RedisService redisService;

    /**
     * Azure OpenAI + 文本分析知识库 + 认知搜索：语义搜索
     * @param question 问题
     * @param key  历史记录的key（AgentId:CorpId）
     * @return openai回答
     */
//    public String answer(String question,String key){
//        List<DataSource> dataSources = new ArrayList<>();
//        DataSource dataSource = new DataSource();
//        Parameter parameter = new Parameter();
//
//        parameter.setKey(getResourceName("SEARCH_KEY"));
//        parameter.setEndpoint(getResourceName("SEARCH_ENDPOINT"));
//        parameter.setIndexName(getResourceName("SEARCH_INDEX_NAME"));
//
//        dataSource.setType("AzureCognitiveSearch");
//        dataSource.setStrictness(5);
//        dataSource.setParameters(parameter);
//        dataSources.add(dataSource);
//
//        List<Message> messages = new ArrayList<>();
////        历史记录作为提示词构建
//        Map<String, String> cacheMap = redisService.getCacheMap(key);
//        if (cacheMap != null) {
//            for (Map.Entry<String, String> entry : cacheMap.entrySet()) {
//                messages.add(Message.builder().role("user").content(entry.getKey()).build());
//                messages.add(Message.builder().role("assistant").content(entry.getValue()).build());
//            }
//        }
//
////         消息内容构建
//        Message message = new Message();
//        message.setRole("user");
//        message.setContent(question);
//        messages.add(message);
//
//        answerBody answerBody = zzz.weixbot.domain.answerBody.builder()
//                .dataSources(dataSources)
//                .messages(messages)
//                .build();
//
//
//        String url = getResourceName("AZURE_OPENAI_ENDPOINT")
//                + "/openai/deployments/"
//                + getResourceName("DEPLOYMENT_MODEL")
//                + "/extensions/chat/completions?api-version=2023-06-01-preview";
//
//        String body = HttpRequest.post(url).header("api-key", getResourceName("AZURE_OPENAI_API_KEY"))
//                .body(JSON.toJSONString(answerBody)).execute().body();
//
////        获取引用文档内容
//        JSON.parseObject(body).getJSONArray("choices")
//                .getJSONObject(0)
//                .getJSONArray("messages")
//                .getJSONObject(0)
//                .getString("content");
//        List<String> documents = new ArrayList<>();
//
//        /**
//         * TODO
//         * 1、出现问题需要replace：The requested information is not available in the retrieved data. Please try another query or topic.
//         * 2、个性化回答
//         * 3、引用文档显示:获取body中的tool字段文档内容，并将返回结果中的[doc1]转为文档链接放置末尾
//         * 4、清空按钮和保留历史数据
//         */
//
////        获取答案
//        String answer = JSON.parseObject(body).getJSONArray("choices")
//                .getJSONObject(0)
//                .getJSONArray("messages")
//                .getJSONObject(1)
//                .getString("content");
//
////        if(answer.contains(answer_error)){
////            answer = answer_error_replace;
////        }
//
//        return answer;
//    }

}
