/**JQuery emotion **/
(function($){
	
	$.fn.extend({
		emotion: function(options){
			if(typeof options == 'object'){
				options = $.extend({},$.fn.emotion.defaults, options);
				var box = new Emotion(this, options);
				return box;
			}
		}
		
	});
	$.fn.emotion.defaults = {
		path:"",
		allowPreview:true,
		destId:"",
		param:{}
	}
	var Emotion = function(self, options){		// Emotion 的构造函数
		
		var offset = self.offset();				//当前对象的位移
		var destObj = $("#"+options.destId);  	//插入的目标对象; 
		
		var path = options.path;
		var rows = 5, cols = 9, total = 135, startNum = 0,
			cells = rows * cols, pages = Math.ceil(total / cells),
			colsHalf = Math.floor(cols / 2),
			currentPageNum = 1,
			menuDiv = $('<div class="ke-menu-default ke-shadow ke-menu"></div>'),
			wrapperDiv = $('<div class="ke-plugin-emoticons"></div>'),
			elements = [];
		
		menuDiv.css('position','absolute');
	    menuDiv.append(wrapperDiv);
	    
	    var previewDiv, previewImg;
		if (options.allowPreview) {		// 是否允许预览
			previewDiv = $('<div class="ke-preview"></div>').css('right', 0);
			previewImg = $('<img class="ke-preview-img" src="' + path + startNum + '.gif" />');
			wrapperDiv.append(previewDiv);
			previewDiv.append(previewImg);
		}
		//输入框绑定
		self.bind('click',function(event){
			var menu = self.data("menu");
			if(!menu){
				createPageTable(currentPageNum);
				self.after(menuDiv);
				menu = menuDiv;
				self.data("menu",menuDiv);
			}
			menu.css({left:(offset.left)+"px", top:(offset.top + self.outerHeight())+ "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDown);
		});
		
		function bindCellEvent(cell, j, num) {
			if (previewDiv) {
				cell.mouseover(function() {
					if (j > colsHalf) {
						previewDiv.css('left', 0);
						previewDiv.css('right', '');
					} else {
						previewDiv.css('left', '');
						previewDiv.css('right', 0);
					}
					previewImg.attr('src', path + num + '.gif');
					$(this).addClass('ke-on');
				});
			} else {
				cell.mouseover(function() {
					$(this).addClass('ke-on');
				});
			}
			cell.mouseout(function() {
				$(this).removeClass('ke-on');
			});
			cell.click(function(e) {
				var oldHtml = destObj.html();
				destObj.html(oldHtml+'<img src="' + path + num + '.gif" border="0" alt="" />');
				hideMenu();		// hide menu
			});
		}
		function createEmoticonsTable(pageNum, parentDiv) {
			var table = document.createElement('table');
			parentDiv.append(table);
			if (previewDiv) {
				$(table).mouseover(function() {
					previewDiv.show('block');
				});
				$(table).mouseout(function() {
					previewDiv.hide();
				});
				elements.push($(table));
			}
			table.className = 'ke-table';
			table.cellPadding = 0;
			table.cellSpacing = 0;
			table.border = 0;
			var num = (pageNum - 1) * cells + startNum;
			for (var i = 0; i < rows; i++) {
				var row = table.insertRow(i);
				for (var j = 0; j < cols; j++) {
					var cell = $(row.insertCell(j));
					cell.addClass('ke-cell');
					bindCellEvent(cell, j, num);
					var span = $('<span class="ke-img"></span>')
						.css('background-position', '-' + (24 * num) + 'px 0px')
						.css('background-image', 'url(' + path + 'static.gif)');
					cell.append(span);
					elements.push(cell);
					num++;
				}
			}
			return table;
		}
		var table = createEmoticonsTable(currentPageNum, wrapperDiv);
		function removeEvent() {
			$.each(elements, function() {
				this.unbind();
			});
		}
		var pageDiv;
		function bindPageEvent(el, pageNum) {
			el.click(function(e) {
				removeEvent();
				table.parentNode.removeChild(table);
				pageDiv.remove();
				table = createEmoticonsTable(pageNum, wrapperDiv);
				createPageTable(pageNum);
				currentPageNum = pageNum;
				e.stop();
			});
		}
		function createPageTable(currentPageNum) {
			pageDiv = $('<div class="ke-page"></div>');
			wrapperDiv.append(pageDiv);
			for (var pageNum = 1; pageNum <= pages; pageNum++) {
				if (currentPageNum !== pageNum) {
					var a = $('<a href="javascript:;">[' + pageNum + ']</a>');
					bindPageEvent(a, pageNum);
					pageDiv.append(a);
					elements.push(a);
				} else {
					pageDiv.append($('<span>[' + pageNum + ']</span>'));
				}
				pageDiv.append($('<span>&nbsp;</span>'));
			}
		}
		
		/**鼠标事件触发**/
		function onBodyDown(event) {
			var target = event.srcElement || event.target;
			if (!(target.id == self.attr("id") || target == menuDiv || $(target).parents(".ke-menu-default").length>0)) {
				hideMenu();
			}
		}
		function hideMenu() {
			menuDiv.slideUp("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
	};
})(jQuery);
