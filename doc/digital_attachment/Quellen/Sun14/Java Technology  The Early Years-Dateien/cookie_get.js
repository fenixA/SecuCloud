




/*
     FILE ARCHIVED ON 23:36:00 Jan 9, 2010 AND RETRIEVED FROM THE
     INTERNET ARCHIVE ON 22:26:55 Okt 20, 2014.
     JAVASCRIPT APPENDED BY WAYBACK MACHINE, COPYRIGHT INTERNET ARCHIVE.

     ALL OTHER CONTENT MAY ALSO BE PROTECTED BY COPYRIGHT (17 U.S.C.
     SECTION 108(a)(3)).
*/
function getCookie(CookieName) {
if (document.cookie.length > 0) { 
mycookie = document.cookie.indexOf(CookieName+"=");
if (mycookie != -1) { mycookie += CookieName.length+1;
end = document.cookie.indexOf(";", mycookie);
if (end == -1) end = document.cookie.length;
return unescape(document.cookie.substring(mycookie, end));
}
}
return null;
} 