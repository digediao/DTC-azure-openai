package zzz.weixbot.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zzz.weixbot.Service.DTCService;
import zzz.weixbot.common.R;
import zzz.weixbot.domain.DTCEntity;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author digediao
 * @version 1.0
 * @description TODO
 * @Date 2023/12/1 15:36
 */

@RestController
@RequestMapping("/dtc")
public class DTCController {
    @Resource
    private DTCService dtcService;

    @GetMapping("/create")
    public R createDTCNodes(){
        List<DTCEntity> entities = dtcService.convertJsonToEntities("C:\\Users\\60131\\Downloads\\Autel to 微软AIGC验证资料20231123\\1.json");
        entities.forEach(System.out::println);
        dtcService.createDTCNodes(entities);
        return R.ok(entities);
    }

    @GetMapping("/getSteps")
    public R getSteps(){
        List<DTCEntity> entities = dtcService.convertJsonToEntities("C:\\Users\\60131\\Downloads\\Autel to 微软AIGC验证资料20231123\\1.json");
        String result = dtcService.jsonPayload(entities);
        return R.ok(result);
    }
}
