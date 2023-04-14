   function selectClickCurrPage() {
                 var currPage = event.target.innerText;
                 var amount = 10;
                 $.ajax({
                       type: 'get',
                       url: "/profile/userposts?userId=" + userId,
                       data:{"currPage":currPage,"amount":5,userId:"${userId}"},
                       success: function(data){
                           $("#module").load("/profile/userposts?userId="+userId+"&currPage="+currPage+"&amount="+amount);
                       }//success
                    });//ajax
             }//selectClickCurrPage
             
   function selectClickCurrPagePrev () {
          var currPage = $( '#currPagePrev' ).val();
          var amount = 10;
          $.ajax({
               type: 'get',
               url: "/profile/userposts?userId=" + userId,
               data:{"currPage":currPage,"amount":5,userId:"${userId}"},
               success: function(data){
                   $("#module").load("/profile/userposts?userId="+userId+"&currPage="+currPage+"&amount="+amount);
               }//success
            }); //ajax
         } //selectClickCurrPagePrev
   
    function selectClickCurrPageNext () {
           var amount = 10;
            var currPage = $( '#currPageNext' ).val();
            $.ajax({
                 type: 'get',
                  url: "/profile/userposts?userId=" + userId,
                  data:{"currPage":currPage,"amount":5,userId:"${userId}"},
                  success: function(data){
                      $("#module").load("/profile/userposts?userId="+userId+"&currPage="+currPage+"&amount="+amount);
                  }//success
            }); //ajax
   } //selectClickCurrPageNext
   
   
   // 댓글에 대한 페이징 처리 
      function selectClickCommentCurrPage() {
                 var currPage = event.target.innerText;
                 var amount = 10;
                 $.ajax({
                       type: 'get',
                       url: "/profile/comment?userId=" + userId,
                       data:{"currPage":currPage,"amount":5,userId:"${userId}"},
                       success: function(data){
                           $("#module").load("/profile/comment?userId="+userId+"&currPage="+currPage+"&amount="+amount);
                       }//success
                    });//ajax
             }//selectClickCommentCurrPage
             
   function selectClickCommentCurrPagePrev () {
          var currPage = $( '#currPagePrev' ).val();
          var amount = 10;
          $.ajax({
               type: 'get',
               url: "/profile/comment?userId=" + userId,
               data:{"currPage":currPage,"amount":5,userId:"${userId}"},
               success: function(data){
                   $("#module").load("/profile/comment?userId="+userId+"&currPage="+currPage+"&amount="+amount);
               }//success
            }); //ajax
         } //selectClickCommentCurrPagePrev
   
    function selectClickCommentCurrPageNext () {
           var amount = 10;
            var currPage = $( '#currPageNext' ).val();
            $.ajax({
                 type: 'get',
                  url: "/profile/comment?userId=" + userId,
                  data:{"currPage":currPage,"amount":5,userId:"${userId}"},
                  success: function(data){
                      $("#module").load("/profile/comment?userId="+userId+"&currPage="+currPage+"&amount="+amount);
                  }//success
            }); //ajax
   } //selectClickCommentCurrPageNext