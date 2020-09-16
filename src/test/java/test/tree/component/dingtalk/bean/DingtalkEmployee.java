package test.tree.component.dingtalk.bean;

import java.util.Date;
import java.util.List;

/**
 * Created on 2019/6/11 14:18
 * by @author JeramTough
 */
public class DingtalkEmployee {

    /**
     * 员工在当前企业内的唯一标识，也称staffId。可由企业在创建时指定，并代表一定含义比如工号，创建后不可修改
     */
    private String userId;

    /**
     *员工在当前开发者企业账号范围内的唯一标识，系统生成，固定值，不会改变
     */
    private String unionid	;


    /**
     *员工名字
     */
    private String name	;


    /**
     *分机号（仅限企业内部开发调用）
     */
    private String tel	;


    /**
     *办公地点
     */
    private String workPlace	;


    /**
     *备注
     */
    private String remark	;


    /**
     *手机号码
     */
    private String mobile	;


    /**
     *员工的电子邮箱
     */
    private String email	;


    /**
     *员工的企业邮箱，如果员工已经开通了企业邮箱，接口会返回，否则不会返回
     */
    private String orgEmail	;


    /**
     *是否已经激活，true表示已激活，false表示未激活
     */
    private boolean isActived	;


    /**
     *在对应的部门中的排序，Map结构的json字符串，key是部门的Id，value是人员在这个部门的排序值
     */
    private String orderInDepts	;


    /**
     *是否为企业的管理员，true表示是，false表示不是
     */
    private boolean isAdmin	;


    /**
     *是否为企业的老板，true表示是，false表示不是
     */
    private boolean isBoss	;

    /**
     *成员所属部门id列表
     */
    private List<Long> department	;

    /**
     *	职位信息
     */
    private String position;

    /**
     *头像url
     */
    private String avatar;

    /**
     *入职时间。Unix时间戳 （在OA后台通讯录中的员工基础信息中维护过入职时间才会返回)
     */
    private Date hiredDate;

    /**
     *员工工号
     */
    private String jobnumber;

    /**
     *扩展属性，可以设置多种属性（但手机上最多只能显示10个扩展属性，具体显示哪些属性，
     * 请到OA管理后台->设置->通讯录信息设置和OA管理后台->设置->手机端显示信息设置）
     */
    private String extattr;









    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }


    public String getOrderInDepts() {
        return orderInDepts;
    }

    public void setOrderInDepts(String orderInDepts) {
        this.orderInDepts = orderInDepts;
    }


    public List<Long> getDepartment() {
        return department;
    }

    public void setDepartment(List<Long> department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(Date hiredDate) {
        this.hiredDate = hiredDate;
    }

    public String getJobnumber() {
        return jobnumber;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    public String getExtattr() {
        return extattr;
    }

    public void setExtattr(String extattr) {
        this.extattr = extattr;
    }

    public boolean isActived() {
        return isActived;
    }

    public void setIsActived(boolean actived) {
        isActived = actived;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isBoss() {
        return isBoss;
    }

    public void setIsBoss(boolean isBoss) {
        this.isBoss = isBoss;
    }

    @Override
    public String toString() {
        return "DingtalkEmployee{" +
                "userId='" + userId + '\'' +
                ", unionid='" + unionid + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", workPlace='" + workPlace + '\'' +
                ", remark='" + remark + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", orgEmail='" + orgEmail + '\'' +
                ", isActived=" + isActived +
                ", orderInDepts='" + orderInDepts + '\'' +
                ", isAdmin=" + isAdmin +
                ", isBoss=" + isBoss +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", avatar='" + avatar + '\'' +
                ", hiredDate=" + hiredDate +
                ", jobnumber='" + jobnumber + '\'' +
                ", extattr='" + extattr + '\'' +
                '}';
    }
}
