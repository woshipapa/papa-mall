package com.papa.mbg;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;
import org.springframework.util.StringUtils;

import java.util.Properties;

public class CommentGenerator extends DefaultCommentGenerator {
    public boolean addRemarks=false;
    private static final String EXAMPLE_SUFFIX="Example";
    private static final String API_MODEL_PROPERTY_FULL_CLASS_NAME="io.swagger.annotations.ApiModelProperty";

    @Override
    public void addConfigurationProperties(Properties props) {
        super.addConfigurationProperties(props);
        addRemarks= StringUtility.isTrue(props.getProperty("addRemarkComments"));
    }

    //给生成的model类字段添加注释(来自数据库中字段的注释)
//    @Override
//    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
//        String remark=introspectedColumn.getRemarks();
//        if(addRemarks&& StringUtils.hasText(remark)){
//            if(remark.contains("\"")){
//                remark.replace("\"","'");
//            }
//            field.addJavaDocLine("@ApiModelProperty(value = \""+remark+"\")");
//        }
//    }

    //添加在生成的model文件头，除了接口和example类
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        super.addJavaFileComment(compilationUnit);
        if(!compilationUnit.isJavaInterface()&&!compilationUnit.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)){
            compilationUnit.addImportedType(new FullyQualifiedJavaType(API_MODEL_PROPERTY_FULL_CLASS_NAME));
        }

    }
    public void addFieldJavadoc(String remarks,Field field){
        field.addJavaDocLine("/**");
        String []remark=remarks.split(System.getProperty("line.separator"));
        for(String s:remark){
            field.addJavaDocLine(" * "+s);
        }
        addJavadocTag(field,false);

        field.addJavaDocLine("*/");
    }
}
