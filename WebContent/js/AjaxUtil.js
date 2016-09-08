var ajaxUtil = {
	name : "ajaxUtil",
	makeAjaxRequest : function(url, requestData, callbackEntity = {},

			callbackFunction, type, errorCallback) {// set default
		var responseJson
		$.ajax({
					url : url,
					type : type,
					data : requestData,
					success : function(response) {
						callbackEntity.callback = callbackFunction;
						callbackEntity.callback(response);
					},
					error : function(response) {
						callbackEntity.errorCallback = errorCallback;
						callbackEntity.errorCallback();
					},
					contentType : "application/json; charset=utf-8"

				})
	}
}// different => get, post ,json ajax method
