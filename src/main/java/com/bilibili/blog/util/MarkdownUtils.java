package com.bilibili.blog.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

public class MarkdownUtils {
/**
 * markdown格式转换成HTML格式
 * @param markdown
 * @return string
 */
    public static String markdownToHtml(String markdown){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
       return renderer.render(document);
    }
    /**
     * 增强扩展(表格生成，标题描点)
     * markdown格式转换成HTML格式
     * @param markdown
     * @return string
     */
    public static String markdownToHtmlExtensions(String markdown){
//       标题自动生成id
        Set<Extension> headingAnchorExtension = Collections.singleton(HeadingAnchorExtension.create());
//        table转换成HTML
        List<Extension> tableExtensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(tableExtensions).build();
        Node document = parser.parse(markdown);

        HtmlRenderer renderer = HtmlRenderer.builder().extensions(headingAnchorExtension).extensions(tableExtensions)
                .attributeProviderFactory(new AttributeProviderFactory() {
            public AttributeProvider create(AttributeProviderContext context) {
                return new CustomAttributeProvider();
            }
        }).build();
        String content = renderer.render(document);
        return content;
    }

    /**
     * 处理标签属性
     */
    static class CustomAttributeProvider implements AttributeProvider{
        @Override
        public void setAttributes(Node node, String s, Map<String, String> map) {
            if (node instanceof Link){
                map.put("target","_blank");
            }
            if (node instanceof TableBlock){
                map.put("class","ui celled table");
            }
        }
    }
}

