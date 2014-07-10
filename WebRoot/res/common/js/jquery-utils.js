$(function(){

	$.extend(Date.prototype, {
		format : function(fmt)  {
			fmt = fmt? fmt:'yyyy-MM-dd';
			var o = {   
			    "M+" : this.getMonth()+1,                 //月份    
			    "d+" : this.getDate(),                    //日    
			    "h+" : this.getHours(),                   //小时    
			    "m+" : this.getMinutes(),                 //分    
			    "s+" : this.getSeconds(),                 //秒    
			    "q+" : Math.floor((this.getMonth()+3)/3), //季度    
			    "S"  : this.getMilliseconds()             //毫秒    
			 };   
			 if(/(y+)/.test(fmt))  
			   fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
			   
			 for(var k in o){
			   if(new RegExp("("+ k +")").test(fmt))   
			 	 fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
			 }   
			 return fmt;
		}
	});
	
	$.extend({
		getDiffDate : function(time){
			var times = "";
			var oldDate = new Date(time);
			var currDate = new Date();
			
			var oldTime = oldDate.getTime();
			var currTime = currDate.getTime();
			var diff = (currTime - oldTime)/1000;
			if(diff < 60){
				times = $.round(diff,0)+"秒前";
			}else if((diff /= 60) < 60){
				times = $.round(diff,0) + "分钟前";
			}else if((diff /= 60) < 24){
				times = $.round(diff,0) + "小时前";
			}else if((diff /= 24) < 30){
				if(diff == 1){
					times = "昨天("+oldDate.format("hh:mm")+")";
				}else{
					times = $.round(diff,0) + "天前";
				}
			}else{
				var years = currDate.getFullYear() - oldDate.getFullYear();
				var months = currDate.getMonth() - oldDate.getMonth();
				if( years <= 0){
					times = $.round(months) + "个月前"; 
				}else{
					times = $.round(years) + "年前";
				};
			};
			return times;
		},
		round : function(number,dv){
			var t=1; 
			for(;dv>0;t*=10,dv--); 
			for(;dv<0;t/=10,dv++); 
			return Math.round(number*t)/t;	
		},
		toInt : function(text,def){
			return isNaN(parseInt(text))? (def || 0) : parseInt(text);
		},
		toFloat : function(text,def){
			return isNaN(parseFloat(text))? (def || 0.0) : parseFloat(text);
		},
		handlePage :function(option){
			option = $.extend({page:{}, url:'', params:{}}, option);
			option.page.items = [];
			if(option.page.totalPage){
				for (var i = 0; i < option.page.totalPage; i++) {
					option.page.items.push(i+1);
				}
			}
			option.page.url = option.url;
			option.page.params = option.params;
			return option.page;
		}
	});
});