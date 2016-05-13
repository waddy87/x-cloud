$.ajaxSetup({  
    contentType:"application/x-www-form-urlencoded;charset=utf-8", 
    complete:function(XMLHttpRequest,textStatus){  
        //通过XMLHttpRequest取得响应头，sessionstatus， 
        var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus");
        if(sessionstatus=="timeoutajax"){ 
        //如果超时就处理 ，指定要跳转的页面  
	        /*sg.layer({
	            type : 2,
	            title : '登录',
	            iframe : {src : 'http://www.baidu.com'},
	            area : ['400px' , '300px'],
	            offset : ['100px','']
	        });*/
        	window.location = sugon.rootURL+"/userlogin";  
        }
    }  
});