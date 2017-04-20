package me.cuijing.entity.system;

/**
 * 用户角色
 * @version 1.0
 * @author: CUI
 * @date： 2017/4/6
 */
public class Role {
    private String ROLE_ID;    // 角色编号 (新生成: UUID 或者 初始自定义:数字)
    private String ROLE_NAME;  // 角色名称
    private String RIGHTS;     // 权限集合字符串
    private String PARENT_ID;  // 父级角色ID 为0的话,表示 此 role 为角色组
    private String ADD_QX;     // 添加权限
    private String DEL_QX;     // 删除权限
    private String EDIT_QX;    // 修改权限
    private String CHA_QX;     // 查找权限

    public String getROLE_ID() {
        return ROLE_ID;
    }

    public void setROLE_ID(String rOLE_ID) {
        ROLE_ID = rOLE_ID;
    }

    public String getROLE_NAME() {
        return ROLE_NAME;
    }

    public void setROLE_NAME(String rOLE_NAME) {
        ROLE_NAME = rOLE_NAME;
    }

    public String getRIGHTS() {
        return RIGHTS;
    }

    public void setRIGHTS(String rIGHTS) {
        RIGHTS = rIGHTS;
    }

    public String getPARENT_ID() {
        return PARENT_ID;
    }

    public void setPARENT_ID(String pARENT_ID) {
        PARENT_ID = pARENT_ID;
    }

    public String getADD_QX() {
        return ADD_QX;
    }

    public void setADD_QX(String aDD_QX) {
        ADD_QX = aDD_QX;
    }

    public String getDEL_QX() {
        return DEL_QX;
    }

    public void setDEL_QX(String dEL_QX) {
        DEL_QX = dEL_QX;
    }

    public String getEDIT_QX() {
        return EDIT_QX;
    }

    public void setEDIT_QX(String eDIT_QX) {
        EDIT_QX = eDIT_QX;
    }

    public String getCHA_QX() {
        return CHA_QX;
    }

    public void setCHA_QX(String cHA_QX) {
        CHA_QX = cHA_QX;
    }


}
