package com.yechtech.dac.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 *
 * @author James
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名（显示）
     */
    private String fullName;

    /**
     * 用户名
     */
    private String username;

    /**
     * Email
     */
    private String email;

    /**
     * 部门
     */
    private String department;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    /**
     * tableau同步的用户的id
     */
    private String tableauId;

    /**
     * 用户唯一标识
     */
    private String uid;

    /**
     * 用户状态
     */
    private Integer status;

    private Boolean belongStore;

    /**
     * 用户psId
     */
    private String psid;

    /**
     * 同步userid
     */
    private String userId;

    /**
     * tableau组id
     */
    private Long groupId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
