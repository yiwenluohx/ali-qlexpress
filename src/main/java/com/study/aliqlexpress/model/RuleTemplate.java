package com.study.aliqlexpress.model;

/**
 * 规则模板
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/8/17 下午6:00
 * @menu 规则模板
 */
public class RuleTemplate {

    /**
     * 模板code
     */
    private String templateCode;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 规则表达式
     */
    private String express;

    /**
     * 状态
     */
    private Integer status;

    public RuleTemplate(String templateCode, String templateName, String express, Integer status) {
        this.templateCode = templateCode;
        this.templateName = templateName;
        this.express = express;
        this.status = status;
    }

    /**
     * Gets the value of templateCode.
     *
     * @return the value of templateCode
     */
    public String getTemplateCode() {
        return templateCode;
    }

    /**
     * Sets the templateCode. *
     * <p>You can use getTemplateCode() to get the value of templateCode</p>
     * * @param templateCode templateCode
     */
    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    /**
     * Gets the value of status.
     *
     * @return the value of status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets the status. *
     * <p>You can use getStatus() to get the value of status</p>
     * * @param status status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Gets the value of express.
     *
     * @return the value of express
     */
    public String getExpress() {
        return express;
    }

    /**
     * Sets the express. *
     * <p>You can use getExpress() to get the value of express</p>
     * * @param express express
     */
    public void setExpress(String express) {
        this.express = express;
    }

    /**
     * Gets the value of templateName.
     *
     * @return the value of templateName
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * Sets the templateName. *
     * <p>You can use getTemplateName() to get the value of templateName</p>
     * * @param templateName templateName
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
