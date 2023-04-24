package org.zerock.wego.service.ranking;

import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.badge.BadgeVO;
import org.zerock.wego.domain.ranking.RankingVO;
import org.zerock.wego.mapper.RankingMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service("rankingService")
public class RankingService {

	private final RankingMapper rankingMapper;
	
	// 한우물왕 1,2,3 위
	public LinkedBlockingDeque<RankingVO> getOneWay1To3() {
		log.trace("getOneWay1To3() invoked");

		LinkedBlockingDeque<RankingVO> ranking1To3Deque = this.rankingMapper.selectOneWayFromStartToEnd(1, 3);

		return ranking1To3Deque;
	} // getOneWay1To3

	// 한우물왕 4~30
	public LinkedBlockingDeque<RankingVO> getOneWay4To30() {
		log.trace("getOneWay4To30() invoked");

		LinkedBlockingDeque<RankingVO> ranking4To30Deque = this.rankingMapper.selectOneWayFromStartToEnd(4, 30);

		return ranking4To30Deque;
	} // getOneWay4To30

	// 해당 유저의 한우물왕 수치
	public RankingVO getOneWayUserRankingByUserId(Integer targetUserId) {
		log.trace("getOneWayUserRankingByUserId() invoked");

		RankingVO userRanking = this.rankingMapper.selectOneWayByUserId(targetUserId);

		return userRanking;
	} // getOneWayUserRankingByUserId

	// 제일높왕 1,2,3 위
	public LinkedBlockingDeque<RankingVO> getHighest1To3() {
		log.trace("getOneWay1To3() invoked");

		LinkedBlockingDeque<RankingVO> ranking1To3Deque = this.rankingMapper.selectHighestFromStartToEnd(1, 3);

		return ranking1To3Deque;
	} // getHighest1To3

	// 제일높왕 4~30
	public LinkedBlockingDeque<RankingVO> getHighest4To30() {
		log.trace("getOneWay4To30() invoked");

		LinkedBlockingDeque<RankingVO> ranking4To30Deque = this.rankingMapper.selectHighestFromStartToEnd(4, 30);

		return ranking4To30Deque;
	} // getHighest4To30

	// 해당 유저의 제일높왕 수치
	public RankingVO getHighestUserRankingByUserId(Integer targetUserId) {
		log.trace("getOneWayUserRankingByUserId() invoked");

		RankingVO userRanking = this.rankingMapper.selectHighestByUserId(targetUserId);

		return userRanking;
	} // getHighestUserRankingByUserId

	// 참대왕 1,2,3 위
	public LinkedBlockingDeque<RankingVO> getPartyKing1To3() {
		log.trace("getOneWay1To3() invoked");

		LinkedBlockingDeque<RankingVO> ranking1To3Deque = this.rankingMapper.selectPartyKingFromStartToEnd(1, 3);

		return ranking1To3Deque;
	} // getPartyKing1To3

	// 참대왕 4~30
	public LinkedBlockingDeque<RankingVO> getPartyKing4To30() {
		log.trace("getOneWay4To30() invoked");

		LinkedBlockingDeque<RankingVO> ranking4To30Deque = this.rankingMapper.selectPartyKingFromStartToEnd(4, 30);

		return ranking4To30Deque;
	} // getPartyKing4To30

	// 해당 유저의 참대왕 수치
	public RankingVO getPartyKingUserRankingByUserId(Integer targetUserId) {
		log.trace("getOneWayUserRankingByUserId() invoked");

		RankingVO userRanking = this.rankingMapper.selectPartyKingByUserId(targetUserId);

		return userRanking;
	} // getPartyKingUserRankingByUserId

	// 후기왕 1,2,3 위
	public LinkedBlockingDeque<RankingVO> getReviewKing1To3() {
		log.trace("getOneWay1To3() invoked");

		LinkedBlockingDeque<RankingVO> ranking1To3Deque = this.rankingMapper.selectReviewKingFromStartToEnd(1, 3);

		return ranking1To3Deque;
	} // getReviewKing1To3

	// 후기왕 4~30
	public LinkedBlockingDeque<RankingVO> getReviewKing4To30() {
		log.trace("getOneWay4To30() invoked");

		LinkedBlockingDeque<RankingVO> ranking4To30Deque = this.rankingMapper.selectReviewKingFromStartToEnd(4, 30);

		return ranking4To30Deque;
	} // getReviewKing4To30

	// 해당 유저의 후기왕 수치
	public RankingVO getReviewKingUserRankingByUserId(Integer targetUserId) {
		log.trace("getOneWayUserRankingByUserId() invoked");

		RankingVO userRanking = this.rankingMapper.selectReviewKingByUserId(targetUserId);

		return userRanking;
	} // getReviewKingUserRankingByUserId


} // end class
