Ext.define('inlineEditor.Model.Brand', {

			extend : 'Ext.data.Model',
			idProperty : 'brandId',
			fields : [{
						name : 'brandId',
						type : 'string',
						defaultValue : ''
					}, {
						name : 'brandName',
						type : 'string',
						defaultValue : 'Brand Name'
					}, {
						name : 'website',
						type : 'string',
						defaultValue : 'Website'
					}, {
						name : 'country',
						type : 'string',
						defaultValue : 'TWN'
					}],
			validators : {
				'brandName' : 'presence'
			},
			hasMany : 'Shoes',
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
					read : 'BrandTableController?action=list',
					update : 'BrandTableController',
					create : 'BrandTableController',
					destroy : 'BrandTableController?action=delete'
				},
				actionMethods : {
					read : 'GET',
					update : 'POST',
					create : 'POST',
					destroy : 'POST'
				},
				idParam : 'brandId',

				noCache : false
			}
		});