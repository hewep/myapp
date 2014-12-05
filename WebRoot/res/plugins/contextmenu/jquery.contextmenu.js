!function($) {
	
	var ContextMenu = function(element, options){
		var self = this;
		this.$element = $(element);	// jquery 对象
		this.element = this.$element.get(0); // dom 对象
		this.$menu = $("<div class='context-menu' menu='"+this.$element.attr("id")+"'><ul></ul></div>");
		
		this._options = this._process_options(options);   //处理参数 
		this._applyEvents();  // 绑定事件
		//元素右键菜单
		this.element.oncontextmenu = function(event){
			
			var event = event || window.event;
			self.$menu.css({display:"block", 
							top: event.clientY + "px", 
							left:event.clientX + "px"});
			
			$("body").bind("click", hideMenu);
			return false;
		};
		
		//菜单添加到当前元素下
		$("body").append(self.$menu);
		
		//隐藏菜单
		function hideMenu() {
			self.$menu.hide();
			$("body").unbind("click", hideMenu);
		}
	};
	ContextMenu.prototype = {
		constructor : ContextMenu,
		addItem : function(name, value){
			var li = $("<li cmd='"+value+"'>"+name+"</li>");
			this.$menu.find("ul").append(li);
			return li;
		},
		_process_options : function(options){
			return $.extend({},$.fn.contextMenu.defaults, options);
		},
		_applyEvents: function(){
			// click 与 mousedown 不能同时起作用
			var self = this;
			this.$menu.delegate("li", "click", function(e){
				//e.stopPropagation();  //阻止事件冒泡
				var li = $(this);
				var cmd = li.attr("cmd");
				if(cmd == "clear"){
					self.$element.empty();
				}
				//self._options.onClick(cmd);
			});
		},
		_unapplyEvents: function(){
			this.$element.undelegate();
		},
		_events : []
	};
	$.fn.contextMenu = function(options){
		var menu = new ContextMenu(this, options);
		this.data("contextmenu", menu);
		return menu;
	};
	
	$.fn.contextMenu.defaults = {
		onClick : $.noop
	};
	
}(window.jQuery);