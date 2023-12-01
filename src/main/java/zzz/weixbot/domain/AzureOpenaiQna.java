package zzz.weixbot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zzz.weixbot.domain.vo.PromptVo;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AzureOpenaiQna {
//    用户提问
    private String question;

//    提示词
    private List<PromptVo> prompt;

}
