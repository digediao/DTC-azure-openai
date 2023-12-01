package zzz.weixbot.dao;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import zzz.weixbot.domain.DTCEntity;

/**
 * @author digediao
 * @version 1.0
 * @description TODO DTC汽车维修mapper
 * @Date 2023/12/1 14:48
 */
@Repository
public interface DTCMapper extends Neo4jRepository<DTCEntity, Long> {
    
}
