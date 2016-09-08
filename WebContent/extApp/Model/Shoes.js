Ext.define('inlineEditor.Model.Shoes', {

			extend : 'Ext.data.Model',
			idProperty : 'shoesId',
			fields : [{
						name : 'shoesId',
						type : 'string'
					}, {
						name : 'shoesName',
						type : 'string'
					}, {
						name : 'series',
						type : 'string'
					}, {
						name : 'category',
						type : 'string'
					}, {
						name : 'price',
						type : 'int'
					}, {
						name : 'brandId',
						type : 'string'
					}],
			validators : {
				'shoesName' : 'presence'
			},
			hasOne : 'Brand',
			proxy : {
				type : 'ajax',
				reader : {
					rootProperty : 'data',
					type : 'json'
				},
				writer : {
					type : 'json',
					rootrootProperty : 'data',
					writeAllFields : true
				},
				api : {
					read : 'ShoesTableController?action=list',
					update : 'ShoesTableController',
					create : 'ShoesTableController',
					destroy : 'ShoesTableController?action=delete'
				},
				actionMethods : {
					read : 'GET',
					update : 'POST',
					create : 'POST',
					destroy : 'POST'
				},
				idParam : 'shoesId',

				noCache : false
			}
		});