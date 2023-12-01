/*
 *  Copyright (c) 2023. felord.cn
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *       https://www.apache.org/licenses/LICENSE-2.0
 *  Website:
 *       https://felord.cn
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package zzz.weixbot.Controller;

import zzz.weixbot.Config.*;
import com.riversoft.weixin.common.decrypt.MessageDecryption;
import com.riversoft.weixin.qy.base.CorpSetting;
import com.riversoft.weixin.qy.message.Messages;
import com.riversoft.weixin.qy.message.QyXmlMessages;
import com.riversoft.weixin.qy.message.json.TextMessage;
import com.riversoft.weixin.qy.request.QyTextRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
//import zzz.weixbot.Service.AnswerService;
import zzz.weixbot.redis.RedisService;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static zzz.weixbot.common.Constant.*;

@Slf4j
@RestController
@RequestMapping("/wecom/callback")
public class WeixinQyController {
    /**
     * token/aesKey 是和企业号应用绑定的，这里为了演示方便使用外部注入，
     * 实际使用的时候一个企业号可能有多个应用，这样的话需要有个分发逻辑。
     * 比如callback url可以定义为  /wx/qy/[应用的ID]，通过ID查询不同的token和aesKey
     */

//    @Resource
//    private AnswerService answerService;
    @Resource
    private RedisService redisService;

    /**
     * 验证回调URL
     *
     * @param msgSignature 签名  能保证唯一
     * @param timestamp    时间戳
     * @param nonce        nonce串 防止重放攻击
     * @param echostr      随机串
     * @return the long
     */
    @GetMapping("/wechat")
    public String verifyUrl(
            @RequestParam("msg_signature") String msgSignature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echostr) {

        String sEchoStr = null; //需要返回的明文
        try {
            // 初始化WXBizMsgCrypt
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(Token, EncodingAESKey, CorpID);

            // 验证回调URL
            sEchoStr = wxcpt.VerifyURL(msgSignature, timestamp, nonce, echostr);

            // 验证成功，返回echostr

            return sEchoStr;
        } catch (AesException e) {
            // 验证失败，记录错误日志
            log.error("Error during URL verification: {}", e.getMessage(), e);
            return sEchoStr;
        }
    }


    /**
     * @return
     */
//    @PostMapping("/wechat")
//    @ResponseBody
//    public String qy(@RequestParam(value = "msg_signature") String signature,
//                     @RequestParam(value = "timestamp") String timestamp,
//                     @RequestParam(value = "nonce") String nonce,
//                     @RequestParam(value = "echostr", required = false) String echostr,
//                     @RequestBody(required = false) String content) {
//
//        try {
//            MessageDecryption messageDecryption = new MessageDecryption(Token, EncodingAESKey, CorpID);
//            if (!StringUtils.isBlank(echostr)) {
//
//                String echo = messageDecryption.decryptEcho(signature, timestamp, nonce, echostr);
//                log.info("消息签名验证成功.");
//                return echo;
//            } else {
//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        QyTextRequest xmlRequest = null;
//                        try {
//
//                            /**
//                             * TODO
//                             * 1、出现问题需要replace：The requested information is not available in the retrieveddata. Please try another query or topic.
//                             * 2、个性化回答
//                             * 3、引用文档显示
//                             * 4、清空按钮和保留历史数据
//                             */
//                            xmlRequest = (QyTextRequest) QyXmlMessages.fromXml(messageDecryption.decrypt(signature, timestamp, nonce, content));
//
//                            String key = AgentId + ":" + CorpID;
//                            String answer = answerService.answer(xmlRequest.getContent(),key);
//
//                            TextMessage textMessage = new TextMessage();
//                            textMessage.text(answer).safe().toUser(xmlRequest.getToUser()).toParty("1|2").toTag("1|2").agentId(Integer.parseInt(AgentId));
//                            CorpSetting corpSetting = new CorpSetting(CorpID, Secret);
//                            Messages.with(corpSetting).send(textMessage);
//
//                            if (redisService.getCacheObject(AgentId) != null) {
//                                Map<String, String> data = redisService.getCacheMap(key);
//                                data.put(xmlRequest.getContent(), answer);
//                            } else {
//                                redisService.setCacheObject(key, new HashMap<String, String>()
//                                        .put(xmlRequest.getContent(), answer));
//                            }
//                        } catch (Exception e) {}
//                    }
//                }).start();
//            }
//        } catch (Exception e) {
//            log.error("callback failed.", e);
//        }
//        return null;
//    }

}
