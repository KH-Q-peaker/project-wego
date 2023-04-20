var searchUrl;
var pathname = window.location.pathname;

if (pathname === '/') {
  searchUrl = '/search';
} else {
  var pageName = pathname.split('/')[1];
  if (pathname.includes('/search')) {
    searchUrl = pathname;
  } else {
    searchUrl = `/${pageName}/search`;
  }
}

var form = document.querySelector('form[action="/search"]');
form.action = searchUrl;