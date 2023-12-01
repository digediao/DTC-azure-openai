package zzz.weixbot.domain;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author digediao
 * @version 1.0
 * @description TODO
 * @Date 2023/12/1 14:44
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Node(labels = "step")
public class DTCEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String code;

    @Property
    private String data;

    @Property
    private String type;

    @JSONField(serialize = false)
    @Property
    private List<DTCEntity> next;

}
