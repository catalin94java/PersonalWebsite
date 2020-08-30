$("#controlPanelSignup").click(function () {
	
	   document.getElementById("controlPanelSignup").style.display = "none"; // to undisplay
	   document.getElementById("buttonreplacement").style.display = ""; // to display
	   return true;
});

var FirstLoading = true;
function RestoreSubmitButton()
{
   if( FirstLoading )
   {
      FirstLoading = false;
      return;
   }
   document.getElementById("controlPanelSignup").style.display = ""; // to display
   document.getElementById("buttonreplacement").style.display = "none"; // to undisplay
}
// To disable restoring submit button, disable or delete next line.
document.onfocus = RestoreSubmitButton;