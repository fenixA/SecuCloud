




/*
     FILE ARCHIVED ON 14:33:34 Jan 7, 2010 AND RETRIEVED FROM THE
     INTERNET ARCHIVE ON 22:26:55 Okt 20, 2014.
     JAVASCRIPT APPENDED BY WAYBACK MACHINE, COPYRIGHT INTERNET ARCHIVE.

     ALL OTHER CONTENT MAY ALSO BE PROTECTED BY COPYRIGHT (17 U.S.C.
     SECTION 108(a)(3)).
*/
function openPopup(url,name,width,height,resizable,scrollbars,menubar,toolbar,location,directories,status) {	
	popup = window.open(url, name, 'width=' + width + ',height=' + height + ',resizable=' + resizable + ',scrollbars=' + scrollbars
	+ ',menubar=' + menubar + ',toolbar=' + toolbar + ',location=' + location + ',directories=' + directories + ',status=' + status
	);
//popup.moveTo(((screen.availWidth-340)/2),((screen.availHeight-360)/2));
	popup.focus();
}