$(document).ready(function () {
  $('.user-img, .user-name').click(function (event) {
    event.stopPropagation();
    event.preventDefault();
    var userId = $(this).attr('id');
    var url = '/profile/' + userId;
    window.location.href = url;
  });
});