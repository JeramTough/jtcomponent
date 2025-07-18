package test.tree2.channel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

/**
 * <p>
 * 栏目表
 * </p>
 *
 * @author shiyc
 * @since 2022-01-20
 */
public class Channel {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(
            using = ToStringSerializer.class
    )
    private Long id;
    private Long createBy;
    @JsonIgnore
    private Long updateBy;

    private Integer deleted;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            shape = JsonFormat.Shape.STRING
    )
    private Date createDate;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            shape = JsonFormat.Shape.STRING
    )
    private Date updateDate;

    private String code;

    private String name;

    private Long parentId;

    private Integer sq;

    private Long siteId;

    private String status;

    private String url;

    private String logo;

    private String displayName;

    private String chanCata;

    private String fullpathId;

    private String fullpathName;

    private String keyword;

    private String memo;

    private Integer isnav;

    private Integer ismap;

    private String chanType;

    private String ipLimit;

    private String redirectUrl;

    private Long orgId;

    private String deleteUserName;

    private String flowKey;

    private String maintainer;

    private String maintainerPhone;

    private Date publishedTime;

    private Date expiredTime;

    private Integer ableManuscript;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getSq() {
        return sq;
    }

    public void setSq(Integer sq) {
        this.sq = sq;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getChanCata() {
        return chanCata;
    }

    public void setChanCata(String chanCata) {
        this.chanCata = chanCata;
    }

    public String getFullpathId() {
        return fullpathId;
    }

    public void setFullpathId(String fullpathId) {
        this.fullpathId = fullpathId;
    }

    public String getFullpathName() {
        return fullpathName;
    }

    public void setFullpathName(String fullpathName) {
        this.fullpathName = fullpathName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getIsnav() {
        return isnav;
    }

    public void setIsnav(Integer isnav) {
        this.isnav = isnav;
    }

    public Integer getIsmap() {
        return ismap;
    }

    public void setIsmap(Integer ismap) {
        this.ismap = ismap;
    }

    public String getChanType() {
        return chanType;
    }

    public void setChanType(String chanType) {
        this.chanType = chanType;
    }

    public String getIpLimit() {
        return ipLimit;
    }

    public void setIpLimit(String ipLimit) {
        this.ipLimit = ipLimit;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getDeleteUserName() {
        return deleteUserName;
    }

    public void setDeleteUserName(String deleteUserName) {
        this.deleteUserName = deleteUserName;
    }

    public String getFlowKey() {
        return flowKey;
    }

    public void setFlowKey(String flowKey) {
        this.flowKey = flowKey;
    }

    public String getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(String maintainer) {
        this.maintainer = maintainer;
    }

    public String getMaintainerPhone() {
        return maintainerPhone;
    }

    public void setMaintainerPhone(String maintainerPhone) {
        this.maintainerPhone = maintainerPhone;
    }

    public Date getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(Date publishedTime) {
        this.publishedTime = publishedTime;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Integer getAbleManuscript() {
        return ableManuscript;
    }

    public void setAbleManuscript(Integer ableManuscript) {
        this.ableManuscript = ableManuscript;
    }
}
