// Set share button links
/*
if( document.getElementById("sharefb") )
{
	document.getElementById("sharefb").href = "https://facebook.com/sharer/sharer.php?u="+encodeURIComponent(window.location.href)+"&title="+encodeURIComponent(document.title);
	document.getElementById("sharetw").href = "https://twitter.com/intent/tweet?text="+encodeURIComponent(document.title)+"&url="+encodeURIComponent(window.location.href);
	document.getElementById("sharewa").href = "https://wa.me/?text="+encodeURIComponent(document.title)+" "+encodeURIComponent(window.location.href);
	document.getElementById("shareml").href = "mailto:?to=&subject="+encodeURIComponent(document.title)+"&body="+encodeURIComponent(window.location.href);
}
*/
function OnSubFb()
{
	ref = document.feedback;
	ref.URL.value = document.URL;
	if( ref.Message.value=="" )
		alert('Please enter your message');
	else
		ref.submit();
}
