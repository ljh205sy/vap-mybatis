package net.codingme.boot.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liujinhui
 * date 2021/3/25 10:33
 */
@Data
@NoArgsConstructor
public class SysUserVO {
    private Integer id;
    private String username;
    private String password;
    private String name;

    public SysUserVO(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public SysUserVO(Integer id, String username, String password, String name) {
        this(username, password, name);
        this.id = id;
    }

    public SysUserVO(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
