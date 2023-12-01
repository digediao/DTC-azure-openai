package zzz.weixbot.Controller;

import org.springframework.web.bind.annotation.*;
import zzz.weixbot.Service.AzureOpenaiService;
import zzz.weixbot.common.R;
import zzz.weixbot.domain.AzureOpenaiQna;

import javax.annotation.Resource;

@RestController
@RequestMapping("/openai")
public class AzureOpenaiController {
    @Resource
    private AzureOpenaiService azureOpenaiService;

    @PostMapping("/qna")
    public R openai(@RequestBody AzureOpenaiQna azureOpenaiQna){
        final String[] answer = {null};
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    answer[0] = azureOpenaiService.openaiQna(azureOpenaiQna);
                }
            }).start();
        }catch (Exception e) {
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
