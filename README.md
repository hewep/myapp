#myapp 开发文档
---------------------------
###一. 代码规范
  ***前台 js 代码***  
  1.  media-pagination 分页的使用

   *  使用中的注意事项: 
>    var option = {page : result.datas, url:"topic/list",params:params};
     目前只能用 result.datas, 对应的 java代码 result.setData("datas", list); 

   * 关于分页参数: 
> 已通过 指令处理, 不需要手动传参;
    int pageNumber = this.getParaToInt(0,1)  可获取当前页码
    pageSize 目前未处理, 默认后端设置

***后台 java 代码***  
  1.  BaseController -- 存放所有controller公用的 方法, 如: 获取当前登录用户, 参数的获取 (只需要通过 this 调用即可)

###二. 代码测试
  所有的测试工作在 test 包中完成
