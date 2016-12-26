package com.jspxcms.core.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jspxcms.common.orm.JpqlBuilder;
import com.jspxcms.common.orm.QuerydslUtils;
import com.jspxcms.core.domain.Message;
import com.jspxcms.core.domain.QMessage;
import com.jspxcms.core.repository.plus.MessageDaoPlus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class MessageDaoImpl implements MessageDaoPlus {

	@SuppressWarnings("unchecked")
	public Page<Object[]> groupByUserId(Integer userId, boolean unread, Pageable pageable) {
		JpqlBuilder jb = new JpqlBuilder();
		jb.append("select max(t.message_id_) as message_id_,                                                          "
				+ "       t.contact_id_ as contact_id_,                                                               "
				+ "       u.f_username as contact_username_,                                                          "
				+ "       max(t.send_time_) as send_time_,                                                            "
				+ "       count(t.contact_id_) as number_of_messages_,                                                "
				+ "       sum(t.unread_) as number_of_unread_                                                         "
				+ "from (                                                                                             "
				+ "  select m.message_id_,m.send_time_,m.receiver_id_ as contact_id_,0 as unread_                     "
				+ "  from cms_message m                                                                               "
				+ "  where m.sender_id_=:userId and m.deletion_flag_<>:deletionSend                                   "
				+ "  UNION                                                                                            "
				+ "  select m.message_id_,m.send_time_,m.sender_id_ as contact_id_,m.is_unread_ as unread_            "
				+ "  from cms_message m                                                                               "
				+ "  where m.receiver_id_=:userId and m.deletion_flag_<>:deletionReceive                              "
				+ ") t join cms_user u on t.contact_id_=u.f_user_id                                                   "
				+ "group by t.contact_id_                                                                             ");
		if (unread) {
			jb.append("having number_of_unread_>0");
			jb.setCountQueryString("select count(*) from (" + jb.getQueryString() + ")");
		} else {
			jb.setCountProjection("distinct contact_id_");
		}
		jb.setParameter("userId", userId);
		jb.setParameter("deletionSend", Message.DELETION_SEND);
		jb.setParameter("deletionReceive", Message.DELETION_RECEIVE);
		return jb.nativePage(em, pageable);
	}

	public Page<Message> findByContactId(Integer userId, Integer contactId, Pageable pageable) {
		QMessage m = QMessage.message;
		JPAQuery<Message> query = queryFactory.selectFrom(m);
		BooleanBuilder senderExp = new BooleanBuilder(m.sender.id.eq(userId)).and(m.deletionFlag
				.ne(Message.DELETION_SEND));
		BooleanBuilder receiverExp = new BooleanBuilder(m.receiver.id.eq(userId)).and(m.deletionFlag
				.ne(Message.DELETION_RECEIVE));
		senderExp.and(m.receiver.id.eq(contactId));
		receiverExp.and(m.sender.id.eq(contactId));
		query.where(senderExp.or(receiverExp));
		return QuerydslUtils.page(query, m, pageable);
	}

	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	private JPAQueryFactory queryFactory;

	@Autowired
	public void setQueryFactory(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

}
