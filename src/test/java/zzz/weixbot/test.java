package zzz.weixbot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
//import zzz.weixbot.Service.AnswerService;
import zzz.weixbot.Service.DTCService;
import zzz.weixbot.domain.DTCEntity;

import javax.annotation.Resource;

import java.util.List;

import static zzz.weixbot.common.Constant.AgentId;
import static zzz.weixbot.common.Constant.CorpID;


@SpringBootTest
public class test {
//    @Resource
//    private AnswerService answerService;
    @Resource
    private DTCService dtcService;

//    @Resource
//    private PersonService personService;

//    @Test
//    public void test1(){
//        System.out.println(answerService.answer("how to fix P0571", AgentId + ":" + CorpID));
//    }

//    @Test
//    public void test2(){
//        personService.saveNode(Person.builder().name("人事").build());
//    }

//    @Test
//    public void testRelation(){
//        personService.createRelation("低格调", "张越凯","同事");
//    }
//
//    @Test
//    public void testCreateRelations(){
//        List<PersonRelationShip> list = personService.createRelationShipByName("陈柳鱼");
//        System.out.println("输出:");
//        list.forEach(System.out::println);
//    }
//
//    @Test
//    public void testCreateAllRelation(){
//        personService.createRelationShip().forEach(System.out::println);
//    }
    @Test
    public void test1(){
        List<DTCEntity> entities = dtcService.convertJsonToEntities("1.json");
        entities.forEach(System.out::println);
        dtcService.createDTCNodes(entities);
    }
}
