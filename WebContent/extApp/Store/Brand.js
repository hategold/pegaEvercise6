Ext.define('inlineEditor.Store.Brand', {
			extend : 'Ext.data.Store',
			requires : ['inlineEditor.Model.Brand'],
			model : 'inlineEditor.Model.Brand',
			autoSync : false,
			autoLoad : true,
			storeId : 'Brand'
		});