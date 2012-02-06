$(function() {
			$('#mb1,#mb2,#mb3,#mb4,#mb5').menubutton({
						plain : false
					});
		});

/** 我要供货 */
function supplyOnClick() {
	$('#ifr').attr('src', ctx + '/supply/index.action');
}

/** 供货管理 */
function supplyManageOnClick() {
	$('#ifr').attr('src', ctx + '/supply/supplymanage.action');
}

/** 收款账户 */
function savingaccountOnClick() {
	$('#ifr').attr('src', ctx + '/savingaccount/index.action');
}

/** 已结货款 */
function paymentOnClick(){
	$('#ifr').attr('src', ctx + '/supply/payment.action');
}
