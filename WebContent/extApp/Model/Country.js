Ext.define('inlineEditor.Model.Country', {

			extend : 'Ext.data.Model',
			fields : ['code', 'fullname'],
			
			 idProperty : 'code',
			// {
			// name : 'code',
			// type : 'string'
			// }, {
			// name : 'fullname',
			// type : 'string'
			// }
			proxy : {
				type : 'ajax',
				reader : {
					rootProperty : function(data) {
						keyArray = Object.keys(data);
						dataArray = [];
						var i;
						for (i = 0; i < keyArray.length; i++) {
							dataArray.push({
										'code' : keyArray[i],
										'fullname' : data[keyArray[i]]
									})
						}
						return dataArray;
					},
					type : 'json'
				},
				api : {
					read : 'countryMap.jsp'
				},
				actionMethods : {
					read : 'GET'
				},
				url : 'countryMap.jsp'
			}
		})