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
//        List<DTCEntity> entities = dtcService.convertJsonToEntities("C:\\Users\\60131\\Downloads\\Autel to 微软AIGC验证资料20231123\\1.json");
        List<DTCEntity> entities = dtcService.convertJsonToEntities("C:\\Users\\低格调\\Downloads\\Autel to 微软AIGC验证资料20231123\\预处理数据\\无人工输出答案\\C044A\\cellHandler.do_cellId=184743(Circuit_System Verification).json");
        entities.forEach(System.out::println);
        dtcService.createDTCNodes(entities);
        return R.ok(entities);
    }

    @GetMapping("/getSteps")
    public R getSteps(){
//        List<DTCEntity> entities = dtcService.convertJsonToEntities("C:\\Users\\60131\\Downloads\\Autel to 微软AIGC验证资料20231123\\1.json");
        List<DTCEntity> entities = dtcService.convertJsonToEntities("C:\\Users\\低格调\\Downloads\\Autel to 微软AIGC验证资料20231123\\预处理数据\\无人工输出答案\\C044A\\cellHandler.do_cellId=184743(Circuit_System Verification).json");
        String result = dtcService.jsonPayload(entities);
        return R.ok(result);
    }
}
