package org.zerock.wego.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.oauth.NaverOAuth;
import org.zerock.wego.service.common.WithdrawService;
import org.zerock.wego.service.oauth.KakaoService;
import org.zerock.wego.service.oauth.OAuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/withdrawal")
@Controller
public class WithdrawController {

private final WithdrawService withdrawService;
private final KakaoService kakaoService;
private final OAuthService oAuthService;
private final NaverOAuth naverOAuth;

@GetMapping("")
public String myAccountWithdrawal(@SessionAttribute(value = "__AUTH__", required = false) UserVO auth, HttpServletRequest req, Model model)
		throws ControllerException {
	
		log.trace("myAccountWithdrawal({}, {}) invoked.", auth, model);
		try {
			Integer userId = auth.getUserId();
			List<String> pathList = new ArrayList<>();
			
			//1. 프로필 이미지로 등록한 파일 삭제
			pathList = withdrawService.getMyProfileImagePathByUserId(userId);
			
			for(int i = 0; i <pathList.size(); i++) {
				String path = pathList.get(i);
				String pathName = pathList.get(i);
				// path가 "/"로 시작한다면, 맨 앞 "/"를 제거.
				if (path.startsWith("/")) {
					pathName = path.substring(1);
				}
				// "/"로 구분하여 opt > upload폴더 내의 파일인지 체크
				String[] array = pathName.split("/");
				if(array[0].equals("opt") && array[1].equals("upload")) {
					Path filePath = Paths.get(path);
		            // 파일 삭제
		            try {
						Files.delete(filePath);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//try-catch
				}//if
			}//for
			pathList.clear();
			
			//2. 모집 파일 이미지로 등록한 파일 삭제
			pathList = withdrawService.getMyPartyImagePathByUserId(userId);
			
			for(int i = 0; i <pathList.size(); i++) {
				String path = pathList.get(i);
				String pathName = pathList.get(i);
				// path가 "/"로 시작한다면, 맨 앞 "/"를 제거.
				if (path.startsWith("/")) {
					pathName = path.substring(1);
				}
				// "/"로 구분하여 opt > upload폴더 내의 파일인지 체크
				String[] array = pathName.split("/");
				if(array[0].equals("opt") && array[1].equals("upload")) {
					Path filePath = Paths.get(path);
		            // 파일 삭제
		            try {
						Files.delete(filePath);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//try-catch
				}//if
			}//for
			pathList.clear();
			
			//3. 리뷰 파일 이미지로 등록한 파일 삭제
			pathList = withdrawService.getMyReviewImagePathByUserId(userId);
			
			for(int i = 0; i <pathList.size(); i++) {
				String path = pathList.get(i);
				String pathName = pathList.get(i);
				// path가 "/"로 시작한다면, 맨 앞 "/"를 제거.
				if (path.startsWith("/")) {
					pathName = path.substring(1);
				}
				// "/"로 구분하여 opt > upload폴더 내의 파일인지 체크
				String[] array = pathName.split("/");
				if(array[0].equals("opt") && array[1].equals("upload")) {
					Path filePath = Paths.get(path);
		            // 파일 삭제
		            try {
						Files.delete(filePath);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//try-catch
				}//if
			}//for
			pathList.clear();
			
			//4. 파일테이블에 userId가 등록한 파일레코드 삭제
			Integer deleteMyProfileImageTable = 
					withdrawService.deleteMyProfileImageFileTableListByUserId(userId);
			Integer deleteMyPartyImageTable = 
					withdrawService.deleteMyPartyImageFileTableListByUserId(userId);
			Integer deleteMyReviewImageTable = 
					withdrawService.deleteMyReviewImageFileTableListByUserId(userId);
			
			//5. 모든 테이블에서 userId와 관련된 데이터 삭제
			withdrawService.deleteAllTableByMeByTableNameAndUserId(userId);
			
			//6. 소셜로그인 연결해제 : 카카오 연결해제
			String kakaoAccessToken = (String)req.getSession().getAttribute("KAKAO_ACCESS_TOKEN");
			kakaoService.unlinkKakao(kakaoAccessToken);
			//6. 소셜로그인 연결해제 : 네이버 연결해제
			String naverAccessToken = (String)req.getSession().getAttribute("NAVER_ACCESS_TOKEN");
			naverOAuth.unlinkNaver(naverAccessToken);
			
			return "redirect:/login/logout";
			
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
    }//myAccountWithdrawal

}// end class