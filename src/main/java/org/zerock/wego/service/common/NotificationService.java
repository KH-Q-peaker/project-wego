package org.zerock.wego.service.common;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.common.CommentDTO;
import org.zerock.wego.domain.common.FavoriteDTO;
import org.zerock.wego.domain.common.NotificationDTO;
import org.zerock.wego.domain.common.NotificationVO;
import org.zerock.wego.exception.NotFoundPageException;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.JoinMapper;
import org.zerock.wego.mapper.NotificationMapper;
import org.zerock.wego.socket.NotificationWebSocketHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service("notificationService")
public class NotificationService {	// POJO
	
	private final NotificationMapper notificationMapper;
	private final JoinMapper joinMapper;
	@Autowired
    private NotificationWebSocketHandler notificationWebSocketHandler;

	// 유저코드 알림목록 조회
	public List<NotificationVO> getAllByUserId(Integer userId) throws ServiceException {
		log.debug("getAllByUserId({}) invoked.", userId);
		try {
	        Objects.requireNonNull(this.notificationMapper);
	        return this.notificationMapper.selectAllByUserId(userId);
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

    // 알림 메시지 전송
    public void sendNotification(Integer userId, String message) {
        notificationWebSocketHandler.sendMessageToUser(userId, message);
    } // sendNotification
	
	// 좋아요과 함께 알림 추가 
	public void registerFavoriteByTargetCdAndUserId(FavoriteDTO favorite) {
		log.debug("registerFavoriteByTargetCdAndUserId({})",favorite);
	    try {
		    this.notificationMapper.insertFavoriteByTargetCdAndUserId(favorite.getTargetCd(), favorite.getUserId());
		}catch (Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}//registerFavoriteByTargetCdAndUserId
	
	// 댓글과 멘션 작성시 알림 
	public void registerCommentNotification(CommentDTO comment) {
		log.debug("registerCommentNotification({}) invoked.", comment);

		try {
				if (comment.getMentionId() != null) {
					this.notificationMapper.insertMentionByCommentIdAndUserId(comment.getCommentId(), comment.getMentionId(),comment.getUserId());
					this.notificationMapper.insertCommentByCommentIdAndUserId(comment.getCommentId(), comment.getUserId());
				} else {
					this.notificationMapper.insertCommentByCommentIdAndUserId(comment.getCommentId(), comment.getUserId());
				} //if-else
				
		} catch (RuntimeException e) {
		    throw new ServiceException(e);
		}// try-catch
    }//registerCommentNotification

	// 뱃지 획득(후기게시글작성)과 함께 알림 추가 
	public void registerBadgeNotification(Integer badgeId, Integer userId) {
	    log.debug("registerBadgeNotification({},{})", badgeId,userId);
	    try {
		    this.notificationMapper.insertBadgeByBadgeIdAndUserId(badgeId, userId);
		}catch (Exception e) {
			throw new ServiceException(e);
		}// try-catch
	} // registerBadgeNotification
	
	// 모집글 삭제(모집취소)와 함께 알림 추가 
	public void removeNotificationById(Integer partyId) throws RuntimeException {
		log.debug("removeNotificationById({},{})", partyId);
	    try {
			List<Integer> userIds = joinMapper.selectUserIdsBySanPartyId(partyId); 	// 모집글에 참여한 사용자의 ID 가져오기

			for (Integer userId : userIds) {// 사용자에게 삭제 후 알림을 이미 받았는지 확인
				if (!notificationMapper.isExistsPartyDeletionNotification(userId, partyId)) { // 그렇지 않은 경우 새 알림 만들기
					log.trace(">>>>>>>>>> 모집글삭제로 인해 취소알림이 긴급으로 갑니다");
					notificationMapper.insertPartyDeletionByPartyIdAndUserId(partyId, userId);
				}
			}			
		}catch (Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}//removeNotificationById
	
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
	
} // end class
