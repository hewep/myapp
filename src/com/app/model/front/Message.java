package com.app.model.front;

import com.app.model.BaseModel;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
/**
 * msg_type  (1:动态, 2:留言, 3:系统留言)
 * @author Administrator
 *
 */
public class Message extends BaseModel<Message>{
	
	private static final long serialVersionUID = 7925448656579052520L;
	public static final Message dao = new Message();
	
	public Page<Message> paginateByUserId(int pageNumber, int pageSize,Record params){
		String userId = params.get("user_id", "0");
		String type = params.get("type", "1");
		return Message.dao.paginate(pageNumber, pageSize, 
									"select a.*, b.user_name as sender_name, c.user_name as receiver_name,"+
									"b.pic_url as sender_pic_url, c.pic_url as receiver_pic_url ", 
									"from message a "+
									"left outer join user b on a.sender_id = b.id "+
								    "left outer join user c on a.receiver_id = c.id "+
								    "where (sender_id=? or receiver_id = ?) and msg_type = ? "+
								    "order by a.id desc", 
									new Object[]{userId, userId, type});
	}
	
}
