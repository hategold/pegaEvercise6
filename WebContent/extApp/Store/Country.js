Ext.define('inlineEditor.Store.Country', {
			extend : 'Ext.data.Store',
			requires : ['inlineEditor.Model.Country'],
			model : 'inlineEditor.Model.Country',
			autoSync : false,
			autoLoad : true,
			storeId : 'Country'
		});