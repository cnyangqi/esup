// ---------------------------------------------公共代码开始-----------------------------------------------------


// ---------------------------------------------公共代码结束-----------------------------------------------------

// ---------------------------------------------编辑器代码开始---------------------------------------------------

var editor;

var config = {
	width : '100%',
	themeType : 'simple'
};

KindEditor.ready(function(K) {
	editor = K.create('#editor_id', config);
});

//---------------------------------------------编辑器代码结束---------------------------------------------------

//---------------------------------------------DOM渲染完毕，执行JavaScript初始化模块-----------------------------

$(function() {

});

//---------------------------------------------DOM渲染完毕，执行JavaScript初始化模块结束-------------------------