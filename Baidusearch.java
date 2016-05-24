package com.svail.test;

import java.net.*;
import java.util.Vector;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.svail.util.FileTool;
import com.svail.util.HTMLTool;
import com.svail.util.Tool;

import java.io.*;
 
public class BaiDu {
    public BaiDu() {
    }
 
    public static void main(String[] args) {
    	Vector<String> pois=FileTool.Load("D:/百度_代码+试运行结果/全国区县.txt", "utf-8");
    	for(int i=0;i<pois.size();i++){
    		String poi=pois.elementAt(i).replace(", ", "");
    		String str="u\""+poi+"\""+",";
    		System.out.println(str);
    		FileTool.Dump(str, "D:/百度_代码+试运行结果/区县result.txt", "utf-8");
    	}
    	
    }
    public static void baiDuSearch() throws FailingHttpStatusCodeException, MalformedURLException, IOException{


        //创建webclient
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //htmlunit 对css和javascript的支持不好，所以请关闭之
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        HtmlPage page = (HtmlPage)webClient.getPage("http://www.baidu.com/");
        //获取搜索输入框并提交搜索内容
        HtmlInput input = (HtmlInput)page.getHtmlElementById("kw");
        System.out.println(input.toString());
        input.setValueAttribute("生态文明建设 方案");
        System.out.println(input.toString());
        //获取搜索按钮并点击
        HtmlInput btn = (HtmlInput)page.getHtmlElementById("su");
        HtmlPage page2 = btn.click();
        //输出新页面的文本
        System.out.println(page2.asXml());
        String str=page2.asXml();
        FileTool.Dump(str, "D:/生态文明建设/result.txt", "utf-8");
        
        
        
        String tur="";
    	try {
    		Parser parser = new Parser();
    		parser.setInputHTML(str);
			parser.setEncoding("utf-8");
		    HasParentFilter parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("id", "page")));
			NodeFilter filter = new AndFilter(new TagNameFilter("a"),new AndFilter(parentFilter1,new HasAttributeFilter("class", "n")));
		    NodeList nodes = parser.extractAllNodesThatMatch(filter);
			if (nodes.size() != 0) {
				TagNode no = (TagNode) nodes.elementAt(0);
				tur ="https://www.baidu.com"+ no.getAttribute("href").replace("&amp;", "");
				System.out.println(tur);
			}
    		
    	}catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
    	
    	
//https://www.baidu.com/s?wd=%E7%94%9F%E6%80%81%E6%96%87%E6%98%8E%E5%BB%BA%E8%AE%BE%20%E6%96%B9%E6%A1%88&pn=10&oq=%E7%94%9F%E6%80%81%E6%96%87%E6%98%8E%E5%BB%BA%E8%AE%BE%20%E6%96%B9%E6%A1%88&tn=baiduhome_pg&ie=utf-8&usm=2&rsv_idx=2&rsv_pq=ea0ce54d00bdd592&rsv_t=ed0bz4xV5VmbU4KXUWFBSdnJ3gzN9Uq4if7RZ61niUHa5SSbk3t6yPnrTbuJR23HYJRC&rsv_page=1
//https://www.baidu.com/s?wd=%E7%94%9F%E6%80%81%E6%96%87%E6%98%8E%E5%BB%BA%E8%AE%BE%20%E6%96%B9%E6%A1%88&amp;pn=10&amp;oq=%E7%94%9F%E6%80%81%E6%96%87%E6%98%8E%E5%BB%BA%E8%AE%BE%20%E6%96%B9%E6%A1%88&amp;ie=utf-8&amp;usm=2&amp;rsv_idx=1&amp;rsv_pq=9e3061b000a600d2&amp;rsv_t=491bAUfhFVGNCeeI%2F5l1K8oGxBmR9kDuHcWuJXYDubD8MiTmPutXbGijqaE&amp;rsv_page=1
    	try{
    	//String content = Tool.fetchURL(tur);
    	String content = HTMLTool.fetchURL("http://www.baidu.com/s?wd=%E7%94%9F%E6%80%81%E6%96%87%E6%98%8E%E5%BB%BA%E8%AE%BE%20%E6%96%B9%E6%A1%88&pn=10&qq-pf-to=pcqq.c2c", "utf-8", "get");
    	Parser parser = new Parser();
    	if (content == null) {
			FileTool.Dump(tur, "-Null.txt", "utf-8");
		}else{
			parser.setInputHTML(content);
			parser.setEncoding("utf-8");
			HasParentFilter parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "result c-container")));
			NodeFilter filter = new AndFilter(new TagNameFilter("div"),new AndFilter(parentFilter1,new HasAttributeFilter("class", "c-abstract")));
		    NodeList nodes = parser.extractAllNodesThatMatch(filter);
		    if (nodes.size() != 0) {
		    	TagNode no = (TagNode) nodes.elementAt(0);
		    	String str1 = no.toPlainTextString().replace("  ", "").replace("&gt;", "")
						.replace("&ensp;", "").replace(" ", "").replace("\r\n", "").replace("\t", "")
						.replace("\n", "").replace("&#8211;", "-").replace("&nbsp;", "")
						.replace("&ldquo", "").replace("&#160;", "").replace("", "").trim();
		    	System.out.println(str1);
		    }
		    parser.reset();
		    parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "result c-container")));
		    HasParentFilter parentFilter2 = new HasParentFilter(new AndFilter(new TagNameFilter("div"),new AndFilter(parentFilter1,new HasAttributeFilter("class", "f13"))));
		    filter = new AndFilter(new TagNameFilter("a"),parentFilter2);
		    nodes = parser.extractAllNodesThatMatch(filter); 
		    if (nodes.size() != 0) {
		    	TagNode no = (TagNode) nodes.elementAt(0);
				tur = no.getAttribute("href");
				System.out.println(tur);
		    }
			
		}
        }catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
    	
    	

    }

 
 
}
