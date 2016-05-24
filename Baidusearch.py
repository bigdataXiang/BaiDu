# -*- coding:utf-8 -*-
import requests
from bs4 import BeautifulSoup
import json


def getpage(url,filename):
    res=requests.get(url)
    soup=BeautifulSoup(res.text,from_encoding="gb18030")
    results=soup.find_all('div',class_="c-container")
    for result in results:

    	title=result.h3.a.get_text()
        #print title.encode（'utf-8'） 
    	href=result.h3.a.get('href')
        time="recent "
        try:
            time=result.find('span',class_="newTimeFactor_before_abs").get_text()    	
        except AttributeError:
            print "no time info"
        item=dict({"Title":title.encode('utf-8'),"href":href,"time":time.encode('utf-8')})
        with open("".join(filename.split())+'.json', 'a') as outfile:
	        json.dump(item, outfile,ensure_ascii=False)
        with open("".join(filename.split())+'.json', 'a') as outfile:
            outfile.write(",\n")

if __name__ == '__main__':
    base_url="http://www.baidu.com/s?wd="
    keywords=[u"生态文明建设 项目 工程",u"生态文明建设 成果"]
	#[u"生态文明体制改革领导机构",u"生态文明建设 方案",u"生态文明建设 领导小组"]
    for kw in keywords:
        url_kw=base_url+kw
        for i in range(1000): #amount of pages you want to parse
            url=url_kw+"&pn="+str(i*10)
            getpage(url,kw)#kw as filename to store results
