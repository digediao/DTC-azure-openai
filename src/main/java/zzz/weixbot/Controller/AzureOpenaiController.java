package zzz.weixbot.Controller;

import org.springframework.web.bind.annotation.*;
import zzz.weixbot.Service.AzureOpenaiService;
import zzz.weixbot.Service.DTCService;
import zzz.weixbot.common.R;
import zzz.weixbot.domain.AzureOpenaiQna;
import zzz.weixbot.domain.DTCEntity;
import zzz.weixbot.domain.vo.PromptVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/openai")
public class AzureOpenaiController {
    @Resource
    private AzureOpenaiService azureOpenaiService;
    @Resource
    private DTCService dtcService;

    @PostMapping("/qna")
    public R openai(@RequestBody AzureOpenaiQna azureOpenaiQna) {
//        格式化prompt
        List<PromptVo> promptList = new ArrayList<>();
        List<DTCEntity> entities = dtcService.convertJsonToEntities("C:\\Users\\低格调\\Downloads\\Autel to 微软AIGC验证资料20231123\\预处理数据\\无人工输出答案\\C044A\\cellHandler.do_cellId=184743(Circuit_System Verification).json");
        String jsonPayload = dtcService.jsonPayload(entities);
        promptList.add(PromptVo.builder()
                .promptQuestion("C044A怎么解决\n" + jsonPayload)
                .promptAnswer("方案一: 执行轮胎压力指示器传感器学习程序具体操作方案:\n" +
                        "1.进行电路/系统验证\n" +
                        "2.执行轮胎压力指示器传感器学习程序。3.验证故障码CO775未被设置\n" +
                        "方案二:更换K9车身控制模块\n" +
                        "具体操作方案:\n" +
                        "4.进行电路/系统验证\n" +
                        "5.执行轮胎压力指示器传感器学习程序。\n" +
                        "6.如果故障码CO775仍然被设置，则更换K9车身控制模块")
                .build()
        );
        azureOpenaiQna.setPrompt(promptList);

        final String[] answer = {null};
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    answer[0] = azureOpenaiService.openaiQna(azureOpenaiQna);
                }
            });
            thread.start();
            thread.join(); // 等待新线程执行完毕
        } catch (Exception e) {
            System.out.println(e);
        }
        return R.ok(answer[0]);
    }

        @GetMapping("/clearHistory")
    public R clearHistory(){
        azureOpenaiService.clearHistory();
        return R.ok();
    }
}
