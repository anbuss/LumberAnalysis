/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function validatePhone(txtPhone) {
	alert(txtPhone);
	var a = txtPhone;
	var filter = '/^[0-9-+]+$/';
	if (filter.test(a)) {
		return true;
	}
	else {
		return false;
	}
}​
function validateEmailvalues(sEmail) {
	alert(sEmail);
	var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	if (filter.test(sEmail)) {
		return true;
	}
	else {
		return false;
	}
}
function validateEmail1(sEmail) {
	var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	if (filter.test(sEmail)) {
		return true;
	}
	else {
		return false;
	}
}​