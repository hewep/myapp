/**
 *  自定义可拖拽插件
 *  $(obj).dragDrop({container:id});
 * @param $
 * @author hewep
 */
(function($) {
	$.extend({
		// 获取鼠标当前坐标
		mouseCoords : function(ev) {
			if (ev.pageX || ev.pageY) {
				return {
					x : ev.pageX,
					y : ev.pageY
				};
			}
			return {
				x : ev.clientX + document.body.scrollLeft
						- document.body.clientLeft,
				y : ev.clientY + document.body.scrollTop
						- document.body.clientTop
			};
		},
		// 获取样式值
		getStyle : function(obj, styleName) {
			return obj.currentStyle ? obj.currentStyle[styleName]
					: document.defaultView.getComputedStyle(obj, null)[styleName];
		}
	});

	// 元素拖拽插件
	$.fn.dragDrop = function(options) {
		var opts = $.extend({}, $.fn.dragDrop.defaults, options);
		
		var container = $("#"+opts.container);  // 指定区域容器
		return this.each(function() {

			// 是否正在拖动
			var bDraging = false;
			// 移动的元素
			var moveEle = $(this);
			// 限制区域
			opts.fixarea = [0,container.width()-moveEle.width(),0,container.height()-moveEle.height()];
			
			// 点击哪个元素，以触发移动。
			// 该元素需要是被移动元素的子元素（比如标题等）
			var focuEle = opts.focuEle ? $(opts.focuEle, moveEle) : moveEle;
			if (!focuEle || focuEle.length <= 0) {
				alert('focuEle is not found! the element must be a child of '
						+ this.id);
				return false;
			}
			// initDiffX|Y : 初始时，鼠标与被移动元素原点的距离
			// moveX|Y : 移动时，被移动元素定位位置 (新鼠标位置与initDiffX|Y的差值)
			// 如果定义了移动中的回调函数，该对象将以参数传入回调函数。
			var dragParams = {
				initDiffX : '',
				initDiffY : '',
				moveX : '',
				moveY : ''
			};

			// 点击时，记录鼠标位置
			// DOM写法： getElementById('***').onmousedown= function(event);
			focuEle.bind('mousedown', function(e) {
				// 标记开始移动
				bDraging = true;
				// 改变鼠标形状
				moveEle.css({
					'cursor' : 'move'
				});
				// 捕获事件。（该用法，还有个好处，就是防止移动太快导致鼠标跑出被移动元素之外）
				if (moveEle.get(0).setCapture) {
					moveEle.get(0).setCapture();
				}
				// （实际上是鼠标当前位置相对于被移动元素原点的距离）
				// DOM写法：(ev.clientX + document.body.scrollLeft -
				// document.body.clientLeft) -
				// document.getElementById('***').style.left;
				dragParams.initDiffX = $.mouseCoords(e).x
						- moveEle.position().left;
				dragParams.initDiffY = $.mouseCoords(e).y
						- moveEle.position().top;
			});

			// 移动过程
			focuEle.bind('mousemove', function(e) {
				if (bDraging) {
					// 被移动元素的新位置，实际上鼠标当前位置与原位置之差
					// 实际上，被移动元素的新位置，也可以直接是鼠标位置，这也能体现拖拽，但是元素的位置就不会精确。
					dragParams.moveX = $.mouseCoords(e).x
							- dragParams.initDiffX;
					dragParams.moveY = $.mouseCoords(e).y
							- dragParams.initDiffY;

					// 是否限定在某个区域中移动.
					// fixarea格式: [x轴最小值,x轴最大值,y轴最小值,y轴最大值]
					if (opts.fixarea) {
						if (dragParams.moveX < opts.fixarea[0]) {
							dragParams.moveX = opts.fixarea[0]
						}
						if (dragParams.moveX > opts.fixarea[1]) {
							dragParams.moveX = opts.fixarea[1]
						}

						if (dragParams.moveY < opts.fixarea[2]) {
							dragParams.moveY = opts.fixarea[2]
						}
						if (dragParams.moveY > opts.fixarea[3]) {
							dragParams.moveY = opts.fixarea[3]
						}
					}

					// 移动方向：可以是不限定、垂直、水平。
					if (opts.dragDirection == 'all') {
						// DOM写法： document.getElementById('***').style.left =
						// '***px';
						moveEle.css({
							'left' : dragParams.moveX,
							'top' : dragParams.moveY
						});
					} else if (opts.dragDirection == 'vertical') {
						moveEle.css({
							'top' : dragParams.moveY
						});
					} else if (opts.dragDirection == 'horizontal') {
						moveEle.css({
							'left' : dragParams.moveX
						});
					}

					// 如果有回调
					if (opts.callback) {
						// 将dragParams作为参数传递
						opts.callback.call(opts.callback, dragParams);
					}
				}
			});

			// 鼠标弹起时，标记为取消移动
			focuEle.bind('mouseup', function(e) {
				bDraging = false;
				moveEle.css({
					'cursor' : 'default'
				});
				if (moveEle.get(0).releaseCapture) {
					moveEle.get(0).releaseCapture();
				}
			});
		});
	};

	// 默认配置
	$.fn.dragDrop.defaults = {
		focuEle : null, // 点击哪个元素开始拖动,可为空。不为空时，需要为被拖动元素的子元素。
		callback : null, // 拖动时触发的回调。
		dragDirection : 'all', // 拖动方向：['all','vertical','horizontal']
		container : null
	};

})(jQuery);