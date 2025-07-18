package test.tree2.dingtalk.bean;

/**
 * Created on 2019/6/12 14:15
 * by @author WeiBoWen
 */
public class DingtalkDepartment {

    /**
     * 钉钉自动生成的部门id
     */
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父部门id，根部门为1
     */
    private Long parentid;

    /**
     * 当前部门在父部门下的所有子部门中的排序值
     */
    private Long order;

    /**
     * 部门标识字段，开发者可用该字段来唯一标识一个部门，并与钉钉外部通讯录里的部门做映射
     */
    private String sourceIdentifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getSourceIdentifier() {
        return sourceIdentifier;
    }

    public void setSourceIdentifier(String sourceIdentifier) {
        this.sourceIdentifier = sourceIdentifier;
    }

    @Override
    public String toString() {
        return "DingtalkDepartment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentid=" + parentid +
                ", order=" + order +
                ", sourceIdentifier='" + sourceIdentifier + '\'' +
                '}';
    }
}
