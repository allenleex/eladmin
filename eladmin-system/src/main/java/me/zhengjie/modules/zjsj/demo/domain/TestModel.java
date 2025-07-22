package me.zhengjie.modules.zjsj.demo.domain;

import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntity;
import javax.persistence.*;
import java.io.Serializable;

/**
 * @author allenleex
 * @date 2025-07-22
 */
@Entity
@Getter
@Setter
@Table(name="test_model")
public class TestModel extends BaseEntity implements Serializable {
    @Id
	@Column(name = "id")
	@ApiModelProperty(value = "ID", hidden = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ApiModelProperty(value = "名称")
    private String name;

    public void copy(TestModel source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
