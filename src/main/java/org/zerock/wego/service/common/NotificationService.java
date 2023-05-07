package org.zerock.wego.service.common;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.zerock.wego.domain.common.CommentDTO;
import org.zerock.wego.domain.common.NotificationDTO;
import org.zerock.wego.domain.common.NotificationVO;
import org.zerock.wego.exception.NotFoundPageException;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.NotificationMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service("notificationService")
public class NotificationService {	// POJO
	
	private final NotificationMapper notificationMapper;
	
		// 유저코드 알림목록 조회
		public List<NotificationVO> getAllByUserId(Integer userId) throws ServiceException {
			log.debug("getAllByUserId({}) invoked.", userId);
			try {
		        Objects.requireNonNull(this.notificationMapper);
		        return this.notificationMapper.selectAllByUserId(userId); // mapper 변수명 변경 예정 
			
			} catch(Exception e) {
				throw new ServiceException(e);
			} // try-catch
		} //getAllByUserId 

		// 알림코드 알림조회
		public NotificationVO getByAlarmId(Integer alarmId) throws ServiceException{
			log.debug("getAlarmId({}) invoked.", alarmId);
			try {
				NotificationVO alarm = this.notificationMapper.selectByAlarmId(alarmId);
				
				return alarm;
			}catch (Exception e) {
				throw new ServiceException(e);
			}// try-catch
		} //getAlarmId
		
		public void registerBadgeNotification(Integer badgeId, Integer userId) {

		    log.debug("registerBadgeNotification({},{})", badgeId,userId);
		    try {
			    this.notificationMapper.insertBadgeByBadgeIdAndUserId(badgeId, userId);

			}catch (Exception e) {
				throw new ServiceException(e);
			}// try-catch
		}
		// 댓글과 멘션 작성시 알림 
		public void registerCommentNotification(CommentDTO comment) {
			log.debug("registerCommentNotification({}) invoked.", comment);

			try {
					if (comment.getMentionId() != null) {
						this.notificationMapper.insertMentionByCommentIdAndUserId(comment.getCommentId(), comment.getMentionId(),comment.getUserId());
						this.notificationMapper.insertCommentByCommentIdAndUserId(comment.getCommentId(), comment.getUserId());
				    	 log.info("/n/n댓글과 맨션 알림들어감/n");

					} else {
						this.notificationMapper.insertCommentByCommentIdAndUserId(comment.getCommentId(), comment.getUserId());
				    	 log.info("/n/n댓글 알림만 들어감/n");
					} //if-else
			
			} catch (RuntimeException e) {
			    throw new ServiceException(e);
			}// try-catch
	    }//registerCommentNotification
		
		// 알림 읽음
		public void modifyStatusByAlarmId(Integer alarmId,Integer userId) throws ServiceException {
		    try {
		    	Objects.requireNonNull(this.notificationMapper);
		        notificationMapper.updateStatusByAlarmIdAndUserId(alarmId, userId);
		      } catch (Exception e) {
		    	 log.info("알림 업데이트에서 에러가 났습니다.");
		        throw new ServiceException(e);
		      }
		} //updateAlarm
		
		// 알림 삭제 
		public void removeAlarm(Integer alarmId) throws ServiceException {
			log.trace("removeAlarm({}) invoked.", alarmId);
			try {
				
				boolean isExist = this.notificationMapper.isExistByUserId(alarmId);	
				
				if(!isExist) {
					throw new NotFoundPageException();
				}// if
				
				this.notificationMapper.deleteByAlarmId(alarmId);
				
				isExist = this.notificationMapper.isExistByUserId(alarmId);	
				if (isExist) {
					log.info("알림이 삭제되지않았습니다.");
				}				
				log.info("알림이 삭제되었습니다.");

			}  catch (NotFoundPageException e) {
				throw e;
				
			} catch (Exception e) {
				throw new ServiceException(e);
			}// try-catch
		} // removeAlarm
		
		
	// 웹소캣을 위한 로직입니다.
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    
    public void addSession(WebSocketSession session) {
    	log.info("/n >>>>>>>>>addSession 성공.");
        sessions.add(session);
    }

    public void removeSession(WebSocketSession session) {
    	log.info("/n >>>>>>>>>removeSession 성공.");
        sessions.remove(session);
    }

    public void notifyAllSessions(NotificationVO notification) {
        sessions.forEach(session -> {
            try {
                session.sendMessage(new TextMessage(notification.toString()));
            } catch (IOException e) {
//                log.error("{} 에게 메시지를 보내는 동안 오류가 발생 : {}", userId, e.getMessage());
            }
        });
    }
} // end class
