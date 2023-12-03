package zzz.weixbot.Service;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import zzz.weixbot.dao.DTCMapper;
import zzz.weixbot.domain.DTCEntity;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author digediao
 * @version 1.0
 * @description TODO DTC汽车维修业务层
 * @Date 2023/12/1 14:52
 */
@Service
public class DTCService {
    @Resource
    private DTCMapper dtcMapper;

    /**
     * 通过索引文档创建完整DTC节点
     */
    public void createDTCNodes(List<DTCEntity> entities) {
        dtcMapper.saveAll(entities);
    }

    /**
     * 导入json文件并解析
     */
    public List<DTCEntity> convertJsonToEntities(String jsonFilePath) {
        DTCEntity entity = null;
        List<DTCEntity> result = new ArrayList<>();
        try {
            String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
            entity = JSON.parseObject(jsonContent, DTCEntity.class);
//            递归将数据放入节点
            processEntity(entity,result);

        } catch (Exception e) {
            throw new RuntimeException("Error reading JSON file", e);
        }
        return result;
    }

    /**
     * 递归将数据放入节点
     * @param entity 节点数据
     * @param result 节点数据集
     */
    private static void processEntity(DTCEntity entity,List<DTCEntity> result) {
        if (entity != null) {
            result.add(entity);

            // 递归处理 next 字段
            List<DTCEntity> nextEntities = entity.getNext();
            if (nextEntities != null && !nextEntities.isEmpty()) {
                for (DTCEntity nextEntity : nextEntities) {
                    processEntity(nextEntity,result);
                }
            }
        }
    }

    /**
     * 根据code输出json格式数据
     */
    public String jsonPayload(List<DTCEntity> entities){
        DTCEntity dtcEntity = null;
        String result = null;
//        查找相应位置并返回后续所有节点数据
        for (DTCEntity entity : entities) {
            if(entity.getId().contains("S003")){
                dtcEntity = entity;
                break;
            }
        }
        if(dtcEntity != null){
//            存在下一级
            result = JSON.toJSONString(dtcEntity);
        }
        return result;
    }
}

